package Main;

public class RankItem {
    private Integer pos;
    private String name;
    private String time;

    public static final String[] rankItemName = {
            "pos", "name", "time"
    };

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private final static long hour = 36000;
    private final static long minute = 600;

    public void setTimeByGrade(long grade) {
        String prefix = "";
        String suffix = "";
        String end = "S";
        if (grade < minute) {
            prefix = "" + grade / 10;
            suffix = "" + grade % 10;
        } else {
            if (grade < hour) {
                prefix = "" + grade / minute;
                suffix = "" + grade % minute / (minute / 10);
                end = "M";
            } else {
                prefix = "" + grade / hour;
                suffix = "" + grade % hour / (hour / 10);
                end = "H";
            }
        }
        this.setTime(prefix + "." + suffix + end);
    }
}
