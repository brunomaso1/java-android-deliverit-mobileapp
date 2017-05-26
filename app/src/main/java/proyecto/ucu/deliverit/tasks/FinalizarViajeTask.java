package proyecto.ucu.deliverit.tasks;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import proyecto.ucu.deliverit.main.MainActivity;
import proyecto.ucu.deliverit.main.NotificacionActivity;
import proyecto.ucu.deliverit.utiles.RespuestaGeneral;
import proyecto.ucu.deliverit.utiles.Valores;

/**
 * Created by DeliverIT on 09/03/2017.
 */

public class FinalizarViajeTask extends AsyncTask<Void, Void, Void>  {

    NotificacionActivity activityPadre;
    Integer idViaje;

    public FinalizarViajeTask(NotificacionActivity activityPadre, Integer idViaje) {
        this.activityPadre = activityPadre;
        this.idViaje = idViaje;
    }

    @Override
    protected Void doInBackground(Void... params) {
        RespuestaGeneral respuesta = new RespuestaGeneral();

        OkHttpClient client = new OkHttpClient();

        String url = Valores.URL_FINALIZAR_VIAJE + idViaje;

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "");

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.body().string().equals("")) {
                respuesta.setCodigo(RespuestaGeneral.CODIGO_OK);
            } else {
                respuesta.setCodigo(RespuestaGeneral.CODIGO_ERROR);
            }
        } catch (IOException e) {
            respuesta.setCodigo(RespuestaGeneral.CODIGO_ERROR);
            e.printStackTrace();
        }
        activityPadre.finalizarViajeTaskRetorno(respuesta);
        return null;
    }
}
