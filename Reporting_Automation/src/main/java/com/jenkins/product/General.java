package com.jenkins.product;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import com.jenkins.database.HibernateWrappers;
import com.jenkins.reporter.CIBuild;
import com.jenkins.reporter.FinalReport;
import com.jenkins.reporter.SubBuild;
import com.jenkins.reporter.build_deploy_details;
import com.jenkins.reporter.build_run_details;
import com.jenkins.reporter.build_test_details;
import com.jenkins.reporter.jenkins_build_details;

public class General {

	static Logger log = Logger.getLogger(General.class.getClass());
	static final String BUILDSTATUS_ABORTED = "ABORTED";
	static final String BUILD_ABORTED = "BUILD_ABORTED";
	static final String BUILDSTATUS_FAILURE = "FAILURE";
	static final String CI = "CI";
	static final String NO_VALUE = "-";

	static final String BUILD = "_Build";
	static final String BDTD = "_BDTD";
	static final String DEPLOY = "_deploy";
	static final String TEST = "_Test";
	static final String QA = "_QA_deploy";
	static final String JENKINS_API = "http://192.168.5.252:8090/job/";
	static final String BDTD_API = "_BDTD/api/xml";

	static final String PRODUCT = "product";
	static final String FULLNAME = "fullname";
	static final String PRODUCT_NAME = "product_name";
	static final String NAME = "name";
	static final String BUILDNUMBER = "jenkinsBuildNumber";

	public FinalReport productAlgoCode1(CIBuild ciBuild) {
		try {
			String productName = ciBuild.getCiName();
			FinalReport finalReport = new FinalReport();
			List<SubBuild> subBuilds = ciBuild.getSubBuildList();
			for (SubBuild subBuild : subBuilds) {

				finalReport.setProduct(productName);
				finalReport.setJenkinsBuildNumber(ciBuild.getBuildNumber());

				if ((productName + BUILD).equals(subBuild.getSubBuildJobName())) {
					if (HibernateWrappers.getObjectListByRestrictionPostgreSQL(
							Restrictions.and(Restrictions.like(FULLNAME, "%" + subBuild.getSubBuildBuildNumber() + "%"),
									Restrictions.eq(PRODUCT_NAME, productName)),
							build_run_details.class).isEmpty()) {
						finalReport.setBuildBranch(NO_VALUE);

					} else {
						build_run_details run_details = HibernateWrappers
								.getObjectListByRestrictionPostgreSQL(
										Restrictions.and(
												Restrictions.like(FULLNAME,
														"%" + subBuild.getSubBuildBuildNumber() + "%"),
												Restrictions.eq(PRODUCT_NAME, productName)),
										build_run_details.class)
								.get(0);
						finalReport.setBuildBranch(run_details.getRepo_url());
					}

					if (HibernateWrappers
							.getObjectListByRestrictionPostgreSQL(
									Restrictions.and(Restrictions.like(FULLNAME, "%" + ciBuild.getBuildNumber() + "%"),
											Restrictions.eq(NAME, productName + BDTD)),
									jenkins_build_details.class)
							.isEmpty()) {
						finalReport.setBuildGeneratedBy(NO_VALUE);
						finalReport.setBuildStartDate(null);

					} else {
						jenkins_build_details jenkinsBuildDetailsCI = HibernateWrappers
								.getObjectListByRestrictionPostgreSQL(
										Restrictions.and(
												Restrictions.like(FULLNAME, "%" + ciBuild.getBuildNumber() + "%"),
												Restrictions.eq(NAME, productName + BDTD)),
										jenkins_build_details.class)
								.get(0);
						if (jenkinsBuildDetailsCI.getUsername() == null) {
							finalReport.setBuildGeneratedBy(CI);
						} else {
							finalReport.setBuildGeneratedBy(jenkinsBuildDetailsCI.getUsername());
						}
						finalReport.setBuildStartDate(jenkinsBuildDetailsCI.getStartdate());
					}
					finalReport.setBuildDuration(subBuild.getDuration());
					finalReport.setBuildStatus(subBuild.getSubBuildStatus());
				} else {
					finalReport.setBuildBranch(NO_VALUE);
					finalReport.setBuildGeneratedBy(NO_VALUE);
					finalReport.setBuildStartDate(null);
					finalReport.setBuildDuration(NO_VALUE);
					finalReport.setBuildStatus(subBuild.getSubBuildStatus());
				}
				finalReport.setDestination_path(NO_VALUE);
				finalReport.setDestination_server(NO_VALUE);
				finalReport.setDeploymentStartDate(null);
				finalReport.setDeploymentStatus(NO_VALUE);
				finalReport.setDeploymentDuration(NO_VALUE);
				finalReport.setTotal_tc(NO_VALUE);
				finalReport.setPassed_tc(NO_VALUE);
				finalReport.setFailed_tc(NO_VALUE);
				finalReport.setSkipped_tc(NO_VALUE);
				finalReport.setTestDuration(NO_VALUE);
				finalReport.setTestStatus(NO_VALUE);
				finalReport.setqADeploymentStatus(NO_VALUE);
			}
			HibernateWrappers.updateContractFolder(finalReport);
			return finalReport;
		} catch (Exception e) {
			log.debug("productAlgoCode1 : " + e);
			System.out.println("productAlgoCode1 : somthing is wrong");
			return null;
		}
	}

