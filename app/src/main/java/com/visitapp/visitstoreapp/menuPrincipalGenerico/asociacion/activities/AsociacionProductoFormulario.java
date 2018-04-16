package com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.visitapp.visitstoreapp.R;

public class AsociacionProductoFormulario extends AppCompatActivity {
    private TextView codigo;
    private TextView nombre;
    private TextView descripcion;
    private Spinner selectTienda;
    private Spinner selectProductoTipo;

    FloatingActionButton botonGuardar;
    FloatingActionButton botonCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asociacion_producto_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        codigo = findViewById(R.id.idCodigoProductoFormulario);
        nombre = findViewById(R.id.idNombreProductoFormulario);
        descripcion = findViewById(R.id.idDescripcionProductoFormulario);
        selectTienda = findViewById(R.id.idTiendaSelect);
        selectProductoTipo = findViewById(R.id.idProductoTipoSelect);

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
