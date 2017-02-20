package proyecto.ucu.deliverit.servicios;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.main.NotificacionActivity;

/**
 * Created by Juancho on 15/02/2017.
 */

public class MessagingService extends FirebaseMessagingService {
    private int notificationID = 1;

    @Override
    public void onMessageReceived(RemoteMessage message) {
        String sender = message.getFrom();
        crearNotificacion(message);
    }

    private void crearNotificacion(RemoteMessage mensaje) {
        Intent i = new Intent(this, NotificacionActivity.class);
        i.putExtra("notificationID", notificationID);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        CharSequence ticker ="Ver Viaje";
        CharSequence contentTitle = "SekthDroid";                       // Nombre Restaurant: " tiene un viaje para ti"
        CharSequence contentText = "Visita ahora SekthDroid!";          // Dirección Sucursal
        Notification notificacion = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/raw/facebook_ringtone_pop"))
                .setTicker(ticker)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.ic_launcher)
                .addAction(R.drawable.ic_launcher, ticker, pendingIntent)
                .setVibrate(new long[] {100, 250, 100, 500})
                .build();
        nm.notify(notificationID, notificacion);
    }
}
