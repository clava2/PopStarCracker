import java.util.*;

public class ActionGraph {
    public Map<String, ActionGraphNode> nodes = new HashMap<String, ActionGraphNode>();

    public ActionGraph() {
    }

    public void addEdge(Board from, PathNode action, Board target){
        if (!this.nodes.containsKey(from.toString())){
            this.nodes.put(from.toString(), new ActionGraphNode(from));
        }
        if (!this.nodes.containsKey(target.toString())){
            this.nodes.put(target.toString(), new ActionGraphNode(target));
        }
        this.nodes.get(from.toString()).addEdge(action, target.toString());
    }

    public List<PathNode> getBest(Board root){
        String rootString = root.toString();
        int bestScore = 0;
        List<PathNode> result = new ArrayList<PathNode>();
        List<String> queue = new ArrayList<String>();
        queue.add(rootString);
        int counter = 0;
        String fmt = "[%8d] queue size: %d\n";
        while(queue.size() > 0){
            counter += 1;
            if(counter % 1000 == 0) {
                System.out.printf(fmt, counter, queue.size());
            }
            String tempBoardString = queue.get(0);
            queue.remove(0);
            ActionGraphNode node = this.nodes.get(tempBoardString);
            for(int i = 0;i != node.action.size();i++){
                int length = node.action.get(i).path.size();
                int score = length * length * 5;
                String outNodeString = node.outNode.get(i);
                queue.add(outNodeString);
                ActionGraphNode outNode = this.nodes.get(outNodeString);
                if (outNode.bestScore < node.bestScore + score) {
                    outNode.bestScore = node.bestScore + score;
                    outNode.bestWay = new ArrayList<PathNode>();
                    outNode.bestWay.addAll(node.bestWay);
                    outNode.bestWay.add(node.action.get(i));
                    if(outNode.bestScore > bestScore){
                        bestScore = outNode.bestScore;
                        result = outNode.bestWay;
                    }
                }
            }
        }
        return result;
    }
}
