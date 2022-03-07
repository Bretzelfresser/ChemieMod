package com.bretzelfresser.chemie.common.container;

import com.bretzelfresser.chemie.common.entities.tileEntities.ReactionChamberTileEntity;
import com.bretzelfresser.chemie.core.init.BlockInit;
import com.bretzelfresser.chemie.core.init.ContainerTypeInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IIntArray;

public class ReactionChamberContainer extends BaseTileEntityContainer<ReactionChamberTileEntity> {

	public ReactionChamberContainer(int id, PlayerInventory playerInventory, ReactionChamberTileEntity tileEntity,
			IIntArray fields) {
		super(ContainerTypeInit.REACTION_CHAMBER_CONTAINER_TYPE.get(), id, playerInventory, tileEntity, fields);
	}

	public ReactionChamberContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		super(ContainerTypeInit.REACTION_CHAMBER_CONTAINER_TYPE.get(), id, playerInventory, buffer, 4);
	}

	@Override
	public void init() {
		addPlayerInventory(8, 84);
		addSlotField(te, 0, 6, 18, 2, 18, 2, 18);
		// addHorizontalSlots(te, 5, 51, 52, 2, 18);
		addSlot(new FuelSlot(te, 4, 51, 52));
		addSlot(new FuelSlot(te, 5, 69, 52));
		// addHorizontalSlots(te, 6, 101, 35, 2, 18);
		addSlot(new LockedSlot(te, 6, 101, 35));
		addSlot(new LockedSlot(te, 7, 119, 35));
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockInit.REACTION_CHAMBER.get());
	}

	public boolean isPowered() {
		return te.getWorld().getBlockState(te.getPos()).get(BlockStateProperties.POWERED);
	}

	/**
	 * 
	 * @return the percentage of progress done starting by 0.0 and going up to 100.0
	 */
	public double getProgressPercentage() {
		int progress = fields.get(0);
		int maxProgress = fields.get(1);
		if (progress == 0 || maxProgress == 0) {
			return 0;
		}
		return 100 - (progress / maxProgress) * 100;
	}

	/**
	 * 
	 * @return the percentage of Fuel starting with 100.0 and going down to 0.0
	 */
	public double getFuelPercentage() {
		int fuel = fields.get(2);
		int maxFuel = fields.get(3);
		if (fuel == 0 || maxFuel == 0)
			return 0;
		return (fuel / maxFuel) * 100;
	}

}
