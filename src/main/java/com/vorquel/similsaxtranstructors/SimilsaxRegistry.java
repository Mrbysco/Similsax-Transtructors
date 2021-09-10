package com.vorquel.similsaxtranstructors;

import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SimilsaxRegistry {

  @ObjectHolder(SimilsaxTranstructors.MODID + ":transtructor_basic")
  public static Item basic;
  @ObjectHolder(SimilsaxTranstructors.MODID + ":transtructor_advanced")
  public static Item advanced;

  @SubscribeEvent
  public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
    event.getRegistry().register(new ItemSimilsax(new Item.Properties().stacksTo(1).durability(800)).setRegistryName("transtructor_basic"));
    event.getRegistry().register(new ItemSimilsax(new Item.Properties().stacksTo(1).durability(9000)).setRegistryName("transtructor_advanced"));
  }
}
