import shapes.Hexagon;

public class HexCel extends Hexagon {
    private char upWall = '0';
    private char upLeftWall = '0';
    private char downLeftWall = '0';
    private char downWall = '0';
    private char downRightWall = '0';
    private char upRightWall = '0';

    public char getUpWall() {
        return upWall;
    }

    public void setUpWall(char upWall) {
        this.upWall = upWall;
    }

    public char getUpLeftWall() {
        return upLeftWall;
    }

    public void setUpLeftWall(char upLeftWall) {
        this.upLeftWall = upLeftWall;
    }

    public char getDownLeftWall() {
        return downLeftWall;
    }

    public void setDownLeftWall(char downLeftWall) {
        this.downLeftWall = downLeftWall;
    }

    public char getDownWall() {
        return downWall;
    }

    public void setDownWall(char downWall) {
        this.downWall = downWall;
    }

    public char getDownRightWall() {
        return downRightWall;
    }

    public void setDownRightWall(char downRightWall) {
        this.downRightWall = downRightWall;
    }

    public char getUpRightWall() {
        return upRightWall;
    }

    public void setUpRightWall(char upRightWall) {
        this.upRightWall = upRightWall;
    }

    public HexCel(){
        super(0, 0, 0);
    }

    public HexCel(double x, double y, double z, double sideLength){
        super(x, y, z, 0, sideLength);
        this.upLeftWall = '1';
        this.downLeftWall = '1';
        this.downWall = '1';
    }

    public HexCel(double x, double y, double z, double orientation, double sideLength){
        super(x, y, z, orientation, sideLength);
        this.upLeftWall = '1';
        this.downLeftWall = '1';
        this.downWall = '1';
    }

    public HexCel(double x, double y, double z, double orientation, double sideLength, char upWall, char upLeftWall, char downLeftWall, char downWall, char downRightWall, char upRightWall) {
        super(x, y, z, orientation, sideLength);
        this.upWall = upWall;
        this.upLeftWall = upLeftWall;
        this.downLeftWall = downLeftWall;
        this.downWall = downWall;
        this.downRightWall = downRightWall;
        this.upRightWall = upRightWall;
    }

}