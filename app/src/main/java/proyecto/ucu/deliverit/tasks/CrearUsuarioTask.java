package proyecto.ucu.deliverit.tasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import proyecto.ucu.deliverit.entidades.Usuario;
import proyecto.ucu.deliverit.inicializacion.RegistroActivity;
import proyecto.ucu.deliverit.utiles.RespuestaGeneral;

/**
 * Created by DeliverIT on 03/03/2017.
 */

public class CrearUsuarioTask extends AsyncTask<Void, Void, Void> {
    RegistroActivity activityPadre;
    Usuario usuario;

    public CrearUsuarioTask(RegistroActivity activityPadre, Usuario usuario) {
        this.activityPadre = activityPadre;
        this.usuario = usuario;
    }

    @Override
    protected Void doInBackground(Void... params) {
        OkHttpClient client = new OkHttpClient();

        String url = "http://192.168.1.42:8080/BackCore/ws/usuario";

        Gson gson = new Gson();
        String objeto = gson.toJson(this.usuario);

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

            activityPadre.crearUsuarioTaskRetorno(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
            activityPadre.crearUsuarioTaskRetorno(null);
        }
        return null;
    }
}
