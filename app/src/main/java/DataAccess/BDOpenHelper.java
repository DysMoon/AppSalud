package DataAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DBPaciente.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Paciente";

    private static final String CREATE_TABLE_PACIENTE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "IdPaciente INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre VARCHAR(40) NOT NULL, " +
                    "apellido VARCHAR(40) NOT NULL, " +
                    "genero VARCHAR(10) NOT NULL, " +
                    "ciudad VARCHAR(40) NOT NULL, " +
                    "edad INTEGER NOT NULL, " +
                    "dni VARCHAR(8) NOT NULL, " +
                    "peso REAL NOT NULL, " +
                    "altura REAL NOT NULL, " +
                    "foto BLOB" +
                    ");";

    public BDOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PACIENTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
