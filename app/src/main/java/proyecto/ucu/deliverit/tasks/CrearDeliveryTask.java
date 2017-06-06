package proyecto.ucu.deliverit.tasks;

import android.app.ProgressDialog;
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

public class CrearDeliveryTask extends AsyncTask<Void, Void, RespuestaGeneral> {
    private RegistroActivity activityPadre;
    private ProgressDialog progressDialog;
    private Delivery delivery;

    public CrearDeliveryTask(RegistroActivity activityPadre, Delivery delivery) {
        this.activityPadre = activityPadre;
        this.delivery = delivery;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activityPadre);
        progressDialog.setMessage(Valores.CREANDO_USUARIO);
        progressDialog.show();
    }

    @Override
    protected RespuestaGeneral doInBackground(Void... params) {
        RespuestaGeneral respuesta = null;

        OkHttpClient client = new OkHttpClient();

        String url = Valores.URL_WS + Delivery.TABLE_NAME;

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
            respuesta = gson.fromJson(response.body().string(), RespuestaGeneral.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    @Override
    protected void onPostExecute(RespuestaGeneral respuestaGeneral) {
        super.onPostExecute(respuestaGeneral);
        progressDialog.dismiss();
        activityPadre.crearDeliveryTaskRetorno(respuestaGeneral);
    }
}
