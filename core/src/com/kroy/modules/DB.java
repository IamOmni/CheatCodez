package com.kroy.modules;

import java.io.File;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;

public class DB {

    /**
     * Fetch leaderboard
     * @param amount - Amount wanting to be fetched
     * @return
     */
    public ArrayList<ArrayList<String>> local_getLeaderboard(String amount) {
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        String path = "scores.db";
        if (!System.getProperty("user.dir").contains("assets"))
            path =  "assets/"+path;

        String url = "jdbc:sqlite:"+ Paths.get(path).toAbsolutePath().toString();
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

    /**
     * Insert score to DB
     * @param username - username of the player
     * @param score - score the player achieved
     * @return boolean for success
     */
    public boolean local_uploadScore(String username, int score) {
        String path = "scores.db";
        if (!System.getProperty("user.dir").contains("assets"))
            path =  "assets/"+path;

        String url = "jdbc:sqlite:"+ Paths.get(path).toAbsolutePath().toString();
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
