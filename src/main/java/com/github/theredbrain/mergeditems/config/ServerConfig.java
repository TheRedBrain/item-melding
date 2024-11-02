package com.github.theredbrain.mergeditems.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(
		name = "mergeditems"
)
public class ServerConfig extends PartitioningSerializer.GlobalData {
	@ConfigEntry.Category("general_server")
	@ConfigEntry.Gui.Excluded
	public GeneralServerConfig server = new GeneralServerConfig();

	public ServerConfig() {
	}

	@Config(
			name = "general_server"
	)
	public static class GeneralServerConfig implements ConfigData {
		@Comment("""
				When set to 'false', merging items will add up all their modifiers.
				
				When set to 'true', similar modifiers (same id, same slot, same operation) will be averaged.
				""")
		public boolean merging_averages_similar_modifiers = true;

		public GeneralServerConfig() {

		}
	}

}
