package proyecto.ucu.deliverit.utiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.entidades.Viaje;

public class CustomAdapter extends ArrayAdapter<Viaje> implements View.OnClickListener {

    private List<Viaje> viajes;
    Context mContext;

    private int lastPosition = -1;

    private static class ViewHolder {
        TextView restaurant_tv, direccion_tv, precio_tv;
        ImageView foto_restaurant_iv;
    }

    public CustomAdapter(List<Viaje> viajes, Context context) {
        super(context, R.layout.row, viajes);
        this.viajes = viajes;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object= getItem(position);
        Viaje viaje = (Viaje)object;

        switch (v.getId()) {
           /* case R.id.item_info:
                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break; */
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viaje viaje = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row, parent, false);
            viewHolder.direccion_tv = (TextView) convertView.findViewById(R.id.direccion_tv);
            viewHolder.precio_tv = (TextView) convertView.findViewById(R.id.precio_tv);
            viewHolder.restaurant_tv = (TextView) convertView.findViewById(R.id.restaurant_tv);
            viewHolder.foto_restaurant_iv = (ImageView) convertView.findViewById(R.id.foto_restaurant_iv);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.direccion_tv.setText("Dir.: " + viaje.getSucursal().getDireccion().getCalle() + " " + viaje.getSucursal().getDireccion().getNroPuerta());
        viewHolder.restaurant_tv.setText(viaje.getSucursal().getRestaurant().getRazonSocial());
        viewHolder.precio_tv.setText("Precio: $" + viaje.getPrecio());

        byte[] imgRestaurant = null;

        try {
            imgRestaurant = Operaciones.decodeImage(viaje.getSucursal().getRestaurant().getUsuario().getFoto());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bitmap imgBitmap = BitmapFactory.decodeByteArray(imgRestaurant, 0, imgRestaurant.length);
        viewHolder.foto_restaurant_iv.setImageBitmap(imgBitmap);

        return convertView;
    }
}
