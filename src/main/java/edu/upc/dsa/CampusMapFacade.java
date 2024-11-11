// Control 1 DSA
//A fecha de 11/11/2024
// David Lamas Martínez
package edu.upc.dsa;

import edu.upc.dsa.models.CampusMap;
import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.PointOfInterest;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.UserLog;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class CampusMapFacade implements CampusMapInterface {
    private static final Logger logger = Logger.getLogger(CampusMapFacade.class);
    private static CampusMapFacade instance;

    private Map<String, User> users = new HashMap<>();
    private List<PointOfInterest> pointsOfInterest = new ArrayList<>();
    private List<UserLog> userLogs = new ArrayList<>();

    // Hacer que la clase sea un Singleton
    private CampusMapFacade() {}

    public static synchronized CampusMapFacade getInstance() {
        if (instance == null) {
            instance = new CampusMapFacade();
        }
        return instance;
    }

    @Override
    public void addUser(User user) {
        logger.info("Inicio addUser - id: " + user.getId());
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            logger.info("Usuario añadido: " + user.getId());
        } else {
            logger.error("Usuario con id " + user.getId() + " ya existe.");
        }
        logger.info("Fin addUser");
    }

    @Override
    public List<User> listUsers() {
        logger.info("Inicio listUsers");
        List<User> sortedUsers = users.values().stream()
                .sorted(Comparator.comparing(User::getApellidos).thenComparing(User::getNombre))
                .collect(Collectors.toList());
        logger.info("Fin listUsers");
        return sortedUsers;
    }

    @Override
    public User getUser(String userId) {
        logger.info("Inicio getUser - id: " + userId);
        User user = users.get(userId);
        if (user == null) {
            logger.error("Usuario con id " + userId + " no encontrado.");
        }
        logger.info("Fin getUser");
        return user;
    }

    @Override
    public void addPointOfInterest(PointOfInterest poi) {
        logger.info("Inicio addPointOfInterest - Coordenadas: (" + poi.getX() + ", " + poi.getY() + ")");
        pointsOfInterest.add(poi);
        logger.info("Punto de interés añadido en: (" + poi.getX() + ", " + poi.getY() + ")");
        logger.info("Fin addPointOfInterest");
    }

    @Override
    public void logUserVisit(String userId, int x, int y) {
        logger.info("Inicio logUserVisit - id usuario: " + userId + ", Coordenadas: (" + x + ", " + y + ")");
        if (users.containsKey(userId) && pointsOfInterest.stream().anyMatch(p -> p.getX() == x && p.getY() == y)) {
            userLogs.add(new UserLog(userId, x, y));
            logger.info("Paso registrado para usuario: " + userId + " en (" + x + ", " + y + ")");
        } else {
            logger.error("Error: Usuario o punto de interés no existen.");
        }
        logger.info("Fin logUserVisit");
    }

    @Override
    public List<UserLog> getUserVisits(String userId) {
        logger.info("Inicio getUserVisits - id usuario: " + userId);
        List<UserLog> visits = userLogs.stream()
                .filter(log -> log.getUserId().equals(userId))
                .collect(Collectors.toList());
        logger.info("Fin getUserVisits");
        return visits;
    }

    @Override
    public List<String> getUsersByPointOfInterest(int x, int y) {
        logger.info("Inicio getUsersByPointOfInterest - Coordenadas: (" + x + ", " + y + ")");
        List<String> usersByPOI = userLogs.stream()
                .filter(log -> log.getX() == x && log.getY() == y)
                .map(UserLog::getUserId)
                .distinct()
                .collect(Collectors.toList());
        logger.info("Fin getUsersByPointOfInterest");
        return usersByPOI;
    }

    @Override
    public List<PointOfInterest> getPointsByType(ElementType type) {
        logger.info("Inicio getPointsByType - Tipo: " + type);
        List<PointOfInterest> pointsByType = pointsOfInterest.stream()
                .filter(p -> p.getType() == type)
                .collect(Collectors.toList());
        logger.info("Fin getPointsByType");
        return pointsByType;
    }

    public void clear() {
        this.userLogs.clear();
        this.users.clear();
        this.pointsOfInterest.clear();
    }

}
