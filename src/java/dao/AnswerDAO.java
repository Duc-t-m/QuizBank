package dao;

import Models.Answer;
import Models.Question;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnswerDAO {

    Connection con;
    String status;

    public AnswerDAO() {
        try {
            con = new DBConnect().getConnection();
            status = "OK";
        } catch (Exception e) {
            status = "Error at connection" + e.getMessage();
        }
    }

    public List<Answer> loadAnswers(int id) {
        String sql = "select * from answers where qid = ?";
        List<Answer> list = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int qid = rs.getInt("qid");
                String choice = rs.getString("choice");
                int isKey = rs.getInt("isKey");
                String text = rs.getString("answerText");
                Answer qs = new Answer(qid, choice, isKey, text);
                list.add(qs);
            }
        } catch (SQLException e) {
            System.out.println("Error at load answer");
        }
        return list;
    }

    public Map<Question, List<Answer>> loadAnswers(List<Question> quizzes) {
        Map<Question, List<Answer>> test = new HashMap<>();
        for (Question question : quizzes) {
            List<Answer> list = new ArrayList<>();
            String sql = "select * from answers where qid = ?";
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, question.getId());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int qid = rs.getInt("qid");
                    String choice = rs.getString("choice");
                    int isKey = rs.getInt("isKey");
                    String text = rs.getString("answerText");
                    Answer qs = new Answer(qid, choice, isKey, text);
                    list.add(qs);
                }
            } catch (SQLException e) {
                System.out.println("Error at load answer");
                System.out.println(e.getMessage());
            }
            Collections.shuffle(list);
            test.put(question, list);
        }
        return test;
    }

    public void insert(int qid, String choice, int isKey, String text) {
        String sql = "insert into answers(qid, choice, isKey, answerText) values(?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, qid);
            ps.setString(2, choice);
            ps.setInt(3, isKey);
            ps.setString(4, text);
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Error at insert answer");
            System.out.println(e.getMessage());
        }
    }

    public void update(int qid, String choice, int isKey, String text) {

        String sqlCheck = "select count(*) from answers where qid=? and choice=?";
        try {
            PreparedStatement ps = con.prepareStatement(sqlCheck);
            ps.setInt(1, qid);
            ps.setString(2, choice);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int check = rs.getInt(1);
            if (check == 1) {
                String sqlUpdate = "update answers set isKey=?, answerText=? where qid=? and choice=?";
                ps = con.prepareStatement(sqlUpdate);
                ps.setInt(1, isKey);
                ps.setString(2, text);
                ps.setInt(3, qid);
                ps.setString(4, choice);
                ps.execute();
            } else if (check == 0) {
                insert(qid, choice, isKey, text);
            }
        } catch (SQLException e) {
            System.out.println("Error at update answer");
            System.out.println(e.getMessage());
        }
    }

    public void delete(int qid, String choice) {
        String sql = "delete from answers where qid=? and choice=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, qid);
            ps.setString(2, choice);
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Error at update answer");
            System.out.println(e.getMessage());
        }
    }

}
