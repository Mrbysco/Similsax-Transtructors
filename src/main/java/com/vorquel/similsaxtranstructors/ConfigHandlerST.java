package com.vorquel.similsaxtranstructors;

import com.lothrazar.library.config.ConfigTemplate;
import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigHandlerST extends ConfigTemplate {

  public static ForgeConfigSpec CONFIG;
  public static ForgeConfigSpec.IntValue BASICRANGE;
  public static ForgeConfigSpec.IntValue ADVANCEDRANGE;
  static {
    final ForgeConfigSpec.Builder BUILDER = builder();
    BUILDER.comment("General settings").push(SimilsaxTranstructors.MODID);
    BUILDER.push("range").comment("How far away from you this tool can build");
    BASICRANGE = BUILDER.comment("How far you can build using the basic transtructor").defineInRange("basic", 32, 2, 128);
    ADVANCEDRANGE = BUILDER.comment("How far you can build using the advanced transtructor").defineInRange("advanced", 128, 2, 256);
    BUILDER.pop();
    BUILDER.pop();
    CONFIG = BUILDER.build();
  }

  public ConfigHandlerST() {
    CONFIG.setConfig(setup(SimilsaxTranstructors.MODID));
  }
}
