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

public class PotionsData implements IPackData {

    private LinkedList<Potion> potions;

    public PotionsData() {
        this.potions = new LinkedList<>();
    }

    /**
     * Define entry to {@link IPackData#parseData} from it
     *
     * @return String
     */
    @Override
    public String getEntryFolder() {
        return "objects/potions/";
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
        PotionObject potionObject = packManagerIn.getGson().fromJson(readerIn, PotionObject.class);
        ResourceLocation registryName = new ResourceLocation(contentPackIn.getNamespace(), potionObject.getRegistryName());
        String baseName = potionObject.getBaseName() != null ? potionObject.getBaseName() : potionObject.getRegistryName();
        EffectInstance[] effectInstances = potionObject.getEffects().stream()
                .map(effectObject ->
                        new EffectInstance(ForgeRegistries.POTIONS.getValue(new ResourceLocation(effectObject.getEffectName())), effectObject.getDuration())).toArray(EffectInstance[]::new);
        this.potions.add(new JsonPotion(baseName, registryName, effectInstances));
    }

    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return LinkedList<? extends IForgeRegistryEntry>
     * @see ForgeRegistries
     */
    @Override
    public LinkedList<Potion> getObjectsList() {
        return this.potions;
    }
}
