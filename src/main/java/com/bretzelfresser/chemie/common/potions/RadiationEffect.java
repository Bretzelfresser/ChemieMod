package com.bretzelfresser.chemie.common.potions;

import com.bretzelfresser.chemie.core.init.ItemInit;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class RadiationEffect extends Effect {

	private int ticks;

	public RadiationEffect() {
		super(EffectType.HARMFUL, 6730496);
		ticks = 0;
	}

	@Override
	public void performEffect(LivingEntity entity, int amplifier) {
		int armorCount = 0;
		for (ItemStack itemstack : entity.getArmorInventoryList()) {
			Item i = itemstack.getItem();
			if (i.equals(ItemInit.LEAD_BOOTS.get()) || i.equals(ItemInit.LEAD_LEGGINS.get())
					|| i.equals(ItemInit.LEAD_CHESTPLATE.get()) || i.equals(ItemInit.LEAD_HELMET.get()))
				armorCount++;
		}
		if (armorCount != 4)
			entity.attackEntityFrom(new DamageSource("radiation"), 1.0f*amplifier);
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		if(amplifier > 5)
			amplifier = 5;
		int time = 25 - 5 * amplifier;
		if (time < 5) {
			time = 5;
		}
		if (time == ticks) {
			ticks = 0;
			return true;
		} else {
			ticks++;
			return false;
		}

	}

}
