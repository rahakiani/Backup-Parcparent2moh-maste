package pro.kidss.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Sms_data")
public class MainData {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int  id;
    @ColumnInfo(name = "number")
    private String number;
    @ColumnInfo(name = "body")
    private String body;
    @ColumnInfo(name = "date")
    private String date;

    public MainData() {

    }

    public int getId() {
        return id;
    }

    @NotNull
    @Override
    public String toString() {
        return "MainData{" +
                "id=" + id +
                ", number='" + number +
                ", body='" + body +
                ", date='" + date +
                '}';
    }

    public MainData(int id, String number, String body, String date) {
        this.id = id;
        this.number = number;
        this.body = body;
        this.date = date;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
