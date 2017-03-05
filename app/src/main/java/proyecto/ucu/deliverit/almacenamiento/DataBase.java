package proyecto.ucu.deliverit.almacenamiento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.entidades.Cliente;
import proyecto.ucu.deliverit.entidades.Delivery;
import proyecto.ucu.deliverit.entidades.Direccion;
import proyecto.ucu.deliverit.entidades.Pedido;
import proyecto.ucu.deliverit.entidades.Restaurant;
import proyecto.ucu.deliverit.entidades.Sucursal;
import proyecto.ucu.deliverit.entidades.Ubicacion;
import proyecto.ucu.deliverit.entidades.Usuario;
import proyecto.ucu.deliverit.entidades.Vehiculo;
import proyecto.ucu.deliverit.entidades.Viaje;
import proyecto.ucu.deliverit.utiles.Operaciones;
import proyecto.ucu.deliverit.utiles.Retorno;
import proyecto.ucu.deliverit.utiles.Valores;

/**
 * Created by DeliverIT on 31/01/2017.
 */

public class DataBase extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DeliverIT.db";

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String TYPE_BIG_INT = " BIGINT";
    private static final String TYPE_BLOB = " BLOB";
    private static final String TYPE_SMALLINT = " SMALLINT";
    private static final String TYPE_DOUBLE = " DOUBLE";

    private static final String PRIMARY_KEY = " PRIMARY KEY";

    private static final String NOT_NULL = " NOT NULL";

    private static final String SQL_CREATE_TABLE_USUARIO
            = "CREATE TABLE " + Usuario.TABLE_NAME + " (" +
                    Usuario._ID + TYPE_INTEGER + PRIMARY_KEY + ","
                    + Usuario.COLUMN_NAME_NOMBRE_USUARIO + TYPE_TEXT + NOT_NULL + ","
                    + Usuario.COLUMN_NAME_PASSWORD + TYPE_TEXT + NOT_NULL + ","
                    + Usuario.COLUMN_NAME_MAIL + TYPE_TEXT + ","
                    + Usuario.COLUMN_NAME_TELEFONO + TYPE_INTEGER + NOT_NULL + ","
                    + Usuario.COLUMN_NAME_CUENTA_RED_PAGOS + TYPE_INTEGER + NOT_NULL + ","
                    + Usuario.COLUMN_NAME_FOTO_PERFIL + TYPE_BLOB + ")";

    private static final String SQL_CREATE_TABLE_DELIVERY
            = "CREATE TABLE " + Delivery.TABLE_NAME + " (" +
                    Delivery._ID + TYPE_INTEGER + PRIMARY_KEY + ","
                    + Delivery.COLUMN_NAME_USUARIO + TYPE_INTEGER + NOT_NULL + ","
                    + Delivery.COLUMN_NAME_TOKEN + TYPE_TEXT + ","
                    + Delivery.COLUMN_NAME_UBICACION + TYPE_INTEGER + ","
                    + Delivery.COLUMN_NAME_VEHICULO + TYPE_SMALLINT + ")";

    private static final String SQL_CREATE_TABLE_DIRECCION
            = "CREATE TABLE " + Direccion.TABLE_NAME + " (" +
            Direccion._ID + TYPE_INTEGER + PRIMARY_KEY + ","
            + Direccion.COLUMN_NAME_CALLE + TYPE_TEXT + NOT_NULL + ","
            + Direccion.COLUMN_NAME_NRO_PUERTA + TYPE_SMALLINT + NOT_NULL + ","
            + Direccion.COLUMN_NAME_ESQUINA + TYPE_TEXT + ","
            + Direccion.COLUMN_NAME_APARTAMENTO + TYPE_SMALLINT + ","
            + Direccion.COLUMN_NAME_LATITUD + TYPE_DOUBLE + ","
            + Direccion.COLUMN_NAME_LONGITUD + TYPE_DOUBLE + ")";

    private static final String SQL_CREATE_TABLE_RESTAURANT
            = "CREATE TABLE " + Restaurant.TABLE_NAME + " (" +
            Restaurant._ID + TYPE_INTEGER + PRIMARY_KEY + ","
            + Restaurant.COLUMN_NAME_RUT + TYPE_BIG_INT + NOT_NULL + ","
            + Restaurant.COLUMN_NAME_RAZON_SOCIAL + TYPE_TEXT + ")";

    private static final String SQL_CREATE_TABLE_SUCURSAL
            = "CREATE TABLE " + Sucursal.TABLE_NAME + " (" +
            Sucursal._ID + TYPE_SMALLINT + ","
            + Sucursal.COLUMN_NOMBRE + TYPE_TEXT + ","
            + Sucursal.COLUMN_NAME_DIRECCION + TYPE_INTEGER + NOT_NULL + ","
            + Sucursal.COLUMN_NAME_RESTAURANT + TYPE_INTEGER + ","
            + PRIMARY_KEY + " (" + Sucursal._ID + ", " + Sucursal.COLUMN_NAME_RESTAURANT + "))";

    private static final String SQL_CREATE_TABLE_VIAJE
            = "CREATE TABLE " + Viaje.TABLE_NAME + " (" +
                    Viaje._ID + TYPE_INTEGER + PRIMARY_KEY + ","
                    + Viaje.COLUMN_NAME_CALIFICAION + TYPE_SMALLINT + ","
                    + Viaje.COLUMN_NAME_DELIVERY + TYPE_INTEGER + ","
                    + Viaje.PRECIO + TYPE_SMALLINT + ","
                    + Viaje.COLUMN_NAME_SUCURSAL + TYPE_SMALLINT + ","
                    + Viaje.COLUMN_NAME_RESTAURANT + TYPE_INTEGER + ")";

    private static final String SQL_CREATE_TABLE_CLIENTE
            = "CREATE TABLE " + Cliente.TABLE_NAME + " (" +
            Cliente._ID + TYPE_INTEGER + PRIMARY_KEY + ","
            + Cliente.COLUMN_NAME_NOMBRE + TYPE_TEXT + ","
            + Cliente.COLUMN_NAME_DIRECCION + TYPE_INTEGER + ","
            + Cliente.COLUMN_NAME_TELEFONO + TYPE_TEXT + NOT_NULL + ")";

    private static final String SQL_CREATE_TABLE_PEDIDO
            = "CREATE TABLE " + Pedido.TABLE_NAME + " (" +
            Pedido._ID + TYPE_INTEGER + ","
            + Pedido.COLUMN_NAME_VIAJE + TYPE_INTEGER + ","
            + Pedido.COLUMN_NAME_DETALLE + TYPE_TEXT + ","
            + Pedido.COLUMN_NAME_FORMA_PAGO + TYPE_TEXT + ","
            + Pedido.COLUMN_NAME_CLIENTE + TYPE_INTEGER + NOT_NULL + ","
            + PRIMARY_KEY + " (" + Pedido._ID + ", " + Pedido.COLUMN_NAME_VIAJE + "))";

    private static final String SQL_CREATE_TABLE_VEHICULO
            = "CREATE TABLE " + Vehiculo.TABLE_NAME + " (" +
            Vehiculo._ID + TYPE_SMALLINT + ","
            + Vehiculo.COLUMN_NAME_DESCRIPCION + TYPE_TEXT + ")";

    private static final String SQL_CREATE_TABLE_UBICACION
            = "CREATE TABLE " + Ubicacion.TABLE_NAME + " (" +
            Ubicacion._ID + TYPE_SMALLINT + PRIMARY_KEY + ","
            + Ubicacion.COLUMN_NAME_LATITUD + TYPE_DOUBLE + NOT_NULL + ","
            + Ubicacion.COLUMN_NAME_LONGITUD + TYPE_DOUBLE + NOT_NULL + ")";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USUARIO);
        db.execSQL(SQL_CREATE_TABLE_DELIVERY);
        db.execSQL(SQL_CREATE_TABLE_DIRECCION);
        db.execSQL(SQL_CREATE_TABLE_RESTAURANT);
        db.execSQL(SQL_CREATE_TABLE_SUCURSAL);
        db.execSQL(SQL_CREATE_TABLE_VIAJE);
        db.execSQL(SQL_CREATE_TABLE_CLIENTE);
        db.execSQL(SQL_CREATE_TABLE_PEDIDO);
        db.execSQL(SQL_CREATE_TABLE_VEHICULO);
        db.execSQL(SQL_CREATE_TABLE_UBICACION);

        ContentValues values = new ContentValues();

        values.put(Vehiculo._ID, 1);
        values.put(Vehiculo.COLUMN_NAME_DESCRIPCION, "Automotor");
        db.insert(Vehiculo.TABLE_NAME, null, values);

        values.put(Vehiculo._ID, 2);
        values.put(Vehiculo.COLUMN_NAME_DESCRIPCION, "Ciclomotor");
        db.insert(Vehiculo.TABLE_NAME, null, values);

        values.put(Vehiculo._ID, 3);
        values.put(Vehiculo.COLUMN_NAME_DESCRIPCION, "Bicileta");
        db.insert(Vehiculo.TABLE_NAME, null, values);

        values.put(Vehiculo._ID, 4);
        values.put(Vehiculo.COLUMN_NAME_DESCRIPCION, "Skate");
        db.insert(Vehiculo.TABLE_NAME, null, values);

        values.put(Vehiculo._ID, 5);
        values.put(Vehiculo.COLUMN_NAME_DESCRIPCION, "Rollers");
        db.insert(Vehiculo.TABLE_NAME, null, values);

        values.put(Vehiculo._ID, 6);
        values.put(Vehiculo.COLUMN_NAME_DESCRIPCION, "Ninguno");
        db.insert(Vehiculo.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertarUbicacion (Ubicacion ubicacion) throws SQLiteException {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        Retorno retorno = Operaciones.validarLatitudLongitud(ubicacion.getLatitud(), ubicacion.getLongitud());

        if (retorno.getCodigo().equals(Valores.CODIGO_EXITO)) {
            ContentValues values = new ContentValues();
            values.put(Ubicacion._ID, ubicacion.getId());
            values.put(Ubicacion.COLUMN_NAME_LATITUD, ubicacion.getLatitud());
            values.put(Ubicacion.COLUMN_NAME_LONGITUD, ubicacion.getLongitud());

            db.insert(Ubicacion.TABLE_NAME, null, values);
        }
    }

    public long insertarUsuario (Usuario usuario) throws SQLiteException {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        Retorno retorno = Operaciones.validarDatos(usuario.getNombre(), usuario.getPassword(), usuario.getTelefono(),
                usuario.getCuentaRedPagos());

        long idNuevoUsuario = 0;

        if (retorno.getCodigo().equals(Valores.CODIGO_EXITO)) {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(Usuario._ID, usuario.getId());
            values.put(Usuario.COLUMN_NAME_NOMBRE_USUARIO, usuario.getNombre());
            values.put(Usuario.COLUMN_NAME_PASSWORD, usuario.getPassword());
            values.put(Usuario.COLUMN_NAME_MAIL, usuario.getMail());
            values.put(Usuario.COLUMN_NAME_TELEFONO, usuario.getTelefono());
            values.put(Usuario.COLUMN_NAME_CUENTA_RED_PAGOS, usuario.getCuentaRedPagos());
            values.put(Usuario.COLUMN_NAME_FOTO_PERFIL, usuario.getFoto());

            // Insert the new row, returning the primary key value of the new row
            idNuevoUsuario = db.insert(Usuario.TABLE_NAME, null, values);
        }
        return idNuevoUsuario;
    }

    public void insertarDelivery (Delivery delivery) throws SQLiteException {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Delivery._ID, delivery.getId());
        values.put(Delivery.COLUMN_NAME_USUARIO, delivery.getUsuario().getId());
        values.put(Delivery.COLUMN_NAME_TOKEN, delivery.getToken());
        values.put(Delivery.COLUMN_NAME_UBICACION, delivery.getUbicacion().getId());
        values.put(Delivery.COLUMN_NAME_VEHICULO, delivery.getVehiculo().getId());

        db.insert(Delivery.TABLE_NAME, null, values);
    }

    public long insertarDireccion(Direccion direccion) throws SQLiteException {

        SQLiteDatabase db = this.getWritableDatabase();

        if (getDireccion(direccion.getId()) == null) {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(Direccion._ID, direccion.getId());
            values.put(Direccion.COLUMN_NAME_CALLE, direccion.getCalle());
            values.put(Direccion.COLUMN_NAME_NRO_PUERTA, direccion.getNroPuerta());
            values.put(Direccion.COLUMN_NAME_APARTAMENTO, direccion.getApartamento());
            values.put(Direccion.COLUMN_NAME_ESQUINA, direccion.getEsquina());
            values.put(Direccion.COLUMN_NAME_LATITUD, direccion.getLatitud());
            values.put(Direccion.COLUMN_NAME_LONGITUD, direccion.getLongitud());

            // Insert the new row, returning the primary key value of the new row
            return db.insert(Direccion.TABLE_NAME, null, values);
        } else {
            return 0;
        }

    }

    public long insertarCliente(Cliente cliente) throws SQLiteException {

        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Cliente._ID, cliente.getId());
        values.put(Cliente.COLUMN_NAME_NOMBRE, cliente.getNombre());
        values.put(Cliente.COLUMN_NAME_DIRECCION, cliente.getDireccion());
        values.put(Cliente.COLUMN_NAME_TELEFONO, cliente.getTelefono());

        // Insert the new row, returning the primary key value of the new row
        return db.insert(Cliente.TABLE_NAME, null, values);
    }

    public long insertarRestaurant(Restaurant restaurant) throws SQLiteException {

        SQLiteDatabase db = this.getWritableDatabase();

        Restaurant r = getRestaurant(restaurant.getId());

        // Si el restaurant no existe lo inserto
        if (r == null) {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(Restaurant._ID, restaurant.getId());
            values.put(Restaurant.COLUMN_NAME_RUT, restaurant.getRut());
            values.put(Restaurant.COLUMN_NAME_RAZON_SOCIAL, restaurant.getRazonSocial());

            // Insert the new row, returning the primary key value of the new row
            return db.insert(Restaurant.TABLE_NAME, null, values);
        } else {
            return 0;
        }

    }

    public long insertarSucursal(Sucursal sucursal) throws SQLiteException {

        SQLiteDatabase db = this.getWritableDatabase();

        if (getSucursal(sucursal.getId()) == null) {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(sucursal._ID, sucursal.getId());
            values.put(Sucursal.COLUMN_NOMBRE, sucursal.getNombre());
            values.put(Sucursal.COLUMN_NAME_DIRECCION, sucursal.getDireccion().getId());
            values.put(Sucursal.COLUMN_NAME_RESTAURANT, sucursal.getRestaurant().getId());

            // Insert the new row, returning the primary key value of the new row
            return db.insert(Sucursal.TABLE_NAME, null, values);
        } else {
            return 0;
        }

    }

    public long insertarViaje(Viaje viaje) throws SQLiteException {

        SQLiteDatabase db = this.getWritableDatabase();

        if (getViaje(viaje.getId()) == null) {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(Viaje._ID, viaje.getId());
            values.put(Viaje.COLUMN_NAME_CALIFICAION, viaje.getCalificacion());
            values.put(Viaje.COLUMN_NAME_PRECIO, viaje.getPrecio());
            values.put(Viaje.COLUMN_NAME_RESTAURANT, viaje.getSucursal().getRestaurant().getId());
            values.put(Viaje.COLUMN_NAME_SUCURSAL, viaje.getSucursal().getId());

            // Insert the new row, returning the primary key value of the new row
            return db.insert(Viaje.TABLE_NAME, null, values);
        } else {
            return 0;
        }

    }

    public long insertarPedido(Pedido pedido) throws SQLiteException {

        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Pedido._ID, pedido.getId());
        values.put(Pedido.COLUMN_NAME_CLIENTE, pedido.getCliente().getId());
        values.put(Pedido.COLUMN_NAME_DETALLE, pedido.getDetalle());
        values.put(Pedido.COLUMN_NAME_FORMA_PAGO, pedido.getFormaPago());
        values.put(Pedido.COLUMN_NAME_VIAJE, pedido.getViaje().getId());

        // Insert the new row, returning the primary key value of the new row
        return db.insert(Pedido.TABLE_NAME, null, values);
    }

    public Ubicacion getUbicacion() throws SQLiteException {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Ubicacion._ID, Ubicacion.COLUMN_NAME_LATITUD, Ubicacion.COLUMN_NAME_LONGITUD
        };

        Cursor c = db.query(
                Ubicacion.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                "",                                       // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        c.moveToFirst();
        Short id = c.getShort(c.getColumnIndexOrThrow(Ubicacion._ID));
        Double latitud = c.getDouble(c.getColumnIndexOrThrow(Ubicacion.COLUMN_NAME_LATITUD));
        Double longitud = c.getDouble(c.getColumnIndexOrThrow(Ubicacion.COLUMN_NAME_LONGITUD));
        c.close();

        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setId(id);
        ubicacion.setLatitud(latitud);
        ubicacion.setLongitud(longitud);

        return ubicacion;
    }

    public Viaje getViaje(Integer idViaje) throws SQLiteException {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Viaje.COLUMN_NAME_PRECIO, Viaje.COLUMN_NAME_RESTAURANT, Viaje.COLUMN_NAME_SUCURSAL
        };

        String selection = Viaje._ID + " = ?";
        String[] selectionArgs = { String.valueOf(idViaje) };

        Cursor c = db.query(
                Viaje.TABLE_NAME,                      // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        c.moveToFirst();
        Integer precio = c.getInt(c.getColumnIndexOrThrow(Viaje.COLUMN_NAME_PRECIO));
        Integer idRestaurant = c.getInt(c.getColumnIndexOrThrow(Viaje.COLUMN_NAME_RESTAURANT));
        Integer idSucursal = c.getInt(c.getColumnIndexOrThrow(Viaje.COLUMN_NAME_SUCURSAL));
        c.close();

        Restaurant restaurant = getRestaurant(idRestaurant);
        Sucursal sucursal = getSucursal(idSucursal);
        Viaje viaje = new Viaje();
        viaje.setId(idViaje);
        viaje.setPrecio(precio);
        viaje.setSucursal(sucursal);
        viaje.setRestaurant(restaurant);

        return viaje;
    }

    public Vehiculo getVehiculo(String desc) throws SQLiteException {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Vehiculo._ID, Vehiculo.COLUMN_NAME_DESCRIPCION
        };

        String selection = Vehiculo.COLUMN_NAME_DESCRIPCION + " = ?";
        String[] selectionArgs = { desc };

        Cursor c = db.query(
                Vehiculo.TABLE_NAME,                      // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        c.moveToFirst();
        Short id = c.getShort(c.getColumnIndexOrThrow(Vehiculo._ID));
        c.close();

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(id);
        vehiculo.setDescripcion(desc);

        return vehiculo;
    }

    public List<Vehiculo> getVehiculos() throws SQLiteException {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Vehiculo._ID, Vehiculo.COLUMN_NAME_DESCRIPCION
        };

        Cursor c = db.query(
                Vehiculo.TABLE_NAME,                      // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        List<Vehiculo> vehiculos = new ArrayList<>();
        if (c.moveToFirst()) {
            while (c.isAfterLast() == false) {
                Short id = c.getShort((short)c.getColumnIndexOrThrow(Vehiculo._ID));
                String descripcion = c.getColumnName(c.getColumnIndexOrThrow(Vehiculo.COLUMN_NAME_DESCRIPCION));
                Vehiculo v = new Vehiculo();
                v.setId(id);
                v.setDescripcion(descripcion);
                vehiculos.add(v);
                c.moveToNext();
            }
        }

        c.close();

        return vehiculos;
    }

    public Restaurant getRestaurant(Integer idRestaurant) throws SQLiteException {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Restaurant.COLUMN_NAME_RAZON_SOCIAL
        };

        String selection = Restaurant._ID + " = ?";
        String[] selectionArgs = { String.valueOf(idRestaurant) };

        Cursor c = db.query(
                Restaurant.TABLE_NAME,                      // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        c.moveToFirst();
        String razonSocial = c.getString(c.getColumnIndexOrThrow(Restaurant.COLUMN_NAME_RAZON_SOCIAL));
        c.close();

        Restaurant restaurant = new Restaurant();
        restaurant.setId(idRestaurant);
        restaurant.setRazonSocial(razonSocial);

        return restaurant;
    }

    public Sucursal getSucursal(Integer idSucursal) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Sucursal.COLUMN_NAME_DIRECCION, Sucursal.COLUMN_NOMBRE
        };

        String selection = Sucursal._ID + " = ?";
        String[] selectionArgs = { String.valueOf(idSucursal) };

        Cursor c = db.query(
                Sucursal.TABLE_NAME,                      // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        c.moveToFirst();
        String nombre = c.getString(c.getColumnIndexOrThrow(Sucursal.COLUMN_NOMBRE));
        Integer idDireccion = c.getInt(c.getColumnIndexOrThrow(Sucursal.COLUMN_NAME_DIRECCION));
        c.close();

        Direccion direccion = getDireccion(idDireccion);

        Sucursal sucursal = new Sucursal();
        sucursal.setId(idSucursal);
        sucursal.setNombre(nombre);
        sucursal.setDireccion(direccion);

        return sucursal;
    }

    public Direccion getDireccion (Integer idDireccion) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Direccion.COLUMN_NAME_CALLE, Direccion.COLUMN_NAME_NRO_PUERTA, Direccion.COLUMN_NAME_ESQUINA
        };

        String selection = Direccion._ID + " = ?";
        String[] selectionArgs = { String.valueOf(idDireccion) };

        Cursor c = db.query(
                Direccion.TABLE_NAME,                      // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        c.moveToFirst();
        String calle = c.getString(c.getColumnIndexOrThrow(Direccion.COLUMN_NAME_CALLE));
        Integer nroPuerta = c.getInt(c.getColumnIndexOrThrow(Direccion.COLUMN_NAME_NRO_PUERTA));
        String esquina = c.getString(c.getColumnIndexOrThrow(Direccion.COLUMN_NAME_ESQUINA));
        c.close();

        Direccion direccion = new Direccion();
        direccion.setId(idDireccion);
        direccion.setCalle(calle);
        direccion.setNroPuerta(nroPuerta);
        direccion.setEsquina(esquina);

        return direccion;
    }

    public void actualizarUbicacion(Ubicacion ubicacion) throws SQLiteException {
        SQLiteDatabase db = this.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(Ubicacion.COLUMN_NAME_LATITUD, ubicacion.getLatitud());
        values.put(Ubicacion.COLUMN_NAME_LONGITUD, ubicacion.getLongitud());

        // Which row to update, based on the title
        String selection = Ubicacion._ID + " = ?";
        String[] selectionArgs = { String.valueOf(ubicacion.getId()) };

        db.update(
                Ubicacion.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

}
