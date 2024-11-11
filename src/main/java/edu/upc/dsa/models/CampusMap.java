// Control 1 DSA
//A fecha de 11/11/2024
// David Lamas Martínez
package edu.upc.dsa.models;

import java.util.*;
import java.util.stream.Collectors;

public class CampusMap {
    private Map<String, User> users = new HashMap<>();
    private List<PointOfInterest> pointsOfInterest = new ArrayList<>();
    private List<UserLog> userLogs = new ArrayList<>();

    // 1. Añadir usuario
    public void addUser(User user) {
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            System.out.println("Usuario ya existe.");
        }
    }

    // 2. Listar usuarios
    public List<User> listUsers() {
        return users.values().stream()
                .sorted(Comparator.comparing(User::getApellidos).thenComparing(User::getNombre))
                .collect(Collectors.toList()); // Usa Collectors.toList()
    }

    // 3. Consultar usuario
    public User getUser(String userId) {
        return users.get(userId);
    }

    // 4. Añadir punto de interés
    public void addPointOfInterest(PointOfInterest poi) {
        pointsOfInterest.add(poi);
    }

    // 5. Registrar paso de usuario
    public void logUserVisit(String userId, int x, int y) {
        if (users.containsKey(userId) && pointsOfInterest.stream().anyMatch(p -> p.getX() == x && p.getY() == y)) {
            userLogs.add(new UserLog(userId, x, y));
        } else {
            System.out.println("Error: Usuario o punto de interés no existen.");
        }
    }

    // 6. Consultar puntos de interés visitados por usuario
    public List<UserLog> getUserVisits(String userId) {
        return userLogs.stream()
                .filter(log -> log.getUserId().equals(userId))
                .collect(Collectors.toList()); // Usa Collectors.toList()
    }

    // 7. Listar usuarios por punto de interés
    public List<String> getUsersByPointOfInterest(int x, int y) {
        return userLogs.stream()
                .filter(log -> log.getX() == x && log.getY() == y)
                .map(UserLog::getUserId)
                .distinct()
                .collect(Collectors.toList()); // Usa Collectors.toList()
    }

    // 8. Consultar puntos de interés por tipo
    public List<PointOfInterest> getPointsByType(ElementType type) {
        return pointsOfInterest.stream()
                .filter(p -> p.getType() == type)
                .collect(Collectors.toList()); // Usa Collectors.toList()
    }

    public void clear() {
        this.users.clear();
        this.pointsOfInterest.clear();
        this.userLogs.clear();
    }
}
