package Main;


import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cell extends Button {

    private boolean isMine;
    private int counts;
    private boolean isOpened;
    private int flagStatus;

    private int x;
    private int y;

    public static int cellWidth;
    public static int cellHeight;
    private final static double imageResize = 0.6;
    private static int imageWidth;
    private static int imageHeight;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.isMine = false;
        this.counts = 0;
        this.isOpened = false;
        this.flagStatus = 0;
        this.setPrefWidth(Cell.cellWidth);
        this.setPrefHeight(Cell.cellHeight);
    }

    public static void setAppearance(int width, int height) {
        Cell.cellWidth = width;
        Cell.cellHeight = height;
        Cell.imageWidth = (int)Math.min(width - 20, Cell.cellWidth * imageResize);
        Cell.imageHeight = (int)Math.min(height - 20, Cell.cellHeight * imageResize);
    }

    public void sweep() {
        this.setOpened(true);
        this.setStyle("-fx-base:DarkSlateGray");
        if (this.getCounts() > 0) {
            this.setText("" + this.getCounts());
        }
        this.setFlagStatus(0);
    }

    public void flip() {
        this.setOpened(false);
        this.setStyle("");
        this.setText("");
        this.setGraphic(null);
        this.setFlagStatus(0);
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getFlagStatus() {
        return flagStatus;
    }

    public void setFlagStatus(int flagStatus) {
        this.flagStatus = flagStatus;
    }

    public void turnChooseBomb() {
        this.setGraphic(new ImageView(new Image("/ImageSrc/Exploded.png",imageWidth,imageHeight,false,true)));
        //this.setStyle("-fx-base:red");
    }

    public void turnBomb() {
        this.setGraphic(new ImageView(new Image("/ImageSrc/Mine.png",imageWidth,imageHeight,false,true)));
    }

    public void turnFlagStatus() {
        this.flagStatus++;
        this.flagStatus %= 3;
        switch (this.flagStatus) {
            case 0:
                this.setGraphic(null);
                break;
            case 1:
                this.setGraphic(new ImageView(new Image("/ImageSrc/Flag.png",imageWidth,imageHeight,false,true)));
                break;
            case 2:
                this.setGraphic(new ImageView(new Image("/ImageSrc/FlagQuestion.png",imageWidth,imageHeight,false,true)));
                break;
        }
    }

    public boolean isFlag() {
        return this.flagStatus == 1;
    }
}
