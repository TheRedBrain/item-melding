package com.github.theredbrain.mergeditems.network.packet;

import com.github.theredbrain.mergeditems.MergedItems;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record MeldItemStacksPacket() implements CustomPayload {
	public static final Id<MeldItemStacksPacket> PACKET_ID = new Id<>(MergedItems.identifier("meld_item_stacks"));
	public static final PacketCodec<RegistryByteBuf, MeldItemStacksPacket> PACKET_CODEC = PacketCodec.of(MeldItemStacksPacket::write, MeldItemStacksPacket::new);

	public MeldItemStacksPacket(RegistryByteBuf registryByteBuf) {
		this();
	}

	private void write(RegistryByteBuf registryByteBuf) {
	}

	@Override
	public Id<? extends CustomPayload> getId() {
		return PACKET_ID;
	}
}
