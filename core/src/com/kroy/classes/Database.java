package com.kroy.classes;


        import org.json.simple.JSONArray;
        import org.json.simple.JSONObject;
        import org.json.simple.parser.JSONParser;

        import java.io.StringBufferInputStream;
        import java.net.URI;
        import java.net.http.HttpClient;
        import java.net.http.HttpRequest;
        import java.net.http.HttpResponse;
        import java.net.http.HttpResponse.BodyHandlers;
        import java.util.ArrayList;

public class Database {
    private String baseUrl;
    private HttpClient client;
    private String apiKey;

    public Database() {
        baseUrl = "https://1bd3pmqr31.execute-api.eu-west-2.amazonaws.com/dev/";
        client = HttpClient.newHttpClient();
        apiKey = "meGJsddzC23xu9kL5ZrNB1zNZPYS5h725T22Syp0";
    }

    public ArrayList<ArrayList<String>> getLeaderboard(String amount) {
        ArrayList<ArrayList<String>> scores = new ArrayList<>();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.baseUrl+"/fetchleaderboard?amount="+amount))
                .header("x-api-key", this.apiKey)
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            JSONParser obj = new JSONParser();
            JSONObject json = (JSONObject) obj.parse(response.body());
            JSONArray body = (JSONArray) json.get("body");
            for (int i=0; i < body.size(); i++) {
                ArrayList<String> temp = new ArrayList<>();

                JSONObject record = (JSONObject) body.get(i);
                temp.add(record.get("username").toString());
                temp.add(record.get("score").toString());

                scores.add(temp);
            }

            return scores;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean uploadScore(String username, int score) {
        JSONObject obj = new JSONObject();
        obj.put("username", username);
        obj.put("score", score);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.baseUrl+"/fetchleaderboard"))
                .header("x-api-key", this.apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(obj.toJSONString()))
                .build();
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
            if (response.statusCode()==200) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
