package com.bretzelfresser.chemie.common.recipes;

import java.util.stream.Stream;

import com.bretzelfresser.chemie.Chemie;
import com.bretzelfresser.chemie.common.entities.tileEntities.ReactionChamberTileEntity;
import com.bretzelfresser.chemie.core.init.RecipeInit;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;

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

public class ReactionChamberRecipe implements IRecipe<IInventory> {

	public static final Serializer SERIALIZER = new Serializer();

	private final Ingredient input1, input2, input3, input4;
	private final ItemStack output;
	private final int workingTime, countInput1, countInput2, countInput3, countInput4;
	private final ResourceLocation id;

	public ReactionChamberRecipe(Ingredient input1, int countInput1, Ingredient input2, int countInput2,
			Ingredient input3, int countInput3, Ingredient input4, int countInput4, ItemStack output, int workingTime,
			ResourceLocation id) {
		super();
		this.input1 = input1;
		this.input2 = input2;
		this.input3 = input3;
		this.input4 = input4;
		this.countInput1 = countInput1;
		this.countInput2 = countInput2;
		this.countInput3 = countInput3;
		this.countInput4 = countInput4;
		this.output = output;
		this.workingTime = workingTime;
		this.id = id;
	}

	public ReactionChamberRecipe(Ingredient input1, int countInput1, Ingredient input2, int countInput2,
			Ingredient input3, int countInput3, ItemStack output, int workingTime, ResourceLocation id) {
		this(input1, countInput1, input2, countInput2, input3, countInput3, null, 0, output, workingTime, id);
	}

	public ReactionChamberRecipe(Ingredient input1, int countInput1, Ingredient input2, int countInput2,
			ItemStack output, int workingTime, ResourceLocation id) {
		this(input1, countInput1, input2, countInput2, null, 0, null, 0, output, workingTime, id);
	}

	public ReactionChamberRecipe(Ingredient input1, int countInput1, ItemStack output, int workingTime,
			ResourceLocation id) {
		this(input1, countInput1, null, 0, null, 0, null, 0, output, workingTime, id);
	}

	@Override
	public boolean matches(IInventory inv, World worldIn) {
		if (inv.getSizeInventory() != 8) {
			return false;
		}
		if (inv instanceof ReactionChamberTileEntity)
			return isValid(inv.getStackInSlot(0), inv.getStackInSlot(1), inv.getStackInSlot(2), inv.getStackInSlot(3));
		return false;
	}

	public boolean isValid(ItemStack input1, ItemStack input2, ItemStack input3, ItemStack input4) {
		ItemStack[] list = new ItemStack[] { input1, input2, input3, input4 };
		Pair<Ingredient, Integer>[] inputs = getListOfIngredients();
		int countEmtyIngredients = getCountEmpty(inputs);
		if (getCountEmpty(list) > countEmtyIngredients)
			return false;
		// just when u have 4 inputs to check
		if (getCountEmpty(inputs) == 0) {
			for (Pair<Ingredient, Integer> p : inputs) {
				for (int i = 0; i < list.length; i++) {
					if (test(list[i], p)) {
						list[i] = ItemStack.EMPTY;
					} else
						return false;
				}
			}
			return true;
			// this will be done when u have only 1, 2 or 3 inputs
		} else {
			int count = 0;
			for (ItemStack stack : list) {
				for (int i = 0; i < inputs.length; i++) {
					if (test(stack, inputs[i])) {
						count++;
						inputs[i] = null;
					}
				}
			}
			if (count == 4 - countEmtyIngredients)
				return true;
		}

		return false;

	}

	public int[] shrink(ItemStack input1, ItemStack input2, ItemStack input3, ItemStack input4) {
		ItemStack[] list = new ItemStack[] { input1, input2, input3, input4 };
		int[] returnList = new int[] {0,0,0,0};
		Pair<Ingredient, Integer>[] inputs = getListOfIngredients();
		for (int j = 0;j<list.length;j++) {
			for (int i = 0; i < inputs.length; i++) {
				if (test(list[j], inputs[i])) {
					returnList[j] =  inputs[i].getSecond();
					inputs[i] = null;
				}
			}
		}
		return returnList;
	}

	private boolean test(ItemStack stack, Pair<Ingredient, Integer> ingredient) {
		if (ingredient == null || stack.isEmpty() || ingredient.getSecond() == 0)
			return false;
		return stack.getCount() >= ingredient.getSecond() && ingredient.getFirst().test(stack);
	}

	private int getCountEmpty(Pair<Ingredient, Integer>[] inputs) {
		int count = 0;
		for (Pair<Ingredient, Integer> p : inputs) {
			if (p.getSecond() == 0)
				count++;
		}
		return count;
	}

	private int getCountEmpty(ItemStack[] list) {
		int count = 0;
		for (int i = 0; i < list.length; i++) {
			if (list[i].isEmpty())
				count++;
		}
		return count;
	}

