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
    List<MainData> getallsms();
    @Query("SELECT  DISTINCT number FROM sms_data  ORDER BY number DESC")
    List<String> getnumber();
    @Query( "SELECT * FROM sms_data WHERE number=:number" )
    List<MainData>getsms(String number);
    @Query( "SELECT status FROM sms_data WHERE number=:number AND body =:body" )
    List<String>getstatuss(String number,String body);
//    @Query( "SELECT body FROM sms_data WHERE number=:id" )
//    List<String> bodyy(String id);
    @Query("SELECT COUNT(*) FROM sms_data WHERE id =:id")
    int checkid(int id);



}
