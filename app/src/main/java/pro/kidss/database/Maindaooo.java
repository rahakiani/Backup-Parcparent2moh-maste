package pro.kidss.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface Maindaooo {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Maindataa mainData);
    @Query("SELECT  * FROM call_data ")
    List<Maindataa> getall();
    @Query("SELECT  DISTINCT number FROM call_data  ORDER BY number DESC")
    List<String> getnumber();
    @Query( "SELECT * FROM call_data WHERE number=:number" )
    List<Maindataa>getcall(String number);
    @Query("SELECT COUNT(*) FROM call_data WHERE id =:id")
    int checkid(Integer id);
    @Query( "SELECT direction FROM call_data WHERE number=:number " )
    List<String>getdirect(String number);
}
