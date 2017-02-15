package proyecto.ucu.deliverit.inicializacion;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.almacenamiento.DataBase;
import proyecto.ucu.deliverit.almacenamiento.SharedPref;
import proyecto.ucu.deliverit.main.MainActivity;

/**
 * Created by Juan on 01/02/2017.
 */

public class HomeActivity extends AppCompatActivity {
    Button registrarse_btn, ingresar_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.home);

        // Creo la base de datos
        DataBase db = new DataBase(getApplicationContext());

        long id;
        try {
            id = db.agregarDelivery ("juan", "a12345678.", "Juan Martin Artegoytia", "juanmartegoytia@gmail.com", "099994974",
                    2020, null);
            System.out.println("***** id = " + id + " *****");
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        // Si nadie se logueó
        if (SharedPref.getIdDelivery(getApplicationContext()) == 0) {
            registrarse_btn = (Button) findViewById(R.id.registrarse_btn);
            ingresar_btn = (Button) findViewById(R.id.ingresar_btn);

            registrarse_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, RegistroActivity.class);
                    startActivity(intent);
                }
            });

            ingresar_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
