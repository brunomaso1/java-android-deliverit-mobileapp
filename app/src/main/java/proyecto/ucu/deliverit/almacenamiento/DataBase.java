package proyecto.ucu.deliverit.almacenamiento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.entidades.Delivery;
import proyecto.ucu.deliverit.utiles.Operaciones;
import proyecto.ucu.deliverit.utiles.Retorno;
import proyecto.ucu.deliverit.utiles.Valores;

/**
 * Created by Juan on 31/01/2017.
 */

public class DataBase extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DeliverIT.db";

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String TYPE_BLOB = " BLOB";

    private static final String PRIMARY_KEY = " PRIMARY KEY";

    private static final String NOT_NULL = " NOT NULL";

    private static final String SQL_CREATE_TABLE_DELIVERY
            = "CREATE TABLE " + Delivery.TABLE_NAME + " (" +
                    Delivery._ID + TYPE_INTEGER + PRIMARY_KEY + ","
                    + Delivery.COLUMN_NAME_NOMBRE_USUARIO + TYPE_TEXT + NOT_NULL + ","
                    + Delivery.COLUMN_NAME_PASSWORD + TYPE_TEXT + NOT_NULL + ","
                    + Delivery.COLUMN_NAME_NOMBRE + TYPE_TEXT + ","
                    + Delivery.COLUMN_NAME_MAIL + TYPE_TEXT + ","
                    + Delivery.COLUMN_NAME_TELEFONO + TYPE_INTEGER + NOT_NULL + ","
                    + Delivery.COLUMN_NAME_CUENTA_RED_PAGOS + TYPE_INTEGER + NOT_NULL + ","
                    + Delivery.COLUMN_NAME_FOTO_PERFIL + TYPE_BLOB + ")";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_DELIVERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long agregarDelivery (String nombreUsuario, String password, String nombre, String mail, String telefono,
                                 int cuentaRedPagos, byte[] fotoPerfil) throws SQLiteException {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        Retorno retorno = Operaciones.validarDatos(nombreUsuario, password, telefono, cuentaRedPagos);

        long idNuevoDelivery = 0;

        if (retorno.getCodigo().equals("0")) {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(Delivery.COLUMN_NAME_NOMBRE_USUARIO, nombreUsuario);
            values.put(Delivery.COLUMN_NAME_PASSWORD, password);
            values.put(Delivery.COLUMN_NAME_NOMBRE, nombre);
            values.put(Delivery.COLUMN_NAME_MAIL, mail);
            values.put(Delivery.COLUMN_NAME_TELEFONO, telefono);
            values.put(Delivery.COLUMN_NAME_CUENTA_RED_PAGOS, cuentaRedPagos);
            values.put(Delivery.COLUMN_NAME_FOTO_PERFIL, fotoPerfil);

            // Insert the new row, returning the primary key value of the new row
            idNuevoDelivery = db.insert(Delivery.TABLE_NAME, null, values);
        }
        return idNuevoDelivery;
    }

    public long login (String usuario, String password) throws SQLiteException {

        long idDelivery = 0;

        Retorno retornoPassword = Operaciones.validarPassword(password);

        // Si la contraseña es válida busco un delivery con los datos ingresados
        if (retornoPassword.getCodigo().equals(Valores.CODIGO_EXITO)) {
            SQLiteDatabase db = this.getReadableDatabase();

            // Columna que voy a retornar en la query
            String[] projection = {
                    Delivery._ID
            };

            // Condición del WHERE
            String selection = Delivery.COLUMN_NAME_NOMBRE_USUARIO + " = ? AND " + Delivery.COLUMN_NAME_PASSWORD + " = ?";
            String[] selectionArgs = { usuario, password };

            Cursor c = db.query(
                    Delivery.TABLE_NAME,                      // The table to query
                    projection,                               // The columns to return
                    selection,                                // The columns for the WHERE clause
                    selectionArgs,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    null                                      // The sort order
            );

            c.moveToFirst();
            idDelivery = c.getLong(
                    c.getColumnIndexOrThrow(Delivery._ID)
            );
            c.close();
        }

        return idDelivery;
    }
}
