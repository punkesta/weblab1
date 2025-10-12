package com.punksoft.weblab1.inventory;

public enum ItemId {
	AK_REDLINE("ak_redline", "AK \"Redline\""),
	AWP_DRAGON_LORE("awp_dragon_lore", "AWP \"Dragon Lore\""),
	M4_DEAD_SPACE("m4_dead_space", "M4 \"Dead Space\""),
	STICKER_NAVI("sticker_navi", "Sticker \"NAVI\""),
	DEFAULT_CASE("default_case", "\"Default\" Case");

	public final String id;
	public final String naming;

	ItemId(String id, String naming) {
		this.id = id;
		this.naming = naming;
	}
}