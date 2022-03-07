package com.bretzelfresser.chemie.core.init;

import com.bretzelfresser.chemie.Chemie;
import com.bretzelfresser.chemie.common.items.Element;
import com.bretzelfresser.chemie.common.items.LeadArmor;
import com.bretzelfresser.chemie.common.items.RadioactiveItem;
import com.bretzelfresser.chemie.core.enums.ModArmorMaterial;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Chemie.MODID);

	public static final RegistryObject<Item> SULFUR = ITEMS.register("sulfur",
			() -> new Element(new Item.Properties()));
	public static final RegistryObject<Item> POLONIUM = ITEMS.register("polonium",
			() -> new RadioactiveItem(new Item.Properties().group(Chemie.ELEMENTS), 1));
	public static final RegistryObject<Item> URANIUM = ITEMS.register("uranium",
			() -> new RadioactiveItem(new Item.Properties().group(Chemie.ELEMENTS), 1));

	public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot",
			() -> new Element(new Item.Properties()));
	public static final RegistryObject<Item> LEAD_NUGGET = ITEMS.register("lead_nugget",
			() -> new Element(new Item.Properties()));
	public static final RegistryObject<Item> POLONIUM_NUGGET = ITEMS.register("polonium_nugget",
			() -> new RadioactiveItem(new Item.Properties().group(Chemie.ELEMENTS), 0));
	public static final RegistryObject<Item> URANIUM_NUGGET = ITEMS.register("uranium_nugget",
			() -> new RadioactiveItem(new Item.Properties().group(Chemie.ELEMENTS), 0));

	// Armor
	public static final RegistryObject<Item> LEAD_HELMET = ITEMS.register("lead_helmet",
			() -> new LeadArmor(ModArmorMaterial.Lead, EquipmentSlotType.HEAD,
					new Item.Properties().group(ItemGroup.COMBAT)));
	public static final RegistryObject<Item> LEAD_LEGGINS = ITEMS.register("lead_leggins",
			() -> new LeadArmor(ModArmorMaterial.Lead, EquipmentSlotType.LEGS,
					new Item.Properties().group(ItemGroup.COMBAT)));
	public static final RegistryObject<Item> LEAD_CHESTPLATE = ITEMS.register("lead_chestplate",
			() -> new LeadArmor(ModArmorMaterial.Lead, EquipmentSlotType.CHEST,
					new Item.Properties().group(ItemGroup.COMBAT)));
	public static final RegistryObject<Item> LEAD_BOOTS = ITEMS.register("lead_boots",
			() -> new LeadArmor(ModArmorMaterial.Lead, EquipmentSlotType.FEET,
					new Item.Properties().group(ItemGroup.COMBAT)));
	

}
