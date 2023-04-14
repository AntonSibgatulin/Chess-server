package Bot;

import com.github.bhlangonijr.chesslib.Side;

public class Figure {
public Side side;
String fig;
@Override
public String toString(){
    return fig;
}
public void ret(String piece){
    fig = piece;
    if (piece.equals("p")) {
       side = Side.BLACK;
    } else if (piece.equals("r")) {
        side = Side.BLACK;
    } else if (piece.equals("n")) {
        side = Side.BLACK;
    } else if (piece.equals("b")) {
        side = Side.BLACK;
    } else if (piece.equals("q")) {
        side = Side.BLACK;
    } else if (piece.equals("k")) {
        side = Side.BLACK;
    }
    else if (piece.equals("P")) {
        side = Side.WHITE;
    } else if (piece.equals("R")) {
        side = Side.WHITE;
    } else if (piece.equals("N")) {
        side = Side.WHITE;
    } else if (piece.equals("B")) {
        side = Side.WHITE;
    } else if (piece.equals("Q")) {
        side = Side.WHITE;
    }else if(piece.equals("K")){
        side = Side.WHITE;
    }else    side = Side.WHITE;
}

    public Figure(String str ){
ret(str);
    }
}
