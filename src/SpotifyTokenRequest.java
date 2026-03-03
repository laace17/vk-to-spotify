import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class SpotifyTokenRequest {
    public static void main(String[] args) throws Exception {
        String clientId = "client_id";
        String clientSecret = "client_secret";
        String redirectUri = "http://127.0.0.1:8888/callback";
        String code = "authorization code";

        URL url = new URL("https://accounts.spotify.com/api/token");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String data = "grant_type=authorization_code" +
                "&code=" + URLEncoder.encode(code, "UTF-8") +
                "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8") +
                "&client_id=" + URLEncoder.encode(clientId, "UTF-8") +
                "&client_secret=" + URLEncoder.encode(clientSecret, "UTF-8");

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = data.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }


        InputStream responseStream;
        if (conn.getResponseCode() >= 400) {
            responseStream = conn.getErrorStream();
            System.out.println("Ошибка! Код ответа: " + conn.getResponseCode());
        } else {
            responseStream = conn.getInputStream();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(responseStream));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        System.out.println("Ответ от Spotify:");
        System.out.println(response.toString());
    }
}