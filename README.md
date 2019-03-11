# Maze-generator

Example:

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
        TextRender.renderTextMaze(maze,false);                               //true to show maze Cell Indexes
    }
