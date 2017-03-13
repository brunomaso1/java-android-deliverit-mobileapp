package proyecto.ucu.deliverit.tasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import proyecto.ucu.deliverit.entidades.Delivery;
import proyecto.ucu.deliverit.inicializacion.RegistroActivity;
import proyecto.ucu.deliverit.utiles.RespuestaGeneral;
import proyecto.ucu.deliverit.utiles.Valores;

/**
 * Created by DeliverIT on 03/03/2017.
 */

public class CrearDeliveryTask extends AsyncTask<Void, Void, Void> {
    RegistroActivity activityPadre;
    Delivery delivery;

    public CrearDeliveryTask(RegistroActivity activityPadre, Delivery delivery) {
        this.activityPadre = activityPadre;
        this.delivery = delivery;
    }

    @Override
    protected Void doInBackground(Void... params) {
        OkHttpClient client = new OkHttpClient();

        String url = Valores.URL_WS + delivery.getClass().getName().toLowerCase();

        Gson gson = new Gson();
        String objeto = gson.toJson(this.delivery);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), objeto);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();

            gson = new Gson();
            RespuestaGeneral respuesta = gson.fromJson(response.body().string(), RespuestaGeneral.class);

            activityPadre.crearDeliveryTaskRetorno(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
            activityPadre.crearDeliveryTaskRetorno(null);
        }
        return null;
    }
}
