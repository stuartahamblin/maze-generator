import shapes.Hexagon;
import view.TextRender;

import java.util.ArrayList;
import java.util.List;

public class Maze {

    private List<List<HexCel>> hexLayout;

    public List<List<HexCel>> getHexLayout() {
        return hexLayout;
    }

    public void setHexLayout(List<List<HexCel>> hexLayout) {
        this.hexLayout = hexLayout;
    }

    public int shiftZeroUp(String[] layout){
        int outputShiftUp = layout[layout.length-1].length()-1;
        int lowestY = layout.length-1;
        int lowestX = layout[lowestY].length()-1;
        for(int y = layout.length-2; y >= 0 ; y--){
            int x = layout[y].length()-1;
            if(lowestX + 2*(lowestY - y) < x){
                outputShiftUp += x - lowestX - 2*(lowestY - y);
                lowestY = y;
                lowestX = x;
            }
        }
        return outputShiftUp;
    }

    public void initializeMaze(String[] layout, double sideLength){
        int shiftUp = shiftZeroUp(layout);
        double bottomToCentroid = Hexagon.getBottomToCentroid(sideLength);
        double leftToCentroid = Hexagon.getLeftToCentroid(sideLength);
        List<List<HexCel>> hL = new ArrayList<>();
        for(int i = 0 ; i <= layout.length-1; i++){                                         //ROWS
            var buildArray = new ArrayList<HexCel>();
            for(int j = 0 ; j < layout[i].length() ; j++){                                  //COLUMNS
                if(layout[i].charAt(j) == '1'){
                    double x = (2*j+1)*leftToCentroid;
                    double y = (shiftUp + ((2*(layout.length - i) -1) - j))*bottomToCentroid;
                    HexCel buildCel = new HexCel(x, y, 0, sideLength);
                    if(j < layout[i].length()-1){                                           //build wall if next item is not '1'
                        if(layout[i].charAt(j+1) != '1'){
                            buildCel.setDownRightWall('1');
                        }
                    }
                    if(j == layout[i].length()-1){                                          //build wall if last in a row
                        buildCel.setDownRightWall('1');
                    }
                    if(i == 0){                                                             //build walls if very top row
                        buildCel.setUpWall('1');
                        buildCel.setUpRightWall('1');
                    }
                    if(i != 0){
                        if(hL.get(i-1).size() <= j+1){                            //build wall if upright index does not exist
                            buildCel.setUpRightWall('1');
                        }
                        if(hL.get(i-1).size() <= j){                              //build wall if above index does not exist
                            buildCel.setUpWall('1');
                        }
                        if(hL.get(i-1).size() > j){                               //build wall if above index is null
                            if(hL.get(i-1).get(j) == null){
                                buildCel.setUpWall('1');
                            }
                        }
                        if(hL.get(i-1).size() > j+1){                             //build wall if upright index is null
                            if(hL.get(i-1).get(j+1) == null){
                                    buildCel.setUpRightWall('1');
                                }
                        }
                    }
                    buildArray.add(buildCel);
                } else {
                    buildArray.add(null);
                }
            }
            hL.add(buildArray);
        }
        setHexLayout(hL);
    }


    public void checkRenderValidInputs(){
        String[] sides = {"U","UL","DL","D","DR","UR"};
        List<List<HexCel>> hL = getHexLayout();
        StringBuilder sB = new StringBuilder();
        for(int w = 0 ; w < sides.length ; w++){
            System.out.println("CHECK " + sides[w] + " values");
            for(int i = 0 ; i < hL.size() ; i++){
                for(int j = 0 ; j < hL.get(i).size() ; j++) {
                    if(hL.get(i).get(j) != null){
                        sB.append(renderTextMazeWall(i,j,sides[w]));
                        sB.append(", ");
                    } else if (hL.get(i).get(j) == null) {
                        sB.append(TextRender.renderWall(sides[w], '0'));
                        sB.append("  ");
                    }
                }
                System.out.println(sB);
                sB.delete(0,sB.length());
            }
        }
    }



    static void wilsonAlgorithm(){

    }

