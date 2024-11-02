package com.github.theredbrain.mergeditems.registry;

import com.github.theredbrain.mergeditems.MergedItems;
import com.github.theredbrain.mergeditems.screen.ItemMergingScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;

public class ScreenHandlerTypesRegistry {
	public static final ScreenHandlerType<ItemMergingScreenHandler> MERGING_ITEMS_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(ItemMergingScreenHandler::new, ItemMergingScreenHandler.ItemMergingData.PACKET_CODEC);

	public static void registerAll() {
		Registry.register(Registries.SCREEN_HANDLER, MergedItems.identifier("merging_items"), MERGING_ITEMS_SCREEN_HANDLER);
	}
}
