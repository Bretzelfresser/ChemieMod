package com.bretzelfresser.chemie.common.container;

import com.bretzelfresser.chemie.common.entities.tileEntities.FridgeTileEntity;
import com.bretzelfresser.chemie.core.init.ContainerTypeInit;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class FridgeContainer extends BaseTileEntityContainer<FridgeTileEntity>{

	public FridgeContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		super(ContainerTypeInit.FRIDGE_CONTAINER_TYPE.get(), id, playerInventory, buffer, 0);
	}

	public FridgeContainer(int id, PlayerInventory playerInventory, FridgeTileEntity te) {
		super(ContainerTypeInit.FRIDGE_CONTAINER_TYPE.get(), id, playerInventory, te, null);
	}

	@Override
	public void init() {
		addPlayerInventory(8, 68);
		addSlotField(te, 0, 8, 18, 9, 18, 2, 18);
	}

}
