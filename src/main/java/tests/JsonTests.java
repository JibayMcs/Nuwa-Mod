package tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.zeamateis.nuwa.client.gui.contentPack.ContentPackChangelogScreen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class JsonTests {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().create();

        try {
            URL url = new URL("https://files.leviathan-studio.com/amateis2/Nuwa/changelogs/changelogs.json");
            BufferedReader read = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            ContentPackChangelogScreen.ChangelogObject changelogObject = gson.fromJson(read, ContentPackChangelogScreen.ChangelogObject.class);
            changelogObject.getUnreleased().forEach(releaseObject -> System.out.println(releaseObject.getVersion()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

}
