package proyecto.ucu.deliverit.inicializacion;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteException;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.almacenamiento.DataBase;
import proyecto.ucu.deliverit.almacenamiento.SharedPref;
import proyecto.ucu.deliverit.entidades.Delivery;
import proyecto.ucu.deliverit.entidades.Usuario;
import proyecto.ucu.deliverit.entidades.Vehiculo;
import proyecto.ucu.deliverit.tasks.CrearDeliveryTask;
import proyecto.ucu.deliverit.tasks.CrearUsuarioTask;
import proyecto.ucu.deliverit.utiles.Operaciones;
import proyecto.ucu.deliverit.utiles.RespuestaGeneral;
import proyecto.ucu.deliverit.utiles.Retorno;
import proyecto.ucu.deliverit.utiles.Valores;

/**
 * Created by DeliverIT on 31/01/2017.
 */

public class RegistroActivity extends AppCompatActivity {
    EditText nombreUsuario_et, password_et, nombre_et, mail_et, telefono_et, cuentaRedPagos_et;
    ImageButton camara_ibtn;
    Button registrarse_btn;
    Spinner vehiculos_sp;

    private Camera camara;
    private Vehiculo vehiculoSeleccionado;

    DataBase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.registro);

        DB = new DataBase(RegistroActivity.this);

        nombreUsuario_et = (EditText) findViewById(R.id.nombreUsuario_et);
        password_et = (EditText) findViewById(R.id.password_et);
        nombre_et = (EditText) findViewById(R.id.nombre_et);
        mail_et = (EditText) findViewById(R.id.mail_et);
        telefono_et = (EditText) findViewById(R.id.telefono_et);
        cuentaRedPagos_et = (EditText) findViewById(R.id.cuentaRedPagos_et);
        camara_ibtn = (ImageButton) findViewById(R.id.camara_ibtn);
        registrarse_btn = (Button) findViewById(R.id.registrarse_btn);
        vehiculos_sp = (Spinner) findViewById(R.id.vehiculos_sp);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.vehiculos_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehiculos_sp.setAdapter(adapter);

        vehiculos_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    vehiculoSeleccionado = DB.getVehiculo(String.valueOf(parent.getItemAtPosition(position)));
                } catch (SQLiteException e) {}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        registrarse_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retorno retorno = Operaciones.validarDatos(nombreUsuario_et.getText().toString(),
                        password_et.getText().toString(),
                        telefono_et.getText().toString(),
                        Integer.parseInt(cuentaRedPagos_et.getText().toString()));

                if (retorno.getCodigo().equals(Valores.CODIGO_EXITO)) {
                    Usuario usuario = new Usuario();
                    usuario.setCuentaRedPagos(Integer.parseInt(cuentaRedPagos_et.getText().toString()));
                    usuario.setMail(mail_et.getText().toString());
                    usuario.setNombre(nombreUsuario_et.getText().toString());
                    usuario.setPassword(password_et.getText().toString());
                    usuario.setTelefono(telefono_et.getText().toString());

                    new CrearUsuarioTask(RegistroActivity.this, usuario).execute();
                } else {
                    Toast.makeText(RegistroActivity.this, retorno.getDescripcion(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        camara_ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraHardware(getApplicationContext())) {
                    try {
                        int idCamara = findFrontFacingCamera();
                        camara = Camera.open(idCamara);
                    } catch (Exception e){
                        Toast.makeText(RegistroActivity.this, R.string.camara_no_disponible, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegistroActivity.this, R.string.no_tiene_camara, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int findFrontFacingCamera() {
        int cameraId = 0;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            cameraId = i;
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                break;
            }
        }
        return cameraId;
    }

    /** Check if this device has a camera */
    public static boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public void crearUsuarioTaskRetorno(RespuestaGeneral respuesta) {

        if (respuesta != null) {

            // Si se inserto el Usuario inserto el Delivery
            if (respuesta.getCodigo().equals(RespuestaGeneral.CODIGO_OK)) {
                Gson gson = new Gson();
                Usuario usuario = gson.fromJson(respuesta.getObjeto(), Usuario.class);


                try {
                    DB.insertarUsuario(usuario);

                    Delivery delivery = new Delivery();
                    delivery.setToken(SharedPref.getToken(RegistroActivity.this));
                    delivery.setUsuario(usuario);
                    delivery.setVehiculo(vehiculoSeleccionado);
                    // setear ubicacion del delivery

                    new CrearDeliveryTask(RegistroActivity.this, delivery).execute();

                } catch (SQLiteException e) {
                    Toast.makeText(RegistroActivity.this, R.string.no_se_pudo_insertar_en_la_base, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(RegistroActivity.this, respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegistroActivity.this, R.string.no_se_pudo_realizar_la_operacion, Toast.LENGTH_SHORT).show();
        }

       // finish();
    }

    public void crearDeliveryTaskRetorno(RespuestaGeneral respuesta) {
        if (respuesta != null) {

            // Si se inserto el Delivery inserto el Delivery
            if (respuesta.getCodigo().equals(RespuestaGeneral.CODIGO_OK)) {
                Gson gson = new Gson();
                Delivery delivery = gson.fromJson(respuesta.getObjeto(), Delivery.class);

                try {
                    DB.insertarUsuario(usuario);


                } catch (SQLiteException e) {
                    Toast.makeText(RegistroActivity.this, R.string.no_se_pudo_insertar_en_la_base, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(RegistroActivity.this, respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegistroActivity.this, R.string.no_se_pudo_realizar_la_operacion, Toast.LENGTH_SHORT).show();
        }
    }
}
