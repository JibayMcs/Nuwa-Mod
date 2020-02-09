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
        ProcessType attackProcess = new ProcessType(new ProcessObject(processPackage + "AttackProcess", "nuwa:attack_process"));
        ProcessType healProcess = new ProcessType(new ProcessObject(processPackage + "HealProcess", "nuwa:heal_process"));
        ProcessType teleportProcess = new ProcessType(new ProcessObject(processPackage + "TeleportProcess", "nuwa:teleport_process"));
        ProcessType summonProcess = new ProcessType(new ProcessObject(processPackage + "SummonProcess", "nuwa:summon_process"));
        ProcessType inventoryProcess = new ProcessType(new ProcessObject(processPackage + "InventoryProcess", "nuwa:inventory_process"));
        ProcessType experienceProcess = new ProcessType(new ProcessObject(processPackage + "ExperienceProcess", "nuwa:experience_process"));
        ProcessType playSoundProcess = new ProcessType(new ProcessObject(processPackage + "PlaySoundProcess", "nuwa:sound_process"));
        this.processTypes.add(attackProcess);
        this.processTypes.add(healProcess);
        this.processTypes.add(teleportProcess);
        this.processTypes.add(summonProcess);
        this.processTypes.add(inventoryProcess);
        this.processTypes.add(experienceProcess);
        this.processTypes.add(playSoundProcess);
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
