package com.example.miniprojet.miniprojet.api.util;

/**
 * Copyright (C) 2016 Chaloupy Julien, Leger Loic, Magras Steeve

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along
 with this program; if not, write to the Free Software Foundation, Inc.,
 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
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
