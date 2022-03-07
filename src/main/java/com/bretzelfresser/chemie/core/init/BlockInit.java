package com.bretzelfresser.chemie.core.init;

import java.util.ArrayList;

import com.bretzelfresser.chemie.Chemie;
import com.bretzelfresser.chemie.common.blocks.BasicOre;
import com.bretzelfresser.chemie.common.blocks.ExplosiveOre;
import com.bretzelfresser.chemie.common.blocks.Fridge;
import com.bretzelfresser.chemie.common.blocks.ReactionChamber;
import com.bretzelfresser.chemie.common.blocks.Sifter;
import com.bretzelfresser.chemie.common.items.RadioactiveBlockItem;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Chemie.MODID, bus = Bus.MOD)
public class BlockInit {
	//everything will be automatically registered at the Elements tab
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Chemie.MODID);
	
	//everything registered with this, has automatically an Block Item in the oresItemGroup
	public static final DeferredRegister<Block> ORES = DeferredRegister.create(ForgeRegistries.BLOCKS, Chemie.MODID);
	//everything registered with this, has automatically an Block Item in the oresItemGroup
		public static final DeferredRegister<Block> MACHINES = DeferredRegister.create(ForgeRegistries.BLOCKS, Chemie.MODID);
	//the icon of the creative Tab has to be first
	public static final RegistryObject<Block> LEAD_ORE = ORES.register("lead_ore",
			() -> new BasicOre(15, 15, 4));
	
	public static final RegistryObject<Block> SULFUR_ORE = ORES.register("sulfur_ore",
			() -> new ExplosiveOre(9, 4, 3));
	
	
	//Element Blocks here
	public static final RegistryObject<Block> SULFUR_BLOCK = BLOCKS.register("sulfur_block",
			() -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(9, 5)));
	public static final RegistryObject<Block> POLONIUM_BLOCK = BLOCKS.register("polonium_block",
			() -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(9, 5)));
	public static final RegistryObject<Block> LEAD_BLOCK = BLOCKS.register("lead_block",
			() -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(9, 5)));
	public static final RegistryObject<Block> URANIUM_BLOCK = BLOCKS.register("uranium_block",
			() -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(9, 5)));
	
	
	
	
	//Machines
	public static final RegistryObject<Block> REACTION_CHAMBER = MACHINES.register("reaction_chamber",
			() -> new ReactionChamber());
	public static final RegistryObject<Block> FRIDGE= MACHINES.register("fridge",
			() -> new Fridge());
	public static final RegistryObject<Block> SIFTER = MACHINES.register("sifter",
			() -> new Sifter());
	
	/**
	 * for all BlockItems from the Ores Registry
	 * @param event - the event that is called
	 */
	@SubscribeEvent
	public static void registerOreItems(RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();
		BlockInit.ORES.getEntries().stream().map(RegistryObject::get).forEach(block -> {
			final Item.Properties properties = new Item.Properties().group(Chemie.ORES);
			final BlockItem blockItem = new BlockItem(block, properties);
			blockItem.setRegistryName(block.getRegistryName());
			registry.register(blockItem);
		});
	}
	
	@SubscribeEvent
	public static void registerMachineItems(RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();
		BlockInit.MACHINES.getEntries().stream().map(RegistryObject::get).forEach(block -> {
			final Item.Properties properties = new Item.Properties().group(Chemie.MACHINES);
			final BlockItem blockItem = new BlockItem(block, properties);
			blockItem.setRegistryName(block.getRegistryName());
			registry.register(blockItem);
		});
	}
	
	@SubscribeEvent
	public static void registerElementItems(RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();
		final ArrayList<BlockItem> blocks = new ArrayList<>();
		final Item.Properties props = new Item.Properties().group(Chemie.ELEMENTS);
		blocks.add(new RadioactiveBlockItem(POLONIUM_BLOCK.get(), props, 3));
		blocks.add(new RadioactiveBlockItem(URANIUM_BLOCK.get(), props, 3));
		blocks.add(new BlockItem(SULFUR_BLOCK.get(), props));
		for(BlockItem b : blocks) {
			b.setRegistryName(b.getBlock().getRegistryName());
			registry.register(b);
		}
	}
}
