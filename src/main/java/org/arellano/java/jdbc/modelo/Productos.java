package org.arellano.java.jdbc.modelo;

import java.util.Date;

public class Productos {
    private Integer id;
    private String nombre;
    private int precio;
    private Date fecha_registro;

    private Categoria categoria;

    private String sku;

    public Productos(){}

    public Productos(Integer id, String nombre, int precio, Date fecha_registro){
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.fecha_registro = fecha_registro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public String toString(){
        return id + " | " + this.nombre + " | " + this.precio + " | " + this.fecha_registro + " | " + getCategoria().getNombre() + " | " + this.sku;
    }
}
