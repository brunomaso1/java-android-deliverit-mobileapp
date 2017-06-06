package proyecto.ucu.deliverit.servicios;

import android.app.NotificationManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.almacenamiento.DataBase;
import proyecto.ucu.deliverit.almacenamiento.SharedPref;
import proyecto.ucu.deliverit.entidades.Pedido;
import proyecto.ucu.deliverit.entidades.Viaje;
import proyecto.ucu.deliverit.main.MainActivity;
import proyecto.ucu.deliverit.main.NotificacionActivity;
import proyecto.ucu.deliverit.utiles.Operaciones;
import proyecto.ucu.deliverit.utiles.Valores;

public class MessagingService extends FirebaseMessagingService {
    private DataBase DB;

    @Override
    public void onMessageReceived(RemoteMessage message) {
        Integer idViaje = Integer.parseInt(message.getData().get(Valores.VIAJE));

        OkHttpClient client = new OkHttpClient();
        String url = Valores.URL_SOLICITAR_PEDIDOS + idViaje;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();
            Gson gson = new Gson();
            List<Pedido> pedidos = Arrays.asList(gson.fromJson(response.body().string(), Pedido[].class));

            DB = new DataBase(MessagingService.this);
            DB.guardarDatosPedidos(pedidos);

            crearNotificacion(pedidos.get(0).getViaje());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLiteException e) {
            e.printStackTrace();
            Toast.makeText(MessagingService.this, R.string.no_se_pudo_insertar_en_la_base, Toast.LENGTH_SHORT).show();
        }
    }

    private void crearNotificacion(Viaje viaje) {
        Intent i = new Intent(getApplicationContext(), NotificacionActivity.class);
        i.putExtra(Valores.NOTIFICATOIN_ID_TEXTO, Valores.NOTIFICATION_ID);
        i.putExtra(Valores.VIAJE, viaje.getId());

        Integer contadorNotificaciones = SharedPref.getContadorNotificacion(getApplicationContext());

        PendingIntent pendingIntent = PendingIntent.getActivity(this, contadorNotificaciones, i, 0);
        SharedPref.guardarContadorNotificacion(getApplicationContext(), contadorNotificaciones + 1);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        CharSequence ticker = "Ver Viaje";
        CharSequence contentTitle = viaje.getSucursal().getRestaurant().getRazonSocial();
        CharSequence contentText = Valores.VIAJE_PARA_TI;
        Notification notificacion = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/raw/jingle.m4a"))
                .setTicker(ticker)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.ic_launcher)
                .addAction(R.drawable.ic_launcher, ticker, pendingIntent)
                .setVibrate(new long[]{100, 250, 100, 500})
                .build();
        nm.notify(Valores.NOTIFICATION_ID, notificacion);
    }
}
