package api.contentpack.common.json.datas.blocks.type;

import api.contentpack.common.minecraft.blocks.JsonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;

public enum BlockType {
    DEFAULT(JsonBlock.class),
    STAIRS(StairsBlock.class),
    SLABS(SlabBlock.class),
    WALL(WallBlock.class);

    private Class<? extends Block> blockType;

    BlockType(Class<? extends Block> blockType) {
        this.blockType = blockType;
    }

    public Class<? extends Block> getBlockType() {
        return blockType;
    }
}
