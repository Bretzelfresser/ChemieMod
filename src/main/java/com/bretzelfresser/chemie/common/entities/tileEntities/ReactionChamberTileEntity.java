package com.bretzelfresser.chemie.common.entities.tileEntities;

import com.bretzelfresser.chemie.common.container.ReactionChamberContainer;
import com.bretzelfresser.chemie.common.recipes.ReactionChamberRecipe;
import com.bretzelfresser.chemie.core.init.RecipeInit;
import com.bretzelfresser.chemie.core.init.TileEntityTypeInit;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.IIntArray;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.Constants;

public class ReactionChamberTileEntity extends ContainerTileEntity implements ITickableTileEntity {

	private int counter, maxCounter, smeltCounter, maxSmeltCounter;
	private ReactionChamberRecipe recipe;

	private IIntArray fields = new IIntArray() {

		@Override
		public void set(int index, int value) {
			switch (index) {
			case 0:
				counter = value;
				break;
			case 1:
				maxCounter = value;
				break;
			case 2:
				smeltCounter = value;
				break;
			case 3:
				maxSmeltCounter = value;
				break;
			default:
				throw new IndexOutOfBoundsException("index: " + index + " is out of Bounds");
			}

		}

		@Override
		public int get(int index) {
			switch (index) {
			case 0:
				return counter;
			case 1:
				return maxCounter;
			case 2:
				return smeltCounter;
			case 3:
				return maxSmeltCounter;
			default:
				throw new IndexOutOfBoundsException("index: " + index + " is out of Bounds");
			}
		}

		@Override
		public int size() {
			return 4;
		}
	};

	public ReactionChamberTileEntity() {
		super(TileEntityTypeInit.REACTION_CHAMBER_TILE_ENTITY.get(), 8);
		counter = 0;
		maxCounter = 1;
		smeltCounter = 0;
		maxSmeltCounter = 1;
	}

	void encodeExtraData(PacketBuffer buffer) {
		buffer.writeByte(fields.size());
	}

	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		super.read(state, nbt);
		counter = nbt.getInt("counter");
		maxCounter = nbt.getInt("maxcounter");
		smeltCounter = nbt.getInt("smeltcounter");
		maxSmeltCounter = nbt.getInt("maxSmeltCounter");
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound = super.write(compound);
		compound.putInt("counter", counter);
		compound.putInt("maxcounter", maxCounter);
		compound.putInt("smeltcounter", smeltCounter);
		compound.putInt("maxSmeltCounter", maxSmeltCounter);
		return compound;
	}

	@Override
	public void tick() {
		if (!world.isRemote)
			return;
		if (RecipeInit.getRecipes(RecipeInit.REACTION_CHAMBER_RECIPE, world.getRecipeManager()).values().size() == 0)
			return;
		ItemStack input1 = getStackInSlot(0);
		ItemStack input2 = getStackInSlot(1);
		ItemStack input3 = getStackInSlot(2);
		ItemStack input4 = getStackInSlot(3);
		ItemStack fuel1 = getStackInSlot(4);
		ItemStack fuel2 = getStackInSlot(5);
		ItemStack output = getStackInSlot(6);
		for (final IRecipe<?> recipe : RecipeInit
				.getRecipes(RecipeInit.REACTION_CHAMBER_RECIPE, world.getRecipeManager()).values()) {
			ReactionChamberRecipe currRec = (ReactionChamberRecipe) recipe;
			if (counter <= 0 && (output.getItem() == currRec.getRecipeOutput().getItem() || output.isEmpty())
					&& currRec.isValid(input1, input2, input3, input4) && checkFuel(fuel1, fuel2)) {
				this.recipe = currRec;
				this.counter = this.recipe.getWorkingTime();
				this.maxCounter = counter;
				int[] toShrink = this.recipe.shrink(input1, input2, input3, input4);
				for(int i = 0;i<4;i++) {
					decrStackSize(i, toShrink[i]);
				}
			}

			BlockState state = world.getBlockState(getPos());
			if (state.get(BlockStateProperties.POWERED) != counter > 0) {
				world.setBlockState(pos, state.with(BlockStateProperties.POWERED, counter > 0),
						Constants.BlockFlags.NOTIFY_NEIGHBORS + Constants.BlockFlags.BLOCK_UPDATE);
			}
			if (counter > 0 && smeltCounter > 0) {
				counter--;
				smeltCounter--;
				if (counter == 0) {
					if (output.isEmpty()) {
						setInventorySlotContents(6, this.recipe.getRecipeOutput().copy());
					} else if (output.getCount() + recipe.getRecipeOutput().copy().getCount() <= output
							.getMaxStackSize()) {
						output.setCount(output.getCount() + recipe.getRecipeOutput().copy().getCount());
					}
				}
				checkFuel(fuel1, fuel2);
				sendUpdate();
			}
			if (counter <= 0 && smeltCounter > 0)
				smeltCounter--;
		}

	}

	private boolean checkFuel(ItemStack fuel1, ItemStack fuel2) {
		if (smeltCounter > 0)
			return true;
		if (ForgeHooks.getBurnTime(fuel1) > 0 && ForgeHooks.getBurnTime(fuel1) == ForgeHooks.getBurnTime(fuel2)) {
			smeltCounter = ForgeHooks.getBurnTime(fuel1);
			if (fuel1.getItem() == fuel2.getItem() && fuel1.getItem() == Items.LAVA_BUCKET) {
				setInventorySlotContents(4, new ItemStack(Items.BUCKET));
				setInventorySlotContents(5, new ItemStack(Items.BUCKET));
				return true;
			}
			decrStackSize(4, 1);
			decrStackSize(5, 1);
			return true;
		}
		return false;
	}
	@Override
	public String setName() {
		return "reaction_chamber";
	}

	@Override
	protected Container createContainer(int id, PlayerInventory inventory) {
		return new ReactionChamberContainer(id, inventory, this, fields);
	}
	
	private void sendUpdate() {
		BlockState state = world.getBlockState(getPos());
		world.setBlockState(pos, state.with(BlockStateProperties.POWERED, smeltCounter > 0),
				Constants.BlockFlags.NOTIFY_NEIGHBORS + Constants.BlockFlags.BLOCK_UPDATE);
	}

}
