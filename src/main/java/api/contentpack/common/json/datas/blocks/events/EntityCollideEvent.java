package api.contentpack.common.json.datas.blocks.events;

import api.contentpack.common.json.datas.blocks.events.actions.base.IProcess;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.stream.Collectors;

public class EntityCollideEvent {

    private List<String> affectedEntities;
    private List<IProcess> processes;

    public EntityCollideEvent(List<String> affectedEntities, List<IProcess> processes) {
        this.affectedEntities = affectedEntities;
        this.processes = processes;
    }

    public List<EntityType<?>> getAffectedEntities() {
        return affectedEntities
                .stream().map(ResourceLocation::new).collect(Collectors.toList())
                .stream().map(ForgeRegistries.ENTITIES::getValue).collect(Collectors.toList());
    }

    public List<IProcess> getProcesses() {
        return processes;
    }
}