    public void renderQuickLayoutText(){
        List<List<HexCel>> objectLists = getHexLayout();
        for(int i = 0 ; i < objectLists.size() ; i++){
            StringBuilder buildLine0 = new StringBuilder();
            for(int j = 0 ; j < objectLists.get(i).size() ; j++){
                if(objectLists.get(i).get(j) == null){
                    buildLine0.append(TextRender.renderWall("U", '0'));                         //
                } else {
                    buildLine0.append(TextRender.renderWall("U", objectLists.get(i).get(j).getUpWall()));
                }
                buildLine0.append(" ");
            }
            System.out.println(buildLine0);
            StringBuilder buildLine1 = new StringBuilder();
            for(int k = 0 ; k < objectLists.get(i).size() ; k++){
                if(objectLists.get(i).get(k) == null){
                    buildLine1.append(TextRender.renderWall("UL", '0'));
                    buildLine1.append(TextRender.renderWall("UR", '0'));
                } else {
                    buildLine1.append(TextRender.renderWall("UL", objectLists.get(i).get(k).getUpLeftWall()));
                    buildLine1.append(TextRender.renderWall("UR", objectLists.get(i).get(k).getUpRightWall()));
                }
                buildLine1.append(" ");
            }
            System.out.println(buildLine1);
            StringBuilder buildLine2 = new StringBuilder();
            for(int l = 0 ; l < objectLists.get(i).size() ; l++){
                if(objectLists.get(i).get(l) == null){
                    buildLine2.append(TextRender.renderWall("DL", '0'));
                    buildLine2.append(TextRender.renderWall("D", '0'));
                    buildLine2.append(TextRender.renderWall("DR", '0'));
                } else {
                    buildLine2.append(TextRender.renderWall("DL", objectLists.get(i).get(l).getDownLeftWall()));
                    buildLine2.append(TextRender.renderWall("D", objectLists.get(i).get(l).getDownWall()));
                    buildLine2.append(TextRender.renderWall("DR", objectLists.get(i).get(l).getDownRightWall()));
                }
                buildLine2.append(" ");
            }
//                System.out.println("row " + row + ", col " + col);
            System.out.println(buildLine2);
        }
    }

    public String renderTextMazeWall(int row, int col, String side){
        String wallCheck = "";
        List<List<HexCel>> hL = getHexLayout();
        int maxRow = hL.size()-1;
        int maxCol = hL.get(row).size()-1;
        switch(side){
            case "U":
                wallCheck += hL.get(row).get(col).getUpWall();
                if(row != 0){                                                               //omit if row 0
                    if(hL.get(row-1).size() > col){                                         //omit if compare !exist
                        if (hL.get(row - 1).get(col) != null) {                             //omit if compare null
                            wallCheck += hL.get(row - 1).get(col).getDownWall();
                        }
                    }
                }
                break;
            case "UL":
                wallCheck += hL.get(row).get(col).getUpLeftWall();
                if(col != 0){                                                               //omit if row 0
                    if(hL.get(row).get(col-1) != null){                                     //omit if compare null
                        wallCheck += hL.get(row).get(col-1).getDownRightWall();             //!exist na
                    }
                }
                break;
            case "DL":
                wallCheck += hL.get(row).get(col).getDownLeftWall();
                if(row != maxRow && col != 0){                                              //omit if last row or first col
                    if(hL.get(row+1).size() >= col){                                        //omit if compare !exist
                        if(hL.get(row+1).get(col-1) != null){                               //omit if compare null
                            wallCheck += hL.get(row+1).get(col-1).getUpRightWall();
                        }
                    }
                }
                break;
            case "D":
                wallCheck += hL.get(row).get(col).getDownWall();
                if(row != maxRow){                                                          //omit if last row
                    if(hL.get(row+1).size() > col){                                         //omit if compare !exist
                        if(hL.get(row+1).get(col) != null){                                 //omit if compare null
                            wallCheck += hL.get(row+1).get(col).getUpWall();
                        }
                    }
                }
                break;
            case "DR":
                wallCheck += hL.get(row).get(col).getDownRightWall();
                if(col != maxCol){                                                          //omit if last col
                    if(hL.get(row).get(col+1) != null){                                     //omit if compare null
                        wallCheck += hL.get(row).get(col+1).getUpLeftWall();                //!exist na
                    }
                }
                break;
            case "UR":
                wallCheck += hL.get(row).get(col).getUpRightWall();
                if(row != 0 && col < hL.get(row -1).size()-1){                              //omit if first row or last col
                    if(hL.get(row-1).size() - 1 > col){                                     //omit if compare !exist
                        if(hL.get(row-1).get(col+1) != null){                               //omit if compare null
                            wallCheck += hL.get(row-1).get(col+1).getDownLeftWall();
                        }
                    }
                }
                break;
        }
        switch(wallCheck){
            case "1":
            case "01":
            case "10": return TextRender.renderWall(side, '1');                             //wall exists
            case "0":
            case "00": return TextRender.renderWall(side, '0');                             //no wall
            default: return TextRender.renderWall(side, 'x');                               //double wall or error
        }
    }

