package com.github.theredbrain.mergeditems;

import com.github.theredbrain.mergeditems.gui.screen.ingame.MeldingScreen;
import com.github.theredbrain.mergeditems.registry.ScreenHandlerTypesRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class MergedItemsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		HandledScreens.register(ScreenHandlerTypesRegistry.MELDING_SCREEN_HANDLER, MeldingScreen::new);

	}
}