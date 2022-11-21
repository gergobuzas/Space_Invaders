package BoardObjects;

import Controller.Constants;

import java.awt.*;

/**
 * This class is the parent class for all objects in the game.
 * It contains the basic attributes of all objects in the game.
 * It also contains the basic methods for all objects in the game.
 * @author Gergo Buzas
 */
public class  BoardObject {
    int x;
    int y;
    Image img;
    Boolean destroyed;

    /**
     * This method is the constructor of the class.
     * It initializes the attributes of the class.
     */
    public BoardObject(){
        destroyed = false;
    }

    /**
     * This method moves the object to the left.
     */
    public void moveLeft() {
        moveX(Constants.MOVE_LEFT);
    }

    /**
     * This method moves the object to the right.
     */
    public void moveRight() {
        moveX(Constants.MOVE_RIGHT);
    }

    /**
     * This method moves the object up.
     */
    public void moveUp(){
        moveY(Constants.MOVE_UP);
    }

    /**
     * This method moves the object down.
     */
    public void moveDown(){
        moveY(Constants.MOVE_DOWN);
    }

    /**
     * This method sets the destroyed atrribute to true.
     */
    public void die(){
        destroyed = true;
    }

    /**
     * This method returns the destroyed attribute of the object.
     * @return Whether the object is dead or not.
     */
    public boolean getDestroyed(){
        return destroyed;
    }

    /**
     * This method sets the destroyed attribute of the object.
     * @param bool Whether the object is dead or not.
     */
    public void setDestroyed(boolean bool){
        this.destroyed = bool;
    }

    /**
     * This method sets an image to the object.
     * @param image The image to be set.
     */
    public void setImage(Image image) {
        this.img = image;
    }

    /**
     * This method returns the image of the object.
     * @return The image of the object.
     */
    public Image getImage() {
        return img;
    }

    /**
     * This method moves the object on the x-axis.
     * @param num The amount of pixels to move the object.
     */
    public void moveX(int num){
        this.x += num;
    }

    /**
     * This method moves the object on the y-axis.
     * @param num The amount of pixels to move the object.
     */
    public void moveY(int num){
        this.y += num;
    }

    /**
     * This method returns the x-coordinate of the object.
     * @return The x-coordinate of the object.
     */
    public int getX() {
        return x;
    }

    /**
     * This method returns the y-coordinate of the object.
     * @return The y-coordinate of the object.
     */
    public int getY() {
        return y;
    }

    /**
     * This method sets the x-coordinate of the object.
     * @param x The x-coordinate of the object.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * This method sets the y-coordinate of the object.
     * @param y The y-coordinate of the object.
     */
    public void setY(int y) {
        this.y = y;
    }

}
