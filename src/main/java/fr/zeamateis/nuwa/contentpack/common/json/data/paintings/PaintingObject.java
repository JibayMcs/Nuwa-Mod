package fr.zeamateis.nuwa.contentpack.common.json.data.paintings;

/**
 * Reprensentation of Json {@link net.minecraft.entity.item.PaintingType} object
 *
 * @author ZeAmateis
 */
public class PaintingObject {

    private String registryName;
    private int width, height;

    public String getRegistryName() {
        return registryName;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
