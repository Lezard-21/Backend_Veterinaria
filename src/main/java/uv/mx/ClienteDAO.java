package uv.mx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    public static List<Cliente> getAllClientes() {
        ResultSet rs = null;
        List<Cliente> resultado = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        Connection conn = Conexion.getConnection();
        String query = "SELECT * from cliente";

        try {
            preparedStatement = conn.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Cliente p = new Cliente(rs.getString("id_cliente"), rs.getString("nombre"), rs.getString("contraseña"));
                resultado.add(p);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                rs.close();
                conn.close();
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return resultado;
    }

    public static String createUsuario(Cliente p) {
        PreparedStatement preparedStatement = null;
        Connection conn = Conexion.getConnection();
        String msj = "";
        try {
            String query = "INSERT INTO cliente (id_cliente, nombre, contraseña) values (?,?,?)";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, p.getId());
            preparedStatement.setString(2, p.getNombre());
            preparedStatement.setString(3, p.getPassword());
            if (preparedStatement.executeUpdate() > 0)
                msj = "cliente agregado";
            else
                msj = "cliente no agregado";

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                conn.close();
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return msj;
    }

    public static Cliente modifyUsuario(Cliente u){
        PreparedStatement stm = null;
        Connection conn = null;
        Cliente updatedUser = null;
    
        conn = Conexion.getConnection();
    
        try {
            String sql = "UPDATE cliente SET nombre = ?, contraseña = ? WHERE id_cliente = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, u.getNombre());
            stm.setString(2, u.getPassword());
            stm.setString(3, u.getId());
            int rowsUpdated = stm.executeUpdate();
    
            if (rowsUpdated > 0) {
                updatedUser = GetClienteFromId(u.getId());
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
                stm = null;
            }
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    
        return updatedUser;
    }

    public static Cliente deleteUsuario(String id){
        Cliente res = null;
        Connection conn = Conexion.getConnection();
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM cliente WHERE id_cliente = ?";
        try {
            res = ClienteDAO.GetClienteFromId(id);
            if (res!=null) {
                preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, id);  
                preparedStatement.executeUpdate();
                conn.close();
                preparedStatement.close();
            }else{
                System.out.println(id);
                System.out.println("El id que ingreso no es valido");
            }
            // String queryRes = "SELECT * from users WHERE id= ?";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } 
        return res;
    }

     public static Cliente GetClienteFromId(String id) {
        ResultSet rs = null;
        Cliente resultado = null;
        PreparedStatement preparedStatement = null;
        Connection conn = Conexion.getConnection();
        String query = "SELECT * from cliente where id_cliente=?";

        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, id); 
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                resultado = new Cliente(rs.getString("id_cliente"), rs.getString("nombre"), rs.getString("contraseña"));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                rs.close();
                conn.close();
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return resultado;
    }

    public static Cliente auth(Cliente cliente){
        ResultSet rs = null;
        Cliente resultado = null;
        PreparedStatement preparedStatement = null;
        Connection conn = Conexion.getConnection();
        String query = "SELECT * from cliente where nombre=? and contraseña=?";
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getPassword()); 
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                resultado = new Cliente(rs.getString("id_cliente"), rs.getString("nombre"), rs.getString("contraseña"));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                rs.close();
                conn.close();
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return resultado;
    }
}
