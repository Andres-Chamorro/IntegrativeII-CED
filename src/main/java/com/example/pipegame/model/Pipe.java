package com.example.pipegame.model;

import com.example.pipegame.ApplicationMain;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pipe {

    private final ImageView image;
    private final int row;
    private final int col;
    private final PipeType type;

   // The `public Pipe(int type, int row, int col)` constructor is initializing a `Pipe` object with
   // the given `type`, `row`, and `col` values.
    public Pipe(int type, int row, int col) {
        PipeType type1;
        ImageView image1;
        this.row = row;
        this.col = col;
        image1 = null;
        type1 = null;
        if (type != -1){
            image1 = getImageView(type);
            type1 = getPipeType(type);
        }
        this.type = type1;
        image = image1;
    }

    /**
     * The function returns an ImageView object with an image based on the given type.
     * 
     * @param type The "type" parameter is an integer that represents the type of pipe image to be
     * loaded.
     * @return The method is returning an ImageView object.
     */
    private ImageView getImageView(int type){
        Image image = new Image("file:"+ ApplicationMain.getFile("images/pipe_"+type+".png").getPath());
        return new ImageView(image);
    }

   /**
    * The function takes an integer input and returns the corresponding PipeType enum value based on
    * the input.
    * 
    * @param type An integer representing the type of pipe.
    * @return The method is returning a PipeType enum value. The specific enum value being returned
    * depends on the input parameter "type".
    */
    private PipeType getPipeType(int type){
        switch (type) {
            case 1 -> {return PipeType.VERTICAL;}
            case 2 -> {return PipeType.HORIZONTAL;}
            case 3 -> {return PipeType.ELBOW_UP_RIGHT;}
            case 4 -> {return PipeType.ELBOW_UP_LEFT;}
            case 5 -> {return PipeType.ELBOW_DOWN_RIGHT;}
            default -> {return PipeType.ELBOW_DOWN_LEFT;}
        }
    }

   /**
    * The getImage() function returns an ImageView object.
    * 
    * @return An ImageView object is being returned.
    */
    public ImageView getImage() {
        return image;
    }

    /**
     * The function returns the value of the variable "row".
     * 
     * @return The method is returning the value of the variable "row".
     */
    public int getRow() {
        return row;
    }

    /**
     * The function returns the value of the variable "col".
     * 
     * @return The method is returning the value of the variable "col".
     */
    public int getCol() {
        return col;
    }

    /**
     * The function returns the type of the pipe.
     * 
     * @return The method is returning the value of the variable "type", which is of type PipeType.
     */
    public PipeType getType() {
        return type;
    }
}