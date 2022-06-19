package com.vorquel.similsaxtranstructors;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

class ConfigHandler {

  private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
  public static ForgeConfigSpec COMMON_CONFIG;
  public static ForgeConfigSpec.IntValue BASICRANGE;
  public static ForgeConfigSpec.IntValue ADVANCEDRANGE;
  static {
    initConfig();
  }

  private static void initConfig() {
    COMMON_BUILDER.comment("General settings").push(SimilsaxTranstructors.MODID);
    COMMON_BUILDER.push("range").comment("How far away from you this tool can build");
    BASICRANGE = COMMON_BUILDER.comment("How far you can build using the basic transtructor").defineInRange("basic", 32, 2, 128);
    ADVANCEDRANGE = COMMON_BUILDER.comment("How far you can build using the advanced transtructor").defineInRange("advanced", 128, 2, 256);
    COMMON_BUILDER.pop();
    COMMON_BUILDER.pop();
    COMMON_CONFIG = COMMON_BUILDER.build();
  }

  public static void loadConfig(ForgeConfigSpec spec, Path path) {
    final CommentedFileConfig configData = CommentedFileConfig.builder(path)
        .sync()
        .autosave()
        .writingMode(WritingMode.REPLACE)
        .build();
    configData.load();
    spec.setConfig(configData);
  }
}
