import java.util.ArrayList;
import java.util.List;

public class ActionGraphNode {
    public Board node;
    public List<String> outNode;
    public List<PathNode> action;
    public int bestScore;
    public List<PathNode> bestWay;

    public ActionGraphNode(Board board) {
        this.node = board;
        this.outNode = new ArrayList<String>();
        this.action = new ArrayList<PathNode>();
        this.bestScore = 0;
        this.bestWay = new ArrayList<PathNode>();
    }

    public void addEdge(PathNode action, String target){
        this.outNode.add(target);
        this.action.add(action);
    }
}
