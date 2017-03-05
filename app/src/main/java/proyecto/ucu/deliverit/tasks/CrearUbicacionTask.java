package proyecto.ucu.deliverit.tasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import proyecto.ucu.deliverit.entidades.Ubicacion;
import proyecto.ucu.deliverit.inicializacion.HomeActivity;
import proyecto.ucu.deliverit.utiles.RespuestaGeneral;

/**
 * Created by DeliverIT on 04/03/2017.
 */

public class CrearUbicacionTask extends AsyncTask<Void, Void, Void> {
    HomeActivity activityPadre;
    Ubicacion ubicacion;

    public CrearUbicacionTask (HomeActivity activityPadre, Ubicacion ubicacion) {
        this.activityPadre = activityPadre;
        this.ubicacion = ubicacion;
    }

    @Override
    protected Void doInBackground(Void... params) {
        OkHttpClient client = new OkHttpClient();

        String url = "http://192.168.1.43:8080/BackCore/ws/ubicacion";

        Gson gson = new Gson();
        String objeto = gson.toJson(this.ubicacion);

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

            activityPadre.crearUbicacionTaskRetorno(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
            activityPadre.crearUbicacionTaskRetorno(null);
        }
        return null;
    }
}
