package com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.UsuarioActual;
import com.visitapp.visitstoreapp.sistema.controllers.tiendas.TiendaController;
import com.visitapp.visitstoreapp.sistema.domain.tiendas.Tienda;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

import static com.visitapp.visitstoreapp.login.PantallaLogIn.USUARIO_ACTUAL;

public class AsociacionTiendaFormulario extends AppCompatActivity {
    FloatingActionButton botonEdicion;

    TextView codigo;
    TextView nombreComercial;
    TextView nombreFiscal;
    TextView nif;
    TextView email;
    TextView telefono;
    TextView direccion;

    Tienda tienda;

    String idEdicion;
    String estadoFormulario;

    EditText codigoEd;
    EditText nombreComercialEd;
    EditText nombreFiscalEd;
    EditText nifEd;
    EditText direccionEd;
    Switch permitePromocionesEd;

    Dialog dialogTiendaEdicion;

    TiendaController tiendaController = new TiendaController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asociacion_tienda_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent myIntent = getIntent();
        idEdicion = myIntent.getStringExtra("tienda_id");
        declararPopUp();
        if(idEdicion == null){
            System.out.println("ESTADO NUEVO");
            estadoFormulario = "nuevo";
            tienda = new Tienda();
            popUpFormularioTienda();
        }else{
            estadoFormulario = "edicion";
            tiendaController.read(idEdicion, new OnGetDataListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(DataSnapshot data) {
                    tienda = data.getValue(Tienda.class);
                }

                @Override
                public void onFailed(DatabaseError databaseError) {

                }
            });
        }

        inicializarCampos();

        botonEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codigoEd.setText(codigo.getText());
                nombreComercialEd.setText(nombreComercial.getText());
                nombreFiscalEd.setText(nombreFiscal.getText());
                nifEd.setText(nif.getText());

                direccionEd.setText(direccion.getText());
                popUpFormularioTienda();
                /*final Dialog dialogTiendaEdicion = new Dialog(AsociacionTiendaFormulario.this);
                dialogTiendaEdicion.setContentView(R.layout.pop_up_asociacion_edicion_tienda);
                final EditText codigoEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaCodigo);
                final EditText nombreComercialEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaNombreComercial);
                final EditText nombreFiscalEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaNombreFiscal);
                final EditText nifEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaNif);
                EditText emailEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaNombreFiscal);
                EditText telefonoEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaTelefono);
                final EditText direccionEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaDireccion);
                final Switch permitePromocionesEd = dialogTiendaEdicion.findViewById(R.id.switchFormularioTiendaPermitePromociones);

                FloatingActionButton botonGuardarPopUp = dialogTiendaEdicion.findViewById(R.id.idButtonGuardarCambiosTienda);
                FloatingActionButton botonCancelarPopUo = dialogTiendaEdicion.findViewById(R.id.idCanelarCambiosTienda);

                codigoEd.setText(codigo.getText());
                nombreComercialEd.setText(nombreComercial.getText());
                nombreFiscalEd.setText(nombreFiscal.getText());
                nifEd.setText(nif.getText());

                direccionEd.setText(direccion.getText());

                dialogTiendaEdicion.show();

                botonGuardarPopUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tienda.setAsociacion_id(USUARIO_ACTUAL.getParametrosUsuarioActual().getAcceso_asociacion_id());
                        tienda.setCodigo(codigoEd.getText().toString());
                        tienda.setNombrePublico(nombreComercialEd.getText().toString());
                        tienda.setNombreFiscal(nombreFiscalEd.getText().toString());
                        tienda.setNif(nifEd.getText().toString());
                        //tienda. //introducir EMAIL
                        //tienda. //introducir TELEFONO
                        tienda.setDireccion(direccionEd.getText().toString());
                        tienda.setPermitePromociones(permitePromocionesEd.isChecked());

                        codigo.setText(tienda.getCodigo());
                        nombreComercial.setText(tienda.getNombrePublico());
                        nombreFiscal.setText(tienda.getNombreFiscal());
                        nif.setText(tienda.getNif());
                        direccion.setText(tienda.getDireccion());

                        dialogTiendaEdicion.dismiss();
                    }
                });

                botonCancelarPopUo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogTiendaEdicion.dismiss();
                    }
                });*/

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void inicializarCampos() {
        codigo = findViewById(R.id.idCodigoTiendaFormulario);
        nombreComercial = findViewById(R.id.idNombrePublicoTiendaFormulario);
        nombreFiscal = findViewById(R.id.idNombreFiscalTiendaFormulario);
        nif = findViewById(R.id.idNifTiendaFormulario);
        email = findViewById(R.id.idEmailTiendaFormulario);
        telefono = findViewById(R.id.idTelefonoTiendaFormulario);
        direccion = findViewById(R.id.idDireccionTiendaFormulario);

        botonEdicion = findViewById(R.id.idButtonEdicionTiendaFormulario);
    }

    private void declararPopUp(){
        dialogTiendaEdicion = new Dialog(AsociacionTiendaFormulario.this);
        dialogTiendaEdicion.setContentView(R.layout.pop_up_asociacion_edicion_tienda);
        codigoEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaCodigo);
        nombreComercialEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaNombreComercial);
        nombreFiscalEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaNombreFiscal);
        nifEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaNif);
        EditText emailEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaNombreFiscal);
        EditText telefonoEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaTelefono);
        direccionEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaDireccion);
        permitePromocionesEd = dialogTiendaEdicion.findViewById(R.id.switchFormularioTiendaPermitePromociones);
    }

    private void popUpFormularioTienda(){
        /*dialogTiendaEdicion = new Dialog(AsociacionTiendaFormulario.this);
        dialogTiendaEdicion.setContentView(R.layout.pop_up_asociacion_edicion_tienda);
        codigoEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaCodigo);
        nombreComercialEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaNombreComercial);
        nombreFiscalEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaNombreFiscal);
        nifEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaNif);
        EditText emailEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaNombreFiscal);
        EditText telefonoEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaTelefono);
        direccionEd = dialogTiendaEdicion.findViewById(R.id.editTextFormularioTiendaDireccion);
        permitePromocionesEd = dialogTiendaEdicion.findViewById(R.id.switchFormularioTiendaPermitePromociones);*/

        FloatingActionButton botonGuardarPopUp = dialogTiendaEdicion.findViewById(R.id.idButtonGuardarCambiosTienda);
        FloatingActionButton botonCancelarPopUo = dialogTiendaEdicion.findViewById(R.id.idCanelarCambiosTienda);

        /*codigoEd.setText(codigo.getText());
        nombreComercialEd.setText(nombreComercial.getText());
        nombreFiscalEd.setText(nombreFiscal.getText());
        nifEd.setText(nif.getText());

        direccionEd.setText(direccion.getText());*/

        dialogTiendaEdicion.show();

        botonGuardarPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tienda.setAsociacion_id(USUARIO_ACTUAL.getParametrosUsuarioActual().getAcceso_asociacion_id());
                tienda.setCodigo(codigoEd.getText().toString());
                tienda.setNombrePublico(nombreComercialEd.getText().toString());
                tienda.setNombreFiscal(nombreFiscalEd.getText().toString());
                tienda.setNif(nifEd.getText().toString());
                //tienda. //introducir EMAIL
                //tienda. //introducir TELEFONO
                tienda.setDireccion(direccionEd.getText().toString());
                tienda.setPermitePromociones(permitePromocionesEd.isChecked());

                codigo.setText(tienda.getCodigo());
                nombreComercial.setText(tienda.getNombrePublico());
                nombreFiscal.setText(tienda.getNombreFiscal());
                nif.setText(tienda.getNif());
                direccion.setText(tienda.getDireccion());

                dialogTiendaEdicion.dismiss();
            }
        });

        botonCancelarPopUo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTiendaEdicion.dismiss();
            }
        });
    }

}
