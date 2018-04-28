package com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.visitapp.visitstoreapp.R;
import com.visitapp.visitstoreapp.VariablesGlobales;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.adapter.spinner.ItemSpinnerNoPicture;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.AsociacionMenuPrincipal;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.adapter.ItemProductoListado;
import com.visitapp.visitstoreapp.sistema.controllers.productos.ProductoController;
import com.visitapp.visitstoreapp.sistema.controllers.productos.ProductoTipoController;
import com.visitapp.visitstoreapp.sistema.controllers.tiendas.TiendaController;
import com.visitapp.visitstoreapp.sistema.domain.productos.Producto;
import com.visitapp.visitstoreapp.sistema.domain.productos.ProductoTipo;
import com.visitapp.visitstoreapp.sistema.domain.tiendas.Tienda;
import com.visitapp.visitstoreapp.sistema.domain.usuarios.UsuarioParametros;
import com.visitapp.visitstoreapp.sistema.interfaces.OnGetDataListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.visitapp.visitstoreapp.login.PantallaLogIn.USUARIO_ACTUAL;

public class AsociacionProductoFormulario extends AppCompatActivity {
    private EditText codigo;
    private EditText nombre;
    private EditText descripcion;
    private EditText precio;

    private TextView seleccionTipoProducto;
    private TextView seleccionTienda;

    private Spinner selectTienda;
    private Spinner selectProductoTipo;

    FloatingActionButton botonGuardar;
    FloatingActionButton botonCancelar;
    FloatingActionButton botonGetBarCode;
    FloatingActionButton botonGetQRCode;
    FloatingActionButton botonFoto;
    FloatingActionButton botonGaleria;

    //campos combo
    String valorComboPTienda = "";
    String valorComboProductoTipo = "";

    //image
    ImageView imagenProducto;

    //rutas
    private StorageReference mStorageRef;
    private static final int CAMERA_REQUEST_CODE = 1;

    ContentValues values;
    Uri imageUri;
    Bitmap thumbnail = null;
    String imageurl;

    String estadoFormulario;

    ProductoTipoController productoTipoController = new ProductoTipoController();
    ProductoController productoController = new ProductoController();

    List<Producto> productoList;

    GridView gridProductosPopUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((VariablesGlobales) getApplication()).setMenuActual("formularioProductos");

        mStorageRef = FirebaseStorage.getInstance().getReference();

        setContentView(R.layout.activity_asociacion_producto_formulario);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final UsuarioParametros usuarioParametros = USUARIO_ACTUAL.getParametrosUsuarioActual();

        declaroCampos();

        Intent myIntent = getIntent(); // gets the previously created intent
        final String idEdicion = myIntent.getStringExtra("producto_id");

