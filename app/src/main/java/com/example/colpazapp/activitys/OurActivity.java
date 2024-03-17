package com.example.colpazapp.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.colpazapp.MainActivity;
import com.example.colpazapp.R;
import com.squareup.picasso.Picasso;

public class OurActivity extends AppCompatActivity {

    // Images View
    ImageView imgVPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our);

        imgVPhoto = findViewById(R.id.imgVPhoto);

        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/imagen-personal-79825.appspot.com/o/Foto.png?alt=media&token=90e04e14-80f0-4e2f-aa55-348e3c86c394").
                placeholder(R.drawable.cargando).
                error(R.drawable.error).
                into(imgVPhoto);
    }

    // Funcion para inicializar el menu del menu_home.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        menu.findItem(R.id.itemNosotros).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }
    // Funcion para permitir los eventos entre los items del menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idItem = item.getItemId();

        if (idItem == R.id.itemCerrarSesion){
            Intent intent = new Intent(OurActivity.this, MainActivity.class);
            startActivity(intent);
        }else if (idItem == R.id.itemCompartir){
            compartirContenido();
        }else if (idItem == R.id.itemHome) {
            // Obtener el email y la contraseña de las intenciones
            Intent intent = getIntent();
            String email = intent.getStringExtra("email");

            // Crear un nuevo intent para devolver el email a HomeActivity
            Intent intentResult = new Intent();
            intentResult.putExtra("email", email);

            // Establecer el resultado como RESULT_OK y pasar el intent de vuelta
            setResult(RESULT_OK, intentResult);

            // Finalizar esta actividad
            finish();
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
}