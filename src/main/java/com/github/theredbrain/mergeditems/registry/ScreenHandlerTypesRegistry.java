package com.github.theredbrain.mergeditems.registry;

import com.github.theredbrain.mergeditems.MergedItems;
import com.github.theredbrain.mergeditems.screen.MeldingScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;

public class ScreenHandlerTypesRegistry {
	public static final ScreenHandlerType<MeldingScreenHandler> MELDING_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(MeldingScreenHandler::new, MeldingScreenHandler.MeldingData.PACKET_CODEC);

	public static void registerAll() {
		Registry.register(Registries.SCREEN_HANDLER, MergedItems.identifier("melding"), MELDING_SCREEN_HANDLER);
	}
}
