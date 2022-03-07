package com.bretzelfresser.chemie.common.blocks;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ExplosiveOre extends BasicOre {

	/**
	 * creates a Block which explodes when an entity walks over it, except for when
	 * the entity is sneaking or immune to fire
	 * 
	 * @param hardness    - the Hardness of the Block
	 * @param resistance  - the Resistance of the Block, for example when it
	 *                    explodes
	 * @param miningLevel - the Minig Level, when the Ore can be mined -> iron has 2
	 */
	public ExplosiveOre(float hardness, float resistance, int miningLevel) {
		super(hardness, resistance, miningLevel);
	}

	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entity) {
		if (!entity.isSneaking() && !entity.isImmuneToFire() && !entity.isCrouching()
				&& !entity.isImmuneToExplosions()) {
			world.destroyBlock(pos, false);
			world.createExplosion(entity, pos.getX(), pos.getY(), pos.getZ(), 4f, Explosion.Mode.BREAK);
		}
	}

}
