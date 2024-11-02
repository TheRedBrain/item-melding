package com.github.theredbrain.mergeditems.registry;

import com.github.theredbrain.mergeditems.network.packet.MergeItemStacksPacket;
import com.github.theredbrain.mergeditems.network.packet.MergeItemStacksPacketReceiver;
import com.github.theredbrain.mergeditems.network.packet.SplitMergedItemStacksPacket;
import com.github.theredbrain.mergeditems.network.packet.SplitMergedItemStacksPacketReceiver;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ServerPacketRegistry {
	public static void init() {

		PayloadTypeRegistry.playC2S().register(MergeItemStacksPacket.PACKET_ID, MergeItemStacksPacket.PACKET_CODEC);
		ServerPlayNetworking.registerGlobalReceiver(MergeItemStacksPacket.PACKET_ID, new MergeItemStacksPacketReceiver());

		PayloadTypeRegistry.playC2S().register(SplitMergedItemStacksPacket.PACKET_ID, SplitMergedItemStacksPacket.PACKET_CODEC);
		ServerPlayNetworking.registerGlobalReceiver(SplitMergedItemStacksPacket.PACKET_ID, new SplitMergedItemStacksPacketReceiver());

	}
}
