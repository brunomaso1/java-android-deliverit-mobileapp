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
import proyecto.ucu.deliverit.entidades.Direccion;
import proyecto.ucu.deliverit.entidades.Sucursal;
import proyecto.ucu.deliverit.main.SucursalesMapActivity;
import proyecto.ucu.deliverit.utiles.Valores;

public class SucursalesViajesTask extends AsyncTask<Void, Void, List<Sucursal>> {
    private SucursalesMapActivity activityPadre;
    private ProgressDialog progressDialog;

    public SucursalesViajesTask(SucursalesMapActivity activityPadre) {
        this.activityPadre = activityPadre;
        progressDialog = new ProgressDialog(activityPadre);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setTitle(Valores.OBTENIENDO_SUCURSALES);
        progressDialog.show();
    }

    @Override
    protected List<Sucursal> doInBackground(Void... params) {
        List<Sucursal> resultado = null;

        OkHttpClient client = new OkHttpClient();
        String url = Valores.URL_DIRECCIONES_VIAJES;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                Gson gson = new Gson();
                resultado = Arrays.asList(gson.fromJson(response.body().string(), Sucursal[].class));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    protected void onPostExecute(List<Sucursal> sucursales) {
        super.onPostExecute(sucursales);
        progressDialog.dismiss();
        activityPadre.direccionesViajesTaskRetorno(sucursales);
    }
}
