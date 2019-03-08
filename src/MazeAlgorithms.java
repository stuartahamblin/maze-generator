import java.util.List;

public abstract class MazeAlgorithms {





//    public int[] getCurrentPosition() {
//        return currentPosition;
//    }
//
//    public void setCurrentPosition(int[] currentPosition) {
//        this.currentPosition = currentPosition;
//    }
//
//    public void setCurrentPosition(int row, int col) {
//        this.getCurrentPosition()[0] = row;
//        this.getCurrentPosition()[1] = col;
//    }
//
//    public int[] getCurrentNeighbor(String wallSide){
//        switch(wallSide){
//            default:
//                return null;
//            case "U":
//                return new int[]{getCurrentPosition()[0]-1, getCurrentPosition()[1]};
//            case "UL":
//
//            case "DL":
//
//            case "D":
//
//            case "DR":
//
//            case "UR":
//
//        }
//        return null;
//    }
//
//    public List<int[]> getCurrentNeighbors() {
//        return currentNeighbors;
//    }

//    public static void wilsonMazeAlgorithm(Maze maze){
////        List<List<HexCel>> hL = hexLayout;
//    }

    public static void main(String[] args) {
        String[] layout = {"1111",
                "11111",
                "011",
                "01111"};
        Maze maze = new Maze(layout, 5);
//        maze.clearUpPath(1,1);
//        maze.clearUpLeftPath(1,1);
//        maze.clearDownLeftPath(1,2);
//        maze.clearDownPath(1,2);
//        maze.clearDownRightPath(1,1);
//        maze.clearUpRightPath(3,1);

        TextRender.renderTextMaze(maze,true);
    }




}
