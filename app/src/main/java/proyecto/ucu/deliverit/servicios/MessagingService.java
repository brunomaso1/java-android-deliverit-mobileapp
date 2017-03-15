package proyecto.ucu.deliverit.servicios;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.almacenamiento.DataBase;
import proyecto.ucu.deliverit.almacenamiento.SharedPref;
import proyecto.ucu.deliverit.entidades.Direccion;
import proyecto.ucu.deliverit.entidades.Restaurant;
import proyecto.ucu.deliverit.entidades.Sucursal;
import proyecto.ucu.deliverit.entidades.Usuario;
import proyecto.ucu.deliverit.entidades.Viaje;
import proyecto.ucu.deliverit.main.NotificacionActivity;
import proyecto.ucu.deliverit.utiles.Valores;

/**
 * Created by DeliverIT on 15/02/2017.
 */

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage message) {
        Map<String, String> datos = message.getData();

        Viaje viaje = new Viaje();
        JSONObject viajeJSON;

        String viajeString = datos.get(Valores.VIAJE);

        try {

            viajeJSON = new JSONObject(viajeString);

            JSONObject sucursalJSON = viajeJSON.getJSONObject(Viaje.SUCURSAL);
            JSONObject direccionJSON = sucursalJSON.getJSONObject(Valores.SUCURSAL_DIRECCION);
            JSONObject restaurantJSON = sucursalJSON.getJSONObject(Valores.SUCURSAL_RESTAURANT);
            JSONObject usuarioJSON = restaurantJSON.getJSONObject(Valores.RESTAURANT_USUARIO);

            Usuario usuario = new Usuario();
            usuario.setId(usuarioJSON.getInt(Valores.USUARIO_ID));
            usuario.setTelefono(usuarioJSON.getString(Valores.USUARIO_TELEFONO));

            Direccion direccion = new Direccion();
            direccion.setId(direccionJSON.getInt(Valores.DIRECCION_ID));
            direccion.setCalle(direccionJSON.getString(Valores.DIRECCION_CALLE));
            direccion.setEsquina(direccionJSON.getString(Valores.DIRECCION_ESQUINA));
            direccion.setNroPuerta(direccionJSON.getInt(Valores.DIRECCION_NRO_PUERTA));
            direccion.setApartamento((short)direccionJSON.getInt(Valores.DIRECCION_APARTAMENTO));
            direccion.setLatitud(direccionJSON.getDouble(Valores.DIRECCION_LATITUD));
            direccion.setLongitud(direccionJSON.getDouble(Valores.DIRECCION_LONGITUD));

            Restaurant restaurant = new Restaurant();
            restaurant.setId(restaurantJSON.getInt(Valores.RESTARURAN_ID));
            restaurant.setRazonSocial(restaurantJSON.getString(Valores.RESTAURANT_RAZON_SOCIAL));
            restaurant.setRut(restaurantJSON.getInt(Valores.RESTAURANT_RUT));
            restaurant.setUsuario(usuario);

            Sucursal sucursal = new Sucursal();
            sucursal.setId(sucursalJSON.getJSONObject(Valores.SUCURSAL_SUCURSAL_PK).getInt(Valores.SUCURSAL_ID));
            sucursal.setDireccion(direccion);
            sucursal.setRestaurant(restaurant);

            viaje.setId(viajeJSON.getInt(Viaje.ID));
            viaje.setPrecio(viajeJSON.getInt(Viaje.PRECIO));
            viaje.setSucursal(sucursal);

            try {
                DataBase db = new DataBase(MessagingService.this);

                db.insertarUsuario(usuario);
                db.insertarDireccion(direccion);
                db.insertarRestaurant(restaurant);
                db.insertarSucursal(sucursal);
                db.insertarViaje(viaje);
            } catch (SQLiteException e) {
                Toast.makeText(MessagingService.this, R.string.no_se_pudo_insertar_en_la_base, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        crearNotificacion(viaje);
    }

    private void crearNotificacion(Viaje viaje) {
        Intent i = new Intent(getApplicationContext(), NotificacionActivity.class);
        i.putExtra(Valores.NOTIFICATOIN_ID_TEXTO, Valores.NOTIFICATION_ID);
        i.putExtra(Valores.VIAJE, viaje.getId());

        Integer contadorNotificaciones = SharedPref.getContadorNotificacion(getApplicationContext());

        PendingIntent pendingIntent = PendingIntent.getActivity(this, contadorNotificaciones, i, 0);
        SharedPref.guardarContadorNotificacion(getApplicationContext(), contadorNotificaciones + 1);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        CharSequence ticker = "Ver Viaje";
        CharSequence contentTitle = viaje.getSucursal().getRestaurant().getRazonSocial();
        CharSequence contentText = Valores.VIAJE_PARA_TI;
        Notification notificacion = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/raw/jingle"))
                .setTicker(ticker)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.ic_launcher)
                .addAction(R.drawable.ic_launcher, ticker, pendingIntent)
                .setVibrate(new long[] {100, 250, 100, 500})
                .build();
        nm.notify(Valores.NOTIFICATION_ID, notificacion);
    }
}
