package me.Vark123.EpicNPC.Tools;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.bukkit.Bukkit;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MineSkinFetcher {

    private static final String MINESKIN_API = "https://api.mineskin.org/get/id/";
    
    public static String[] fetchSkinFromIdAsync(int id) {
    	try {
			StringBuilder builder = new StringBuilder();
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(MINESKIN_API + id).openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();

            Scanner scanner = new Scanner(httpURLConnection.getInputStream());
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
            }

            scanner.close();
            httpURLConnection.disconnect();

			JsonObject jsonObject = (JsonObject) JsonParser.parseString(builder.toString());
            JsonObject textures = jsonObject.get("data").getAsJsonObject().get("texture").getAsJsonObject();
            String value = textures.get("value").getAsString();
            String signature = textures.get("signature").getAsString();
            
            return new String[] {value, signature};
		} catch (IOException exception) {
            Bukkit.getLogger().severe("Could not fetch skin! (Id: " + id + "). Message: " + exception.getMessage());
            exception.printStackTrace();
        }
    	return null;
    }

}
