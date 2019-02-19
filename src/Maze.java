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
        List<List<HexCel>> outputLayout = new ArrayList<>();
        for(int i = 0 ; i <= layout.length-1; i++){
            var buildArray = new ArrayList<HexCel>();
            for(int j = 0 ; j < layout[i].length() ; j++){
                if(layout[i].charAt(j) == '1'){
                    double x = (2*j+1)*leftToCentroid;
                    double y = (shiftUp + ((2*(layout.length - i) -1) - j))*bottomToCentroid;
//                    System.out.println("x: " + x + ", y: " + y);
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
                        if(outputLayout.get(i-1).size() <= j+1){                            //build wall if upright index does not exist
                            buildCel.setUpRightWall('1');
                            if(outputLayout.get(i-1).size() <= j){                          //build wall if above index does not exist
                                buildCel.setUpWall('1');
                            }
                        }
                        if(outputLayout.get(i-1).size() >= layout[i].length()){             //build wall
                            if(outputLayout.get(i-1).get(j) == null){
                                buildCel.setUpWall('1');
                            }
                            if(outputLayout.get(i-1).size() > layout[i].length()){
                                if(outputLayout.get(i-1).get(j+1) == null){
                                    buildCel.setUpRightWall('1');
                                }
                            }
                        }
                    }
                    buildArray.add(buildCel);
                } else {
                    buildArray.add(null);
                }
            }
            outputLayout.add(buildArray);
        }
        setHexLayout(outputLayout);
    }

    static void wilsonAlgorithm(){

    }

    public static void quickWallRenderText(List<List<HexCel>> objectLists){
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
                    buildLine0.append(TextRender.renderWall("UL", '0'));
                    buildLine0.append(TextRender.renderWall("UR", '0'));
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
                    buildLine0.append(TextRender.renderWall("DL", '0'));
                    buildLine0.append(TextRender.renderWall("D", '0'));
                    buildLine0.append(TextRender.renderWall("DR", '0'));
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

    public static void main(String[] args) {
        Maze sampleMaze1 = new Maze();
        String[] layout = {"01111",
                           "1",
                           "1111",
                           "1"};
        sampleMaze1.initializeMaze(layout, 5);
        List<List<HexCel>> sampleMap = sampleMaze1.getHexLayout();
        System.out.println(sampleMap);
        quickWallRenderText(sampleMap);
//        System.out.println(sampleMap.get(0).get(0));
//        System.out.println(sampleMap.get(0).get(0).getUpRightWall());
//        System.out.println(TextRender.renderWall("UL", sampleMap.get(0).get(0).getUpLeftWall()));
//        TextRender.renderWall("U",'1');
//        System.out.println(sampleMap.get(0).size());
//        hexPortionRender('1',0);
//        hexPortionRender('1',1);
//        hexPortionRender('1',2);

    }

}
