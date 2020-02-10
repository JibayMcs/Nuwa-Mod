package fr.zeamateis.nuwa.contentpack.common.json.data.biomes;

import net.minecraft.entity.EntityClassification;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

public class SpawnObject {

    private EntityClassification entityClassification;
    private String entity;
    private int weight;
    private int minGroupCount, maxGroupCount;
    private int minMaxGroupCount = -1;

    public EntityClassification getEntityClassification() {
        return entityClassification;
    }

    public String getEntity() {
        return entity;
    }

    public int getWeight() {
        return weight;
    }

    public int getMinGroupCount() {
        return minGroupCount;
    }

    public int getMaxGroupCount() {
        return maxGroupCount;
    }

    public Biome.SpawnListEntry getSpawnListEntry() {
        return new Biome.SpawnListEntry(ForgeRegistries.ENTITIES.getValue(new ResourceLocation(entity)), weight, minMaxGroupCount != -1 ? minMaxGroupCount : minGroupCount, minMaxGroupCount != -1 ? minMaxGroupCount : maxGroupCount);
    }
}
