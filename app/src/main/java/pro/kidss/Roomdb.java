package pro.kidss;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MsinData.class}, version = 1, exportSchema = false)
public abstract class Roomdb extends RoomDatabase {
    //creat database instance
    private static Roomdb database;
    //Define database name
    private static String DATABASE_NAME = "gallery.db";

    public abstract MainDao mainDao();

    public synchronized static Roomdb getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder( context.getApplicationContext()
                    , Roomdb.class, DATABASE_NAME )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    //creat dao

}

