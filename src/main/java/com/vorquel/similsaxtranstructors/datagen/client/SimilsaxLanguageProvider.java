package com.vorquel.similsaxtranstructors.datagen.client;

import com.vorquel.similsaxtranstructors.SimilsaxTranstructors;
import com.vorquel.similsaxtranstructors.registry.SimilsaxRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class SimilsaxLanguageProvider extends LanguageProvider {

  public SimilsaxLanguageProvider(PackOutput packOutput) {
    super(packOutput, SimilsaxTranstructors.MODID, "en_us");
  }

  @Override
  protected void addTranslations() {
    this.addItem(SimilsaxRegistry.BASIC, "Basic Transtructor");
    this.add("item.similsaxtranstructors.transtructor_basic.tooltip", "Places matching blocks in any direction");
    this.addItem(SimilsaxRegistry.ADVANCED, "Advanced Transtructor");
    this.add("item.similsaxtranstructors.transtructor_advanced.tooltip", "Places matching blocks in any direction");
  }
}
