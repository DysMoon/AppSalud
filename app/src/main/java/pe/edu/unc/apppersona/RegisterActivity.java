package pe.edu.unc.apppersona;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;

import DataAccess.DAOPaciente;
import Models.Paciente;
import Models.PacienteAPI;
import Network.ApiServicioSalud;
import Network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText txtNombre, txtApellido, txtEdad, txtDni, txtPeso, txtAltura;
    private RadioGroup rgGenero;
    private Spinner spCiudad;
    private ImageView imgFoto;
    private byte[] imgSeleccionada;

    private final String[] city = {"Seleccionar ciudad", "Cajamarca", "Chiclayo", "Trujillo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_register);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        rgGenero = findViewById(R.id.rgGenero);
        spCiudad = findViewById(R.id.spCiudad);
        txtEdad = findViewById(R.id.txtEdad);
        txtDni = findViewById(R.id.txtDni);
        txtPeso = findViewById(R.id.txtPeso);
        txtAltura = findViewById(R.id.txtAltura);
        imgFoto = findViewById(R.id.imgFoto);

        spCiudad.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, city));
        spCiudad.setSelection(0);

        Toolbar toolbar = findViewById(R.id.toolbarRegistrar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        Button btnListar = findViewById(R.id.btnListar);

        btnRegistrar.setOnClickListener(v -> registrarPacientes());
        btnListar.setOnClickListener(v -> listarPacientes());
        imgFoto.setOnClickListener(v -> seleccionarFoto());
    }

    private void listarPacientes() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    private void registrarPacientes() {
        if (validar()) {
            registrarPacienteEnBD();
        }
    }

    private void registrarPacienteEnBD() {
        String nombre = txtNombre.getText().toString().trim();
        String apellido = txtApellido.getText().toString().trim();
        String genero = getGenero();
        int edad = Integer.parseInt(txtEdad.getText().toString().trim());
        String ciudad = spCiudad.getSelectedItem().toString();
        String dni = txtDni.getText().toString().trim();
        double peso = Double.parseDouble(txtPeso.getText().toString().trim());
        double altura = Double.parseDouble(txtAltura.getText().toString().trim());

        String foto = "foto_referencia"; // corrección: ya no usamos getText() en imgSeleccionada

        PacienteAPI paciente = new PacienteAPI(nombre, apellido, genero, ciudad, edad, dni, peso, altura, foto);
        paciente.setImgFoto(imgSeleccionada);

        DAOPaciente daoPaciente = new DAOPaciente();
        daoPaciente.addPaciente(this, paciente);
        Toast.makeText(this, "Paciente registrado correctamente", Toast.LENGTH_SHORT).show();

        if (Paciente.verificarDNI(dni)) {
            enviarPost(paciente);
        } else {
            Toast.makeText(this, "No se pudo registrar el paciente", Toast.LENGTH_SHORT).show();
            txtDni.requestFocus();
        }
    }

    private void enviarPost(PacienteAPI paciente) {
        ApiServicioSalud apiServicioSalud = RetrofitClient.getClient().create(ApiServicioSalud.class);
        Call<PacienteAPI> call = apiServicioSalud.PostPacientes(paciente);
        call.enqueue(new Callback<PacienteAPI>() {
            @Override
            public void onResponse(Call<PacienteAPI> call, Response<PacienteAPI> response) {
                if (response.isSuccessful()) {
                    mostrarDialogo();
                } else {
                    Toast.makeText(RegisterActivity.this, "Error al registrar el paciente", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PacienteAPI> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarDialogo() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Aviso");
        dialog.setMessage("¿Desea seguir registrando?");
        dialog.setIcon(R.drawable.baseline_add_alert_24);
        dialog.setNegativeButton("No", (d, which) -> finish());
        dialog.setPositiveButton("Sí", (d, which) -> limpiar());
        dialog.create().show();
    }

    private boolean validar() {
        if (txtNombre.getText().toString().trim().isEmpty()) {
            txtNombre.setError("Nombre(s) del paciente obligatorio");
            txtNombre.requestFocus();
            return false;
        } else if (txtApellido.getText().toString().trim().isEmpty()) {
            txtApellido.setError("Apellidos del paciente obligatorio");
            txtApellido.requestFocus();
            return false;
        } else if (!generoValido()) {
            Toast.makeText(this, "Debe seleccionar un género", Toast.LENGTH_LONG).show();
            rgGenero.requestFocus();
            return false;
        } else if (!ciudadValida()) {
            Toast.makeText(this, "Debe seleccionar una ciudad", Toast.LENGTH_LONG).show();
            spCiudad.requestFocus();
            return false;
        } else if (txtEdad.getText().toString().trim().isEmpty()) {
            txtEdad.setError("Edad del paciente obligatoria");
            txtEdad.requestFocus();
            return false;
        } else if (txtDni.getText().toString().trim().isEmpty()) {
            txtDni.setError("N° de DNI obligatorio");
            txtDni.requestFocus();
            return false;
        } else if (!Paciente.verificarDNI(txtDni.getText().toString().trim())) {
            txtDni.setError("Ingrese los 8 dígitos correctamente");
            txtDni.requestFocus();
            return false;
        } else if (txtPeso.getText().toString().trim().isEmpty()) {
            txtPeso.setError("Peso del paciente obligatorio");
            txtPeso.requestFocus();
            return false;
        } else if (txtAltura.getText().toString().trim().isEmpty()) {
            txtAltura.setError("Altura del paciente obligatoria");
            txtAltura.requestFocus();
            return false;
        } else if (imgSeleccionada == null) {
            Toast.makeText(this, "Debe seleccionar una foto", Toast.LENGTH_LONG).show();
            imgFoto.requestFocus();
            return false;
        }
        return true;
    }

    private boolean ciudadValida() {
        return spCiudad.getSelectedItemPosition() != 0;
    }

    private boolean generoValido() {
        int idGenero = rgGenero.getCheckedRadioButtonId();
        return idGenero == R.id.rbFemenino || idGenero == R.id.rbMasculino;
    }

    private String getGenero() {
        int id = rgGenero.getCheckedRadioButtonId();
        if (id == R.id.rbFemenino) return "Female";
        if (id == R.id.rbMasculino) return "Male";
        return "";
    }

    private void seleccionarFoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityIfNeeded(Intent.createChooser(intent, "Seleccionar Imagen"), 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uploadedPhoto = data.getData();
            imgFoto.setImageURI(uploadedPhoto);
            BitmapDrawable drawable = (BitmapDrawable) imgFoto.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imgSeleccionada = stream.toByteArray();
        } else {
            Toast.makeText(this, "No seleccionaste imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiar() {
        txtNombre.setText("");
        txtApellido.setText("");
        rgGenero.clearCheck();
        spCiudad.setSelection(0);
        txtEdad.setText("");
        txtDni.setText("");
        txtPeso.setText("");
        txtAltura.setText("");
        imgFoto.setImageResource(R.drawable.click);
        imgSeleccionada = null;
    }
}
