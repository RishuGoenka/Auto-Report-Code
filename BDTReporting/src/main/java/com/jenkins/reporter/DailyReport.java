package com.jenkins.reporter;

public class DailyReport {
	String buildStatus;
	String deploymentStatus;
	String testStatus;
	String FailedTestCount;
	String PassedTestCount;
	String SkippedTestCount;
	String TotalTestCount;

	public DailyReport() {
		super();
	}

	public DailyReport(String buildStatus, String deploymentStatus, String testStatus, String failedTestCount,
			String passedTestCount, String skippedTestCount, String totalTestCount) {
		super();
		this.buildStatus = buildStatus;
		this.deploymentStatus = deploymentStatus;
		this.testStatus = testStatus;
		FailedTestCount = failedTestCount;
		PassedTestCount = passedTestCount;
		SkippedTestCount = skippedTestCount;
		TotalTestCount = totalTestCount;
	}

	public String getBuildStatus() {
		return buildStatus;
	}

	public void setBuildStatus(String buildStatus) {
		this.buildStatus = buildStatus;
	}

	public String getDeploymentStatus() {
		return deploymentStatus;
	}

	public void setDeploymentStatus(String deploymentStatus) {
		this.deploymentStatus = deploymentStatus;
	}

	public String getTestStatus() {
		return testStatus;
	}

	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}

	public String getFailedTestCount() {
		return FailedTestCount;
	}

	public void setFailedTestCount(String failedTestCount) {
		FailedTestCount = failedTestCount;
	}

	public String getPassedTestCount() {
		return PassedTestCount;
	}

	public void setPassedTestCount(String passedTestCount) {
		PassedTestCount = passedTestCount;
	}

	public String getSkippedTestCount() {
		return SkippedTestCount;
	}

	public void setSkippedTestCount(String skippedTestCount) {
		SkippedTestCount = skippedTestCount;
	}

	public String getTotalTestCount() {
		return TotalTestCount;
	}

	public void setTotalTestCount(String totalTestCount) {
		TotalTestCount = totalTestCount;
	}

}
