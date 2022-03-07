package com.bretzelfresser.chemie.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;

public class BasicOre extends Block{

	
	public BasicOre(float hardness, float resistance, int miningLevel) {
		super(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLUE)
				.hardnessAndResistance(hardness, resistance)
				.harvestTool(ToolType.PICKAXE).harvestLevel(miningLevel)
				.sound(SoundType.STONE));
	}
}
