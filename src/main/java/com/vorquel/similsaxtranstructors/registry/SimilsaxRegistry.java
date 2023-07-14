package com.vorquel.similsaxtranstructors.registry;

import com.vorquel.similsaxtranstructors.SimilsaxTranstructors;
import com.vorquel.similsaxtranstructors.item.ItemSimilsax;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SimilsaxRegistry {

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SimilsaxTranstructors.MODID);
  public static final RegistryObject<Item> BASIC = ITEMS.register("transtructor_basic", () -> new ItemSimilsax(new Item.Properties().durability(800)));
  public static final RegistryObject<Item> ADVANCED = ITEMS.register("transtructor_advanced", () -> new ItemSimilsax(new Item.Properties().durability(9000)));
}
