package com.bretzelfresser.chemie.common.container;

import java.util.Objects;

import javax.annotation.Nullable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;

public abstract class BaseTileEntityContainer<T extends LockableLootTileEntity> extends Container {

	protected final PlayerInventory playerInteventory;
	protected final IWorldPosCallable canInteractWithCallable;
	protected final T te;
	protected final IIntArray fields;

	/**
	 * for the Client Side
	 * 
	 * @param type
	 * @param id
	 * @param playerInventory
	 * @param buffer
	 * @param sizeOfIIntArray - only use when u have a IIntArray otherwise set to 0
	 */
	public BaseTileEntityContainer(final ContainerType<?> type, final int id, final PlayerInventory playerInventory,
			final PacketBuffer buffer, int sizeOfIIntArray) {
		this(type, id, playerInventory, getClientTileEntity(playerInventory, buffer), new IntArray(sizeOfIIntArray));
	}

	/**
	 * for the Server Side
	 * 
	 * @param type
	 * @param id
	 * @param playerInventory
	 * @param tileEntity
	 * @param only use when u use IIntArrays, otherwise set it to null
	 */
	public BaseTileEntityContainer(final ContainerType<?> type, final int id, final PlayerInventory playerInventory,
			final T te, @Nullable IIntArray fields) {
		super(type, id);
		this.playerInteventory = playerInventory;
		this.te = te;
		this.canInteractWithCallable = IWorldPosCallable.of(te.getWorld(), te.getPos());
		if(fields == null || fields.size() == 0)
			this.fields = null;
		else {
			this.fields = fields;
			trackIntArray(fields);
		}
		init();
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;
	}

	protected int addHorizontalSlots(IInventory handler, int Index, int x, int y, int amount,
			int distanceBetweenSlots) {
		for (int i = 0; i < amount; i++) {
			addSlot(new Slot(handler, Index, x, y));
			Index++;
			x += distanceBetweenSlots;
		}
		return Index;
	}

	protected int addSlotField(IInventory handler, int StartIndex, int x, int y, int horizontalAmount,
			int horizontalDistance, int verticalAmount, int VerticalDistance) {
		for (int i = 0; i < verticalAmount; i++) {
			StartIndex = addHorizontalSlots(handler, StartIndex, x, y, horizontalAmount, horizontalDistance);
			y += VerticalDistance;
		}
		return StartIndex;
	}

	/**
	 * this method is called at the end when one of the constructors is used
	 */
	public abstract void init();

	protected void addPlayerInventory(int x, int y) {
		// the Rest
		addSlotField(playerInteventory, 9, x, y, 9, 18, 3, 18);
		y += 58;
		// Hotbar
		addHorizontalSlots(playerInteventory, 0, x, y, 9, 18);
	}

	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings("unchecked")
	protected static <X extends TileEntity> X getClientTileEntity(final PlayerInventory inventory,
			final PacketBuffer buffer) {
		Objects.requireNonNull(inventory, "the inventory must not be null");
		Objects.requireNonNull(buffer, "the buffer must not be null");
		final TileEntity tileEntity = inventory.player.world.getTileEntity(buffer.readBlockPos());
		return (X) tileEntity;
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			if (index < te.getSizeInventory()
					&& !this.mergeItemStack(stack1, te.getSizeInventory(), this.inventorySlots.size(), true))
				return ItemStack.EMPTY;
			if (!this.mergeItemStack(stack1, 0, te.getSizeInventory(), false))
				return ItemStack.EMPTY;
			if (stack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}
		return stack;
	}

	protected static class LockedSlot extends Slot {

		public LockedSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return false;
		}

	}

	protected static class FilterSlot extends Slot {

		private Item[] validItems;

		public FilterSlot(IInventory inventoryIn, int index, int xPosition, int yPosition, final Item[] validItems) {
			super(inventoryIn, index, xPosition, yPosition);
			this.validItems = validItems;
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			for (Item i : validItems) {
				if (i.equals(stack.getItem()))
					return true;
			}
			return false;
		}

	}
	
	public static class FuelSlot extends Slot{

		public FuelSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			return ForgeHooks.getBurnTime(stack) > 0;
		}
		
	}

}
