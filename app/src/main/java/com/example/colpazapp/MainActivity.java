package com.example.colpazapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.colpazapp.activitys.HomeActivity;
import com.example.colpazapp.activitys.RegisterActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{

    // Buttons
    Button btnRegister, btnLogin;
    //EditTexts
    EditText edPassword, edEmail;
    //ImageButtons
    ImageButton imgButtonPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegister       = findViewById(R.id.buttonRegister);
        btnLogin          = findViewById(R.id.buttonLogin);
        edPassword        = findViewById(R.id.edPassword);
        edEmail           = findViewById(R.id.edEmail);
        imgButtonPassword = findViewById(R.id.viewPassword);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                onClickRegister();
            }
        });

        imgButtonPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onClickTogglePassword();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                onClickLogin();
            }
        });
    }
    //Funcion para manejar el cambio a la actividad "RegisterActivity"
    private void onClickRegister()
    {
        // Cambiar de actividad
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
    //Funcion para ocultar y visualizar la clave
    private void onClickTogglePassword() {
        if (edPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            edPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            edPassword.setTypeface(Typeface.DEFAULT); // Restaurar el tipo de fuente predeterminado
            imgButtonPassword.setImageResource(android.R.drawable.ic_menu_view);
        } else {
            edPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            edPassword.setTypeface(Typeface.DEFAULT); // Restaurar el tipo de fuente predeterminado
            imgButtonPassword.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
        }
    }
    //Funcion para iniciar sesion
    private void onClickLogin()
    {
        // Definir una lista de campos y sus mensajes de error asociados
        Map<EditText, String> campos = new HashMap<>();
        campos.put(edEmail, "Correo");
        campos.put(edPassword, "Clave");

        // Variable para almacenar el primer campo vacio encontrado
        EditText campoVacio = null;

        // Iterar sobre la lista de campos
        for (Map.Entry<EditText, String> entry : campos.entrySet()) {
            EditText campo = entry.getKey();
            String nombreCampo = entry.getValue();
            String valor = campo.getText().toString();

            // Verificar si el campo esta vacio
            if (TextUtils.isEmpty(valor)) {
                campo.setError("Campo de " + nombreCampo + " vacio");
                // Si es el primer campo vacio encontrado, almacenarlo
                if (campoVacio == null) {
                    campoVacio = campo;
                }
            }else{
                // Verificar el formato del correo electronico
                if (campo == edEmail && !android.util.Patterns.EMAIL_ADDRESS.matcher(valor).matches()) {
                    campo.setError("Formato de correo electronico invalido");
                    if (campoVacio == null) {
                        campoVacio = campo;
                    }
                }

                // Verificar la longitud mínima de la clave
                if (campo == edPassword && valor.length() < 5) {
                    campo.setError("La clave debe tener al menos 5 caracteres");
                    if (campoVacio == null) {
                        campoVacio = campo;
                    }
                }
            }
        }

        // Si se encontro algun campo vacío, enfocar en ese campo y mostrar un mensaje
        if (campoVacio != null) {
            campoVacio.requestFocus();
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
        } else {
            // Cambiar de actividad
            String email = edEmail.getText().toString();

            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        }
    }
}