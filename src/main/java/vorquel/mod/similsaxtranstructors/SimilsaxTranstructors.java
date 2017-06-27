package vorquel.mod.similsaxtranstructors;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static vorquel.mod.similsaxtranstructors.Config.showOverlay;

@Mod(modid = SimilsaxTranstructors.MOD_ID, name = "Similsax Transtructors", updateJSON = "https://raw.githubusercontent.com/PrinceOfAmber/Similsax-Transtructors/master/update.json")
public class SimilsaxTranstructors {
  public static final String MOD_ID = "similsaxtranstructors";
  public static final ItemSimilsaxTranstructor itemBasic = new ItemSimilsaxTranstructor("Basic");
  public static final ItemSimilsaxTranstructor itemAdvanced = new ItemSimilsaxTranstructor("Advanced");
  @SidedProxy(clientSide = "vorquel.mod.similsaxtranstructors.ClientProxy", serverSide = "vorquel.mod.similsaxtranstructors.Proxy")
  static Proxy proxy;
  static Logger log = LogManager.getLogger(MOD_ID);
  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    Config.init(event.getSuggestedConfigurationFile());
    ConfigSynchonizer.init();
    
    
//    
//    RegistryThing.register(itemBasic.setRegistryName(new ResourceLocation(MOD_ID, "similsaxTranstructorBasic")));
//  
//    RegistryThing.register(  itemAdvanced.setRegistryName(new ResourceLocation(MOD_ID, "similsaxTranstructorAdvanced")));
    proxy.registerItemModel();
    

    MinecraftForge.EVENT_BUS.register(RegistryThing.class);
  }
  @Mod.EventHandler
  public void init(FMLInitializationEvent event) {
    if (showOverlay){ proxy.registerBlockOverlay();}
    MinecraftForge.EVENT_BUS.register(new ConfigSynchonizer());

  }
  @Mod.EventHandler
  public void serverStarting(FMLServerStartingEvent event) {
    log.info("Syncing Server configs");
    itemBasic.setUses(Config.basicUses);
    itemAdvanced.setUses(Config.advancedUses);
    itemBasic.range = Config.basicRange;
    itemAdvanced.range = Config.advancedRange;
  }
}
