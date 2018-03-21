package hernandez.com.iics.mobileattendancemonitoringsystem;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mobile.db";
    private Context context;
    SQLiteDatabase db;

    private final String USER_TABLE = "login";
    private static final String DATABASE_PATH = "/data/data/hernandez.com.iics.mobileattendancemonitoringsystem/databases/";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;
        createdb();
    }
    public void createdb(){
        boolean isExist = checkDBExist();

        if (isExist){
            this.getReadableDatabase();
            copyDatabase();
        }
    }

    private void copyDatabase() {
        try {
            InputStream inputStream = context.getAssets().open(DATABASE_NAME);
            String outFileName = DATABASE_PATH + DATABASE_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);

            byte[] b = new byte[1024];
            int length;

            while ((length = inputStream.read(b))>0){
                outputStream.write(b,0,length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private SQLiteDatabase openDatabase(){
        String path = DATABASE_PATH+DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(path,null, SQLiteDatabase.OPEN_READWRITE);
        return db;
    }
    public void close(){
        if (db != null){
            db.close();
        }
    }

    public boolean checkUserExist(String username, String password){
        String[] columns = {"username"};
        db = openDatabase();
        String selection = "username = ? and password = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(USER_TABLE,columns,selection,selectionArgs,null,null,null);
        int count = 0;
        count = cursor.getCount();
        cursor.close();
        close();
        if (count>0){
            return true;
        } else{
            return false;
        }

    }

    private boolean checkDBExist() {
        SQLiteDatabase sqLiteDatabase = null;
        String path = DATABASE_PATH + DATABASE_NAME;
        sqLiteDatabase = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);
        if (sqLiteDatabase != null){
            sqLiteDatabase.close();
            return true;
        } else{
            return false;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
