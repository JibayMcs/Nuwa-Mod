package fr.zeamateis.nuwa.contentpack.common.data;

import api.contentpack.ContentPack;
import api.contentpack.PackManager;
import api.contentpack.data.IData;
import fr.zeamateis.nuwa.contentpack.common.json.data.events.ProcessObject;
import fr.zeamateis.nuwa.contentpack.common.minecraft.registries.ProcessType;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.zip.ZipFile;

public class ProcessesData implements IData {

    private LinkedList<ProcessType> processTypes;

    public ProcessesData() {
        this.processTypes = new LinkedList<>();
    }

    /**
     * Use {@link ContentPack}, {@link ZipFile} and {@link InputStreamReader}
     * instances to parse datas from Content Pack zip file
     *
     * @param packManagerIn
     */
    @Override
    public void parseData(PackManager packManagerIn) {
        String processPackage = "fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.";
        this.processTypes.add(new ProcessType(new ProcessObject(processPackage + "AttackProcess", "nuwa:attack_process")));
        this.processTypes.add(new ProcessType(new ProcessObject(processPackage + "HealProcess", "nuwa:heal_process")));
        this.processTypes.add(new ProcessType(new ProcessObject(processPackage + "TeleportProcess", "nuwa:teleport_process")));
        this.processTypes.add(new ProcessType(new ProcessObject(processPackage + "SummonProcess", "nuwa:summon_process")));
        this.processTypes.add(new ProcessType(new ProcessObject(processPackage + "InventoryProcess", "nuwa:inventory_process")));
        this.processTypes.add(new ProcessType(new ProcessObject(processPackage + "ExperienceProcess", "nuwa:experience_process")));
        this.processTypes.add(new ProcessType(new ProcessObject(processPackage + "PlaySoundProcess", "nuwa:sound_process")));
        this.processTypes.add(new ProcessType(new ProcessObject(processPackage + "EffectProcess", "nuwa:effect_process")));
    }

    /**
     * Define objects list injectable in the Forge Registry System
     * to register it
     *
     * @return LinkedList<? extends IForgeRegistryEntry>
     * @see ForgeRegistries
     */
    @Override
    public LinkedList<ProcessType> getObjectsList() {
        return this.processTypes;
    }

}
