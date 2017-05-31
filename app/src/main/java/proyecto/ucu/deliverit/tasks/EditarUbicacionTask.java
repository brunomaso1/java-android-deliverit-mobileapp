package proyecto.ucu.deliverit.tasks;

import android.app.ProgressDialog;
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

public class EditarUbicacionTask extends AsyncTask<Void, Void, RespuestaGeneral> {
    HomeActivity activityPadre;
    ProgressDialog progressDialog;
    Ubicacion ubicacion;

    public EditarUbicacionTask (HomeActivity activityPadre, Ubicacion ubicacion) {
        this.activityPadre = activityPadre;
        this.ubicacion = ubicacion;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activityPadre);
        progressDialog.setMessage(Valores.ACTUALIZANDO_UBICACION);
        progressDialog.show();
    }

    @Override
    protected RespuestaGeneral doInBackground(Void... params) {
        RespuestaGeneral respuesta = null;
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

            respuesta = new RespuestaGeneral();
            respuesta.setCodigo(RespuestaGeneral.CODIGO_OK);
            respuesta.setObjeto(objeto);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    @Override
    protected void onPostExecute(RespuestaGeneral respuestaGeneral) {
        super.onPostExecute(respuestaGeneral);
        progressDialog.dismiss();
        activityPadre.editarUbicacionTaskRetorno(respuestaGeneral);
    }
}
