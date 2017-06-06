package proyecto.ucu.deliverit.almacenamiento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import proyecto.ucu.deliverit.entidades.Cliente;
import proyecto.ucu.deliverit.entidades.Delivery;
import proyecto.ucu.deliverit.entidades.Direccion;
import proyecto.ucu.deliverit.entidades.EstadoViaje;
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

public class DataBase extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DeliverIT.db";

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String TYPE_BIG_INT = " BIGINT";
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
                    + Usuario.COLUMN_NAME_FOTO_PERFIL + TYPE_TEXT + ")";

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
            + Restaurant.COLUMN_NAME_RAZON_SOCIAL + TYPE_TEXT + ","
            + Restaurant.COLUMN_NAME_FOTO + TYPE_TEXT + ")";

    private static final String SQL_CREATE_TABLE_SUCURSAL
            = "CREATE TABLE " + Sucursal.TABLE_NAME + " (" +
            Sucursal._ID + TYPE_INTEGER + PRIMARY_KEY + ","
            + Sucursal.COLUMN_NAME_DIRECCION + TYPE_INTEGER + NOT_NULL + ","
            + Sucursal.COLUMN_NAME_RESTAURANT + TYPE_INTEGER + NOT_NULL + ")";

    private static final String SQL_CREATE_TABLE_VIAJE
            = "CREATE TABLE " + Viaje.TABLE_NAME + " (" +
                    Viaje._ID + TYPE_INTEGER + PRIMARY_KEY + ","
                    + Viaje.PRECIO + TYPE_SMALLINT + ","
                    + Viaje.COLUMN_NAME_SUCURSAL + TYPE_SMALLINT + ","
                    + Viaje.COLUMN_NAME_ESTADO + TYPE_SMALLINT  + NOT_NULL + ")";

    private static final String SQL_CREATE_TABLE_CLIENTE
            = "CREATE TABLE " + Cliente.TABLE_NAME + " (" +
            Cliente._ID + TYPE_INTEGER + PRIMARY_KEY + ","
            + Cliente.COLUMN_NAME_NOMBRE + TYPE_TEXT + ","
            + Cliente.COLUMN_NAME_DIRECCION + TYPE_INTEGER + NOT_NULL + ","
            + Cliente.COLUMN_NAME_TELEFONO + TYPE_TEXT + NOT_NULL + ")";

    private static final String SQL_CREATE_TABLE_PEDIDO
            = "CREATE TABLE " + Pedido.TABLE_NAME + " (" +
            Pedido._ID + TYPE_INTEGER + PRIMARY_KEY + ","
            + Pedido.COLUMN_NAME_VIAJE + TYPE_INTEGER + ","
            + Pedido.COLUMN_NAME_DETALLE + TYPE_TEXT + ","
            + Pedido.COLUMN_NAME_CLIENTE + TYPE_INTEGER + NOT_NULL + ")";

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

        SQLiteDatabase db = this.getWritableDatabase();

        long idNuevoUsuario = 0;

        if (getUsuario(usuario.getId()) == null) {
            Retorno retorno = Operaciones.validarDatos(usuario.getNombre(), usuario.getPassword(), usuario.getTelefono(),
                    usuario.getCuentaRedPagos());

            if (retorno.getCodigo().equals(Valores.CODIGO_EXITO)) {
                ContentValues values = new ContentValues();
                values.put(Usuario._ID, usuario.getId());
                values.put(Usuario.COLUMN_NAME_NOMBRE_USUARIO, usuario.getNombre());
                values.put(Usuario.COLUMN_NAME_PASSWORD, usuario.getPassword());
                values.put(Usuario.COLUMN_NAME_MAIL, usuario.getMail());
                values.put(Usuario.COLUMN_NAME_TELEFONO, usuario.getTelefono());
                values.put(Usuario.COLUMN_NAME_CUENTA_RED_PAGOS, usuario.getCuentaRedPagos());
                values.put(Usuario.COLUMN_NAME_FOTO_PERFIL, usuario.getFoto());

                idNuevoUsuario = db.insert(Usuario.TABLE_NAME, null, values);
            }

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

    public void insertarDireccion(Direccion direccion) throws SQLiteException {

        SQLiteDatabase db = this.getWritableDatabase();

        if (getDireccion(direccion.getId()) == null) {
            ContentValues values = new ContentValues();
            values.put(Direccion._ID, direccion.getId());
            values.put(Direccion.COLUMN_NAME_CALLE, direccion.getCalle());
            values.put(Direccion.COLUMN_NAME_NRO_PUERTA, direccion.getNroPuerta());
            values.put(Direccion.COLUMN_NAME_APARTAMENTO, direccion.getApartamento());
            values.put(Direccion.COLUMN_NAME_ESQUINA, direccion.getEsquina());
            values.put(Direccion.COLUMN_NAME_LATITUD, direccion.getLatitud());
            values.put(Direccion.COLUMN_NAME_LONGITUD, direccion.getLongitud());

            db.insert(Direccion.TABLE_NAME, null, values);
        }
    }

    public void insertarCliente(Cliente cliente) throws SQLiteException {

        SQLiteDatabase db = this.getWritableDatabase();

        if (getCliente(cliente.getId()) == null) {
            ContentValues values = new ContentValues();
            values.put(Cliente._ID, cliente.getId());
            values.put(Cliente.COLUMN_NAME_NOMBRE, cliente.getNombre());
            values.put(Cliente.COLUMN_NAME_DIRECCION, cliente.getDireccion().getId());
            values.put(Cliente.COLUMN_NAME_TELEFONO, cliente.getTelefono());

            db.insert(Cliente.TABLE_NAME, null, values);
        }
    }

    public void insertarRestaurant(Restaurant restaurant) throws SQLiteException {
        SQLiteDatabase db = this.getWritableDatabase();

        if (getRestaurant(restaurant.getId()) == null) {
            ContentValues values = new ContentValues();
            values.put(Restaurant._ID, restaurant.getId());
            values.put(Restaurant.COLUMN_NAME_RAZON_SOCIAL, restaurant.getRazonSocial());
            values.put(Restaurant.COLUMN_NAME_FOTO, restaurant.getUsuario().getFoto());
            db.insert(Restaurant.TABLE_NAME, null, values);
        }
    }

    public void insertarSucursal(Sucursal sucursal) throws SQLiteException {
        SQLiteDatabase db = this.getWritableDatabase();

        if (getSucursal(sucursal.getId()) == null) {
            ContentValues values = new ContentValues();
            values.put(sucursal._ID, sucursal.getId());
            values.put(Sucursal.COLUMN_NAME_DIRECCION, sucursal.getDireccion().getId());
            values.put(Sucursal.COLUMN_NAME_RESTAURANT, sucursal.getRestaurant().getId());

            db.insert(Sucursal.TABLE_NAME, null, values);
        }
    }

    public void insertarViaje(Viaje viaje) throws SQLiteException {
        SQLiteDatabase db = this.getWritableDatabase();

        if (getViaje(viaje.getId()) == null) {
            ContentValues values = new ContentValues();
            values.put(Viaje._ID, viaje.getId());
            values.put(Viaje.COLUMN_NAME_PRECIO, viaje.getPrecio());
            values.put(Viaje.COLUMN_NAME_SUCURSAL, viaje.getSucursal().getId());
            values.put(Viaje.COLUMN_NAME_ESTADO, viaje.getEstado().getId());
            db.insert(Viaje.TABLE_NAME, null, values);
        }
    }

    public void insertarPedido(Pedido pedido) throws SQLiteException {
        SQLiteDatabase db = this.getWritableDatabase();

        if (getPedido(pedido.getId()) == null) {
            ContentValues values = new ContentValues();
            values.put(Pedido._ID, pedido.getId());
            values.put(Pedido.COLUMN_NAME_CLIENTE, pedido.getCliente().getId());
            values.put(Pedido.COLUMN_NAME_DETALLE, pedido.getDetalle());
            values.put(Pedido.COLUMN_NAME_VIAJE, pedido.getViaje().getId());

            db.insert(Pedido.TABLE_NAME, null, values);
        }
    }

    public void eliminarViaje(Integer idViaje) throws SQLiteException {

        SQLiteDatabase db = this.getWritableDatabase();

        // Define 'where' part of query.
        String selection = Viaje._ID + " = ?";

        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(idViaje) };

        // Issue SQL statement.
        db.delete(Viaje.TABLE_NAME, selection, selectionArgs);
    }

    public void eliminarPedido(Integer idPedido) throws SQLiteException {

        SQLiteDatabase db = this.getWritableDatabase();

        String selection = Pedido._ID + " = ?";
        String[] selectionArgs = { String.valueOf(idPedido) };
        db.delete(Pedido.TABLE_NAME, selection, selectionArgs);
    }

    public void eliminarCliente(Integer idCliente) throws SQLiteException {

        SQLiteDatabase db = this.getWritableDatabase();

        String selection = Cliente._ID + " = ?";
        String[] selectionArgs = { String.valueOf(idCliente) };
        db.delete(Cliente.TABLE_NAME, selection, selectionArgs);
    }

    public void eliminarDireccion(Integer idDireccion) throws SQLiteException {

        SQLiteDatabase db = this.getWritableDatabase();

        String selection = Direccion._ID + " = ?";
        String[] selectionArgs = { String.valueOf(idDireccion) };
        db.delete(Direccion.TABLE_NAME, selection, selectionArgs);
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

        Short id = null;
        Double latitud = null;
        Double longitud = null;

        while (c.moveToNext()) {
            id = c.getShort(c.getColumnIndexOrThrow(Ubicacion._ID));
            latitud = c.getDouble(c.getColumnIndexOrThrow(Ubicacion.COLUMN_NAME_LATITUD));
            longitud = c.getDouble(c.getColumnIndexOrThrow(Ubicacion.COLUMN_NAME_LONGITUD));
        }
        c.close();

        Ubicacion ubicacion = null;
        if (id != null) {
            ubicacion = new Ubicacion();
            ubicacion.setId(id);
            ubicacion.setLatitud(latitud);
            ubicacion.setLongitud(longitud);
        }

        return ubicacion;
    }

    public Viaje getViaje(Integer idViaje) throws SQLiteException {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Viaje._ID, Viaje.COLUMN_NAME_PRECIO, Viaje.COLUMN_NAME_SUCURSAL, Viaje.COLUMN_NAME_ESTADO
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

        Integer precio = null;
        Integer idSucursal = null;
        Integer id = null;
        Integer estado = null;
        while (c.moveToNext()) {
            id = c.getInt(c.getColumnIndexOrThrow(Viaje._ID));
            precio = c.getInt(c.getColumnIndexOrThrow(Viaje.COLUMN_NAME_PRECIO));
            idSucursal = c.getInt(c.getColumnIndexOrThrow(Viaje.COLUMN_NAME_SUCURSAL));
            estado = c.getInt(c.getColumnIndexOrThrow(Viaje.COLUMN_NAME_ESTADO));
        }
        c.close();

        Viaje viaje = null;
        if (id != null) {
            Sucursal sucursal = getSucursal(idSucursal);
            viaje = new Viaje();
            viaje.setId(id);
            viaje.setPrecio(precio);
            viaje.setSucursal(sucursal);
            viaje.setEstado(new EstadoViaje(estado));
        }

        return viaje;
    }

    public Usuario getUsuario(Integer idUsuario) throws SQLiteException {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Usuario._ID, Usuario.COLUMN_NAME_FOTO_PERFIL, Usuario.COLUMN_NAME_TELEFONO, Usuario.COLUMN_NAME_CUENTA_RED_PAGOS,
                Usuario.COLUMN_NAME_NOMBRE_USUARIO, Usuario.COLUMN_NAME_PASSWORD
        };

        String selection = Usuario._ID + " = ?";
        String[] selectionArgs = { String.valueOf(idUsuario) };

        Cursor c = db.query(
                Usuario.TABLE_NAME,                      // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        Integer id = null;
        String foto = null;
        String telefono = null;
        Integer cuentaRedPagos = null;
        String nombreUsuario = null;
        String password = null;
        while (c.moveToNext()) {
            id = c.getInt(c.getColumnIndexOrThrow(Usuario._ID));
            foto = c.getString(c.getColumnIndexOrThrow(Usuario.COLUMN_NAME_FOTO_PERFIL));
            telefono = c.getString(c.getColumnIndexOrThrow(Usuario.COLUMN_NAME_TELEFONO));
            cuentaRedPagos = c.getInt(c.getColumnIndexOrThrow(Usuario.COLUMN_NAME_CUENTA_RED_PAGOS));
            nombreUsuario = c.getString(c.getColumnIndexOrThrow(Usuario.COLUMN_NAME_NOMBRE_USUARIO));
            password = c.getString(c.getColumnIndexOrThrow(Usuario.COLUMN_NAME_PASSWORD));
        }
        c.close();

        Usuario u = null;

        if (id != null) {
            u = new Usuario();
            u.setId(id);
            u.setFoto(foto);
            u.setTelefono(telefono);
            u.setCuentaRedPagos(cuentaRedPagos);
            u.setPassword(password);
            u.setNombre(nombreUsuario);
        }

        return u;
    }

    public Pedido getPedido(Integer idPedido) throws SQLiteException {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Pedido._ID, Pedido.COLUMN_NAME_DETALLE, Pedido.COLUMN_NAME_VIAJE, Pedido.COLUMN_NAME_CLIENTE
        };

        String selection = Pedido._ID + " = ?";
        String[] selectionArgs = { String.valueOf(idPedido) };

        Cursor c = db.query(
                Pedido.TABLE_NAME,                        // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        String detalle = null;
        Integer idViaje = null;
        Integer idCliente = null;
        Integer id = null;
        while (c.moveToNext()) {
            id = c.getInt(c.getColumnIndexOrThrow(Pedido._ID));
            detalle = c.getString(c.getColumnIndexOrThrow(Pedido.COLUMN_NAME_DETALLE));
            idViaje = c.getInt(c.getColumnIndexOrThrow(Pedido.COLUMN_NAME_VIAJE));
            idCliente = c.getInt(c.getColumnIndexOrThrow(Pedido.COLUMN_NAME_CLIENTE));
        }
        c.close();

        Pedido pedido = null;
        if (id != null) {
            Viaje viaje = getViaje(idViaje);
            Cliente cliente = getCliente(idCliente);
            pedido = new Pedido();
            pedido.setId(id);
            pedido.setDetalle(detalle);
            pedido.setViaje(viaje);
            pedido.setCliente(cliente);
        }

        return pedido;
    }

    public List<Pedido> getPedidos(Integer idViaje) throws SQLiteException {
        List<Pedido> pedidos = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Pedido._ID, Pedido.COLUMN_NAME_DETALLE, Pedido.COLUMN_NAME_VIAJE, Pedido.COLUMN_NAME_CLIENTE
        };

        String selection = Pedido.COLUMN_NAME_VIAJE + " = ?";
        String[] selectionArgs = { String.valueOf(idViaje) };

        Cursor c = db.query(
                Pedido.TABLE_NAME,                        // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        String detalle = null;
        Integer idPedido = null;
        Integer idCliente = null;
        while (c.moveToNext()) {
            idPedido = c.getInt(c.getColumnIndexOrThrow(Pedido._ID));
            detalle = c.getString(c.getColumnIndexOrThrow(Pedido.COLUMN_NAME_DETALLE));
            idCliente = c.getInt(c.getColumnIndexOrThrow(Pedido.COLUMN_NAME_CLIENTE));

            Viaje viaje = getViaje(idViaje);
            Cliente cliente = getCliente(idCliente);

            Pedido pedido = new Pedido();
            pedido.setId(idPedido);
            pedido.setViaje(viaje);
            pedido.setDetalle(detalle);
            pedido.setCliente(cliente);

            pedidos.add(pedido);
        }
        c.close();

        return pedidos;
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

    public Cliente getCliente(Integer idCliente) throws SQLiteException {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Cliente._ID, Cliente.COLUMN_NAME_NOMBRE, Cliente.COLUMN_NAME_TELEFONO, Cliente.COLUMN_NAME_DIRECCION
        };

        String selection = Cliente._ID + " = ?";
        String[] selectionArgs = { String.valueOf(idCliente) };

        Cursor c = db.query(
                Cliente.TABLE_NAME,                       // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        String nombre = null;
        String telefono = null;
        Integer idDireccion = null;
        Integer id = null;
        while (c.moveToNext()) {
            id = c.getInt(c.getColumnIndexOrThrow(Cliente._ID));
            nombre = c.getString(c.getColumnIndexOrThrow(Cliente.COLUMN_NAME_NOMBRE));
            telefono = c.getString(c.getColumnIndexOrThrow(Cliente.COLUMN_NAME_TELEFONO));
            idDireccion = c.getInt(c.getColumnIndexOrThrow(Cliente.COLUMN_NAME_DIRECCION));
        }
        c.close();

        Direccion direccion = null;

        if (idDireccion != null) {
            direccion = getDireccion(idDireccion);
        }

        Cliente cliente = null;

        if (id != null) {
            cliente = new Cliente();
            cliente.setId(id);
            cliente.setNombre(nombre);
            cliente.setTelefono(telefono);
            cliente.setDireccion(direccion);
        }

        return cliente;
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
                Restaurant._ID, Restaurant.COLUMN_NAME_RAZON_SOCIAL, Restaurant.COLUMN_NAME_FOTO
        };

        String selection = Restaurant._ID + " = ?";
        String[] selectionArgs = {String.valueOf(idRestaurant)};

        Cursor c = db.query(
                Restaurant.TABLE_NAME,                      // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        String razonSocial = null;
        String foto = null;
        Integer id = null;
        while (c.moveToNext()) {
            id = c.getInt(c.getColumnIndexOrThrow(Restaurant._ID));
            razonSocial = c.getString(c.getColumnIndexOrThrow(Restaurant.COLUMN_NAME_RAZON_SOCIAL));
            foto = c.getString(c.getColumnIndexOrThrow(Restaurant.COLUMN_NAME_FOTO));
        }

        c.close();
        Restaurant restaurant = null;
        if (id != null) {
            restaurant = new Restaurant();
            restaurant.setId(id);
            restaurant.setRazonSocial(razonSocial);
            restaurant.setFoto(foto);
        }
        return restaurant;
    }

    public Sucursal getSucursal(Integer idSucursal) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Sucursal._ID, Sucursal.COLUMN_NAME_DIRECCION, Sucursal.COLUMN_NAME_RESTAURANT
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

        String nombre = null;
        Direccion direccion = null;
        Restaurant restaurant = null;
        Integer id = null;
        while (c.moveToNext()) {
            id = c.getInt(c.getColumnIndexOrThrow(Sucursal._ID));
            direccion = getDireccion(c.getInt(c.getColumnIndexOrThrow(Sucursal.COLUMN_NAME_DIRECCION)));
            restaurant = getRestaurant(c.getInt(c.getColumnIndexOrThrow(Sucursal.COLUMN_NAME_RESTAURANT)));
        }
        c.close();

        Sucursal sucursal = null;
        if (id != null) {
            sucursal = new Sucursal();
            sucursal.setId(id);
            sucursal.setDireccion(direccion);
            sucursal.setRestaurant(restaurant);
        }

        return sucursal;
    }

    public Direccion getDireccion (Integer idDireccion) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Direccion._ID, Direccion.COLUMN_NAME_CALLE, Direccion.COLUMN_NAME_NRO_PUERTA, Direccion.COLUMN_NAME_ESQUINA, Direccion.COLUMN_NAME_LATITUD, Direccion.COLUMN_NAME_LONGITUD
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

        String calle = null;
        Integer nroPuerta = null;
        String esquina = null;
        Integer id = null;
        Double lat = null;
        Double longitud = null;
        while (c.moveToNext()) {
            id = c.getInt(c.getColumnIndexOrThrow(Direccion._ID));
            calle = c.getString(c.getColumnIndexOrThrow(Direccion.COLUMN_NAME_CALLE));
            nroPuerta = c.getInt(c.getColumnIndexOrThrow(Direccion.COLUMN_NAME_NRO_PUERTA));
            esquina = c.getString(c.getColumnIndexOrThrow(Direccion.COLUMN_NAME_ESQUINA));
            lat = c.getDouble(c.getColumnIndexOrThrow(Direccion.COLUMN_NAME_LATITUD));
            longitud = c.getDouble(c.getColumnIndexOrThrow(Direccion.COLUMN_NAME_LONGITUD));
        }
        c.close();

        Direccion direccion = null;
        if (id != null) {
            direccion = new Direccion();
            direccion.setId(id);
            direccion.setCalle(calle);
            direccion.setNroPuerta(nroPuerta);
            direccion.setEsquina(esquina);
            direccion.setLatitud(lat);
            direccion.setLongitud(longitud);
        }


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

    public void actualizarFoto(Integer id, String fotoBase64) throws SQLiteException {
        SQLiteDatabase db = this.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(Usuario.COLUMN_NAME_FOTO_PERFIL, fotoBase64);

        // Which row to update, based on the title
        String selection = Usuario._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        db.update(
                Usuario.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public void guardarDatosPedidos(List<Pedido> pedidos) throws SQLiteException {
        for (Pedido p : pedidos) {
            insertarRestaurant(p.getViaje().getSucursal().getRestaurant());
            insertarDireccion(p.getViaje().getSucursal().getDireccion());
            insertarSucursal(p.getViaje().getSucursal());
            insertarViaje(p.getViaje());
            insertarDireccion(p.getCliente().getDireccion());
            insertarCliente(p.getCliente());
            insertarPedido(p);
        }
    }
}
