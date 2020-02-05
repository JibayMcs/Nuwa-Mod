package fr.zeamateis.nuwa.contentpack.common.json.data.blocks.craftstudio;

public class CraftStudioBlockObject {

    private String model;
    //private TextureObject texture;

    public String getModel() {
        return model;
    }

    /*public TextureObject getTexture() {
        return texture;
    }*/

    @Override
    public String toString() {
        return "CraftStudioBlockObject{" +
                "model='" + model + '\'' +
                '}';
    }
}
