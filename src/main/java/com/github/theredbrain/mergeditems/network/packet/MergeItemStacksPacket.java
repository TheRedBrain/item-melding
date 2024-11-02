package com.github.theredbrain.mergeditems.network.packet;

import com.github.theredbrain.mergeditems.MergedItems;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record MergeItemStacksPacket() implements CustomPayload {
	public static final Id<MergeItemStacksPacket> PACKET_ID = new Id<>(MergedItems.identifier("merge_item_stacks"));
	public static final PacketCodec<RegistryByteBuf, MergeItemStacksPacket> PACKET_CODEC = PacketCodec.of(MergeItemStacksPacket::write, MergeItemStacksPacket::new);

	public MergeItemStacksPacket(RegistryByteBuf registryByteBuf) {
		this();
	}

	private void write(RegistryByteBuf registryByteBuf) {
	}

	@Override
	public Id<? extends CustomPayload> getId() {
		return PACKET_ID;
	}
}
