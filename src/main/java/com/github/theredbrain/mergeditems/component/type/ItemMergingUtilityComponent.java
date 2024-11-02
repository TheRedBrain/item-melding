package com.github.theredbrain.mergeditems.component.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record ItemMergingUtilityComponent(
		String possible_merging_items
) {
	public static final ItemMergingUtilityComponent DEFAULT = new ItemMergingUtilityComponent("");

	public static final Codec<ItemMergingUtilityComponent> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
							Codec.STRING.fieldOf("possible_merging_items").forGetter(ItemMergingUtilityComponent::possible_merging_items)
					)
					.apply(instance, ItemMergingUtilityComponent::new)
	);
	public static final PacketCodec<RegistryByteBuf, ItemMergingUtilityComponent> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.STRING,
			ItemMergingUtilityComponent::possible_merging_items,
			ItemMergingUtilityComponent::new
	);

	public ItemMergingUtilityComponent(
			String possible_merging_items
	) {
		this.possible_merging_items = possible_merging_items;
	}
}
