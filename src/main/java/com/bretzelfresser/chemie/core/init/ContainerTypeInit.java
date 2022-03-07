package com.bretzelfresser.chemie.core.init;

import com.bretzelfresser.chemie.Chemie;
import com.bretzelfresser.chemie.common.container.FridgeContainer;
import com.bretzelfresser.chemie.common.container.ReactionChamberContainer;
import com.bretzelfresser.chemie.common.container.SifterContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypeInit {
	
	public static final DeferredRegister<ContainerType<?>> CONTAINER = DeferredRegister.create(ForgeRegistries.CONTAINERS, Chemie.MODID);
	
	public static final RegistryObject<ContainerType<ReactionChamberContainer>> REACTION_CHAMBER_CONTAINER_TYPE = CONTAINER.register("reaction_chamber_container", 
			() -> IForgeContainerType.create(ReactionChamberContainer::new));
	
	public static final RegistryObject<ContainerType<FridgeContainer>> FRIDGE_CONTAINER_TYPE = CONTAINER.register("fridge_container", 
			() -> IForgeContainerType.create(FridgeContainer::new));
	
	public static final RegistryObject<ContainerType<SifterContainer>> SIFTER_CONTAINER_TYPE = CONTAINER.register("sifter_container", 
			() -> IForgeContainerType.create(SifterContainer::new));

}
