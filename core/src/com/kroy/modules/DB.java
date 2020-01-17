package com.kroy.modules;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.StringBufferInputStream;
// Imports for cloud DB
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


// Local DB imports


public class DB {
    private String baseUrl;
    private HttpClient client;
    private String apiKey;

    public DB() {
        baseUrl = "https://1bd3pmqr31.execute-api.eu-west-2.amazonaws.com/dev/";
        client = HttpClient.newHttpClient();
        apiKey = "meGJsddzC23xu9kL5ZrNB1zNZPYS5h725T22Syp0";
    }

    public ArrayList<ArrayList<String>> getLeaderboard(String amount) {
        ArrayList<ArrayList<String>> scores = new ArrayList<>();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.baseUrl + "/fetchleaderboard?type=fetch&amount=" + amount))
                .header("x-api-key", this.apiKey)
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            JSONParser obj = new JSONParser();
            JSONObject json = (JSONObject) obj.parse(response.body());
            JSONArray body = (JSONArray) json.get("body");
            for (int i = 0; i < body.size(); i++) {
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
                .uri(URI.create(this.baseUrl + "/fetchleaderboard?type=insert"))
                .header("x-api-key", this.apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(obj.toJSONString()))
                .build();
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public ArrayList<ArrayList<String>> local_getLeaderboard(String amount) {
        ArrayList<ArrayList<String>> results = new ArrayList<>();

        String url = "jdbc:sqlite:"+ Paths.get("..", "assets")+"/scores.db";
        String sql = "SELECT * FROM `scores` LIMIT " + amount;
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(sql);

                    // loop through the result set
                    while (rs.next()) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add(rs.getString("username"));
                        tmp.add(String.format("%s",rs.getInt("score")));
                        results.add(tmp);
                    }
            }
        } catch (SQLException e){
            System.out.println(e.toString());
        }

        return results;

    }

    public boolean local_uploadScore(String username, int score) {
        String url = "jdbc:sqlite:"+ Paths.get("..", "assets")+"/scores.db";
        System.out.println(url);
        String sql = String.format("INSERT INTO `scores` (username, score) VALUES (\"%s\", %d) ", username, score);
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                try {
                    Statement stmt  = conn.createStatement();
                    ResultSet rs    = stmt.executeQuery(sql);
                    return true;
                } catch (SQLException e){
                    return false;
                }
            }
        } catch (SQLException e){
            return false;
        }

        return false;

    }
}
