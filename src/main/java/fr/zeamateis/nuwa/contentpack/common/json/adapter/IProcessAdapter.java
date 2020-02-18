package fr.zeamateis.nuwa.contentpack.common.json.adapter;

import api.contentpack.json.process.IProcess;
import com.google.gson.*;
import fr.zeamateis.nuwa.init.NuwaRegistries;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;

/**
 * {@link Gson} adapter class for {@link IProcess} objects
 *
 * @author ZeAmateis
 */
public class IProcessAdapter implements JsonDeserializer<IProcess> {

    /**
     * Gson invokes this call-back method during deserialization when it encounters a field of the
     * specified type.
     * <p>In the implementation of this call-back method, you should consider invoking
     * {@link JsonDeserializationContext#deserialize(JsonElement, Type)} method to create objects
     * for any non-trivial field of the returned object. However, you should never invoke it on the
     * the same type passing {@code json} since that will cause an infinite loop (Gson will call your
     * call-back method again).
     *
     * @param jsonElement The Json data being deserialized
     * @param type        The type of the Object to deserialize to
     * @param context     The {@link JsonDeserializationContext}
     * @return a deserialized object of the specified type typeOfT which is a subclass of {@code T}
     * @throws JsonParseException if json is not in the expected format of {@code typeofT}
     */
    @Override
    public final IProcess deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject member = (JsonObject) jsonElement;
        final JsonElement process = get(member, "processName");
        final JsonElement parameters = get(member, "parameters");
        final Type actualType = processForName(process);

        IProcess deserializedProcess = context.deserialize(parameters, actualType);

       /* if (member.has("conditions")) {
            final JsonElement conditions = get(member, "conditions");

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
        }*/

        return deserializedProcess;
    }

    private Type processForName(final JsonElement typeElem) {
        try {
            return Class.forName(NuwaRegistries.PROCESS.getValue(new ResourceLocation(typeElem.getAsString())).getProcessObject().getProcessClass());
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