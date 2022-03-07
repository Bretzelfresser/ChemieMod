package com.bretzelfresser.chemie.common.recipes;

import com.bretzelfresser.chemie.Chemie;

import net.minecraft.item.crafting.IRecipeType;

public class SifterRecipeType implements IRecipeType<SifterRecipe>{

	@Override
	public String toString() {
		return Chemie.MODID + ":" + "sifter_recipe";
	}
}
