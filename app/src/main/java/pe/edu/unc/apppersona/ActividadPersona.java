package pe.edu.unc.apppersona;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import java.util.ArrayList;
import java.util.List;

import Models.Persona;

public class ActividadPersona extends AppCompatActivity {

    EditText txtNombres, txtApellidos, txtEdad, txtDni, txtPeso, txtAltura;

    RadioGroup rgSexo; //si ya se pone esto ya no es necesario los radioboton
    Spinner sp_ciudad;
    ImageView imgFoto;

    Button btnRegistrar, btnListar;

    String[] ciudad = {"Seleccionar ciudad","Lima", "Arequipa", "Trujillo"};
    Uri imgSeleccionado = null;
    private Persona oPersona;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ly_persona);

        Toolbar oBarra = findViewById(R.id.toolbarRegistrarPersonas);
        setSupportActionBar(oBarra);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        // Inicializar la lista para evitar NullPointerException


        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtEdad = findViewById(R.id.txtEdad);
        txtDni = findViewById(R.id.txtDni);
        txtPeso = findViewById(R.id.txtPeso);
        txtAltura = findViewById(R.id.txtAltura);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnListar = findViewById(R.id.btnListar);
        rgSexo = findViewById(R.id.rgSexo);
        sp_ciudad = findViewById(R.id.sp_ciudad);
        //cargar los item para sp_ciudad(lista)
        sp_ciudad.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ciudad));
        sp_ciudad.setSelection(0);

        imgFoto = findViewById(R.id.imgFoto);
        imgFoto.setOnClickListener(v -> seleccionarFoto());//para que sea desde la galeria

        btnRegistrar.setOnClickListener(v -> registrarPersona());
        btnListar.setOnClickListener(v -> listarPersonas());

    }
    //Metodo(Superclase) que recoge la imagen seleccionada

    private void seleccionarFoto(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //filtrar los archivos de tipo imagen
        intent.setType("image/*");
        //startActivityForResult(intent, 1);
        startActivityIfNeeded(Intent.createChooser(intent, "Selecciona una imagen"), 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 100 && resultCode == RESULT_OK && intent != null && intent.getData() != null) {
            imgSeleccionado = intent.getData();
            imgFoto.setImageURI(imgSeleccionado);
        }
    }


    private void registrarPersona() {

        if (validarControles())
            return;

        try {
            // Asignación en bloque, manteniendo tu estructura
            String nombres = txtNombres.getText().toString().trim();
            String apellidos = txtApellidos.getText().toString().trim();
            String sexo = obtenerseleccionarSexo();
            String ciudad = sp_ciudad.getSelectedItem() != null ? sp_ciudad.getSelectedItem().toString() : "";
            int edad = Integer.parseInt(txtEdad.getText().toString().trim());
            String dni = txtDni.getText().toString().trim();
            double peso = Double.parseDouble(txtPeso.getText().toString().trim());
            double altura = Double.parseDouble(txtAltura.getText().toString().trim());

            // Crear el objeto Persona
            Persona persona = new Persona(nombres, apellidos, sexo, ciudad, edad, dni, peso, altura, imgSeleccionado);
//            listaPersonas.add(persona);
                ActividadPrincipal.listaPersonas.add(oPersona);
//Habia una condicional DNI
            // Confirmación del registro
            Toast.makeText(this, "Persona registrada correctamente", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, persona.toString(), Toast.LENGTH_LONG).show();

            // Limpiar los campos después del registro

            cuadroDialogo();
            limpiar();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ingrese valores numéricos válidos en Edad, Peso y Altura", Toast.LENGTH_SHORT).show();
        }
    }

    private void cuadroDialogo() {

        AlertDialog.Builder oDialogo = new AlertDialog.Builder(this);
        oDialogo.setTitle("Aviso");
        oDialogo.setMessage("Desea seguir registrando?");
        oDialogo.setIcon(R.drawable.baseline_add_alert_24);
        oDialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ActividadPersona.this.finish();
            }
        });
        oDialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                limpiar();
            }
        });
        oDialogo.create();
        oDialogo.show();
    }


    private boolean validarControles() {
        if (ComprobarCampoObligatorio(txtNombres, "Nombres") ||
                ComprobarCampoObligatorio(txtApellidos, "Apellidos") ||
                (obtenerseleccionarSexo().isEmpty() && mostrarMensaje("Debe seleccionar un sexo")) ||
                (sp_ciudad.getSelectedItemPosition() == 0 && mostrarMensaje("Debe seleccionar una ciudad")) ||
                ComprobarCampoObligatorio(txtEdad, "Edad") ||
                ComprobarCampoObligatorio(txtDni, "DNI") ||
                (!verificarDNI(txtDni.getText().toString().trim()) && mostrarMensaje("DNI incorrecto")) ||
                ComprobarCampoObligatorio(txtPeso, "Peso") ||
                ComprobarCampoObligatorio(txtAltura, "Altura") ||
                (imgSeleccionado == null && mostrarMensaje("Debe seleccionar una imagen de galería"))) {
            return true;
        }
        return false;
    }



    public static boolean verificarDNI(String dni) {
        return dni != null && dni.matches("\\d{8}");
    }
    private boolean mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        rgSexo.requestFocus();
        rgSexo.check(R.id.rbFemenino);
        return true;
    }


    private boolean ComprobarCampoObligatorio(EditText campo, String mensajeError) {
        if (campo.getText().toString().trim().isEmpty()) {
            campo.setError("Campo" + mensajeError + "obligatorio");
            campo.requestFocus();
            return true;
        }
        return false;
    }


    private String obtenerseleccionarSexo() {
        int identificador = rgSexo.getCheckedRadioButtonId();
        if (identificador == R.id.rbFemenino) return "Femenino";
        if(identificador == R.id.rbMasculino) return "Masculino";
        return "";

    }

    private void listarPersonas() {
        // Convertir lista de objetos Persona a lista de Strings
        ArrayList<String> listaPersonasString = new ArrayList<>();
        for (Persona p : ActividadPrincipal.listaPersonas)
            listaPersonasString.add(p.getNombres() + "," + p.getApellidos() + "," + p.getSexo() + "," +
                    p.getCiudad() + "," + p.getEdad() + "," + p.getDni() + "," + p.getPeso() + "," + p.getAltura());

        Intent intent = new Intent(this, ActividadListadoPersonas.class);
        intent.putStringArrayListExtra("listaPersonas", listaPersonasString); // Enviar lista de Strings
        startActivity(intent);

    }

    private void limpiar() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtEdad.setText("");
        txtDni.setText("");
        txtPeso.setText("");
        txtAltura.setText("");
        rgSexo.check(R.id.rbFemenino);
        sp_ciudad.setSelection(0);
        imgFoto.setImageResource(R.drawable.click);
        imgSeleccionado = null;
        txtNombres.requestFocus();
    }

    public void onClickListar(View boton) {
        Intent intent = new Intent(this, ActividadListadoPersonas.class);
        startActivity(intent);
    }
}
