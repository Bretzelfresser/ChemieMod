package com.bretzelfresser.chemie.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public abstract class BasicTileEntityBlock extends RotatableBlock{

	public BasicTileEntityBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return createTheTileEntity(state, world);
	}
	
	protected abstract TileEntity createTheTileEntity(BlockState state, IBlockReader world);

}
