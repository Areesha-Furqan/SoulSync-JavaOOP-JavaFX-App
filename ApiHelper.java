package soulsyncproject;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiHelper {
    private static final String API_KEY = "AIzaSyD65ucAFhFl_Uj7C-R5WeR9XDRFvKZv2TA";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;

    public static String getShortGeminiReply(String prompt) {
        try {
            // Build JSON Request
            JSONObject request = new JSONObject();
            JSONArray contents = new JSONArray();
            JSONObject part = new JSONObject();
            part.put("text", "Answer briefly (1–2 lines): " + prompt);

            JSONArray parts = new JSONArray();
            parts.put(part);

            JSONObject message = new JSONObject();
            message.put("parts", parts);
            contents.put(message);
            request.put("contents", contents);

            // HTTP Request
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            os.write(request.toString().getBytes("utf-8"));
            os.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }

            JSONObject json = new JSONObject(response.toString());
            return json.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");

        } catch (Exception e) {
            e.printStackTrace();
            return "🐾 Sorry, I couldn't find an answer right now.";
        }
    }
}
