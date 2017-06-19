package proyecto.ucu.deliverit.main;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.almacenamiento.DataBase;
import proyecto.ucu.deliverit.custom_adapters.CustomAdapterForViajesPublicados;
import proyecto.ucu.deliverit.entidades.Viaje;
import proyecto.ucu.deliverit.excepciones.NegocioException;
import proyecto.ucu.deliverit.utiles.Valores;

public class ViajesActivity extends AppCompatActivity {
    private List<Viaje> viajes;
    private ListView viajes_lv;

    public CustomAdapterForViajesPublicados adapter;

    private DataBase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.mis_viajes);

        DB = new DataBase(ViajesActivity.this);

        viajes_lv = (ListView) findViewById(R.id.viajes_lv);

        try {
            viajes = getViajes();
        } catch (NegocioException e) {
            Toast.makeText(ViajesActivity.this, R.string.no_se_pudieron_obtener_datos_base, Toast.LENGTH_LONG).show();
        }
    }

    private List<Viaje> getViajes() throws NegocioException {
        List<Viaje> viajes = new ArrayList<>();
        try {
            Intent intent =  getIntent();
            String activityPadre = intent.getStringExtra(Valores.ACTIVITY_PADRE);

            if (activityPadre.equals(Valores.ACTIVITY_PADRE_INGRESOS)) {
                viajes = DB.getViajesMensuales();
            } else {
                viajes = DB.getViajes();
            }

            if (viajes.size() == 0) {
                Toast.makeText(ViajesActivity.this, R.string.no_finalizo_viaje, Toast.LENGTH_LONG).show();
            } else {
                adapter = new CustomAdapterForViajesPublicados(viajes, ViajesActivity.this);
                viajes_lv.setAdapter(adapter);
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
            throw new NegocioException(e.getMessage());
        }
        return viajes;
    }
}
