// Control 1 DSA
//A fecha de 11/11/2024
// David Lamas Martínez
package edu.upc.dsa;


import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.PointOfInterest;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.UserLog;

import java.util.List;

public interface CampusMapInterface {
    void addUser(User user);
    List<User> listUsers();
    User getUser(String userId);
    void addPointOfInterest(PointOfInterest poi);
    void logUserVisit(String userId, int x, int y);
    List<UserLog> getUserVisits(String userId);
    List<String> getUsersByPointOfInterest(int x, int y);
    List<PointOfInterest> getPointsByType(ElementType type);
}

