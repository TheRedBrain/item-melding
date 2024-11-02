package com.github.theredbrain.mergeditems;

import com.github.theredbrain.inventorysizeattributes.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.mergeditems.component.type.ItemMergingUtilityComponent;
import com.github.theredbrain.mergeditems.component.type.MergedItemsComponent;
import com.github.theredbrain.mergeditems.config.ServerConfig;
import com.github.theredbrain.mergeditems.registry.ItemComponentRegistry;
import com.github.theredbrain.mergeditems.registry.ScreenHandlerTypesRegistry;
import com.github.theredbrain.mergeditems.registry.ServerPacketRegistry;
import com.github.theredbrain.mergeditems.screen.ItemMergingScreenHandler;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MergedItems implements ModInitializer {
	public static final String MOD_ID = "mergeditems";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ServerConfig.GeneralServerConfig generalServerConfig;

	public static ComponentType<MergedItemsComponent> MERGED_ITEMS_COMPONENT_TYPE;
	public static ComponentType<ItemMergingUtilityComponent> ITEM_MERGING_UTILITY_COMPONENT_TYPE;

	public static final boolean isInventorySizeAttributesLoaded = FabricLoader.getInstance().isModLoaded("inventorysizeattributes");

	public static int getActiveInventorySize(PlayerEntity player) {
		return isInventorySizeAttributesLoaded ? ((DuckPlayerEntityMixin) player).inventorysizeattributes$getActiveInventorySlotAmount() : 27;
	}

	public static int getActiveHotbarSize(PlayerEntity player) {
		return isInventorySizeAttributesLoaded ? ((DuckPlayerEntityMixin) player).inventorysizeattributes$getActiveHotbarSlotAmount() : 9;
	}

	public static void openItemMergingScreen(PlayerEntity playerEntity, int maxMergedItemsAmount, String title, List<Identifier> list) {
		playerEntity.openHandledScreen(new ExtendedScreenHandlerFactory<>() {
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
				return new ItemMergingScreenHandler(syncId, playerInventory, 1, list);
			}
		});
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Merging your items since 2024!");

		// Config
		AutoConfig.register(ServerConfig.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
		generalServerConfig = ((ServerConfig) AutoConfig.getConfigHolder(ServerConfig.class).getConfig()).server;

		ItemComponentRegistry.init();
		ScreenHandlerTypesRegistry.registerAll();
		ServerPacketRegistry.init();
	}

	public static Identifier identifier(String path) {
		return Identifier.of(MOD_ID, path);
	}
}