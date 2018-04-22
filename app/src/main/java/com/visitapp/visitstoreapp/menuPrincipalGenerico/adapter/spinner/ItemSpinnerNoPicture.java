package com.visitapp.visitstoreapp.menuPrincipalGenerico.adapter.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.visitapp.visitstoreapp.R;

import java.util.List;
import java.util.Map;

public class ItemSpinnerNoPicture extends BaseAdapter {
    private List<Map> lista;
    private Context context;

    public ItemSpinnerNoPicture(List<Map> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }


    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1 seleccion del objeto por posicion
        System.out.println("LA LISTA:" + lista);
        final Map<String, String> obj = lista.get(position);
        System.out.println("ELEMENTO ASIGNAR"+obj);

        // 2 asignar el layaout
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_tienda_spinner_no_picture, null);
        }

        // 3 asignar los valores a los parametros
        TextView codigo = convertView.findViewById(R.id.idCodigoSpinnerNoPicture);
        TextView nombreTienda = convertView.findViewById(R.id.idNombreSpinnerNoPicture);

        codigo.setText(obj.get("codigo"));
        nombreTienda.setText(obj.get("nombre"));
        return convertView;
    }
}
