package com.sjbestudio.appmundoautor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity
{
    private EditText user, pass, email, direction;
    private Button register, backLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = findViewById(R.id.userText);
        pass = findViewById(R.id.passText);
        email = findViewById(R.id.emailText);
        direction = findViewById(R.id.dirText);
        register = findViewById(R.id.creatCuenta);
        backLogin = findViewById(R.id.backLogin);

        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(user.getText().toString().isEmpty() || pass.getText().toString().isEmpty() ||
                   email.getText().toString().isEmpty() || register.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Register(user.getText().toString(), pass.getText().toString(), direction.getText().toString(), email.getText().toString());
                }
            }
        });
    }

    void Register(String user, String pass, String dir, String email)
    {
        String url = "https://mundoautosweb.000webhostapp.com/api/registro?user="+user+"&pass="+pass+"&email="+email+"&dir="+dir;
        RequestQueue que = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String state = response.getString("success");
                            String message = response.getString("message");

                            if(state.equals("ok"))
                            {
                                Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
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
