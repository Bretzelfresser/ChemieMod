package com.bretzelfresser.chemie.common.recipes;

import com.bretzelfresser.chemie.Chemie;
import com.bretzelfresser.chemie.core.init.ItemInit;
import com.bretzelfresser.chemie.core.init.RecipeInit;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class SifterRecipe implements IRecipe<IInventory> {

	public static final Serializer SERIALIZER = new Serializer();

	private final ItemStack output;
	private final Ingredient input;
	private final int siftingTime;
	private final ResourceLocation id;

	public SifterRecipe(Ingredient input, ItemStack output,int siftingTime, ResourceLocation id) {
		this.input = input;
		this.output = output;
		this.id = id;
		this.siftingTime = siftingTime;
	}

	@Override
	public boolean matches(IInventory inv, World worldIn) {
		return input.test(inv.getStackInSlot(0));
	}

	public Ingredient getInput() {
		return this.input;
	}
	
	public boolean isValid(ItemStack input1) {
		return input.test(input1);
	}

	@Override
	public ItemStack getCraftingResult(IInventory inv) {
		return this.output.copy();
	}

	@Override
	public boolean canFit(int width, int height) {
		return false;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.output;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return SifterRecipe.SERIALIZER;
	}

	@Override
	public IRecipeType<?> getType() {
		return RecipeInit.SIFTER_RECIPE;
	}
	public int getSiftTime() {
		return this.siftingTime;
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(ItemInit.POLONIUM.get());
	}

	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
			implements IRecipeSerializer<SifterRecipe> {

		Serializer() {
			this.setRegistryName(Chemie.MODID, "sifter_recipe");
		}

		@Override
		public SifterRecipe read(ResourceLocation recipeId, JsonObject json) {
			final JsonElement inputElement = JSONUtils.isJsonArray(json, "input") ? JSONUtils.getJsonArray(json, "input") : JSONUtils.getJsonObject(json, "input");
			final Ingredient input = Ingredient.deserialize(inputElement);
			final ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));
			final int sifintTime = JSONUtils.getInt(json, "siftingTime", 200);
			return new SifterRecipe(input, output,sifintTime, recipeId);
		}

		@Override
		public SifterRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
			final Ingredient input = Ingredient.read(buffer);
			final ItemStack output = buffer.readItemStack();
			final int sifitngTime = buffer.readInt();
			return new SifterRecipe(input, output,sifitngTime, recipeId);
		}

		@Override
		public void write(PacketBuffer buffer, SifterRecipe recipe) {
			recipe.input.write(buffer);
			buffer.writeItemStack(recipe.output);
			buffer.writeVarInt(recipe.siftingTime);
		}

	}

}
