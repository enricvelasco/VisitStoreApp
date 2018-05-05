package com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.sistema.controllers.asociaciones.AsociacionController;
import com.visitapp.visitstoreapp.sistema.domain.asociaciones.Asociacion;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

import static com.visitapp.visitstoreapp.login.PantallaLogIn.USUARIO_ACTUAL;

public class AsociacionConfiguracionParametros extends AppCompatActivity {

    Asociacion asociacion = new Asociacion();
    AsociacionController asociacionController = new AsociacionController();
    TextView nombreAsociacion;
    TextView distritoAsociacion;
    TextView emailAsociacion;
    TextView telefonoAsociacion;
    TextView direccionAsociacion;

    FloatingActionButton botonConfiguracionAvanzada;
    FloatingActionButton botonEdicionAsociacion;

    Dialog popUpConfiguracionAvanzada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asociacion_configuracion_parametros);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        asociacion.set_id(USUARIO_ACTUAL.getParametrosUsuarioActual().getAcceso_asociacion_id());

        cargarDatosFormulario();
        cargarAsociacion();

        botonConfiguracionAvanzada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpConfiguracionAvanzada = new Dialog(AsociacionConfiguracionParametros.this);
                popUpConfiguracionAvanzada.setContentView(R.layout.pop_up_configuracion_avanzada_asociacion);

                popUpConfiguracionAvanzada.show();

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void cargarDatosFormulario() {
        nombreAsociacion = findViewById(R.id.idNombreAsociacionConfiguracion);
        distritoAsociacion = findViewById(R.id.idNombreDistritoConfiguracionAsociacion);
        emailAsociacion = findViewById(R.id.idEmailAsociacionConfiguracion);
        telefonoAsociacion = findViewById(R.id.idTelefonoConfiguracionAsociacion);
        direccionAsociacion = findViewById(R.id.idDireccionAsociacionConfiguracion);

        botonConfiguracionAvanzada = findViewById(R.id.idButtonConfiguracionAvanzadaAsociacion);
        botonEdicionAsociacion = findViewById(R.id.idButtonEdicionAsociacionConfigurcaion);
    }
    private void cargarAsociacion(){
        asociacionController.read(asociacion, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                asociacion = data.getValue(Asociacion.class);
                asignarValoresCorrespondientes();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

    private void asignarValoresCorrespondientes() {
        nombreAsociacion.setText(asociacion.getNombre());
        distritoAsociacion.setText("distrito");
        emailAsociacion.setText(asociacion.getEmail());
        telefonoAsociacion.setText(asociacion.getTelefono());
        direccionAsociacion.setText(asociacion.getDireccion().direccionItemPopUp());
    }

}