	public FinalReport productAlgoCode3(CIBuild ciBuild) {
		try {
			String productName = ciBuild.getCiName();
			boolean status = HibernateWrappers.getObjectListByRestrictionPostgreSQL(
					Restrictions.and(Restrictions.eq(BUILDNUMBER, ciBuild.getBuildNumber()),
							Restrictions.like(PRODUCT, productName)),
					FinalReport.class).isEmpty();
			if (status) {
				FinalReport finalReport;
				finalReport = new FinalReport();
				List<SubBuild> subBuilds = ciBuild.getSubBuildList();
				for (SubBuild subBuild : subBuilds) {
					if ((productName + BUILD).equals(subBuild.getSubBuildJobName())) {
						finalReport.setJenkinsBuildNumber(ciBuild.getBuildNumber());
						finalReport.setProduct(productName);

						if (HibernateWrappers
								.getObjectListByRestrictionPostgreSQL(
										Restrictions.and(
												Restrictions.like(FULLNAME,
														"%" + subBuild.getSubBuildBuildNumber() + "%"),
												Restrictions.eq(PRODUCT_NAME, productName)),
										build_run_details.class)
								.isEmpty()) {
							finalReport.setBuildBranch(NO_VALUE);

						} else {
							build_run_details run_details = HibernateWrappers
									.getObjectListByRestrictionPostgreSQL(
											Restrictions.and(
													Restrictions.like(FULLNAME,
															"%" + subBuild.getSubBuildBuildNumber() + "%"),
													Restrictions.eq(PRODUCT_NAME, productName)),
											build_run_details.class)
									.get(0);
							finalReport.setBuildBranch(run_details.getRepo_url());
						}

						if (HibernateWrappers
								.getObjectListByRestrictionPostgreSQL(
										Restrictions.and(
												Restrictions.like(FULLNAME, "%" + ciBuild.getBuildNumber() + "%"),
												Restrictions.eq(NAME, productName + BDTD)),
										jenkins_build_details.class)
								.isEmpty()) {
							finalReport.setBuildGeneratedBy(NO_VALUE);
							finalReport.setBuildStartDate(null);

						} else {
							jenkins_build_details jenkinsBuildDetailsCI = HibernateWrappers
									.getObjectListByRestrictionPostgreSQL(
											Restrictions.and(
													Restrictions.like(FULLNAME, "%" + ciBuild.getBuildNumber() + "%"),
													Restrictions.eq(NAME, productName + BDTD)),
											jenkins_build_details.class)
									.get(0);
							if (jenkinsBuildDetailsCI.getUsername() == null) {
								finalReport.setBuildGeneratedBy(CI);
							} else {
								finalReport.setBuildGeneratedBy(jenkinsBuildDetailsCI.getUsername());
							}
							finalReport.setBuildStartDate(jenkinsBuildDetailsCI.getStartdate());
						}
						finalReport.setBuildDuration(subBuild.getDuration());
						finalReport.setBuildStatus(subBuild.getSubBuildStatus());
					} else if ((productName + DEPLOY).equals(subBuild.getSubBuildJobName())) {

						if (HibernateWrappers
								.getObjectListByRestrictionPostgreSQL(
										Restrictions.and(
												Restrictions.like(FULLNAME,
														"%" + subBuild.getSubBuildBuildNumber() + "%"),
												Restrictions.eq(PRODUCT_NAME, productName)),
										build_deploy_details.class)
								.isEmpty()) {
							finalReport.setDestination_path(NO_VALUE);
							finalReport.setDestination_server(NO_VALUE);
							finalReport.setDeploymentStartDate(null);

						} else {
							build_deploy_details deployDetails = HibernateWrappers
									.getObjectListByRestrictionPostgreSQL(
											Restrictions.and(
													Restrictions.like(FULLNAME,
															"%" + subBuild.getSubBuildBuildNumber() + "%"),
													Restrictions.eq(PRODUCT_NAME, productName)),
											build_deploy_details.class)
									.get(0);
							finalReport.setDestination_path(deployDetails.getDestination_path());
							finalReport.setDestination_server(deployDetails.getDestination_server());
							finalReport.setDeploymentStartDate(deployDetails.getCreated_dtm());
						}
						finalReport.setDeploymentDuration(subBuild.getDuration());
						finalReport.setDeploymentStatus(subBuild.getSubBuildStatus());
						finalReport.setFailed_tc(NO_VALUE);
						finalReport.setTotal_tc(NO_VALUE);
						finalReport.setPassed_tc(NO_VALUE);
						finalReport.setSkipped_tc(NO_VALUE);
						finalReport.setTestDuration(NO_VALUE);
						finalReport.setTestStatus(NO_VALUE);
						finalReport.setqADeploymentStatus(NO_VALUE);
					}
				}
				HibernateWrappers.updateContractFolder(finalReport);
				return finalReport;
			}
		} catch (Exception e) {
			log.debug("productAlgoCode3 : " + e);
			System.out.println("productAlgoCode3 : somthing is wrong");
		}
		return null;
	}

