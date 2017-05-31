package proyecto.ucu.deliverit.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import proyecto.ucu.deliverit.entidades.Viaje;
import proyecto.ucu.deliverit.main.MainActivity;
import proyecto.ucu.deliverit.utiles.Valores;

public class ViajesPublicadosTask extends AsyncTask<Object, Object, List<Viaje>> {
    MainActivity activityPadre;
    ProgressDialog progressDialog;

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
        List<Viaje> viajes = null;

        OkHttpClient client = new OkHttpClient();
        String url = Valores.URL_VIAJES_PUBLICADOS;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();
            Gson gson = new Gson();
            viajes = Arrays.asList(gson.fromJson(response.body().string(), Viaje[].class));

        } catch (IOException e) {
            e.printStackTrace();
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
