// Control 1 DSA
//A fecha de 11/11/2024
// David Lamas Martínez
package edu.upc.dsa;


import edu.upc.dsa.models.User;

import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.PointOfInterest;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import static org.junit.Assert.*;

import java.util.Date;

public class CampusTest {
    private CampusMapFacade campusMap;

    @After
    public void tearDown() {
        // Limpiar el estado del Singleton después de cada prueba
        this.campusMap.clear();
    }
    @Before
    public void setUp() {
        campusMap = CampusMapFacade.getInstance();
        campusMap.addUser(new User("1", "John", "Doe", "john@example.com", new Date()));
        PointOfInterest poi = new PointOfInterest(5, 5, ElementType.TREE);
        campusMap.addPointOfInterest(poi);
    }

    @Test
    public void testAddAndGetUser() {
        User user = campusMap.getUser("1");
        assertNotNull(user);
        assertEquals("Doe", user.getApellidos());
    }
    @Test
    public void testAddUser() {
        // Crear un usuario
        String userId = "2";
        String nombre = "Juan";
        String apellidos = "Pérez";
        String email = "juan.perez@dominio.com";
        Date fechaNacimiento = new Date(); // Fecha actual

        User usuario = new User(userId, nombre, apellidos, email, fechaNacimiento);

        // Añadir el usuario
        campusMap.addUser(usuario);

        // Verificar que el usuario ha sido añadido correctamente
        User usuarioRegistrado = campusMap.getUser(userId);

        // Comprobar que los datos del usuario añadido son correctos
        assertNotNull(usuarioRegistrado); // Verificar que el usuario no es nulo
        assertEquals(userId, usuarioRegistrado.getId());
        assertEquals(nombre, usuarioRegistrado.getNombre());
        assertEquals(apellidos, usuarioRegistrado.getApellidos());
        assertEquals(email, usuarioRegistrado.getEmail());
        assertEquals(fechaNacimiento, usuarioRegistrado.getFechaNacimiento());
    }


    @Test
    public void testLogUserVisit() {
        // Añadir un usuario con id "5"
        campusMap.addUser(new User("5", "Nombre", "Apellido", "correo@dominio.com", new Date()));

        // Añadir un punto de interés en las coordenadas (5, 5)
        campusMap.addPointOfInterest(new PointOfInterest(5, 5, ElementType.DOOR));

        // Registrar que el usuario con id "5" pasa por el punto de interés (5, 5)
        campusMap.logUserVisit("5", 5, 5);

        // Verificar que el usuario con id "5" tiene 1 visita registrada
        assertEquals(1, campusMap.getUserVisits("5").size());

        // Verificar que el usuario con id "1" NO tiene visitas registradas
        assertEquals(0, campusMap.getUserVisits("1").size());
    }

    @Test
    public void testGetUsersByPointOfInterest() {
        campusMap.logUserVisit("1", 5, 5);
        assertTrue(campusMap.getUsersByPointOfInterest(5, 5).contains("1"));
    }
}
