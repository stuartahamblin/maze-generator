package shapes;

abstract public class Polygon {

    private int sides;
    private double sideLength;
    private double positionX;
    private double positionY;
    private double positionZ;


    public double getSideLength() {
        return sideLength;
    }

    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
    }

    public void setSides(int sides) {
        this.sides = sides;
    }

    public int getSides() {
        return sides;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getPositionZ() {
        return positionZ;
    }

    public void setPositionZ(double positionZ) {
        this.positionZ = positionZ;
    }

    public double[] getCentroid() {
        return centroid;
    }

    public void setCentroid(double[] centroid) {
        this.centroid = centroid;
    }

    public void setCentroid(double x, double y){
        this.positionX = x;
        this.positionY = y;
        this.positionZ = 0;
    }

    public void setCentroid(double x,double y, double z){
        this.positionX = x;
        this.positionY = y;
        this.positionZ = z;
    }

    private double[] centroid = {positionX, positionY, positionZ};

    abstract int findSides();

    abstract double findArea();

    abstract double findParameter();
}