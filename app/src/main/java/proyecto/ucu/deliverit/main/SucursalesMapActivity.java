package proyecto.ucu.deliverit.main;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.almacenamiento.DataBase;
import proyecto.ucu.deliverit.entidades.Sucursal;
import proyecto.ucu.deliverit.entidades.Ubicacion;
import proyecto.ucu.deliverit.tasks.SucursalesViajesTask;
import proyecto.ucu.deliverit.utiles.MapUtils;

public class SucursalesMapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap map;
    private DataBase DB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sucursales_map);

        DB = new DataBase(SucursalesMapActivity.this.getApplicationContext());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        new SucursalesViajesTask(SucursalesMapActivity.this).execute();
    }

    public void direccionesViajesTaskRetorno(List<Sucursal> sucursales) {
        Ubicacion ubicacion = null;

        try {
            ubicacion = DB.getUbicacion();
        } catch (SQLiteException e) {
            Toast.makeText(SucursalesMapActivity.this, R.string.no_se_pudieron_obtener_datos_base, Toast.LENGTH_LONG).show();
        }

        MapUtils.agregarMarkersMain(this.map, ubicacion, sucursales);
    }
}
