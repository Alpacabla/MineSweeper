package Main;

import Main.EasyTransFromWindow.GAMETYPE;
import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class Game implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private FlowPane mineField;

    private Timer timer;

    private ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>();
    private ArrayList<Cell> mines = new ArrayList<Cell>();

    private String name = null;
    private GAMETYPE gameType = GAMETYPE.EASY;
    private int xRange = 8;
    private int yRange = 8;
    private int bombCounts = 10;
    private int cellCounts = xRange * yRange;

    private final static int[][] director8 = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };

    public void openRank() {
        new Utils().openRank();
    }

    public void reStart() {
        timer.stop();
        new Utils().changeStage(FXRobotHelper.getStages().get(0), "../Fx/Game.fxml");
    }

    private final EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Cell cell = null;
            try {
                cell = (Cell) event.getTarget();
                MouseButton mouseButton = event.getButton();
                int x = cell.getX(), y = cell.getY();
                switch (mouseButton) {
                    case PRIMARY:       //鼠标左键事件
                        if (!cell.isOpened()) {
                            if (cell.isFlag()) {
                                cell.flip();
                            } else {
                                if (cell.isMine()) {
                                    failure(x, y);
                                } else {
                                    cellCounts -= dfsSweep(x, y);
                                }
                            }
                        } else {
                            if (cell.getCounts() != 0) {
                                //当已经被打开了就使用自动打开，如果有错误的旗子就游戏结束
                                Cell escapeMine = null;
                                if ((escapeMine = autoOpen(x, y, cell.getCounts())) != null) {
                                    failure(escapeMine.getX(), escapeMine.getY());
                                }
                            }
                        }
                        break;
                    case SECONDARY:     //鼠标右键事件
                        if (!cell.isOpened()) {
                            cell.turnFlagStatus();
                        }
                        break;
                }
                if (cellCounts == bombCounts) {
                    succeed();
                }
            } catch (ClassCastException e) {
                //点到按钮中的图片或者文字时进入exception，把事件给cell，接着就会被flowPane监听到并且给cell本身处理
                ((Node) event.getTarget()).getParent().fireEvent(event);
            }
        }
    };

    //因为使用了fxml样式，所以初始化要是用这个方法，不然会出现多线程问题
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.cells.clear();

        this.name = EasyTransFromWindow.name;
        this.gameType = EasyTransFromWindow.gameType;
        this.xRange = EasyTransFromWindow.xRange;
        this.yRange = EasyTransFromWindow.yRange;
        this.bombCounts = EasyTransFromWindow.counts;
        this.cellCounts = this.xRange * this.yRange;

        setCellsAppearance();

        borderPane.setPrefWidth(this.xRange * Cell.cellWidth);
        borderPane.setPrefHeight(this.yRange * Cell.cellHeight + 100.0);

        mineField.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

        timer = new Timer();
        timer.start();
        borderPane.setTop(timer);

        creatMineField();

        //设置关闭事件，返回上一个窗口
        ObservableList<Stage> stages = FXRobotHelper.getStages();
        stages.get(0).setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                timer.stop();
                new Utils().newWindow("扫雷", "../Fx/Greeting.fxml");
            }
        });
    }

    private void setCellsAppearance() {
        int maxRange = Math.max(xRange, yRange);
        final int maxWidth = 50, maxHeight = 50;
        final int minWidth = 35, minHeight = 35;
        if (maxRange <= 10) {
            Cell.setAppearance(maxWidth, maxWidth);
        } else {
            int width = (int) Math.max(minWidth, 10.0 / maxRange * maxWidth);
            int height = (int) Math.max(minHeight, 10.0 / maxRange * maxHeight);
            Cell.setAppearance(width, height);
        }
    }

    private void creatMineField() {
        for (int i = 0; i < xRange; i++) {
            ArrayList<Cell> cellArr = new ArrayList<Cell>();
            for (int j = 0; j < yRange; j++) {
                cellArr.add(new Cell(i, j));
            }
            cells.add(cellArr);
        }

        for (ArrayList<Cell> cellArr : cells) {
            for (Cell cell : cellArr) {
                mineField.getChildren().add(cell);
            }
        }

        Random random = new Random();
        for (int i = 1; i <= bombCounts; i++) {
            int x = random.nextInt(this.xRange);
            int y = random.nextInt(this.yRange);
            while (cells.get(x).get(y).isMine()) {
                x = random.nextInt(this.xRange);
                y = random.nextInt(this.yRange);
            }
            cells.get(x).get(y).setMine(true);
            mines.add(cells.get(x).get(y));
        }

        for (int i = 0; i < xRange; i++) {
            for (int j = 0; j < yRange; j++) {
                int count = 0;
                for (int k = 0; k < 8; k++) {
                    int x = i + director8[k][0], y = j + director8[k][1];
                    if (judge(x, y) && cells.get(x).get(y).isMine()) {
                        count++;
                    }
                }
                cells.get(i).get(j).setCounts(count);
            }
        }
    }

    private boolean judge(int x, int y) {
        return (x >= 0 && x < this.xRange) && (y >= 0 && y < this.yRange);
    }

    private Cell autoOpen(int x, int y, int correctCount) {
        int count = 0;
        Cell escapeMine = null;
        for (int[] i : director8) {
            int xx = x + i[0], yy = y + i[1];
            if (judge(xx, yy)) {
                if (cells.get(xx).get(yy).isFlag()) {
                    count++;
                } else {
                    if (cells.get(xx).get(yy).isMine()) {
                        escapeMine = cells.get(xx).get(yy);
                    }
                }
            }
        }

        if (count == correctCount) {
            if (escapeMine == null) {
                for (int[] i : director8) {
                    int xx = x + i[0], yy = y + i[1];
                    if (judge(xx, yy) && !cells.get(xx).get(yy).isFlag()) {
                        this.cellCounts -= dfsSweep(xx, yy);
                    }
                }
            }
            return escapeMine;
        } else {
            return null;
        }
    }


    private int dfsSweep(int x, int y) {
        if (!judge(x, y)) return 0;
        Cell cell = cells.get(x).get(y);
        if (cell.isMine() || cell.isOpened() || cell.isFlag()) return 0;
        int count = 0;
        cell.sweep();
        count++;
        if (cell.getCounts() > 0) return count;
        for (int[] i : director8) {
            int xx = x + i[0], yy = y + i[1];
            count += dfsSweep(xx, yy);
        }
        return count;
    }

    private void flipAll() {
        for (ArrayList<Cell> cellArr : cells) {
            for (Cell cell : cellArr) {
                cell.flip();
            }
        }
        this.cellCounts = this.xRange * this.yRange;
        this.gameType = GAMETYPE.NONE;
    }

    private void succeed() {
        timer.stop();
        long grade = timer.getTime();
        String result = "" + (timer.getTime() / 10) + "." + (timer.getTime() % 10);
        mineField.removeEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

        if (gameType != GAMETYPE.NONE) {
            if (new Utils().writeAGrade(name, gameType, grade) == true) {
                new Utils().showAnAlert(Alert.AlertType.INFORMATION,
                        "胜利!", "扫雷成功，你的耗时为：" + result + "秒" +
                                "\n突破历史记录！");
            } else {
                new Utils().showAnAlert(Alert.AlertType.INFORMATION,
                        "胜利!", "扫雷成功，你的耗时为：" + result + "秒" +
                                "\n并没有突破历史记录。呜呜呜。");
            }
        } else {
            new Utils().showAnAlert(Alert.AlertType.INFORMATION,
                    "胜利!", "扫雷成功，你的耗时为：" + result + "秒" +
                            "\n本次不计入成绩");
        }

    }

    private void failure(int x, int y) {
        cells.get(x).get(y).turnChooseBomb();
        for (Cell mine : mines) {
            if (mine.getX() != x || mine.getY() != y) {
                mine.turnBomb();
            }
        }
        if (new Utils().showAnConfirm("游戏结束", "你想要重新开始吗?\n当前情况重新开始不会计分，雷与上一次位置相同。") == true) {
            //按当前情况重新开始
            flipAll();
        } else {
            //取消时去除事件监听
            mineField.removeEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        }
    }
}

