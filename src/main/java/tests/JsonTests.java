package tests;

import api.contentpack.common.json.datas.blocks.events.EntityCollideEvent;
import api.contentpack.common.json.datas.blocks.events.actions.AttackProcess;
import api.contentpack.common.json.datas.blocks.events.actions.HealProcess;
import api.contentpack.common.json.datas.blocks.events.actions.base.IProcess;
import api.contentpack.common.json.datas.blocks.properties.BlockEventProperties;
import api.contentpack.common.json.datas.damageSources.type.DamageSourceType;
import com.google.gson.*;
import fr.zeamateis.nuwa.init.NuwaRegistries;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;
import java.util.Arrays;

public class JsonTests {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(IProcess.class, new InterfaceAdapter<AttackProcess>())
                .registerTypeAdapter(IProcess.class, new InterfaceAdapter<HealProcess>())
                .setPrettyPrinting()
                .create();


        EntityCollideEvent entityCollideEvent = new EntityCollideEvent(Arrays.asList("minecraft:player"),
                Arrays.asList(
                        new AttackProcess(DamageSourceType.CACTUS, 1.0F),
                        new HealProcess(1.0F))
        );


        BlockEventProperties blockEventProperties = new BlockEventProperties(entityCollideEvent);

        String event = gson.toJson(blockEventProperties);

        System.out.println(event);

        BlockEventProperties parsedEvent = gson.fromJson(event, BlockEventProperties.class);
    }


    public static class InterfaceAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {

        @Override
        public final JsonElement serialize(final T object, final Type interfaceType, final JsonSerializationContext context) {
            final JsonObject member = new JsonObject();
            member.addProperty("processName", object.getClass().getSimpleName());
            member.add("parameters", context.serialize(object));
            return member;
        }

        @Override
        public final T deserialize(final JsonElement elem, final Type interfaceType, final JsonDeserializationContext context)
                throws JsonParseException {
            final JsonObject member = (JsonObject) elem;
            final JsonElement typeString = get(member, "processName");
            final JsonElement data = get(member, "parameters");
            final Type actualType = typeForName(typeString);

            return context.deserialize(data, actualType);
        }

        private Type typeForName(final JsonElement typeElem) {
            try {
                System.out.println(typeElem.getAsString());
                System.out.println(NuwaRegistries.PROCESS.getValue(new ResourceLocation(typeElem.getAsString())));
                return Class.forName(String.format("%s.%s", "api.contentpack.common.json.datas.blocks.events.actions",
                        NuwaRegistries.PROCESS.getValue(new ResourceLocation(typeElem.getAsString()))));
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e);
            }
        }

        private JsonElement get(final JsonObject wrapper, final String memberName) {
            final JsonElement elem = wrapper.get(memberName);
            if (elem == null) {
                throw new JsonParseException("no '" + memberName + "' member found in json file.");
            }
            return elem;
        }

    }
}
