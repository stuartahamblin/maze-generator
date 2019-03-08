import shapes.Hexagon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Maze extends MazeAlgorithms{

    private List<List<HexCel>> hexLayout;

    public List<List<HexCel>> getHexLayout() {
        return hexLayout;
    }

    public void setHexLayout(List<List<HexCel>> hexLayout) {
        this.hexLayout = hexLayout;
    }

    public void setUpWallInst(int row, int col, char wallType){
        this.getHexLayout().get(row).get(col).setUpWall(wallType);
    }

    public void setUpLeftWallInst(int row, int col, char wallType){
        this.getHexLayout().get(row).get(col).setUpLeftWall(wallType);
    }

    public void setDownLeftWallInst(int row, int col, char wallType){
        this.getHexLayout().get(row).get(col).setDownLeftWall(wallType);
    }

    public void setDownWallInst(int row, int col, char wallType){
        this.getHexLayout().get(row).get(col).setDownWall(wallType);
    }

    public void setDownRightWallInst(int row, int col, char wallType){
        this.getHexLayout().get(row).get(col).setDownRightWall(wallType);
    }

    public void setUpRightWallInst(int row, int col, char wallType){
        this.getHexLayout().get(row).get(col).setUpRightWall(wallType);
    }

    public void clearUpPath(int row, int col){
        this.getHexLayout().get(row).get(col).setUpWall('0');
        this.getHexLayout().get(row-1).get(col).setDownWall('0');
    }

    public void clearUpLeftPath(int row, int col){
        this.getHexLayout().get(row).get(col).setUpLeftWall('0');
        this.getHexLayout().get(row).get(col-1).setDownRightWall('0');
    }

    public void clearDownLeftPath(int row, int col){
        this.getHexLayout().get(row).get(col).setDownLeftWall('0');
        this.getHexLayout().get(row+1).get(col-1).setUpRightWall('0');
    }

    public void clearDownPath(int row, int col){
        this.getHexLayout().get(row).get(col).setDownWall('0');
        this.getHexLayout().get(row+1).get(col).setUpRightWall('0');
    }

    public void clearDownRightPath(int row, int col){
        this.getHexLayout().get(row).get(col).setDownRightWall('0');
        this.getHexLayout().get(row).get(col+1).setUpLeftWall('0');
    }

    public void clearUpRightPath(int row, int col){
        this.getHexLayout().get(row).get(col).setUpRightWall('0');
        this.getHexLayout().get(row-1).get(col+1).setDownLeftWall('0');
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

    private String[] bufferEdges(String[] layout){
        String[] output = new String[layout.length+2];
        int maxL = 0;
        for(String lay : layout){
            if(maxL < lay.length()){
                maxL = lay.length();
            }
        }
        output[0] = new String(new char[maxL + 2]).replace("\0", "0");
        output[layout.length+1] = new String(new char[maxL + 2]).replace("\0", "0");
        StringBuilder sB = new StringBuilder();
        for(int i = 0 ; i < layout.length ; i++){
            sB.append("0");
            sB.append(layout[i]);
            int addZeros = maxL - layout[i].length()+1;
            sB.append(new String(new char[addZeros]).replace("\0", "0"));
            output[i+1] = sB.toString();
            sB.delete(0, sB.length());
        }
        return output;
    }

    public void initializeMaze(String[] simpleLayout, double sideLength){
        String[] layout = bufferEdges(simpleLayout);
        int shiftUp = shiftZeroUp(layout);
        double bottomToCentroid = Hexagon.getBottomToCentroid(sideLength);
        double leftToCentroid = Hexagon.getLeftToCentroid(sideLength);
        List<List<HexCel>> hL = new ArrayList<>();
        for(int i = 0 ; i < layout.length; i++){                                    //ROWS
            var buildArray = new ArrayList<HexCel>();
            for(int j = 0 ; j < layout[i].length() ; j++){                          //COLUMNS
                if(layout[i].charAt(j) == '1'){
                    double x = (2*j+1)*leftToCentroid;
                    double y = (shiftUp + ((2*(layout.length - i) -1) - j))*bottomToCentroid;
                    HexCel buildCel = new HexCel(x, y, 0, sideLength);
                        if(layout[i].charAt(j+1) != '1'){                           //build wall if next item is null
                            buildCel.setDownRightWall('1');
                        }
                        if(hL.get(i-1).size() > j){                                 //build wall if above index is null
                            if(hL.get(i-1).get(j) == null){
                                buildCel.setUpWall('1');
                            }
                        }
                        if(hL.get(i-1).size() > j+1){                               //build wall if upright index is null
                            if(hL.get(i-1).get(j+1) == null){
                                    buildCel.setUpRightWall('1');
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

    public Maze(String[] simpleLayout, double sideLength){
        initializeMaze(simpleLayout, sideLength);
//        MazeAlgorithms.wilsonMazeAlgorithm();
    }

//------------------------------------------------------------------


    public List<int[]> findValidNeighbors(int[] position) {
        List<List<HexCel>> hL = getHexLayout();
        String[] hexWalls = HexCel.getHexWalls();
        List<int[]> output = new ArrayList<>();
        for(String wall : hexWalls){
            switch (wall) {
                case "U":
                    if(hL.get(position[0]-1).get(position[1]) != null){
                        output.add(new int[]{position[0]-1,position[1]});
                    }
                    break;
                case "UL":
                    if(hL.get(position[0]).get(position[1]-1) != null){
                        output.add(new int[]{position[0],position[1]-1});
                    }
                    break;
                case "DL":
                    if(hL.get(position[0]+1).get(position[1]-1) != null){
                        output.add(new int[]{position[0]+1, position[1]-1});
                    }
                    break;
                case "D":
                    if(hL.get(position[0]+1).get(position[1]) != null){
                        output.add(new int[]{position[0]+1,position[1]});
                    }
                    break;
                case "DR":
                    if(hL.get(position[0]).get(position[1]+1) != null){
                        output.add(new int[]{position[0],position[1]+1});
                    }
                    break;
                case "UR":
                    if(hL.get(position[0]-1).get(position[1]+1) != null){
                        output.add(new int[]{position[0]-1,position[1]+1});
                    }
                    break;
                default:
                    break;
            }
        }
        return output;
    }

    public void wilsonMazeAlgorithm(){
        List<List<HexCel>> hL = getHexLayout();
        int[] desiredPosition;
        int[] currentPosition = {0,0};
        List<int[]> currentNeighbors;
        List<int[]> unusedCells = new ArrayList<>();
        String[][] usedCells = new String[hL.size()][hL.get(0).size()];
        for(int row = 1 ; row < hL.size()-1 ; row++){
            for(int col = 1 ; col < hL.get(row).size()-1 ; col++){
                if(hL.get(row).get(col) != null){
                    unusedCells.add(new int[]{row,col});
                    usedCells[row][col] = "    ";
                }
            }
        }
        int randomUnused = (int) Math.floor(Math.random() * (unusedCells.size()));
        desiredPosition = unusedCells.get(randomUnused);
        usedCells[desiredPosition[0]][desiredPosition[1]] = "used";
        unusedCells.remove(randomUnused);

        int randomCurrent = (int) Math.floor(Math.random() * (unusedCells.size()));
        currentPosition = unusedCells.get(randomCurrent);
        usedCells[currentPosition[0]][currentPosition[1]] = "curr";
        unusedCells.remove(currentPosition);

        currentNeighbors = findValidNeighbors(currentPosition);
        int randomNeighbor = (int) Math.floor(Math.random() * (currentNeighbors.size()));
        int[] pickedNeighbor = currentNeighbors.get(randomNeighbor);
        usedCells[currentPosition[0]][currentPosition[1]] = "curr";
        usedCells[pickedNeighbor[0]][pickedNeighbor[1]] = "pick";

        TextRender.AATool(usedCells);
//        System.out.println(Arrays.toString(usedCells[0]));
//        System.out.println(Arrays.toString(unusedCells.get(4)));
    }

//------------------------------------------------------------------
    public static void main(String[] args) {
        String[] layout = {"1111",
                "11111",
                "011",
                "01111"};
        Maze maze = new Maze(layout, 5);
        maze.clearUpPath(2,1);
        maze.clearUpLeftPath(2,2);
        maze.clearDownLeftPath(1,3);
        maze.clearDownPath(3,2);
        maze.clearDownRightPath(2,2);
        maze.clearUpRightPath(3,2);
        maze.wilsonMazeAlgorithm();
        TextRender.renderTextMaze(maze,true);
//        TextRender.LATool(maze.findValidNeighbors(new int[]{1,2}));
    }

//    public int[][][] celRenderSchedule(){
//        List<List<HexCel>> hL = getHexLayout();
//        int rowLength = hL.get(0).size();
//        int[][][] output = new int[2*hL.size()+rowLength][rowLength][3];
//        for(int row = 2-rowLength ; row < hL.size()+rowLength/2; row++){
//            for(int h = 1 ; h < 3 ; h++){
//                for(int col = 0 ; col < rowLength ; col++){
//                    int pick = 2*row+col+h;
//                    if( pick >= 0 && pick < output.length){
//                        output[pick][col][0] = row;
//                        output[pick][col][1] = col;
//                        output[pick][col][2] = h;
//                        if(row >= hL.size()){
//                            output[pick][col][0] *= -1;             //negative row values represent blank/null hex cells
//                        }
//                        if(row < hL.size() && row >= 0){
//                            if(hL.get(row).get(col) == null){
//                                output[pick][col][0] = -1;          //negative row values represent blank/null hex cells
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return output;
//    }

//    public void renderTextMaze(){
//        int[][][] rS = celRenderSchedule();
//        StringBuilder sB = new StringBuilder();
//        for(int textLine = 3 ; textLine < rS.length - 3 ; textLine++){
//            for(int textString = 1 ; textString < rS[0].length-1 ; textString++){
//                int xPick = rS[textLine][textString][0];
//                int yPick = rS[textLine][textString][1];
//                int upperLowerHex = rS[textLine][textString][2];
//                if(xPick >= 0){                                                         //if present hex
//                    if(upperLowerHex == 1){                                             //if upper hex
//                        sB.append(renderStringMazeWall(xPick, yPick, "UL"));
//                        if(rS[textLine][textString+1][0] < 0){                          //if UR hex is blank/null
//                            sB.append(renderStringMazeWall(xPick, yPick, "UR"));
//                        }
//                    } else if (upperLowerHex == 2){                                     //if lower hex
//                        sB.append(renderStringMazeWall(xPick, yPick, "DL"));
//                        sB.append(renderStringMazeWall(xPick, yPick, "D"));
//                        if(rS[textLine][textString+1][0] < 0){                          //if DR hex is blank/null
//                            sB.append(renderStringMazeWall(xPick, yPick, "DR"));
//                        }
//                    }
//                } else {                                                                //if blank/null hex cells
//                    if(rS[textLine][textString][2] == 1){                               //if upper blank/null hex cell
//                        if(rS[textLine][textString-1][0] < 0){                          //if UL hex is blank/null
//                            sB.append(" ");
//                        }
//                        sB.append("  ");
//                    } else if (upperLowerHex == 2){                                     //if lower blank/null hex cell
//                        if(rS[textLine][textString-1][0] < 0){                          //if DL hex is blank/null
//                            sB.append(" ");
//                        }
//                        if(rS[textLine+1][textString][0] > 0){                          //if D hex present
//                            sB.append(renderStringMazeWall(rS[textLine+1][textString][0], rS[textLine+1][textString][1], "U"));
//                        } else {                                                        //if D hex blank/null
//                            sB.append("  ");
//                        }
//                    }
//                }
////                System.out.println(sB +"       textLine = "+ textLine + ", textString =" + textString);
//            }
//            System.out.println(sB);
//            sB.delete(0,sB.length());
//        }
//    }




//    public static void main(String[] args) {
//        Maze sampleM = new Maze();
//        String[] layout = {"11111",
//                "0011",
//                "11111",
//                "001"};
//        sampleM.initializeMaze(layout, 5);
//        sampleM.getHexLayout().get(2).get(3).setUpWall('1');
//        sampleM.renderQuickLayoutText();
//        System.out.println("U TESTS:");
//        System.out.println("row 0: " + sampleM.renderStringMazeWall(0, 2, "U").equals(" __ "));
//        System.out.println("compare another: " + sampleM.renderStringMazeWall(2, 2, "U").equals(" __ "));
//        System.out.println("double wall: " + sampleM.renderStringMazeWall(2, 3, "U").equals(" XX "));
//        System.out.println("compare null: " + sampleM.renderStringMazeWall(2, 0, "U").equals(" __ "));
//        System.out.println("compare notExist: " + sampleM.renderStringMazeWall(2, 4, "U").equals(" __ "));
//    }

//        Maze sampleUL = new Maze();
//        String[] layoutUL = {"11111",
//                "001",
//                "11111"
//                };
//        sampleUL.initializeMaze(layoutUL, 5);
//        sampleUL.getHexLayout().get(0).get(1).setDownRightWall('1');
//        sampleUL.renderQuickLayoutText();
//        System.out.println("UL TESTS:");
//        System.out.println("row 0: " + sampleUL.renderStringMazeWall(0,1,"UL").equals("/  "));
//        System.out.println("compare another: " + sampleUL.renderStringMazeWall(2,2,"UL").equals("/  "));
//        System.out.println("double wall: " + sampleUL.renderStringMazeWall(0,2,"UL").equals("X  "));
//        System.out.println("compare null: " + sampleUL.renderStringMazeWall(2,1,"UL").equals("/  "));
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
//        System.out.println("rowMax: " + sampleDL.renderStringMazeWall(3,1,"DL").equals("\\"));
//        System.out.println("colMin: " + sampleDL.renderStringMazeWall(2,0,"DL").equals("\\"));
//        System.out.println("rowMax / colMin: " + sampleDL.renderStringMazeWall(3,0,"DL").equals("\\"));
//        System.out.println("compare another: " + sampleDL.renderStringMazeWall(0,3,"DL").equals("\\"));
//        System.out.println("double wall: " + sampleDL.renderStringMazeWall(1,2,"DL").equals("X"));
//        System.out.println("compare null: " + sampleDL.renderStringMazeWall(0,1,"DL").equals("\\"));
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
//        System.out.println("rowMax: " + sampleD.renderStringMazeWall(2,1,"D").equals("__"));
//        System.out.println("compare another: " + sampleD.renderStringMazeWall(0,1,"D").equals("__"));
//        System.out.println("double wall: " + sampleD.renderStringMazeWall(0,2,"D").equals("xx"));
//        System.out.println("compare null: " + sampleD.renderStringMazeWall(0,0,"D").equals("__"));
//        System.out.println("compare notExist: " + sampleD.renderStringMazeWall(0,3,"D").equals("__"));
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
//        System.out.println("colMax: " + sampleDR.renderStringMazeWall(0,4,"DR").equals("/"));
//        System.out.println("compare another: " + sampleDR.renderStringMazeWall(1,2,"DR").equals("/"));
//        System.out.println("double wall: " + sampleDR.renderStringMazeWall(0,1,"DR").equals("X"));
//        System.out.println("compare null: " + sampleDR.renderStringMazeWall(0,2,"DR").equals("/"));
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
//        System.out.println("row 0: " + sampleUR.renderStringMazeWall(0,2,"UR").equals("\\"));
//        System.out.println("colMax: " + sampleUR.renderStringMazeWall(2,4,"UR").equals("\\"));
//        System.out.println("row 0 / colMax: " + sampleUR.renderStringMazeWall(0,4,"UR").equals("\\"));
//        System.out.println("compare another: " + sampleUR.renderStringMazeWall(2,1,"UR").equals("\\"));
//        System.out.println("double wall: " + sampleUR.renderStringMazeWall(1,2,"UR").equals("X"));
//        System.out.println("compare null: " + sampleUR.renderStringMazeWall(2,0,"UR").equals("\\"));
//        System.out.println("compare notExist: " + sampleUR.renderStringMazeWall(2,3,"UR").equals("\\"));
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
////        System.out.println("row 0: " + m.renderStringMazeWall(0,2,"U").equals(" __ "));
////        System.out.println("compare another: " + m.renderStringMazeWall(2,2,"U").equals(" __ "));
////        System.out.println("double wall: " + m.renderStringMazeWall(2,3,"U").equals(" xx "));
////        System.out.println("compare null: " + m.renderStringMazeWall(2,0,"U").equals(" __ "));
////        System.out.println("compare notExist: " + m.renderStringMazeWall(2,4,"U").equals(" __ "));
////
////
////
//    }


}
