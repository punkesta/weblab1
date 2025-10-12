package com.punksoft.weblab1.inventory;

public enum Weapon {

	AK("ak", "AK", "ak.png"),
	M4("m4", "M4", "m4.png"),
	AWP("awp", "AWP", "awp.png"),;

	public final String id;
	public final String naming;
	public final String iconPath;

	Weapon(String id, String naming, String iconPath) {
		this.id = id;
		this.naming = naming;
		this.iconPath = iconPath;
	}
}