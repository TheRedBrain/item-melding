package com.github.theredbrain.mergeditems.block;

import com.github.theredbrain.mergeditems.screen.ItemMergingScreenHandler;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemMergingBlock extends Block {
	public static final MapCodec<ItemMergingBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
			Codec.INT.fieldOf("maxMergedItemsAmount").forGetter(x -> x.maxMergedItemsAmount),
			Codec.STRING.fieldOf("title").forGetter(x -> x.title),
			Identifier.CODEC.listOf().fieldOf("list").forGetter(x -> x.list),
			createSettingsCodec()
	).apply(instance, ItemMergingBlock::new));
	private final int maxMergedItemsAmount;
	private final String title;
	private final List<Identifier> list;

	public ItemMergingBlock(int maxMergedItemsAmount, String title, List<Identifier> list, Settings settings) {
		super(settings);
		this.maxMergedItemsAmount = maxMergedItemsAmount;
		this.title = title;
		this.list = list;
	}

	@Override
	protected MapCodec<ItemMergingBlock> getCodec() {
		return CODEC;
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (world.isClient) {
			return ActionResult.SUCCESS;
		}
		player.openHandledScreen(new ExtendedScreenHandlerFactory<>() {
			@Override
			public ItemMergingScreenHandler.ItemMergingData getScreenOpeningData(ServerPlayerEntity player) {
				return new ItemMergingScreenHandler.ItemMergingData(maxMergedItemsAmount, list);
			}

			@Override
			public Text getDisplayName() {
				return Text.translatable(title);
			}

			@Nullable
			@Override
			public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
				return new ItemMergingScreenHandler(syncId, playerInventory, maxMergedItemsAmount, list);
			}
		});
		return ActionResult.CONSUME;
	}
}
