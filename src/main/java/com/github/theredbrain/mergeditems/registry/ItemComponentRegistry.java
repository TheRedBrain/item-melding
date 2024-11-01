package com.github.theredbrain.mergeditems.registry;

import com.github.theredbrain.mergeditems.MergedItems;
import com.github.theredbrain.mergeditems.component.type.MeldingComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ItemComponentRegistry {
	public static final ComponentType<MeldingComponent> MELDING_COMPONENT_TYPE = Registry.register(
			Registries.DATA_COMPONENT_TYPE,
			MergedItems.identifier("melding"),
			ComponentType.<MeldingComponent>builder().codec(MeldingComponent.CODEC).packetCodec(MeldingComponent.PACKET_CODEC).build()
	);

	public static void init() {
	}
}
