package org.model.util;


public enum MapDirection {
    NORTH,
    SOUTH,
    EAST,
    WEST,
    NORTH_WEST,
    SOUTH_EAST,
    SOUTH_WEST,
    NORTH_EAST;

    public String toString(){
        return switch(this){
            case NORTH -> "N";
            case NORTH_EAST -> "NE";
            case EAST -> "E";
            case SOUTH_EAST -> "SE";
            case SOUTH -> "S";
            case SOUTH_WEST -> "SW";
            case WEST -> "W";
            case NORTH_WEST -> "NW";
        };
    }
    public MapDirection next(){
        return switch(this){
            case EAST -> SOUTH_EAST;
            case SOUTH_EAST -> SOUTH;
            case WEST -> NORTH_WEST;
            case NORTH_WEST -> NORTH;
            case NORTH -> NORTH_EAST;
            case NORTH_EAST ->  EAST;
            case SOUTH -> SOUTH_WEST;
            case SOUTH_WEST -> WEST;

        };
    }

    public static MapDirection fromIntToDirection(int x) throws IllegalArgumentException {
        return switch (x) {
            case 0 -> NORTH;
            case 1 -> NORTH_EAST;
            case 2 -> EAST;
            case 3 -> SOUTH_EAST;
            case 4 -> SOUTH;
            case 5 -> SOUTH_WEST;
            case 6 -> WEST;
            case 7 -> NORTH_WEST;
            default -> throw new IllegalArgumentException("Error - illegal num direction");
        };
    }

    public int fromDirectionToInt() {
        return switch (this) {
            case NORTH -> 0;
            case NORTH_EAST -> 1;
            case EAST -> 2;
            case SOUTH_EAST -> 3;
            case SOUTH -> 4;
            case SOUTH_WEST -> 5;
            case WEST -> 6;
            case NORTH_WEST -> 7;
        };
    }


    public Vector2d toUnitVector(){
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTH_EAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTH_EAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTH_WEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTH_WEST -> new Vector2d(-1, 1);
        };
    }


}