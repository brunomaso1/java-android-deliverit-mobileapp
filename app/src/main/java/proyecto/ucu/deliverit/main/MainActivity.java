package proyecto.ucu.deliverit.main;

import android.database.sqlite.SQLiteException;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.almacenamiento.DataBase;
import proyecto.ucu.deliverit.almacenamiento.SharedPref;
import proyecto.ucu.deliverit.entidades.Ubicacion;
import proyecto.ucu.deliverit.tasks.FinalizarViajeTask;
import proyecto.ucu.deliverit.utiles.RespuestaGeneral;

/**
 * Created by DeliverIT on 31/01/2017.
 */

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    DataBase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        DB = new DataBase(MainActivity.this);
        Ubicacion ubicacion = null;
        try {
            ubicacion = DB.getUbicacion();
        } catch (SQLiteException e) {}

        LatLng ubicacionActual = new LatLng(ubicacion.getLatitud(), ubicacion.getLongitud());
        map.addMarker(new MarkerOptions().position(ubicacionActual).title("Ubicación Actual"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionActual, 16.0f));
    }

    public void finalizarViajeTaskRetorno (RespuestaGeneral respuesta) {
        if (respuesta.getCodigo().equals(RespuestaGeneral.CODIGO_OK)) {
            SharedPref.guardarViajeEnCurso(MainActivity.this, 0);
        }
    }
}
