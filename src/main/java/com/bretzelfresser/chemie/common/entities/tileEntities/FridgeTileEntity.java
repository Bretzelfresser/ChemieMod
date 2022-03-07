package com.bretzelfresser.chemie.common.entities.tileEntities;

import com.bretzelfresser.chemie.common.container.FridgeContainer;
import com.bretzelfresser.chemie.core.init.TileEntityTypeInit;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class FridgeTileEntity extends ContainerTileEntity{

	public FridgeTileEntity() {
		super(TileEntityTypeInit.FRIDGE_TILE_ENTITY.get(), 18);
	}

	@Override
	protected Container createContainer(int id, PlayerInventory inventory) {
		return new FridgeContainer(id, inventory, this);
	}

	@Override
	protected String setName() {
		return "fridge";
	}

}
