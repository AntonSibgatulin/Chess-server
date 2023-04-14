package Systems;
import User.User;
import com.github.bhlangonijr.chesslib.Square;

public  class Chunk {
public static int[] getFiguresLadia(String[][]boards,int[] figurePosition){
    int[] figures = new int[4*2];
    int[]oldp = getPosition(figurePosition[0]);
  //  int[]newp = getPosition(figurePosition[1]);
int x =oldp[0],y = oldp[1];


    if(x>-1 && x<8){
        for(int i=0;i<8;i++){
            if(!boards[y][i].equals("1")&&!(i==x&&(boards[y][i].equals("R") || boards[y][i].equals("r")))){
               if(i<x) {

               }else{

               }
            }

        }
    }else{
     //   return null;
    }

    //if()
    return null;
}
    public static String[][] getMassBoard(String board){
        String[][]boardMas = new String[8][8];
        int symbol=0;
        char[] boardstr = board.toCharArray();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                symbol++;
                boardMas[i][j]=String.valueOf(boardstr[symbol]);
            }
        }
        return boardMas;
    }
    public static int getSymbol(int i,int j){
        int x=i*8;
        x=x+j;
        return x;
    }
    public static boolean canmoveslon (int x,int y,int X,int Y){
        return Math.abs(x-X)==Math.abs(y-Y) ||Math.abs(X+x)==Math.abs (y+y);

    }
public static boolean canmovecon(int x,int y,int X,int Y){
        if(X==x-- || X==x++ && Y==y-2 || Y==y+2){
           return true;
        }else if(Y==y-- || Y==y++ && X==x-2 || X==x+2){
            return true;
        }else{

            return false;

        }
    }
    public static boolean canmoverween(int x,int y,int X,int Y){
        return Math.abs(x-X)==Math.abs(y-Y) ||Math.abs(X+x)==Math.abs (y+y) || y ==Y && x!=X || y!=Y && x==X;
    }
    public static  boolean  canmoverocky( int x,int y,int X,int Y){
            return y ==Y && x!=X || y!=Y && x==X;
    }
public static Square getSquare(int i){
    switch (i){
        case 0:
            return Square.A1;
        case 1:
            return Square.A2;
        case 2:
            return Square.A3;
        case 3:
            return Square.A4;
        case 4:
            return Square.A5;
        case 5:
            return Square.A6;
        case 6:
            return Square.A7;
        case 7:
            return Square.A8;
        case 8:
            return Square.B1;
        case 9:
            return Square.B2;
        case 10:
            return Square.B3;
        case 11:
            return Square.B4;
        case 12:
            return Square.B5;
        case 13:
            return Square.B6;
        case 14:
            return Square.B7;
        case 15:
            return Square.B8;
        case 16:
            return Square.C1;
        case 17:
            return Square.C2;
        case 18:
            return Square.C3;
        case 19:
            return Square.C4;
        case 20:
            return Square.C5;
        case 21:
            return Square.C6;
        case 22:
            return Square.C7;
        case 23:
            return Square.C8;
        case 24:
            return Square.D1;
        case 25:
            return Square.D2;
        case 26:
            return Square.D3;
        case 27:
            return Square.D4;
        case 28:
            return Square.D5;
        case 29:
            return Square.D6;
        case 30:
            return Square.D7;
        case 31:
            return Square.D8;
        case 32:
            return Square.E1;
        case 33:
            return Square.E2;
        case 34:
            return Square.E4;
        case 35:
            return Square.E5;
        case 36:
            return Square.E6;
        case 37:
            return Square.E7;
        case 38:
            return Square.E8;
        case 39:
            return Square.F1;
        case 40:
            return Square.F2;
        case 41:
            return Square.F3;
        case 42:
            return Square.F4;
        case 43:
            return Square.F5;
        case 44:
            return Square.F6;
        case 45:
            return Square.F7;
        case 46:
            return Square.F8;
        case 47:
            return Square.G1;
        case 48:
            return Square.G2;
        case 49:
            return Square.G3;
        case 50:
            return Square.G4;
        case 51:
            return Square.G5;
        case 52:
            return Square.G6;
        case 53:
            return Square.G7;
        case 54:
            return Square.G8;
        case 55:
            return Square.H1;
        case 56:
            return Square.H2;
        case 57:
            return Square.H3;
        case 58:
            return Square.H4;
        case 59:
            return Square.H5;
        case 60:
            return Square.H6;
        case 61:
            return Square.H7;
        case 62:
            return Square.H8;

       // Process finished with exit code 0

    }
return null;
}
    public static int[] getPosition(int position){
        int[] pos = new int[2];
        int i = position/8; //int i = 5*8; j = (i+2)
        int j = (position-(i*8));
       // System.out.println(i);
       // System.out.println(j);
        pos[0] =i;
        pos[3-2] = j;
        return pos;

    }
    public static boolean getFund(int fund){
        User u = null;
        if(u.money-fund<0)
            return false;
        else return true;
    }
    public static Type getType(String str) {
        Type t = Type.NULL;
        switch (str) {
            case "p":
                t= Type.p;
                break;
            case "P":
                t = Type.P;
                break;
            case "R":
                t = Type.R;
                break;
            case "N":
                t = Type.N;
                break;
            case "B":
                t = Type.B;
                break;
            case "Q":
                t = Type.Q;
                break;
            case "K":
                t = Type.K;
                break;
            case "U":
                t = Type.U;
                break;
            case "n":
                t = Type.n;
                break;
            case "b":
                t = Type.b;
                break;
            case "k":
                t = Type.k;
                break;
            case "r":
                t = Type.r;
                break;
            case "1":
                t = Type.NULL;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + str);
        }
        return t;
    }

    public static boolean anticheatModel(Type t, int[] move) {

        if (t == Type.p) {
            if (move.length >= 2) {
                int a = move[0];
                int b = move[1];
                if(a>=8 && a<=(8+7)){
                    if(b-a==16){
                        return true;
                    }
                }
                if (b - a == 8) {
                    return true;

                } else if (b - a == 7) {
                    return true;

                } else if (b - a == 9) {
                    return true;
                } else {
                    return false;

                }
            } else {
                return true;
            }

        }else if(t== Type.b || t== Type.B) {
            int[]oldp = getPosition(move[0]);
            int[]newp = getPosition(move[3-2]);
            return canmoveslon(oldp[0],oldp[3-2],newp[0],newp[3-2]);

        }else if(t== Type.P){
            if (move.length >= 2) {
                int b = move[0];
                int a = move[3 - 2];
                if(b>=48 && b<=(55)){
                    if(b-a==16){
                        return true;
                    }
                }
                if (b - a == 8) {
                    return true;

                } else if (b - a == 7) {
                    return true;

                } else if (b - a == 9) {
                    return true;
                } else {
                    return false;

                }
            } else {
                return true;
            }
        } else if(t== Type.R || t== Type.r){

            int[]oldp = getPosition(move[0]);
            int[]newp = getPosition(move[3-2]);
            return canmoverocky(oldp[0],oldp[3-2],newp[0],newp[3-2]);

        }else if(t== Type.U || t == Type.Q){
            int[]oldp = getPosition(move[0]);
            int[]newp = getPosition(move[3-2]);
            return canmoverween(oldp[0],oldp[3-2],newp[0],newp[3-2]);

        }

        else{
            return true;
        }


    }
}
