package com.kroy.modules;

import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;


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
