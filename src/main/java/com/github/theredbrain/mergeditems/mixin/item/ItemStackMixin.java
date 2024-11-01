package com.github.theredbrain.mergeditems.mixin.item;

import com.github.theredbrain.mergeditems.registry.ItemComponentRegistry;
import com.github.theredbrain.mergeditems.util.MeldingHelper;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(ItemStack.class)
public class ItemStackMixin {

	@Inject(
			method = "applyAttributeModifier",
			at = @At("HEAD"),
			cancellable = true
	)
	public void applyAttributeModifier(AttributeModifierSlot slot, BiConsumer<RegistryEntry<EntityAttribute>, EntityAttributeModifier> attributeModifierConsumer, CallbackInfo ci) {
		if (((ItemStack) (Object) this).contains(ItemComponentRegistry.MELDING_COMPONENT_TYPE)) {
			MeldingHelper.applyMeldedAttributeModifiersForAttributeModifierSlot(((ItemStack) (Object) this), slot, attributeModifierConsumer);
			ci.cancel();
		}
	}

	@Inject(
			method = "applyAttributeModifiers",
			at = @At("HEAD"),
			cancellable = true
	)
	public void applyAttributeModifiers(EquipmentSlot slot, BiConsumer<RegistryEntry<EntityAttribute>, EntityAttributeModifier> attributeModifierConsumer, CallbackInfo ci) {
		if (((ItemStack) (Object) this).contains(ItemComponentRegistry.MELDING_COMPONENT_TYPE)) {
			MeldingHelper.applyMeldedAttributeModifiersForEquipmentSlot(((ItemStack) (Object) this), slot, attributeModifierConsumer);
			ci.cancel();
		}
	}

}
