package proyecto.ucu.deliverit.main;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.almacenamiento.DataBase;
import proyecto.ucu.deliverit.custom_adapters.CustomAdapterForViajesPublicados;
import proyecto.ucu.deliverit.entidades.Viaje;
import proyecto.ucu.deliverit.excepciones.NegocioException;

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
            getViajes();
        } catch (NegocioException e) {
            Toast.makeText(ViajesActivity.this, R.string.no_se_pudieron_obtener_datos_base, Toast.LENGTH_LONG).show();
        }
    }

    private void getViajes() throws NegocioException {
        try {
            viajes = DB.getViajes();
        } catch (SQLiteException e) {
            e.printStackTrace();
            throw new NegocioException(e.getMessage());
        }
    }
}
