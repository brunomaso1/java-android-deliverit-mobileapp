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
import proyecto.ucu.deliverit.entidades.Usuario;
import proyecto.ucu.deliverit.inicializacion.LoginActivity;
import proyecto.ucu.deliverit.utiles.RespuestaGeneral;
import proyecto.ucu.deliverit.utiles.Valores;

public class LoginTask extends AsyncTask<Void, Void, RespuestaGeneral> {
    private LoginActivity activityPadre;
    private String usuario;
    private String password;
    private ProgressDialog progressDialog;

    public LoginTask(LoginActivity activityPadre, String usuario, String password) {
        this.activityPadre = activityPadre;
        this.usuario = usuario;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activityPadre);
        progressDialog.setMessage(Valores.LOGIN);
        progressDialog.show();
    }

    @Override
    protected RespuestaGeneral doInBackground(Void... params) {
        RespuestaGeneral respuestaGeneral = null;

        OkHttpClient client = new OkHttpClient();

        String url = Valores.URL_LOGIN + this.usuario + "/" + this.password;

        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                respuestaGeneral = new RespuestaGeneral();
                Gson gson = new Gson();
                respuestaGeneral = gson.fromJson(response.body().string(), RespuestaGeneral.class);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return respuestaGeneral;
    }

    @Override
    protected void onPostExecute(RespuestaGeneral respuestaGeneral) {
        super.onPostExecute(respuestaGeneral);
        progressDialog.dismiss();
        activityPadre.loginTaskRetorno(respuestaGeneral);
    }
}
