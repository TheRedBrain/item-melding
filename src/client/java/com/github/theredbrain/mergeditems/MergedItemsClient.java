package com.github.theredbrain.mergeditems;

import com.github.theredbrain.mergeditems.config.ClientConfig;
import com.github.theredbrain.mergeditems.gui.screen.ingame.ItemMergingScreen;
import com.github.theredbrain.mergeditems.network.packet.OpenItemMergingScreenPacket;
import com.github.theredbrain.mergeditems.registry.ClientEventsRegistry;
import com.github.theredbrain.mergeditems.registry.ScreenHandlerTypesRegistry;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.util.Identifier;

import java.util.List;

public class MergedItemsClient implements ClientModInitializer {
	public static ClientConfig CLIENT_CONFIG = ConfigApiJava.registerAndLoadConfig(ClientConfig::new, RegisterType.BOTH);
	@Override
	public void onInitializeClient() {

		HandledScreens.register(ScreenHandlerTypesRegistry.MERGING_ITEMS_SCREEN_HANDLER, ItemMergingScreen::new);

		ClientEventsRegistry.initializeClientEvents();
	}

	public static void openItemMergingScreen(MinecraftClient client, int maxMergedItemsAmount, String title, List<Identifier> list) {
		if (client.player != null) {
			ClientPlayNetworking.send(new OpenItemMergingScreenPacket(maxMergedItemsAmount, title, list));
		}
	}

}