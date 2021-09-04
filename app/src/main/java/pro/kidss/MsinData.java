package pro.kidss;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Video_data")
public class MsinData implements Serializable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "like")
    private int lik;
    @ColumnInfo(name = "down")
    private int down;
    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "type")
    private String type;


    public MsinData() {

    }

    @Override
    public String toString() {
        return "MsinData{" +
                ", address='" + address + '\'' +
                ", lik=" + lik +
                ", down=" + down +
                ", date=" + date +
                ", type=" + type +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Ignore
    public MsinData(String addres, int lik, int down, String date, String type) {
//        this.ID = ID;
        this.address = addres;
        this.lik = lik;
        this.down = down;
        this.date = date;
        this.type = type;
    }


    public int getLik() {
        return lik;
    }

    public void setLik(int lik) {
        this.lik = lik;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

//    public int getID() {
//            return ID;
//        }

//        public void setID(int ID) {
//            this.ID = ID;
//        }

    public String getText() {
        return address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setText(String text) {
        this.address = text;
    }
}

