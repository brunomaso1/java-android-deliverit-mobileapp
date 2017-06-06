package proyecto.ucu.deliverit.inicializacion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import proyecto.ucu.deliverit.R;


public class OlvidoPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.cambiar_password);
    }
}
