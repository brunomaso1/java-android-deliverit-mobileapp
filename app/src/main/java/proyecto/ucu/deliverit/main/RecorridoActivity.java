package proyecto.ucu.deliverit.main;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.almacenamiento.DataBase;
import proyecto.ucu.deliverit.almacenamiento.SharedPref;
import proyecto.ucu.deliverit.entidades.Pedido;
import proyecto.ucu.deliverit.entidades.Ubicacion;
import proyecto.ucu.deliverit.entidades.Viaje;
import proyecto.ucu.deliverit.utiles.RespuestaGeneral;
import proyecto.ucu.deliverit.utiles.Valores;

/**
 * Created by DeliverIT on 25/03/2017.
 */

public class RecorridoActivity extends FragmentActivity implements OnMapReadyCallback {
    DataBase DB;

    Viaje viaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent =  getIntent();
        Integer idViaje = intent.getIntExtra(Valores.VIAJE, 0);

        if (idViaje != 0) {
            try {
                viaje = DB.getViaje(idViaje);
            } catch (SQLiteException e) {
                Toast.makeText(RecorridoActivity.this, R.string.no_se_pudieron_obtener_datos_base, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RecorridoActivity.this, R.string.no_se_recibio_ningun_viaje, Toast.LENGTH_SHORT).show();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        DB = new DataBase(RecorridoActivity.this);
        Ubicacion ubicacion = null;
        try {
            ubicacion = DB.getUbicacion();

            List<Pedido> pedidos = DB.getPedidos(viaje.getId());
            agregarMarkers(map, ubicacion, pedidos);
        } catch (SQLiteException e) {
            Toast.makeText(RecorridoActivity.this, R.string.no_se_pudieron_obtener_datos_base, Toast.LENGTH_SHORT).show();
        }
    }

    public void finalizarViajeTaskRetorno (RespuestaGeneral respuesta) {
        if (respuesta.getCodigo().equals(RespuestaGeneral.CODIGO_OK)) {
            SharedPref.guardarViajeEnCurso(RecorridoActivity.this, 0);
        }
    }

    private void agregarMarkers(GoogleMap mapa, Ubicacion ubicacion, List<Pedido> pedidos) {

        LatLng ubicacionActual = new LatLng(ubicacion.getLatitud(), ubicacion.getLongitud());
        mapa.addMarker(new MarkerOptions().position(ubicacionActual).title(Valores.TU_UBICACION));
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionActual, 16.0f));

        for (Pedido p : pedidos) {
            if (p.getCliente().getDireccion().getLatitud() != null && p.getCliente().getDireccion().getLongitud() != null) {
                LatLng coordenadasCliente = new LatLng(p.getCliente().getDireccion().getLatitud(),
                        p.getCliente().getDireccion().getLongitud());
                mapa.addMarker(new MarkerOptions().position(coordenadasCliente).title(p.getCliente().getNombre())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }
        }
    }
}