        if(idEdicion == null){
            System.out.println("ESTADO NUEVO");
            estadoFormulario = "nuevo";
        }else{
            System.out.println("ID PRODUCTO A EDITAR "+idEdicion);
            System.out.println("ESTADO EDICION");
            estadoFormulario = "edicion";

            productoController.read(idEdicion, new OnGetDataListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(DataSnapshot data) {
                    Producto productoEdit = data.getValue(Producto.class);
                    codigo.setText(productoEdit.getCodigo());
                    //codigo = findViewById(R.id.idCodigoProductoFormulario);
                    nombre.setText(productoEdit.getNombre());
                    precio.setText(productoEdit.getPrecio());
                    descripcion.setText(productoEdit.getDescripcion());

                    Picasso.with(getApplicationContext()).load(productoEdit.getImagen()).resize(768,768).centerCrop().into(imagenProducto);
                    //selectTienda.setPos
                    /*selectTienda.set
                    selectProductoTipo
                    imagenProducto*/
                }

                @Override
                public void onFailed(DatabaseError databaseError) {

                }
            });
        }




        //asignar nombres de las tiendas a mostrar en el select
        TiendaController tiendaController = new TiendaController();
        tiendaController.queryEquals("asociacion_id", USUARIO_ACTUAL.getParametrosUsuarioActual().getAcceso_asociacion_id(), new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                final List<Map> listadoDeTiendas = new ArrayList<>();

                for(DataSnapshot element:data.getChildren()){
                    Map<String,String> object = new HashMap();
                    Tienda tienda = element.getValue(Tienda.class);
                    System.out.println("ASIGNA TIENDA"+tienda.getNombrePublico());
                    object.put("codigo", tienda.getCodigo());
                    object.put("nombre", tienda.getNombrePublico());
                    object.put("id", tienda.get_id());
                    listadoDeTiendas.add(object);
                }
                System.out.println("EL LISTADO DE LAS TIENDAS:"+listadoDeTiendas);
                ItemSpinnerNoPicture itemTiendaListado = new ItemSpinnerNoPicture(listadoDeTiendas, getBaseContext());
                /*selectTienda.setAdapter(itemTiendaListado);

                selectTienda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(estadoFormulario.equals("edicion")){
                            System.out.println("ENTRA EN ITEM SELECTED!!!!"+position);
                            //valorComboPTienda = listadoDeTiendas.stream().filter(item -> item.get))

                            for(Map map : listadoDeTiendas){
                                if(map.get("id").equals(idEdicion)){
                                    //valorComboPTienda

                                }
                            }

                        }else if(estadoFormulario.equals("nuevo")){
                            valorComboPTienda = listadoDeTiendas.get(position).get("id").toString();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });*/
            }
            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });


        productoTipoController.getList(new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                final List<Map> productoTipoList = new ArrayList<>();

                for(DataSnapshot element:data.getChildren()){
                    Map<String,String> object = new HashMap();
                    ProductoTipo productoTipo = element.getValue(ProductoTipo.class);
                    object.put("codigo", productoTipo.getCodigo());
                    object.put("nombre", productoTipo.getDescripcion());
                    object.put("id", productoTipo.get_id());
                    productoTipoList.add(object);
                }
                ItemSpinnerNoPicture itemTiendaListado = new ItemSpinnerNoPicture(productoTipoList, getBaseContext());
                /*selectProductoTipo.setAdapter(itemTiendaListado);
                selectProductoTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        valorComboProductoTipo = productoTipoList.get(position).get("id").toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });

        botonFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });


        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICK EN BOTON GUARDAR");
                Producto producto = new Producto();
                producto.setCodigo(codigo.getText().toString());
                producto.setNombre(nombre.getText().toString());
                producto.setPrecio(precio.getText().toString());
                producto.setDescripcion(descripcion.getText().toString());
                producto.setAsociacion_id(USUARIO_ACTUAL.getParametrosUsuarioActual().getAcceso_asociacion_id());

                //prueba
                producto.setTienda_id(valorComboPTienda);
                producto.setProductosTipo_id(valorComboProductoTipo);

                if(TextUtils.isEmpty(codigo.getText()) || TextUtils.isEmpty(nombre.getText()) || thumbnail == null){
                    Toast.makeText(getApplicationContext(), "Faltan campos obligatorios", Toast.LENGTH_SHORT).show();
                }else{
                    //Envia el producto y lo guarda junto la subida de la imagen
                    subirImagenStorage(producto);
                }



            }
        });

        seleccionTipoProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICK EN EL TIPO DE PROUCTO");
            }
        });

        seleccionTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICK ON TIENDA");
            }
        });

        //DETECTAMOS LOS CAMPOS QUE HAY QUE RELLENAR OBLIGATORIOS
        /*codigo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activarDesactivarBoton(codigo.getText().toString(), nombre.getText().toString(), botonGuardar);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activarDesactivarBoton(codigo.getText().toString(), nombre.getText().toString(), botonGuardar);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void declaroCampos() {
        //campos formulario
        codigo = findViewById(R.id.idCodigoProductoFormulario);
        //codigo = findViewById(R.id.idCodigoProductoFormulario);
        nombre = findViewById(R.id.idNombreProductoFormulario);
        precio = findViewById(R.id.idPrecioProductoFormulario);
        descripcion = findViewById(R.id.idDescripcionProductoFormulario);
        /*selectTienda = findViewById(R.id.idTiendaSelect);
        selectProductoTipo = findViewById(R.id.idProductoTipoSelect);*/
        imagenProducto = findViewById(R.id.idImagenProducto);

        seleccionTipoProducto = findViewById(R.id.idSeleccionTipoProducto);
        seleccionTienda = findViewById(R.id.idSeleccionTienda);

        //botones formulario
        /*botonGetBarCode = findViewById(R.id.idFloatigButtonGetBarCodeProducto);
        botonGetBarCode = findViewById(R.id.idFloatingButtonGetQRCodeProducto);*/
        botonGuardar = findViewById(R.id.idFloatingButtonGuardarProducto);
        botonFoto = findViewById(R.id.idFloattingButtonTakePictureProducto);
        botonGaleria = findViewById(R.id.idFloatingButtonGetFromGalleryPictureProduct);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case CAMERA_REQUEST_CODE:
                if (requestCode == CAMERA_REQUEST_CODE)
                    if (resultCode == Activity.RESULT_OK) {
                        try {
                            thumbnail = MediaStore.Images.Media.getBitmap(
                                    getContentResolver(), imageUri);
                            imageurl = getRealPathFromURI(imageUri);

                            Matrix matrix = new Matrix();
                            matrix.postRotate(90);
                            thumbnail = Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(), thumbnail.getHeight(), matrix, true);

                            //imagenProducto.setImageBitmap(thumbnail);
                            Picasso.with(getApplicationContext()).load(imageUri).rotate(90f).resize(768,768).centerCrop().into(imagenProducto);
                            //imagenProducto.setImageURI(imageUri);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
        }
    }

    private void subirImagenStorage(final Producto producto){

        final StorageReference filepath = mStorageRef.child("productos").child(producto.get_id()).child(producto.get_id());
        Uri uri = getImageUri(getApplicationContext(), thumbnail);

        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        producto.setImagen(uri.toString());
                        /*ProductoController productoController = new ProductoController();
                        productoController.update(producto);*/

                        //ProductoController productoController = new ProductoController();
                        productoController.save(producto);

                        Toast.makeText(getApplicationContext(), "Guardado Finalizado", Toast.LENGTH_SHORT).show();

                        //Cambiar a MENU PRINCIPAL
                        Intent i = new Intent(getApplicationContext(), AsociacionMenuPrincipal.class);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        System.out.println("ERROR DE: "+producto.getNombre());
                        System.out.println("ERROR URL: "+producto.getImagen());
                        Toast.makeText(getApplicationContext(), "Error al guardar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("ERROR SUBIR IMAGEN");
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void activarDesactivarBoton(String codigo, String nombre, FloatingActionButton buttonLogIn) {
        if(codigo.matches("") || nombre.matches("")){
            buttonLogIn.setEnabled(false);
        }else{
            buttonLogIn.setEnabled(true);
        }
    }

    private void cargarListadoProductos(ProductoController productoController, UsuarioParametros usuarioParametros) {
        productoController.queryEquals("asociacion_id", usuarioParametros.getAcceso_asociacion_id(), new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                productoList = new ArrayList<>();
                for(DataSnapshot element : data.getChildren()){
                    Producto producto = element.getValue(Producto.class);
                    productoList.add(producto);
                }
                ItemProductoListado item = new ItemProductoListado(productoList, getApplicationContext());
                gridProductosPopUp.setAdapter(item);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

}
