package api.contentpack.common.json.datas.blocks.craftstudio;

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
