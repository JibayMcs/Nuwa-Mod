package api.contentpack.common.json.datas.blocks.type;

import api.contentpack.common.minecraft.blocks.*;
import net.minecraft.block.Block;

public enum BlockType {
    DEFAULT(JsonBlock.class),
    STAIRS(JsonStairsBlock.class),
    SLABS(JsonSlabBlock.class),
    WALL(JsonWallBlock.class),
    FALLING_BLOCK(JsonFallingBlock.class),
    FENCE(JsonFenceBlock.class),
    FENCE_GATE(JsonFenceGateBlock.class),
    TRAPDOOR(JsonTrapdoorBlock.class),
    DOOR(JsonDoorBlock.class),
    FLOWER(JsonFlowerBlock.class),
    TALL_PLANT(JsonTallFlowerBlock.class),
    GRASS(JsonGrassBlock.class);

    private Class<? extends Block> blockType;

    BlockType(Class<? extends Block> blockType) {
        this.blockType = blockType;
    }

    public Class<? extends Block> getBlockType() {
        return blockType;
    }
}
