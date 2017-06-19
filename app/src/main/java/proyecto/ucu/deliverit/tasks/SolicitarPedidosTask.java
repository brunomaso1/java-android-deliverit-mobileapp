package proyecto.ucu.deliverit.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import proyecto.ucu.deliverit.entidades.Pedido;
import proyecto.ucu.deliverit.main.MainActivity;
import proyecto.ucu.deliverit.utiles.DateDeserializer;
import proyecto.ucu.deliverit.utiles.Valores;

public class SolicitarPedidosTask extends AsyncTask<Void, Void, List<Pedido>> {
    private MainActivity activityPadre;
    private ProgressDialog progressDialog;
    private Integer idViaje;

    public SolicitarPedidosTask(MainActivity activityPadre, Integer idViaje) {
        this.activityPadre = activityPadre;
        this.progressDialog = new ProgressDialog(this.activityPadre);
        this.idViaje = idViaje;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMessage(Valores.OBTENIENDO_PEDIDOS);
        progressDialog.show();
    }

    @Override
    protected List<Pedido> doInBackground(Void... params) {
        List<Pedido> resultado = null;

        OkHttpClient client = new OkHttpClient();
        String url = Valores.URL_SOLICITAR_PEDIDOS + idViaje;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
                Gson gson = gsonBuilder.create();
                resultado = Arrays.asList(gson.fromJson(response.body().string(), Pedido[].class));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    protected void onPostExecute(List<Pedido> pedidos) {
        super.onPostExecute(pedidos);
        progressDialog.dismiss();
        activityPadre.solicitarPedidosTaskRetorno(pedidos);
    }
}
