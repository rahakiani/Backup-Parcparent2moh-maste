package pro.kidss;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class sqliteDB extends SQLiteOpenHelper {

    final static String DATABASE_NAME = "videomanager";
    final static int DATABASE_VERSION = 1;
    final static String TABLE_VIDEO = "videos";

    final static String KET_NAME = "name";
    final static String KEY_ID = "id";

    public sqliteDB(Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREAT_VIDEO_TABLE =
                "CREATE TABLE " + TABLE_VIDEO +
                        "(" + KEY_ID + " INTEGER PRIMARY KEY," +
                        KET_NAME + " TEXT" + ")";
        sqLiteDatabase.execSQL( CREAT_VIDEO_TABLE );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL( "DROP TABLE IF EXISTS " + TABLE_VIDEO );
        onCreate( sqLiteDatabase );

    }

    public void addVideo(Video video) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( KET_NAME, video.getName() );
        contentValues.put( KEY_ID, video.getId() );
        db.insert( TABLE_VIDEO, null, contentValues );
        db.close();

    }

    public Video getvideos(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query( TABLE_VIDEO, new String[]{
                        KEY_ID, KET_NAME},
                KEY_ID + "=?",
                new String[]{String.valueOf( id )}, null, null, null, null );
        if (cursor != null) {
            cursor.moveToFirst();
            Video video = new Video( Integer.parseInt( cursor.getString( 0 ) ),
                    cursor.getString( 1 ),
                    cursor.getString( 2 ) );
            return video;
        }

        }
}
