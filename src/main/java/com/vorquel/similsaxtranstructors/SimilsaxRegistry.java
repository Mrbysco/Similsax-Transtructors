package com.vorquel.similsaxtranstructors;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SimilsaxRegistry {
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SimilsaxTranstructors.MODID);

  public static final RegistryObject<Item> BASIC = ITEMS.register("transtructor_basic", () -> new ItemSimilsax(new Item.Properties().stacksTo(1).durability(800)));
  public static final RegistryObject<Item> ADVANCED = ITEMS.register("transtructor_advanced", () -> new ItemSimilsax(new Item.Properties().stacksTo(1).durability(9000)));
}
