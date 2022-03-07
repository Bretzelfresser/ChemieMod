package com.bretzelfresser.chemie.common.items;

import com.bretzelfresser.chemie.Chemie;

import net.minecraft.item.Item;

public class Element extends Item{

	public Element(Properties properties) {
		super(properties.group(Chemie.ELEMENTS));
	}

}
