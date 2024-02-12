package org.arellano.java.jdbc.servicio;

import org.arellano.java.jdbc.modelo.Categoria;
import org.arellano.java.jdbc.modelo.Productos;

import java.sql.SQLException;
import java.util.List;

public interface Servicio {
    List<Productos> listar() throws SQLException;

    Productos porId(Long id) throws SQLException;

    Productos guardar(Productos producto) throws SQLException;

    void eliminar(Long id) throws SQLException;

    List<Categoria> listarCategoria() throws SQLException;

    Categoria porIdCategoria(Long id) throws SQLException;

    Categoria guardarCategoria(Categoria categoria) throws SQLException;

    void eliminarCategoria(Long id) throws SQLException;

    void guardarProductoConCategoria(Productos producto, Categoria categoria) throws SQLException;
}
