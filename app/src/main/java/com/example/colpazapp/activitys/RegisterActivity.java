package com.example.colpazapp.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.colpazapp.MainActivity;
import com.example.colpazapp.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    //Buttons
    Button buttonConfirmRegister;
    //EditTexts
    EditText edRegisterPassword, edRegisterEmail, edRegisterAddress, edRegisterPhone, edRegisterName, edRegisterBirthdate;
    //ImageButtons
    ImageButton imgButtonRegisterPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imgButtonRegisterPassword = findViewById(R.id.viewRegisterPassword);
        buttonConfirmRegister     = findViewById(R.id.buttonConfirmRegister);
        //Campos de registro
        edRegisterPassword        = findViewById(R.id.edRegisterPassword);
        edRegisterAddress         = findViewById(R.id.edRegisterAddress);
        edRegisterEmail           = findViewById(R.id.edRegisterEmail);
        edRegisterBirthdate       = findViewById(R.id.edRegisterBirthdate);
        edRegisterName            = findViewById(R.id.edRegisterName);
        edRegisterPhone           = findViewById(R.id.edRegisterPhone);

        imgButtonRegisterPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                onClickRegisterTogglePassword();
            }
        });

        buttonConfirmRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                onClickRegisterUser();
            }
        });

        edRegisterBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showDatePickerDialog();
            }
        });
    }

    //Funcion para ocultar y visualizar la clave
    private void onClickRegisterTogglePassword()
    {
        if (edRegisterPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            edRegisterPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            edRegisterPassword.setTypeface(Typeface.DEFAULT); // Restaurar el tipo de fuente predeterminado
            imgButtonRegisterPassword.setImageResource(android.R.drawable.ic_menu_view);
        } else {
            edRegisterPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            edRegisterPassword.setTypeface(Typeface.DEFAULT); // Restaurar el tipo de fuente predeterminado
            imgButtonRegisterPassword.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
        }
    }
    //Funcion para registrar usuario
    private void onClickRegisterUser()
    {
        // Definir una lista de campos y sus mensajes de error asociados
        Map<EditText, String> campos = new HashMap<>();
        campos.put(edRegisterEmail, "Correo");
        campos.put(edRegisterPassword, "Clave");
        campos.put(edRegisterAddress, "Direccion");
        campos.put(edRegisterPhone, "Teléefono");
        campos.put(edRegisterBirthdate, "Fecha de nacimiento");
        campos.put(edRegisterName, "Nombre");

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
                if (campo == edRegisterEmail && !android.util.Patterns.EMAIL_ADDRESS.matcher(valor).matches()) {
                    campo.setError("Formato de correo electronico invalido");
                    if (campoVacio == null) {
                        campoVacio = campo;
                    }
                }

                // Verificar la longitud mínima de la clave
                if (campo == edRegisterPassword && valor.length() < 5) {
                    campo.setError("La clave debe tener al menos 5 caracteres");
                    if (campoVacio == null) {
                        campoVacio = campo;
                    }
                }

                // Verificar el formato y longitud del número de telefono
                if (campo == edRegisterPhone) {
                    if (!TextUtils.isDigitsOnly(valor)) {
                        campo.setError("El telefono solo debe contener numeros");
                        if (campoVacio == null) {
                            campoVacio = campo;
                        }
                    } else if (valor.length() != 10) {
                        campo.setError("El telefono debe tener exactamente 10 dígitos");
                        if (campoVacio == null) {
                            campoVacio = campo;
                        }
                    }
                }
            }
        }

        // Si se encontro algun campo vacío, enfocar en ese campo y mostrar un mensaje
        if (campoVacio != null) {
            campoVacio.requestFocus();
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
            alert.setTitle("Confirmar").setMessage("Sus datos son correctos?").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = edRegisterEmail.getText().toString();

                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .setCancelable(false)
                .setIcon(R.drawable.logocolpaz)
                .show();
        }
    }
    //Agregar seleccion de fecha
    private void showDatePickerDialog(){
        Calendar calendario     = Calendar.getInstance();
        int year                = calendario.get(Calendar.YEAR);
        int month               = calendario.get(Calendar.MONTH);
        int dayOfMonth          = calendario.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                edRegisterBirthdate.setText(year+" - "+(month+1)+" - "+dayOfMonth);
                edRegisterBirthdate.setError(null);
            }
        },year, month, dayOfMonth);
        dialog.show();
    }
}