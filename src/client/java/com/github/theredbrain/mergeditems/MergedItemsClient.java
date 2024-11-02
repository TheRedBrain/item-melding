package com.github.theredbrain.mergeditems;

import com.github.theredbrain.mergeditems.gui.screen.ingame.ItemMergingScreen;
import com.github.theredbrain.mergeditems.registry.ClientEventsRegistry;
import com.github.theredbrain.mergeditems.registry.ScreenHandlerTypesRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class MergedItemsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		HandledScreens.register(ScreenHandlerTypesRegistry.MERGING_ITEMS_SCREEN_HANDLER, ItemMergingScreen::new);

		ClientEventsRegistry.initializeClientEvents();
	}
}