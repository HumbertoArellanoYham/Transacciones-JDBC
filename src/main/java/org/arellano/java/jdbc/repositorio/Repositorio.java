package org.arellano.java.jdbc.repositorio;

import org.arellano.java.jdbc.modelo.Productos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repositorio<T> {
    void setConnection(Connection connection);
    List<T> listar() throws SQLException;

    T porId(Long id) throws SQLException;

    T guardar(T t) throws SQLException;

    void eliminar(Long id) throws SQLException;
}
