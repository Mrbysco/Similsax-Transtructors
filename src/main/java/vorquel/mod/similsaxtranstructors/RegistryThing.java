package vorquel.mod.similsaxtranstructors;
import static vorquel.mod.similsaxtranstructors.SimilsaxTranstructors.MOD_ID;
import static vorquel.mod.similsaxtranstructors.SimilsaxTranstructors.itemAdvanced;
import static vorquel.mod.similsaxtranstructors.SimilsaxTranstructors.itemBasic;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.apache.commons.lang3.tuple.Pair;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryThing {
  @SubscribeEvent
  public static void onRegistryEvent(RegistryEvent.Register<Item> event) {
    event.getRegistry().register(SimilsaxTranstructors.itemBasic.setRegistryName(new ResourceLocation(SimilsaxTranstructors.MOD_ID, "similsaxtranstructorbasic")));
    event.getRegistry().register(SimilsaxTranstructors.itemAdvanced.setRegistryName(new ResourceLocation(SimilsaxTranstructors.MOD_ID, "similsaxtranstructoradvanced")));
  }
  @SubscribeEvent
  public static void registerModels(ModelRegistryEvent event) {
    ModelLoader.setCustomModelResourceLocation(itemBasic, 0, new ModelResourceLocation(MOD_ID + ":similsaxtranstructorbasic", "inventory"));
    ModelLoader.setCustomModelResourceLocation(itemAdvanced, 0, new ModelResourceLocation(MOD_ID + ":similsaxtranstructoradvanced", "inventory"));
  }
}
