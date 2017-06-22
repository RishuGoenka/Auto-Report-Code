package com.jenkins.reporter;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FinalReport")
public class FinalReport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DataSetId")
	private int dataSetId;

	@Column(name = "Product")
	private String product;

	@Column(name = "JenkinsBuildNumber")
	private String jenkinsBuildNumber;

	@Column(name = "BuildBranch")
	private String buildBranch;

	@Column(name = "BuildGeneratedBy")
	private String buildGeneratedBy;

	@Column(name = "BuildStartDate")
	private Date buildStartDate;

	@Column(name = "BuildDuration")
	private String buildDuration;

	@Column(name = "BuildStatus")
	private String buildStatus;

	@Column(name = "Destination_path")
	private String destination_path;

	@Column(name = "Destination_server")
	private String destination_server;

	@Column(name = "DeploymentStatus")
	private String deploymentStatus;

	@Column(name = "DeploymentStartDate")
	private Date deploymentStartDate;

	@Column(name = "DeploymentDuration")
	private String deploymentDuration;

	@Column(name = "Total_tc")
	private String total_tc;

	@Column(name = "Passed_tc")
	private String passed_tc;

	@Column(name = "Failed_tc")
	private String failed_tc;

	@Column(name = "Skipped_tc")
	private String skipped_tc;

	@Column(name = "TestDuration")
	private String testDuration;

	@Column(name = "TestStatus")
	private String testStatus;

	@Column(name = "QADeploymentStatus")
	private String qADeploymentStatus;

	public FinalReport() {
		super();
	}

	public FinalReport(int dataSetId, String product, String jenkinsBuildNumber, String buildBranch,
			String buildGeneratedBy, Date buildStartDate, String buildDuration, String buildStatus,
			String destination_path, String destination_server, String deploymentStatus, Date deploymentStartDate,
			String deploymentDuration, String total_tc, String passed_tc, String failed_tc, String skipped_tc,
			String testDuration, String testStatus, String qADeploymentStatus) {
		super();
		this.dataSetId = dataSetId;
		this.product = product;
		this.jenkinsBuildNumber = jenkinsBuildNumber;
		this.buildBranch = buildBranch;
		this.buildGeneratedBy = buildGeneratedBy;
		this.buildStartDate = buildStartDate;
		this.buildDuration = buildDuration;
		this.buildStatus = buildStatus;
		this.destination_path = destination_path;
		this.destination_server = destination_server;
		this.deploymentStatus = deploymentStatus;
		this.deploymentStartDate = deploymentStartDate;
		this.deploymentDuration = deploymentDuration;
		this.total_tc = total_tc;
		this.passed_tc = passed_tc;
		this.failed_tc = failed_tc;
		this.skipped_tc = skipped_tc;
		this.testDuration = testDuration;
		this.testStatus = testStatus;
		this.qADeploymentStatus = qADeploymentStatus;
	}

	public int getDataSetId() {
		return dataSetId;
	}

	public void setDataSetId(int dataSetId) {
		this.dataSetId = dataSetId;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getJenkinsBuildNumber() {
		return jenkinsBuildNumber;
	}

	public void setJenkinsBuildNumber(String jenkinsBuildNumber) {
		this.jenkinsBuildNumber = jenkinsBuildNumber;
	}

	public String getBuildBranch() {
		return buildBranch;
	}

	public void setBuildBranch(String buildBranch) {
		this.buildBranch = buildBranch;
	}

	public String getBuildGeneratedBy() {
		return buildGeneratedBy;
	}

	public void setBuildGeneratedBy(String buildGeneratedBy) {
		this.buildGeneratedBy = buildGeneratedBy;
	}

	public Date getBuildStartDate() {
		return buildStartDate;
	}

	public void setBuildStartDate(Date buildStartDate) {
		this.buildStartDate = buildStartDate;
	}

	public String getBuildDuration() {
		return buildDuration;
	}

	public void setBuildDuration(String buildDuration) {
		this.buildDuration = buildDuration;
	}

	public String getBuildStatus() {
		return buildStatus;
	}

	public void setBuildStatus(String buildStatus) {
		this.buildStatus = buildStatus;
	}

	public String getDestination_path() {
		return destination_path;
	}

	public void setDestination_path(String destination_path) {
		this.destination_path = destination_path;
	}

	public String getDestination_server() {
		return destination_server;
	}

	public void setDestination_server(String destination_server) {
		this.destination_server = destination_server;
	}

	public String getDeploymentStatus() {
		return deploymentStatus;
	}

	public void setDeploymentStatus(String deploymentStatus) {
		this.deploymentStatus = deploymentStatus;
	}

	public Date getDeploymentStartDate() {
		return deploymentStartDate;
	}

	public void setDeploymentStartDate(Date deploymentStartDate) {
		this.deploymentStartDate = deploymentStartDate;
	}

	public String getDeploymentDuration() {
		return deploymentDuration;
	}

	public void setDeploymentDuration(String deploymentDuration) {
		this.deploymentDuration = deploymentDuration;
	}

	public String getTotal_tc() {
		return total_tc;
	}

	public void setTotal_tc(String total_tc) {
		this.total_tc = total_tc;
	}

	public String getPassed_tc() {
		return passed_tc;
	}

	public void setPassed_tc(String passed_tc) {
		this.passed_tc = passed_tc;
	}

	public String getFailed_tc() {
		return failed_tc;
	}

	public void setFailed_tc(String failed_tc) {
		this.failed_tc = failed_tc;
	}

	public String getSkipped_tc() {
		return skipped_tc;
	}

	public void setSkipped_tc(String skipped_tc) {
		this.skipped_tc = skipped_tc;
	}

	public String getTestDuration() {
		return testDuration;
	}

	public void setTestDuration(String testDuration) {
		this.testDuration = testDuration;
	}

	public String getTestStatus() {
		return testStatus;
	}

	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}

	public String getqADeploymentStatus() {
		return qADeploymentStatus;
	}

	public void setqADeploymentStatus(String qADeploymentStatus) {
		this.qADeploymentStatus = qADeploymentStatus;
	}

	@Override
	public String toString() {
		return "FinalReport [dataSetId=" + dataSetId + ", product=" + product + ", jenkinsBuildNumber="
				+ jenkinsBuildNumber + ", buildBranch=" + buildBranch + ", buildGeneratedBy=" + buildGeneratedBy
				+ ", buildStartDate=" + buildStartDate + ", buildDuration=" + buildDuration + ", buildStatus="
				+ buildStatus + ", destination_path=" + destination_path + ", destination_server=" + destination_server
				+ ", deploymentStatus=" + deploymentStatus + ", deploymentStartDate=" + deploymentStartDate
				+ ", deploymentDuration=" + deploymentDuration + ", total_tc=" + total_tc + ", passed_tc=" + passed_tc
				+ ", failed_tc=" + failed_tc + ", skipped_tc=" + skipped_tc + ", testDuration=" + testDuration
				+ ", testStatus=" + testStatus + ", qADeploymentStatus=" + qADeploymentStatus + "]";
	}

}
