package fr.zeamateis.nuwa.contentpack.common.json.adapter;

import com.google.gson.*;
import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.base.IProcess;
import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.condition.base.ICondition;
import fr.zeamateis.nuwa.init.NuwaRegistries;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;
import java.util.Objects;

public class IProcessAdapter implements JsonDeserializer<IProcess> {

    @Override
    public final IProcess deserialize(final JsonElement elem, final Type interfaceType, final JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject member = (JsonObject) elem;
        final JsonElement process = get(member, "processName");
        final JsonElement parameters = get(member, "parameters");
        final Type actualType = processForName(process);

        IProcess deserializedProcess = context.deserialize(parameters, actualType);

        if (member.has("conditions")) {
            final JsonElement conditions = get(member, "conditions");
            final JsonElement test = get(member, "test");

            for (int i = 0; i < conditions.getAsJsonArray().size(); i++) {
                JsonElement conditionObj = conditions.getAsJsonArray().get(i).getAsJsonObject().get("condition");
                NuwaRegistries.CONDITION.getValues().stream()
                        .filter(Objects::nonNull)
                        .filter(conditionType -> conditionType.getRegistryName().equals(new ResourceLocation(conditionObj.getAsString())))
                        .forEach(conditionType -> {
                            try {
                                ICondition condition = (ICondition) Class.forName(conditionType.getConditionObject().getConditionClass()).newInstance();
                                deserializedProcess.conditions.add(condition);
                            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                                e.printStackTrace();
                            }
                        });
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