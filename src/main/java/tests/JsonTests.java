package tests;

import api.contentpack.common.json.adapter.ItemStackAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class JsonTests {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder()
                //.registerTypeAdapter(IProcess.class, new IProcessAdapter<AttackProcess>())
                // .registerTypeAdapter(IProcess.class, new IProcessAdapter<HealProcess>())
                .registerTypeAdapter(ItemStack.class, new ItemStackAdapter())
                .setPrettyPrinting()
                .create();


        ItemStack itemStack = new ItemStack(Items.DIAMOND);

        System.out.println(gson.toJson(itemStack));
    }
}
