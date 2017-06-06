package proyecto.ucu.deliverit.main;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;

import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.almacenamiento.DataBase;
import proyecto.ucu.deliverit.entidades.Pedido;
import proyecto.ucu.deliverit.entidades.Viaje;
import proyecto.ucu.deliverit.tasks.SolicitarPedidosTask;
import proyecto.ucu.deliverit.tasks.ViajesPublicadosTask;
import proyecto.ucu.deliverit.utiles.CustomAdapter;
import proyecto.ucu.deliverit.utiles.Valores;

public class MainActivity extends AppCompatActivity {
    private List<Viaje> viajes;
    private Viaje viaje;

    private String[] opciones_sidebar_array;

    private ListView viajes_lv, sidebar_lv;
    private ImageButton mapa_ibtn;
    private DrawerLayout drawer_layout;
    private ActionBarDrawerToggle mDrawerToggle;

    private CustomAdapter adapter;

    private DataBase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        DB = new DataBase(MainActivity.this);

        opciones_sidebar_array = getResources().getStringArray(R.array.opciones_sidebar_array);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        viajes_lv = (ListView) findViewById(R.id.viajes_lv);
        mapa_ibtn = (ImageButton) findViewById(R.id.mapa_ibtn);
        sidebar_lv = (ListView) findViewById(R.id.sidebar_lv);

        sidebar_lv.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, opciones_sidebar_array));
        sidebar_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              switch (position) {
                  case 6: // Salir
                    finish();
              }
              }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, drawer_layout, R.string.sidebar_open, R.string.sidebar_close) {
            public void onDrawerClosed(View view) {}

            public void onDrawerOpened(View drawerView) {}
        };
        drawer_layout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new ViajesPublicadosTask(MainActivity.this).execute();
    }

    public void viajesPublicadosTaskRetorno (List<Viaje> viajesPublicados) {
        if (viajesPublicados == null) {
            Toast.makeText(MainActivity.this, "No se pudieron obtener los viajes", Toast.LENGTH_LONG).show();
        } else {
            viajes = viajesPublicados;

            adapter = new CustomAdapter(viajesPublicados, MainActivity.this);
            viajes_lv.setAdapter(adapter);

            viajes_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    viaje = viajes.get(position);
                    new SolicitarPedidosTask(MainActivity.this, viaje.getId()).execute();
                }
            });

            mapa_ibtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viajes.size() > 0) {
                        Intent intent = new Intent(MainActivity.this, SucursalesMapActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, R.string.no_existen_viajes_disponibles, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void solicitarPedidosTaskRetorno(List<Pedido> pedidos) {
        try {
            DB.guardarDatosPedidos(pedidos);
        } catch (SQLiteException e) {
            Toast.makeText(MainActivity.this, R.string.no_se_pudo_insertar_en_la_base, Toast.LENGTH_LONG).show();
        }

        Intent i = new Intent(getApplicationContext(), NotificacionActivity.class);
        i.putExtra(Valores.NOTIFICATOIN_ID_TEXTO, Valores.NOTIFICATION_ID);
        i.putExtra(Valores.VIAJE, viaje.getId());
        startActivity(i);
    }
}
