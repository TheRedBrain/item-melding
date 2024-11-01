package com.github.theredbrain.mergeditems.gui.screen.ingame;

import com.github.theredbrain.mergeditems.MergedItems;
import com.github.theredbrain.mergeditems.screen.MeldingScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

@Environment(EnvType.CLIENT)
public class MeldingScreen extends HandledScreen<MeldingScreenHandler> {
	private static final Text MELD_BUTTON_LABEL_TEXT = Text.translatable("gui.melding.meld_button_label");
	private static final Text SPLIT_BUTTON_LABEL_TEXT = Text.translatable("gui.melding.split_button_label");
	public static final Identifier SLOT_TEXTURE = Identifier.ofVanilla("textures/gui/sprites/container/slot.png");
	public static final Identifier MERGING_BACKGROUND_TEXTURE = MergedItems.identifier("textures/gui/container/merging_background.png");

	private ButtonWidget meldButton;
	private ButtonWidget splitButton;

	private final int hotbarSize;
	private final int inventorySize;
	private final PlayerEntity playerEntity;
	private final List<Identifier> meldableItemTags;

	public MeldingScreen(MeldingScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		this.playerEntity = inventory.player;
		this.hotbarSize = Minecrawl.getActiveHotbarSize(inventory.player);
		this.inventorySize = Minecrawl.getActiveInventorySize(inventory.player);
		this.meldableItemTags = handler.getMeldableItemTags();
	}

	@Override
	protected void init() {

		super.init();

		this.meldButton = this.addDrawableChild(ButtonWidget.builder(MELD_BUTTON_LABEL_TEXT, button -> this.meld()).dimensions(this.x + 15, this.y + 48, 64, 20).build());

		this.splitButton = this.addDrawableChild(ButtonWidget.builder(SPLIT_BUTTON_LABEL_TEXT, button -> this.split()).dimensions(this.x + 97, this.y + 48, 64, 20).build());

	}

	private void meld() {
		ClientPlayNetworking.send(new MeldItemStacksPacket());
	}

	private void split() {
		ClientPlayNetworking.send(new SplitMeldedItemStacksPacket());
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);
		this.drawMouseoverTooltip(context, mouseX, mouseY);
	}

	@Override
	public void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
		int x = this.x;
		int y = this.y;
		int k;
		int m;
		boolean showInactiveSlots = true;// TODO RPGCraftingClient.clientConfigHolder.getConfig().generalClientConfig.show_inactive_slots;

		context.drawTexture(MERGING_BACKGROUND_TEXTURE, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight, this.backgroundWidth, this.backgroundHeight);

		for (k = 0; k < (showInactiveSlots ? 27 : Math.min(this.inventorySize, 27)); ++k) {
			m = (k / 9);
			context.drawTexture(SLOT_TEXTURE, x + 7 + (k - (m * 9)) * 18, y + 83 + (m * 18), 0, 0, 18, 18, 18, 18);
		}
		for (k = 0; k < (showInactiveSlots ? 9 : Math.min(this.hotbarSize, 9)); ++k) {
			context.drawTexture(SLOT_TEXTURE, x + 7 + k * 18, y + 141, 0, 0, 18, 18, 18, 18);
		}

//		int index = 0;
//		List<RecipeEntry<RPGCraftingRecipe>> recipeList = this.recipeList;
//		int recipeCounter = recipeList.size();
//		for (int i = this.scrollPosition; i < Math.min(this.scrollPosition + (RECIPE_FIELD_HEIGTH * RECIPE_FIELD_WIDTH), recipeCounter); i++) {
//			if (i > recipeList.size()) {
//				break;
//			}
//			RPGCraftingRecipe craftingRecipe = recipeList.get(i).value();
//			if (craftingRecipe != null && this.client != null && this.client.world != null) {
//
//				x = this.x + 62 + (index % RECIPE_FIELD_WIDTH * 18);
//				y = this.y + 63 + (index / RECIPE_FIELD_WIDTH) * 18;
//
//				ItemStack resultItemStack = craftingRecipe.getResult(this.client.world.getRegistryManager());
//				Identifier identifier;
//				if (i == this.handler.getSelectedRecipe()) {
//					identifier = RECIPE_SELECTED_TEXTURE;
//				} else if (mouseX >= x && mouseY >= y && mouseX < x + 18 && mouseY < y + 18) {
//					identifier = RECIPE_HIGHLIGHTED_TEXTURE;
//				} else {
//					identifier = RECIPE_TEXTURE;
//				}
//				context.drawGuiTexture(identifier, x, y, 18, 18);
//				context.drawItemWithoutEntity(resultItemStack, x + 1, y + 1);
//				context.drawItemInSlot(this.textRenderer, resultItemStack, x + 1, y + 1);
//
//				index++;
//			}
//		}
//		x = this.x;
//		y = this.y;
//		k = (int) (65.0F * this.scrollAmount);
//		Identifier identifier = this.shouldScroll() ? SCROLLER_VERTICAL_6_7_TEXTURE : SCROLLER_VERTICAL_6_7_DISABLED_TEXTURE;
//		context.drawGuiTexture(identifier, x + 119, y + 63 + k, 6, 7);
//
//		int selectedRecipe = this.handler.getSelectedRecipe();
//		if (selectedRecipe != -1 && this.client != null && this.client.world != null && selectedRecipe < recipeList.size()) {
//
//			ItemStack resultItemStack = this.handler.getCraftingResultInventory().getStack(0);
//			Text resultName;
//			int count = resultItemStack.getCount();
//			if (count > 1) {
//				resultName = Text.translatable("gui.rpg_crafting.recipe_result.results_title", resultItemStack.getName(), resultItemStack.getCount());
//			} else {
//				resultName = resultItemStack.getName();
//			}
//			context.drawText(this.textRenderer, resultName, x + 155, y + 26, 16777215, false);
//
//			if (this.craftingResultDescription != Text.EMPTY) {
//				context.drawTextWrapped(this.textRenderer, this.craftingResultDescription, x + 139, y + 42, 132, 16777215);
//			}
//
//			context.drawText(this.textRenderer, Text.translatable("gui.rpg_crafting.recipe_result.ingredients_title").formatted(Formatting.UNDERLINE), x + 135, y + 80, 16777215, false);
//
//		}
	}

}
