package com.jenkins.reporter;

public class SubBuild {

	private String subBuildJobName;
	private String subBuildStatus;
	private String subBuildBuildNumber;
	private String duration;

	public SubBuild() {
		super();
	}

	public SubBuild(String subBuildJobName, String subBuildStatus, String subBuildBuildNumber, String duration) {
		super();
		this.subBuildJobName = subBuildJobName;
		this.subBuildStatus = subBuildStatus;
		this.subBuildBuildNumber = subBuildBuildNumber;
		this.duration = duration;
	}

	public String getSubBuildJobName() {
		return subBuildJobName;
	}

	public void setSubBuildJobName(String subBuildJobName) {
		this.subBuildJobName = subBuildJobName;
	}

	public String getSubBuildStatus() {
		return subBuildStatus;
	}

	public void setSubBuildStatus(String subBuildStatus) {
		this.subBuildStatus = subBuildStatus;
	}

	public String getSubBuildBuildNumber() {
		return subBuildBuildNumber;
	}

	public void setSubBuildBuildNumber(String subBuildBuildNumber) {
		this.subBuildBuildNumber = subBuildBuildNumber;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "SubBuild [subBuildJobName=" + subBuildJobName + ", subBuildStatus=" + subBuildStatus
				+ ", subBuildBuildNumber=" + subBuildBuildNumber + ", duration=" + duration + "]";
	}

}
