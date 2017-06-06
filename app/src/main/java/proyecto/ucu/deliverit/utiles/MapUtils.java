package proyecto.ucu.deliverit.utiles;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import proyecto.ucu.deliverit.entidades.Pedido;
import proyecto.ucu.deliverit.entidades.Sucursal;
import proyecto.ucu.deliverit.entidades.Ubicacion;

public class MapUtils {

    public static void agregarMarkersRecorrido(GoogleMap mapa, Ubicacion ubicacion, List<Pedido> pedidos) {

        LatLng ubicacionActual = new LatLng(ubicacion.getLatitud(), ubicacion.getLongitud());
        mapa.addMarker(new MarkerOptions().position(ubicacionActual).title(Valores.TU_UBICACION));
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionActual, 16.0f));

        for (Pedido p : pedidos) {
            if (p.getCliente().getDireccion().getLatitud() != null && p.getCliente().getDireccion().getLongitud() != null) {
                LatLng coordenadasCliente = new LatLng(p.getCliente().getDireccion().getLatitud(),
                        p.getCliente().getDireccion().getLongitud());
                mapa.addMarker(new MarkerOptions().position(coordenadasCliente).title(p.getCliente().getNombre())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }
        }
        LatLng coordenadasSuc = new LatLng(pedidos.get(0).getViaje().getSucursal().getDireccion().getLatitud(),
                pedidos.get(0).getViaje().getSucursal().getDireccion().getLongitud());
        mapa.addMarker(new MarkerOptions().position(coordenadasSuc).title(pedidos.get(0).getViaje().getSucursal().getRestaurant().getRazonSocial())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
    }

    public static void agregarMarkersMain(GoogleMap mapa, Ubicacion ubicacion, List<Sucursal> sucursales) {

        LatLng ubicacionActual = new LatLng(ubicacion.getLatitud(), ubicacion.getLongitud());
        mapa.addMarker(new MarkerOptions().position(ubicacionActual).title(Valores.TU_UBICACION));
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionActual, 16.0f));

        for (Sucursal s : sucursales) {
            if (s.getDireccion().getLatitud() != null && s.getDireccion().getLongitud() != null) {
                LatLng coordenadasCliente = new LatLng(s.getDireccion().getLatitud(), s.getDireccion().getLongitud());
                mapa.addMarker(new MarkerOptions().position(coordenadasCliente).title(s.getRestaurant().getRazonSocial())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }
        }
    }
}
