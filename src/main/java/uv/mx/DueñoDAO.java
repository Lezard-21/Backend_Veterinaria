package uv.mx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DueñoDAO {
    public static List<Dueño> getAllDueños() {
        ResultSet rs = null;
        List<Dueño> resultado = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        Connection conn = Conexion.getConnection();
        String query = "SELECT * from dueño";

        try {
            preparedStatement = conn.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Dueño p = new Dueño(rs.getString("id_dueño"), rs.getString("nombre"), rs.getString("telefono"));
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

    public static String createDueño(Dueño p) {
        PreparedStatement preparedStatement = null;
        Connection conn = Conexion.getConnection();
        String msj = "";
        try {
            String query = "INSERT INTO dueño (id_dueño, nombre, telefono) values (?,?,?)";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, p.getId());
            preparedStatement.setString(2, p.getNombre());
            preparedStatement.setString(3, p.getTelefono());
            if (preparedStatement.executeUpdate() > 0)
                msj = "Dueño agregado";
            else
                msj = "Dueño no agregado";

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

    public static Dueño modifyDueño(Dueño u){
        PreparedStatement stm = null;
        Connection conn = null;
        Dueño updatedUser = null;
    
        conn = Conexion.getConnection();
    
        try {
            String sql = "UPDATE dueño SET nombre = ?, telefono = ? WHERE id_dueño = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, u.getNombre());
            stm.setString(2, u.getTelefono());
            stm.setString(3, u.getId());
            int rowsUpdated = stm.executeUpdate();
    
            if (rowsUpdated > 0) {
                updatedUser = GetDueñoFromId(u.getId());
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

    public static Dueño deleteDueño(String id){
        Dueño res = null;
        Connection conn = Conexion.getConnection();
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM dueño WHERE id_dueño = ?";
        try {
            res = DueñoDAO.GetDueñoFromId(id);
            System.out.println(DueñoDAO.GetDueñoFromId(id));
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

     public static Dueño GetDueñoFromId(String id) {
        ResultSet rs = null;
        Dueño resultado = null;
        PreparedStatement preparedStatement = null;
        Connection conn = Conexion.getConnection();
        String query = "SELECT * from dueño where id_dueño=?";

        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, id); 
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                resultado = new Dueño(rs.getString("id_dueño"), rs.getString("nombre"), rs.getString("telefono"));
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
