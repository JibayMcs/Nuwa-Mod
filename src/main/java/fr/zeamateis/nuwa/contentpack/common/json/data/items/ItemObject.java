package fr.zeamateis.nuwa.contentpack.common.json.data.items;

import fr.zeamateis.nuwa.contentpack.common.json.data.items.properties.ArmorMaterialProperties;
import fr.zeamateis.nuwa.contentpack.common.json.data.items.properties.ItemsPropertiesObject;
import fr.zeamateis.nuwa.contentpack.common.json.data.items.properties.SoundProperties;
import fr.zeamateis.nuwa.contentpack.common.json.data.items.properties.ToolMaterialProperties;

/**
 * Reprensentation of Json {@link net.minecraft.item.Item} object
 *
 * @author ZeAmateis
 */
public class ItemObject {

    private ItemsPropertiesObject properties;
    private String registryName;
    private String itemGroup;
    private String itemType;

    private String cropsBlock;

    private ArmorMaterialProperties armorProperties;
    private ToolMaterialProperties toolProperties;
    private SoundProperties soundProperties;

    public String getRegistryName() {
        return registryName;
    }

    public ItemsPropertiesObject getProperties() {
        return properties;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public String getItemType() {
        return itemType;
    }

    public String getCropsBlock() {
        return cropsBlock;
    }

    public ArmorMaterialProperties getArmorProperties() {
        return armorProperties;
    }

    public ToolMaterialProperties getToolProperties() {
        return toolProperties;
    }

    public SoundProperties getSoundProperties() {
        return soundProperties;
    }
}
