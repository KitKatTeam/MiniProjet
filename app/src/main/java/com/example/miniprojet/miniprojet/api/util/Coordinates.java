package com.example.miniprojet.miniprojet.api.util;

/**
 * Created by Tales of symphonia on 27/01/2016.
 */
public class Coordinates {
    private final Float positionY;
    private final Float positionX;

    public Coordinates(Float positionX, Float positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;

        Coordinates that = (Coordinates) o;

        if (positionY != null ? !positionY.equals(that.positionY) : that.positionY != null)
            return false;
        return positionX != null ? positionX.equals(that.positionX) : that.positionX == null;

    }

    @Override
    public int hashCode() {
        int result = positionY != null ? positionY.hashCode() : 0;
        result = 31 * result + (positionX != null ? positionX.hashCode() : 0);
        return result;
    }
}
