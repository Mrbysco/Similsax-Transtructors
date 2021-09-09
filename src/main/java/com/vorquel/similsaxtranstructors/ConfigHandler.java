package com.vorquel.similsaxtranstructors;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import java.nio.file.Path;

class ConfigHandler {

  public static int basicUses;
  public static int advancedUses;
  public static int basicRange;
  public static int advancedRange;
  public static boolean showOverlay;
  private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
  public static ForgeConfigSpec COMMON_CONFIG;
  public static ForgeConfigSpec.BooleanValue SHOW_OVERLAY;
  public static ForgeConfigSpec.IntValue BASICUSES;
  public static ForgeConfigSpec.IntValue ADVANCEDUSES;
  public static ForgeConfigSpec.IntValue BASICRANGE;
  public static ForgeConfigSpec.IntValue ADVANCEDRANGE;
  static {
    initConfig();
  }

  private static void initConfig() {
    COMMON_BUILDER.comment("General settings").push(SimilsaxTranstructors.MODID);
    SHOW_OVERLAY = COMMON_BUILDER.comment("Should there be an overlay to show transtructor function? ")
        .define("showOverlay", true);
    //    basicUses = config.getInt("basicUses", "general", 200, 1, Short.MAX_VALUE, "How many times you can use the basic transtructor");
    //    advancedUses = config.getInt("advancedUses", "general", 1000, 1, Short.MAX_VALUE, "How many times you can use the advanced transtructor");
    //    basicRange = config.getInt("basicRange", "general", 16, 2, 128, "How far you can use the basic transtructor");
    //    advancedRange = config.getInt("advancedRange", "general", 64, 2, 128, "How far you can use the advanced transtructor");
    //    showOverlay = config.getBoolean("showOverlay", "general", true, "Should there be an overlay to show transtructor function?");
    //    TOOLTIPS = COMMON_BUILDER.comment("Bonemeal Tooltip").define("itemTooltip", true);
    //    GRASS_MIDNIGHT = COMMON_BUILDER.comment("Allows bonemeal to work during midnight, "
    //        + "but only on grass (to grow flowers, maybe a rare firework).  "
    //        + "If false it just never works on grass just like other blocks. ")
    //        .define("grassMidnight", true);
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
