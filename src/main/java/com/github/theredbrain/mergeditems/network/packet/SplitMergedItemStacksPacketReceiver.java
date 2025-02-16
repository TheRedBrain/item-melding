package com.github.theredbrain.mergeditems.network.packet;

import com.github.theredbrain.mergeditems.MergedItems;
import com.github.theredbrain.mergeditems.component.type.MergedItemsComponent;
import com.github.theredbrain.mergeditems.screen.ItemMergingScreenHandler;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class SplitMergedItemStacksPacketReceiver implements ServerPlayNetworking.PlayPayloadHandler<SplitMergedItemStacksPacket> {
	@Override
	public void receive(SplitMergedItemStacksPacket payload, ServerPlayNetworking.Context context) {

		ServerPlayerEntity player = context.player();

		ScreenHandler screenHandler = player.currentScreenHandler;

		if (screenHandler instanceof ItemMergingScreenHandler itemMergingScreenHandler) {
			ItemStack containerItemStack = itemMergingScreenHandler.inventory.getStack(2);

			if (containerItemStack.isEmpty()) {
				player.sendMessage(Text.translatable("hud.message.item_splitting.container_stack_empty"));
				return;
			}

			MergedItemsComponent mergedItemsComponent = containerItemStack.get(MergedItems.MERGED_ITEMS_COMPONENT_TYPE);

			if (mergedItemsComponent == null) {
				player.sendMessage(Text.translatable("hud.message.item_splitting.no_merged_items", containerItemStack.getName()));
			} else {

				if (mergedItemsComponent.isEmpty()) {
					player.sendMessage(Text.translatable("hud.message.item_splitting.no_merged_items"));
					return;
				}

				if (!itemMergingScreenHandler.inventory.getStack(3).isEmpty()) {
					player.sendMessage(Text.translatable("hud.message.item_splitting.extract_slot_not_empty"));
					return;
				}

				MergedItemsComponent.Builder builder = new MergedItemsComponent.Builder(mergedItemsComponent);

				ItemStack extractedItemStack = builder.removeLast();

				containerItemStack.set(MergedItems.MERGED_ITEMS_COMPONENT_TYPE, builder.build());
				itemMergingScreenHandler.inventory.setStack(3, extractedItemStack);
			}
		}
	}
}