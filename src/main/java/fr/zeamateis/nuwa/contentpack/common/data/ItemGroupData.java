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
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.zip.ZipFile;

/**
 * Data reading class for json representation of {@link ItemGroup} objects
 *
 * @author ZeAmateis
 */
public class ItemGroupData implements IPackData {

    private LinkedList<ItemGroupType> itemGroupTypes;

    public ItemGroupData() {
        this.itemGroupTypes = new LinkedList<>();
    }

    /**
     * Define entry to {@link IPackData#parseData} from it
     *
     * @return the full entry folder path
     */
    @Override
    public String getEntryFolder() {
        return "objects/itemgroup/";
    }

    /**
     * Use {@link PackManager}, {@link ContentPack}, {@link ZipFile} and {@link InputStreamReader}
     * instances to parse datas from Content Pack
     *
     * @param packManagerIn The {@link PackManager} instance
     * @param contentPackIn The {@link ContentPack} instance
     * @param readerIn      The {@link InputStreamReader} instance
     */
    @Override
    public void parseData(PackManager packManagerIn, ContentPack contentPackIn, InputStreamReader readerIn) {
        ItemGroupObject itemGroupObject = packManagerIn.getGson().fromJson(readerIn, ItemGroupObject.class);

        ResourceLocation registryName = new ResourceLocation(contentPackIn.getNamespace(), itemGroupObject.getRegistryName());

        String label = String.format("%s.%s", contentPackIn.getNamespace(), registryName.getPath());

        this.itemGroupTypes.add(new ItemGroupType(new ItemGroup(label) {
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
        }).setRegistryName(registryName));

    }

    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return {@link LinkedList} type of {@link IForgeRegistryEntry}
     * @see net.minecraftforge.registries.ForgeRegistries
     */
    @Override
    public LinkedList<ItemGroupType> getObjectsList() {
        return this.itemGroupTypes;
    }

}
