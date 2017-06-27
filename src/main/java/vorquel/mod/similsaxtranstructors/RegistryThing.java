package vorquel.mod.similsaxtranstructors;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.apache.commons.lang3.tuple.Pair;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryThing {
  //forge made some breaking api changes so we need this thing
  //https://twitter.com/amadornes/status/879013891937906689
  //https://gist.github.com/amadornes/d14422f56e3626c938100e83dcb32cfd
//  private static Multimap<Class<?>, Pair<IForgeRegistryEntry<?>, List<Runnable>>> registryObjects = MultimapBuilder.hashKeys()
//      .arrayListValues().build();
//  public static Consumer<Runnable> register(IForgeRegistryEntry<?> object) {
//    List<Runnable> list = new ArrayList<>();
//    registryObjects.put(object.getRegistryType(), Pair.of(object, list));
//    return list::add;
//  }
//  @SuppressWarnings({ "unchecked", "rawtypes" })
  @SubscribeEvent
  public static void onRegistryEvent(RegistryEvent.Register<Item> event) {
    
    

    event.getRegistry().register(SimilsaxTranstructors.itemBasic.setRegistryName(new ResourceLocation(SimilsaxTranstructors.MOD_ID, "similsaxTranstructorBasic")));
  
    event.getRegistry().register(  SimilsaxTranstructors.itemAdvanced.setRegistryName(new ResourceLocation(SimilsaxTranstructors.MOD_ID, "similsaxTranstructorAdvanced")));
    
    
//    IForgeRegistry reg = event.getRegistry();
//    for (Pair<IForgeRegistryEntry<?>, List<Runnable>> pair : registryObjects.get(reg.getRegistrySuperType())) {
//      
//      System.out.println("key  "+pair.getKey());
//      
//      reg.register(pair.getKey());
//      pair.getValue().forEach(Runnable::run);
//    }
  }
}
