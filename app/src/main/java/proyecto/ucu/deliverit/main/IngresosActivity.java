package proyecto.ucu.deliverit.main;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.almacenamiento.DataBase;
import proyecto.ucu.deliverit.entidades.Viaje;

public class IngresosActivity extends AppCompatActivity {

    private Spinner opciones_ingresos_sp;
    private LinearLayout grafica_ll;

    private DataBase DB;

    private List<Viaje> viajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.ingresos);

        agregarSpinner();

        grafica_ll = (LinearLayout) findViewById(R.id.grafica_ll);

        DB = new DataBase(IngresosActivity.this);

        /*try {
            viajes = DB.getIngresosMensuales();
        } catch (SQLiteException e) {
            e.printStackTrace();
            Toast.makeText(IngresosActivity.this, R.string.no_se_pudieron_obtener_datos_base, Toast.LENGTH_LONG).show();
        }

        if (viajes.size() > 0) {
            armarGrafica(true);
        } else {
            Toast.makeText(IngresosActivity.this, R.string.no_tiene_ingresos, Toast.LENGTH_LONG).show();
        }*/
    }

    private void agregarSpinner() {
        opciones_ingresos_sp = (Spinner) findViewById(R.id.opciones_ingresos_sp);
        opciones_ingresos_sp.setSelection(0);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.opciones_ingresos_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        opciones_ingresos_sp.setAdapter(adapter);

        opciones_ingresos_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccionado = String.valueOf(parent.getItemAtPosition(position));
                try {
                    grafica_ll.removeAllViewsInLayout();
                    if (seleccionado.equals("Este mes")) {
                        viajes = DB.getIngresosMensuales();
                        armarGrafica(true);
                    } else {
                        viajes = DB.getIngresosAnuales();
                        armarGrafica(false);
                    }
                } catch (SQLiteException e) {
                    e.printStackTrace();
                    Toast.makeText(IngresosActivity.this, R.string.no_se_pudieron_obtener_datos_base, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void armarGrafica(boolean mensual) {
        ArrayList<String> columnas = getColumnas(mensual);
        Map<Integer, Integer> valoresColumnas = getValoresColumnas(mensual);
        consolidatGrafica(columnas, valoresColumnas);
    }

    private ArrayList<String> getColumnas(boolean mensual) {
        ArrayList<String> columnas = new ArrayList<>();
        if (mensual) {
            for (int i = 0; i < cantDiasHastaHoy(); i++) {
                columnas.add(String.valueOf(i + 1));
            }
        } else {
            int mesActual = Calendar.getInstance().get(Calendar.MONTH) + 1;
            for (int i = 0; i < mesActual; i++) {
                columnas.add(String.valueOf(i + 1));
            }
        }
        return columnas;
    }

    private Map<Integer, Integer> getValoresColumnas(boolean mensual) {
        Map<Integer, Integer> ingresos = new HashMap<>();

        for (Viaje v : viajes) {
            if (mensual) {
                if (!ingresos.containsKey(v.getFecha().getDate() + 1)) {
                    ingresos.put(v.getFecha().getDate() + 1, v.getPrecio());
                } else {
                    ingresos.put(v.getFecha().getDate() + 1, ingresos.get(v.getFecha().getDate() + 1) + v.getPrecio());
                }
            } else {
                if (!ingresos.containsKey(v.getFecha().getMonth() + 1)) {
                    ingresos.put(v.getFecha().getMonth() + 1, v.getPrecio());
                } else {
                    ingresos.put(v.getFecha().getMonth() + 1, ingresos.get(v.getFecha().getMonth() + 1) + v.getPrecio());
                }
            }
        }
        return ingresos;
    }

    private ArrayList<BarEntry> consolidatGrafica(ArrayList<String> nombreColumnas, Map<Integer, Integer> valoresColumnas) {
        ArrayList<BarEntry> grafica = new ArrayList<>();

        for (int i = 0; i < nombreColumnas.size(); i++) {
            if (valoresColumnas.containsKey(i + 1)) {
                grafica.add(new BarEntry(valoresColumnas.get(i + 1), i));
            }
        }

        BarDataSet dataset = new BarDataSet(grafica, "$ de ingresos");
        dataset.setColors(ColorTemplate.LIBERTY_COLORS);
        BarChart chart = new BarChart(IngresosActivity.this);
        BarData data = new BarData(nombreColumnas, dataset);
        chart.setData(data);
        chart.animateXY(5000, 5000);
        grafica_ll.addView(chart);

        return grafica;
    }

    private int cantDiasHastaHoy() {
        return new Timestamp(System.currentTimeMillis()).getDate();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}