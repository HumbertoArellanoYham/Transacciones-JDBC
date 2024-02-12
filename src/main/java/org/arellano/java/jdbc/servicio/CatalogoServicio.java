package org.arellano.java.jdbc.servicio;

import org.arellano.java.jdbc.modelo.Categoria;
import org.arellano.java.jdbc.modelo.Productos;
import org.arellano.java.jdbc.repositorio.CategoriaRepositorioImp;
import org.arellano.java.jdbc.repositorio.ProductoRepositorioImplement;
import org.arellano.java.jdbc.repositorio.Repositorio;
import org.arellano.java.jdbc.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CatalogoServicio implements Servicio{
    private Repositorio<Productos> productosRepositorio;
    private Repositorio<Categoria> categoriaRepositorio;

    public CatalogoServicio(){
        this.productosRepositorio = new ProductoRepositorioImplement();
        this.categoriaRepositorio = new CategoriaRepositorioImp();
    }

    @Override
    public List<Productos> listar() throws SQLException {
        try(Connection coon = ConexionBaseDatos.getConnection()){
            productosRepositorio.setConnection(coon);

            return productosRepositorio.listar();
        }
    }

    @Override
    public Productos porId(Long id) throws SQLException {
        try(Connection coon = ConexionBaseDatos.getConnection()){
            productosRepositorio.setConnection(coon);

            return productosRepositorio.porId(id);
        }
    }

    @Override
    public Productos guardar(Productos producto) throws SQLException {
        try(Connection coon = ConexionBaseDatos.getConnection()){
            productosRepositorio.setConnection(coon);

            if(coon.getAutoCommit()){
                coon.setAutoCommit(false);
            }

            Productos nuevoProducto = null;

            try{
                nuevoProducto = productosRepositorio.guardar(producto);

                coon.commit();
            }catch (SQLException e){
                coon.rollback();
                throw new RuntimeException(e.getCause());
            }

            return nuevoProducto;
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try(Connection coon = ConexionBaseDatos.getConnection()){
            productosRepositorio.setConnection(coon);

            if(coon.getAutoCommit()){
                coon.setAutoCommit(false);
            }

            try{
                productosRepositorio.eliminar(id);

                coon.commit();
            }catch (SQLException e){
                coon.rollback();
                throw new RuntimeException(e.getCause());
            }
        }

    }

    @Override
    public List<Categoria> listarCategoria() throws SQLException {
        try(Connection coon = ConexionBaseDatos.getConnection()){
            categoriaRepositorio.setConnection(coon);

            return categoriaRepositorio.listar();
        }
    }

    @Override
    public Categoria porIdCategoria(Long id) throws SQLException {
        try(Connection coon = ConexionBaseDatos.getConnection()){
            categoriaRepositorio.setConnection(coon);

            return categoriaRepositorio.porId(id);
        }
    }

    @Override
    public Categoria guardarCategoria(Categoria categoria) throws SQLException {
        try(Connection coon = ConexionBaseDatos.getConnection()){
            categoriaRepositorio.setConnection(coon);

            if(coon.getAutoCommit()){
                coon.setAutoCommit(false);
            }

            Categoria nuevaCategoria = null;

            try{
                nuevaCategoria = categoriaRepositorio.guardar(categoria);

                coon.commit();
            }catch (SQLException e){
                coon.rollback();
                throw new RuntimeException(e.getCause());
            }

            return nuevaCategoria;
        }
    }

    @Override
    public void eliminarCategoria(Long id) throws SQLException {
        try(Connection coon = ConexionBaseDatos.getConnection()){
            categoriaRepositorio.setConnection(coon);


            if(coon.getAutoCommit()){
                coon.setAutoCommit(false);
            }

            try{
                categoriaRepositorio.eliminar(id);

                coon.commit();
            }catch (SQLException e){
                coon.rollback();
                throw new RuntimeException(e.getCause());
            }
        }
    }

    @Override
    public void guardarProductoConCategoria(Productos producto, Categoria categoria) throws SQLException {
        try(Connection coon = ConexionBaseDatos.getConnection()){
            categoriaRepositorio.setConnection(coon);
            productosRepositorio.setConnection(coon);

            if(coon.getAutoCommit()){
                coon.setAutoCommit(false);
            }

            try{
                Categoria nuevaCategoria = categoriaRepositorio.guardar(categoria);
                producto.setCategoria(nuevaCategoria);

                productosRepositorio.guardar(producto);

                coon.commit();
            } catch (SQLException e){
                coon.rollback();
                throw new RuntimeException(e.getCause());
            }
        }
    }
}
