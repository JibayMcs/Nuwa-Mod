package api.contentpack.common.data;

import api.contentpack.client.itemGroup.ItemGroups;
import api.contentpack.client.itemGroup.JsonItemGroup;
import api.contentpack.common.ContentPack;
import api.contentpack.common.IPackData;
import api.contentpack.common.PackManager;
import api.contentpack.common.json.datas.itemGroups.ItemGroupObject;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.zip.ZipFile;

public class ItemGroupData implements IPackData {

    @Override
    public String getEntryFolder() {
        return "objects/itemgroup/index.json";
    }

    @Override
    public void parseData(ContentPack contentPackIn, ZipFile zipFileIn, InputStreamReader readerIn) {
        ItemGroupObject itemGroupObject = PackManager.GSON.fromJson(readerIn, ItemGroupObject.class);

        if (itemGroupObject != null) {
            String formatedId = String.format("%s.%s", itemGroupObject.getId().getNamespace(), itemGroupObject.getId().getPath());

            ItemGroup parsedItemGroup = new JsonItemGroup(formatedId, itemGroupObject.hasNoTitle(), itemGroupObject.hasSearchBar(), itemGroupObject.hasScrollBar(), itemGroupObject.getBackgroundImage()) {
                @OnlyIn(Dist.CLIENT)
                @Override
                public ItemStack createIcon() {
                    return ForgeRegistries.ITEMS.getValue(itemGroupObject.getIcon()).getDefaultInstance();
                }
            };
            ItemGroups.putItemGroup(itemGroupObject.getId(), parsedItemGroup);
        }
    }

    @Override
    public LinkedList<? extends ForgeRegistryEntry> getObjectsList() {
        return null;
    }

    @Override
    public IForgeRegistry getObjectsRegistry() {
        return null;
    }
}
