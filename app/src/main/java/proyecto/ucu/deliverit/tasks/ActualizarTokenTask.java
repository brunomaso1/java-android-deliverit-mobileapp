package proyecto.ucu.deliverit.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import proyecto.ucu.deliverit.main.MainActivity;
import proyecto.ucu.deliverit.utiles.Valores;

public class ActualizarTokenTask extends AsyncTask<Void, Void, Void> {
    private MainActivity activityPadre;
    private ProgressDialog progressDialog;
    private String token;
    private Integer idDelivery;

    public ActualizarTokenTask(MainActivity activityPadre, String token, Integer idDelivery) {
        this.activityPadre = activityPadre;
        this.token = token;
        this.idDelivery = idDelivery;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activityPadre);
        progressDialog.setMessage(Valores.ACTUALIZANDO_TOKEN);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        OkHttpClient client = new OkHttpClient();
        String url = Valores.URL_WS + Valores.URL_ACTUALIZAR_TOKEN + idDelivery + Valores.BARRA_DIAGONAL + token;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        super.onPostExecute(v);
        progressDialog.dismiss();
        activityPadre.actualizarTokenTaskRetorno();
    }
}