package org.model;

import org.model.util.Vector2d;

public abstract class WorldElement {
    protected Vector2d position;

    public Vector2d getPosition(){
        return position;
    }
}
