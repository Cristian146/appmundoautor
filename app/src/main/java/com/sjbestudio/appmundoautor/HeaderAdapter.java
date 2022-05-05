package com.sjbestudio.appmundoautor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.ViewHolder>
{
    private ArrayList<ProductModel> productModels;
    public ArrayList<String> productCart = new ArrayList<String>();
    String id_context;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder implements com.sjbestudio.appmundoautor.ViewHolder
    {
        Button buttonAdd;
        TextView ivaText;
        TextView nombreText;
        TextView codigoText;
        TextView descripcionText;
        TextView precioText, cantidad, stock;
        ImageView imageView;
        ConstraintLayout layout;
        ImageCarousel imageCarousel;
        private float mScaleFactor = 1.0f;

        public ViewHolder(View view)
        {
            super(view);

            nombreText = view.findViewById(R.id.idNombre);
            codigoText = view.findViewById(R.id.idCodigo);
            descripcionText = view.findViewById(R.id.idDescripcion);
            precioText = view.findViewById(R.id.precioID);
            cantidad = view.findViewById(R.id.cantidad);
            stock = view.findViewById(R.id.stockprod);
            //imageView = view.findViewById(R.id.imageView2);
            imageCarousel = view.findViewById(R.id.carousel);
            buttonAdd = view.findViewById(R.id.button3);
            layout = view.findViewById(R.id.constrainrL);
            ivaText = view.findViewById(R.id.textIva);

            cantidad.setText("1");
        }
    }
    public HeaderAdapter(ArrayList<ProductModel> productModels, String id_context) {
        this.productModels = productModels;
        this.id_context = id_context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_header, viewGroup, false);

        context = view.getContext();

        return new ViewHolder(view);
    }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int pos) {

            int id = productModels.get(pos).getId();

            viewHolder.nombreText.setText(productModels.get(pos).getNombre());
            viewHolder.codigoText.setText(productModels.get(pos).getCodigo());
            viewHolder.descripcionText.setText(productModels.get(pos).getDescripcion());
            viewHolder.precioText.setText(productModels.get(pos).getPrecio() + "");
            viewHolder.ivaText.setText(productModels.get(pos).getIva() + "");
            viewHolder.imageCarousel.setData(productModels.get(pos).getImages());
            viewHolder.stock.setText(productModels.get(pos).getCantidad() + "");

            // Load Image
            String cod = productModels.get(pos).getCodigo();
            final String[] cantidad = {""};

            int posti = pos;

            viewHolder.cantidad.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    cantidad[0] = viewHolder.cantidad.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            viewHolder.buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cantidad[0].isEmpty() || cantidad[0] == "0")
                    {
                        Intent intent = new Intent((Activity)context, HomeActivity.class);
                        AddCart(id_context, id, "1");
                    }
                    else
                    {
                        Intent intent = new Intent((Activity)context, HomeActivity.class);
                        AddCart(id_context, id, cantidad[0]);
                    }
                }
            });
        }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public void AddCart(String id, int id_prod, String cantidad)
    {
        String url = "https://mundoautosweb.000webhostapp.com/api/add_cart.php?id="+id+"&id_prod="+id_prod+"&cant="+cantidad;
        RequestQueue que = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");

                            if(message.equals("ok"))
                            {
                                Toast.makeText(context,"Se añadio el carrito satisfactoriamente", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(context,"Error al añadir", Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch (JSONException ex)
                        {
                            Toast.makeText(context,"Message" +  ex, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(context,"Message" +  error, Toast.LENGTH_SHORT).show();
                    }
                });

        que.add(jsonObjectRequest);
    }
}
