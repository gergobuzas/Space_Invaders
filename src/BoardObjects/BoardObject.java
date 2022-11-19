package BoardObjects;

import Controller.Constans;

import java.awt.*;

public class  BoardObject {
    int x;
    int y;
    Image img;
    Boolean destroyed;


    public BoardObject(){
        destroyed = false;
    }

    public void moveLeft() {
        moveX(Constans.MOVE_LEFT);
    }

    public void moveRight() {
        moveX(Constans.MOVE_RIGHT);
    }

    public void moveUp(){
        moveY(Constans.MOVE_UP);
    }

    public void moveDown(){
        moveY(Constans.MOVE_DOWN);
    }

    public void die(){
        destroyed = true;
    }

    public boolean getDestroyed(){
        return destroyed;
    }

    public void setDestroyed(boolean bool){
        this.destroyed = bool;
    }


    public void setImage(Image image) {

        this.img = image;
    }

    public Image getImage() {

        return img;
    }

    public void moveX(int num){
        this.x += num;
    }

    public void moveY(int num){
        this.y += num;
    }

    //getter for X
    public int getX() {
        return x;
    }

    // getter for Y getY
    public int getY() {
        return y;
    }

    // setter for X
    public void setX(int x) {
        this.x = x;
    }

    // setter for Y
    public void setY(int y) {
        this.y = y;
    }

}
