import org.junit.jupiter.api.Test;

public class TestBoard {
    @Test
    public void shouldConstructBoard(){
        Board boardFromString = new Board("                                                                                          rgbpyrgbpy");
        Board expectedBoard = new Board(new BLOCK_TYPE[][]{
                {BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY},
                {BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY},
                {BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY},
                {BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY},
                {BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY},
                {BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY},
                {BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY},
                {BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY},
                {BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY,BLOCK_TYPE.BLOCK_EMPTY},
                {BLOCK_TYPE.BLOCK_RED, BLOCK_TYPE.BLOCK_GREEN, BLOCK_TYPE.BLOCK_BLUE,BLOCK_TYPE.BLOCK_PURSE, BLOCK_TYPE.BLOCK_YELLOW, BLOCK_TYPE.BLOCK_RED,BLOCK_TYPE.BLOCK_GREEN,BLOCK_TYPE.BLOCK_BLUE,BLOCK_TYPE.BLOCK_PURSE,BLOCK_TYPE.BLOCK_YELLOW}
        });
        assert boardFromString.equals(expectedBoard);
    }
    @Test
    public void shouldDrop(){
        Board original = new Board("                                                                                         yrgbpyrgbp ");
        Board expected = new Board("                                                                                          rgbpyrgbpy");
        original.drop();
        assert original.equals(expected);

        original = new Board("                                                                                r bpyrgbpyr bpyrgbpy");
        expected = new Board("                                                                                rbpyrgbpy rbpyrgbpy ");
        original.drop();
        assert original.equals(expected);

        original = new Board("                                                                                r bpyrgbpyr bpyrgbp ");
        expected = new Board("                                                                                rbpyrgbp  rbpyrgbpy ");
    }
}
