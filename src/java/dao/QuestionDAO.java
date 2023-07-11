package dao;

import Models.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class QuestionDAO {

    Connection con;
    String status;
    private Map<Integer, Question> questions;
    private Set<String> topics;
    private static int maxID;

    public static int getMaxID() {
        return maxID;
    }

    public QuestionDAO() {
        try {
            con = new DBConnect().getConnection();
            status = "OK";
        } catch (Exception e) {
            status = "Error at connection" + e.getMessage();
        }
    }

    public void loadQuestions() {
        questions = new LinkedHashMap<>();
        topics = new HashSet<>();
        String sql = "select * from questions order by id asc";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String text = rs.getString("questionText");
                String topic = rs.getString("topic");
                String author = rs.getString("author");
                Question qs = new Question(id, text, topic, author);
                questions.put(id, qs);
                topics.add(topic);
            }
        } catch (SQLException e) {
            System.out.println("Error at load questions");
        }

        String sql2 = "select ident_current('dbo.questions')";
        try {
            PreparedStatement ps = con.prepareStatement(sql2);
            ResultSet rs = ps.executeQuery();
            rs.next();
            maxID = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("Error at load questions");
        }
    }
    
    public List<Question> loadQuestions(List<String> topics, int number){
       List<Question> test =  questions.values()
                                            .stream().filter(question -> topics.contains(question.getTopic())).collect(Collectors.toList());
        Collections.shuffle(test);
        if(number>test.size())
            return test;
        return test.subList(0, number);
    }
    public Map<Integer, Question> getQuestions() {
        return questions;
    }

    public String[] getTopics() {
        String arr[] = new String[topics.size()];
        return topics.toArray(arr);
    }

    public void insert(String text, String topic, String teacher) {
        String sql = "insert into questions(questionText, topic, author) values(?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, text);
            ps.setString(2, topic);
            ps.setString(3, teacher);
            ps.execute();
            maxID++;
        } catch (SQLException e) {
            System.out.println("Error at insert question");
            System.out.println(e);
        }

    }

    public void delete(String id) {
        String sqlAtt = "delete from attempDetails where qid = ?";
        String sqlA = "delete from answers where qid = ?";
        String sqlQ = "delete from questions where id = ?";
        try {
            PreparedStatement psAtt = con.prepareStatement(sqlAtt);
            PreparedStatement psA = con.prepareStatement(sqlA);
            PreparedStatement psQ = con.prepareStatement(sqlQ);
            psAtt.setString(1, id);
            psA.setString(1, id);
            psQ.setString(1, id);
            psAtt.execute();
            psA.execute();
            psQ.execute();
        } catch (SQLException e) {
            System.out.println("Error at delete question");
            System.out.println(e.getMessage());
        }
    }

    public void update(int qid, String question, String topic, String user) {
        String sql = "update questions set questionText=?, topic=?, author=? where id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, question);
            ps.setString(2, topic);
            ps.setString(3, user);
            ps.setInt(4, qid);
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Error at update question");
            System.out.println(e.getMessage());
        }
    }

    public List<Question> loadQuestions(Set<Integer> qids) {
        List<Question> test =  questions.values()
                                            .stream().filter(question -> qids.contains(question.getId())).collect(Collectors.toList());
        return test;
    }

}
