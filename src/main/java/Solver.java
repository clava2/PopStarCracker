import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Solver {

    public static List<Board> sample(List<Board> original, int percentage){
        Random random = new Random();
        List<Board> result = new ArrayList<Board>();
        int judge = 0;
        for(Board board: original) {
            judge = random.nextInt(100);
            if(judge < percentage){
                result.add(board);
            }
        }
        return result;
    }
    public static void main(String[] args) {
        Matcher matcher = null;
        BufferedImage boardImage = null;
        try {
            matcher = new Matcher("C:\\Users\\Cong\\IdeaProjects\\PopStarCracker\\data\\images\\templates");
            boardImage = ImageIO.read(new File("C:\\Users\\Cong\\IdeaProjects\\PopStarCracker\\data\\images\\stages\\stage9.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BLOCK_TYPE[][] boardData = matcher.matchAll(boardImage);

        Board board = new Board(boardData);         // 第一个Board


        List<Board> queue = new ArrayList<Board>();
        ActionGraph actionGraph = new ActionGraph();
        queue.add(board);

        String template = "[%d] depth:%d queue length: %d, node count: %d\n";
        int count = 0;
        int depth = 1;
        queue.add(null);

        int percentage = 1;

        while (queue.size() > 1) {
            count += 1;
            if (count % 1000 == 0) {
                System.out.printf(template, count, depth, queue.size(), actionGraph.nodes.size());
            }
            Board tempBoard = queue.get(0);
            queue.remove(0);
            if(tempBoard == null){
                if (queue.size() > 10000) {
                    queue = sample(queue, percentage);
                }
                depth += 1;
                tempBoard = queue.get(0);
                queue.add(null);
            }
            List<PathNode> allPopables = tempBoard.getAllPopable();
            for (PathNode node : allPopables) {
                Board newBoard = tempBoard.pop(node);
                newBoard.drop();
                actionGraph.addEdge(tempBoard, node, newBoard);
                queue.add(newBoard);
            }
        }
        actionGraph.getBest(board);
        System.out.println(actionGraph);
    }
}
