package com.zycus.scriptExecutor.enums;

public enum UpgradeType {
	Utility("u"), CMS("c"), Patch("p"), Release("r");

	private String value;

	private UpgradeType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
