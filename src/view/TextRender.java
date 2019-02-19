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

    public static String renderWall(String side, char type){
        switch(side){
            case "U":
                switch(type){
                    case '1': return " __ ";
                    default: return "XXXX";
                }
            case "UL":
                switch(type){
                    case '1': return "/  ";
                    default: return "   ";
                }
            case "DL":
                switch(type){
                    case '1': return "\\";
                    default: return " ";
                }
            case "D":
                switch(type){
                    case '1': return "__";
                    default: return "  ";
                }
            case "DR":
                switch(type){
                    case '1': return "/ ";
                    default: return " ";
                }
            case "UR":
                switch(type){
                    case '1': return "\\";
                    default: return " ";
                }
        }
        return "THIS IS NOT WORKING";
    }

    public static void renderRows(){

    }







//         __
//     __ /  \ __
//    /  \\__//  \
//    \__/    \__/
}
