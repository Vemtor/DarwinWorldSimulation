package org.model.util;
import org.model.WorldElement;

import java.util.Objects;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Vector2d{
    private final int x;
    private final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;

    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public String toString(){
        return "(%s,%s)".formatted(String.valueOf(x), String.valueOf(y));
    }

    public boolean precedes(Vector2d other){
        return this.x<=other.x && this.y<=other.y;

    }

    public boolean follows(Vector2d other){
        return this.x>=other.x && this.y>=other.y;
    }

    public Vector2d add(Vector2d other){
        int new_x = this.x + other.x;
        int new_y = this.y + other.y;
        return new Vector2d(new_x,new_y);
    }

    public Vector2d subtract(Vector2d other){
        int new_x = -(other.x - this.x);
        int new_y = -(other.y - this.y);
        return new Vector2d(new_x,new_y);
    }

    public Vector2d upperRight(Vector2d other){
        int new_x = max(this.x,other.x);
        int new_y = max(this.y, other.y);
        return new Vector2d(new_x,new_y);
    }

    public Vector2d lowerLeft(Vector2d other){
        int new_x = min(this.x,other.x);
        int new_y = min(this.y, other.y);
        return new Vector2d(new_x,new_y);
    }

    public Vector2d opposite(){
        int new_x = -this.x;
        int new_y = -this.y;

        return new Vector2d(new_x,new_y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static void main(String[] args){
        Vector2d wektor = new Vector2d(3,4);
        Vector2d other = new Vector2d(2,3);
        Vector2d wektor_added = wektor.add(other);
        System.out.println(wektor_added.toString());
        System.out.println(wektor.equals(wektor));

    }

}