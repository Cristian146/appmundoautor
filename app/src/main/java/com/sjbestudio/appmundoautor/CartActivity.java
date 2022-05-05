package com.sjbestudio.appmundoautor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity
{

    public ArrayList<String> productCart;
    Button backPage;
    Button buttonCont;
    TextView textSub;
    TextView textEnvio;

    ConstraintLayout lay;
    final float[] priceSub = new float[2];
    final String[] ids = new String[1];
    double envio = 1.50f;

    int finalIntent;
    private Variables variables;
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle saveInstaceState)
    {
        super.onCreate(saveInstaceState);
        setContentView(R.layout.activity_cart);

        //Product Cart
        backPage = findViewById(R.id.backButtonCart);
        buttonCont = findViewById(R.id.buttonC);
        textSub = findViewById(R.id.textView25);
        textEnvio = findViewById(R.id.textView26);

        LoadProducts(getIntent().getExtras().getString("id"));

        backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                intent.putExtra("id", getIntent().getExtras().getString("id"));
                startActivity(intent);
            }
        });

        buttonCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(finalIntent == 0)
                {
                    Toast.makeText(getApplicationContext(),"Debe haber mas de un producto en el carrito", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    RegisterCompra(ids[0]);
                }
            }
        });
    }

    private void RegisterCompra(String ids_cart)
    {
        String url = variables.ambiente.concat("register_buy?ids=" + ids_cart);

        RequestQueue que = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");

                            if(message.equals("ok"))
                            {
                                Toast.makeText(getApplicationContext(),"Se registro su compra", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Ocurrio un error al registrar la compra", Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch (JSONException ex)
                        {
                            Toast.makeText(getApplicationContext(),"Message" +  ex, Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(getApplicationContext(),"Message" +  error, Toast.LENGTH_SHORT).show();
                    }
                });

        que.add(jsonObjectRequest);
    }

    private void LoadProducts(String id)
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

        lay = findViewById(R.id.constMan);

        ArrayList<ProductModel> productModels = new ArrayList<>();

        String url = "https://mundoautosweb.000webhostapp.com/api/carrito?id=" + id;

        RequestQueue que = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                int id;
                String nombre, descripcion, codigo;
                double precio;
                double iva;
                int cantidad;

                for (int i = 0; i < response.length(); i++)
                {
                    ArrayList<CarouselItem> items = new ArrayList<>();
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        id = jsonObject.getInt("id_cart_prod");
                        nombre = jsonObject.getString("nombre");
                        descripcion = jsonObject.getString("descp");
                        codigo = jsonObject.getString("codigo");
                        precio = jsonObject.getDouble("precio");
                        iva = jsonObject.getDouble("iva");
                        cantidad = jsonObject.getInt("cantidad");

                        items.add(new CarouselItem(jsonObject.getString("imagen"), ""));
                        items.add(new CarouselItem(jsonObject.getString("imagen2"), ""));
                        items.add(new CarouselItem(jsonObject.getString("imagen3"), ""));

                        productModels.add(new ProductModel(id, nombre, descripcion, codigo, (float) precio, items, (float)iva, cantidad));

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),"Error" + e, Toast.LENGTH_SHORT).show();
                    }
                }
                ids[0] = "";

                for (int j = 0; j < productModels.size(); j++)
                {
                    priceSub[1] = productModels.get(j).getPrecio() * productModels.get(j).getIva() / 100;


                    priceSub[1] = priceSub[1] + productModels.get(j).getPrecio();
                    priceSub[1] = priceSub[1] * productModels.get(j).getCantidad();

                    priceSub[0] = priceSub[0] + priceSub[1];

                    ids[0] = ids[0] + productModels.get(j).getId() + ",";
                }

                finalIntent = productModels.size();

                DecimalFormat df = new DecimalFormat("#.00");

                textSub.setText(priceSub[0] + "$");
                textEnvio.setText(envio + "$");
                buttonCont.setText("Continuar    " + df.format((priceSub[0] + envio)) + "$");

                adapter = new CartAdapter(productModels);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"No existen productos en el carrito de compras", Toast.LENGTH_SHORT).show();
                buttonCont.setText("Continuar    " + (priceSub[0] + envio) + "$");
                textSub.setText(priceSub[0] + "$");
                textEnvio.setText(envio + "$");

                finalIntent = 0;
            }
        });
        que.add(jsonArrayRequest);
    }
}