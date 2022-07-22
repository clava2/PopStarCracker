public class Position {
    public int rowIndex = 0;
    public int columnIndex = 0;

    public Position(){
    }
    public Position(int rowIndex, int columnIndex){
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public boolean equals(Object other){
        if (!(other instanceof Position)){
            return false;
        }
        Position anotherPosition = (Position)other;
        return anotherPosition.rowIndex == this.rowIndex && anotherPosition.columnIndex == this.columnIndex;
    }

    public int hashCode(){
        return this.rowIndex * 10 + this.columnIndex;
    }
}
