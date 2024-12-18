package com.github.theredbrain.mergeditems.config;

import com.github.theredbrain.mergeditems.MergedItems;
import me.fzzyhmstrs.fzzy_config.annotations.Comment;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;

public class ServerConfig extends Config {

	public ServerConfig() {
		super(MergedItems.identifier("server"));
	}

	@Comment("""
				When set to 'false', merging items will add up all their modifiers.
				
				When set to 'true', similar modifiers (same id, same slot, same operation) will be averaged.
				""")
	public ValidatedBoolean merging_averages_similar_modifiers = new ValidatedBoolean(true);

}
