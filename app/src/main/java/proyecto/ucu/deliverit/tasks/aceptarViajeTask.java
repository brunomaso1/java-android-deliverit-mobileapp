package proyecto.ucu.deliverit.tasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import proyecto.ucu.deliverit.almacenamiento.SharedPref;
import proyecto.ucu.deliverit.entidades.Viaje;
import proyecto.ucu.deliverit.main.NotificacionActivity;
import proyecto.ucu.deliverit.utiles.RespuestaGeneral;

/**
 * Created by JMArtegoytia on 24/02/2017.
 */

public class AceptarViajeTask extends AsyncTask<Void, Void, Void> {
    NotificacionActivity activityPadre;
    Integer idDelivery;
    Integer idViaje;

    public AceptarViajeTask(NotificacionActivity activityPadre, Integer idDelivery, Integer idViaje) {
        this.activityPadre = activityPadre;
        this.idDelivery = idDelivery;
        this.idViaje = idViaje;
    }

    @Override
    protected Void doInBackground(Void... params) {
        OkHttpClient client = new OkHttpClient();

        String url = "http://192.168.1.45:8080/BackCore/ws/viaje/aceptarViaje/" + idViaje + "/" + idDelivery;

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "");

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
            activityPadre.aceptarTaskRetorno(0);
        } catch (IOException e) {
            e.printStackTrace();
            activityPadre.aceptarTaskRetorno(-1);
        }
        return null;
    }
}
