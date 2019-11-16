package com.kroy.classes;

public class gameMap {
    private Stage[] stages;

    public gameMap(Stage[] stages) {
        this.stages = stages;
    }

    public Stage[] getStages() {
        return stages;
    }

    public void setStages(Stage[] stages) {
        this.stages = stages;
    }
}
