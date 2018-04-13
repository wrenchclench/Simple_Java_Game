package main;

import java.awt.*;
import java.util.LinkedList;

// Updates every object in the game and renders them
public class Handler {

    LinkedList<GameObject> object = new LinkedList<GameObject>();

    //Tick for every object in the linkedlist
    public void tick(){
        for (int i= 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);

            tempObject.tick();
        }

    }
    //Render every object in the linkedlist
    public void render(Graphics g){
        for (int i= 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);

            tempObject.render(g);
        }

    }

    public void addObject(GameObject object){
        this.object.add(object);
    }

    public void removeObject(GameObject obejct){
        this.object.remove(obejct);
    }

}
