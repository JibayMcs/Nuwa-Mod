package api.contentpack.common.json.datas.blocks.properties;

import net.minecraft.block.SoundType;

enum SoundTypeObject {
    WOOD(SoundType.WOOD),
    GROUND(SoundType.GROUND),
    PLANT(SoundType.PLANT),
    STONE(SoundType.STONE),
    METAL(SoundType.METAL),
    GLASS(SoundType.GLASS),
    CLOTH(SoundType.CLOTH),
    SAND(SoundType.SAND),
    SNOW(SoundType.SNOW),
    LADDER(SoundType.LADDER),
    ANVIL(SoundType.ANVIL),
    SLIME(SoundType.SLIME),
    WET_GRASS(SoundType.WET_GRASS),
    CORAL(SoundType.CORAL),
    BAMBOO(SoundType.BAMBOO),
    BAMBOO_SAPLING(SoundType.BAMBOO_SAPLING),
    SCAFFOLDING(SoundType.SCAFFOLDING),
    SWEET_BERRY_BUSH(SoundType.SWEET_BERRY_BUSH),
    CROP(SoundType.CROP),
    STEM(SoundType.STEM),
    NETHER_WART(SoundType.NETHER_WART),
    LANTERN(SoundType.LANTERN);

    private SoundType soundType;

    SoundTypeObject(SoundType soundType) {
        this.soundType = soundType;
    }

    public SoundType getSoundType() {
        return soundType;
    }
}