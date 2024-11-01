package com.github.theredbrain.mergeditems.registry;

import com.github.theredbrain.mergeditems.network.packet.MeldItemStacksPacket;
import com.github.theredbrain.mergeditems.network.packet.MeldItemStacksPacketReceiver;
import com.github.theredbrain.mergeditems.network.packet.SplitMeldedItemStacksPacket;
import com.github.theredbrain.mergeditems.network.packet.SplitMeldedItemStacksPacketReceiver;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ServerPacketRegistry {
	public static void init() {

		PayloadTypeRegistry.playC2S().register(MeldItemStacksPacket.PACKET_ID, MeldItemStacksPacket.PACKET_CODEC);
		ServerPlayNetworking.registerGlobalReceiver(MeldItemStacksPacket.PACKET_ID, new MeldItemStacksPacketReceiver());

		PayloadTypeRegistry.playC2S().register(SplitMeldedItemStacksPacket.PACKET_ID, SplitMeldedItemStacksPacket.PACKET_CODEC);
		ServerPlayNetworking.registerGlobalReceiver(SplitMeldedItemStacksPacket.PACKET_ID, new SplitMeldedItemStacksPacketReceiver());

	}
}
