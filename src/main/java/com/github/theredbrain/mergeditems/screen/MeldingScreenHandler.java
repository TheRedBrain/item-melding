package com.github.theredbrain.mergeditems.screen;

import com.github.theredbrain.mergeditems.registry.ScreenHandlerTypesRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public class MeldingScreenHandler extends ScreenHandler {

	private final PlayerInventory playerInventory;
	private final World world;
	private final int maxMeldingAmount;
	private final List<Identifier> meldableItemTags;
	Runnable contentsChangedListener = () -> {
	};
	public final Inventory inventory = new SimpleInventory(4) {
		@Override
		public int getMaxCountPerStack() {
			return 999;
		}

		@Override
		public void markDirty() {
			super.markDirty();
			MeldingScreenHandler.this.onContentChanged(this);
			MeldingScreenHandler.this.contentsChangedListener.run();
		}
	};

	public MeldingScreenHandler(int syncId, PlayerInventory playerInventory, MeldingData data) {
		this(syncId, playerInventory, data.maxMeldingAmount, data.meldableItemTags);
	}

	public MeldingScreenHandler(int syncId, PlayerInventory playerInventory, int maxMeldingAmount, List<Identifier> meldableItemTags) {
		super(ScreenHandlerTypesRegistry.MELDING_SCREEN_HANDLER, syncId);
		this.playerInventory = playerInventory;
		this.world = playerInventory.player.getWorld();
		this.maxMeldingAmount = maxMeldingAmount;
		this.meldableItemTags = meldableItemTags;
		int i;
		// hotbar 0 - 8
		for (i = 0; i < 9; ++i) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
		// main inventory 9 - 35
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
			}
		}
		// 36
		this.addSlot(new Slot(inventory, 0, 16, 27));
		// 37
		this.addSlot(new Slot(inventory, 1, 62, 27));
		// 38
		this.addSlot(new Slot(inventory, 2, 98, 27));
		// 39
		this.addSlot(new Slot(inventory, 3, 144, 27));

	}

	@Override
	public ItemStack quickMove(PlayerEntity player, int slot) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}

	@Override
	public void onClosed(PlayerEntity player) {
		super.onClosed(player);
		if (player instanceof ServerPlayerEntity) {
			this.dropInventory(player, this.inventory);
		}
	}

	public List<Identifier> getMeldableItemTags() {
		return this.meldableItemTags;
	}

	public int getMaxMeldingAmount() {
		return this.maxMeldingAmount;
	}

	public record MeldingData(
			int maxMeldingAmount,
			List<Identifier> meldableItemTags
	) {

		public static final PacketCodec<RegistryByteBuf, MeldingData> PACKET_CODEC = PacketCodec.of(MeldingData::write, MeldingData::new);

		public MeldingData(RegistryByteBuf registryByteBuf) {
			this(
					registryByteBuf.readInt(),
					registryByteBuf.readList(Identifier.PACKET_CODEC)
			);
		}

		private void write(RegistryByteBuf registryByteBuf) {
			registryByteBuf.writeInt(this.maxMeldingAmount);
			registryByteBuf.writeCollection(this.meldableItemTags, Identifier.PACKET_CODEC);
		}
	}
}
