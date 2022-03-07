package com.bretzelfresser.chemie.core.init;

import com.bretzelfresser.chemie.Chemie;
import com.bretzelfresser.chemie.common.entities.tileEntities.FridgeTileEntity;
import com.bretzelfresser.chemie.common.entities.tileEntities.ReactionChamberTileEntity;
import com.bretzelfresser.chemie.common.entities.tileEntities.SifterTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeInit {

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister
			.create(ForgeRegistries.TILE_ENTITIES, Chemie.MODID);

	public static final RegistryObject<TileEntityType<ReactionChamberTileEntity>> REACTION_CHAMBER_TILE_ENTITY = TILE_ENTITY_TYPES
			.register("reaction_chamber_tile_entity", () -> TileEntityType.Builder
					.create(ReactionChamberTileEntity::new, BlockInit.REACTION_CHAMBER.get()).build(null));
	public static final RegistryObject<TileEntityType<FridgeTileEntity>> FRIDGE_TILE_ENTITY = TILE_ENTITY_TYPES
			.register("fridge_tile_entity",
					() -> TileEntityType.Builder.create(FridgeTileEntity::new, BlockInit.FRIDGE.get()).build(null));
	public static final RegistryObject<TileEntityType<SifterTileEntity>> SIFTER_TILE_ENTITY = TILE_ENTITY_TYPES
			.register("sifter_tile_entity",
					() -> TileEntityType.Builder.create(SifterTileEntity::new, BlockInit.SIFTER.get()).build(null));
}
