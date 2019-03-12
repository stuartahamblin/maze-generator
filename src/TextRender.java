import java.util.List;
import java.util.Objects;

public class TextRender {

    public static final int UPPER_CELL = 1;
    public static final int LOWER_CELL = 2;
    public static final int ROW_INDEX = 0;
    public static final int COL_INDEX = 1;
    public static final int HEX_UPPER_LOWER_INDEX = 2;

    public static void checkRenderValidInputs(Maze maze){
        String[] sides = {"U","UL","DL","D","DR","UR"};
        List<List<HexCel>> hL = maze.getHexLayout();
        StringBuilder sB = new StringBuilder();
        for(int w = 0 ; w < sides.length ; w++){
            System.out.println("CHECK " + sides[w] + " values");
            for(int i = 0 ; i < hL.size() ; i++){
                for(int j = 0 ; j < hL.get(i).size() ; j++) {
                    if(Objects.nonNull(hL.get(i).get(j))){
                        sB.append(renderStringMazeWall(maze, i,j,sides[w]));
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

    public static void AATool(int[][] AA){
        StringBuilder sB = new StringBuilder();
        for(int row = 0 ; row < AA.length ; row++){
                sB.append("[");
            for(int col = 0 ; col < AA[row].length ; col++){
                sB.append(AA[row][col]);
                if(col < AA[row].length-1){
                    sB.append(",");
                }
            }
                sB.append("]");
        }
            System.out.println(sB);
            sB.delete(0, sB.length());
    }

    public static void AATool(String[][] AA){
        StringBuilder sB = new StringBuilder();
        for(int row = 0 ; row < AA.length ; row++){
            sB.append("[");
            for(int col = 0 ; col < AA[row].length ; col++){
                sB.append(AA[row][col]);
                if(col < AA[row].length-1){
                    sB.append(",");
                }
            }
            sB.append("]");
            System.out.println(sB);
            sB.delete(0, sB.length());
        }
    }

    public static void LATool(List<int[]> LA, String description){
        StringBuilder sB = new StringBuilder();
        sB.append(description + " = ");
        for(int row = 0 ; row < LA.size() ; row++){
            sB.append("[");
            for(int col = 0 ; col < LA.get(row).length ; col++){
                sB.append(LA.get(row)[col]);
                if(col < LA.get(row).length-1){
                    sB.append(",");
                }
            }
            sB.append("]");
        }
            System.out.println(sB);
            sB.delete(0, sB.length());
    }

    public static String renderWall(String side, char wallType){
        switch(side){
            case "U":
                switch(wallType){
                    case '1': return "__";
                    case '0': return "  ";
                    default: return "XX";
                }
            case "UL":
                switch(wallType){
                    case '1': return "/  ";
                    case '0': return "   ";
                    default: return "X  ";
                }
            case "UR":
            case "DL":
                switch(wallType){
                    case '1': return "\\";
                    case '0': return " ";
                    default: return "X";
                }
            case "D":
                switch(wallType){
                    case '1': return "__";
                    case '0': return "  ";
                    default: return "xx";
                }
            case "DR":
                switch(wallType){
                    case '1': return "/";
                    case '0': return " ";
                    default: return "X";
                }
        }
        return "";
    }

    public static String renderStringMazeWall(Maze maze, int row, int col, String side){
        String wallCheck = "";
        List<List<HexCel>> hL = maze.getHexLayout();
        switch(side){
            case "U":
                ;
                wallCheck += hL.get(row).get(col).getUpWall();
                if (Objects.nonNull(hL.get(row - 1).get(col))) {                             //omit if compare null
                    wallCheck += hL.get(row - 1).get(col).getDownWall();
                }
                break;
            case "UL":
                wallCheck += hL.get(row).get(col).getUpLeftWall();
                if(Objects.nonNull(hL.get(row).get(col-1))){                                 //omit if compare null
                    wallCheck += hL.get(row).get(col-1).getDownRightWall();
                }
                break;
            case "DL":
                wallCheck += hL.get(row).get(col).getDownLeftWall();
                if(Objects.nonNull(hL.get(row+1).get(col-1))){                               //omit if compare null
                    wallCheck += hL.get(row+1).get(col-1).getUpRightWall();
                }
                break;
            case "D":
                wallCheck += hL.get(row).get(col).getDownWall();
                if(Objects.nonNull(hL.get(row+1).get(col))){                                 //omit if compare null
                    wallCheck += hL.get(row+1).get(col).getUpWall();
                }
                break;
            case "DR":
                wallCheck += hL.get(row).get(col).getDownRightWall();
                if(Objects.nonNull(hL.get(row).get(col+1))){                                 //omit if compare null
                    wallCheck += hL.get(row).get(col+1).getUpLeftWall();
                }
                break;
            case "UR":
                wallCheck += hL.get(row).get(col).getUpRightWall();
                if(Objects.nonNull(hL.get(row-1).get(col+1))){                               //omit if compare null
                    wallCheck += hL.get(row-1).get(col+1).getDownLeftWall();
                }
                break;
        }
        switch(wallCheck){
            case "1":
            case "01":
            case "10": return TextRender.renderWall(side, '1');             //wall exists
            case "0":
            case "00": return TextRender.renderWall(side, '0');             //no wall
            default: return TextRender.renderWall(side, 'x');               //double wall or error
        }
    }

    public static int[][][] celRenderSchedule(Maze maze){
        List<List<HexCel>> hL = maze.getHexLayout();
        int rowLength = hL.get(0).size();
        int[][][] output = new int[2*hL.size()+rowLength][rowLength][3];
        for(int row = 2-rowLength ; row < hL.size()+rowLength/2; row++){
            for(int h = 1 ; h < 3 ; h++){
                for(int col = 0 ; col < rowLength ; col++){
                    int pick = 2*row+col+h;                         //loop moves DR
                    if( pick >= 0 && pick < output.length){
                        output[pick][col][ROW_INDEX] = row;
                        output[pick][col][COL_INDEX] = col;
                        output[pick][col][HEX_UPPER_LOWER_INDEX] = h;
                        if(row >= hL.size()){
                            output[pick][col][ROW_INDEX] *= -1;             //negative row values represent blank/null hex cells
                        }
                        if(row < hL.size() && row >= 0){
                            if(Objects.isNull(hL.get(row).get(col))){
                                output[pick][col][ROW_INDEX] = -1;          //negative row values represent blank/null hex cells
                            }
                        }
                    }
                }
            }
        }
        return output;
    }

    @Deprecated                         //use renderTextMaze
    public static void renderQuickLayoutText(Maze maze){
        List<List<HexCel>> objectLists = maze.getHexLayout();
        for(int i = 0 ; i < objectLists.size() ; i++){
            StringBuilder buildLine0 = new StringBuilder();
            for(int j = 0 ; j < objectLists.get(i).size() ; j++){
                if(objectLists.get(i).get(j) == null){
                    buildLine0.append(TextRender.renderWall("U", '0'));
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
            System.out.println(buildLine2);
        }
    }

    public static void renderTextMaze(Maze maze, boolean includeIndex){
        int[][][] rS = celRenderSchedule(maze);
        StringBuilder sB = new StringBuilder();
        for(int textLine = 3 ; textLine < rS.length - 3 ; textLine++){
            for(int textString = 1 ; textString < rS[0].length-1 ; textString++){
                int rPick = rS[textLine][textString][ROW_INDEX];
                int cPick = rS[textLine][textString][COL_INDEX];
                int upperLowerHex = rS[textLine][textString][HEX_UPPER_LOWER_INDEX];
                if(rPick >= 0){                                                             //if hex present
                    if(upperLowerHex == UPPER_CELL){                                        //if upper hex
                        sB.append(renderStringMazeWall(maze, rPick, cPick, "UL"));
                        if(includeIndex){                                                   //if indexes included
                            sB.delete(sB.length()-2,sB.length());
                            String x = Integer.toString(rPick);
                            String y = Integer.toString(cPick);
                            sB.append(x.charAt(x.length()-1));
                            sB.append(y.charAt(y.length()-1));
                        }
                        if(rS[textLine][textString+1][ROW_INDEX] < 0){   //if UR hex is blank/null
                            sB.append(renderStringMazeWall(maze, rPick, cPick, "UR"));
                        }
                    } else if (upperLowerHex == LOWER_CELL){                                    //if lower hex
                        sB.append(renderStringMazeWall(maze, rPick, cPick, "DL"));
                        sB.append(renderStringMazeWall(maze, rPick, cPick, "D"));
                        if(rS[textLine][textString+1][ROW_INDEX] < 0){   //if DR hex is blank/null
                            sB.append(renderStringMazeWall(maze, rPick, cPick, "DR"));
                        }
                    }
                } else {                                                                        //if blank/null hex cells
                    if(rS[textLine][textString][HEX_UPPER_LOWER_INDEX] == UPPER_CELL){                       //if upper blank/null hex cell
                        if(rS[textLine][textString-1][0] < 0){                                  //if UL hex is blank/null
                            sB.append(" ");
                        }
                        sB.append("  ");
                    } else if (upperLowerHex == LOWER_CELL){                                    //if lower hex cell
                        if(rS[textLine][textString-1][ROW_INDEX] < 0){                          //if DL hex is blank/null
                            sB.append(" ");
                        }
                        if(rS[textLine+1][textString][ROW_INDEX] > 0){                          //if D hex present
                            sB.append(renderStringMazeWall(maze, rS[textLine+1][textString][ROW_INDEX], rS[textLine+1][textString][COL_INDEX], "U"));
                        } else {                                                                //if D hex blank/null
                            sB.append("  ");
                        }
                    }
                }
//                System.out.println(sB +"       textLine = "+ textLine + ", textString =" + textString);
            }
            System.out.println(sB);
            sB.delete(0,sB.length());
        }
    }

//    public static Boolean ofValue(boolean value) {
//        return value ? true : false;
//    }



}
