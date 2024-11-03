package com.github.theredbrain.mergeditems.util;

import com.github.theredbrain.mergeditems.MergedItems;
import com.github.theredbrain.mergeditems.component.type.MergedItemsComponent;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class MergedAttributeHelper {

	public static Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getMergedAttributeModifiersForTrinketStack(Trinket trinket, ItemStack itemStack, SlotReference slot, LivingEntity entity) {

		Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> map = trinket.getModifiers(itemStack, slot, entity, SlotAttributes.getIdentifier(slot)); // empty map
		List<TrinketEntry> mergedList = new ArrayList<>();

		List<TrinketsAttributeModifiersComponent.Entry> entryList = itemStack.getOrDefault(TrinketsAttributeModifiersComponent.TYPE, TrinketsAttributeModifiersComponent.DEFAULT).modifiers();

		for (TrinketsAttributeModifiersComponent.Entry entry : entryList) {
			if (entry.slot().isEmpty() || entry.slot().get().equals(slot.inventory().getSlotType().getId())) {
				TrinketEntry newEntry = new TrinketEntry(entry.attribute(), entry.modifier().id(), entry.modifier().operation(), entry.slot(), List.of(entry.modifier().value()));
				for (TrinketEntry entry1 : mergedList) {
					if (entry1.matches(newEntry)) {
						newEntry = entry1.addNewValue(newEntry);
					}
				}
				mergedList.add(newEntry);
			}
		}

		MergedItemsComponent mergedItemsComponent = itemStack.get(MergedItems.MERGED_ITEMS_COMPONENT_TYPE);
		if (mergedItemsComponent != null) {
			for (ItemStack itemStack1 : mergedItemsComponent.iterate()) {
				for (TrinketsAttributeModifiersComponent.Entry entry : itemStack1.getOrDefault(TrinketsAttributeModifiersComponent.TYPE, TrinketsAttributeModifiersComponent.DEFAULT).modifiers()) {
					if (entry.slot().isEmpty() || entry.slot().get().equals(slot.inventory().getSlotType().getId())) {
						TrinketEntry newEntry = new TrinketEntry(entry.attribute(), entry.modifier().id(), entry.modifier().operation(), entry.slot(), List.of(entry.modifier().value()));
						boolean bl = true;
						for (int i = 0; i < mergedList.size(); i++) {
							if (mergedList.get(i).matches(newEntry)) {
								newEntry = mergedList.get(i).addNewValue(newEntry);
								mergedList.add(i + 1, newEntry);
								mergedList.remove(i);
								bl = false;
							}
						}
						if (bl) {
							mergedList.add(newEntry);
						}
					}
				}
			}
		}

		for (TrinketsAttributeModifiersComponent.Entry entry : getFinalTrinketList(mergedList)) {
			if (entry.slot().isEmpty() || entry.slot().get().equals(slot.inventory().getSlotType().getId())) {
				map.put(entry.attribute(), entry.modifier());
			}
		}
		return map;
	}

	public static void applyMergedAttributeModifiersForAttributeModifierSlot(ItemStack itemStack, AttributeModifierSlot slot, BiConsumer<RegistryEntry<EntityAttribute>, EntityAttributeModifier> attributeModifierConsumer) {

		List<Entry> mergedList = new ArrayList<>();

		List<AttributeModifiersComponent.Entry> entryList = itemStack.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT).modifiers();
		if (entryList.isEmpty()) {
			entryList = itemStack.getItem().getAttributeModifiers().modifiers();
		}

		for (AttributeModifiersComponent.Entry entry : entryList) {
			if (entry.slot().equals(slot)) {
				Entry newEntry = new Entry(entry.attribute(), entry.modifier().id(), entry.modifier().operation(), entry.slot(), List.of(entry.modifier().value()));
				for (Entry entry1 : mergedList) {
					if (entry1.matches(newEntry)) {
						newEntry = entry1.addNewValue(newEntry);
					}
				}
				mergedList.add(newEntry);
			}
		}

		MergedItemsComponent mergedItemsComponent = itemStack.get(MergedItems.MERGED_ITEMS_COMPONENT_TYPE);
		if (mergedItemsComponent != null) {
			for (ItemStack itemStack1 : mergedItemsComponent.iterate()) {
				for (AttributeModifiersComponent.Entry entry : itemStack1.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT).modifiers()) {
					if (entry.slot().equals(slot)) {
						Entry newEntry = new Entry(entry.attribute(), entry.modifier().id(), entry.modifier().operation(), entry.slot(), List.of(entry.modifier().value()));
						boolean bl = true;
						for (int i = 0; i < mergedList.size(); i++) {
							if (mergedList.get(i).matches(newEntry)) {
								newEntry = mergedList.get(i).addNewValue(newEntry);
								mergedList.add(i + 1, newEntry);
								mergedList.remove(i);
								bl = false;
							}
						}
						if (bl) {
							mergedList.add(newEntry);
						}
					}
				}
			}
		}

		for (AttributeModifiersComponent.Entry entry : getFinalList(mergedList)) {
			if (entry.slot().equals(slot)) {
				attributeModifierConsumer.accept(entry.attribute(), entry.modifier());
			}
		}
	}

	public static void applyMergedAttributeModifiersForEquipmentSlot(ItemStack itemStack, EquipmentSlot slot, BiConsumer<RegistryEntry<EntityAttribute>, EntityAttributeModifier> attributeModifierConsumer) {

		List<Entry> mergedList = new ArrayList<>();

		List<AttributeModifiersComponent.Entry> entryList = itemStack.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT).modifiers();
		if (entryList.isEmpty()) {
			entryList = itemStack.getItem().getAttributeModifiers().modifiers();
		}

		for (AttributeModifiersComponent.Entry entry : entryList) {
			if (entry.slot().matches(slot)) {
				Entry newEntry = new Entry(entry.attribute(), entry.modifier().id(), entry.modifier().operation(), entry.slot(), List.of(entry.modifier().value()));
				for (Entry entry1 : mergedList) {
					if (entry1.matches(newEntry)) {
						newEntry = entry1.addNewValue(newEntry);
					}
				}
				mergedList.add(newEntry);
			}
		}

		MergedItemsComponent mergedItemsComponent = itemStack.get(MergedItems.MERGED_ITEMS_COMPONENT_TYPE);
		if (mergedItemsComponent != null) {
			for (ItemStack itemStack1 : mergedItemsComponent.iterate()) {
				for (AttributeModifiersComponent.Entry entry : itemStack1.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT).modifiers()) {
					if (entry.slot().matches(slot)) {
						Entry newEntry = new Entry(entry.attribute(), entry.modifier().id(), entry.modifier().operation(), entry.slot(), List.of(entry.modifier().value()));
						for (Entry entry1 : mergedList) {
							if (entry1.matches(newEntry)) {
								newEntry = entry1.addNewValue(newEntry);
							}
						}
						mergedList.add(newEntry);
					}
				}
			}
		}

		for (AttributeModifiersComponent.Entry entry : getFinalList(mergedList)) {
			if (entry.slot().matches(slot)) {
				attributeModifierConsumer.accept(entry.attribute(), entry.modifier());
			}
		}
	}

	private static List<AttributeModifiersComponent.Entry> getFinalList(List<Entry> list) {
		List<AttributeModifiersComponent.Entry> finalList = new ArrayList<>();
		for (Entry entry : list) {
			List<Double> valueList = entry.values;
			double finalValue = 0.0;
			if (!valueList.isEmpty()) {
				double value = 0;
				for (Double valueListEntry : valueList) {
					value += valueListEntry;
				}
				finalValue = MergedItems.generalServerConfig.merging_averages_similar_modifiers ? (value / valueList.size()) : value;
			}
			finalList.add(new AttributeModifiersComponent.Entry(entry.attribute, new EntityAttributeModifier(entry.id, finalValue, entry.operation), entry.slot));
		}
		return finalList;
	}

	private static List<TrinketsAttributeModifiersComponent.Entry> getFinalTrinketList(List<TrinketEntry> list) {
		List<TrinketsAttributeModifiersComponent.Entry> finalList = new ArrayList<>();
		for (TrinketEntry entry : list) {
			List<Double> valueList = entry.values;
			double finalValue = 0.0;
			if (!valueList.isEmpty()) {
				double value = 0;
				for (Double valueListEntry : valueList) {
					value += valueListEntry;
				}
				finalValue = MergedItems.generalServerConfig.merging_averages_similar_modifiers ? (value / valueList.size()) : value;
			}
			finalList.add(new TrinketsAttributeModifiersComponent.Entry(entry.attribute, new EntityAttributeModifier(entry.id, finalValue, entry.operation), entry.slot));
		}
		return finalList;
	}

	public record Entry(
			RegistryEntry<EntityAttribute> attribute,
			Identifier id,
			EntityAttributeModifier.Operation operation,
			AttributeModifierSlot slot,
			List<Double> values
	) {
		public Entry addNewValue(Entry entry) {
			List<Double> newList = new ArrayList<>();
			newList.addAll(this.values);
			newList.addAll(entry.values);
			return new Entry(this.attribute, this.id, this.operation, this.slot, newList);
		}

		public boolean matches(Entry entry) {
			return entry.attribute.equals(this.attribute) && entry.id.equals(this.id) && entry.operation.equals(this.operation) && entry.slot.equals(this.slot);
		}
	}

	public record TrinketEntry(
			RegistryEntry<EntityAttribute> attribute,
			Identifier id,
			EntityAttributeModifier.Operation operation,
			Optional<String> slot,
			List<Double> values
	) {
		public TrinketEntry addNewValue(TrinketEntry entry) {
			List<Double> newList = new ArrayList<>();
			newList.addAll(this.values);
			newList.addAll(entry.values);
			return new TrinketEntry(this.attribute, this.id, this.operation, this.slot, newList);
		}

		public boolean matches(TrinketEntry entry) {
			return entry.attribute.equals(this.attribute) && entry.id.equals(this.id) && entry.operation.equals(this.operation) && entry.slot.equals(this.slot);
		}
	}
}
