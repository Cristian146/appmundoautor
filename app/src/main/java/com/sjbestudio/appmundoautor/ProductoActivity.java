package com.sjbestudio.appmundoautor;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class ProductoActivity extends AppCompatActivity
{
    private Button buttonBack;

    EditText nom;
    EditText image;
    EditText stock;
    EditText price;
    EditText dispo;
    EditText imagen;

    @Override

    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_create_product);

        nom = findViewById(R.id.nom);
        price = findViewById(R.id.price);
        stock = findViewById(R.id.code);
        dispo = findViewById(R.id.des);
        image = findViewById(R.id.image);

        buttonBack = findViewById(R.id.backButtonCreate);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}