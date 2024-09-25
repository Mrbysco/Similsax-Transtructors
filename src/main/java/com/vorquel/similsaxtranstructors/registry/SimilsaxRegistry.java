package com.vorquel.similsaxtranstructors.registry;

import com.vorquel.similsaxtranstructors.ItemSimilsax;
import com.vorquel.similsaxtranstructors.SimilsaxTranstructors;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SimilsaxRegistry {

  public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SimilsaxTranstructors.MODID);
  public static final DeferredItem<ItemSimilsax> BASIC = ITEMS.register("transtructor_basic", () -> new ItemSimilsax(new Item.Properties().durability(800)));
  public static final DeferredItem<ItemSimilsax> ADVANCED = ITEMS.register("transtructor_advanced", () -> new ItemSimilsax(new Item.Properties().durability(9000)));
}
