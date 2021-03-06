package Main;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class Timer extends Label {

    private final long limit = 360000;
    private long time = 0;

    Thread timer = null;
    private boolean status = true;

    public void start() {
        this.setWidth(100);
        this.setHeight(100);
        this.setAlignment(Pos.TOP_CENTER);
        this.setStyle("-fx-font: bold;-fx-font-size: 27");
        time = 0;
        final long[] lastSecond = {10};
        this.setText(String.valueOf(0));
        timer = new Thread(() -> {
            try {

                //必须要用platform.runlater来执行，否则会出现线程错误

                Timer timer = this;
                while (status) {
                    time++;
                    Thread.sleep(100);
                    if (time >= lastSecond[0]) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                timer.setText(new String(String.valueOf(time / 10)));
                            }
                        });
                        lastSecond[0] += 10l;
                    }
                    if (time >= limit) {
                        stop();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        timer.start();
    }

    //就算有多线程风险也没关系，总之只差了几毫秒
    public void stop() {
        status = false;
    }

    public long getTime() {
        return time;
    }
}
