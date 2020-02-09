package fr.zeamateis.nuwa.init;


import fr.zeamateis.nuwa.Constant;
import fr.zeamateis.nuwa.contentpack.common.minecraft.blocks.base.IJsonBlock;
import fr.zeamateis.nuwa.contentpack.common.minecraft.items.base.IJsonItem;
import fr.zeamateis.nuwa.contentpack.common.minecraft.registries.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(modid = Constant.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class NuwaRegistries {
    public static final IForgeRegistry<ArmorMaterialType> ARMOR_MATERIAL = new RegistryBuilder<ArmorMaterialType>()
            .setName(new ResourceLocation(Constant.MODID, "armor_material"))
            .setType(ArmorMaterialType.class)
            .setIDRange(0, Integer.MAX_VALUE)
            .create();

    public static final IForgeRegistry<ToolMaterialType> TOOL_MATERIAL = new RegistryBuilder<ToolMaterialType>()
            .setName(new ResourceLocation(Constant.MODID, "tool_material"))
            .setType(ToolMaterialType.class)
            .setIDRange(0, Integer.MAX_VALUE)
            .create();


    public static final IForgeRegistry<ProcessType> PROCESS = new RegistryBuilder<ProcessType>()
            .setName(new ResourceLocation(Constant.MODID, "process"))
            .setType(ProcessType.class)
            .setIDRange(0, Integer.MAX_VALUE)
            .create();

    public static final IForgeRegistry<ItemGroupType> ITEM_GROUP = new RegistryBuilder<ItemGroupType>()
            .setName(new ResourceLocation(Constant.MODID, "item_group"))
            .setType(ItemGroupType.class)
            .setIDRange(0, Integer.MAX_VALUE)
            .create();

    public static final IForgeRegistry<BlockEventType> BLOCK_EVENT = new RegistryBuilder<BlockEventType>()
            .setName(new ResourceLocation(Constant.MODID, "block_events"))
            .setType(BlockEventType.class)
            .setIDRange(0, Integer.MAX_VALUE)
            .create();

    public static final IForgeRegistry<ContainerType> CONTAINER = new RegistryBuilder<ContainerType>()
            .setName(new ResourceLocation(Constant.MODID, "container"))
            .setType(ContainerType.class)
            .setIDRange(0, Integer.MAX_VALUE)
            .create();

    @SubscribeEvent
    public static void onMissingToolMaterial(RegistryEvent.MissingMappings<ToolMaterialType> event) {
        for (RegistryEvent.MissingMappings.Mapping<ToolMaterialType> mapping : event.getAllMappings()) {
            mapping.ignore();
        }
    }

    @SubscribeEvent
    public static void onMissingArmorMaterial(RegistryEvent.MissingMappings<ArmorMaterialType> event) {
        for (RegistryEvent.MissingMappings.Mapping<ArmorMaterialType> mapping : event.getAllMappings()) {
            mapping.ignore();
        }
    }

    @SubscribeEvent
    public static void onMissingItem(RegistryEvent.MissingMappings<Item> event) {
        for (RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getAllMappings()) {
            mapping.registry.getValues().stream().filter(item -> item instanceof IJsonItem)
                    .forEach(item -> {
                        mapping.ignore();
                    });
        }
    }

    @SubscribeEvent
    public static void onMissingBlock(RegistryEvent.MissingMappings<Block> event) {
        for (RegistryEvent.MissingMappings.Mapping<Block> mapping : event.getAllMappings()) {
            mapping.registry.getValues().stream().filter(item -> item instanceof IJsonBlock)
                    .forEach(item -> {
                        mapping.ignore();
                    });
        }
    }
}