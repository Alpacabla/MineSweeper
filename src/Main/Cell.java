package Main;


import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Cell extends Button {

    private boolean isFlag;
    private boolean isMine;
    private int counts;
    private boolean isOpened;
    private boolean isFailure;

    private int x;
    private int y;

    public Cell(int x,int y) {
        this.x = x;
        this.y = y;
        this.isFlag = false;
        this.isMine = false;
        this.counts = 0;
        this.isOpened = false;
        this.isFailure = false;
        //this.addEventHandler(MouseEvent.MOUSE_CLICKED,eventEventHandler);
    }

    private final static EventHandler<MouseEvent> eventEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Cell cell = (Cell) event.getSource();
            MouseButton mouseButton = event.getButton();
            switch (mouseButton) {
                case PRIMARY:       //鼠标左键事件
                    cell.isOpened = true;
                    cell.setStyle("-fx-base:grey");
                    if (cell.isMine) {
                        cell.isFailure = true;
                    } else {
                        if (cell.counts > 0) {
                            cell.setText("" + cell.counts);
                        }

                    }
                    break;
                case SECONDARY:     //鼠标右键事件
                    if (cell.isOpened) {
                        cell.isFlag = true;
                        cell.setText("旗");
                    }
                    break;
            }
        }
    };

    public void sweep() {
        this.setOpened(true);
        this.setStyle("-fx-base:grey");
        if (this.getCounts() > 0) {
            this.setText("" + this.getCounts());
        }
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

    public void turnChooseBomb() {
        this.setText("雷");
        this.setStyle("-fx-base:red");
    }

    public void turnBomb() {
        this.setText("雷");
    }
}
