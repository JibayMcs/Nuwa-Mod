package fr.zeamateis.nuwa.contentpack.common.data;

import api.contentpack.ContentPack;
import api.contentpack.PackManager;
import api.contentpack.data.IPackData;
import fr.zeamateis.nuwa.contentpack.client.json.data.itemGroups.ItemGroupObject;
import fr.zeamateis.nuwa.contentpack.common.minecraft.registries.ItemGroupType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.zip.ZipFile;

public class ItemGroupData implements IPackData {

    private LinkedList<ItemGroupType> itemGroupTypes;

    public ItemGroupData() {
        this.itemGroupTypes = new LinkedList<>();
    }

    @Override
    public String getEntryFolder() {
        return "objects/itemgroup/";
    }

    /**
     * Use {@link PackManager}, {@link ContentPack}, {@link ZipFile} and {@link InputStreamReader}
     * instances to parse datas from Content Pack zip file
     *
     * @param packManagerIn
     * @param contentPackIn
     * @param zipFileIn
     * @param readerIn
     */
    @Override
    public void parseData(PackManager packManagerIn, ContentPack contentPackIn, ZipFile zipFileIn, InputStreamReader readerIn) {
        ItemGroupObject itemGroupObject = packManagerIn.getGson().fromJson(readerIn, ItemGroupObject.class);

        String label = String.format("%s.%s", itemGroupObject.getRegistryName().getNamespace(), itemGroupObject.getRegistryName().getPath());

        this.itemGroupTypes.add(new ItemGroupType(itemGroupObject.getRegistryName(), new ItemGroup(label) {
            @Override
            public ItemStack createIcon() {
                return ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemGroupObject.getItemIcon())).getDefaultInstance();
            }

            @Override
            public boolean hasSearchBar() {
                return itemGroupObject.hasSearchBar();
            }

            @Override
            public String getBackgroundImageName() {
                return this.hasSearchBar() ? "item_search.png" : itemGroupObject.getBackgroundImage() != null ? itemGroupObject.getBackgroundImage() : super.getBackgroundImageName();
            }

            @Override
            public boolean hasScrollbar() {
                return itemGroupObject.hasScrollBar();
            }

            @Override
            public ItemGroup setNoTitle() {
                return itemGroupObject.hasNoTitle() ? super.setNoTitle() : this;
            }
        }));

    }

    @Override
    public LinkedList<ItemGroupType> getObjectsList() {
        return this.itemGroupTypes;
    }

}
