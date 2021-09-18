package pro.kidss.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Maindataa.class}, version = 1, exportSchema = false)
public abstract  class Romdb extends RoomDatabase {
        //creat database instance
        private static Romdb database;
        //Define database name
        private static String DATABASE_NAME = "Call.db";

        public abstract Maindaooo mainDao();

        public synchronized static Romdb getInstance(Context context) {
            if (database == null) {
                database = Room.databaseBuilder( context.getApplicationContext()
                        , Romdb.class, DATABASE_NAME )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return database;
        }

        //creat dao

    }
