package com.github.theredbrain.mergeditems.network.packet;

import com.github.theredbrain.mergeditems.MergedItems;
import com.github.theredbrain.mergeditems.component.type.MeldingComponent;
import com.github.theredbrain.mergeditems.registry.ItemComponentRegistry;
import com.github.theredbrain.mergeditems.screen.MeldingScreenHandler;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.component.ComponentChanges;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Objects;

public class MeldItemStacksPacketReceiver implements ServerPlayNetworking.PlayPayloadHandler<MeldItemStacksPacket> {
	@Override
	public void receive(MeldItemStacksPacket payload, ServerPlayNetworking.Context context) {

		ServerPlayerEntity player = context.player();

		ScreenHandler screenHandler = player.currentScreenHandler;

		if (screenHandler instanceof MeldingScreenHandler meldingScreenHandler) {
			ItemStack containerItemStack = meldingScreenHandler.inventory.getStack(1);
			ItemStack meldedItemStack = meldingScreenHandler.inventory.getStack(0);

			List<Identifier> identifierList = meldingScreenHandler.getMeldableItemTags();

			boolean bl = false;
			for (Identifier identifier : identifierList) {
				MergedItems.LOGGER.info("checked item tag id: " + identifier);
				MergedItems.LOGGER.info("containerItemStack: " + containerItemStack);
				TagKey<Item> itemTagKey = TagKey.of(RegistryKeys.ITEM, identifier);
				bl = containerItemStack.isIn(itemTagKey);
			}
			if (!bl) {
				player.sendMessage(Text.translatable("hud.message.melding.item_can_not_be_melded_into"));
				return;
			}

			if (meldedItemStack.getMaxCount() > 1) {
				player.sendMessage(Text.translatable("hud.message.melding.item_can_not_be_melded_into_other_items"));
				return;
			}

			List<ItemStack> meldedItemStacksOfMeldedItemStack = meldedItemStack.getOrDefault(ItemComponentRegistry.MELDING_COMPONENT_TYPE, MeldingComponent.DEFAULT).melded_items();

			if (!meldedItemStacksOfMeldedItemStack.isEmpty()) {
				player.sendMessage(Text.translatable("hud.message.melding.melded_item_stack_has_melds"));
				return;
			}

			MeldingComponent containerStackMeldingComponent = containerItemStack.getOrDefault(ItemComponentRegistry.MELDING_COMPONENT_TYPE, MeldingComponent.DEFAULT);
			List<ItemStack> meldedItemStacksOfContainerItemStack = containerStackMeldingComponent.melded_items();

			if (meldedItemStacksOfContainerItemStack.size() >= meldingScreenHandler.getMaxMeldingAmount()) {
				player.sendMessage(Text.translatable("hud.message.melding.item_has_reached_max_amount_of_melded_items"));
				return;
			}

			String possible_melded_items = containerStackMeldingComponent.possible_melded_items();
			if (!Objects.equals(possible_melded_items, "") && !meldedItemStack.isIn(TagKey.of(Registries.ITEM.getKey(), Identifier.of(possible_melded_items)))) {
				player.sendMessage(Text.translatable("hud.message.melding.item_can_not_be_melded_into_item"));
				return;
			}
			containerStackMeldingComponent.melded_items().add(meldedItemStack.copy());
			containerItemStack.applyChanges(ComponentChanges.builder().add(ItemComponentRegistry.MELDING_COMPONENT_TYPE, containerStackMeldingComponent).build());

			meldingScreenHandler.inventory.setStack(0, ItemStack.EMPTY);
		}
	}
}