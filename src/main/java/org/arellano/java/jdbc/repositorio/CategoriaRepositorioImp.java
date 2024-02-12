package org.arellano.java.jdbc.repositorio;

import org.arellano.java.jdbc.modelo.Categoria;
import org.arellano.java.jdbc.modelo.Productos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepositorioImp implements Repositorio<Categoria> {

    private Connection coon;

    public CategoriaRepositorioImp(){};

    public CategoriaRepositorioImp(Connection coon){
        this.coon = coon;
    }

    public Connection getConnection(){
        return this.coon;
    }

    @Override
    public void setConnection(Connection connection){
        this.coon = connection;
    }

    @Override
    public List<Categoria> listar() throws SQLException {
        List<Categoria> categoriaList = new ArrayList<>();

        try(Statement statement = coon.createStatement()){
            ResultSet resultSet = statement.executeQuery("select * from categorias");
            while(resultSet.next()){
                Categoria categoriaNueva = listarCategoria(resultSet);

                categoriaList.add(categoriaNueva);
            }
        }

        return categoriaList;
    }

    @Override
    public Categoria porId(Long id) throws SQLException {
        Categoria categoria = null;

        try(PreparedStatement preparedStatement = coon.prepareStatement("select * from categorias where id = ?")){
            preparedStatement.setLong(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    categoria = listarCategoria(resultSet);
                }
            }
        }
        return categoria;
    }

    @Override
    public Categoria guardar(Categoria categoria) throws SQLException {
        String sentenciaSql = "";
        boolean idPositivo = categoria.getId() != null && categoria.getId() > 0;

        if(idPositivo){
             sentenciaSql = "update categorias set nombre = ? where id = ?";
        } else {
            sentenciaSql = "insert into categorias(nombre) values(?)";
        }

        try(PreparedStatement preparedStatement = coon.prepareStatement(sentenciaSql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, categoria.getNombre());

            if(idPositivo){
                preparedStatement.setLong(2, categoria.getId());
            }

            preparedStatement.executeUpdate();

            if(categoria.getId() == null){
                try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                    if(resultSet.next())
                        categoria.setId(resultSet.getLong(1));
                }
            }

        }

        return categoria;
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        PreparedStatement preparedStatement = coon.prepareStatement("delete from categorias where id = ?");
        preparedStatement.setLong(1, id);

        preparedStatement.executeUpdate();
    }

    private Categoria listarCategoria(ResultSet resultSet) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(resultSet.getLong("id"));
        categoria.setNombre(resultSet.getString("nombre"));

        return categoria;
    }
}
