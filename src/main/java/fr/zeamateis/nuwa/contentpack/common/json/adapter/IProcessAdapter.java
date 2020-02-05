package fr.zeamateis.nuwa.contentpack.common.json.adapter;

import com.google.gson.*;
import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.base.IProcess;
import fr.zeamateis.nuwa.init.NuwaRegistries;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;

public class IProcessAdapter<T extends IProcess> implements JsonSerializer<T>, JsonDeserializer<T> {

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
        final JsonElement process = get(member, "processName");
        final JsonElement parameters = get(member, "parameters");
        final Type actualType = processForName(process);

        T deserializedProcess = ((T) context.deserialize(parameters, actualType));

        if (member.has("conditions")) {
            final JsonElement conditions = get(member, "conditions");
            for (int i = 0; i < conditions.getAsJsonArray().size(); i++) {
                JsonElement condition = conditions.getAsJsonArray().get(i).getAsJsonObject().get("condition");
                //  ICondition parsedCondition = (ICondition) conditionForName(condition);
                System.out.println(condition.getAsString());
                System.out.println(NuwaRegistries.PROCESS.getValue(
                        new ResourceLocation(
                                condition.getAsString()))
                        .getProcessObject()
                        .getProcessClass());
                // System.out.println(parsedCondition.getRegistryName());
                //deserializedProcess.conditions.add();

            }
        }

        return deserializedProcess;
    }

    private Type processForName(final JsonElement typeElem) {
        try {
            return Class.forName(NuwaRegistries.PROCESS.getValue(new ResourceLocation(typeElem.getAsString())).getProcessObject().getProcessClass());
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }

    private Type conditionForName(final JsonElement typeElem) {
        for (int i = 0; i < typeElem.getAsJsonArray().size(); i++) {
            try {
                return Class.forName(NuwaRegistries.CONDITION.getValue(new ResourceLocation(typeElem.getAsJsonArray().get(i).getAsString())).getConditionObject().getConditionClass());
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e);
            }
        }
        return null;
    }

    private JsonElement get(final JsonObject wrapper, final String memberName) {
        final JsonElement elem = wrapper.get(memberName);
        if (elem == null) {
            throw new JsonParseException("no '" + memberName + "' member found in json file.");
        }
        return elem;
    }

}