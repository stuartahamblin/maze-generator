package shapes;

public class hexagon extends polygon {

    private double orientationAngle;                                 // 0 <= orientation < 60

    @Override
    int findSides() {
        return 6;
    }

    @Override
    double findArea() {
        double multiplier = (3 * Math.sqrt(3)) / 2;
        double sideLength = getSideLength();
        return multiplier * Math.pow(sideLength, 2);
    }

    @Override
    double findParameter() {
        return 6 * getSideLength();
    }

    public double getOrientationAngle() {
        return orientationAngle;
    }

    public void setOrientationAngle(double angle) {
        if(angle >= 0 && angle < 60){
            this.orientationAngle = angle;
        } else {
            this.orientationAngle = 0;
        }
    }

    public double getBottomToCentroid(){                                    //need to refactor for orientation other than 0
        return (Math.sqrt(3) * super.getSideLength())/ 2;
    }

    public double getLeftToCentroid(){                                      //need to refactor for orientation other than 0
        return getSideLength();
    }

    public hexagon(){
        setCentroid(0,0,0);
    }

    public hexagon(double x, double y){
        setCentroid(x,y,0);
    }

    public hexagon(double x, double y, double z){
        setCentroid(x,y,z);
    }

    public hexagon(double x, double y, double z, double orientation, double sideLength){
        setCentroid(x,y,z);
        setOrientationAngle(orientation);
        setSideLength(sideLength);
    }
}
