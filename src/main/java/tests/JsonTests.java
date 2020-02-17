package tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonTests {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().create();

        String json = "{\n" +
                "  \"registryName\": \"effect_flames\",\n" +
                "  \"effectType\": \"HARMFUL\",\n" +
                "  \"liquidColor\": -609685,\n" +
                "  \"performEffect\": {\n" +
                "    \"processes\": [\n" +
                "      {\n" +
                "        \"processName\": \"nuwa:set_on_fire_process\",\n" +
                "        \"parameters\": {\n" +
                "          \"duration\": 1\n" +
                "        },\n" +
                "        \"conditions\" : [\n" +
                "          {\n" +
                "            \"entity\" : \"Player\"\n" +
                "          }  \n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";


    }

}
