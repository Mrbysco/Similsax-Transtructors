package com.vorquel.similsaxtranstructors.registry;

import com.vorquel.similsaxtranstructors.SimilsaxTranstructors;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ConfigHandlerST {

  public static ModConfigSpec CONFIG;
  public static ModConfigSpec.IntValue BASICRANGE;
  public static ModConfigSpec.IntValue ADVANCEDRANGE;
  static {
    final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    BUILDER.comment("General settings").push(SimilsaxTranstructors.MODID);
    BUILDER.push("range").comment("How far away from you this tool can build");
    BASICRANGE = BUILDER.comment("How far you can build using the basic transtructor").defineInRange("basic", 32, 2, 128);
    ADVANCEDRANGE = BUILDER.comment("How far you can build using the advanced transtructor").defineInRange("advanced", 128, 2, 256);
    BUILDER.pop();
    BUILDER.pop();
    CONFIG = BUILDER.build();
  }
}
