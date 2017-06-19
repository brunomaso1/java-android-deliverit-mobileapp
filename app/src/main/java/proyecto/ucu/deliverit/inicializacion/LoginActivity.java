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
import proyecto.ucu.deliverit.entidades.Usuario;
import proyecto.ucu.deliverit.main.MainActivity;
import proyecto.ucu.deliverit.tasks.LoginTask;
import proyecto.ucu.deliverit.utiles.RespuestaGeneral;

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
                if (nombreUsuario_Et.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, R.string.ingrese_usuario_login, Toast.LENGTH_LONG).show();
                } else if (password_et.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, R.string.ingrese_password_login, Toast.LENGTH_LONG).show();
                } else {
                    new LoginTask(LoginActivity.this, nombreUsuario_Et.getText().toString(), password_et.getText().toString()).execute();
                }
            }
        });
    }

    public void loginTaskRetorno(RespuestaGeneral respuestaGeneral) {
        if (respuestaGeneral == null) {
            Toast.makeText(LoginActivity.this, R.string.no_se_pudo_realizar_la_operacion, Toast.LENGTH_LONG).show();
        } else {
            if (respuestaGeneral.getCodigo() == RespuestaGeneral.CODIGO_OK) {
                SharedPref.guardarIdDelivery(LoginActivity.this, Integer.parseInt(respuestaGeneral.getObjeto()));

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, respuestaGeneral.getMensaje(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