	public FinalReport productAlgoCode4(CIBuild ciBuild) {
		try {
			String productName = ciBuild.getCiName();
			boolean status = HibernateWrappers.getObjectListByRestrictionPostgreSQL(
					Restrictions.and(Restrictions.eq(BUILDNUMBER, ciBuild.getBuildNumber()),
							Restrictions.like(PRODUCT, "%" + productName + "%")),
					FinalReport.class).isEmpty();
			if (status) {
				FinalReport finalReport;
				finalReport = new FinalReport();
				List<SubBuild> subBuilds = ciBuild.getSubBuildList();
				for (SubBuild subBuild : subBuilds) {
					if ((productName + BUILD).equals(subBuild.getSubBuildJobName())) {
						finalReport.setJenkinsBuildNumber(ciBuild.getBuildNumber());
						finalReport.setProduct(productName);

						if (HibernateWrappers
								.getObjectListByRestrictionPostgreSQL(
										Restrictions.and(
												Restrictions.like(FULLNAME,
														"%" + subBuild.getSubBuildBuildNumber() + "%"),
												Restrictions.eq(PRODUCT_NAME, productName)),
										build_run_details.class)
								.isEmpty()) {
							finalReport.setBuildBranch(NO_VALUE);
						} else {
							build_run_details run_details = HibernateWrappers
									.getObjectListByRestrictionPostgreSQL(
											Restrictions.and(
													Restrictions.like(FULLNAME,
															"%" + subBuild.getSubBuildBuildNumber() + "%"),
													Restrictions.eq(PRODUCT_NAME, productName)),
											build_run_details.class)
									.get(0);
							finalReport.setBuildBranch(run_details.getRepo_url());
						}

						if (HibernateWrappers
								.getObjectListByRestrictionPostgreSQL(
										Restrictions.and(
												Restrictions.like(FULLNAME, "%" + ciBuild.getBuildNumber() + "%"),
												Restrictions.eq(NAME, productName + BDTD)),
										jenkins_build_details.class)
								.isEmpty()) {
							finalReport.setBuildGeneratedBy(NO_VALUE);
							finalReport.setBuildStartDate(null);
						} else {
							jenkins_build_details jenkinsBuildDetailsCI = HibernateWrappers
									.getObjectListByRestrictionPostgreSQL(
											Restrictions.and(
													Restrictions.like(FULLNAME, "%" + ciBuild.getBuildNumber() + "%"),
													Restrictions.eq(NAME, productName + BDTD)),
											jenkins_build_details.class)
									.get(0);
							if (jenkinsBuildDetailsCI.getUsername() == null) {
								finalReport.setBuildGeneratedBy(CI);
							} else {
								finalReport.setBuildGeneratedBy(jenkinsBuildDetailsCI.getUsername());
							}
							finalReport.setBuildStartDate(jenkinsBuildDetailsCI.getStartdate());
						}
						finalReport.setBuildDuration(subBuild.getDuration());
						finalReport.setBuildStatus(subBuild.getSubBuildStatus());
					} else if ((productName + DEPLOY).equals(subBuild.getSubBuildJobName())) {

						if (HibernateWrappers
								.getObjectListByRestrictionPostgreSQL(
										Restrictions.and(
												Restrictions.like(FULLNAME,
														"%" + subBuild.getSubBuildBuildNumber() + "%"),
												Restrictions.eq(PRODUCT_NAME, productName)),
										build_deploy_details.class)
								.isEmpty()) {
							finalReport.setDestination_path(NO_VALUE);
							finalReport.setDestination_server(NO_VALUE);
							finalReport.setDeploymentStartDate(null);
						} else {
							build_deploy_details deployDetails = HibernateWrappers
									.getObjectListByRestrictionPostgreSQL(
											Restrictions.and(
													Restrictions.like(FULLNAME,
															"%" + subBuild.getSubBuildBuildNumber() + "%"),
													Restrictions.eq(PRODUCT_NAME, productName)),
											build_deploy_details.class)
									.get(0);
							finalReport.setDestination_path(deployDetails.getDestination_path());
							finalReport.setDestination_server(deployDetails.getDestination_server());
							finalReport.setDeploymentStartDate(deployDetails.getCreated_dtm());
						}
						finalReport.setDeploymentDuration(subBuild.getDuration());
						finalReport.setDeploymentStatus(subBuild.getSubBuildStatus());
					} else if ((productName + TEST).equals(subBuild.getSubBuildJobName())) {
						if (HibernateWrappers
								.getObjectListByRestrictionPostgreSQL(
										Restrictions.and(
												Restrictions.like(FULLNAME,
														"%" + subBuild.getSubBuildBuildNumber() + "%"),
												Restrictions.eq(PRODUCT_NAME, productName)),
										build_test_details.class)
								.isEmpty()) {
							finalReport.setTotal_tc(NO_VALUE);
							finalReport.setPassed_tc(NO_VALUE);
							finalReport.setFailed_tc(NO_VALUE);
							finalReport.setSkipped_tc(NO_VALUE);
						} else {
							build_test_details testDetailsSUB = HibernateWrappers
									.getObjectListByRestrictionPostgreSQL(
											Restrictions.and(
													Restrictions.like(FULLNAME,
															"%" + subBuild.getSubBuildBuildNumber() + "%"),
													Restrictions.eq(PRODUCT_NAME, productName)),
											build_test_details.class)
									.get(0);
							finalReport.setTotal_tc(testDetailsSUB.getTotal_tc());
							finalReport.setPassed_tc(testDetailsSUB.getPassed_tc());
							finalReport.setFailed_tc(testDetailsSUB.getFailed_tc());
							finalReport.setSkipped_tc(testDetailsSUB.getSkipped_tc());
						}
						finalReport.setTestDuration(subBuild.getDuration());
						finalReport.setTestStatus(subBuild.getSubBuildStatus());
						finalReport.setqADeploymentStatus(NO_VALUE);
					}
				}
				HibernateWrappers.updateContractFolder(finalReport);
				return finalReport;
			}
		} catch (Exception e) {
			log.debug("productAlgoCode4 : " + e);
			System.out.println("productAlgoCode4 : somthing is wrong");
		}
		return null;
	}

