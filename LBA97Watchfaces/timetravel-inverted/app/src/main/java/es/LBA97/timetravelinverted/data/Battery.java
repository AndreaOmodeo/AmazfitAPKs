package es.LBA97.timetravelinverted.data;

/**
 * Battery data.
 */

public class Battery {

    private final int level;
    private final int scale;

    public Battery(int level, int scale) {
        this.level = level;
        this.scale = scale;
    }

    public int getLevel() {
        return level;
    }

    public int getScale() {
        return scale;
    }

}
