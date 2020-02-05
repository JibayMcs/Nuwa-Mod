package fr.zeamateis.nuwa.contentpack.common.json.adapter;

import api.contentpack.PackManager;
import com.google.gson.*;
import fr.zeamateis.nuwa.contentpack.common.data.ProcessesData;
import fr.zeamateis.nuwa.contentpack.common.minecraft.registries.ProcessType;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;

public class IProcessAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {

    private final PackManager packManager;

    public IProcessAdapter(PackManager packManagerIn) {
        this.packManager = packManagerIn;
    }

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
            //   className = Class.forName(String.format("%s", ((ProcessType)process.getKey().getValue(new ResourceLocation(typeElem.getAsString()))).getProcessObject().getProcessClass())));

            return Class.forName(packManager.getDataRegistryMap().entrySet().stream()
                    .filter(classEntry -> classEntry.getValue().equals(ProcessesData.class))
                    .map(classEntry -> ((ProcessType) classEntry.getKey().getValue(new ResourceLocation(typeElem.getAsString()))).getProcessObject().getProcessClass()).findFirst().get());
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