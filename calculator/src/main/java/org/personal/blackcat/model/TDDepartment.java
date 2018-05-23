package org.personal.blackcat.model;

/**
 * a Two-dimensional coordinate department model
 * @author lifan.li
 * @since 2018/5/23
 **/
public class TDDepartment {

    private double x;

    private double y;

    public TDDepartment() {
    }

    public TDDepartment(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
