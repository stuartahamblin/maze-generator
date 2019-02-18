package view;

public class textRender {

    static String[] hexRender(char celType){
        if(celType == 1){
            return new String[] {" __ ","/  \\", "\\__/"};
        } else {
            return new String[] {"    ","    ","    "};
        }
    }
}
