package DataAccess;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Models.Paciente;

public class DAOPaciente {

    private final List<Paciente> pacienteList;

    public DAOPaciente() {
        this.pacienteList = new ArrayList<>();
    }

    public boolean addPaciente(Activity context, Paciente paciente) {
        boolean response = false;
        BDOpenHelper dbHelper = new BDOpenHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (db != null && db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put("nombre", paciente.getNombre());
            values.put("apellido", paciente.getApellido());
            values.put("genero", paciente.getGenero());
            values.put("ciudad", paciente.getCiudad());
            values.put("edad", paciente.getEdad());
            values.put("dni", paciente.getDni());
            values.put("peso", paciente.getPeso());
            values.put("altura", paciente.getAltura());
            values.put("foto", paciente.getFoto());

            long row = db.insert("Paciente", null, values);
            response = row > 0;

            db.close();
            dbHelper.close();
        }
        return response;
    }

    public boolean cargar(Activity context) {
        boolean response = false;
        pacienteList.clear();  // evitar duplicados

        BDOpenHelper dbHelper = new BDOpenHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor records = db.rawQuery("SELECT * FROM Paciente", null);

        if (records.moveToFirst()) {
            response = true;
            do {
                String nombre = records.getString(1);
                String apellido = records.getString(2);
                String genero = records.getString(3);
                String ciudad = records.getString(4);
                int edad = records.getInt(5);
                String dni = records.getString(6);
                double peso = records.getDouble(7);
                double altura = records.getDouble(8);
                byte[] foto = records.getBlob(9);

                Paciente paciente = new Paciente(nombre, apellido, genero, ciudad, edad, dni, peso, altura, foto);
                pacienteList.add(paciente);
            } while (records.moveToNext());
        }

        records.close();
        db.close();
        dbHelper.close();

        return response;
    }

    public List<Paciente> getPatientList() {
        return pacienteList;
    }

    public int getSize() {
        return pacienteList.size();
    }

    public Paciente getPaciente(int index) {
        return pacienteList.get(index);
    }
}
