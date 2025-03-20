package pe.edu.unc.apppersona;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import Models.Persona;

public class AdaptadorPersonas extends BaseAdapter {
    private List<Persona> listaPersona;

    private Context context;
    //para asociar a recurso layput(diseño de la tarjeta)
    LayoutInflater inflater;

    public AdaptadorPersonas(List<Persona> listaPersona, Context contexto) {
        this.listaPersona = listaPersona;
        this.context = contexto;
        inflater= (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listaPersona.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View vista = inflater.inflate(R.layout.ly_item_lista_persona, null);
        ImageView imgFoto = vista.findViewById(R.id.imgItemFoto);
        TextView lbNombre= vista.findViewById(R.id.lbNombreCompleto);
        TextView lbTipoPeso = vista.findViewById(R.id.lbTipoPeso);
        TextView lbTipoPersona = vista.findViewById(R.id.lbTipoPersona);
        ImageView imgSexo = vista.findViewById(R.id.imgItemSexo);
        TextView lbProcedencia = vista.findViewById(R.id.lbProcedencia);
        //Llenar valores em los objetos de tipo View
        Persona oP = listaPersona.get(i);

        imgFoto.setImageURI(listaPersona.get(i).getFoto());
        lbNombre.setText(listaPersona.get(i).getNombreCompleto());
        lbTipoPeso.setText(listaPersona.get(i).getTipoPeso());
        lbProcedencia.setText(listaPersona.get(i).getCiudad());


        lbTipoPersona.setText(oP.getTipoPersona());

        if(oP.getSexo().equals("Femenino"))
            imgSexo.setImageResource(R.drawable.femenino);
        else
            imgSexo.setImageResource(R.drawable.masculno);


        return vista;
    }
}
