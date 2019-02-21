package view;

public class TextRender {

    public static void hexPortionRender(int celType, int portion){
        String[] fullString = {" __ ","/  \\", "\\__/"};
        if(celType == '1'){
            System.out.println(fullString[portion]);
        } else {
            System.out.println("    ");
        }
    }

    public static String renderWall(String side, char wallType){
        switch(side){
            case "U":
                switch(wallType){
                    case '1': return " __ ";
                    case '0': return "    ";
                    default: return " xx ";
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

    public static void renderRows(){

    }







//         __
//     __ /  \ __
//    /  \\__//  \
//    \__/    \__/
}
