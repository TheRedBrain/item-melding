package com.github.theredbrain.mergeditems.network.packet;

import com.github.theredbrain.mergeditems.MergedItems;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record SplitMergedItemStacksPacket() implements CustomPayload {
	public static final Id<SplitMergedItemStacksPacket> PACKET_ID = new Id<>(MergedItems.identifier("split_merged_item_stacks"));
	public static final PacketCodec<RegistryByteBuf, SplitMergedItemStacksPacket> PACKET_CODEC = PacketCodec.of(SplitMergedItemStacksPacket::write, SplitMergedItemStacksPacket::new);

	public SplitMergedItemStacksPacket(RegistryByteBuf registryByteBuf) {
		this();
	}

	private void write(RegistryByteBuf registryByteBuf) {
	}

	@Override
	public Id<? extends CustomPayload> getId() {
		return PACKET_ID;
	}
}
