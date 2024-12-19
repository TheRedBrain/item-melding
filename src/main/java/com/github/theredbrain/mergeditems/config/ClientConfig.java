package com.github.theredbrain.mergeditems.config;

import com.github.theredbrain.mergeditems.MergedItems;
import me.fzzyhmstrs.fzzy_config.annotations.Comment;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;

public class ClientConfig extends Config {

	public ClientConfig() {
		super(MergedItems.identifier("client"));
	}

	public ValidatedBoolean show_inactive_inventory_slots = new ValidatedBoolean(true);

}
