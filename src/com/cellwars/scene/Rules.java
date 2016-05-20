package com.cellwars.scene;

import com.cellwars.actor.Map;
import javafx.scene.shape.Rectangle;

/**
 * Created by Tam�s on 2015-04-30.
 */

public class Rules {
    private Map map;
    private float cellRadius;
    private float packageRadius;

    private static Rules instance = null;

    public static void initRules(Rules rules) {
        getRules().map = rules.getMap();
        getRules().cellRadius = rules.getCellRadius();
        getRules().packageRadius = rules.getPackageRadius();
    }

    public static Rules getRules() {
        if(instance == null) {
            synchronized (Rules.class) {
                if (instance == null)
                    instance = new Rules();
            }
        }
        return instance;
    }

    public Map getMap() {
        return map;
    }

    public Rectangle getRectangleMap() {
        return new Rectangle(map.getX(), map.getY(), map.getWidth(), map.getHeight());
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setMap(double x, double y, double width, double height) {
        map.setX(x);
        map.setY(y);
        map.setWidth(width);
        map.setHeight(height);
    }

    public float getCellRadius() {
        return cellRadius;
    }

    public void setCellRadius(float cellRadius) {
        this.cellRadius = cellRadius;
    }

    public float getPackageRadius() {
        return packageRadius;
    }

    public void setPackageRadius(float packageRadius) {
        this.packageRadius = packageRadius;
    }

}

