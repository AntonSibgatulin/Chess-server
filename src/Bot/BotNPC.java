package Bot;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGenerator;
import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;
import com.github.bhlangonijr.chesslib.move.MoveList;

import javax.swing.*;

public class BotNPC {
    public double getAbsoluteValue ( String piece,int x,int y,Side side) {
        if (piece.equals("p")) {
            return 10+ ( side==Side.WHITE ? pawnEvalWhite[y][x] : pawnEvalBlack[y][x] );
        } else if (piece.equals("r")) {
            return 50+ ( side==Side.WHITE ? rookEvalWhite[y][x] : rookEvalBlack[y][x] );
        } else if (piece.equals("n")) {
            return 30+ ( side==Side.WHITE ? knightEval[y][x] : knightEval[y][x] );
        } else if (piece.equals("b")) {
            return 30+ ( side==Side.WHITE ? bishopEvalWhite[y][x] : bishopEvalBlack[y][x] );
        } else if (piece.equals("q")) {
            return 90+ ( side==Side.WHITE ? evalQueen[y][x] : evalQueen[y][x] );
        } else if (piece.equals("k")) {
            return 900+ ( side==Side.WHITE ? kingEvalWhite[y][x] : kingEvalBlack[y][x] );
        }else if (piece.equals("P")) {
            return 10+ ( side==Side.WHITE ? pawnEvalWhite[y][x] : pawnEvalBlack[y][x] );
        } else if (piece.equals("R")) {
            return 50+ ( side==Side.WHITE ? rookEvalWhite[y][x] : rookEvalBlack[y][x] );
        } else if (piece.equals("N")) {
            return 30+ ( side==Side.WHITE ? knightEval[y][x] : knightEval[y][x] );
        } else if (piece.equals("B")) {
            return 30+ ( side==Side.WHITE ? bishopEvalWhite[y][x] : bishopEvalBlack[y][x] );
        } else if (piece.equals("Q")) {
            return 90+ ( side==Side.WHITE ? evalQueen[y][x] : evalQueen[y][x] );
        } else if (piece.equals("K")) {
            return 900+ ( side==Side.WHITE ? kingEvalWhite[y][x] : kingEvalBlack[y][x] );
        }else return 0;
    };

    public double  getPieceValue (Figure piece,int x,int y) {
        if (piece == null) {
            return 0;
        }

        double absoluteValue = getAbsoluteValue(piece.toString(),x,y,piece.side);
      //  System.out.println(absoluteValue);
        //System.out.println(piece.side == Side.WHITE ? absoluteValue : -absoluteValue);
        return piece.side == Side.WHITE ? absoluteValue : -absoluteValue;
    };
    public  String[][] boardMas(String board){
        String[][]boardMas = new String[8][8];
        int symbol=0;
        char[] boardstr = board.toCharArray();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){

                boardMas[i][j]=String.valueOf(boardstr[symbol]);
                symbol++;
            }
        }
        return boardMas;
    }

    public double evaluateBoard  (String[][] board) {
        double totalEvaluation = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //System.out.println(y);
                totalEvaluation = totalEvaluation + getPieceValue(new Figure(board[i][j]),i,j);
            }
        }

        return totalEvaluation;
    };



   public  Move minimaxRoot(int depth, Board game, boolean isMaximisingPlayer) {
     //  JFrame j = new JFrame();
      // j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      // j.setSize(600,600);
      // Graphic gr = new Graphic();
       long open=System.currentTimeMillis();
       MoveList newGameMoves = null;
       double bestMove = -9999;
       Move bestMoveFound = null;

       try {
           newGameMoves = MoveGenerator.generateLegalMoves(game);
           for(int i = 0; i < newGameMoves.size(); i++) {
               if(System.currentTimeMillis()-open <=45*1000){

                   Move newGameMove = newGameMoves.get(i);
               game.doMove(newGameMove);
               double value = minimax(depth - 1, game, -1000, 1000, !isMaximisingPlayer);
              game.undoMove();
               if (value >= bestMove) {
                   System.out.println(sigmoid(value)+" "+newGameMove.toString());
                   System.out.println(sigmoid(value)*(1-sigmoid(value)));

                   bestMove = value;
                   // System.out.println(value);
                   bestMoveFound = newGameMove;
               }
           }else
               break;
           }
       } catch (MoveGeneratorException e) {
           e.printStackTrace();
       }


       //j.setVisible(true);
        return bestMoveFound;
    };
