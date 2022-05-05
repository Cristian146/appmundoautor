package com.sjbestudio.appmundoautor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class BuyActivity extends AppCompatActivity {
    //private Variables variables;

    Button backPage;
    Button finalizar;

    TextView textSub;

    @Override
    protected void onCreate(Bundle saveInstaceState) {
        super.onCreate(saveInstaceState);
        setContentView(R.layout.activity_buy);

        backPage = findViewById(R.id.backButtonBuy);
        finalizar = findViewById(R.id.button4);
        textSub = findViewById(R.id.textView29);

        backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("id", getIntent().getExtras().getString("id"));
                startActivity(intent);
            }
        });

        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DeleteCart();
            }
        });
    }

    void DeleteCart()
    {
        String url =   "https://automundotulcan.000webhostapp.com/api/getRegis.php";
        RequestQueue que = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            if(message.equals("delete"))
                            {
                                Toast.makeText(getApplicationContext(),"Se elimino el producto del carrito", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"No se pudo procesar la compra", Toast.LENGTH_SHORT).show();
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
}