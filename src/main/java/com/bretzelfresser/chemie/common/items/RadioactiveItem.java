package com.bretzelfresser.chemie.common.items;

import com.bretzelfresser.chemie.core.init.PotionInit;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class RadioactiveItem extends Item {

	private int radiationStrenght;
	
	public RadioactiveItem(Properties properties, final int radioationStrenght) {
		super(properties);
		this.radiationStrenght = radioationStrenght;
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entity, int itemSlot, boolean isSelected) {
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
				livingEntity.addPotionEffect(new EffectInstance(PotionInit.RADIATION.get(), 1, radiationStrenght));
		}
	}

}
