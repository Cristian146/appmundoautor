package com.sjbestudio.appmundoautor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>
{

    private ArrayList<ProductModel> productModels;
    public ArrayList<String> productCart = new ArrayList<String>();
    Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder implements com.sjbestudio.appmundoautor.ViewHolder {

        Button buttonDelete;
        TextView ivaText;
        TextView nombreText;
        TextView StockText;
        TextView DisponibilidadText;
        TextView precioText, cantidadID;
        ConstraintLayout layout;
        ImageCarousel imageCarousel;

        public ViewHolder(View view)
        {
            super(view);
            nombreText = view.findViewById(R.id.idNombre);
            StockText = view.findViewById(R.id.idStock);
            DisponibilidadText = view.findViewById(R.id.idDisponibilidad);
            precioText = view.findViewById(R.id.precioID);
            ivaText = view.findViewById(R.id.textIva);
            imageCarousel = view.findViewById(R.id.carousel);
            buttonDelete = view.findViewById(R.id.button_delete);
            layout = view.findViewById(R.id.constrainrL);

        }
    }
    public CartAdapter(ArrayList<ProductModel> productModels) {
        this.productModels = productModels;
    }

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cart_header, viewGroup, false);

        context = view.getContext();
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartAdapter.ViewHolder viewHolder, final int pos) {

        viewHolder.nombreText.setText(productModels.get(pos).getNombre());
        viewHolder.StockText.setText(productModels.get(pos).getStock());
        viewHolder.DisponibilidadText.setText(productModels.get(pos).getDisponibilidad());
        viewHolder.precioText.setText(productModels.get(pos).getPrecio() + "");
        //viewHolder.ivaText.setText(productModels.get(pos).getIva());


        int id = productModels.get(pos).getId();

        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteCart(id);
            }
        });
    }

    void AddCartModel(String productModel)
    {
        productCart.add(productModel);
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return productModels.size();
    }

    void DeleteCart(int id)
    {
        String url =   "https://automundotulcan.000webhostapp.com/api/getRegis.php" + id;
        RequestQueue que = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            if(message.equals("delete"))
                            {
                                Toast.makeText(context,"Se elimino el producto", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(context,"No se pudo eliminar el product", Toast.LENGTH_SHORT).show();
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
