package com.jenkins.reporter;

import java.util.List;

public class CIBuild {
	private String buildNumber;
	private String ciName;
	private List<SubBuild> subBuildList;
	private Integer count;

	public CIBuild() {
		super();
	}

	public CIBuild(String buildNumber, String ciName, List<SubBuild> subBuildList, Integer count) {
		super();
		this.buildNumber = buildNumber;
		this.ciName = ciName;
		this.subBuildList = subBuildList;
		this.count = count;
	}

	public String getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	public String getCiName() {
		return ciName;
	}

	public void setCiName(String ciName) {
		this.ciName = ciName;
	}

	public List<SubBuild> getSubBuildList() {
		return subBuildList;
	}

	public void setSubBuildList(List<SubBuild> subBuildList) {
		this.subBuildList = subBuildList;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "CIBuild [buildNumber=" + buildNumber + ", ciName=" + ciName + ", subBuildList=" + subBuildList
				+ ", count=" + count + "]";
	}

}
