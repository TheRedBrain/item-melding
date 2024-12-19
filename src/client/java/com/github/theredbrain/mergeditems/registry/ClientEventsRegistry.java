package com.github.theredbrain.mergeditems.registry;

import com.github.theredbrain.mergeditems.MergedItems;
import com.github.theredbrain.mergeditems.component.type.MergedItemsComponent;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class ClientEventsRegistry {

	public static void initializeClientEvents() {
		ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {

			MergedItemsComponent mergedItemsComponentOfContainerItemStack = stack.get(MergedItems.MERGED_ITEMS_COMPONENT_TYPE);

			if (mergedItemsComponentOfContainerItemStack != null) {
				if (!mergedItemsComponentOfContainerItemStack.isEmpty()) {
					lines.add(Text.empty());
					lines.add(Text.translatable("item.mergeditems.merged_items_component.tooltip.head_line"));
					for (ItemStack itemStack : mergedItemsComponentOfContainerItemStack.iterate()) {
						lines.add(Text.translatable("item.mergeditems.merged_items_component.tooltip.entry", itemStack.getName()));
					}
				}
			}
		});
	}
}
