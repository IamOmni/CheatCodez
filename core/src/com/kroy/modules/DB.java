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

    public ArrayList<ArrayList<String>> local_getLeaderboard(String amount) {
        ArrayList<ArrayList<String>> results = new ArrayList<>();

        String url = "jdbc:sqlite:"+ Paths.get("assets")+"/scores.db";
        System.out.println(url);
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
        String url = "jdbc:sqlite:"+ Paths.get("assets", "scores.db").toAbsolutePath().toString();
        System.out.println(url);
        String sql = String.format("INSERT INTO `scores` (username, score) VALUES (\"%s\", %d) ", username, score);
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                try {
                    Statement stmt  = conn.createStatement();
                    boolean rs    = stmt.execute(sql);
                    return true;
                } catch (SQLException e){

                    System.out.println(e);
                    return false;
                }
            }
        } catch (SQLException e){
            System.out.println(e);
            return false;
        }

        return false;

    }
}
