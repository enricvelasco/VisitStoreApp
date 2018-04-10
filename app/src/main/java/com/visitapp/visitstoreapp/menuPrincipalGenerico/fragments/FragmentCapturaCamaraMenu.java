package com.visitapp.visitstoreapp.menuPrincipalGenerico.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.visitapp.visitstoreapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCapturaCamaraMenu extends Fragment {
    private StorageReference mStorageRef;

    private Button buttonTakeUpload;
    private ImageView imagenCaptura;

    private static final int CAMERA_REQUEST_CODE = 1;

    private ProgressDialog mProgress;

    File photoFile = null;
    Uri file;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_captura_camara_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        System.out.println("VISTA CREADA");
        mStorageRef = FirebaseStorage.getInstance().getReference();

        buttonTakeUpload = view.findViewById(R.id.uploadButton);
        imagenCaptura = view.findViewById(R.id.imageView2);

        mProgress = new ProgressDialog(getActivity());

        buttonTakeUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("ACTIVITY RESULT");
        System.out.println("REQUEST CODE "+requestCode);
        System.out.println("RESULT CODE "+resultCode);
        System.out.println("DATA "+data.getExtras());
        System.out.println("STORAGE REF "+mStorageRef);


        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            System.out.println("ENTRA............................s");
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imagenCaptura.setImageBitmap(bitmap);

            Uri uri = getImageUri(getActivity(),bitmap);
            mProgress.setMessage("Cargando imagen...");
            mProgress.show();
/*
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
*/
            //Uri uri = (Uri) data.getExtras().get("data");
            //Uri uri = Uri.fromFile(photoFile);
            //Uri uri = Uri.;
            StorageReference filepath = mStorageRef.child("photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgress.dismiss();
                    Toast.makeText(getActivity(), "Carga finalizada...", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mProgress.dismiss();
                    System.out.println("ERROR SUBIR IMAGEN");
                }
            });
        }


    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private File createImageFile() throws IOException {
        // Create an image mSelectedFile name
        String timeStamp = new Date().toString();
        String imageFileName = "IMG_" + "AAAAA";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File file = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


        return file;
    }
}
