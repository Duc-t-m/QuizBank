
package Models;

import java.sql.Timestamp;

public class Result {
    private String user;
    private Timestamp time;
    private float score;

    public void setUser(String user) {
        this.user = user;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getUser() {
        return user;
    }

    public Timestamp getTime() {
        return time;
    }

    public float getScore() {
        return score;
    }

    public Result() {
    }

    public Result(String username, Timestamp time, float score) {
        this.user = username;
        this.time = time;
        this.score = score;
    }
    
}
