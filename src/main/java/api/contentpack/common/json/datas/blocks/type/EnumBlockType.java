package api.contentpack.common.json.datas.blocks.type;

import api.contentpack.common.minecraft.blocks.*;
import api.contentpack.common.minecraft.blocks.base.JsonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public enum EnumBlockType {
    DEFAULT(JsonBlock.class, Block.Properties.from(Blocks.STONE)),
    STAIRS(JsonStairsBlock.class, Block.Properties.from(Blocks.OAK_STAIRS)),
    SLABS(JsonSlabBlock.class, Block.Properties.from(Blocks.STONE_SLAB)),
    WALL(JsonWallBlock.class, Block.Properties.from(Blocks.COBBLESTONE_WALL)),
    FALLING_BLOCK(JsonFallingBlock.class, Block.Properties.from(Blocks.SAND)),
    FENCE(JsonFenceBlock.class, Block.Properties.from(Blocks.OAK_FENCE)),
    FENCE_GATE(JsonFenceGateBlock.class, Block.Properties.from(Blocks.OAK_FENCE_GATE)),
    TRAPDOOR(JsonTrapdoorBlock.class, Block.Properties.from(Blocks.OAK_TRAPDOOR)),
    DOOR(JsonDoorBlock.class, Block.Properties.from(Blocks.OAK_DOOR)),
    FLOWER(JsonFlowerBlock.class, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT)),
    TALL_PLANT(JsonTallPlantBlock.class, Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT)),
    GRASS(JsonGrassBlock.class, Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT)),
    GLASS(JsonGlassBlock.class, Block.Properties.from(Blocks.GLASS)),
    PANE(JsonPaneBlock.class, Block.Properties.from(Blocks.IRON_BARS)),
    CARPET(JsonCarpetBlock.class, Block.Properties.from(Blocks.WHITE_CARPET)),
    CROPS(JsonCropsBlock.class, Block.Properties.from(Blocks.WHEAT)),
    LIGHT_BLOCK(JsonBlock.class, Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5F).lightValue(15)),
    SLOW_BLOCK(JsonSlowBlock.class, Block.Properties.from(Blocks.COBWEB)),
    BIOME_COLOR(JsonBiomeColorBlock.class, Block.Properties.from(Blocks.STONE));

    private Class<? extends Block> blockType;
    private Block.Properties defaultProperties;

    EnumBlockType(Class<? extends Block> blockType, Block.Properties defaultProperties) {
        this.blockType = blockType;
        this.defaultProperties = defaultProperties;
    }


    public Class<? extends Block> getBlockType() {
        return blockType;
    }

    public Block.Properties getDefaultProperties() {
        return defaultProperties;
    }
}