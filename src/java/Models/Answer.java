
package Models;

public class Answer {
    private int qid;
    private String choice;
    private int isKey;
    private String text;
    
    public Answer(int qid, String choice, int isKey, String text) {
        this.qid = qid;
        this.choice = choice;
        this.isKey = isKey;
        this.text = text;
    }

    public Answer() {
    }

    public int getQid() {
        return qid;
    }

    public String getChoice() {
        return choice;
    }

    public int getIsKey() {
        return isKey;
    }

    public String getText() {
        return text;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public void setIsKey(int isKey) {
        this.isKey = isKey;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
