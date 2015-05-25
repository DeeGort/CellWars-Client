package com.cellwars.actor;

import com.cellwars.scene.Rules;
import com.sun.javafx.geom.Vec2d;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Created by Tamás on 2015-04-30.
 */
public class Cell extends AElement {

    public Cell(Vec2d position) {
        super(position);
        body.setRadius(Rules.CELLRADIUS);
    }
}
