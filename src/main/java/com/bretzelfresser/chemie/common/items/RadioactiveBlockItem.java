package com.bretzelfresser.chemie.common.items;

import com.bretzelfresser.chemie.core.init.PotionInit;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class RadioactiveBlockItem extends BlockItem{

	private int radiationStrenght;
	
	public RadioactiveBlockItem(Block blockIn, Properties builder, int radiationStrenght) {
		super(blockIn, builder);
		this.radiationStrenght = radiationStrenght;
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entity, int itemSlot, boolean isSelected) {
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
				livingEntity.addPotionEffect(new EffectInstance(PotionInit.RADIATION.get(), 1, radiationStrenght));
		}

	}

}
