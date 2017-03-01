package proyecto.ucu.deliverit.servicios;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import proyecto.ucu.deliverit.utiles.Valores;

/**
 * Created by DeliverIT on 12/02/2017.
 */

public class ConsultaViajes extends IntentService {

    public ConsultaViajes() {
        super("ConsultaViajes");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                try {
                    while(true) {
                        String url = "http://192.168.1.42:8080/ServiciosDeliverIT/ws/vehiculo/count";

                        OkHttpClient client;

                        OkHttpClient.Builder builder = new OkHttpClient.Builder();
                        builder.connectTimeout(Valores.TIMEOUT_WS, TimeUnit.SECONDS);
                        client = builder.build();

                        Request request = new Request.Builder()
                                .url(url)
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(Call call, final Response response) throws IOException {
                                if (!response.isSuccessful()) {
                                    throw new IOException("Unexpected code " + response);
                                } else {
                                    System.out.println("*** response = " + response.body().string() + " ***");
                                }
                            }
                        });
                        Thread.sleep(Valores.TIMER_VIAJES);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();


    }
}
