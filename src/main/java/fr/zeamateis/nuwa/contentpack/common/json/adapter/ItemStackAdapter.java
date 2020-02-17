package fr.zeamateis.nuwa.contentpack.common.json.adapter;

import com.google.gson.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Type;

/**
 * ItemStack Serializer/Deserializer
 *
 * @version 0.0.1
 */
public class ItemStackAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {

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
    public ItemStack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject stackObject = jsonElement.getAsJsonObject();
        CompoundNBT tagCompound = new CompoundNBT();

        String itemName = stackObject.get("id").getAsString();
        tagCompound.putString("id", itemName);

        byte count = stackObject.has("Count") ? stackObject.get("Count").getAsByte() : 1;
        tagCompound.putByte("Count", count);

        if (stackObject.has("tag")) {
            JsonObject tag = stackObject.get("tag").getAsJsonObject();

            CompoundNBT compoundNBT = new CompoundNBT();

            if (tag.has("Damage")) {
                int damage = tag.get("Damage").getAsInt();
                compoundNBT.putInt("Damage", damage);
            }
            if (tag.has("Unbreakable")) {
                boolean unbreakable = tag.get("Unbreakable").getAsBoolean();
                compoundNBT.putBoolean("Unbreakable", unbreakable);
            }
            if (tag.has("CanDestroy")) {
                ListNBT canDestroyList = new ListNBT();
                JsonArray canDestroy = tag.getAsJsonArray("CanDestroy");
                canDestroy.forEach(jsonElement1 -> canDestroyList.add(new StringNBT(jsonElement1.getAsString())));
                compoundNBT.put("CanDestroy", canDestroyList);
            }

            if (!compoundNBT.isEmpty()) {
                tagCompound.put("tag", compoundNBT);
            }

            return ItemStack.read(tagCompound);
        } else {
            return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)), count);
        }
    }

    /**
     * Gson invokes this call-back method during serialization when it encounters a field of the
     * specified type.
     *
     * <p>In the implementation of this call-back method, you should consider invoking
     * {@link JsonSerializationContext#serialize(Object, Type)} method to create JsonElements for any
     * non-trivial field of the {@code src} object. However, you should never invoke it on the
     * {@code src} object itself since that will cause an infinite loop (Gson will call your
     * call-back method again).</p>
     *
     * @param itemStack the object that needs to be converted to Json.
     * @param type      the actual type (fully genericized version) of the source object.
     * @param context   The {@link JsonDeserializationContext}
     * @return a JsonElement corresponding to the specified object.
     */
    @Override
    public JsonElement serialize(ItemStack itemStack, Type type, JsonSerializationContext context) {
        JsonObject values = new JsonObject();
        if (itemStack == null) {
            return null;
        }

        values.add("id", new JsonPrimitive(itemStack.getItem().getRegistryName().toString()));
        values.add("Count", new JsonPrimitive(itemStack.getCount()));

        if (!itemStack.getTag().isEmpty()) {
            JsonObject tagObject = new JsonObject();

            tagObject.add("Damage", new JsonPrimitive(itemStack.getDamage()));
            tagObject.add("Unbreakable", new JsonPrimitive(itemStack.isDamageable()));

            ListNBT listnbt1 = itemStack.getTag().getList("CanDestroy", 8);
            JsonArray canDestroyList = new JsonArray();
            listnbt1.forEach(inbt -> canDestroyList.add(inbt.getString()));
            tagObject.add("CanDestroy", canDestroyList);

            values.add("tag", tagObject);
        }
        return values;
    }

}