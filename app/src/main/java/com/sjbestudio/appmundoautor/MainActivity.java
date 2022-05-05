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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    EditText usuario;
    EditText Password;
    Button getbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = (EditText) findViewById(R.id.idNombre);
        Password = (EditText) findViewById(R.id.editTextTextPassword);
        getbutton = (Button) findViewById(R.id.button);


        getbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClassConnection connection = new ClassConnection();

                try {
                    String name = usuario.getText().toString();
                    String password = Password.getText().toString();
                    String url = "https://automundotulcan.000webhostapp.com/api/getRegis.php?name=" + name + "&password=" + password;
                    String response = connection.execute(url).get();

                    ObjectMapper obj = new ObjectMapper();
                    UserModel user = obj.readValue(response, new TypeReference<UserModel>() {
                    });
                    if (user.getError()) {
                        Toast.makeText(getApplicationContext(), "Datos Incorrectos", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Bienvenido " + user.getName(), Toast.LENGTH_SHORT).show();
                        Intent miIntent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(miIntent);
                    }


                    //usuario.setText (usuarios);
                    // Password.setText (contra);

                } catch (Exception e) {
                    System.out.println("Error: " + e);
                }
                //private void obtenerInfo(){
                //  /String name = usuario.getText().toString();
                // String password= Password.getText().toString();

                //  Toast.makeText(getApplicationContext(), "Nombre:"+name,Toast.LENGTH_SHORT).show();
                // Toast.makeText(getApplicationContext(), "Contraseña:"+password,Toast.LENGTH_SHORT).show();


                // }
            }

        });
    }
}

