package com.jenkins.reporter;

public class Report {
	int build_Success;
	int build_Aborted;
	int build_Failure;
	int build_Unstable;
	int deploy_Success;
	int deploy_Aborted;
	int deploy_Failure;
	int deploy_Unstable;
	int test_Success;
	int test_Aborted;
	int test_Failure;
	int test_Unstable;
	float Buildstability;
	float Deploystability;
	float Teststability;
	float BDTstability;
	int aborted;

	public Report() {
		super();
	}

	public Report(int build_Success, int build_Aborted, int build_Failure, int build_Unstable, int deploy_Success,
			int deploy_Aborted, int deploy_Failure, int deploy_Unstable, int test_Success, int test_Aborted,
			int test_Failure, int test_Unstable, float buildstability, float deploystability, float teststability,
			float bDTstability, int aborted) {
		super();
		this.build_Success = build_Success;
		this.build_Aborted = build_Aborted;
		this.build_Failure = build_Failure;
		this.build_Unstable = build_Unstable;
		this.deploy_Success = deploy_Success;
		this.deploy_Aborted = deploy_Aborted;
		this.deploy_Failure = deploy_Failure;
		this.deploy_Unstable = deploy_Unstable;
		this.test_Success = test_Success;
		this.test_Aborted = test_Aborted;
		this.test_Failure = test_Failure;
		this.test_Unstable = test_Unstable;
		Buildstability = buildstability;
		Deploystability = deploystability;
		Teststability = teststability;
		BDTstability = bDTstability;
		this.aborted = aborted;
	}

	public int getBuild_Success() {
		return build_Success;
	}

	public void setBuild_Success(int build_Success) {
		this.build_Success = build_Success;
	}

	public int getBuild_Aborted() {
		return build_Aborted;
	}

	public void setBuild_Aborted(int build_Aborted) {
		this.build_Aborted = build_Aborted;
	}

	public int getBuild_Failure() {
		return build_Failure;
	}

	public void setBuild_Failure(int build_Failure) {
		this.build_Failure = build_Failure;
	}

	public int getBuild_Unstable() {
		return build_Unstable;
	}

	public void setBuild_Unstable(int build_Unstable) {
		this.build_Unstable = build_Unstable;
	}

	public int getDeploy_Success() {
		return deploy_Success;
	}

	public void setDeploy_Success(int deploy_Success) {
		this.deploy_Success = deploy_Success;
	}

	public int getDeploy_Aborted() {
		return deploy_Aborted;
	}

	public void setDeploy_Aborted(int deploy_Aborted) {
		this.deploy_Aborted = deploy_Aborted;
	}

	public int getDeploy_Failure() {
		return deploy_Failure;
	}

	public void setDeploy_Failure(int deploy_Failure) {
		this.deploy_Failure = deploy_Failure;
	}

	public int getDeploy_Unstable() {
		return deploy_Unstable;
	}

	public void setDeploy_Unstable(int deploy_Unstable) {
		this.deploy_Unstable = deploy_Unstable;
	}

	public int getTest_Success() {
		return test_Success;
	}

	public void setTest_Success(int test_Success) {
		this.test_Success = test_Success;
	}

	public int getTest_Aborted() {
		return test_Aborted;
	}

	public void setTest_Aborted(int test_Aborted) {
		this.test_Aborted = test_Aborted;
	}

	public int getTest_Failure() {
		return test_Failure;
	}

	public void setTest_Failure(int test_Failure) {
		this.test_Failure = test_Failure;
	}

	public int getTest_Unstable() {
		return test_Unstable;
	}

	public void setTest_Unstable(int test_Unstable) {
		this.test_Unstable = test_Unstable;
	}

	public float getBuildstability() {
		return Buildstability;
	}

	public void setBuildstability(float buildstability) {
		Buildstability = buildstability;
	}

	public float getDeploystability() {
		return Deploystability;
	}

	public void setDeploystability(float deploystability) {
		Deploystability = deploystability;
	}

	public float getTeststability() {
		return Teststability;
	}

	public void setTeststability(float teststability) {
		Teststability = teststability;
	}

	public float getBDTstability() {
		return BDTstability;
	}

	public void setBDTstability(float bDTstability) {
		BDTstability = bDTstability;
	}

	public int getAborted() {
		return aborted;
	}

	public void setAborted(int aborted) {
		this.aborted = aborted;
	}

	@Override
	public String toString() {
		return "Report [build_Success=" + build_Success + ", build_Aborted=" + build_Aborted + ", build_Failure="
				+ build_Failure + ", build_Unstable=" + build_Unstable + ", deploy_Success=" + deploy_Success
				+ ", deploy_Aborted=" + deploy_Aborted + ", deploy_Failure=" + deploy_Failure + ", deploy_Unstable="
				+ deploy_Unstable + ", test_Success=" + test_Success + ", test_Aborted=" + test_Aborted
				+ ", test_Failure=" + test_Failure + ", test_Unstable=" + test_Unstable + ", Buildstability="
				+ Buildstability + ", Deploystability=" + Deploystability + ", Teststability=" + Teststability
				+ ", BDTstability=" + BDTstability + ", aborted=" + aborted + "]";
	}

}