public double th(double x){
    return (Math.pow(Math.E,x)-Math.pow(Math.E,(-1*x)))/(Math.pow(Math.E,x)+Math.pow(Math.E,(-1*x)));
}
public double sigmoid(double x){
    return  (1/(1+(Math.pow(Math.E,(-1*x)))));
}
    public double minimax (int depth, Board game, double alpha,double  beta,boolean isMaximisingPlayer) throws MoveGeneratorException {

        if (depth == 0) {
            return -evaluateBoard(boardMas(game.getBoard()));
        }

        MoveList newGameMoves = MoveGenerator.generateLegalMoves(game);

        if (isMaximisingPlayer) {
            double  bestMove = -9999;
            for (int i = 0; i < newGameMoves.size(); i++) {
                game.doMove(newGameMoves.get(i));
                bestMove = Math.max(bestMove, minimax(depth - 1, game, alpha, beta, !isMaximisingPlayer));
                game.undoMove();
                alpha = Math.max(alpha, bestMove);
                if (beta <= alpha) {
                    return bestMove;
                }
            }
            return bestMove;
        } else {
            double bestMove = 9999;
            for (int i = 0; i < newGameMoves.size(); i++) {
                game.doMove(newGameMoves.get(i));
                bestMove = Math.min(bestMove, minimax(depth - 1, game, alpha, beta, !isMaximisingPlayer));
                game.undoMove();
                beta = Math.min(beta, bestMove);
                if (beta <= alpha) {
                    return bestMove;
                }
            }
            return bestMove;
        }
    };

    public double minimax1(int depth, Board game, boolean isMaximisingPlayer) {
        if (depth == 0) {
            return -evaluateBoard(boardMas(game.getBoard()));
        }
        MoveList newGameMoves = null;
        try {
            newGameMoves = MoveGenerator.generateLegalMoves(game);
            if (isMaximisingPlayer) {
                double bestMove = -9999;
                for (int i = 0; i < newGameMoves.size(); i++) {
                    game.doMove(newGameMoves.get(i));
                    bestMove = Math.max(bestMove, minimax1(depth - 1, game, !isMaximisingPlayer));
                    game.undoMove();
                }
                return bestMove;
            } else {
                double bestMove = 9999;
                for (int i = 0; i < newGameMoves.size(); i++) {
                    game.doMove(newGameMoves.get(i));
                    bestMove = Math.min(bestMove, minimax1(depth - 1, game, !isMaximisingPlayer));
                    game.undoMove();
                }
                return bestMove;
            }
        } catch (MoveGeneratorException e) {
            e.printStackTrace();
        }
            return 0;
    };
    public Move TheBestMove(Board board, Side side){
        double bestValue =(side==Side.WHITE?9999:-9999);
        Move bestMove = null;
        try {
            MoveList moves = MoveGenerator.generateLegalMoves(board);
            for (int  i = 0; i < moves.size(); i++) {
                Move newGameMove = moves.get(i);
                board.doMove(newGameMove);

                //take the negative as AI plays as black
                double boardValue = -evaluateBoard(boardMas(board.getBoard()));

                board.undoMove();
                //System.out.println("boardValue "+boardValue+",bestValue "+bestValue);
                if (boardValue > bestValue) {
                    bestValue = boardValue;
                    bestMove = newGameMove;
                }
            }
        } catch (MoveGeneratorException e) {
            e.printStackTrace();
        }
return bestMove;

    }








    double[][] pawnEvalWhite =
            {
    {0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0},
        {5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0},
        {1.0,  1.0,  2.0,  3.0,  3.0,  2.0,  1.0,  1.0},
        {0.5,  0.5,  1.0,  2.5,  2.5,  1.0,  0.5,  0.5},
        {0.0,  0.0,  0.0,  2.3,  2.3,  0.0,  0.0,  0.0},
        {0.5, -0.5, -1.0,  0.0,  0.0, -1.0, -0.5,  0.5},
        {0.5,  1.0, 1.0,  -2.0, -2.0,  1.0,  1.0,  0.5},
        {0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0}
        };
    public double[][] reverseArray(double[][] arr) {

        double arrR[][] = new double [arr[0].length][arr.length]; //поменять местами размерность
        for (int i = 0; i < arrR.length; i++){
            for (int j = 0; j <arrR[i].length; j++){
                arrR[i][j] = arr[j][i];
            }
        }
        return arrR;
    }