    public static void AAATool(int[][][] AAA){
        StringBuilder sB = new StringBuilder();
        for(int row = 0 ; row < AAA.length ; row++){
            for(int col = 0 ; col < AAA[row].length ; col++){
                sB.append("[");
                for(int xy = 0; xy < AAA[row][col].length ; xy++){
                    sB.append(AAA[row][col][xy]);
                    if(xy < AAA[row][col].length-1){
                        sB.append(",");
                    }
                }
                sB.append("]");
            }
            System.out.println(sB);
            sB.delete(0, sB.length());
        }
    }


    public int[][][] celRenderSchedule(){
        List<List<HexCel>> hL = getHexLayout();
        int maxRowLength = 1;
        for(int row = 0 ; row < hL.size(); row++){
            int currentRowLength = hL.get(row).size();
            if (currentRowLength > maxRowLength) {
                maxRowLength = currentRowLength;
            }
        }
        int[][][] output = new int[2*hL.size()+maxRowLength-1][maxRowLength][2];
        for(int col = 0 ; col < maxRowLength ; col++){
            for(int row = 0 ; row < hL.size(); row++){
                for(int i = 0 ; i < col ; i++){
                    output[i][col][0] = -1;
                    output[i][col][1] = -1;
                }
                output[2*row+col][col][0] = row;
                output[2*row+col][col][1] = col;
                output[2*row+col+1][col][0] = row;
                output[2*row+col+1][col][1] = col;
                for(int f = 0 ; f < maxRowLength-col - 1 ; f++){
                    output[2*hL.size() + maxRowLength -f - 2][col][0] = -1;
                    output[2*hL.size() + maxRowLength -f - 2][col][1] = -1;
                }
            }
        }
//        AAATool(output);
        return output;
    }

