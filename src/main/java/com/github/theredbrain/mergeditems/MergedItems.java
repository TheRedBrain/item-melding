package com.github.theredbrain.mergeditems;

import com.github.theredbrain.inventorysizeattributes.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.mergeditems.config.ServerConfig;
import com.github.theredbrain.mergeditems.registry.BlockRegistry;
import com.github.theredbrain.mergeditems.registry.ItemComponentRegistry;
import com.github.theredbrain.mergeditems.registry.ScreenHandlerTypesRegistry;
import com.github.theredbrain.mergeditems.registry.ServerPacketRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MergedItems implements ModInitializer {
	public static final String MOD_ID = "mergeditems";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ServerConfig.GeneralServerConfig generalServerConfig;

	public static final boolean isInventorySizeAttributesLoaded = FabricLoader.getInstance().isModLoaded("inventorysizeattributes");

	public static int getActiveInventorySize(PlayerEntity player) {
		return isInventorySizeAttributesLoaded ? ((DuckPlayerEntityMixin) player).inventorysizeattributes$getActiveInventorySlotAmount() : 27;
	}

	public static int getActiveHotbarSize(PlayerEntity player) {
		return isInventorySizeAttributesLoaded ? ((DuckPlayerEntityMixin) player).inventorysizeattributes$getActiveHotbarSlotAmount() : 9;
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Merging your items since 2024!");

		// Config
		AutoConfig.register(ServerConfig.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
		generalServerConfig = ((ServerConfig) AutoConfig.getConfigHolder(ServerConfig.class).getConfig()).server;

		BlockRegistry.init();
		ItemComponentRegistry.init();
		ScreenHandlerTypesRegistry.registerAll();
		ServerPacketRegistry.init();
	}

	public static Identifier identifier(String path) {
		return Identifier.of(MOD_ID, path);
	}
}