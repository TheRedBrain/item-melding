package com.github.theredbrain.mergeditems.network.packet;

import com.github.theredbrain.mergeditems.component.type.MergedItemsComponent;
import com.github.theredbrain.mergeditems.registry.ItemComponentRegistry;
import com.github.theredbrain.mergeditems.screen.ItemMergingScreenHandler;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class MergeItemStacksPacketReceiver implements ServerPlayNetworking.PlayPayloadHandler<MergeItemStacksPacket> {
	@Override
	public void receive(MergeItemStacksPacket payload, ServerPlayNetworking.Context context) {

		ServerPlayerEntity player = context.player();

		ScreenHandler screenHandler = player.currentScreenHandler;

		if (screenHandler instanceof ItemMergingScreenHandler itemMergingScreenHandler) {
			ItemStack containerItemStack = itemMergingScreenHandler.inventory.getStack(1);
			ItemStack meldedItemStack = itemMergingScreenHandler.inventory.getStack(0);

			List<Identifier> identifierList = itemMergingScreenHandler.getMergableItemTags();

			boolean bl = false;
			for (Identifier identifier : identifierList) {
				TagKey<Item> itemTagKey = TagKey.of(RegistryKeys.ITEM, identifier);
				bl = containerItemStack.isIn(itemTagKey);
			}
			if (!bl) {
				player.sendMessage(Text.translatable("hud.message.item_merging.item_can_not_be_merged_into"));
				return;
			}

			if (meldedItemStack.getMaxCount() > 1) {
				player.sendMessage(Text.translatable("hud.message.item_merging.item_can_not_be_merged_into_other_items"));
				return;
			}

			MergedItemsComponent mergedItemsComponentOfMeldedItemStack = meldedItemStack.get(ItemComponentRegistry.MERGED_ITEMS_COMPONENT_TYPE);

			if (mergedItemsComponentOfMeldedItemStack != null && !mergedItemsComponentOfMeldedItemStack.isEmpty()) {
				player.sendMessage(Text.translatable("hud.message.item_merging.merged_item_stack_has_items_merged"));
			}

			MergedItemsComponent mergedItemsComponentOfContainerItemStack = containerItemStack.get(ItemComponentRegistry.MERGED_ITEMS_COMPONENT_TYPE);

			if (mergedItemsComponentOfContainerItemStack == null) {
				player.sendMessage(Text.translatable("hud.message.item_merging.item_can_not_be_merged_into", containerItemStack.getName()));
			} else {
//				MergedItemsComponent containerStackMergedItemsComponent = containerItemStack.getOrDefault(ItemComponentRegistry.MELDING_COMPONENT_TYPE, MergedItemsComponent.DEFAULT);
//				List<ItemStack> meldedItemStacksOfContainerItemStack = containerStackMergedItemsComponent.melded_items();

				if (mergedItemsComponentOfContainerItemStack.size() >= itemMergingScreenHandler.getMaxMergedItemsAmount()) {
					player.sendMessage(Text.translatable("hud.message.item_merging.item_has_reached_max_amount_of_merged_items", containerItemStack.getName()));
					return;
				}

				MergedItemsComponent.Builder builder = new MergedItemsComponent.Builder(mergedItemsComponentOfContainerItemStack);
//			String possible_melded_items = containerStackMergedItemsComponent.possible_melded_items();
//			if (!Objects.equals(possible_melded_items, "") && !meldedItemStack.isIn(TagKey.of(Registries.ITEM.getKey(), Identifier.of(possible_melded_items)))) {
//				player.sendMessage(Text.translatable("hud.message.item_merging.item_can_not_be_melded_into_item"));
//				return;
//			}
				builder.add(meldedItemStack);
//				containerStackMergedItemsComponent.melded_items().add(meldedItemStack.copy());
				containerItemStack.set(ItemComponentRegistry.MERGED_ITEMS_COMPONENT_TYPE, builder.build());
//				containerItemStack.applyChanges(ComponentChanges.builder().add(ItemComponentRegistry.MELDING_COMPONENT_TYPE, containerStackMergedItemsComponent).build());

				itemMergingScreenHandler.inventory.setStack(0, ItemStack.EMPTY);
			}
		}
	}
}