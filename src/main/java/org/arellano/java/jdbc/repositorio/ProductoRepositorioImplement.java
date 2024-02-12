package org.arellano.java.jdbc.repositorio;

import org.arellano.java.jdbc.modelo.Categoria;
import org.arellano.java.jdbc.modelo.Productos;
import org.arellano.java.jdbc.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorioImplement implements Repositorio<Productos> {

    private Connection connection;

    public ProductoRepositorioImplement(){};

    public ProductoRepositorioImplement(Connection connection){
        this.connection = connection;
    }

    public Connection getConnection(){
        return this.connection;
    }

    @Override
    public void setConnection(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Productos> listar() throws SQLException {
        List<Productos> productosList = new ArrayList<>();

        try(Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery("SELECT p.*, c.nombre as categoria FROM productos as p inner join categorias as c on (p.categoria_id = c.id)")) {

            while(resultSet.next()){
                Productos producto = llenarProductos(resultSet);
                productosList.add(producto);
            }

        }

        return productosList;
    }

    @Override
    public Productos porId(Long id) throws SQLException {
        Productos producto = null;

        try (PreparedStatement preparedStatement = connection
                .prepareStatement("select p.*, c.nombre as categoria from productos as p inner join categorias as c on (p.categoria_id = c.id) where p.id = ?")){
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                producto = llenarProductos(resultSet);
            }

            resultSet.close();

        }

        return producto;
    }

    @Override
    public Productos guardar(Productos productos) throws SQLException {
        String resultSql;
        boolean idPositivo = productos.getId() != null && productos.getId() > 0;

        if(idPositivo){
            resultSql = "update productos set nombre = ?, precio = ?, categoria_id = ?, sku = ? where id = ?";
        } else {
            resultSql = "Insert into productos(nombre, precio, categoria_id, sku, fecha_registro) values (?, ?, ?, ?, ?)";
        }

        try(PreparedStatement preparedStatement = connection.prepareStatement(resultSql, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, productos.getNombre());
            preparedStatement.setLong(2, productos.getPrecio());
            preparedStatement.setLong(3, productos.getCategoria().getId());
            preparedStatement.setString(4, productos.getSku());

            if(idPositivo){
                preparedStatement.setLong(5, productos.getId());
            } else {
                preparedStatement.setDate(5, new Date(productos.getFecha_registro().getTime()));
            }

            preparedStatement.executeUpdate();

            // Si se inserta un producto Retornarlo y obtener el id generado
            if(productos.getId() == null){
                try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                    if(resultSet.next()){
                        productos.setId(resultSet.getInt(1));
                    }
                }
            }

            return productos;
        }

    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement("delete from productos where id = ?")){
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        }
    }

    private Productos llenarProductos(ResultSet resultSet) throws SQLException {
        Productos producto = new Productos();
        producto.setId(resultSet.getInt("id"));
        producto.setNombre(resultSet.getString("nombre"));
        producto.setPrecio(resultSet.getInt("precio"));
        producto.setFecha_registro(resultSet.getDate("fecha_registro"));
        producto.setSku(resultSet.getString("sku"));

        Categoria categoria = new Categoria();
        categoria.setId(resultSet.getLong("categoria_id"));
        categoria.setNombre(resultSet.getString("categoria"));

        producto.setCategoria(categoria);
        return producto;
    }
}
