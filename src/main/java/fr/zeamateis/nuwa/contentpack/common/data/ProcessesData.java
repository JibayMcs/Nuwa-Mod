package fr.zeamateis.nuwa.contentpack.common.data;

import api.contentpack.ContentPack;
import api.contentpack.PackManager;
import api.contentpack.data.IData;
import fr.zeamateis.nuwa.contentpack.common.json.data.events.ProcessObject;
import fr.zeamateis.nuwa.contentpack.common.minecraft.registries.ProcessType;
import net.minecraft.util.ResourceLocation;

import java.util.LinkedList;

public class ProcessesData implements IData {

    private LinkedList<ProcessType> processTypes;

    public ProcessesData() {
        this.processTypes = new LinkedList<>();
    }


    /**
     * Use {@link PackManager}
     * instance to parse datas from Content Pack zip file
     *
     * @param packManagerIn The {@link PackManager} instance
     * @param contentPackIn The {@link ContentPack} instance
     */
    @Override
    public void parseData(PackManager packManagerIn, ContentPack contentPackIn) {
        String processPackage = "fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.";
        this.processTypes.add(new ProcessType(new ProcessObject(processPackage + "AttackProcess")).setRegistryName(new ResourceLocation("nuwa:attack_process")));
        this.processTypes.add(new ProcessType(new ProcessObject(processPackage + "HealProcess")).setRegistryName(new ResourceLocation("nuwa:heal_process")));
        this.processTypes.add(new ProcessType(new ProcessObject(processPackage + "TeleportProcess")).setRegistryName(new ResourceLocation("nuwa:teleport_process")));
        this.processTypes.add(new ProcessType(new ProcessObject(processPackage + "SummonProcess")).setRegistryName(new ResourceLocation("nuwa:summon_process")));
        this.processTypes.add(new ProcessType(new ProcessObject(processPackage + "InventoryProcess")).setRegistryName(new ResourceLocation("nuwa:inventory_process")));
        this.processTypes.add(new ProcessType(new ProcessObject(processPackage + "ExperienceProcess")).setRegistryName(new ResourceLocation("nuwa:experience_process")));
        this.processTypes.add(new ProcessType(new ProcessObject(processPackage + "PlaySoundProcess")).setRegistryName(new ResourceLocation("nuwa:sound_process")));
        this.processTypes.add(new ProcessType(new ProcessObject(processPackage + "EffectProcess")).setRegistryName(new ResourceLocation("nuwa:effect_process")));
        this.processTypes.add(new ProcessType(new ProcessObject(processPackage + "SetOnFireProcess")).setRegistryName(new ResourceLocation("nuwa:set_on_fire_process")));
        this.processTypes.add(new ProcessType(new ProcessObject(processPackage + "MovementSpeedProcess")).setRegistryName(new ResourceLocation("nuwa:movement_speed_process")));
    }

    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return {@link LinkedList} type of {@link net.minecraftforge.registries.IForgeRegistryEntry}
     * @see net.minecraftforge.registries.ForgeRegistries
     */
    @Override
    public LinkedList<ProcessType> getObjectsList() {
        return this.processTypes;
    }

}
