package com.visitapp.visitstoreapp.menuPrincipalGenerico.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.visitapp.visitstoreapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPrincipal extends Fragment {

    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment_principal, container, false);
        //return inflater.inflate(R.layout.fragment_fragment_principal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        textView = view.findViewById(R.id.textoFragment1);
        textView.setText("BIENVENIDO A FRAGMENT PRINCIPAL");
        super.onViewCreated(view, savedInstanceState);
    }


}
