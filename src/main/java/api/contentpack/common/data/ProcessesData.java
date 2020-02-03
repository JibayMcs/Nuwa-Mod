package api.contentpack.common.data;

import api.contentpack.common.ContentPack;
import api.contentpack.common.PackManager;
import api.contentpack.common.data.base.IData;
import api.contentpack.common.data.base.IPackData;
import api.contentpack.common.json.datas.events.processes.ProcessObject;
import api.contentpack.common.minecraft.registries.ProcessType;
import fr.zeamateis.nuwa.init.NuwaRegistries;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

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
        ProcessType attackProcess = new ProcessType(new ProcessObject("api.contentpack.common.json.datas.blocks.events.processes.AttackProcess", "nuwa:attack_process"));
        ProcessType healProcess = new ProcessType(new ProcessObject("api.contentpack.common.json.datas.blocks.events.processes.HealProcess", "nuwa:heal_process"));
        ProcessType giveItemProcess = new ProcessType(new ProcessObject("api.contentpack.common.json.datas.blocks.events.processes.GiveItemProcess", "nuwa:give_item_process"));
        ProcessType teleportProcess = new ProcessType(new ProcessObject("api.contentpack.common.json.datas.blocks.events.processes.TeleportProcess", "nuwa:teleport_process"));
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

    /**
     * Link {@link IPackData#getObjectsList()} to the correct Forge Registry
     *
     * @return IForgeRegistry
     * @see ForgeRegistries
     */
    @Override
    public IForgeRegistry<ProcessType> getObjectsRegistry() {
        return NuwaRegistries.PROCESS;
    }
}
