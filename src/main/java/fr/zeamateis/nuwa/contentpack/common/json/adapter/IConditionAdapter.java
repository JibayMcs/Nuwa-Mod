package fr.zeamateis.nuwa.contentpack.common.json.adapter;

import com.google.gson.*;
import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.condition.base.ICondition;
import fr.zeamateis.nuwa.init.NuwaRegistries;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;

public class IConditionAdapter implements JsonDeserializer<ICondition> {

    @Override
    public final ICondition deserialize(final JsonElement elem, final Type interfaceType, final JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject member = (JsonObject) elem;
        final JsonElement typeString = get(member, "condition");
        final JsonElement data = get(member, "test");
        final Type actualType = typeForName(typeString);

        return context.deserialize(data, actualType);
    }

    private Type typeForName(final JsonElement typeElem) {
        try {
            return Class.forName(NuwaRegistries.CONDITION.getValue(new ResourceLocation(typeElem.getAsString())).getConditionObject().getConditionClass());
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