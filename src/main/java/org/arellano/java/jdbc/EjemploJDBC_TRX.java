package org.arellano.java.jdbc;

import org.arellano.java.jdbc.modelo.Categoria;
import org.arellano.java.jdbc.modelo.Productos;
import org.arellano.java.jdbc.repositorio.CategoriaRepositorioImp;
import org.arellano.java.jdbc.repositorio.ProductoRepositorioImplement;
import org.arellano.java.jdbc.repositorio.Repositorio;
import org.arellano.java.jdbc.servicio.CatalogoServicio;
import org.arellano.java.jdbc.servicio.Servicio;
import org.arellano.java.jdbc.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


/* Zona horaria
   String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=UTC";
*/

/* Ejemplo de transacciones con JDBC
*  Una transaccion es un conjunto de operaciones sobre la base de datos que se deben ejecutar
* como una sola unidad
*  Ejemplo:
*   Si no se ejecuta una operacion no se ejecuta ninguna y gestionamos el error*/

public class EjemploJDBC_TRX {
    public static void main(String[] args) throws SQLException {
        Servicio servicio = new CatalogoServicio();

        System.out.println("\n============== Listar productos ============");
        servicio.listar().forEach(System.out::println);

        System.out.println("\n============== Insertar nueva Categoria ============");
        Categoria categoria = new Categoria();
        categoria.setNombre("Iluminación");

        System.out.println("\n============== Guardar un productos ============");
        Productos productoNuevo = new Productos();
        productoNuevo.setNombre("Amper 0 zugar");
        productoNuevo.setPrecio(22);
        productoNuevo.setFecha_registro(new Date());
        productoNuevo.setSku("11124");

        servicio.guardarProductoConCategoria(productoNuevo, categoria);
        System.out.println("Se guardo con exito!" + productoNuevo.getId());

        System.out.println("\n============== Listar todos los productos ============");
        servicio.listar().forEach(System.out::println);
    }
}
