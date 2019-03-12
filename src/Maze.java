import shapes.Hexagon;

import java.util.*;

public class Maze{

    private List<List<HexCel>> hexLayout;

    public List<List<HexCel>> getHexLayout() {
        return hexLayout;
    }

    public void setHexLayout(List<List<HexCel>> hexLayout) {
        this.hexLayout = hexLayout;
    }

    public void setUpWallInst(int[] rowCol, char wallType){
        this.getHexLayout().get(rowCol[0]).get(rowCol[1]).setUpWall(wallType);
    }

    public void setUpLeftWallInst(int[] rowCol, char wallType){
        this.getHexLayout().get(rowCol[0]).get(rowCol[1]).setUpLeftWall(wallType);
    }

    public void setDownLeftWallInst(int[] rowCol, char wallType){
        this.getHexLayout().get(rowCol[0]).get(rowCol[1]).setDownLeftWall(wallType);
    }

    public void setDownWallInst(int[] rowCol, char wallType){
        this.getHexLayout().get(rowCol[0]).get(rowCol[1]).setDownWall(wallType);
    }

    public void setDownRightWallInst(int[] rowCol, char wallType){
        this.getHexLayout().get(rowCol[0]).get(rowCol[1]).setDownRightWall(wallType);
    }

    public void setUpRightWallInst(int[] rowCol, char wallType){
        this.getHexLayout().get(rowCol[0]).get(rowCol[1]).setUpRightWall(wallType);
    }

    public Maze(String[] simpleLayout, double sideLength){
        initializeMaze(simpleLayout, sideLength);
        wilsonMazeAlgorithm();
    }

    public Maze(String[] simpleLayout, int[] entrancePosition, int[] exitPosition, double sideLength){
        initializeMaze(simpleLayout, sideLength);
        wilsonMazeAlgorithm();

        List<int[]> entranceDirections = findEntranceExitDirections(entrancePosition);
        int randomEntranceIndex = (int) Math.floor(Math.random() * entranceDirections.size());
        clearEntranceExit(entrancePosition, entranceDirections.get(randomEntranceIndex));

        List<int[]> exitDirections = findEntranceExitDirections(exitPosition);
        int randomExitIndex = (int) Math.floor(Math.random() * exitDirections.size());
        clearEntranceExit(exitPosition, exitDirections.get(randomExitIndex));
    }

    public void clearUpPath(int[] rowCol){
        this.getHexLayout().get(rowCol[0]).get(rowCol[1]).setUpWall('0');
        this.getHexLayout().get(rowCol[0]-1).get(rowCol[1]).setDownWall('0');
    }

    public void clearUpLeftPath(int[] rowCol){
        this.getHexLayout().get(rowCol[0]).get(rowCol[1]).setUpLeftWall('0');
        this.getHexLayout().get(rowCol[0]).get(rowCol[1]-1).setDownRightWall('0');
    }

    public void clearDownLeftPath(int[] rowCol){
        this.getHexLayout().get(rowCol[0]).get(rowCol[1]).setDownLeftWall('0');
        this.getHexLayout().get(rowCol[0]+1).get(rowCol[1]-1).setUpRightWall('0');
    }

    public void clearDownPath(int[] rowCol){
        this.getHexLayout().get(rowCol[0]).get(rowCol[1]).setDownWall('0');
        this.getHexLayout().get(rowCol[0]+1).get(rowCol[1]).setUpWall('0');
    }

    public void clearDownRightPath(int[] rowCol){
        this.getHexLayout().get(rowCol[0]).get(rowCol[1]).setDownRightWall('0');
        this.getHexLayout().get(rowCol[0]).get(rowCol[1]+1).setUpLeftWall('0');
    }

    public void clearUpRightPath(int[] rowCol){
        this.getHexLayout().get(rowCol[0]).get(rowCol[1]).setUpRightWall('0');
        this.getHexLayout().get(rowCol[0]-1).get(rowCol[1]+1).setDownLeftWall('0');
    }

