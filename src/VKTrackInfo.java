import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class VKTrackInfo {
    public static void main(String[] args) throws IOException {
        StringBuilder jsonContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/catalog0.json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
        }

        JSONObject root = new JSONObject(jsonContent.toString());
        JSONArray payload = root.getJSONArray("payload");

        JSONArray payloadInner = payload.getJSONArray(1);
        JSONObject playlistWrapper = payloadInner.getJSONObject(1);
        JSONObject playlist = playlistWrapper.getJSONObject("playlist");

        JSONArray tracks = playlist.getJSONArray("list");
        int cnt = 0;
        for (int i = 0; i < tracks.length(); i++) {
            JSONArray track = tracks.getJSONArray(i);
            String title = track.getString(3);
            String artist = track.getString(4);
            cnt++;
            System.out.println(artist + " — " + title);
        }
        System.out.println(cnt);
    }
}