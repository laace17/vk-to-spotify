import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class FInalLikedTrackAdd {
    private static final String ACCESS_TOKEN = "access_token";

    public static void main(String[] args) throws IOException {
        BufferedReader autor = new BufferedReader(new FileReader("src/author.txt"));
        BufferedReader name = new BufferedReader(new FileReader("src/name.txt"));
        try {
            for(int i = 0; i <= 2; i++){
                String trackName = name.readLine();
                String artistName = autor.readLine();

                String trackId = searchTrackId(trackName, artistName);
                String encodedId = URLEncoder.encode(trackId, "UTF-8");
                String apiUrl = "https://api.spotify.com/v1/me/tracks?ids=" + encodedId;

                URL url = new URL(apiUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Authorization", "Bearer " + ACCESS_TOKEN);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");

                conn.setDoOutput(true);
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(new byte[0]);
                }

                int responseCode = conn.getResponseCode();
                System.out.println("Response code: " + responseCode);

                if (responseCode == 200 || responseCode == 201) {
                    System.out.println("Успешно добавлено в понравившиеся!");
                } else {
                    System.out.println("Ошибка:");
                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conn.getErrorStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                        }
                    }
                }

                conn.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String searchTrackId(String trackName, String artistName) throws Exception {
        String query = URLEncoder.encode(trackName + " " + artistName, "UTF-8");
        String apiUrl = "https://api.spotify.com/v1/search?q=" + query + "&type=track&limit=1";

        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + ACCESS_TOKEN);

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray items = jsonResponse.getJSONObject("tracks").getJSONArray("items");

            if (items.length() > 0) {
                return items.getJSONObject(0).getString("id");
            }
        }
        return null;
    }
//    private static void getAuthor() throws IOException {
//        BufferedReader author = new BufferedReader(new FileReader("src\\музыка.txt"));
//        for(int i = 0; i < 100; i++){
//            String s = "";
//            String author2 = author.readLine();
//            for(int j = 0; j < author2.length(); j++){
//                if(author2.charAt(j) == ' ' && author2.charAt(j + 1) == '—'){
//                    break;
//                }
//                s += author2.charAt(j);
//            }
//            System.out.println(s);
//            s = "";
//        }
//    }
//    private static void getTrack() throws IOException {
//        BufferedReader track = new BufferedReader(new FileReader("src\\музыка.txt"));
//        for(int i = 0; i < 100; i++){
//            String s = "";
//            String track2 = track.readLine();
//            boolean flag = false;
//            for(int j = 0; j < track2.length(); j++){
//                if(flag){
//                    s += track2.charAt(j);
//                }
//                if(track2.charAt(j) == ' ' && track2.charAt(j - 1) == '—'){
//                    flag = true;
//                }
//            }
//            System.out.println(s);
//            s = "";
//        }
//    }
}