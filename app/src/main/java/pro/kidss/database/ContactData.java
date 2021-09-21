package pro.kidss.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Contact_data")
public class ContactData {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int  id;
    @ColumnInfo(name = "number")
    private String number;
    @ColumnInfo(name = "name")
    private String name;

    public ContactData(){

    }
    public ContactData(int id, String number, String name) {
        this.id = id;
        this.number = number;
        this.name = name;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", number='" + number +
                ", name='" + name +
                '}';
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
