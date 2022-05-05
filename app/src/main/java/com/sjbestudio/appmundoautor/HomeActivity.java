package com.sjbestudio.appmundoautor;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeActivity  extends AppCompatActivity {
    private Spinner categorias;
    Button boton;
    Spinner products;
    Spinner spinner;
    ArrayList<String> arrayListCategorias;
    ArrayAdapter<String>adapterCategorias, adapterSubCategorias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        spinner = (Spinner) findViewById(R.id.spinner);

        init();


        boton= (Button) findViewById(R.id.button2);
        boton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                ClassConnection connection = new ClassConnection();

                try {
                    String url = "https://automundotulcan.000webhostapp.com/api/getCategorias.php";
                    String response = connection.execute(url).get();



                    ObjectMapper obj = new ObjectMapper();
                    CategoriasRequest categoriasRequest = obj.readValue(response, new TypeReference<CategoriasRequest>() {
                    });
                    if (categoriasRequest.error) {
                        Toast.makeText(getApplicationContext(), "Datos Incorrectos", Toast.LENGTH_SHORT).show();
                    } else {
                        List<String> nombreCategoria = categoriasRequest.data.stream().map(item -> item.name).collect(Collectors.toList());
                        for(String n : nombreCategoria){

                            System.out.println("Nombre: " + n);
                        }
                        System.out.println("todos: ");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e);
                }
            }
        });
        queue.add(stringRequest);

    }
}