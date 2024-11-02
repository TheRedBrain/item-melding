package com.github.theredbrain.mergeditems.component.type;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class MergedItemsComponent implements TooltipData {
	public static final MergedItemsComponent DEFAULT = new MergedItemsComponent(List.of());
	public static final Codec<MergedItemsComponent> CODEC;
	public static final PacketCodec<RegistryByteBuf, MergedItemsComponent> PACKET_CODEC;
	final List<ItemStack> stacks;

	public MergedItemsComponent(List<ItemStack> stacks) {
		this.stacks = stacks;
	}

	public ItemStack get(int index) {
		return (ItemStack)this.stacks.get(index);
	}

	public Stream<ItemStack> stream() {
		return this.stacks.stream().map(ItemStack::copy);
	}

	public Iterable<ItemStack> iterate() {
		return this.stacks;
	}

	public Iterable<ItemStack> iterateCopy() {
		return Lists.transform(this.stacks, ItemStack::copy);
	}

	public int size() {
		return this.stacks.size();
	}

	public boolean isEmpty() {
		return this.stacks.isEmpty();
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (!(o instanceof MergedItemsComponent)) {
			return false;
		} else {
			MergedItemsComponent mergedItemsComponent = (MergedItemsComponent)o;
			return ItemStack.stacksEqual(this.stacks, mergedItemsComponent.stacks);
		}
	}

	public int hashCode() {
		return ItemStack.listHashCode(this.stacks);
	}

	public String toString() {
		return "Merged items" + String.valueOf(this.stacks);
	}

	static {
		CODEC = ItemStack.CODEC.listOf().xmap(MergedItemsComponent::new, (component) -> {
			return component.stacks;
		});
		PACKET_CODEC = ItemStack.PACKET_CODEC.collect(PacketCodecs.toList()).xmap(MergedItemsComponent::new, (component) -> {
			return component.stacks;
		});
	}

	public static class Builder {
		private final List<ItemStack> stacks;

		public Builder(MergedItemsComponent base) {
			this.stacks = new ArrayList<>(base.stacks);
		}

		public MergedItemsComponent.Builder clear() {
			this.stacks.clear();
			return this;
		}

		private int addInternal(ItemStack stack) {
			if (!stack.isStackable()) {
				for (ItemStack itemStack : this.stacks) {
					if (ItemStack.areItemsAndComponentsEqual(itemStack, stack)) {
						return -1;
					}
				}
				return this.stacks.size();
			}
			return -1;
		}

		public void add(ItemStack stack) {
			if (!stack.isEmpty() && !stack.isStackable()) {
					int j = this.addInternal(stack);
					if (j != -1) {
						this.stacks.add(j, stack);
					}
			}
		}

		@Nullable
		public ItemStack removeLast() {
			if (this.stacks.isEmpty()) {
				return null;
			} else {
				return ((ItemStack) this.stacks.removeLast()).copy();
			}
		}

		public MergedItemsComponent build() {
			return new MergedItemsComponent(List.copyOf(this.stacks));
		}
	}
}
