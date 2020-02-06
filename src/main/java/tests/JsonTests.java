package tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.zeamateis.nuwa.contentpack.common.json.adapter.IConditionAdapter;
import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.condition.PlayerHeldItemCondition;
import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.condition.base.ICondition;

import java.util.List;

public class JsonTests {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().registerTypeAdapter(ICondition.class, new IConditionAdapter()).create();

        String checkEmpty = "{\n" +
                "  \"conditions\": [\n" +
                "    {\n" +
                "      \"condition\" : \"" + PlayerHeldItemCondition.class.getName() + "\",\n" +
                "      \"test\": {\n" +
                "        \"hand\": \"MAIN_HAND\",\n" +
                "        \"check\": {\n" +
                "          \"type\" : \"IS_EMPTY\",\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        String checkContain = "{\n" +
                "  \"conditions\": [\n" +
                "    {\n" +
                "      \"condition\" : \"" + PlayerHeldItemCondition.class.getName() + "\",\n" +
                "      \"test\": {\n" +
                "        \"hand\": \"MAIN_HAND\",\n" +
                "        \"check\": {\n" +
                "          \"type\" : \"EQUAL\",\n" +
                "          \"value\" : \"minecraft:apple\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        TestCondition testCondition = gson.fromJson(checkContain, TestCondition.class);

        /*testCondition.conditions.forEach(iCondition -> {
            System.out.println(((PlayerHeldItemCondition) iCondition).hand);
            iCondition.test();
        });*/
    }

    static class TestCondition {
        List<ICondition> conditions;
    }
}
