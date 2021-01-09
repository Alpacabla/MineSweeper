package Main;

public class EasyTransFromWindow {

    //实现窗口之间传递数据

    //游戏类型传递x,y,counts
    public static int xRange;
    public static int yRange;
    public static int counts;
    public static GAMETYPE gameType;

    public enum GAMETYPE {
        EASY, MEDIUM, HARD, NONE
    }

    //登录的用户名
    public static String name;

}
