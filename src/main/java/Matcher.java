import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class Matcher {
    private int[][] templates = new int[5][42 * 42];

    public BLOCK_TYPE[][] matchAll(BufferedImage image){
        Image scaledImage = image.getScaledInstance(420,420, Image.SCALE_AREA_AVERAGING);
        BufferedImage bufferedScaledImage = new BufferedImage(420,420,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedScaledImage.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        graphics.dispose();

        BLOCK_TYPE[][] result = new BLOCK_TYPE[10][10];
        for (int i = 0;i != 10;i++){
            for(int j = 0;j != 10;j++){
                int x = j * 42;
                int y = i * 42;
                BufferedImage subImage = bufferedScaledImage.getSubimage(x, y, 42, 42);
                result[i][j] = this.matchSingle(subImage);
            }
        }
        return result;
    }

    public double calculateError(int[] rgbs, int[] standard){
        double error = 0;
        for (int i = 0;i != rgbs.length;i++){
            int p = rgbs[i];
            int s = standard[i];
            int errorA = (((p & 0xff000000) >> 24) - ((s & 0xff000000) >> 24));
            int errorR = (((p & 0x00ff0000) >> 16) - ((s & 0x00ff0000) >> 16));
            int errorG = (((p & 0x0000ff00) >> 8) - ((s & 0x0000ff00) >> 8));
            int errorB = (p & 0x000000ff) - (s & 0x000000ff);
            error += errorA * errorA + errorR * errorR + errorG * errorG + errorB * errorB;
        }
        return error;
    }

    public BLOCK_TYPE matchSingle(BufferedImage candidate){
        int[] rgbs = candidate.getRGB(0,0,42, 42, null, 0, 42);
        double error = 1e9;
        int result = 0;
        for(int i = 0;i != templates.length;i++){
            int[] standard = templates[i];
            double tempError = calculateError(rgbs, standard);
            if(tempError < error) {
                error = tempError;
                result = i;
            }
        }
        Map<Integer, BLOCK_TYPE> id2type = new HashMap<Integer, BLOCK_TYPE>(){{
            put(0, BLOCK_TYPE.BLOCK_PURSE);
            put(1, BLOCK_TYPE.BLOCK_YELLOW);
            put(2, BLOCK_TYPE.BLOCK_RED);
            put(3, BLOCK_TYPE.BLOCK_GREEN);
            put(4, BLOCK_TYPE.BLOCK_BLUE);
        }};
        return id2type.get(result);

    }


    public int[] readImageRGB(String templatePath) throws IOException {
        BufferedImage tempImage = ImageIO.read(new File(templatePath));
        Image scaledImage = tempImage.getScaledInstance(42,42, Image.SCALE_AREA_AVERAGING);
        BufferedImage bufferedScaledImage = new BufferedImage(42,42,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedScaledImage.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        graphics.dispose();
        int[] RGBs = bufferedScaledImage.getRGB(0,0,42,42,null, 0, 42);
        return RGBs;
    }

    public int[][] pixelAverage(int[][] inputRGBs){
        int[][] resultRGBs = new int[inputRGBs[0].length][4];
        int counter = 0;
        for(int[] RGBs : inputRGBs){
            for(int j = 0;j != resultRGBs.length;j++){
                int a = (RGBs[j] & 0xff000000) >> 24;
                int r = (RGBs[j] & 0x00ff0000) >> 16;
                int g = (RGBs[j] & 0x0000ff00) >> 8;
                int b = (RGBs[j] & 0x000000ff);
                resultRGBs[j][0] += a; resultRGBs[j][1] += r;
                resultRGBs[j][2] += g; resultRGBs[j][3] += b;
            }
        }
        return resultRGBs;
    }

    public int[] constructPixel(int[][] departedPixel){
        int[] finalRGBs = new int[42 * 42];
        for(int i = 0;i != finalRGBs.length;i++){
            finalRGBs[i] = ((departedPixel[i][0] / 10) << 24) +
                    ((departedPixel[i][1] / 10) << 16) +
                    ((departedPixel[i][2] / 10) << 8) +
                    (departedPixel[i][3] / 10);
        }
        return finalRGBs;
    }

    public Matcher(String templatePath) throws IOException {
        String[] types = new String[]{"purse", "yellow", "red", "green", "blue"};
        for(int i = 0;i != types.length;i++) {
            int[][] rawRGBs = new int[10][42 * 42];
            for (int j = 1; j != 11; j++)
                rawRGBs[j - 1] = readImageRGB(templatePath + "\\" + types[i] + "\\" + j + ".png");
            int[][] resultRGBs = pixelAverage(rawRGBs);
            // 恢复图像
            this.templates[i] = constructPixel(resultRGBs);
        }
    }

    public static void main(String[] args){
        Matcher matcher = null;
        BufferedImage image = null;
        BufferedImage board = null;
        try {
            matcher = new Matcher("C:\\Users\\Cong\\IdeaProjects\\PopStarCracker\\data\\images\\templates");
            image = ImageIO.read(new File("C:\\Users\\Cong\\IdeaProjects\\PopStarCracker\\data\\images\\templates\\red\\1.png"));
            board = ImageIO.read(new File("C:\\Users\\Cong\\IdeaProjects\\PopStarCracker\\data\\images\\stages\\stage9.png"));
        } catch(IOException e){
            e.printStackTrace();
        }
        BLOCK_TYPE[][] result = matcher.matchAll(board);
        System.out.println(result);
    }
}
