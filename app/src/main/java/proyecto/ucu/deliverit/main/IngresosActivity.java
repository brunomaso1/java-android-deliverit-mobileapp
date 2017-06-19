package proyecto.ucu.deliverit.main;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.almacenamiento.DataBase;

public class IngresosActivity extends AppCompatActivity {

    private TextView texto_tv, ingresos_tv;
    private Button detalle_btn;

    private DataBase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.ingresos);

        texto_tv = (TextView) findViewById(R.id.texto_tv);
        ingresos_tv = (TextView) findViewById(R.id.ingresos_tv);

        DB = new DataBase(IngresosActivity.this);

        try {
            int ingresos = DB.getIngresosMensuales();

            if (ingresos != 0) {
                detalle_btn = (Button) findViewById(R.id.detalle_btn);
                texto_tv.setText(completarTexto());
                ingresos_tv.setText(completarIngresos(ingresos));

                detalle_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(IngresosActivity.this, ViajesActivity.class);
                        startActivity(intent);
                    }
                });
            } else {
                Toast.makeText(IngresosActivity.this, R.string.no_tiene_ingresos, Toast.LENGTH_LONG).show();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
            Toast.makeText(IngresosActivity.this, R.string.no_se_pudieron_obtener_datos_base, Toast.LENGTH_LONG).show();
        }
    }

    private String completarTexto() {
        Date primerDiaMes = new Date();
        primerDiaMes.setDate(1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String primerDiaMesString = sdf.format(primerDiaMes);

        Date hoy = new Date();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        String hoyString = sdf.format(hoy);

        StringBuilder sb = new StringBuilder();
        sb.append("Desde el día ");
        sb.append(primerDiaMesString);
        sb.append("\n");
        sb.append("hasta ");
        sb.append(hoyString);
        sb.append(" sus ingresos son de:");

        return sb.toString();
    }

    private String completarIngresos(int ingresos) {
        StringBuilder sb = new StringBuilder();
        sb.append("$");
        sb.append(ingresos);

        return sb.toString();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}