import java.awt.*;

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

    //getters
    public int getNum(){
        return num;
    }

    public String getColor() {
        return color;
    }

    public String getFilling() {
        return filling;
    }

    public String getShape() {
        return shape;
    }

    public String getSrc() {
        return src;
    }
}
