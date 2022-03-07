package com.bretzelfresser.chemie.common.entities.tileEntities;

import com.bretzelfresser.chemie.common.container.SifterContainer;
import com.bretzelfresser.chemie.common.recipes.SifterRecipe;
import com.bretzelfresser.chemie.core.init.RecipeInit;
import com.bretzelfresser.chemie.core.init.TileEntityTypeInit;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraftforge.common.util.Constants;

public class SifterTileEntity extends ContainerTileEntity implements ITickableTileEntity {

	private int counter, maxCounter;
	private SifterRecipe recipe;

	public SifterTileEntity() {
		super(TileEntityTypeInit.SIFTER_TILE_ENTITY.get(), 2);
		counter = -1;
		maxCounter = 1;
		recipe = null;
	}

	@Override
	protected Container createContainer(int id, PlayerInventory inventory) {
		return new SifterContainer(id, inventory, this);
	}

	@Override
	protected String setName() {
		return "sifter";
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound = super.write(compound);
		compound.putInt("counter", counter);
		compound.putInt("maxcounter", maxCounter);
		return compound;

	}

	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		super.read(state, nbt);
		counter = nbt.getInt("counter");
		maxCounter = nbt.getInt("maxcounter");
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getMaxCounter() {
		return maxCounter;
	}

	public void setMaxCounter(int maxCounter) {
		this.maxCounter = maxCounter;
	}

	public double getCunterPercentage() {
		if (world.getBlockState(getPos()).get(BlockStateProperties.POWERED))
			return 100 - Math.round(((double) (counter) / (double) (maxCounter)) * 100d);
		return 0;
	}

	@Override
	public void tick() {
		if (world.isRemote)
			return;
		if (RecipeInit.getRecipes(RecipeInit.SIFTER_RECIPE, world.getRecipeManager()).values().size() == 0)
			return;
		ItemStack input = getStackInSlot(0);
		ItemStack output = getStackInSlot(1);
		for (final IRecipe<?> recipe : RecipeInit.getRecipes(RecipeInit.SIFTER_RECIPE, world.getRecipeManager())
				.values()) {
			this.recipe = (SifterRecipe) recipe;
			if ((recipe.getRecipeOutput().getItem() == output.getItem() || output.isEmpty()) && counter <= 0
					&& this.recipe.isValid(input)) {
				counter = this.recipe.getSiftTime();
				maxCounter = this.recipe.getSiftTime();
				input.shrink(1);
			}
			BlockState state = world.getBlockState(getPos());
			if (state.get(BlockStateProperties.POWERED) != counter > 0) {
				world.setBlockState(pos, state.with(BlockStateProperties.POWERED, counter > 0),
						Constants.BlockFlags.NOTIFY_NEIGHBORS + Constants.BlockFlags.BLOCK_UPDATE);

			}
			if (counter > 0 && state.get(BlockStateProperties.POWERED)) {
				counter--;
				if (counter == 0) {
					if (output.isEmpty()) {
						setInventorySlotContents(1, recipe.getRecipeOutput().copy());
					} else {
						output.setCount(output.getCount() + recipe.getRecipeOutput().getCount());
					}
				}
			}
			
		}
	
	}

}