    public static void main(String[] args) {
        Maze maze = new Maze();
        String[] layout = {"1111",
                           "111",
                           "1111"};
        maze.initializeMaze(layout, 5);
        maze.renderQuickLayoutText();
        int[][][] cS = maze.celRenderSchedule();
        AAATool(cS);

    }

//    public static void main(String[] args) {
////
//        Maze sampleM = new Maze();
//        String[] layout = {"11111",
//                           "0011",
//                           "11111",
//                           "001"};
//        sampleM.initializeMaze(layout, 5);
//        sampleM.getHexLayout().get(2).get(3).setUpWall('1');
//        sampleM.renderQuickLayoutText();
//        System.out.println("U TESTS:");
//        System.out.println("row 0: " + sampleM.renderTextMazeWall(0,2,"U").equals(" __ "));
//        System.out.println("compare another: " + sampleM.renderTextMazeWall(2,2,"U").equals(" __ "));
//        System.out.println("double wall: " + sampleM.renderTextMazeWall(2,3,"U").equals(" XX "));
//        System.out.println("compare null: " + sampleM.renderTextMazeWall(2,0,"U").equals(" __ "));
//        System.out.println("compare notExist: " + sampleM.renderTextMazeWall(2,4,"U").equals(" __ "));

//        Maze sampleUL = new Maze();
//        String[] layoutUL = {"11111",
//                "001",
//                "11111"
//                };
//        sampleUL.initializeMaze(layoutUL, 5);
//        sampleUL.getHexLayout().get(0).get(1).setDownRightWall('1');
//        sampleUL.renderQuickLayoutText();
//        System.out.println("UL TESTS:");
//        System.out.println("row 0: " + sampleUL.renderTextMazeWall(0,1,"UL").equals("/  "));
//        System.out.println("compare another: " + sampleUL.renderTextMazeWall(2,2,"UL").equals("/  "));
//        System.out.println("double wall: " + sampleUL.renderTextMazeWall(0,2,"UL").equals("X  "));
//        System.out.println("compare null: " + sampleUL.renderTextMazeWall(2,1,"UL").equals("/  "));
//        System.out.println("compare notExist: not applicable");
//
//        Maze sampleDL = new Maze();
//        String[] layoutDL = {"11111",
//                "001",
//                "11111",
//                "11"
//                };
//        sampleDL.initializeMaze(layoutDL, 5);
//        sampleDL.getHexLayout().get(2).get(1).setUpRightWall('1');
//        sampleDL.renderQuickLayoutText();
//        System.out.println("DL TESTS:");
//        System.out.println("rowMax: " + sampleDL.renderTextMazeWall(3,1,"DL").equals("\\"));
//        System.out.println("colMin: " + sampleDL.renderTextMazeWall(2,0,"DL").equals("\\"));
//        System.out.println("rowMax / colMin: " + sampleDL.renderTextMazeWall(3,0,"DL").equals("\\"));
//        System.out.println("compare another: " + sampleDL.renderTextMazeWall(0,3,"DL").equals("\\"));
//        System.out.println("double wall: " + sampleDL.renderTextMazeWall(1,2,"DL").equals("X"));
//        System.out.println("compare null: " + sampleDL.renderTextMazeWall(0,1,"DL").equals("\\"));
//
//        Maze sampleD = new Maze();
//        String[] layoutD = {"11111",
//                "011",
//                "11111"
//        };
//        sampleD.initializeMaze(layoutD, 5);
//        sampleD.getHexLayout().get(1).get(2).setUpWall('1');
//        sampleD.renderQuickLayoutText();
//        System.out.println("D TESTS:");
//        System.out.println("rowMax: " + sampleD.renderTextMazeWall(2,1,"D").equals("__"));
//        System.out.println("compare another: " + sampleD.renderTextMazeWall(0,1,"D").equals("__"));
//        System.out.println("double wall: " + sampleD.renderTextMazeWall(0,2,"D").equals("xx"));
//        System.out.println("compare null: " + sampleD.renderTextMazeWall(0,0,"D").equals("__"));
//        System.out.println("compare notExist: " + sampleD.renderTextMazeWall(0,3,"D").equals("__"));
//
//        Maze sampleDR = new Maze();
//        String[] layoutDR = {"11101",
//                "0011",
//                "11111"
//        };
//        sampleDR.initializeMaze(layoutDR, 5);
//        sampleDR.getHexLayout().get(0).get(1).setDownRightWall('1');
//        sampleDR.renderQuickLayoutText();
//        System.out.println("DR TESTS:");
//        System.out.println("colMax: " + sampleDR.renderTextMazeWall(0,4,"DR").equals("/"));
//        System.out.println("compare another: " + sampleDR.renderTextMazeWall(1,2,"DR").equals("/"));
//        System.out.println("double wall: " + sampleDR.renderTextMazeWall(0,1,"DR").equals("X"));
//        System.out.println("compare null: " + sampleDR.renderTextMazeWall(0,2,"DR").equals("/"));
//
//        Maze sampleUR = new Maze();
//        String[] layoutUR = {"11111",
//                "0011",
//                "11111"
//        };
//        sampleUR.initializeMaze(layoutUR, 5);
//        sampleUR.getHexLayout().get(1).get(2).setUpRightWall('1');
//        sampleUR.renderQuickLayoutText();
//        System.out.println("UR TESTS:");
//        System.out.println("row 0: " + sampleUR.renderTextMazeWall(0,2,"UR").equals("\\"));
//        System.out.println("colMax: " + sampleUR.renderTextMazeWall(2,4,"UR").equals("\\"));
//        System.out.println("row 0 / colMax: " + sampleUR.renderTextMazeWall(0,4,"UR").equals("\\"));
//        System.out.println("compare another: " + sampleUR.renderTextMazeWall(2,1,"UR").equals("\\"));
//        System.out.println("double wall: " + sampleUR.renderTextMazeWall(1,2,"UR").equals("X"));
//        System.out.println("compare null: " + sampleUR.renderTextMazeWall(2,0,"UR").equals("\\"));
//        System.out.println("compare notExist: " + sampleUR.renderTextMazeWall(2,3,"UR").equals("\\"));
//
//    }


//    public static void main(String[] args) {
//        Maze m = new Maze();
//        String[] layout = {"00000",
//                           "0101010",
//                           "0001100",
//                           "1111111",
//                           "0011100",
//                           "0101010",
//                           "0001000"};
//        m.initializeMaze(layout, 5);
////        m.getHexLayout().get(1).get(3).setUpWall('1');
////        m.getHexLayout().get(3).get(0).setDownRightWall('1');
////        m.getHexLayout().get(3).get(3).setUpWall('1');
////        m.getHexLayout().get(3).get(3).setDownRightWall('1');
////        m.getHexLayout().get(6).get(3).setUpWall('1');
////        m.getHexLayout().get(6).get(3).setUpRightWall('1');
//        m.renderQuickLayoutText();
//        m.checkRenderValidInputs();
////        m.getHexLayout().get(2).get(3).setUpWall('1');
////        System.out.println("U TESTS:");
////        System.out.println("row 0: " + m.renderTextMazeWall(0,2,"U").equals(" __ "));
////        System.out.println("compare another: " + m.renderTextMazeWall(2,2,"U").equals(" __ "));
////        System.out.println("double wall: " + m.renderTextMazeWall(2,3,"U").equals(" xx "));
////        System.out.println("compare null: " + m.renderTextMazeWall(2,0,"U").equals(" __ "));
////        System.out.println("compare notExist: " + m.renderTextMazeWall(2,4,"U").equals(" __ "));
////
////
////
//    }


}
