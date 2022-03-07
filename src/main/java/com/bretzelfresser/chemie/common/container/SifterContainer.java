package com.bretzelfresser.chemie.common.container;

import com.bretzelfresser.chemie.common.entities.tileEntities.SifterTileEntity;
import com.bretzelfresser.chemie.core.init.ContainerTypeInit;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IntReferenceHolder;

public class SifterContainer extends BaseTileEntityContainer<SifterTileEntity> {

	public SifterContainer(int id, PlayerInventory playerInventory, SifterTileEntity te) {
		super(ContainerTypeInit.SIFTER_CONTAINER_TYPE.get(), id, playerInventory, te, null);
	}

	public SifterContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		super(ContainerTypeInit.SIFTER_CONTAINER_TYPE.get(), id, playerInventory, buffer, 0);
	}

	@Override
	public void init() {
		addSlot(new Slot(te, 0, 39, 36) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return true;
			}
			
		});
		addSlot(new Slot(te, 1, 127, 36) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
		});
		addPlayerInventory(8, 84);
		trackCounter();
	}
	
	private void trackCounter() {
		trackInt(new IntReferenceHolder() {

			@Override
			public int get() {
				return te.getCounter() & 0xffff;
			}

			@Override
			public void set(int arg0) {
				te.setCounter(arg0 & 0x0000ffff);
				
			}
		});
		trackInt(new IntReferenceHolder() {
			
			@Override
			public void set(int arg0) {
				int s = arg0 & 0xffff0000;
				te.setCounter(s);
				
			}
			
			@Override
			public int get() {
				return (getCounter() >> 16) & 0xffff;
			}
		});
		trackInt(new IntReferenceHolder() {
			
			@Override
			public void set(int value) {
				te.setMaxCounter(value & 0x0000ffff);
				
			}
			
			@Override
			public int get() {
				return te.getMaxCounter() & 0xffff;
			}
		});
		trackInt(new IntReferenceHolder() {
			
			@Override
			public void set(int value) {
				int s = value & 0xffff0000;
				te.setMaxCounter(s);
				
			}
			
			@Override
			public int get() {
				return (te.getMaxCounter() >> 16) & 0xffff;
			}
		});
	}
	
	public int getCounter() {
		
		return te.getCounter();
	}
	
	public double getCounterPercentage() {
		return te.getCunterPercentage();
	}
	
	public boolean isPowered(){
		return te.getWorld().getBlockState(te.getPos()).get(BlockStateProperties.POWERED);
	}
}
