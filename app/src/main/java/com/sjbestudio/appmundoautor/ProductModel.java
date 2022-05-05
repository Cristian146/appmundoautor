package com.sjbestudio.appmundoautor;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;

public class ProductModel
{
    ArrayList<CarouselItem> images = new ArrayList<>();
    int id;
    String nombre;
    String disponibilidad;
    String stock;
    float precio;
    float iva;

    public ProductModel() {}

    public ProductModel(int id, String nombre, String disponibilidad, String stock, float precio, ArrayList<CarouselItem> images, float iva)
    {
        this.id = id;
        this.nombre = nombre;
        this.disponibilidad = disponibilidad;
        this.stock = stock;
        this.precio = precio;
        this.images = images;
        this.iva = iva;
    }

    //Getter
    public float getPrecio() {
        return precio;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public ArrayList<CarouselItem> getImages() {
        return images;
    }
    public String getNombre() {
        return nombre;
    }

    public String getStock() {
        return stock;
    }

    public float getIva() { return iva; }

    public int getId() {return id;}

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public void setImages(ArrayList<CarouselItem> images) {
        this.images = images;
    }

    public  void setIva(float iva) { this.iva = iva; }
    public void setId(int id) {this.id = id;}
}
