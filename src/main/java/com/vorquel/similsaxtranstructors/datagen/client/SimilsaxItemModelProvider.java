package com.vorquel.similsaxtranstructors.datagen.client;

import com.vorquel.similsaxtranstructors.SimilsaxTranstructors;
import com.vorquel.similsaxtranstructors.registry.SimilsaxRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class SimilsaxItemModelProvider extends ItemModelProvider {

  public SimilsaxItemModelProvider(PackOutput packOutput, ExistingFileHelper helper) {
    super(packOutput, SimilsaxTranstructors.MODID, helper);
  }

  @Override
  protected void registerModels() {
    this.handheldItem(SimilsaxRegistry.BASIC.get());
    this.handheldItem(SimilsaxRegistry.ADVANCED.get());
  }
}
