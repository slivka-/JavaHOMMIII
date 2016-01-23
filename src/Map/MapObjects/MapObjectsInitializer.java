package Map.MapObjects;

import GraphicsProcessing.Graphics;

import java.io.File;

public class MapObjectsInitializer {

    private Graphics g;
    //private File townsFolder = new File("assets\\towns");


    public MapObjectsInitializer(Graphics g) {
        this.g = g;
    }

    public void initializeObjects() {
        initializeTowns();
        initializeMines();
        initializeArmies();
        initializeMiscellaneous();
        initializeResources();
    }

    private void initializeTowns() {
        //g.getImagesByFolderName("towns");
    }

    private void initializeMines() {

    }

    private void initializeArmies() {

    }

    private void initializeMiscellaneous() {

    }

    private void initializeResources() {

    }
}
