package com.github.theredbrain.mergeditems.block;

import com.github.theredbrain.mergeditems.MergedItems;
import com.github.theredbrain.mergeditems.screen.MeldingScreenHandler;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
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

public class MeldingBlock extends Block {

	public MeldingBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected MapCodec<MeldingBlock> getCodec() {
		return null;
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (world.isClient) {
			return ActionResult.SUCCESS;
		}
		player.openHandledScreen(createCraftingTabProviderBlockScreenHandlerFactory(state, world, pos));
//		player.sendMessage(Text.translatable("gui.crafting_bench.no_crafting_root_block_nearby"), true);
		return ActionResult.CONSUME;
//        player.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE); // TODO stats
	}

	public static NamedScreenHandlerFactory createCraftingTabProviderBlockScreenHandlerFactory(BlockState state, World world, BlockPos pos) {

		List<Identifier> list = List.of(MergedItems.identifier("meldable_items_1"));
		return new ExtendedScreenHandlerFactory<>() {
			@Override
			public MeldingScreenHandler.MeldingData getScreenOpeningData(ServerPlayerEntity player) {
				return new MeldingScreenHandler.MeldingData(1, list);
			}

			@Override
			public Text getDisplayName() {
				return Text.translatable("gui.melding.title");
			}

			@Nullable
			@Override
			public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
				return new MeldingScreenHandler(syncId, playerInventory, 1, list);
			}
		};
	}
}
