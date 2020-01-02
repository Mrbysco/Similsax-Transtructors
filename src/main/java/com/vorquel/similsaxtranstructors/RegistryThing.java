package com.vorquel.similsaxtranstructors;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class RegistryThing {

  public static ItemGroup itemGroup = new ItemGroup(SimilsaxTranstructors.MODID) {

    @Override
    public ItemStack createIcon() {
      return new ItemStack(Blocks.DIRT);
    }
  };
}
