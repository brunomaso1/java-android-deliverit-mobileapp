package proyecto.ucu.deliverit.main;

import android.app.NotificationManager;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.almacenamiento.DataBase;
import proyecto.ucu.deliverit.almacenamiento.SharedPref;
import proyecto.ucu.deliverit.entidades.Pedido;
import proyecto.ucu.deliverit.entidades.Viaje;
import proyecto.ucu.deliverit.tasks.AceptarViajeTask;
import proyecto.ucu.deliverit.tasks.FinalizarViajeTask;
import proyecto.ucu.deliverit.utiles.Operaciones;
import proyecto.ucu.deliverit.utiles.RespuestaGeneral;
import proyecto.ucu.deliverit.utiles.Valores;

public class NotificacionActivity extends AppCompatActivity {
    private TextView razonSocial_tv, direccion_tv, precio_tv;
    private ImageView restaurant_iv;
    private Button aceptar_btn, rechazar_btn;
    private ImageButton mapa_ibtn;
    private ListView pedidos_lv;

    private DataBase DB;

    private Viaje viaje;
    private List<Pedido> pedidos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.notificacion);

        DB = new DataBase(NotificacionActivity.this);

        razonSocial_tv = (TextView) findViewById(R.id.razonSocial_tv);
        direccion_tv = (TextView) findViewById(R.id.direccion_tv);
        precio_tv = (TextView) findViewById(R.id.precio_tv);
        restaurant_iv = (ImageView) findViewById(R.id.restaurant_iv);
        aceptar_btn = (Button) findViewById(R.id.aceptar_btn);
        rechazar_btn = (Button) findViewById(R.id.rechazar_btn);
        mapa_ibtn = (ImageButton) findViewById(R.id.mapa_ibtn);
        pedidos_lv = (ListView) findViewById(R.id.pedidos_lv);

        Intent intent =  getIntent();
        final Integer idViaje = intent.getIntExtra(Valores.VIAJE, 0);

        if (idViaje != 0) {
            try {
                viaje = DB.getViaje(idViaje);
                pedidos = DB.getPedidos(viaje.getId());
            } catch (SQLiteException e) {
                Toast.makeText(NotificacionActivity.this, R.string.no_se_pudieron_obtener_datos_base, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(NotificacionActivity.this, R.string.no_se_recibio_ningun_viaje, Toast.LENGTH_LONG).show();
        }

        List<String> pedidosString = new ArrayList<>();
        for (int i = 0; i < pedidos.size(); i++) {
            String pedido = "Dir. " + (i + 1) + ": " +  pedidos.get(i).getCliente().getDireccion().getCalle()
                    + " " + pedidos.get(i).getCliente().getDireccion().getNroPuerta();
            if (pedidos.get(i).getCliente().getDireccion().getApartamento() != null) {
                pedido = pedido + " apto. " + pedidos.get(i).getCliente().getDireccion().getApartamento();
            }
            pedidosString.add(pedido);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                pedidosString);

        pedidos_lv.setAdapter(arrayAdapter);

        razonSocial_tv.setText("Local: " + viaje.getSucursal().getRestaurant().getRazonSocial());
        precio_tv.setText("Precio: $" + viaje.getPrecio());
        direccion_tv.setText("Dirección: " + viaje.getSucursal().getDireccion().getCalle()
            + " " + viaje.getSucursal().getDireccion().getNroPuerta() + " esq. " + viaje.getSucursal().getDireccion().getEsquina());


        byte[] imgRestaurant = null;

        try {
            imgRestaurant = Operaciones.decodeImage(viaje.getSucursal().getRestaurant().getFoto());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bitmap imgBitmap = BitmapFactory.decodeByteArray(imgRestaurant, 0, imgRestaurant.length);
        restaurant_iv.setImageBitmap(imgBitmap);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Cancelamos la Notificacion que hemos comenzado
        nm.cancel(getIntent().getExtras().getInt(Valores.NOTIFICATOIN_ID_TEXTO));

        rechazar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    eliminarPedidosEnCascada();
                } catch (SQLiteException e) {
                    Toast.makeText(NotificacionActivity.this, R.string.no_se_pudo_realizar_la_operacion, Toast.LENGTH_LONG).show();
                }

                finish();
            }
        });

        aceptar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new AceptarViajeTask(NotificacionActivity.this,
                        (int)SharedPref.getIdDelivery(NotificacionActivity.this), viaje.getId()).execute();
            }
        });

        mapa_ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificacionActivity.this, RecorridoActivity.class);
                intent.putExtra(Valores.VIAJE, viaje.getId());
                startActivity(intent);
            }
        });
    }

    public void aceptarTaskRetorno(Integer retorno) {
        if (retorno == Integer.parseInt(Valores.CODIGO_EXITO)) {
            SharedPref.guardarViajeEnCurso(NotificacionActivity.this, viaje.getId());

            aceptar_btn.setVisibility(View.INVISIBLE);
            Button finalizar_btn = (Button)findViewById(R.id.aceptar_btn);
            finalizar_btn.setText("FINALIZAR");
            finalizar_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new FinalizarViajeTask(NotificacionActivity.this, viaje.getId()).execute();
                }
            });
        } else {
            Toast.makeText(NotificacionActivity.this, R.string.viaje_tomado, Toast.LENGTH_LONG).show();
        }
    }

    public void finalizarViajeTaskRetorno() {

        finish();
    }


    private void eliminarPedidosEnCascada () throws SQLiteException {
        DB = new DataBase(NotificacionActivity.this);
        for (Pedido pedido : this.pedidos) {
            DB.eliminarPedido(pedido.getId());
            DB.eliminarCliente(pedido.getCliente().getId());
            DB.eliminarDireccion(pedido.getCliente().getDireccion().getId());

            // La sucursal se elimina sólo si el delivery nunca realizó un viaje proveniente de ella
            if (DB.getViajeSucursal(pedido.getViaje().getId(), pedido.getViaje().getSucursal().getId()) == 0) {
                DB.eliminarSucursal(pedido.getViaje().getSucursal().getId());
                DB.eliminarDireccion(pedido.getViaje().getSucursal().getDireccion().getId());
            }

        }

        DB.eliminarViaje(viaje.getId());
        this.pedidos.clear();
    }
}
