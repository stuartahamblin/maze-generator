import java.util.ArrayList;
import java.util.List;

public class maze {

    private List<List<hexCel>> hexLayout;

    public List<List<hexCel>> getHexLayout() {
        return hexLayout;
    }

    public void setHexLayout(List<List<hexCel>> hexLayout) {
        this.hexLayout = hexLayout;
    }

    private void initializeMaze(String[] layout, double celLength){


        List<List<hexCel>> outputLayout = new ArrayList<>();
        for(int i = layout.length-1 ; i >= 0; i--){
            List<hexCel> buildArray = new ArrayList<>();
            for(int j = layout[i].length()-1; j >= 0 ; j--){
                System.out.println("i: " + i+ ", j: " +j);
                if(layout[i].charAt(j) == '1'){
                    buildArray.add(new hexCel());
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

    public static void main(String[] args) {
        maze sampleMaze1 = new maze();
        String[] layout = {"011","111","110"};
        sampleMaze1.initializeMaze(layout, 6);
        System.out.println(sampleMaze1.getHexLayout());
//        System.out.println(layout[0].charAt(0));
//        System.out.println(layout[0].charAt(0));
//        List<List<hexCel>> outputLayout = new ArrayList<>();
//        outputLayout.add(new ArrayList<>());
//        outputLayout.add(new ArrayList<>());
//        System.out.println(outputLayout);
//        System.out.println(layout[0].length());
//        hexCel[][] sampleHexMax = {new hexCel[3], new hexCel[3], new hexCel[3]};
//        System.out.println(sampleHexMax[0][0]);
    }
}
