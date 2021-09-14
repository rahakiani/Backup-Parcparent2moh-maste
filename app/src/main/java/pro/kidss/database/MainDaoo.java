package pro.kidss.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MainDaoo {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MainData mainData);
    @Query("SELECT  * FROM sms_data ")
    List<MainData> getall();
    @Query("SELECT  DISTINCT number FROM sms_data  ORDER BY number DESC")
    List<String> getnumber();
    @Query( "SELECT * FROM sms_data WHERE number=:number" )
    List<MainData>getsms(String number);



}
