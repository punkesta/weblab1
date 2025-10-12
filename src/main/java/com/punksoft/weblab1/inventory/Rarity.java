package com.punksoft.weblab1.inventory;

public enum Rarity {

	CASE("case", "Case", 10, -2976719, -279206),
	STICKERPACK("stickerpack", "Stickerpack", 20, -2976719, -279206),
	CHARMPACK("charmpack", "Charmpack", 30, -2976719, -279206),
	GRAFFITIPACK("graffitipack", "Graffitipack", 40, -2976719, -279206),
	BOX("box", "Box", 50, -9338746, -1),
	NAMELESS("nameless", "Nameless", 60, -2976719, -279206),
	ARCANE("arcane", "Arcane", 70, -5301729, -2604216),
	LEGENDARY("legendary", "Legendary", 80, -6220420, -3522907),
	EPIC("epic", "Epic", 90, -9632087, -6934584),
	RARE("rare", "Rare", 100, -15778924, -13081411),
	UNCOMMON("uncommon", "Uncommon", 110, -15700577, -13003064),
	COMMON("common", "Common", 120, -9338746, -1),
	NONE("none", "None", 130, -10263706, -1),;

	public final String id;
	public final String naming;
	public final int priority;
	public final int color;
	public final int textColor;

	Rarity(String id, String naming, int priority, int color, int textColor) {
		this.id = id;
		this.naming = naming;
		this.priority = priority;
		this.color = color;
		this.textColor = textColor;
	}

	public static Rarity getById(String id) {
		for (var v : values()) {
			if (v.id.equals(id)) return v;
		}
		return null;
	}
}