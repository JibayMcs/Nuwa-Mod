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
        ProcessType attackProcess = new ProcessType(new ProcessObject("fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.AttackProcess", "nuwa:attack_process"));
        ProcessType healProcess = new ProcessType(new ProcessObject("fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.HealProcess", "nuwa:heal_process"));
        ProcessType giveItemProcess = new ProcessType(new ProcessObject("fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.GiveItemProcess", "nuwa:give_item_process"));
        ProcessType teleportProcess = new ProcessType(new ProcessObject("fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.TeleportProcess", "nuwa:teleport_process"));
        this.processTypes.add(attackProcess);
        this.processTypes.add(healProcess);
        this.processTypes.add(giveItemProcess);
        this.processTypes.add(teleportProcess);
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
