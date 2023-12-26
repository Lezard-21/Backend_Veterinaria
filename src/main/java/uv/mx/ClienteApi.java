package uv.mx;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import spark.Request;
import spark.Response;
import spark.Route;

public class ClienteApi {
    static Gson gson = new Gson();

    public static Route mostrar = (Request req, Response res) -> {
        res.type("application/json");
        return gson.toJson(ClienteDAO.getAllClientes());
    };
    
    public static Route buscarPorId = (Request req, Response res) -> {
        String id = req.queryParams("id");
        res.type("application/json");
        return gson.toJson(ClienteDAO.GetClienteFromId(id));
    };

    public static Route agregar = (Request req, Response res) -> {
        Cliente user = gson.fromJson(req.body(), Cliente.class);
        String id = UUID.randomUUID().toString();
        user.setId(id);
        ClienteDAO.createUsuario(user);
        res.type("application/json");
        res.status(200);
        return gson.toJson(id);
    };

    public static Route eliminar = (Request req, Response res) -> {
        // String id = req.queryParams("id");
        String id = req.params(":id");
        // String id = gson.fromJson(req.body(), String.class);
        Cliente u = ClienteDAO.deleteUsuario(id);
        res.type("application/json");
        if (u!=null) {
            res.status(200);
            return gson.toJson(u);                
        }else{
            res.status(404);
            JsonObject r = new JsonObject();
            r.addProperty("error", "Error al eliminar el usuario");
            return r;
        }
        // return gson.toJson(DAO.GetUsuariosFromId(id));
    };

    public static Route modificar = (Request req, Response res) -> {
        Cliente user = gson.fromJson(req.body(), Cliente.class);    
        res.type("application/json");
        return gson.toJson( ClienteDAO.modifyUsuario(user));
    };
}
