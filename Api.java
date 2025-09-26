package soulsyncproject;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONArray;

public class Api {

    // Your Gemini API key
    private static final String API_KEY = "AIzaSyD65ucAFhFl_Uj7C-R5WeR9XDRFvKZv2TA";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Gemini Console Chat ===");
        System.out.println("Type 'exit' to end the chat.");

        while (true) {
            System.out.print("\nEnter your prompt: ");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("👋 Chat ended.");
                break;
            }

            try {
                // Create JSON request
                JSONObject request = new JSONObject();
                JSONArray contents = new JSONArray();
                JSONObject part = new JSONObject();
                part.put("text", userInput);

                JSONArray parts = new JSONArray();
                parts.put(part);

                JSONObject message = new JSONObject();
                message.put("parts", parts);

                contents.put(message);
                request.put("contents", contents);

                // Open HTTP connection
                URL url = new URL(API_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                // Send request
                OutputStream os = conn.getOutputStream();
                byte[] input = request.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
                os.flush();
                os.close();

                // Read response
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String responseLine;

                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                // Parse JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray candidates = jsonResponse.getJSONArray("candidates");
                String outputText = candidates.getJSONObject(0)
                                              .getJSONObject("content")
                                              .getJSONArray("parts")
                                              .getJSONObject(0)
                                              .getString("text");

                System.out.println("\nGemini's Response:\n" + outputText);

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
