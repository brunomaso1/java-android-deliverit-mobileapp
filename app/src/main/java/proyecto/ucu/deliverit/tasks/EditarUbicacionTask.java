package proyecto.ucu.deliverit.tasks;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import proyecto.ucu.deliverit.entidades.Ubicacion;
import proyecto.ucu.deliverit.inicializacion.HomeActivity;
import proyecto.ucu.deliverit.utiles.RespuestaGeneral;
import proyecto.ucu.deliverit.utiles.Valores;

/**
 * Created by DeliverIT on 04/03/2017.
 */

public class EditarUbicacionTask extends AsyncTask<Void, Void, Void> {
    HomeActivity activityPadre;
    Ubicacion ubicacion;

    public EditarUbicacionTask (HomeActivity activityPadre, Ubicacion ubicacion) {
        this.activityPadre = activityPadre;
        this.ubicacion = ubicacion;
    }

    @Override
    protected Void doInBackground(Void... params) {
        OkHttpClient client = new OkHttpClient();

        String url = Valores.URL_WS + Ubicacion.TABLE_NAME + Valores.BARRA_DIAGONAL + ubicacion.getId();

        Gson gson = new Gson();
        String objeto = gson.toJson(this.ubicacion);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), objeto);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();

        try {
            client.newCall(request).execute();

            RespuestaGeneral respuesta = new RespuestaGeneral();
            respuesta.setCodigo(RespuestaGeneral.CODIGO_OK);
            respuesta.setObjeto(objeto);
            activityPadre.editarUbicacionTaskRetorno(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
            activityPadre.editarUbicacionTaskRetorno(null);
        }
        return null;
    }
}
