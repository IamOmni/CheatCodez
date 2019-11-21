package com.kroy.classes;

public class gameMap {
    private Sector[] sectors;

    public gameMap(Sector[] sectors) {
        this.sectors = sectors;
    }

    public Sector[] getSectors() {
        return sectors;
    }

    public void setSectors(Sector[] sectors) {
        this.sectors = sectors;
    }
}
