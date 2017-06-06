package proyecto.ucu.deliverit.inicializacion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.almacenamiento.DataBase;
import proyecto.ucu.deliverit.almacenamiento.SharedPref;
import proyecto.ucu.deliverit.main.MainActivity;

public class LoginActivity extends AppCompatActivity {
    private TextView olvidoPassword_tv;
    private EditText nombreUsuario_Et, password_et;
    private Button ingresar_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.login);

        olvidoPassword_tv = (TextView) findViewById(R.id.olvidoPassword_tv);
        nombreUsuario_Et = (EditText) findViewById(R.id.nombreUsuario_et);
        password_et = (EditText) findViewById(R.id.password_et);
        ingresar_btn = (Button) findViewById(R.id.ingresar_btn);

        olvidoPassword_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(LoginActivity.this, OlvidoPasswordActivity.class);
                startActivity(intent);*/
            }
        });

        ingresar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                /*if (!nombreUsuario_Et.getText().toString().equals("")) {
                    DataBase db = new DataBase(LoginActivity.this);
                    long idDelivery = db.login(nombreUsuario_Et.getText().toString(), password_et.getText().toString());

                    if (idDelivery == 0) {
                        Toast.makeText(LoginActivity.this, R.string.no_existe_delivery, Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPref.guardarIdDelivery(getApplicationContext(), idDelivery);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(LoginActivity.this, R.string.nombre_usuario_vacio, Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }
}
