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
                  System.out.println("*** clickeo = " + position);
              }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, drawer_layout, R.string.sidebar_open, R.string.sidebar_close) {
            public void onDrawerClosed(View view) {
                System.out.println("*** sidebar close ***");
                //getActionBar().setTitle(mTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                System.out.println("*** sidebar open ***");
                //getActionBar().setTitle(mDrawerTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawer_layout.setDrawerListener(mDrawerToggle);

    }

    @Override
    protected void onStart() {
        super.onStart();
        new ViajesPublicadosTask(MainActivity.this).execute();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu()
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = drawer_layout.isDrawerOpen(sidebar_lv);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }*/

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

   /* private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        System.out.println("*** clickeo = " + position);
        // update the main content by replacing fragments
        /*Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    } */
}
