package com.github.theredbrain.mergeditems.util;

import com.github.theredbrain.mergeditems.MergedItems;
import com.github.theredbrain.mergeditems.component.type.MeldingComponent;
import com.github.theredbrain.mergeditems.registry.ItemComponentRegistry;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsAttributeModifiersComponent;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class MeldingHelper {

	public static Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getMeldedAttributeModifiersForTrinketStack(Trinket trinket, ItemStack itemStack, SlotReference slot, LivingEntity entity) {

		MeldingComponent meldingComponent = itemStack.getOrDefault(ItemComponentRegistry.MELDING_COMPONENT_TYPE, MeldingComponent.DEFAULT);
		TrinketsAttributeModifiersComponent trinketsAttributeModifiersComponent = itemStack.getOrDefault(TrinketsAttributeModifiersComponent.TYPE, TrinketsAttributeModifiersComponent.DEFAULT);

		Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> map = trinket.getModifiers(itemStack, slot, entity, SlotAttributes.getIdentifier(slot)); // empty map
//		if (stack.contains(TrinketsAttributeModifiersComponent.TYPE)) {
//			for (TrinketsAttributeModifiersComponent.Entry entry : stack.getOrDefault(TrinketsAttributeModifiersComponent.TYPE, TrinketsAttributeModifiersComponent.DEFAULT).modifiers()) {
//				map.put(entry.attribute(), entry.modifier());
//			}
//		}
		return map;
//
//		if (stack.contains(TrinketsAttributeModifiersComponent.TYPE)) {
//			for (TrinketsAttributeModifiersComponent. Entry entry : stack.getOrDefault(TrinketsAttributeModifiersComponent.TYPE, TrinketsAttributeModifiersComponent.DEFAULT).modifiers()) {
//				if (entry.slot().isEmpty() || entry.slot().get().equals(slot.inventory().getSlotType().getId())) {
//					map.put(entry.attribute(), entry.modifier());
//				}
//			}
//		}
//		return map;
	}

	public static void applyMeldedAttributeModifiersForAttributeModifierSlot(ItemStack itemStack, AttributeModifierSlot slot, BiConsumer<RegistryEntry<EntityAttribute>, EntityAttributeModifier> attributeModifierConsumer) {

		Map<Pair<Pair<RegistryEntry<EntityAttribute>, Identifier>, Pair<EntityAttributeModifier.Operation, AttributeModifierSlot>>, List<Double>> map = new HashMap<>();

		List<AttributeModifiersComponent.Entry> entryList = itemStack.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT).modifiers();
		if (entryList.isEmpty()) {
			entryList = itemStack.getItem().getAttributeModifiers().modifiers();
		}

		for (AttributeModifiersComponent.Entry entry : entryList) {
			if (entry.slot().equals(slot)) {
				Pair<Pair<RegistryEntry<EntityAttribute>, Identifier>, Pair<EntityAttributeModifier.Operation, AttributeModifierSlot>> key = new Pair<>(new Pair<>(entry.attribute(), entry.modifier().id()), new Pair<>(entry.modifier().operation(), entry.slot()));
				List<Double> oldValueList = map.get(key);
				List<Double> newValueList = new ArrayList<>();
				if (oldValueList != null) {
					newValueList.addAll(oldValueList);
				}
				newValueList.add(entry.modifier().value());
				map.put(key, newValueList);
			}
		}

		for (ItemStack itemStack1 : itemStack.getOrDefault(ItemComponentRegistry.MELDING_COMPONENT_TYPE, MeldingComponent.DEFAULT).melded_items()) {
			for (AttributeModifiersComponent.Entry entry : itemStack1.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT).modifiers()) {
				if (entry.slot().equals(slot)) {
					Pair<Pair<RegistryEntry<EntityAttribute>, Identifier>, Pair<EntityAttributeModifier.Operation, AttributeModifierSlot>> key = new Pair<>(new Pair<>(entry.attribute(), entry.modifier().id()), new Pair<>(entry.modifier().operation(), entry.slot()));
					List<Double> oldValueList = map.get(key);
					List<Double> newValueList = new ArrayList<>();
					if (oldValueList != null) {
						newValueList.addAll(oldValueList);
					}
					newValueList.add(entry.modifier().value());
					map.put(key, newValueList);
				}
			}
		}

		for (AttributeModifiersComponent.Entry entry : getFinalList(map)) {
			if (entry.slot().equals(slot)) {
				attributeModifierConsumer.accept(entry.attribute(), entry.modifier());
			}
		}
	}

	public static void applyMeldedAttributeModifiersForEquipmentSlot(ItemStack itemStack, EquipmentSlot slot, BiConsumer<RegistryEntry<EntityAttribute>, EntityAttributeModifier> attributeModifierConsumer) {

		Map<Pair<Pair<RegistryEntry<EntityAttribute>, Identifier>, Pair<EntityAttributeModifier.Operation, AttributeModifierSlot>>, List<Double>> map = new HashMap<>();

		List<AttributeModifiersComponent.Entry> entryList = itemStack.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT).modifiers();
		if (entryList.isEmpty()) {
			entryList = itemStack.getItem().getAttributeModifiers().modifiers();
		}

		for (AttributeModifiersComponent.Entry entry : entryList) {
			if (entry.slot().matches(slot)) {
				Pair<Pair<RegistryEntry<EntityAttribute>, Identifier>, Pair<EntityAttributeModifier.Operation, AttributeModifierSlot>> key = new Pair<>(new Pair<>(entry.attribute(), entry.modifier().id()), new Pair<>(entry.modifier().operation(), entry.slot()));
				List<Double> oldValueList = map.get(key);
				List<Double> newValueList = new ArrayList<>();
				if (oldValueList != null) {
					newValueList.addAll(oldValueList);
				}
				newValueList.add(entry.modifier().value());
				map.put(key, newValueList);
			}
		}

		for (ItemStack itemStack1 : itemStack.getOrDefault(ItemComponentRegistry.MELDING_COMPONENT_TYPE, MeldingComponent.DEFAULT).melded_items()) {
			for (AttributeModifiersComponent.Entry entry : itemStack1.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT).modifiers()) {
				if (entry.slot().matches(slot)) {
					Pair<Pair<RegistryEntry<EntityAttribute>, Identifier>, Pair<EntityAttributeModifier.Operation, AttributeModifierSlot>> key = new Pair<>(new Pair<>(entry.attribute(), entry.modifier().id()), new Pair<>(entry.modifier().operation(), entry.slot()));
					List<Double> oldValueList = map.get(key);
					List<Double> newValueList = new ArrayList<>();
					if (oldValueList != null) {
						newValueList.addAll(oldValueList);
					}
					newValueList.add(entry.modifier().value());
					map.put(key, newValueList);
				}
			}
		}

		for (AttributeModifiersComponent.Entry entry : getFinalList(map)) {
			if (entry.slot().matches(slot)) {
				attributeModifierConsumer.accept(entry.attribute(), entry.modifier());
			}
		}
	}

	private static List<AttributeModifiersComponent.Entry> getFinalList(Map<Pair<Pair<RegistryEntry<EntityAttribute>, Identifier>, Pair<EntityAttributeModifier.Operation, AttributeModifierSlot>>, List<Double>> map) {
		List<AttributeModifiersComponent.Entry> finalList = new ArrayList<>();
		for (Map.Entry<Pair<Pair<RegistryEntry<EntityAttribute>, Identifier>, Pair<EntityAttributeModifier.Operation, AttributeModifierSlot>>, List<Double>> entry : map.entrySet()) {
			List<Double> valueList = entry.getValue();
			double value = 0;
			if (!valueList.isEmpty()) {
				for (Double valueListEntry : valueList) {
					value += valueListEntry;
				}
				double finalValue = MergedItems.generalServerConfig.melding_averages_similar_modifiers ? (value / valueList.size()) : value;
				finalList.add(new AttributeModifiersComponent.Entry(entry.getKey().getLeft().getLeft(), new EntityAttributeModifier(entry.getKey().getLeft().getRight(), finalValue, entry.getKey().getRight().getLeft()), entry.getKey().getRight().getRight()));
			}
		}
		return finalList;
	}
}
