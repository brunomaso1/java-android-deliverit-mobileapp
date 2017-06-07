package proyecto.ucu.deliverit.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import proyecto.ucu.deliverit.entidades.Viaje;
import proyecto.ucu.deliverit.main.MainActivity;
import proyecto.ucu.deliverit.utiles.Valores;

public class ViajesPublicadosTask extends AsyncTask<Object, Object, List<Viaje>> {
    private MainActivity activityPadre;
    private ProgressDialog progressDialog;

    public ViajesPublicadosTask(MainActivity activityPadre) {
        this.activityPadre = activityPadre;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activityPadre);
        progressDialog.setMessage(Valores.CARGANDO_VIAJES);
        progressDialog.show();
    }

    @Override
    protected List<Viaje> doInBackground(Object... params) {
        List<Viaje> viajes = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        String url = Valores.URL_VIAJES_PUBLICADOS + new Timestamp(System.currentTimeMillis());

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                Gson gson = new Gson();
                viajes = Arrays.asList(gson.fromJson(response.body().string(), Viaje[].class));
            } else {
                viajes = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            viajes = null;
        }
        return viajes;
    }

    @Override
    protected void onPostExecute(List<Viaje> viajes) {
        super.onPostExecute(viajes);
        progressDialog.dismiss();
        activityPadre.viajesPublicadosTaskRetorno(viajes);
    }
}
