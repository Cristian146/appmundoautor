package com.sjbestudio.appmundoautor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<UserModel> userModelList = new ArrayList<>();

    private EditText usuario;
    private EditText contra;
    private AlertDialog.Builder builder;
    private Toast alerta;
    private String mensaje;
    private Button btnIniciar, btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Usuario Register
        userModelList.add(new UserModel("admin", "admin", "admin"));
        userModelList.add(new UserModel("cliente", "cliente", "cliente"));
        // Usuario Register End

        usuario = findViewById(R.id.editTextTextPersonName);
        contra = findViewById(R.id.editTextTextPassword);
        btnIniciar = findViewById(R.id.button);
        btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(usuario.length() == 0 || contra.length() == 0)
                {
                    mensaje = "Se requiere una contraseña y un usuario";
                    alerta = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
                    alerta.show();
                }
                else
                {
                    for(UserModel model : userModelList)
                    {
                        String usuarioGet = usuario.getText().toString();
                        String passwordGet = contra.getText().toString();

                        if(usuarioGet.isEmpty() || passwordGet.isEmpty())
                        {
                            mensaje = "Se requiere un usuario y una contraseña";
                            alerta = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
                            alerta.show();
                        }
                        else
                        {
                            Login(usuarioGet, passwordGet);
                        }
                    }
                }
            }
        });
    }

    void Login(String user, String pass)
    {
        String url = "https://mundoautosweb.000webhostapp.com/api/login.php?user="+user+"&pass="+pass;
        RequestQueue que = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String id = response.getString("id");
                            String message = response.getString("message");

                            Toast.makeText(getApplicationContext(),"Bienvenido", Toast.LENGTH_SHORT).show();

                            if(message.equals("ok"))
                            {
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                intent.putExtra("id", id);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Usuario y/o contrasela incorrecta", Toast.LENGTH_SHORT).show();
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