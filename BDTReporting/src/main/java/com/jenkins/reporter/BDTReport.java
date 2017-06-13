package com.jenkins.reporter;

public class BDTReport {
	int buildSuccessCount;
	int deploymentSuccessCount;
	int testSuccessCount;
	int TotalBuildCount;

	double SuccessBuildPercentage;
	double SuccessDeployPercentage;
	double SuccessTestPercentage;
	double SuccessBDTPercentage;

	public int getBuildSuccessCount() {
		return buildSuccessCount;
	}

	public void setBuildSuccessCount(int buildSuccessCount) {
		this.buildSuccessCount = buildSuccessCount;
	}

	public int getDeploymentSuccessCount() {
		return deploymentSuccessCount;
	}

	public void setDeploymentSuccessCount(int deploymentSuccessCount) {
		this.deploymentSuccessCount = deploymentSuccessCount;
	}

	public int getTestSuccessCount() {
		return testSuccessCount;
	}

	public void setTestSuccessCount(int testSuccessCount) {
		this.testSuccessCount = testSuccessCount;
	}

	public int getTotalBuildCount() {
		return TotalBuildCount;
	}

	public void setTotalBuildCount(int totalBuildCount) {
		TotalBuildCount = totalBuildCount;
	}

	public double getSuccessBuildPercentage() {
		return SuccessBuildPercentage;
	}

	public void setSuccessBuildPercentage(double successBuildPercentage) {
		SuccessBuildPercentage = successBuildPercentage;
	}

	public double getSuccessDeployPercentage() {
		return SuccessDeployPercentage;
	}

	public void setSuccessDeployPercentage(double successDeployPercentage) {
		SuccessDeployPercentage = successDeployPercentage;
	}

	public double getSuccessTestPercentage() {
		return SuccessTestPercentage;
	}

	public void setSuccessTestPercentage(double successTestPercentage) {
		SuccessTestPercentage = successTestPercentage;
	}

	public double getSuccessBDTPercentage() {
		return SuccessBDTPercentage;
	}

	public void setSuccessBDTPercentage(double successBDTPercentage) {
		SuccessBDTPercentage = successBDTPercentage;
	}

}
