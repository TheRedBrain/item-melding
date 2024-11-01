package com.github.theredbrain.mergeditems.mixin.trinkets;

import com.github.theredbrain.mergeditems.registry.ItemComponentRegistry;
import com.github.theredbrain.mergeditems.util.MeldingHelper;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.TrinketModifiers;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TrinketModifiers.class)
public class TrinketModifiersMixin {

	@Inject(
			method = "get(Lnet/minecraft/item/ItemStack;Ldev/emi/trinkets/api/SlotReference;Lnet/minecraft/entity/LivingEntity;)Lcom/google/common/collect/Multimap;",
			at = @At("HEAD"),
			cancellable = true
	)
	private static void get(ItemStack stack, SlotReference slot, LivingEntity entity, CallbackInfoReturnable<Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier>> cir){
		if (stack.contains(ItemComponentRegistry.MELDING_COMPONENT_TYPE)) {
			cir.setReturnValue(MeldingHelper.getMeldedAttributeModifiersForTrinketStack(TrinketsApi.getTrinket(stack.getItem()), stack, slot, entity));
			cir.cancel();
		}
	}

	@Inject(
			method = "get(Ldev/emi/trinkets/api/Trinket;Lnet/minecraft/item/ItemStack;Ldev/emi/trinkets/api/SlotReference;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/Identifier;)Lcom/google/common/collect/Multimap;",
			at = @At("HEAD"),
			cancellable = true
	)
	private static void get(Trinket trinket, ItemStack stack, SlotReference slot, LivingEntity entity, Identifier slotIdentifier, CallbackInfoReturnable<Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier>> cir){
		if (stack.contains(ItemComponentRegistry.MELDING_COMPONENT_TYPE)) {
			cir.setReturnValue(MeldingHelper.getMeldedAttributeModifiersForTrinketStack(trinket, stack, slot, entity));
			cir.cancel();
		}
	}
}
