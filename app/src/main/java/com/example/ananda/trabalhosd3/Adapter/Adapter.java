package com.example.ananda.trabalhosd3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.ananda.trabalhosd3.Model.Paises;
import com.example.ananda.trabalhosd3.R;

import java.util.List;




public class Adapter extends ArrayAdapter<Paises> {


    public Adapter(Context context, List<Paises> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Paises paises = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_paises, parent, false);
        }

        TextView tvNome = (TextView) convertView.findViewById(R.id.nome);
        TextView tvCapital = (TextView) convertView.findViewById(R.id.capital);


        tvNome.setText(paises.name);
        tvCapital.setText(paises.capital);


        return convertView;
    }

}
