package fr.zeamateis.nuwa.contentpack.common.data;

import api.contentpack.ContentPack;
import api.contentpack.PackManager;
import api.contentpack.data.IPackData;
import fr.zeamateis.nuwa.contentpack.common.json.data.potions.PotionObject;
import fr.zeamateis.nuwa.contentpack.common.minecraft.potion.JsonPotion;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.zip.ZipFile;

/**
 * Data reading class for json representation of {@link Potion} objects
 *
 * @author ZeAmateis
 */
public class PotionsData implements IPackData {

    private LinkedList<Potion> potions;

    public PotionsData() {
        this.potions = new LinkedList<>();
    }

    /**
     * Define entry to {@link IPackData#parseData} from it
     *
     * @return the full entry folder path
     */
    @Override
    public String getEntryFolder() {
        return "objects/potions/";
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
        PotionObject potionObject = packManagerIn.getGson().fromJson(readerIn, PotionObject.class);
        ResourceLocation registryName = new ResourceLocation(contentPackIn.getNamespace(), potionObject.getRegistryName());
        EffectInstance[] effectInstances = potionObject.getEffects().stream()
                .map(effectObject ->
                        new EffectInstance(ForgeRegistries.POTIONS.getValue(new ResourceLocation(effectObject.getEffectName())), effectObject.getDuration())).toArray(EffectInstance[]::new);
        this.potions.add(new JsonPotion(potionObject.getBaseName(), registryName, effectInstances));
    }

    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return {@link LinkedList} type of {@link net.minecraftforge.registries.IForgeRegistryEntry}
     * @see net.minecraftforge.registries.ForgeRegistries
     */
    @Override
    public LinkedList<Potion> getObjectsList() {
        return this.potions;
    }
}
