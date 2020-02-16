package tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.zeamateis.nuwa.contentpack.common.json.data.biomes.PreconfiguredFeaturesObject;

import java.util.Arrays;
import java.util.List;

public class JsonTests {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().create();

        List<PreconfiguredFeaturesObject> objects = Arrays.asList(PreconfiguredFeaturesObject.values());

        System.out.println(gson.toJson(objects));
    }

}
