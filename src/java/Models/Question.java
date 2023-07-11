
package Models;

public class Question {
    private int id;
    private String text;
    private String topic;
    private String author;

    public Question() {
    }

    public Question(int id, String text, String topic, String author) {
        this.id = id;
        this.text = text;
        this.topic = topic;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getTopic() {
        return topic;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
}
