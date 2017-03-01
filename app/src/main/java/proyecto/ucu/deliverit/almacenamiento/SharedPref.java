package proyecto.ucu.deliverit.almacenamiento;


import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by deliverIT on 31/01/2017.
 */

public class SharedPref {
    public static final String SHARED_PREF_NAME = "SharedPreferencesDeliverIT";

    public static final String ID_DELIVERY = "idDelivery";

    public static long getIdDelivery (Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        long idDelivery = sharedPref.getLong(ID_DELIVERY, 0);
        System.out.println("*** idDelivery = " + idDelivery);
        return idDelivery;
    }

    public static void guardarIdDelivery (Context context, long idDelivery) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE).edit();
        editor.putLong(ID_DELIVERY, idDelivery);
        editor.commit();
    }
}
