// Control 1 DSA
//A fecha de 11/11/2024
// David Lamas Martínez
package edu.upc.dsa.models;

public class UserLog {
    private String userId;
    private int x;
    private int y;

    public UserLog(){
    }

    public UserLog(String userId, int x, int y) {
        this.userId = userId;
        this.x = x;
        this.y = y;
    }

    // Getters y Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