	private Pair<Ingredient, Integer>[] getListOfIngredients() {
		@SuppressWarnings("unchecked")
		Pair<Ingredient, Integer>[] recipe = new Pair[] { Pair.of(this.input1, this.countInput1),
				Pair.of(this.input2, this.countInput2), Pair.of(this.input3, this.countInput3),
				Pair.of(this.input4, this.countInput4) };
		return recipe;

	}

	public int getCountInput1() {
		return countInput1;
	}

	public int getCountInput2() {
		return countInput2;
	}

	public int getCountInput3() {
		return countInput3;
	}

	public int getCountInput4() {
		return countInput4;
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

	public int getWorkingTime() {
		return workingTime;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	@Override
	public IRecipeType<?> getType() {
		return RecipeInit.REACTION_CHAMBER_RECIPE;
	}

	private static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
			implements IRecipeSerializer<ReactionChamberRecipe> {

		public Serializer() {
			this.setRegistryName(Chemie.MODID, "reaction_chamber");
		}

		@Override
		public ReactionChamberRecipe read(ResourceLocation recipeId, JsonObject json) {
			Pair<Integer, Ingredient> pairInput1 = deserializeItems(getJsonElement(json, "input1"));
			int reactionTime = JSONUtils.getInt(json, "reactionTime", 200);
			final ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));
			if (json.has("input4") && json.has("input3") && json.has("input2")) {
				Pair<Integer, Ingredient> pairInput2 = deserializeItems(getJsonElement(json, "input2"));
				Pair<Integer, Ingredient> pairInput3 = deserializeItems(getJsonElement(json, "input3"));
				Pair<Integer, Ingredient> pairInput4 = deserializeItems(getJsonElement(json, "input4"));
				return new ReactionChamberRecipe(pairInput1.getSecond(), pairInput1.getFirst(), pairInput2.getSecond(),
						pairInput2.getFirst(), pairInput3.getSecond(), pairInput3.getFirst(), pairInput4.getSecond(),
						pairInput4.getFirst(), output, reactionTime, recipeId);
			}
			if (json.has("input3") && json.has("input2")) {
				Pair<Integer, Ingredient> pairInput2 = deserializeItems(getJsonElement(json, "input2"));
				Pair<Integer, Ingredient> pairInput3 = deserializeItems(getJsonElement(json, "input3"));
				return new ReactionChamberRecipe(pairInput1.getSecond(), pairInput1.getFirst(), pairInput2.getSecond(),
						pairInput2.getFirst(), pairInput3.getSecond(), pairInput3.getFirst(), output, reactionTime,
						recipeId);
			}
			if (json.has("input2")) {
				Pair<Integer, Ingredient> pairInput2 = deserializeItems(getJsonElement(json, "input2"));
				return new ReactionChamberRecipe(pairInput1.getSecond(), pairInput1.getFirst(), pairInput2.getSecond(),
						pairInput2.getFirst(), output, reactionTime, recipeId);
			}
			return new ReactionChamberRecipe(pairInput1.getSecond(), pairInput1.getFirst(), output, reactionTime,
					recipeId);
		}

		@Override
		public ReactionChamberRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
			Ingredient input1 = Ingredient.read(buffer);
			int countInput1 = buffer.readVarInt();
			Ingredient input2 = Ingredient.read(buffer);
			int countInput2 = buffer.readVarInt();
			Ingredient input3 = Ingredient.read(buffer);
			int countInput3 = buffer.readVarInt();
			Ingredient input4 = Ingredient.read(buffer);
			int countInput4 = buffer.readVarInt();
			ItemStack output = buffer.readItemStack();
			int workTime = buffer.readVarInt();
			return new ReactionChamberRecipe(input1, countInput1, input2, countInput2, input3, countInput3, input4,
					countInput4, output, workTime, recipeId);
		}

		@Override
		public void write(PacketBuffer buffer, ReactionChamberRecipe recipe) {
			recipe.input1.write(buffer);
			buffer.writeVarInt(recipe.countInput1);
			recipe.input2.write(buffer);
			buffer.writeVarInt(recipe.countInput2);
			recipe.input3.write(buffer);
			buffer.writeVarInt(recipe.countInput3);
			recipe.input4.write(buffer);
			buffer.writeVarInt(recipe.countInput4);
			buffer.writeItemStack(recipe.output);
			buffer.writeVarInt(recipe.workingTime);

		}

		private JsonElement getJsonElement(JsonObject obj, String name) {
			return JSONUtils.isJsonArray(obj, name) ? JSONUtils.getJsonArray(obj, name)
					: JSONUtils.getJsonObject(obj, name);
		}

		private Pair<Integer, Ingredient> deserializeItems(JsonElement el) {
			int count = JSONUtils.getInt(el.getAsJsonObject(), "count", 1);
			if (count <= 0)
				throw new JsonIOException("count has to be greater then 0, u cant make a Ingredient with less then 1");
			Ingredient input = deserializeIngredient(el);
			return Pair.of(count, input);
		}

		private Ingredient deserializeIngredient(JsonElement json) {
			return Ingredient.fromItemListStream(Stream.of(Ingredient.deserializeItemList(json.getAsJsonObject())));
		}

	}

}
