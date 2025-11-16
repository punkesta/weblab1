package com.noname.weblabs.inventory;

public enum Collection {

	DEFAULT("default", "Default", "default.png"),
	ANNIVERSARY("anniversary", "Anniversary", "anniversary.png");

	public final String id;
	public final String naming;
	public final String iconPath;

	Collection(String id, String naming, String iconPath) {
		this.id = id;
		this.naming = naming;
		this.iconPath = iconPath;
	}
}