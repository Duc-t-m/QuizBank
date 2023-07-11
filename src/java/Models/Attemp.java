
package Models;

import java.sql.Timestamp;

public class Attemp {
    private String username;
    private int qid;
    private int isCorrect;
    private Timestamp time;
    private String choice;

    public Attemp() {
    }

    public Attemp(String username, int qid, int isCorrect, Timestamp time, String choice) {
        this.username = username;
        this.qid = qid;
        this.isCorrect = isCorrect;
        this.time = time;
        this.choice = choice;
    }

    public String getUsername() {
        return username;
    }

    public int getQid() {
        return qid;
    }

    public int getIsCorrect() {
        return isCorrect;
    }

    public Timestamp getTime() {
        return time;
    }

    public String getChoice() {
        return choice;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public void setIsCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
    
}
