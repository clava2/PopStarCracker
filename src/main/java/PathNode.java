import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PathNode {
    public Set<Position> path;

    public PathNode() {
        this.path = new HashSet<Position>();
    }
    public void add(Position p) {
        this.path.add(p);
    }

    public void add(int row, int column){
        this.path.add(new Position(row, column));
    }

}
