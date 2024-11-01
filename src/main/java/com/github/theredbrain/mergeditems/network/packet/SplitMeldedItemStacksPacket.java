package com.github.theredbrain.mergeditems.network.packet;

import com.github.theredbrain.mergeditems.MergedItems;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record SplitMeldedItemStacksPacket() implements CustomPayload {
	public static final Id<SplitMeldedItemStacksPacket> PACKET_ID = new Id<>(MergedItems.identifier("split_melded_item_stacks"));
	public static final PacketCodec<RegistryByteBuf, SplitMeldedItemStacksPacket> PACKET_CODEC = PacketCodec.of(SplitMeldedItemStacksPacket::write, SplitMeldedItemStacksPacket::new);

	public SplitMeldedItemStacksPacket(RegistryByteBuf registryByteBuf) {
		this();
	}

	private void write(RegistryByteBuf registryByteBuf) {
	}

	@Override
	public Id<? extends CustomPayload> getId() {
		return PACKET_ID;
	}
}
