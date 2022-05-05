package com.sjbestudio.appmundoautor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity  extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private Variables variables;
    ArrayList<String> categories = new ArrayList<String>();
    public ArrayList<String> productCart = new ArrayList<String>();
    public ArrayList<String> getCart;

    private HeaderAdapter adapter;

    private TextView searchData;
    private Button search;
    private FloatingActionButton buttonCart;
    private FloatingActionButton addProducts;
    private ConstraintLayout lay;
    private Spinner spinner;
    private LinearLayout linearLayout;
    private LinearLayout.LayoutParams layoutParams;
    ArrayAdapter<String> adapterCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Categories Load
        LoadCategories();

        // Products Load
        LoadProducts("Todos");

        search = findViewById(R.id.button2);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchData = findViewById(R.id.editTextTextPersonName2);

                if(searchData.getText().toString().equals(""))
                {
                    LoadProductsWithName("Todos");
                }
                else
                {
                    LoadProductsWithName(searchData.getText().toString());
                }
            }
        });

        buttonCart = findViewById(R.id.cartButton);
        buttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(adapter.productCart.isEmpty())
                {
                    getCart = getIntent().getStringArrayListExtra("CartData");

                    Intent intent = new Intent(v.getContext(), CartActivity.class);
                    intent.putExtra("id", getIntent().getExtras().getString("id"));
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(v.getContext(), CartActivity.class);
                    intent.putExtra("CartData", adapter.productCart);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

    }

    // Methods
    private void LoadCategories()
    {
        spinner = findViewById(R.id.spinner);

        // Load Categories from WEB API
        String url =  variables.getAmbiente().concat("getCategorias.php");

        RequestQueue quees = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                String nombre;


                for (int i = 0; i < response.length(); i++)
                {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        nombre = jsonObject.getString("categori");

                        categories.add(nombre);

                    } catch (JSONException e) {}
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
        categories.add("Todos");

        quees.add(jsonArrayRequest);
        adapterCat = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCat);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LoadProducts((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void LoadProductsWithName(String search)
    {
        RecyclerView recyclerView = findViewById(R.id.reciclerView);
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        lay = findViewById(R.id.HomePage);

        ArrayList<ProductModel> productModels = new ArrayList<>();

        String url =  variables.getAmbiente().concat("getProducts.php?filter=" + search);
        System.out.println("valor de url: " + url);
        List<ProductModel> productModel = this.getProductos(url);

        for (ProductModel producto : productModel) {
            productModels.add(new ProductModel(producto.getId(), producto.getNombre(), producto.getDisponibilidad(), producto.getStock(), producto.getPrecio(), producto.getImages(), producto.getIva()));
            adapter = new HeaderAdapter(productModels, getIntent().getExtras().getString("id"));
            recyclerView.setAdapter(adapter);
            productCart = adapter.productCart;
        }
    }

    private void LoadProducts(String search)
    {
        RecyclerView recyclerView = findViewById(R.id.reciclerView);
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {return null; }
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {}
            @Override
            public int getItemCount() {
                return 0;
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        lay = findViewById(R.id.HomePage);

        ArrayList<ProductModel> productModels = new ArrayList<>();

        String url = variables.getAmbiente().concat("getProducts.php");
        System.out.println("valor de url: " + url);
        List<ProductModel> productModel = this.getProductos(url);

        for (ProductModel producto : productModel) {
                productModels.add(new ProductModel(producto.getId(), producto.getNombre(), producto.getDisponibilidad(), producto.getStock(), producto.getPrecio(), producto.getImages(), producto.getIva()));
                adapter = new HeaderAdapter(productModels, getIntent().getExtras().getString("id"));
                recyclerView.setAdapter(adapter);
                productCart = adapter.productCart;
        }
    }
    public List<ProductModel> getProductos(String url) {
//        String url = "http://localhost/abakend/api/getUsu.php";
        String respuesta = "";
        try {
            respuesta = peticionHttp(url, "GET");
            System.out.println("La respuesta es:\n" + respuesta);
        } catch (Exception e) {
            // Manejar excepción
            e.printStackTrace();
        }
        ObjectMapper m = new ObjectMapper();
        List<ProductModel> products = new ArrayList<>();
        try{
            products = m.readValue(respuesta, new TypeReference<ProductModel>() {});
            System.out.println("Datos: " + products);
        }catch (Exception e){
            System.out.println("e: " + e);
        }
        return products;
    }

    public  String peticionHttp(String urls, String method) throws Exception {
        StringBuilder resultado = new StringBuilder();
        URL url = new URL(urls);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod(method);
        BufferedReader rd = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
        String linea;
        while ((linea = rd.readLine()) != null) {
            resultado.append(linea);
        }
        rd.close();
        return resultado.toString();
    }
}
