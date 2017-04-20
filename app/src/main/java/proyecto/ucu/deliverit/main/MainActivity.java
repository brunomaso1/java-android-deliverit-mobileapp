package proyecto.ucu.deliverit.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.almacenamiento.DataBase;

/**
 * Created by DeliverIT on 31/01/2017.
 */

public class MainActivity extends AppCompatActivity {
    DataBase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
    }
}
