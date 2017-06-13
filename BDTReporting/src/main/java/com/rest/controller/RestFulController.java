package com.rest.controller;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.jenkins.reporter.CIBuild;
import com.jenkins.reporter.JenkinsReport;

@RestController()
@RequestMapping("/rest")
public class RestFulController {

	static Logger log = Logger.getLogger(RestFulController.class.getName());
	JenkinsReport jenkinsReport = new JenkinsReport();

	@RequestMapping(path = "/generateJenkinsReport", method = RequestMethod.GET)
	public @ResponseBody List<CIBuild> generateJenkinsReport() {
		try {
			List<CIBuild> ciBuilds = jenkinsReport.buildCIObject(null, null);
			return ciBuilds;
		} catch (Exception e) {
			log.debug(e);
			return null;
		}
	}

}
