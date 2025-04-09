package pe.edu.unc.apppersona.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import DataAccess.DAOPaciente;
import Models.Paciente;
import pe.edu.unc.apppersona.R;

public class AdapterPacientes extends BaseAdapter {

    private final DAOPaciente daoPaciente;
    private final Context context;
    private final LayoutInflater layoutInflater;

    public AdapterPacientes(DAOPaciente daoPaciente, Context context) {
        this.daoPaciente = daoPaciente;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return daoPaciente.getSize();
    }

    @Override
    public Object getItem(int position) {
        return daoPaciente.getPaciente(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewPaciente = layoutInflater.inflate(R.layout.ly_item_list, null);

        ImageView imgItemFoto = viewPaciente.findViewById(R.id.imgItemFoto);
        TextView lbNombreCompleto = viewPaciente.findViewById(R.id.lbNombreCompleto);
        TextView lbTipoPeso = viewPaciente.findViewById(R.id.lbTipoPeso);
        TextView lbTipoPersona = viewPaciente.findViewById(R.id.lbTipoPersona);
        ImageView imgItemSexo = viewPaciente.findViewById(R.id.imgItemSexo);
        TextView lbProcedencia = viewPaciente.findViewById(R.id.lbProcedencia);

        Paciente paciente = daoPaciente.getPaciente(position);

        if (paciente.getImgFoto() != null) {
            imgItemFoto.setImageBitmap(convertToBitmap(paciente.getImgFoto()));
        } else {
            imgItemFoto.setImageResource(R.drawable.click); // imagen por defecto
        }

        lbNombreCompleto.setText(paciente.nombreCompleto());
        lbTipoPeso.setText(paciente.pesoPaciente());
        lbProcedencia.setText(paciente.getCiudad());
        lbTipoPersona.setText(paciente.edadPaciente());

        if (paciente.getGenero().equals("Male")) {
            imgItemSexo.setImageResource(R.drawable.male);
        } else {
            imgItemSexo.setImageResource(R.drawable.female);
        }

        return viewPaciente;
    }

    private Bitmap convertToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
