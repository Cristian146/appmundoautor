package com.sjbestudio.appmundoautor;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;

public class ProductModel
{
    ArrayList<CarouselItem> images = new ArrayList<>();
    int id;
    String nombre;
    String descripcion;
    String codigo;
    float precio;
    float iva;
    int cantidad;

    public ProductModel() {}

    public ProductModel(int id, String nombre, String descripcion, String codigo, float precio, ArrayList<CarouselItem> images, float iva, int cantidad)
    {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.precio = precio;
        this.images = images;
        this.iva = iva;
        this.cantidad = cantidad;
    }

    //Getter
    public float getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ArrayList<CarouselItem> getImages() {
        return images;
    }
    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public float getIva() { return iva; }

    public int getId() {return id;}

    public int getCantidad() { return cantidad; }
    //Setter

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setImages(ArrayList<CarouselItem> images) {
        this.images = images;
    }

    public  void setIva(float iva) { this.iva = iva; }
    public void setId(int id) {this.id = id; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