double[][] knight ={
        {0.0,1.0,2.0,2.0,2.0,2.0,1.0,0.0},
        {1.0,2.4,2.4,2.4,2.4,2.4,2.4,1.0},
        {1.0,2.8,4.0,5.0,5.0,4.0,2.8,1.0},
        {1.0,2.3,3.6,4.0,4.0,3.6,2.3,1.0},
        {1.0,2.2,2.8,3.0,3.0,2.8,2.2,1.0},
        {0.0,2.6,2.6,3.0,3.0,2.6,2.6,0.0},
        {0.5,2.0,2.0,2.3,2.0,2.0,2.0,0.5},
        {0.0,0.5,1.5,1.5,1.5,1.5,0.0,0.0}
};
    double[][] knightEval =
            {
        {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0},
        {-4.0, -2.0,  0.0,  0.0,  0.0,  0.0, -2.0, -4.0},
        {-3.0,  0.0,  1.0,  1.5,  1.5,  1.0,  0.0, -3.0},
        {-3.0,  0.5,  1.5,  2.0,  2.0,  1.5,  0.5, -3.0},
        {-3.0,  0.0,  1.5,  2.0,  2.0,  1.5,  0.0, -3.0},
        {-3.0,  0.5,  1.0,  1.5,  1.5,  1.0,  0.5, -3.0},
        {-4.0, -2.0,  0.0,  0.5,  0.5,  0.0, -2.0, -4.0},
        {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0}
            };
double[][] pawn = {

        {3.6,3.6,3.6,3.6,3.6,3.6,3.6,3.6},
        {3.6,3.6,3.6,3.6,3.6,3.6,3.6,3.6},
        {1.4,1.4,2.0,2.4,2.4,2.0,1.6,1.6},
        {1.0,1.3,1.3,2.0,2.0,1.8,1.2,1.2},
        {0.6,1.2,1.2,2.0,2.0,1.0,1.0,1.0},
        {1.2,1.2,1.0,1.2,1.2,1.0,5.5,5.0},
        {1.0,1.0,1.0,0.0,0.0,1.4,5.5,5.0},
        {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}

};
    double[][] bishopEvalWhite = {
            { -2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0},
            { -1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0},
        { -1.0,  0.0,  0.5,  1.0,  1.0,  0.5,  0.0, -1.0},
        { -1.0,  0.5,  0.5,  1.0,  1.0,  0.5,  0.5, -1.0},
        { -1.0,  0.0,  1.0,  1.0,  1.0,  1.0,  0.0, -1.0},
        { -1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0, -1.0},
        { -1.0,  0.5,  0.0,  0.0,  0.0,  0.0,  0.5, -1.0},
        { -2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0}
        };

    double[][] bishopEvalBlack = reverseArray(bishopEvalWhite);

    double[][] rookEvalWhite = {
    {  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0},
        {  0.5,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        {  0.0,   0.0, 0.0,  0.5,  0.5,  0.0,  0.0,  0.0}
};

    double[][] rookEvalBlack = reverseArray(rookEvalWhite);

    double[][] evalQueen =
            {
    { -2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0},
                    { -1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0},
                    { -1.0,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -1.0},
        { -0.5,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5},
        {  0.0,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5},
        { -1.0,  0.5,  0.5,  0.5,  0.5,  0.5,  0.0, -1.0},
        { -1.0,  0.0,  0.5,  0.0,  0.0,  0.0,  0.0, -1.0},
        {-2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0}};

    double[][] kingEvalWhite = {

            {-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
            {-3.0, -4.0,  -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
            {-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
            {-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
            {-2.0, -3.0, -3.0, -4.0, -4.0, -3.0, -3.0, -2.0},
            {-1.0, -2.0, -2.0, -2.0, -2.0, -2.0, -2.0, -1.0},
            {2.0, 2.0, 0.0, 0.0, 0.0, 0.0, 2.0, 2.0},
            {2.0, 3.0, 1.0, 0.0, 0.0, 1.0, 3.0, 2.0}
    };
    public  double[][] kingEvalBlack = reverseArray(kingEvalWhite);
    double[][] pawnEvalBlack = reverseArray(pawn);
}
