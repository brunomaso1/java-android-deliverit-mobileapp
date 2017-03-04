package proyecto.ucu.deliverit.almacenamiento;


import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by deliverIT on 31/01/2017.
 */

public class SharedPref {
    private static final String SHARED_PREF_NAME = "SharedPreferencesDeliverIT";

    private static final String ID_DELIVERY = "idDelivery";

    private static final String TOKEN = "token";

    public static long getIdDelivery (Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        long idDelivery = sharedPref.getLong(ID_DELIVERY, 0);
        return idDelivery;
    }

    public static void guardarIdDelivery (Context context, long idDelivery) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE).edit();
        editor.putLong(ID_DELIVERY, idDelivery);
        editor.commit();
    }

    public static String getToken (Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String token = sharedPref.getString(TOKEN, "");
        return token;
    }

    public static void guardarToken (Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE).edit();
        editor.putString(TOKEN, token);
        editor.commit();
    }
}
