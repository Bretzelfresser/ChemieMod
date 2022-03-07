package com.bretzelfresser.chemie.common.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.bretzelfresser.chemie.common.entities.tileEntities.ReactionChamberTileEntity;
import com.bretzelfresser.chemie.core.init.TileEntityTypeInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ReactionChamber extends BasicTileEntityBlock{

	protected static final Map<Direction, VoxelShape> SHAPES = new HashMap<Direction, VoxelShape>();
	private static final VoxelShape shape = Stream.of(Block.makeCuboidShape(15, 10, 15, 16, 16, 16),
			Block.makeCuboidShape(2, 0, 2, 14, 1, 15), Block.makeCuboidShape(0, 1, 1, 16, 7, 7),
			Block.makeCuboidShape(1, 1, 7, 15, 12, 16), Block.makeCuboidShape(7, 7, 4, 11, 8, 7),
			Block.makeCuboidShape(8, 8, 6, 9, 9, 7), Block.makeCuboidShape(0, 7, 0, 1, 16, 1),
			Block.makeCuboidShape(11, 9, 6, 13, 10, 7), Block.makeCuboidShape(7, 12, 9, 9, 13, 10),
			Block.makeCuboidShape(9, 12, 6, 11, 13, 7), Block.makeCuboidShape(7, 9, 6, 8, 11, 7),
			Block.makeCuboidShape(6, 11, 6, 7, 12, 7), Block.makeCuboidShape(13, 12, 7, 14, 13, 11),
			Block.makeCuboidShape(13, 10, 6, 14, 12, 7), Block.makeCuboidShape(10, 8, 5, 11, 9, 6),
			Block.makeCuboidShape(1, 7, 0, 15, 8, 1), Block.makeCuboidShape(1, 10, 16, 15, 15, 16),
			Block.makeCuboidShape(1, 8, 0, 15, 15, 0), Block.makeCuboidShape(1, 16, 1, 15, 16, 15),
			Block.makeCuboidShape(0, 10, 6, 0, 15, 15), Block.makeCuboidShape(16, 10, 6, 16, 15, 15),
			Block.makeCuboidShape(0, 8, 1, 0, 15, 6), Block.makeCuboidShape(16, 8, 1, 16, 15, 6),
			Block.makeCuboidShape(1, 15, 0, 15, 16, 1), Block.makeCuboidShape(1, 15, 15, 15, 16, 16),
			Block.makeCuboidShape(15, 7, 1, 16, 8, 7), Block.makeCuboidShape(0, 7, 1, 1, 8, 7),
			Block.makeCuboidShape(0, 9, 6, 1, 10, 16), Block.makeCuboidShape(15, 9, 6, 16, 10, 16),
			Block.makeCuboidShape(15, 15, 0, 16, 16, 16), Block.makeCuboidShape(0, 15, 0, 1, 16, 16),
			Block.makeCuboidShape(15, 7, 0, 16, 16, 1), Block.makeCuboidShape(15, 7, 6, 16, 10, 7),
			Block.makeCuboidShape(0, 7, 6, 1, 10, 7), Block.makeCuboidShape(0, 10, 15, 1, 16, 16)).reduce((v1, v2) -> {
				return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
			}).get();

	public ReactionChamber() {
		super(Properties.create(Material.ROCK).notSolid().hardnessAndResistance(15f, 1000f).sound(SoundType.METAL));
		runCalculation(shape);
	}

	@Override
	protected TileEntity createTheTileEntity(BlockState state, IBlockReader world) {
		return TileEntityTypeInit.REACTION_CHAMBER_TILE_ENTITY.get().create();
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return state.get(BlockStateProperties.POWERED) ? 0 : 13;
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isRemote) {
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof ReactionChamberTileEntity)
				NetworkHooks.openGui((ServerPlayerEntity) player, (ReactionChamberTileEntity) te, pos);
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(BlockStateProperties.POWERED);
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(HORIZONTAL_FACING)));
	}

	protected static void calculateShapes(Direction to, VoxelShape shape) {
		VoxelShape[] buffer = new VoxelShape[] { shape, VoxelShapes.empty() };

		int times = (to.getHorizontalIndex() - Direction.NORTH.getHorizontalIndex() + 4) % 4;
		for (int i = 0; i < times; i++) {
			buffer[0].forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.or(buffer[1],
					VoxelShapes.create(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
			buffer[0] = buffer[1];
			buffer[1] = VoxelShapes.empty();
		}

		SHAPES.put(to, buffer[0]);
	}

	protected void runCalculation(VoxelShape shape) {
		for (Direction direction : Direction.values()) {
			calculateShapes(direction, shape);
		}
	}

}
