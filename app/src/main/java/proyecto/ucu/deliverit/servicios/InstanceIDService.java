package proyecto.ucu.deliverit.servicios;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import proyecto.ucu.deliverit.almacenamiento.SharedPref;

/**
 * Created by Juancho on 15/02/2017.
 */

public class InstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        SharedPref.guardarToken(InstanceIDService.this, refreshedToken);
    }
}
