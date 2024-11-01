package com.github.theredbrain.mergeditems.network.packet;

import com.github.theredbrain.mergeditems.component.type.MeldingComponent;
import com.github.theredbrain.mergeditems.registry.ItemComponentRegistry;
import com.github.theredbrain.mergeditems.screen.MeldingScreenHandler;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.component.ComponentChanges;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class SplitMeldedItemStacksPacketReceiver implements ServerPlayNetworking.PlayPayloadHandler<SplitMeldedItemStacksPacket> {
	@Override
	public void receive(SplitMeldedItemStacksPacket payload, ServerPlayNetworking.Context context) {

		ServerPlayerEntity player = context.player();

		ScreenHandler screenHandler = player.currentScreenHandler;

		if (screenHandler instanceof MeldingScreenHandler meldingScreenHandler) {
			ItemStack containerItemStack = meldingScreenHandler.inventory.getStack(2);

			MeldingComponent meldingComponent = containerItemStack.getOrDefault(ItemComponentRegistry.MELDING_COMPONENT_TYPE, MeldingComponent.DEFAULT);

			if (meldingComponent.melded_items().isEmpty()) {
				player.sendMessage(Text.translatable("hud.message.melding.no_melded_items"));
				return;
			}

			if (!meldingScreenHandler.inventory.getStack(3).isEmpty()) {
				player.sendMessage(Text.translatable("hud.message.melding.extract_slot_not_empty"));
				return;
			}

			ItemStack extractedItemStack = meldingComponent.melded_items().removeLast();
			containerItemStack.applyChanges(ComponentChanges.builder().add(ItemComponentRegistry.MELDING_COMPONENT_TYPE, meldingComponent).build());

			meldingScreenHandler.inventory.setStack(3, extractedItemStack);
		}
	}
}