package uv.mx;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import spark.Request;
import spark.Response;
import spark.Route;

public class DueñoApi {
    static Gson gson = new Gson();

    public static Route mostrar = (Request req, Response res) -> {
        res.type("application/json");
        return gson.toJson(DueñoDAO.getAllDueños());
    };
    
    public static Route buscarPorId = (Request req, Response res) -> {
        String id = req.queryParams("id");
        res.type("application/json");
        return gson.toJson(DueñoDAO.GetDueñoFromId(id));
    };

    public static Route agregar = (Request req, Response res) -> {
        Dueño user = gson.fromJson(req.body(), Dueño.class);
        String id = UUID.randomUUID().toString();
        user.setId(id);
        DueñoDAO.createDueño(user);
        res.type("application/json");
        res.status(200);
        return gson.toJson(id);
    };

    public static Route eliminar = (Request req, Response res) -> {
        // String id = req.queryParams("id");
        String id = req.params(":id");
        // String id = gson.fromJson(req.body(), String.class);
        Dueño u = DueñoDAO.deleteDueño(id);
        res.type("application/json");
        if (u!=null) {
            res.status(200);
            return gson.toJson(u);                
        }else{
            res.status(404);
            JsonObject r = new JsonObject();
            r.addProperty("error", "Error al eliminar el Dueño");
            return r;
        }
        // return gson.toJson(DAO.GetUsuariosFromId(id));
    };

    public static Route modificar = (Request req, Response res) -> {
        Dueño user = gson.fromJson(req.body(), Dueño.class);    
        res.type("application/json");
        return gson.toJson( DueñoDAO.modifyDueño(user));
    };
}
