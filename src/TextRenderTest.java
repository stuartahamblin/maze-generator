import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Text;

public class TextRenderTest {

    public String[] layoutUL = {"11111",
            "001",
            "11111"
    };
    Maze sampleUL = new Maze(layoutUL, 5);

    @Test
    public void uLWallRowOne(){
        TextRender.renderTextMaze(sampleUL,true);
        String result1 = TextRender.renderStringMazeWall(sampleUL,1,1,"UL");
        Assert.assertEquals("/  ", result1);
    }

    @Test
    public void uLWallCompareNull() {
        String result2 = TextRender.renderStringMazeWall(sampleUL,2,3,"UL");
        Assert.assertEquals("/  ", result2);
    }

    @Test
    public void uLDoubleWall(){
        sampleUL.getHexLayout().get(1).get(3).setDownRightWall('1');
        sampleUL.getHexLayout().get(1).get(4).setUpRightWall('1');
        String result3 = TextRender.renderStringMazeWall(sampleUL,1,4,"UL");
        Assert.assertEquals("X  ", result3);
    }


//        String[] layout = {"11111",
//                "0011",
//                "11111",
//                "001"};
//        Maze sampleM = new Maze(layout, 5);
//        sampleM.getHexLayout().get(2).get(3).setUpWall('1');
//        System.out.println("row 0: " + sampleM.TextRender.renderStringMazeWall(0, 2, "U").equals(" __ "));
//        renderStringMazeWall(sampleM, 1, 2, "U");
//        System.out.println("compare another: " + sampleM.renderStringMazeWall(2, 2, "U").equals(" __ "));
//        System.out.println("double wall: " + sampleM.renderStringMazeWall(2, 3, "U").equals(" XX "));
//        System.out.println("compare null: " + sampleM.renderStringMazeWall(2, 0, "U").equals(" __ "));
//        System.out.println("compare notExist: " + sampleM.renderStringMazeWall(2, 4, "U").equals(" __ "));
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
//    }
}