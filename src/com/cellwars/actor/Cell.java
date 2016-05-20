package com.cellwars.actor;

import com.cellwars.scene.Rules;
import com.sun.javafx.geom.Vec2d;

/**
 * Created by Tam�s on 2015-04-30.
 */
public class Cell extends AElement {

    public Cell(Vec2d position) {
        super(position);
        body.setRadius(Rules.getRules().getCellRadius());
    }
}
