package pro.kidss.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MainDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MsinData msinData);

    //Delet
    @Delete
    void delet(MsinData msinData);

    //Delet All
    @Delete
    void reset(List<MsinData> msinData);

    //update
//    @Query( "UPDATE video_data SET address = :sText WHERE ID = :sID" )
//
//    void update(int sID , String sText);

    //get all data

    @Query("SELECT  * FROM video_data ")
    List<MsinData> getallvideo();

    @Query("UPDATE video_data SET `like`= 1 WHERE address =:address ")
    void like(String address);

    @Query("SELECT `like`  FROM  video_data WHERE address =:address ")
    int checklike(String address);

    @Query("UPDATE video_data SET down = 1 WHERE address =:address ")
    void adddown(String address);

    @Query("SELECT down  FROM  video_data WHERE address =:address ")
    int checkdown(String address);

    @Query("SELECT * FROM video_data WHERE `like`=1 ")
    List<MsinData> getlike();

    @Query("UPDATE video_data SET `like` = 0 WHERE address =:address ")
    void deletlike(String address);

    @Query("SELECT COUNT(*) FROM video_data WHERE address =:address")
    int checkaddress(String address);

    @Query("SELECT  DISTINCT type FROM video_data  ORDER BY type DESC")
    List<String> gettyper();

    @Query("SELECT  DISTINCT date FROM video_data WHERE type=:type ORDER BY date DESC")
    List<String> getdater(String type);

    @Query("SELECT date FROM video_data WHERE type=:typee")
    List<String> getdatee(String typee);

    @Query("SELECT address FROM video_data WHERE type=:typee AND date=:date ")
    List<String> getaddressss(String typee, String date);
    @Query("SELECT * FROM video_data WHERE address =:address ")
    List<MsinData> getaall(String address);
    @Query("SELECT file FROM video_data WHERE address =:address ")
    String getfilee(String address);

//    @Query( "SELECT address FROM video_data WHERE date=:date " )
//    List<String> getaddressss( String date);


//    @Query("SELECT COUNT(*) FROM video_data WHERE date =:date")
//    int checkdate (String  date);
}

