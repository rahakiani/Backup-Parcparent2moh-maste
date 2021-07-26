package pro.kidss;

public class Video {
    int id;
    String name;
    String Time;

    public Video(int id, String name, String time) {
        this.id = id;
        this.name = name;
        Time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
