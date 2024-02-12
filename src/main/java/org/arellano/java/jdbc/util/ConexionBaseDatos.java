package org.arellano.java.jdbc.util;

/*Utilizar el patron singleton
* Se basa en hacer la conexion de manera separada
* Esto provoca que se genere una instancia por cada peticion
* Y todos los campos se mantienen privados*/

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {
    private static final String url = "jdbc:mysql://localhost:3306/java_curso";
    private static final String user = "root";
    private static final String password = "rootsasa";
    private static BasicDataSource pool;

    public static BasicDataSource getInstancia() throws SQLException {
        if(pool == null){
            pool = new BasicDataSource();
            pool.setUrl(url);
            pool.setUsername(user);
            pool.setPassword(password);
            pool.setInitialSize(3);
            pool.setMinIdle(3);
            pool.setMaxIdle(8);
            pool.setMaxTotal(8);
        }

        return pool;
     }

     public static Connection getConnection()throws SQLException{
        return getInstancia().getConnection();
     }
}
