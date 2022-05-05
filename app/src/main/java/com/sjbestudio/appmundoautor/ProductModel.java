package com.sjbestudio.appmundoautor;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;

public class ProductModel
{
    CarouselItem images;
    int id;
    String nombre;
    String stock;
    String disponibilidad;
    float precio;
    float iva;

    public ProductModel() {}

    public ProductModel(int id, String nombre, String stock, String disponibilidad, float precio, CarouselItem images, float iva)
    {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.disponibilidad = disponibilidad;
        this.precio = precio;
        this.images = images;
        this.iva = iva;

    }

    //Getter
    public float getPrecio() {
        return precio;
    }

    public String getStock() {
        return stock;
    }

    public CarouselItem getImages() {
        return images;
    }
    public String getNombre() {
        return nombre;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public float getIva() { return iva; }

    public int getId() {return id;}

    //Setter

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

    public void setImages(CarouselItem images) {
        this.images = images;
    }

    public  void setIva(float iva) { this.iva = iva; }
    public void setId(int id) {this.id = id; }
}
