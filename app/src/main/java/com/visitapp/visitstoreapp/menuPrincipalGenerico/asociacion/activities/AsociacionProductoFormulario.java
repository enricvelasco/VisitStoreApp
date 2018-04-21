package com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.activities;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.sistema.controllers.productos.ProductoController;
import com.visitapp.visitstoreapp.sistema.controllers.productos.ProductoTipoController;
import com.visitapp.visitstoreapp.sistema.controllers.tiendas.TiendaController;
import com.visitapp.visitstoreapp.sistema.domain.productos.Producto;
import com.visitapp.visitstoreapp.sistema.domain.productos.ProductoTipo;
import com.visitapp.visitstoreapp.sistema.domain.tiendas.Tienda;
import com.visitapp.visitstoreapp.sistema.domain.usuarios.UsuarioParametros;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

import java.util.ArrayList;
import java.util.List;

import static com.visitapp.visitstoreapp.login.PantallaLogIn.USUARIO_ACTUAL;

public class AsociacionProductoFormulario extends AppCompatActivity {
    private EditText codigo;
    private EditText nombre;
    private EditText descripcion;
    private Spinner selectTienda;
    private Spinner selectProductoTipo;

    FloatingActionButton botonGuardar;
    FloatingActionButton botonCancelar;
    FloatingActionButton botonGetBarCode;
    FloatingActionButton botonGetQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asociacion_producto_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final UsuarioParametros usuarioParametros = USUARIO_ACTUAL.getParametrosUsuarioActual();

        //campos formulario
        codigo = findViewById(R.id.idCodigoProductoFormulario);
        //codigo = findViewById(R.id.idCodigoProductoFormulario);
        nombre = findViewById(R.id.idNombreProductoFormulario);
        descripcion = findViewById(R.id.idDescripcionProductoFormulario);
        selectTienda = findViewById(R.id.idTiendaSelect);
        selectProductoTipo = findViewById(R.id.idProductoTipoSelect);


        //botones formulario
        botonGetBarCode = findViewById(R.id.idFloatigButtonGetBarCodeProducto);
        botonGetBarCode = findViewById(R.id.idFloatingButtonGetQRCodeProducto);
        botonGuardar = findViewById(R.id.idFloatingButtonGuardarProducto);
        //botonCancelar = findViewById(R.id.idFloatingButtonCancelarProducto);

        String valorComboProductoTipo = "";

        //asignar nombres de las tiendas a mostrar en el select
        TiendaController tiendaController = new TiendaController();
        tiendaController.queryEquals("asociacion_id", USUARIO_ACTUAL.getParametrosUsuarioActual().getAcceso_asociacion_id(), new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                ArrayList<String> strings = new ArrayList<>();
                for(DataSnapshot element:data.getChildren()){
                    Tienda tienda = element.getValue(Tienda.class);
                    strings.add(tienda.getNombrePublico());
                }
                selectTienda.setAdapter(new ArrayAdapter<>(AsociacionProductoFormulario.this, android.R.layout.simple_spinner_item, strings));
                selectTienda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        System.out.println("SELECT"+parent.getItemAtPosition(position).toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });

        ProductoTipoController productoTipoController = new ProductoTipoController();
        productoTipoController.getList(new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                ArrayList<String> strings = new ArrayList<>();
                final List<ProductoTipo> productoTipoList = new ArrayList<>();
                for(DataSnapshot element:data.getChildren()){
                    ProductoTipo productoTipo = element.getValue(ProductoTipo.class);
                    strings.add(productoTipo.getDescripcion());
                    productoTipoList.add(productoTipo);
                }
                selectProductoTipo.setAdapter(new ArrayAdapter<>(AsociacionProductoFormulario.this, android.R.layout.simple_spinner_item, strings));
                selectProductoTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        System.out.println("SELECT TIPO PRODUCTO"+parent.getItemAtPosition(position).toString());
                        /*String valorId = "";
                        for(ProductoTipo productoTipo : productoTipoList){
                            if(productoTipo.getDescripcion().equals(parent.getItemAtPosition(position).toString())){
                                valorId.set productoTipo.get_id();
                            }
                        }
                        valorComboProductoTipo*/
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });






        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Producto producto = new Producto();
                producto.setCodigo(codigo.getText().toString());
                producto.setNombre(nombre.getText().toString());
                producto.setDescripcion(descripcion.getText().toString());
                producto.setAsociacion_id(USUARIO_ACTUAL.getParametrosUsuarioActual().getAcceso_asociacion_id());

                //prueba
                producto.setTienda_id("b15f2cb3-0b77-4073-baee-419a5b223204");
                producto.setProductosTipo_id("cb028cec-ccf9-4e51-b5ec-816d447446f2");

                ProductoController productoController = new ProductoController();
                productoController.save(producto);

                //selectProductoTipo.getParent();

                //producto.setTienda_id(selectProductoTipo.getParent());

            }
        });
        /*botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
