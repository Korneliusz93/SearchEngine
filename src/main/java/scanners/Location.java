package scanners;

public class Location {

    private long lineNum;

    public Location(long lineNum, String log) {
        this.lineNum = lineNum;
        this.log = log;
    }

    private String log;

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public long getLineNum() {
        return lineNum;
    }

    public void setLineNum(long lineNum) {
        this.lineNum = lineNum;
    }
}
