package com.vorquel.similsaxtranstructors.registry;

import com.vorquel.similsaxtranstructors.SimilsaxTranstructors;
import com.vorquel.similsaxtranstructors.item.ItemSimilsax;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SimilsaxRegistry {

  public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SimilsaxTranstructors.MODID);
  public static final DeferredItem<ItemSimilsax> BASIC = ITEMS.registerItem("transtructor_basic", ItemSimilsax::new, properties -> properties.durability(800));
  public static final DeferredItem<ItemSimilsax> ADVANCED = ITEMS.registerItem("transtructor_advanced", ItemSimilsax::new, properties -> properties.durability(9000));
}
