package com.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import com.jenkins.database.TestDataSessionFactoryForMsSQL;
import com.jenkins.product.General;
import com.jenkins.reporter.CIBuild;
import com.jenkins.reporter.JenkinsReport;

public class ConsolidatedReport {

	public static Logger log = Logger.getLogger(ExcelReporting.class.getName());
	public static final String JENKINS_API = "http://192.168.5.252:8090/job/";
	public static final String BDTD_API = "_BDTD/api/xml";
	public static final String ICONTRACT = "iContract";
	public static final String ISOURCE = "iSource";
	public static final String ISAVE = "iSave";
	public static final String IMANAGE = "iManage";
	public static Map<String, String> productMapping = new HashMap<String, String>();

	public static void consolidatedFinalReportForAllProuct() {
		try {
			TestDataSessionFactoryForMsSQL.getSessionFactory();
			productMapping.put(JENKINS_API + ICONTRACT + BDTD_API, ICONTRACT);
			productMapping.put(JENKINS_API + IMANAGE + BDTD_API, IMANAGE);
			productMapping.put(JENKINS_API + ISAVE + BDTD_API, ISAVE);
			productMapping.put(JENKINS_API + ISOURCE + BDTD_API, ISOURCE);
			JenkinsReport jenkinsReport = new JenkinsReport();
			Map<String, ArrayList<CIBuild>> mapOfCIList = new HashMap<String, ArrayList<CIBuild>>();

			for (Map.Entry<String, String> entry : productMapping.entrySet()) {
				ArrayList<CIBuild> listOfCiBuild = (ArrayList<CIBuild>) jenkinsReport.buildCIObject(entry.getKey(),
						entry.getValue());
				mapOfCIList.put(entry.getValue(), listOfCiBuild);
			}

			General generalProduct = new General();
			for (Map.Entry<String, ArrayList<CIBuild>> entry2 : mapOfCIList.entrySet()) {
				for (CIBuild ciBuild : entry2.getValue()) {
					if (ciBuild.getCount() == 1)
						generalProduct.productAlgoCode1(ciBuild);
					else if (ciBuild.getCount() == 3)
						generalProduct.productAlgoCode3(ciBuild);
					else if (ciBuild.getCount() == 5)
						generalProduct.productAlgoCode5(ciBuild);
					else if (ciBuild.getCount() == 4)
						generalProduct.productAlgoCode4(ciBuild);
				}
			}
		} catch (Exception e) {
			log.debug("consolidatedFinalReportForAllProuct :" + e);
		}
	}

}
