package com.punksoft.weblab1.inventory;

public enum Type {

	LOOTBOX("lootbox", "Lootbox", 10),
	GLOVES("gloves", "Gloves", 20),
	KNIFE("knife", "Knife", 30),
	GUN("gun", "Gun", 50),
	STICKER("sticker", "Sticker", 60);

	public final String id;
	public final String naming;
	public final int priority;

	Type(String id, String naming, int priority) {
		this.id = id;
		this.naming = naming;
		this.priority = priority;
	}
}