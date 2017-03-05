package proyecto.ucu.deliverit.inicializacion;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteException;
import android.location.Location;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.almacenamiento.DataBase;
import proyecto.ucu.deliverit.almacenamiento.SharedPref;
import proyecto.ucu.deliverit.entidades.Delivery;
import proyecto.ucu.deliverit.entidades.Ubicacion;
import proyecto.ucu.deliverit.main.MainActivity;
import proyecto.ucu.deliverit.tasks.CrearDeliveryTask;
import proyecto.ucu.deliverit.tasks.CrearUbicacionTask;
import proyecto.ucu.deliverit.tasks.EditarUbicacionTask;
import proyecto.ucu.deliverit.utiles.RespuestaGeneral;

/**
 * Created by DeliverIT on 01/02/2017.
 */

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {
    private final int PETICION_PERMISO_LOCALIZACION = 1000;

    private DataBase DB;

    Button registrarse_btn, ingresar_btn;

    GoogleApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.home);

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

        registrarse_btn = (Button) findViewById(R.id.registrarse_btn);
        ingresar_btn = (Button) findViewById(R.id.ingresar_btn);

        // Si nadie se logueó
        if (SharedPref.getIdDelivery(getApplicationContext()) == 0) {

            registrarse_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, RegistroActivity.class);
                    startActivity(intent);
                }
            });

            ingresar_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
        // Si ya existe un Usuario logueado va directo a la activity principal
        else {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PETICION_PERMISO_LOCALIZACION);
        } else {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);

            // Si el usuario otorgo permisos ya existe una Ubicación en la base
            // El valor true es para actualizar esa Ubicación
            enviarUbicacion(lastLocation, true);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(HomeActivity.this, R.string.problema_de_conexion_con_google, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PETICION_PERMISO_LOCALIZACION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Permiso concedido
                @SuppressWarnings("MissingPermission")
                Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);

                // El usuario acaba de otorgar permisos para obtener la Ubicación
                // El valor false indica que se debe insertar la Ubicación en el sistema
                enviarUbicacion(lastLocation, false);

            } else {
                //Permiso denegado
                finish();
            }
        }
    }

    private void enviarUbicacion(Location location, boolean insertado) {
        if (!insertado) {
            Ubicacion ubicacion = new Ubicacion();
            ubicacion.setLatitud(location.getLatitude());
            ubicacion.setLongitud(location.getLongitude());

            new CrearUbicacionTask(HomeActivity.this, ubicacion).execute();
        } else {
            DataBase DB = new DataBase(HomeActivity.this);
            Ubicacion ubicacion = DB.getUbicacion();
            new EditarUbicacionTask(HomeActivity.this, ubicacion).execute();
        }
    }

    public void crearUbicacionTaskRetorno(RespuestaGeneral respuesta) {
        if (respuesta != null) {

            // Si se inserto la Ubicación en la base del sistema
            if (respuesta.getCodigo().equals(RespuestaGeneral.CODIGO_OK)) {
                Gson gson = new Gson();
                Ubicacion ubicacion = gson.fromJson(respuesta.getObjeto(), Ubicacion.class);

                try {
                    // Insertamos la ubicación en la base del dispositivo
                    DB = new DataBase(HomeActivity.this);
                    DB.insertarUbicacion(ubicacion);
                } catch (SQLiteException e) {
                    Toast.makeText(HomeActivity.this, R.string.no_se_pudo_insertar_en_la_base, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(HomeActivity.this, R.string.no_se_pudo_insertar_en_la_base, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(HomeActivity.this, respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(HomeActivity.this, R.string.no_se_pudo_realizar_la_operacion, Toast.LENGTH_SHORT).show();
        }
    }

    public void editarUbicacionTaskRetorno(RespuestaGeneral respuesta) {
        if (respuesta != null) {
            Gson gson = new Gson();
            Ubicacion ubicacion = gson.fromJson(respuesta.getObjeto(), Ubicacion.class);

            try {
                // Insertamos la ubicación en la base del dispositivo
                DB = new DataBase(HomeActivity.this);
                DB.actualizarUbicacion(ubicacion);
            } catch (SQLiteException e) {
                Toast.makeText(HomeActivity.this, R.string.no_se_pudo_insertar_en_la_base, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(HomeActivity.this, R.string.no_se_pudo_insertar_en_la_base, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(HomeActivity.this, R.string.no_se_pudo_realizar_la_operacion, Toast.LENGTH_SHORT).show();
        }
    }
}
