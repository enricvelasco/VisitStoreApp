package com.visitapp.visitstoreapp.menuPrincipalGenerico.asociacion.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.visitapp.visitstoreapp.menuPrincipalGenerico.adapter.spinner.ItemSpinnerNoPicture;
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
    FloatingActionButton botonFoto;
    FloatingActionButton botonGaleria;

    //campos combo
    String valorComboPTienda = "";
    String valorComboProductoTipo = "";

    //image
    ImageView imagenProducto;

    //rutas
    private StorageReference mStorageRef;
    StorageReference urlPictureProduct;

    private static final int CAMERA_REQUEST_CODE = 1;
    Bitmap bitImagenProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStorageRef = FirebaseStorage.getInstance().getReference();

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
        imagenProducto = findViewById(R.id.idImagenProducto);

        //botones formulario
        botonGetBarCode = findViewById(R.id.idFloatigButtonGetBarCodeProducto);
        botonGetBarCode = findViewById(R.id.idFloatingButtonGetQRCodeProducto);
        botonGuardar = findViewById(R.id.idFloatingButtonGuardarProducto);
        botonFoto = findViewById(R.id.idFloattingButtonTakePictureProducto);
        botonGaleria = findViewById(R.id.idFloatingButtonGetFromGalleryPictureProduct);
        //botonCancelar = findViewById(R.id.idFloatingButtonCancelarProducto);




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
                selectTienda.setAdapter(itemTiendaListado);
                selectTienda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        valorComboPTienda = listadoDeTiendas.get(position).get("id").toString();
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
                selectProductoTipo.setAdapter(itemTiendaListado);
                selectProductoTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        valorComboProductoTipo = productoTipoList.get(position).get("id").toString();
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

        botonFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
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
                producto.setTienda_id(valorComboPTienda);
                producto.setProductosTipo_id(valorComboProductoTipo);

                //imagen

                ProductoController productoController = new ProductoController();
                productoController.save(producto);

                subirImagenStorage(producto);

            }
        });
        /*botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*System.out.println("ACTIVITY RESULT");
        System.out.println("REQUEST CODE " + requestCode);
        System.out.println("RESULT CODE " + resultCode);
        System.out.println("DATA " + data.getExtras());
        System.out.println("STORAGE REF " + mStorageRef);*/


        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            System.out.println("ENTRA............................s");
            bitImagenProducto = (Bitmap) data.getExtras().get("data");
            imagenProducto.setImageBitmap(bitImagenProducto);
            /*Uri uri = getImageUri(this, bitmap);

            StorageReference filepath = mStorageRef.child("photos").child("producto").child("prueba").child(uri.getLastPathSegment());

            System.out.println("LA URL DE LA FOTO ES:"+filepath.getDownloadUrl());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(getApplicationContext(), "Carga finalizada...", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("ERROR SUBIR IMAGEN");
                }
            });*/
        }
    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void subirImagenStorage(final Producto producto){
        Uri uri = getImageUri(this, bitImagenProducto);

        final StorageReference filepath = mStorageRef.child("photos").child("productos").child(producto.get_id()).child(producto.get_id());
        //urlPictureProduct = String.valueOf(filepath.getDownloadUrl());
        System.out.println("LA URL DE LA FOTO ES:"+filepath);
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //urlPictureProduct = filepath.getDownloadUrl();
                //producto.setImagen(filepath.getPath());
                //ProductoController productoController = new ProductoController();

                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        producto.setImagen(uri.toString());
                        ProductoController productoController = new ProductoController();
                        productoController.update(producto);
                        Toast.makeText(getApplicationContext(), "Carga finalizada...", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        System.out.println("ERROR DE: "+producto.getNombre());
                        System.out.println("ERROR URL: "+producto.getImagen());
                        Toast.makeText(getApplicationContext(), "Error al cargaar item imagen", Toast.LENGTH_SHORT).show();
                    }
                });


                //productoController.update(producto);
                //Toast.makeText(getApplicationContext(), "Carga finalizada...", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("ERROR SUBIR IMAGEN");
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
        //return String.valueOf(filepath.getDownloadUrl());
        // /photos/productos/06d35521-0490-47ad-89a6-a9feb1330e09/06d35521-0490-47ad-89a6-a9feb1330e09
        //"/photos/productos/10678594-60bf-4fb1-bf75-4562f9705248/10678594-60bf-4fb1-bf75-4562f9705248"
        //"/photos/productos/06d35521-0490-47ad-89a6-a9feb1330e09/06d35521-0490-47ad-89a6-a9feb1330e09"
        //"gs://visitstoreapp.appspot.com/photos/productos/1e13af45-70a2-44ac-91c5-6b1f099c9395/1e13af45-70a2-44ac-91c5-6b1f099c9395"
        //https://firebasestorage.googleapis.com/v0/b/visitstoreapp.appspot.com/o/photos%2Fproductos%2F1e13af45-70a2-44ac-91c5-6b1f099c9395%2F1e13af45-70a2-44ac-91c5-6b1f099c9395?alt=media&token=b5941c33-ece4-4c8a-80e9-184f3f5f1f77
    }

}
