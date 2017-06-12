package proyecto.ucu.deliverit.custom_adapters;

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

import java.util.List;

import proyecto.ucu.deliverit.R;
import proyecto.ucu.deliverit.entidades.Viaje;
import proyecto.ucu.deliverit.utiles.Operaciones;

public class CustomAdapterForSidebar extends ArrayAdapter<Sidebar> {

    private List<Sidebar> opciones;
    private Context context;

    private int lastPosition = -1;

    public CustomAdapterForSidebar (List<Sidebar> opciones, Context context) {
        super(context, R.layout.row_sidebar, opciones);
        this.opciones = opciones;
        this.context = context;
    }

    private static class ViewHolder {
        TextView texto_sidebar_tv;
        ImageView icono_iv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String texto = getItem(position).getTexto();
        int idIcono = getItem(position).getIdIcono();
        CustomAdapterForSidebar.ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_sidebar, parent, false);
            viewHolder.texto_sidebar_tv = (TextView) convertView.findViewById(R.id.texto_sidebar_tv);
            viewHolder.icono_iv = (ImageView) convertView.findViewById(R.id.icono_iv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

      //  Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
      //  result.startAnimation(animation);
        lastPosition = position;

        viewHolder.texto_sidebar_tv.setText(texto);
        viewHolder.icono_iv.setImageResource(idIcono);

        return convertView;
    }
}
