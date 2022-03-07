package com.bretzelfresser.chemie;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bretzelfresser.chemie.core.init.BlockInit;
import com.bretzelfresser.chemie.core.init.ContainerTypeInit;
import com.bretzelfresser.chemie.core.init.ItemInit;
import com.bretzelfresser.chemie.core.init.PotionInit;
import com.bretzelfresser.chemie.core.init.RecipeInit;
import com.bretzelfresser.chemie.core.init.TileEntityTypeInit;
import com.bretzelfresser.chemie.world.OreGeneration;

import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("chemie")
public class Chemie {
	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger();
	// Modid
	public static final String MODID = "chemie";

	public static final ItemGroup ORES = new ChemieItemGroups("ores", 0);
	public static final ItemGroup MACHINES = new ChemieItemGroups("machines", 1);
	public static final ItemGroup ELEMENTS = new ChemieItemGroups("elements", 2);

	public Chemie() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		bus.addListener(this::setup);
		bus.addGenericListener(IRecipeSerializer.class ,RecipeInit::registerRecipes);
		ItemInit.ITEMS.register(bus);

		BlockInit.BLOCKS.register(bus);
		BlockInit.ORES.register(bus);
		BlockInit.MACHINES.register(bus);
		
		TileEntityTypeInit.TILE_ENTITY_TYPES.register(bus);
		
		ContainerTypeInit.CONTAINER.register(bus);

		PotionInit.EFFECTS.register(bus);
		PotionInit.POTIONS.register(bus);
		

		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGeneration::generateOres);
		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		PotionInit.addPotionRecipes();
		RenderTypeLookup.setRenderLayer(BlockInit.REACTION_CHAMBER.get(), net.minecraft.client.renderer.RenderType.getTranslucent());
	}

	public static class ChemieItemGroups extends ItemGroup {

		private final int indexOfIcon;
		public ChemieItemGroups(final String label, final int indexOfIcon) {
			super(MODID + "." + label);
			this.indexOfIcon = indexOfIcon;
		}

		@Override
		public ItemStack createIcon() {
			switch(indexOfIcon) {
			case 0:
				return BlockInit.LEAD_ORE.get().asItem().getDefaultInstance();
			case 1:
				return BlockInit.REACTION_CHAMBER.get().asItem().getDefaultInstance();
			case 2:
				return ItemInit.SULFUR.get().asItem().getDefaultInstance();
			default:
				return Blocks.BARRIER.asItem().getDefaultInstance();
			}
		}

	}

}
