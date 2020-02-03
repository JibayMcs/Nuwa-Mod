package api.contentpack.common.json.adapter;

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

    @Override
    public ItemStack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject stackObject = jsonElement.getAsJsonObject();
        CompoundNBT tagCompound = new CompoundNBT();

        String itemName = stackObject.get("id").getAsString();
        tagCompound.putString("id", itemName);

        byte count = stackObject.get("Count").getAsByte();
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