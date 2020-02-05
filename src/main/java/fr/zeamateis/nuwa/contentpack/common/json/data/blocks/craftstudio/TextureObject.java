package fr.zeamateis.nuwa.contentpack.common.json.data.blocks.craftstudio;

public class TextureObject {

    private int width, height, size;
    private String name;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "TextureObject{" +
                "width=" + width +
                ", height=" + height +
                ", size=" + size +
                ", name='" + name + '\'' +
                '}';
    }
}
