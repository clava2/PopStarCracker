import java.util.*;

public class Board {
    public BLOCK_TYPE[][] board;

    public Board() {
        this.board = new BLOCK_TYPE[10][10];
        for (int row = 0;row != 10;row++){
            this.board[row] = new BLOCK_TYPE[10];
            for(int column = 0; column != 10;column ++){
                this.board[row][column] = BLOCK_TYPE.BLOCK_EMPTY;
            }
        }
    }

    public Board(Board board){
        this.board = new BLOCK_TYPE[10][10];
        for (int row = 0;row != 10;row++){
            for(int column = 0;column != 10;column ++){
                this.board[row][column] = board.board[row][column];
            }
        }
    }

    public Board(BLOCK_TYPE[][] data){
        this.board = data;
    }

    public Board(String board) {
        if(board.length() != 100){
            return;
        }
        this.board = new BLOCK_TYPE[10][10];
        Map<Character, BLOCK_TYPE> char2type = new HashMap<Character, BLOCK_TYPE>(){{
            put('r', BLOCK_TYPE.BLOCK_RED);
            put('g', BLOCK_TYPE.BLOCK_GREEN);
            put('b', BLOCK_TYPE.BLOCK_BLUE);
            put('p', BLOCK_TYPE.BLOCK_PURSE);
            put('y', BLOCK_TYPE.BLOCK_YELLOW);
            put(' ', BLOCK_TYPE.BLOCK_EMPTY);
        }};
        for (int row = 0;row != 10;row ++){
            for(int column = 0;column != 10;column++){
                this.board[row][column] = char2type.get(board.charAt(row * 10 + column));
            }
        }
    }

    public void dropColumn(int columnIndex){
        int targetRowIndex = 9;
        for(int rowIndex = 9;rowIndex >= 0;rowIndex--) {
            if (this.board[rowIndex][columnIndex] != BLOCK_TYPE.BLOCK_EMPTY) {
                board[targetRowIndex][columnIndex] = this.board[rowIndex][columnIndex];
                targetRowIndex -= 1;
            }
        }
        for(;targetRowIndex >= 0;targetRowIndex--){
            board[targetRowIndex][columnIndex] = BLOCK_TYPE.BLOCK_EMPTY;
        }
    }

    public boolean columnEmpty(int columnIndex){
        for(int rowIndex = 0;rowIndex != 10;rowIndex++){
            if (this.board[rowIndex][columnIndex] != BLOCK_TYPE.BLOCK_EMPTY){
                return false;
            }
        }
        return true;
    }

    public void swapColumn(int leftIndex, int rightIndex){
        for(int rowIndex = 0;rowIndex != 10;rowIndex++){
            BLOCK_TYPE temp = this.board[rowIndex][leftIndex];
            this.board[rowIndex][leftIndex] = board[rowIndex][rightIndex];
            this.board[rowIndex][rightIndex] = temp;
        }
    }

    public void deleteEmptyColumn(){
        int leftIndex = 0;int rightIndex = 0;
        while(rightIndex < 10){
            if(!columnEmpty(rightIndex)){
                swapColumn(leftIndex, rightIndex);
                leftIndex++;
            }
            rightIndex++;
        }
    }

    public void drop(){
        for(int columnIndex = 0;columnIndex != 10;columnIndex++){
            dropColumn(columnIndex);
        }
        deleteEmptyColumn();
    }

    public long hash() {
        int length = BLOCK_TYPE.values().length;
        int result = 0;
        for(int row = 0;row != 10;row++){
            for(int column = 0;column != 10;column++){
                result *= length;
                result += this.board[row][column].ordinal();
            }
        }
        return result;
    }

    public boolean equals(Object others){
        if (!(others instanceof Board)){
            return false;
        }
        Board anotherBoard = (Board)others;
        for(int row = 0;row != 10;row++){
            if(!Arrays.equals(this.board[row], anotherBoard.board[row])){
                return false;
            }
        }
        return true;
    }

    public void getPopable(int row, int column, PathNode path, BLOCK_TYPE type){
        if(row < 0 || row >= 10) {
            return;
        }
        if(column < 0 || column >= 10) {
            return;
        }
        if(this.board[row][column] != type) {
            return;
        }
        Position position = new Position(row, column);
        if(path.path.contains(position)) {
            return;
        }
        path.add(position);
        getPopable(row - 1, column, path, type);
        getPopable(row + 1, column, path, type);
        getPopable(row, column - 1, path, type);
        getPopable(row, column + 1, path, type);
    }

    public PathNode getPopable(int row, int column){
        PathNode result = new PathNode();
        getPopable(row, column, result, this.board[row][column]);
        return result;
    }

    public List<PathNode> getAllPopable() {
        List<PathNode> result = new ArrayList<PathNode>();
        Set<Position> tested = new HashSet<Position>();
        for (int i = 0; i != 10; i++) {
            for (int j = 0; j != 10; j++) {
                if(this.board[i][j] == BLOCK_TYPE.BLOCK_EMPTY){
                    continue;
                }
                PathNode popable = getPopable(i, j);
                boolean valid = true;
                for (Position position : popable.path) {
                    if (tested.contains(position)) {
                        valid = false;
                        break;
                    }
                }
                if(popable.path.size() == 1) {
                    continue;
                }
                if (valid) {
                    result.add(popable);
                }
                for (Position position : popable.path) {
                    tested.add(position);
                }
            }
        }
        return result;
    }

    public Board pop(PathNode nodes){
        Board newBoard = new Board(this);
        for(Position position: nodes.path){
            newBoard.board[position.rowIndex][position.columnIndex] = BLOCK_TYPE.BLOCK_EMPTY;
        }
        return newBoard;
    }

    public String toString(){
        Map<BLOCK_TYPE, String> block2string = new HashMap<BLOCK_TYPE, String>(){{
            put(BLOCK_TYPE.BLOCK_BLUE, "b");put(BLOCK_TYPE.BLOCK_EMPTY, " ");
            put(BLOCK_TYPE.BLOCK_GREEN, "g");put(BLOCK_TYPE.BLOCK_RED, "r");
            put(BLOCK_TYPE.BLOCK_PURSE, "p"); put(BLOCK_TYPE.BLOCK_YELLOW, "y");
        }};
        String result = "";
        for (int row = 0;row != 10;row++){
            for(int column = 0;column != 10;column++){
                result += block2string.get(this.board[row][column]);
            }
        }
        return result;
    }
}
