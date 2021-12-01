package pro.kidss.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MainContact {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ContactData contactData);

//    @Query("SELECT  * FROM contact_data ")
//    List<MsinData> getallcontact();
    @Query("SELECT  DISTINCT name FROM contact_data WHERE number=:number ORDER BY name DESC")
    String getnamee(String number);

    @Query("SELECT COUNT(*) FROM contact_data WHERE id =:id")
    int checknumber(int id);
    @Query("SELECT  DISTINCT name FROM contact_data  ORDER BY name DESC")
    List<String> getnamedis();
    @Query("SELECT  DISTINCT number FROM contact_data  ORDER BY number DESC")
    List<String> getnumberdic();
    @Query("DELETE FROM contact_data")
    void deletcontact();
}
