package com.bretzelfresser.chemie.common.recipes;

import com.bretzelfresser.chemie.Chemie;

import net.minecraft.item.crafting.IRecipeType;

public class ReactionChamberRecipeType implements IRecipeType<ReactionChamberRecipe>{
	
	@Override
	public String toString() {
		return Chemie.MODID + ":" + "reaction_chamber";
	}

}