    public int shiftLastRowFirstCol(String[] layout){
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
        int shiftUp = shiftLastRowFirstCol(layout);
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

    public List<int[]> findValidDirections(int[] position) {
        String[] hexWalls = HexCel.getHexWalls();
        List<int[]> output = new ArrayList<>();
        for(String wall : hexWalls){
            switch (wall) {
                case "U":
                    if(Objects.nonNull(getHexLayout().get(position[0]-1).get(position[1]))){
                        output.add(new int[]{-1,0});
                    }
                    break;
                case "UL":
                    if(Objects.nonNull(getHexLayout().get(position[0]).get(position[1]-1))){
                        output.add(new int[]{0,-1});
                    }
                    break;
                case "DL":
                    if(Objects.nonNull(getHexLayout().get(position[0]+1).get(position[1]-1))){
                        output.add(new int[]{1,-1});
                    }
                    break;
                case "D":
                    if(Objects.nonNull(getHexLayout().get(position[0]+1).get(position[1]))){
                        output.add(new int[]{1,0});
                    }
                    break;
                case "DR":
                    if(Objects.nonNull(getHexLayout().get(position[0]).get(position[1]+1))){
                        output.add(new int[]{0,1});
                    }
                    break;
                case "UR":
                    if(Objects.nonNull(getHexLayout().get(position[0]-1).get(position[1]+1))){
                        output.add(new int[]{-1,1});
                    }
                    break;
                default:
                    break;
            }
        }
        return output;
    }

    public List<int[]> findEntranceExitDirections(int[] position) {
        String[] hexWalls = HexCel.getHexWalls();
        List<int[]> output = new ArrayList<>();
        for(String wall : hexWalls){
            switch (wall) {
                case "U":
                    if(getHexLayout().get(position[0]-1).get(position[1]) == null){
                        output.add(new int[]{-1,0});
                    }
                    break;
                case "UL":
                    if(getHexLayout().get(position[0]).get(position[1]-1) == null){
                        output.add(new int[]{0,-1});
                    }
                    break;
                case "DL":
                    if(getHexLayout().get(position[0]+1).get(position[1]-1) == null){
                        output.add(new int[]{1,-1});
                    }
                    break;
                case "D":
                    if(getHexLayout().get(position[0]+1).get(position[1]) == null){
                        output.add(new int[]{1,0});
                    }
                    break;
                case "DR":
                    if(getHexLayout().get(position[0]).get(position[1]+1) == null){
                        output.add(new int[]{0,1});
                    }
                    break;
                case "UR":
                    if(getHexLayout().get(position[0]-1).get(position[1]+1) == null){
                        output.add(new int[]{-1,1});
                    }
                    break;
                default:
                    break;
            }
        }
        return output;
    }

    public int[] clearPath(int[] current, int[] direction){
        int[] nextCurrent = new int[2];
        if(direction[1] >= 0 && direction[0] <= 0){
            if(direction[0] == -1){                             //UP
                if(direction[1] == 0){
                    clearUpPath(current);
                } else {
                    clearUpRightPath(current);
                }

            } else {
                clearDownRightPath(current);
            }
        } else if(direction[1] <= 0 && direction[0] >= 0){
            if(direction[0] == 1){                              //DOWN
                if(direction[1] == 0){
                    clearDownPath(current);
                } else {
                    clearDownLeftPath(current);
                }

            } else {
                clearUpLeftPath(current);
            }
        }
        nextCurrent[0] = current[0] + direction[0];
        nextCurrent[1] = current[1] + direction[1];
        return nextCurrent;
    }

    public void clearEntranceExit(int[] current, int[] direction){
        if(direction[1] >= 0 && direction[0] <= 0){
            if(direction[0] == -1){                             //UP
                if(direction[1] == 0){
                    setUpWallInst(current, '0');
                } else {
                    setUpRightWallInst(current, '0');
                }

            } else {
                setDownRightWallInst(current,'0');
            }
        } else if(direction[1] <= 0 && direction[0] >= 0){
            if(direction[0] == 1){                              //DOWN
                if(direction[1] == 0){
                    setDownWallInst(current, '0');
                } else {
                    setDownLeftWallInst(current, '0');
                }

            } else {
                setUpLeftWallInst(current, '0');
            }
        }
    }

    public void wilsonMazeAlgorithm(){
        List<List<HexCel>> hL = getHexLayout();
        List<String> keys = new ArrayList<>();
        LinkedHashMap<String,Boolean> rowColAvail = new LinkedHashMap<>();
        int availCount = 0;
        int randomAvailIndex;
        int[] startingRC = new int[2];
        int[] currentRC;
        List<int[]> validDirections;
        int randomDirection;
        int[] directionPicked;
        int[][][] cellDirections = new int[hL.size()][hL.get(0).size()][2];
        for(int row = 1 ; row < hL.size()-1 ; row++){
            for(int col = 1 ; col < hL.get(row).size()-1 ; col++){
                if(Objects.nonNull(hL.get(row).get(col))){
                    String avail = row + "," + col;
                    keys.add(avail);
                    rowColAvail.put(avail,true);
                    availCount++;
                }
            }
        }
        randomAvailIndex = (int) Math.floor(Math.random() * availCount);    ///Key the first path will finish at
        rowColAvail.put(keys.get(randomAvailIndex),false);
        availCount--;
        do {
            randomAvailIndex = (int) Math.floor(Math.random() * availCount);
            for (String key : keys) {
                if (rowColAvail.get(key)) {
                    if (randomAvailIndex == 0) {
                        startingRC[0] = Integer.parseInt(key.substring(0, key.indexOf(",")));        //Key the path will start at
                        startingRC[1] = Integer.parseInt(key.substring(key.indexOf(",") + 1));
                        break;
                    }
                    randomAvailIndex--;
                }
            }
            currentRC = startingRC.clone();
            do {
                validDirections = findValidDirections(currentRC);                                   //determine direction
                randomDirection = (int) Math.floor(Math.random() * (validDirections.size()));
                directionPicked = validDirections.get(randomDirection);
                cellDirections[currentRC[0]][currentRC[1]][0] = directionPicked[0];                 //record direction on currentRC
                cellDirections[currentRC[0]][currentRC[1]][1] = directionPicked[1];
                currentRC[0] += directionPicked[0];                                                 //set currentRC
                currentRC[1] += directionPicked[1];
            } while (rowColAvail.get(currentRC[0] + "," + currentRC[1]));
            currentRC = startingRC.clone();
            do {                                                                                    //WALK START TO FINISH
                directionPicked[0] = cellDirections[currentRC[0]][currentRC[1]][0];
                directionPicked[1] = cellDirections[currentRC[0]][currentRC[1]][1];
                rowColAvail.put(currentRC[0] + "," + currentRC[1], false);
                availCount--;
                currentRC = clearPath(currentRC, directionPicked);
            } while (rowColAvail.get(currentRC[0] + "," + currentRC[1]));
        } while (availCount > 0);
    }


//    public int[][] row(int value) {
//        // create rows here
//    }
//
//    public int[] column(int value) {
//        // create columns
//    }



    public static void main(String[] args) {
        String[] layout = {
                "0000001",
                "0000111",
                "0011111",
                "1111111",
                "1110011",
                "1100011",
                "1100111",
                "1111111",
                "11111",
                "111",
                "1"};
        Maze maze = new Maze(layout, new int[]{1,7}, new int[]{11,1},5);
        TextRender.renderTextMaze(maze,false);
    }

}