	public FinalReport productAlgoCode5(CIBuild ciBuild) {
		try {
			String productName = ciBuild.getCiName();
			boolean status = HibernateWrappers.getObjectListByRestrictionPostgreSQL(
					Restrictions.and(Restrictions.eq(BUILDNUMBER, ciBuild.getBuildNumber()),
							Restrictions.like(PRODUCT, productName)),
					FinalReport.class).isEmpty();
			if (status) {
				FinalReport finalReport;
				finalReport = new FinalReport();
				List<SubBuild> subBuilds = ciBuild.getSubBuildList();
				for (SubBuild subBuild : subBuilds) {
					if ((productName + BUILD).equals(subBuild.getSubBuildJobName())) {
						finalReport.setJenkinsBuildNumber(ciBuild.getBuildNumber());
						finalReport.setProduct(productName);

						if (HibernateWrappers
								.getObjectListByRestrictionPostgreSQL(
										Restrictions.and(
												Restrictions.like(FULLNAME,
														"%" + subBuild.getSubBuildBuildNumber() + "%"),
												Restrictions.eq(PRODUCT_NAME, productName)),
										build_run_details.class)
								.isEmpty()) {
							finalReport.setBuildBranch(NO_VALUE);
						} else {
							build_run_details run_details = HibernateWrappers
									.getObjectListByRestrictionPostgreSQL(
											Restrictions.and(
													Restrictions.like(FULLNAME,
															"%" + subBuild.getSubBuildBuildNumber() + "%"),
													Restrictions.eq(PRODUCT_NAME, productName)),
											build_run_details.class)
									.get(0);
							finalReport.setBuildBranch(run_details.getRepo_url());
						}

						if (HibernateWrappers
								.getObjectListByRestrictionPostgreSQL(
										Restrictions.and(
												Restrictions.like(FULLNAME, "%" + ciBuild.getBuildNumber() + "%"),
												Restrictions.eq(NAME, productName + BDTD)),
										jenkins_build_details.class)
								.isEmpty()) {
							finalReport.setBuildGeneratedBy(NO_VALUE);
							finalReport.setBuildStartDate(null);

						} else {
							jenkins_build_details jenkinsBuildDetailsCI = HibernateWrappers
									.getObjectListByRestrictionPostgreSQL(
											Restrictions.and(
													Restrictions.like(FULLNAME, "%" + ciBuild.getBuildNumber() + "%"),
													Restrictions.eq(NAME, productName + BDTD)),
											jenkins_build_details.class)
									.get(0);
							if (jenkinsBuildDetailsCI.getUsername() == null) {
								finalReport.setBuildGeneratedBy(CI);
							} else {
								finalReport.setBuildGeneratedBy(jenkinsBuildDetailsCI.getUsername());
							}
							finalReport.setBuildGeneratedBy(jenkinsBuildDetailsCI.getUsername().isEmpty() ? CI
									: jenkinsBuildDetailsCI.getUsername());
							finalReport.setBuildStartDate(jenkinsBuildDetailsCI.getStartdate());
						}
						finalReport.setBuildDuration(subBuild.getDuration());
						finalReport.setBuildStatus(subBuild.getSubBuildStatus());
					} else if ((productName + DEPLOY).equals(subBuild.getSubBuildJobName())) {

						if (HibernateWrappers
								.getObjectListByRestrictionPostgreSQL(
										Restrictions.and(
												Restrictions.like(FULLNAME,
														"%" + subBuild.getSubBuildBuildNumber() + "%"),
												Restrictions.eq(PRODUCT_NAME, productName)),
										build_deploy_details.class)
								.isEmpty()) {
							finalReport.setDestination_path(NO_VALUE);
							finalReport.setDestination_server(NO_VALUE);
							finalReport.setDeploymentStartDate(null);

						} else {
							build_deploy_details deployDetails = HibernateWrappers
									.getObjectListByRestrictionPostgreSQL(
											Restrictions.and(
													Restrictions.like(FULLNAME,
															"%" + subBuild.getSubBuildBuildNumber() + "%"),
													Restrictions.eq(PRODUCT_NAME, productName)),
											build_deploy_details.class)
									.get(0);
							finalReport.setDestination_path(deployDetails.getDestination_path());
							finalReport.setDestination_server(deployDetails.getDestination_server());
							finalReport.setDeploymentStartDate(deployDetails.getCreated_dtm());
						}
						finalReport.setDeploymentDuration(subBuild.getDuration());
						finalReport.setDeploymentStatus(subBuild.getSubBuildStatus());
					} else if ((productName + TEST).equals(subBuild.getSubBuildJobName())) {
						if (HibernateWrappers
								.getObjectListByRestrictionPostgreSQL(
										Restrictions.and(
												Restrictions.like(FULLNAME,
														"%" + subBuild.getSubBuildBuildNumber() + "%"),
												Restrictions.eq(PRODUCT_NAME, productName)),
										build_test_details.class)
								.isEmpty()) {
							finalReport.setTotal_tc(NO_VALUE);
							finalReport.setPassed_tc(NO_VALUE);
							finalReport.setFailed_tc(NO_VALUE);
							finalReport.setSkipped_tc(NO_VALUE);

						} else {
							build_test_details testDetailsSUB = HibernateWrappers
									.getObjectListByRestrictionPostgreSQL(
											Restrictions.and(
													Restrictions.like(FULLNAME,
															"%" + subBuild.getSubBuildBuildNumber() + "%"),
													Restrictions.eq(PRODUCT_NAME, productName)),
											build_test_details.class)
									.get(0);
							finalReport.setTotal_tc(testDetailsSUB.getTotal_tc());
							finalReport.setPassed_tc(testDetailsSUB.getPassed_tc());
							finalReport.setFailed_tc(testDetailsSUB.getFailed_tc());
							finalReport.setSkipped_tc(testDetailsSUB.getSkipped_tc());
						}
						finalReport.setTestDuration(subBuild.getDuration());
						finalReport.setTestStatus(subBuild.getSubBuildStatus());
					} else if ((productName + QA).equals(subBuild.getSubBuildJobName())) {
						finalReport.setqADeploymentStatus(NO_VALUE);
					}
				}
				HibernateWrappers.updateContractFolder(finalReport);
				return finalReport;
			}
		} catch (Exception e) {
			log.debug("productAlgoCode5 : " + e);
			System.out.println("productAlgoCode5 : somthing is wrong");
		}
		return null;
	}
}
