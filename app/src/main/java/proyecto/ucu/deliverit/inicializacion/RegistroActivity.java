package proyecto.ucu.deliverit.inicializacion;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.utiles.Operaciones;

/**
 * Created by DeliverIT on 31/01/2017.
 */

public class RegistroActivity extends AppCompatActivity {
    EditText nombreUsuario_et, password_et, nombre_et, mail_et, telefono_et, cuentaRedPagos_et;
    ImageButton camara_ibtn;
    Button registrarse_btn;

    private Camera camara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.registro);

        nombreUsuario_et = (EditText) findViewById(R.id.nombreUsuario_et);
        password_et = (EditText) findViewById(R.id.password_et);
        nombre_et = (EditText) findViewById(R.id.nombre_et);
        mail_et = (EditText) findViewById(R.id.mail_et);
        telefono_et = (EditText) findViewById(R.id.telefono_et);
        cuentaRedPagos_et = (EditText) findViewById(R.id.cuentaRedPagos_et);
        camara_ibtn = (ImageButton) findViewById(R.id.camara_ibtn);
        registrarse_btn = (Button) findViewById(R.id.registrarse_btn);

        camara_ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraHardware(getApplicationContext())) {
                    try {
                        int idCamara = findFrontFacingCamera();
                        camara = Camera.open(idCamara);
                    } catch (Exception e){
                        Toast.makeText(RegistroActivity.this, R.string.camara_no_disponible, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegistroActivity.this, R.string.no_tiene_camara, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int findFrontFacingCamera() {
        int cameraId = 0;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            cameraId = i;
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                break;
            }
        }
        return cameraId;
    }

    /** Check if this device has a camera */
    public static boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
}
