package com.jenkins.reporter;

import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class JenkinsReport {

	public static Logger log = Logger.getLogger(JenkinsReport.class.getName());


	public List<CIBuild> buildCIObject(String API, String value){
		try {
			List<CIBuild> ciBuildList = new ArrayList<CIBuild>();
			URL url = null;
			URL urlLastBuild;
			Document dom = null;
			Document dom1 = null;
			String suiteTitle = "Jenkins Test Suite Report";
			String emailContent = suiteTitle + "<br/><br/>--------------------------<br/><br/>";
			CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
			final String password = "Apr@1234";
			emailContent = emailContent + "Getting status of \""
					+ /* dt.toString() */"view" + "\": ";
			if (API.contains("BDT")) {
				Authenticator.setDefault(new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("sam.samuel@zycus.com", password.toCharArray());
					}
				});
				String loginPassword = "sam.samuel@zycus.com" + ":" + password;
				String encoded = new sun.misc.BASE64Encoder().encode(loginPassword.getBytes());
				url = new URL(API);
				URLConnection conn = url.openConnection();
				conn.setRequestProperty("Authorization", "Basic " + encoded);
				dom = new SAXReader().read(conn.getInputStream());
			} else {
				url = new URL(API);
				dom = new SAXReader().read(url);
			}
			String x = API.split("/api")[0];
			String lastBuildvalue = getJobString(x, API);
			if (API.contains("BDT")) {
				Authenticator.setDefault(new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("sam.samuel@zycus.com", password.toCharArray());
					}
				});
				String loginPassword = "sam.samuel@zycus.com" + ":" + password;
				String encoded = new sun.misc.BASE64Encoder().encode(loginPassword.getBytes());
				urlLastBuild = new URL(lastBuildvalue);
				URLConnection conn = urlLastBuild.openConnection();
				conn.setRequestProperty("Authorization", "Basic " + encoded);
				dom1 = new SAXReader().read(conn.getInputStream());
			} else {
				urlLastBuild = new URL(lastBuildvalue);
				dom1 = new SAXReader().read(urlLastBuild);
			}
			List<Element> buildElements = dom.selectNodes("//multiJobProject/build");

			List<Element> buildElements1 = dom1.selectNodes("//multiJobBuild");

			String lastBuildValue = buildElements1.get(0).selectSingleNode("//multiJobBuild/displayName").getText()
					.trim();

			Integer lastBuildNumber = Integer.parseInt(lastBuildValue.split("#")[1]);

			CIBuild ciBuild = null;

			for (Element buildElement : buildElements) {
				ciBuild = new CIBuild();
				List<SubBuild> subBuildList = new ArrayList<SubBuild>();
				Element element = buildElement;
				ciBuild.setBuildNumber(element.selectSingleNode("number").getText().trim());
				if (lastBuildNumber >= Integer.parseInt(ciBuild.getBuildNumber())) {
					List<Element> subBuildElements = element.selectNodes("./subBuild");
					ciBuild.setCount(subBuildElements.size());
					for (Element subBuildElement : subBuildElements) {
						SubBuild subBuild = new SubBuild();
						subBuild.setSubBuildJobName(subBuildElement.selectSingleNode("jobName").getText().trim());
						subBuild.setSubBuildStatus(subBuildElement.selectSingleNode("result").getText().trim());
						subBuild.setSubBuildBuildNumber(
								subBuildElement.selectSingleNode("buildNumber").getText().trim());
						subBuild.setDuration(subBuildElement.selectSingleNode("duration").getText().trim());
						subBuildList.add(subBuild);
					}
					ciBuild.setSubBuildList(subBuildList);
					ciBuild.setCiName(value);
					ciBuildList.add(ciBuild);
				}
			}
			return ciBuildList;
		} catch (Exception e) {
			log.debug(e);
			return null;
		}
	}

	private String getJobString(String x, String api) {
		try {
			String job = "/lastCompletedBuild/api/xml?tree=displayName,result,url,subBuilds[jobName,parentBuildNumber,result,url]";
			if ("http://192.168.5.252:8090/job/iContract_BDTD/api/xml".equals(api))
				return x + job;
			else if ("http://192.168.5.252:8090/job/iSave_BDTD/api/xml".equals(api))
				return x + job;
			else if ("http://192.168.5.252:8090/job/iSource_BDTD/api/xml".equals(api))
				return x + job;
			else if ("http://192.168.5.252:8090/job/iManage_BDTD/api/xml".equals(api))
				return x + job;
		} catch (Exception e) {
			log.debug(e);
		}
		return api;
	}


}
