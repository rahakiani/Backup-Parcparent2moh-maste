package pro.kidss.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Call_data")
public class Maindataa {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int  id;
    @ColumnInfo(name = "number")
    private String number;
    @ColumnInfo(name = "direction")
    private String direction;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "time")
    private String time;

    @Override
    public String toString() {
        return "Maindataa{" +
                "id=" + id +
                ", number='" + number +
                ", direction='" + direction +
                ", date='" + date +
                ", time='" + time +
                '}';
    }

    public Maindataa(int id, String number, String direction, String date, String time) {
        this.id = id;
        this.number = number;
        this.direction = direction;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
