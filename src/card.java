import java.awt.*;

/**
 * class for cards
 */
public class card {
    String src;
    int num; //can be 1, 2, 3
    String color; //can be r, g, p
    String filling; //can be sol, str, opn
    String shape; //can be dmd, squ, ovl

    //constructor
    public card(int num, String color, String filling, String shape, String src) {
        this.num = num;
        this.color = color;
        this.filling = filling;
        this.shape = shape;
        this.src = src;
    }

    /**
     * get the number of shapes on the card
     * @return the number of shapes of card
     */
    public int getNum(){
        return num;
    }

    /**
     * get the color of card
     * @return the color of card
     */
    public String getColor() {
        return color;
    }

    /**
     * get the filling of card
     * @return the filling of card
     */
    public String getFilling() {
        return filling;
    }

    /**
     * get the shape on card
     * @return the shape on card
     */
    public String getShape() {
        return shape;
    }

    /**
     * get the corresponding source of card image
     * @return the source of card image
     */
    public String getSrc() {
        return src;
    }
}
