// Control 1 DSA
//A fecha de 11/11/2024
// David Lamas Martínez
package edu.upc.dsa.services;

import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.PointOfInterest;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.UserLog;
import edu.upc.dsa.CampusMapFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/users", description = "Operaciones relacionadas con los usuarios del campus")
@Path("/users")
public class UserService {

    private CampusMapFacade campusMap = CampusMapFacade.getInstance();

    // 1. Añadir usuario
    @POST
    @ApiOperation(value = "Añadir un nuevo usuario", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario añadido exitosamente"),
            @ApiResponse(code = 400, message = "Error al añadir el usuario")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        try {
            campusMap.addUser(user);
            return Response.status(Response.Status.CREATED).entity(user).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al añadir el usuario").build();
        }
    }

    // 2. Listar usuarios
    @GET
    @ApiOperation(value = "Listar todos los usuarios", response = User.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuarios listados exitosamente")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> listUsers() {
        return campusMap.listUsers();
    }

    // 3. Consultar usuario por ID
    @GET
    @Path("/{userId}")
    @ApiOperation(value = "Consultar un usuario por ID", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario encontrado"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("userId") String userId) {
        User user = campusMap.getUser(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Usuario no encontrado").build();
        }
        return Response.ok(user).build();
    }

    // 4. Añadir punto de interés
    @POST
    @Path("/points")
    @ApiOperation(value = "Añadir un nuevo punto de interés", response = PointOfInterest.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Punto de interés añadido exitosamente"),
            @ApiResponse(code = 400, message = "Error al añadir el punto de interés")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPointOfInterest(PointOfInterest poi) {
        try {
            campusMap.addPointOfInterest(poi);
            return Response.status(Response.Status.CREATED).entity(poi).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al añadir el punto de interés").build();
        }
    }

    // 5. Registrar paso de usuario por punto de interés
    @POST
    @Path("/{userId}/visit")
    @ApiOperation(value = "Registrar visita de un usuario a un punto de interés")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Visita registrada exitosamente"),
            @ApiResponse(code = 404, message = "Usuario o punto de interés no encontrado")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logUserVisit(@PathParam("userId") String userId, UserLog log) {
        campusMap.logUserVisit(userId, log.getX(), log.getY());
        return Response.status(Response.Status.OK).build();
    }

    // 6. Consultar visitas de un usuario
    @GET
    @Path("/{userId}/visits")
    @ApiOperation(value = "Consultar visitas de un usuario", response = UserLog.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Visitas encontradas"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserVisits(@PathParam("userId") String userId) {
        List<UserLog> visits = campusMap.getUserVisits(userId);
        if (visits.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No se encontraron visitas para el usuario").build();
        }
        return Response.ok(visits).build();
    }

    // 7. Listar usuarios por punto de interés
    @GET
    @Path("/points/{x}/{y}/users")
    @ApiOperation(value = "Listar usuarios por punto de interés", response = String.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuarios encontrados para el punto de interés"),
            @ApiResponse(code = 404, message = "No se encontraron usuarios para el punto de interés")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersByPointOfInterest(@PathParam("x") int x, @PathParam("y") int y) {
        List<String> users = campusMap.getUsersByPointOfInterest(x, y);
        if (users.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No se encontraron usuarios para este punto de interés").build();
        }
        return Response.ok(users).build();
    }

    @GET
    @Path("/points/type/{type}")
    @ApiOperation(value = "Consultar puntos de interés por tipo", response = PointOfInterest.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Puntos de interés encontrados"),
            @ApiResponse(code = 404, message = "No se encontraron puntos de interés para este tipo")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPointsByType(@PathParam("type") String typeString) {
        ElementType type = null;
        try {
            // Convertir el string a mayúsculas y luego al enum
            type = ElementType.valueOf(typeString.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Si no es un valor válido de ElementType
            return Response.status(Response.Status.BAD_REQUEST).entity("Tipo no válido").build();
        }

        // Llamar al método que obtiene los puntos de interés filtrados por tipo
        List<PointOfInterest> points = campusMap.getPointsByType(type);
        if (points.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No se encontraron puntos de interés de este tipo").build();
        }
        return Response.ok(points).build();
    }


}
