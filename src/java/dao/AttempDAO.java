package dao;

import Models.Attemp;
import Models.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;

public class AttempDAO {

    Connection con;
    String status;

    public AttempDAO() {
        try {
            con = new DBConnect().getConnection();
            status = "OK";
        } catch (Exception e) {
            status = "Error at connection" + e.getMessage();
        }
    }

    public void insertAttemp(String user, int qid, int isCorrect, Timestamp time, String choice) {
        String sql = "insert into attempDetails(username, qid, isCorrect, timeFinished, choice) values(?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setInt(2, qid);
            ps.setInt(3, isCorrect);
            ps.setTimestamp(4, time);
            ps.setString(5, choice);
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Error at insert attemp");
            System.out.println(e.getMessage());
        }

    }

    public List<Result> loadResults(String user) {
        List<Result> results = new ArrayList<>();

        String sql = "select username, timeFinished, score from attemps where username=? order by timeFinished desc";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String username = rs.getString(1);
                Timestamp date = rs.getTimestamp(2);
                float score = Math.round(rs.getFloat(3));
                Result result = new Result(username, date, score);
                results.add(result);
            }

        } catch (SQLException e) {
            System.out.println("Error at load results");
            System.out.println(e.getMessage());
        }
        return results;
    }

    public void insertResult(String user, Timestamp time, float score) {
        String sql = "insert into attemps(username, timeFinished, score) values(?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setTimestamp(2, time);
            ps.setFloat(3, score);
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Error at insert result");
            System.out.println(e.getMessage());
        }
    }

    public Map<Integer, Attemp> loadAttemps(String user, Timestamp time) {
        Map<Integer, Attemp> results = new LinkedHashMap<>();
        String sql = "select * from attempDetails where username=? and timeFinished=? order by qid";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, time.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String username = rs.getString(1);
                int qid = rs.getInt(2);
                int isCorrect = rs.getInt(3);
                Timestamp timeF = rs.getTimestamp(4);
                String choice = rs.getString(5);
                Attemp attemp = new Attemp(username, qid, isCorrect, timeF, choice);
                results.put(qid, attemp);
            }

        } catch (SQLException e) {
            System.out.println("Error at load attemps");
            System.out.println(e.getMessage());
        }
        return results;
    }
    
    public static void main(String[] args) {
       AttempDAO attDao = new AttempDAO();
       Map<Integer, Attemp> map = attDao.loadAttemps("teacher1", Timestamp.valueOf("2023-03-15 10:53:53.717"));
        for (Map.Entry<Integer, Attemp> entry : map.entrySet()) {
            Integer key = entry.getKey();
            Attemp value = entry.getValue();
            System.out.println(key+" : "+value);
            
        }
    }
}
