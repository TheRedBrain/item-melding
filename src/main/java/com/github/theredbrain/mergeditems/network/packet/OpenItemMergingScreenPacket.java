package com.github.theredbrain.mergeditems.network.packet;

import com.github.theredbrain.mergeditems.MergedItems;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.List;

public record OpenItemMergingScreenPacket(
		int maxMergedItemsAmount,
		String title,
		List<Identifier> list
) implements CustomPayload {
	public static final CustomPayload.Id<OpenItemMergingScreenPacket> PACKET_ID = new CustomPayload.Id<>(MergedItems.identifier("open_item_merging_screen"));
	public static final PacketCodec<RegistryByteBuf, OpenItemMergingScreenPacket> PACKET_CODEC = PacketCodec.of(OpenItemMergingScreenPacket::write, OpenItemMergingScreenPacket::new);

	public OpenItemMergingScreenPacket(PacketByteBuf buf) {
		this(
				buf.readInt(),
				buf.readString(),
				buf.readList(Identifier.PACKET_CODEC)
		);
	}
	private void write(RegistryByteBuf registryByteBuf) {
		registryByteBuf.writeInt(this.maxMergedItemsAmount);
		registryByteBuf.writeString(this.title);
		registryByteBuf.writeCollection(this.list, Identifier.PACKET_CODEC);
	}

	@Override
	public CustomPayload.Id<? extends CustomPayload> getId() {
		return PACKET_ID;
	}
}