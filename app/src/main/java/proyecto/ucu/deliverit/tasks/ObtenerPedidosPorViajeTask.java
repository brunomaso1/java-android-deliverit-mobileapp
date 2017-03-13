package proyecto.ucu.deliverit.tasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import proyecto.ucu.deliverit.entidades.Pedido;
import proyecto.ucu.deliverit.main.NotificacionActivity;
import proyecto.ucu.deliverit.utiles.RespuestaGeneral;
import proyecto.ucu.deliverit.utiles.Valores;

/**
 * Created by DeliverIT on 13/03/2017.
 */

public class ObtenerPedidosPorViajeTask extends AsyncTask<Void, Void, Void> {
    NotificacionActivity activityPadre;
    Integer idViaje;

    public ObtenerPedidosPorViajeTask(NotificacionActivity activityPadre, Integer idViaje) {
        this.activityPadre = activityPadre;
        this.idViaje = idViaje;
    }

    @Override
    protected Void doInBackground(Void... params) {
        OkHttpClient client = new OkHttpClient();

        String url = Valores.URL_OBTENER_PEDIDOS_POR_VIAJE + idViaje;

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "");

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();
            Gson gson = new Gson();
            List<Pedido> pedidos = gson.fromJson(response.body().string(), List.class);

            activityPadre.obtenerPedidosPorViajeTaskRetorno(pedidos);
        } catch (IOException e) {
            e.printStackTrace();
            activityPadre.obtenerPedidosPorViajeTaskRetorno(null);
        }
        return null;
    }
}
