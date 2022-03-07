package com.bretzelfresser.chemie.common.entities.tileEntities;

import com.bretzelfresser.chemie.Chemie;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class ContainerTileEntity extends LockableLootTileEntity{

	protected final int slots;
	protected NonNullList<ItemStack> items;
	
	protected ContainerTileEntity(TileEntityType<?> tileEntityTypeIn, int slots) {
		super(tileEntityTypeIn);
		this.slots = slots;
		this.items = NonNullList.withSize(slots, ItemStack.EMPTY);
	}
	
	@Override
	public int getSizeInventory() {
		return slots;
	}
	
	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.items;
	}
	
	@Override
	protected void setItems(NonNullList<ItemStack> items) {
		this.items = items;
	}
	
	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container." + Chemie.MODID +"." + setName());
	}
	
	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return createContainer(id, player);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound = super.write(compound);
		//if(!this.checkLootAndWrite(compound))
			ItemStackHelper.saveAllItems(compound, items);
			Chemie.LOGGER.debug("compoundWritten");
		return compound;
	}
	
	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		super.read(state, nbt);
		this.items = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
		//if(!this.checkLootAndRead(nbt))
			ItemStackHelper.loadAllItems(nbt, this.items);
			Chemie.LOGGER.debug("compound Read");
	}
	
	
	protected abstract Container createContainer(int id, PlayerInventory inventory);
	protected abstract String setName();

}
