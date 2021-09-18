package pro.kidss.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MainData.class}, version = 2, exportSchema = false)
public abstract class Roomdbb extends RoomDatabase {
    private static Roomdbb database;
    private static String DATABASE_NAME = "sms.db";
    public abstract MainDaoo mainDao();
    public synchronized static Roomdbb getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder( context.getApplicationContext()
                    , Roomdbb.class, DATABASE_NAME )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
}
