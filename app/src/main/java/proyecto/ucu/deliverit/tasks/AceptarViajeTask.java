package proyecto.ucu.deliverit.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import proyecto.ucu.deliverit.entidades.Viaje;
import proyecto.ucu.deliverit.main.NotificacionActivity;
import proyecto.ucu.deliverit.utiles.DateDeserializer;
import proyecto.ucu.deliverit.utiles.Valores;

public class AceptarViajeTask extends AsyncTask<Void, Void, Integer> {
    private NotificacionActivity activityPadre;
    private ProgressDialog progressDialog;
    private Integer idDelivery;
    private Integer idViaje;

    public AceptarViajeTask(NotificacionActivity activityPadre, Integer idDelivery, Integer idViaje) {
        this.activityPadre = activityPadre;
        this.idDelivery = idDelivery;
        this.idViaje = idViaje;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activityPadre);
        progressDialog.setMessage(Valores.SOLICITANDO_VIAJE);
        progressDialog.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        Integer retorno = null;
        OkHttpClient client = new OkHttpClient();

        String url = Valores.URL_ACEPAR_VIAJE + idViaje + Valores.BARRA_DIAGONAL + idDelivery;

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "");

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                Gson gson = new Gson();
                retorno = gson.fromJson(response.body().string(), Integer.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    @Override
    protected void onPostExecute(Integer retorno) {
        super.onPostExecute(retorno);
        progressDialog.dismiss();
        activityPadre.aceptarTaskRetorno(retorno);
    }
}
