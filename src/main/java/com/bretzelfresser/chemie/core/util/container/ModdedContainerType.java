package com.bretzelfresser.chemie.core.util.container;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.network.IContainerFactory;

public class ModdedContainerType<T extends Container> extends ContainerType<T>{

	public ModdedContainerType(IContainerFactory<T> factory) {
		super(factory);
	}

}
