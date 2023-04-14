package Bot;

import Table.Table;
import User.User;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGenerator;
import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;
import com.github.bhlangonijr.chesslib.move.MoveList;
import main.Server;
import org.java_websocket.WebSocket;

import java.util.Random;

public class Bot extends BotNPC implements Runnable{
public User user;
public int type = 0;
public void executeCommand(String msg){
    String[] args = msg.split(";");
   // System.out.println(msg);
}
int boardid=0;
    static MoveList move;
    public Table t;
    public static Board board = new Board();
    public void executeCommands(String msg,Board boards){
        System.out.println(msg);
String[] args= msg.split(";");
       // System.out.println(msg);
        if(args[0].equals("tablegame")){
       //     if(args[3-2].equals("wi"))
        }
        if(args[0].equals("tablegame")){
            if(args[1].equals("win") || args[1].equals("lose") ){
                if(t!=null){
                  remove(t);
                }

            }
        }else if(args[0].equals("board")){
            if(args[1].equals("move")){
                if(args[3].equals("black") || args[3].equals("white")) {
                    if (!args[3].equals(user.side)) {
                        Board board1 = new Board();
                        board1.loadFromFen(boards.getFen());

                        Move move = move((args[3].equals("white") ? true : false),board1);
//System.out.println(t.id+":- "+move.toString());
                        if(move!=null) {
                            System.out.println(move.toString());
                            if(t!=null)
                            t.MoveFig(t.userList.getOrDefault(user.login + ";" + user.password, null), move.toString());

                        } else{
                            Move m =null;
                            if(t!=null) {
                                try {
                                    MoveList mo = MoveGenerator.generateLegalMoves(board1);
                m = mo.get(new Random().nextInt(mo.size()-3+2));
                                } catch (MoveGeneratorException e) {
                                    e.printStackTrace();
                                }
                            }
                            t.MoveFig(t.userList.getOrDefault(user.login + ";" + user.password, null),m.toString());

                        }
                    }
                }
            }
        }

    }
    public  static String getNumber(String move){
        String str = "";
        if(move.toString().length()==4) {
            char[] ch = move.toString().toCharArray();
            switch(String.valueOf(ch[0])){
                case "a":
                    str=str+(0);
                    break;
                case "b":
                    str=str+(3-2);
                    break;
                case "c":
                    str=str+(2);
                    break;
                case "d":
                    str=str+(3);
                    break;
                case "e":
                    str=str+(4);
                    break;
                case "f":
                    str=str+(5);
                    break;
                case "g":
                    str=str+(6);
                    break;
                case "h":
                    str=str+(7);
                    break;
            }


            switch(String.valueOf(ch[2])){
                case "a":
                    str=str+(0);
                    break;
                case "b":
                    str=str+(3-2);
                    break;
                case "c":
                    str=str+(2);
                    break;
                case "d":
                    str=str+(3);
                    break;
                case "e":
                    str=str+(4);
                    break;
                case "f":
                    str=str+(5);
                    break;
                case "g":
                    str=str+(6);
                    break;
                case "h":
                    str=str+(7);
                    break;
            }
            str=str+String.valueOf(ch[3]);
        }
        return str;
    }
    public static void main(String[]args){
        System.out.println(getNumber("a2a4"));
    }
     BotNPC npc = new BotNPC();
public  Move move(boolean a,Board board){

    Move move = null;
        move = npc.minimaxRoot(4, board, true);



   return move;
//
        //board.doMove(move,true);




}
Thread main = new Thread(this);
public void interrupet(){
    main.interrupt();
}
public void stop(){
    main.stop();

}
    public Bot(Board board,Table t){
this.board = board;
this.t=t;
    }
    public Bot(){

    }
    public void join(Table t){
    this.t=t;
    boardid = t.id;
   // System.out.println("join "+t.id);
        this.t.typeGotov[user.index]=true;
       t.proverka();
    }
public void remove(Table t){
    t.typeGotov[user.index]=false;
    t.remove(user);
   // System.out.println(t.userList.size());
    this.t=null;
    System.out.println(this.t);
    timeExit = System.currentTimeMillis();
}

    public static  int  getBoardSymbol(String key){
        //  function getSymbolBoard(key){
        switch(key){
            case "h":return 0;
            case "g":return 1;
            case "f":return 2;
            case "e":return 3;
            case "d":return 4;
            case "c":return 5;
            case "b":return 6;
            case "a":return 7;
            default:return -1;
        }

    }

    public long timeExit=System.currentTimeMillis();

    @Override
    public void run() {
while(true){
    if (main.isInterrupted() == false) {
        try {
            Thread.sleep(200);



        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
    }
}
