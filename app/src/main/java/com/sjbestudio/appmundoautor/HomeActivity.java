package com.sjbestudio.appmundoautor;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Header;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeActivity  extends AppCompatActivity {

    private RecyclerView mRvListaProductos;

    private HeaderAdapter headerAdapter;
    private Spinner categorias;
    private Spinner subcategoria;
    private RecyclerView productos;
    RecyclerView recyclerView;
    Spinner spinner;
    Spinner spinner2;
    ArrayList<String> arrayListCategorias;
    ArrayList<String> arrayListSubCategorias;
    ArrayList<String> arrayLisProductos;
    ArrayAdapter<String>adapterCategorias, adapterSubCategorias,adapterProductos;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);


        String[] datos = getCategorias();
        ArrayAdapter<String> adapters= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
        spinner.setAdapter(adapters);
        mRvListaProductos = findViewById(R.id.reciclerView);

        mRvListaProductos.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvListaProductos.setLayoutManager(linearLayoutManager);



        ArrayList<Header> myDataset= new ArrayList<>();
        myDataset.add(new Header("juan", "1"));
        myDataset.add(new Header("oma", "1"));
        myDataset.add(new Header("oscar", "1"));

        headerAdapter = new HeaderAdapter(myDataset);
//        recyclerView.setAdapter(headerAdapter);


        Spinner spnLocale = (Spinner)findViewById(R.id.spinner);
        spnLocale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //ArrayAdapter<String> adapters = (ArrayAdapter<String>) spinner.getAdapter();
                getSubCategorias(datos[i]);
                } public void onNothingSelected(AdapterView<?> adapterView) { return; } });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String[] getCategorias(){
        ClassConnection connection = new ClassConnection();
        String[] datos = new String[0];
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
                datos = new String[nombreCategoria.size()];
                for (int i = 0; i < nombreCategoria.size(); i++) {
                    datos[i] = nombreCategoria.get(i);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return datos;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String[] getSubCategorias(String name){
        ClassConnection connection = new ClassConnection();
        String[] dato = new String[0];
        try {
            String url = "https://automundotulcan.000webhostapp.com/api/getSubCategorias.php?name="+name+"";
            String response = connection.execute(url).get();


            ObjectMapper obj = new ObjectMapper();
            CategoriasRequest  subcategoriasRequest = obj.readValue(response, new TypeReference<CategoriasRequest>() {
            });

            if (subcategoriasRequest.error) {
                Toast.makeText(getApplicationContext(), "Datos no encontrados", Toast.LENGTH_SHORT).show();
            } else {
                List<String> nombreSubCategoria = subcategoriasRequest.data.stream().map(item -> item.name).collect(Collectors.toList());
                dato = new String[nombreSubCategoria.size()];
                for (int i = 0; i < nombreSubCategoria.size(); i++) {
                    dato[i] = nombreSubCategoria.get(i);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        ArrayAdapter<String> adapters= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dato);
        spinner2.setAdapter(adapters);
        return dato;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String[] getProductos(String name){
        ClassConnection connection = new ClassConnection();
        String[] produ = new String[0];
        try {
            String url = "https://automundotulcan.000webhostapp.com/api/getProducts.php?name="+name+"";
            String response = connection.execute(url).get();


            ObjectMapper obj = new ObjectMapper();
            ProductoRequest productoRequest = obj.readValue(response, new TypeReference<ProductoRequest>() {
            });



            if (productoRequest.error) {
                Toast.makeText(getApplicationContext(), "Datos no encontrados", Toast.LENGTH_SHORT).show();
            } else {
                List<String> nombreProducto = productoRequest.data.stream().map(item -> item.name).collect(Collectors.toList());
                produ = new String[nombreProducto.size()];
                for (int i = 0; i < nombreProducto.size(); i++) {
                    produ[i] = nombreProducto.get(i);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        ArrayAdapter<String> adapters= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, produ);
        spinner2.setAdapter(adapters);
        return produ;
    }


}
