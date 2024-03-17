package com.example.colpazapp.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.colpazapp.MainActivity;
import com.squareup.picasso.Picasso;

import com.example.colpazapp.R;

public class HomeActivity extends AppCompatActivity {

    final int CONS_ACTIVITY_B = 1;
    // Images View
    ImageView imgVLogoUNISANGIL;
    //TextView
    TextView txtViewEmail;
    // Buttons
    Button buttonInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Obtener el correo electronico del intent
        String email = getIntent().getStringExtra("email");

        buttonInscription = findViewById(R.id.buttonInscription);
        txtViewEmail      = findViewById(R.id.txtViewEmail);

        txtViewEmail.setText(email);

        imgVLogoUNISANGIL = findViewById(R.id.imgVLogoUNISANGIL);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/primer-proyecto-b27bf.appspot.com/o/descarga.png?alt=media&token=25c46eb9-55a2-4c48-a31e-31198e8874d2").
                placeholder(R.drawable.cargando).
                error(R.drawable.error).
                into(imgVLogoUNISANGIL);

        buttonInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onClickRegisterInscription();
            }
        });
    }
    // Funcion para inicializar el menu del menu_home.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        menu.findItem(R.id.itemHome).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }
    // Funcion para permitir los eventos entre los items del menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idItem = item.getItemId();

        if (idItem == R.id.itemCerrarSesion){
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        }else if (idItem == R.id.itemCompartir){
            compartirContenido();
        }else if (idItem == R.id.itemNosotros) {
            // Obtener el correo electronico del TextView
            String email = txtViewEmail.getText().toString();

            // Iniciar OurActivity y pasar el correo electrónico como un extra
            Intent intent = new Intent(HomeActivity.this, OurActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    // Boton de compartir
    private void compartirContenido() {
        // Crear un Intent para compartir contenido
        Intent compartirIntent = new Intent(Intent.ACTION_SEND);
        compartirIntent.setType("text/plain");
        compartirIntent.putExtra(Intent.EXTRA_TEXT, "¡Hola! Estoy compartiendo contenido desde mi app.");

        // Mostrar el selector de aplicaciones para compartir
        startActivity(Intent.createChooser(compartirIntent, "Compartir contenido"));
    }

    //Obtener correo que se mantiene entre ventanas
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CONS_ACTIVITY_B){
            if(resultCode == RESULT_OK && data != null){
                String email = data.getStringExtra("email");
                txtViewEmail.setText(email);
            }
        }
    }

    // Boton de inscripcion
    private void onClickRegisterInscription()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
        alert.setTitle("Confirmar").setMessage("Inscripcion exitosa a UNISANGIL").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MensajeToast("Inscripcion Finalizada, pronto se contactaran contigo");
                    }
                })
                .setNegativeButton("Cancelar", null)
                .setCancelable(false)
                .setIcon(R.drawable.logocolpaz)
                .show();
    }

    private void MensajeToast(String mensaje){
        Toast.makeText(this,mensaje, Toast.LENGTH_SHORT).show();
    }
}