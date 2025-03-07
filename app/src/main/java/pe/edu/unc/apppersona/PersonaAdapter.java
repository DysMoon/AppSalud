package pe.edu.unc.apppersona;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import Models.Persona;

public class PersonaAdapter extends ArrayAdapter<Persona> {

    public PersonaAdapter(Context context, List<Persona> personas) {
        super(context, 0, personas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_persona, parent, false);
        }


        Persona persona = getItem(position);


        TextView tvDni = convertView.findViewById(R.id.tvDni);
        TextView tvNombres = convertView.findViewById(R.id.tvNombres);
        TextView tvDatos = convertView.findViewById(R.id.tvDatos);


        if (persona != null) {
            tvDni.setText("DNI: " + persona.getDni()); // Asegúrate de tener el método getDni()
            tvNombres.setText(persona.getNombres() + " " + persona.getApellidos()); // Métodos getNombre() y getApellido()
            tvDatos.setText(persona.toString()); // Usa toString() o arma la cadena manualmente
        }

        return convertView;
    }
}
