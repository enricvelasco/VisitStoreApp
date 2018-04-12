package com.visitapp.visitstoreapp.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.visitapp.visitstoreapp.sistema.domain.usuarios.Usuario;
import com.visitapp.visitstoreapp.sistema.domain.usuarios.UsuarioNiveles;
import com.visitapp.visitstoreapp.sistema.domain.usuarios.UsuarioParametros;
import com.visitapp.visitstoreapp.VariablesGlobales;
import com.visitapp.visitstoreapp.menuPrincipalGenerico.MenuPrincipalGenerico;
import com.visitapp.visitstoreapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PantallaLogIn extends Activity implements
        View.OnClickListener{
    //Usuario
    UsuarioParametros usuarioParametros = new UsuarioParametros();

    EditText inputEmail;
    EditText inputPassword;
    Button buttonLogIn;
    TextView createNewUser;
    Button btn;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mail_password);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        createNewUser = findViewById(R.id.linkCreateNewUser);
        inputEmail = findViewById(R.id.emailLogIn);
        inputPassword = findViewById(R.id.passwordLogIn);
        buttonLogIn = findViewById(R.id.buttonSignIn);
        buttonLogIn.setEnabled(false);
        //detectar si hay email y password para activar el boton de login

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        inputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activarDesactivarBoton(inputPassword.getText().toString(), inputEmail.getText().toString(), buttonLogIn);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        inputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activarDesactivarBoton(inputPassword.getText().toString(), inputEmail.getText().toString(), buttonLogIn);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //BUTTONS
        //findViewById(R.id.buttonLogIn).setOnClickListener(this);
        findViewById(R.id.linkCreateNewUser).setOnClickListener(this);

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("HACE EL CLICK");
                signIn(inputEmail.getText().toString(), inputPassword.getText().toString());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        System.out.println("ENTRA ON START");
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void activarDesactivarBoton(String email, String password, Button buttonLogIn) {
        if(email.matches("") || password.matches("")){
            buttonLogIn.setEnabled(false);
        }else{
            buttonLogIn.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        System.out.println("ON CLICK");
        int i = v.getId();
        if(i == R.id.buttonLogIn){
            //hacer la peticion para loguearse
            System.out.println("BUSCA HACER EL LOGIN");
            signIn(inputEmail.getText().toString(), inputPassword.getText().toString());
        }else if(i == R.id.linkCreateNewUser){
            //crear nuevo usuario
            System.out.println("HABRIA QUE CREAR NUEVO USUARIO");
        }
    }

    private void signIn(String email, String password) {
        /*Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }*/

        //showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            System.out.println("SUCCESS LOGIN");
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {

                            System.out.println("ERROR LOGIN");
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            /*Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();*/
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            //mStatusTextView.setText(R.string.auth_failed);
                        }
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void updateUI(FirebaseUser user) {
//        System.out.println("COMPRUEBA EL USUARIO"+ user.getEmail());
        if(user != null){
            System.out.println("COMPRUEBA EL USUARIO"+ user.getUid());
            System.out.println("CAMBIARA A PANTALLA DE MENU PRINCIPAL");



            //montar usuario
            usuarioParametros.set_id(user.getUid());
            usuarioParametros.setEmail(user.getEmail());
            usuarioParametros.setNombre("Asociacion 1");

            //localizar el nivel al que corresponde
            usuarioParametros.setNivel_id(localizarNivelUsuario(usuarioParametros));

            ((VariablesGlobales) this.getApplication()).setUsuarioParametros(usuarioParametros);

            //cargarNivelesUsuarioDemo(usuarioParametros);

            Intent i = new Intent(getApplicationContext(), MenuPrincipalGenerico.class);
            startActivity(i);
        }else{
            System.out.println("QUEDARSE EN PANTALLA LOGIN");
        }
        /*hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);

            findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
        }*/
    }

    private String localizarNivelUsuario(final UsuarioParametros usuarioParametros) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("usuariosNiveles");

        //final UsuarioNiveles usuarioNiveles = new UsuarioNiveles();
        final UUID[] idNivel = {null};
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //System.out.println("DATOS RECIBIDOS"+dataSnapshot.getValue());
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    //System.out.println("LOOP "+data.getValue());
                    Iterable<DataSnapshot> usuariosCorrespondientes = data.child("usuarios").getChildren();
                    for (DataSnapshot user: usuariosCorrespondientes) {
                        if(user.child("_id").getValue().equals(usuarioParametros.get_id())){
                            System.out.println("¡¡USUARIO ENCONTRADO!! "+usuarioParametros.getNombre());
                            usuarioParametros.setNivel_id(data.child("_id").getValue().toString());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return usuarioParametros.getNivel_id();
    }

    private void cargarNivelesUsuarioDemo(UsuarioParametros usuarioParametros) {
        List<UsuarioNiveles> usuarioNiveles = new ArrayList<>();
        UsuarioNiveles un1 = new UsuarioNiveles();
        un1.setNombre("Asociación");
        un1.setObservaciones("control sobre todas sus tiendas");
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuarioParametros);
        un1.setUsuarios(usuarios);
        usuarioNiveles.add(un1);
        UsuarioNiveles un2 = new UsuarioNiveles();
        un2.setNombre("Tienda");
        un2.setObservaciones("solo tiene el control de los productos de la propia tienda");
        usuarioNiveles.add(un2);
        UsuarioNiveles un3 = new UsuarioNiveles();
        un3.setNombre("Usuario registrado");
        un3.setObservaciones("puede visualizar los productos, guardar favs y ver descripciones");
        usuarioNiveles.add(un3);
        UsuarioNiveles un4 = new UsuarioNiveles();
        un4.setNombre("Usuario sin registrar");
        un4.setObservaciones("solo puede visualizar");
        usuarioNiveles.add(un4);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("usuariosNiveles");
        myRef.setValue(usuarioNiveles);
    }

}
