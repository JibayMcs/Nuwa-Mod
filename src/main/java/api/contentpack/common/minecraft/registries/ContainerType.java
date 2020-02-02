package api.contentpack.common.minecraft.registries;

import api.contentpack.common.json.datas.containers.ContainersObject;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

public class ContainerType implements IForgeRegistryEntry<ContainerType> {

    private final ContainersObject containersObject;
    private ResourceLocation registryName;

    public ContainerType(ContainersObject containersObject) {
        this.setRegistryName(new ResourceLocation(containersObject.getRegistryName()));
        this.containersObject = containersObject;
    }

    public ContainersObject getContainersObject() {
        return containersObject;
    }

    /**
     * A unique identifier for this entry, if this entry is registered already it will return it's official registry name.
     * Otherwise it will return the name set in setRegistryName().
     * If neither are valid null is returned.
     *
     * @return Unique identifier or null.
     */
    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return registryName;
    }

    /**
     * Sets a unique name for this Item. This should be used for uniquely identify the instance of the Item.
     * This is the valid replacement for the atrocious 'getUnlocalizedName().substring(6)' stuff that everyone does.
     * Unlocalized names have NOTHING to do with unique identifiers. As demonstrated by vanilla blocks and items.
     * <p>
     * The supplied name will be prefixed with the currently active mod's modId.
     * If the supplied name already has a prefix that is different, it will be used and a warning will be logged.
     * <p>
     * If a name already exists, or this Item is already registered in a registry, then an IllegalStateException is thrown.
     * <p>
     * Returns 'this' to allow for chaining.
     *
     * @param name Unique registry name
     * @return This instance
     */
    @Override
    public ContainerType setRegistryName(ResourceLocation name) {
        this.registryName = name;
        return this;
    }

    /**
     * Determines the type for this entry, used to look up the correct registry in the global registries list as there can only be one
     * registry per concrete class.
     *
     * @return Root registry type.
     */
    @Override
    public Class<ContainerType> getRegistryType() {
        return ContainerType.class;
    }
}
