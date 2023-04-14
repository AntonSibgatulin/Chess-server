package Table;

import Systems.Chunk;
import User.User;
import WebServer.WebsocketServer;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.BoardEvent;
import com.github.bhlangonijr.chesslib.BoardEventListener;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGenerator;
import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;
import database.DatabasConnect;
import main.Server;
import org.java_websocket.WebSocket;

import javax.swing.Timer;
import javax.swing.event.DocumentEvent.EventType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import User.TypeUser;
public class Table extends Thread {
    public int id = 0;
    public String lastMove = "";
    public int ctavka = 0;
    public String name = "TestBoard";
    private long time = 60000;

    public long getTime() {
        return time;
    }

    private Board boardDesktop = new Board();
    public Map<String, User> userList = new HashMap<String, User>();
    public List<User> viewListener = new ArrayList<>();
    WebsocketServer web;
    public boolean gameStart = false;
    public int fund = 0;
    DatabasConnect deb;
    String[] logins = new String[2];
    public String history = "";

    public void proverka() {
        if (typeGotov[0] && typeGotov[1]) {
            if(userList.size()>1) {
                gameStart = true;
                whiteTime = System.currentTimeMillis();
                blackTime = whiteTime;
int i=0;
                Iterator ii = userList.entrySet().iterator();
                while (ii.hasNext()) {
                    i++;
                    Map.Entry pair = (Map.Entry) ii.next();
                    // System.out.println(pair.getKey() + " = " + pair.getValue());
                    User user = (User) pair.getValue();
                    history = history + user.login + "|";
                    //if (user.userType != TypeUser.BOT)
                        user.send("board;move;" + boardDesktop.getBoard() + ";" + (boardDesktop.getSideToMove() == Side.WHITE ? "black" : "white") + ";" + lastMove,boardDesktop);
                  /*  else {
                        user.botU.executeCommands("board;move;" + (boardDesktop.getSideToMove() == Side.WHITE ? "black" : "white"), boardDesktop);
                      if(i==1)  ii = userList.entrySet().iterator();
                    }*/
                }
            }
        }

    }

    public boolean[] typeGotov = new boolean[2];
    public User user;
    public long timeDeleteTable = 30000L;
    public long timeCreate = System.currentTimeMillis();
    public int timer = 500;
    public long whiteTime, blackTime;
    public void TimeOut(Side side) {
        String c = side == Side.WHITE ? "white" : "black";
        Iterator i = userList.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry pair = (Map.Entry) i.next();
            User user = (User) pair.getValue();
            if (c.equals(user.side)) {
                //if (user.userType != TypeUser.BOT) {
                    user.send("game;timeout;",boardDesktop);
                    user.send("account;money;" + user.money,boardDesktop);

               // } else user.botU.executeCommands("you;lose;", boardDesktop);


                user.money = user.money - ctavka;

                deb.getloser(user);
                break;
                //   user.send("tables;remove;" + id,boardDesktop);
                //  user.send("tablegame;lose;" + (-ctavka),boardDesktop);


            }
        }
        gameStart = false;

        if (c.equals("white"))
            updateBoard("black");
        else
            updateBoard("white");

    }
    public Timer t = new Timer(timer, new ActionListener() {


        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(userList.size()>2) {
                Iterator i = userList.entrySet().iterator();
                while (i.hasNext()) {
                    Map.Entry en = (Map.Entry) i.next();
                    User us = (User) en.getValue();
                 //   if(us.botU!=null)
                 //   userList.remove(us.login + ";" + us.password);
                   remove(us);
                    break;

                }
            }
            if(gameStart) {
                if (boardDesktop.isKingAttacked()) {

                }
                if (boardDesktop.isMated()) {

                    // System.out.println("mate");
                    gameStart = false;
                    updateBoard(boardDesktop.getSideToMove() != Side.WHITE ? "white" : "black");
                }


                if (boardDesktop.isStaleMate()) {
                    //System.out.println("pat");

                    gameStart = false;
                    updateBoard("pat");
                }
            }
            if (gameStart && userList.size() >= (2)) {
                if (boardDesktop.getSideToMove() == Side.WHITE) {
                    if (System.currentTimeMillis() - whiteTime >= timer * 120) {

                        TimeOut(boardDesktop.getSideToMove());
                    } else {
                        //  System.out.println(System.currentTimeMillis()-whiteTime);
                        // TimeOut(boardDesktop.getSideToMove());
                    }
                } else if (boardDesktop.getSideToMove() == Side.BLACK) {
                    if (System.currentTimeMillis() - blackTime >= timer * 120)
                        TimeOut(boardDesktop.getSideToMove());
                    else {
                        // System.out.println(System.currentTimeMillis()-blackTime);
                    }
                }
            } else if (gameStart && userList.size() == 1) {
                t.stop();
            } else if (!gameStart) {
                if (userList.size() > 0) {
                    Iterator it = userList.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry) it.next();
                        //System.out.println(pair.getKey() + " = " + pair.getValue());
                        User user = (User) pair.getValue();
                        if (typeGotov[user.index] != true) {
                            if (user.index == 0) {
                                if (System.currentTimeMillis() - whiteTime >= 16000L) {

                                    remove(user);

                                } else {
                                    //System.out.println(System.currentTimeMillis()-whiteTime);
                                }
                            } else {
                                if (System.currentTimeMillis() - blackTime >= 16000L) {
                                    remove(user);


                                } else {
                                    //   System.out.println(System.currentTimeMillis()-blackTime);
                                }
                            }

                        }
                        break; // avoids a ConcurrentModificationException
                    }
                }
            }

        }
    });

    @Override
    public void run() {
        while (true) {
            try {
                if (super.isInterrupted() == false)
                    if (!gameStart && userList.size() == 0) {
                        Thread.sleep(1000);
                        if (System.currentTimeMillis() - timeCreate >= timeDeleteTable) {

                            super.interrupt();
                            Server.mainController.remove(this);

                            break;
                        } else {
                            //   System.out.println(System.currentTimeMillis()-timeCreate);
                        }
                    }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void remove(User user) {
        if (user.userType != TypeUser.BOT) {
            user.send("tables;remove;" + id,boardDesktop);

            user.send("tables;exit;",boardDesktop);


        }else {
            user.botU.t=null;
        }
        userList.remove(user.login + ";" + user.password);
        new Thread(new Runnable(){
            @Override
            public void run(){
                Server.mainController.web.upd(Table.this);
            }
        }).start();
    }
    // winPlayerOneBecouseTwoPlayerToDoExit();
    public boolean addUserView(User user) {
        boolean ret = false;
        if (viewListener.size() < 100) {
            if (user != null) {
                ret = true;
                viewListener.add(user);
            }
        }


        return ret;
    }

    public boolean getChessSide(Side s, String str) {
        if (s == Side.WHITE) {
            switch (str) {
                case "K":
                    return true;
                case "Q":
                    return true;
                case "R":
                    return true;
                case "N":
                    return true;
                case "P":
                    return true;
                case "B":
                    return true;
                default:
                    return false;
            }
        } else {

            switch (str) {
                case "k":
                    return true;
                case "q":
                    return true;
                case "r":
                    return true;
                case "n":
                    return true;
                case "p":
                    return true;
                case "b":
                    return true;
                default:
                    return false;
            }
        }
    }

    public boolean getFigures(int old) {

        if (old <= boardDesktop.getBoard().toString().length()) {
            char[] mas = boardDesktop.getBoard().toCharArray();
            if (getChessSide(boardDesktop.getSideToMove(), String.valueOf(mas[old]))) {
                return true;
            } else
                return false;
        } else return false;

    }

    public void returnBoards() {

        Iterator<User> i = viewListener.iterator();
        if (viewListener.size() > 0) {
            while (i.hasNext()) {
                User e = i.next();
                //   e.send("board;move;" + boardDesktop.getBoard()+";main");
                    user.send("board;move;" + boardDesktop.getBoard() + ";main",boardDesktop);
            }
        }
    }

    public void returnAllboard() {


        Iterator it = userList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            //   System.out.println(pair.getKey() + " = " + pair.getValue());
            User user = (User) pair.getValue();
            //  System.out.println("board;move;" + boardDesktop.getBoard());
                user.send("board;move;" + boardDesktop.getBoard() + ";main",boardDesktop);
            }
    }

    private void updateBoard(String str) {

        //    if(gameStart==false){
        timeCreate = System.currentTimeMillis();
        for (int u = 0; u < typeGotov.length; u++) {
            typeGotov[u] = false;
        }
        boardDesktop = new Board();

        Iterator it = userList.entrySet().iterator();
        while (it.hasNext()) {

            Map.Entry pair = (Map.Entry) it.next();
            User user = (User) pair.getValue();
            if (str.equals("pat")) {
            //    if (user.userType != TypeUser.BOT) {
                    user.send("game;end;pat",boardDesktop);
              /*  } else {
                    user.botU.executeCommands("you;pat;", boardDesktop);
                    it = userList.entrySet().iterator();
                }*/
                boolean ret = deb.addMoneyFundcuzGetUser(user, fund / 2);
                deb.drawU(user);
            } else if (str.equals(user.side)) {
                boolean ret = deb.addMoneyFundcuzGetUser(user, fund);

                deb.getwin(user);
                history = history + user.login;
                deb.addBoardGame(history);
                history = "";
               // if (user.userType != TypeUser.BOT) {
                    user.send("tablegame;win;" + fund / 2,boardDesktop);
                    // user.send("tablegame;end;" + user.money);
                    user.send("account;money;" + user.money,boardDesktop);
                    // user.send("tables;remove;" + id);
            /*    } else {
                    user.botU.executeCommands("you;win;", boardDesktop);
                    it = userList.entrySet().iterator();
                }*/
            } else {

                deb.getloser(user);
                user.money = user.money - ctavka;
                //  user.send("tables;exit;",boardDesktop);
               // if (user.userType != TypeUser.BOT) {
                    user.send("account;money;" + user.money,boardDesktop);
                    //   user.send("tables;remove;" + id);
                    user.send("tablegame;lose;" + (-ctavka),boardDesktop);

              /*  } else
                    user.botU.executeCommands("you;lose;", boardDesktop);
                it = userList.entrySet().iterator();
            }*/
          //  if (user.userType != TypeUser.BOT) {
                user.send("position;board;" + user.index,boardDesktop);
                user.send("game;restart;",boardDesktop);
                //   userList.remove(user);
                // return;
           /* } else {
                user.botU.timeExit = System.currentTimeMillis();
            }
            */


        }
            if(user.userType==TypeUser.BOT)
                it=userList.entrySet().iterator();
        }
        whiteTime = System.currentTimeMillis();
        blackTime = whiteTime;
    }

    public void returnbase(User conn) {
        //if (conn.userType != TypeUser.BOT) {
            conn.send("board;move;" + boardDesktop.getBoard() + ";main");
       // } else {
         //   conn.botU.executeCommands("board;move;main;", boardDesktop);
        //}
    }

    public boolean removeUser(String str) {

        boolean darknight = false;
        String[] cmd = str.split(";");
        if (cmd.length >= 3) {
            //  System.out.println(">2");
            if (cmd[0].equals("removeuser")) {
                //System.out.println("removeuser");

                if (userList.size() > 0) {
//System.out.println(">0");
                    User e = userList.getOrDefault(cmd[1] + ";" + cmd[2], null);
                    if (e != null) {
                        if (gameStart) {
                            //   System.out.println("User!=null");

                            e.money = e.money - ctavka;
                            e.send("tables;exit;");
                            e.send("account;money;" + e.money);

                            e.send("tables;remove;" + id);
                            e.send("tablegame;lose;" + (-ctavka));
                            userList.remove(e.login + ";" + e.password);
                            deb.getloser(e);
                            if (userList.size() == 1) {
                                Iterator i = userList.entrySet().iterator();
                                while (i.hasNext()) {
                                    Map.Entry pair = (Map.Entry) i.next();
                                    User user = (User) pair.getValue();

                                        user.send("tablegame;exitenemy;",boardDesktop);

                                }
                            }
                            darknight = true;
                        } else {
                            if (typeGotov[e.index]) {
                                boolean ret = deb.addMoneyFundcuzGetUser(user, ctavka);
                                e.send("tables;exit;",boardDesktop);
                                e.send("account;money;" + e.money,boardDesktop);
                                e.send("tables;remove;" + id,boardDesktop);
                                typeGotov[e.index] = false;
                                userList.remove(e.login + ";" + e.password);

                                if (userList.size() == 1) {
                                    Iterator i = userList.entrySet().iterator();
                                    while (i.hasNext()) {
                                        Map.Entry pair = (Map.Entry) i.next();
                                        User user = (User) pair.getValue();

                                            user.send("tablegame;exitenemy;",boardDesktop);

                                    }
                                }
                                darknight = true;

                            } else {
                                e.send("tables;remove;" + id);
                                e.send("tables;exit;");
                                userList.remove(e.login + ";" + e.password);


                                darknight = true;
                            }
                        }
                    }

                }
            }


        }
        if (gameStart) {
            if (userList.size() == 1) {
                winPlayerOneBecouseTwoPlayerToDoExit();
            }
        }


        return darknight;
    }

    public void executeCommand(String str) {


        String[] cmd = str.split(";");
        if (cmd.length >= 2) {


            if (!gameStart) {
                if (cmd[0].equals("board")) {
                    if (cmd[3 - 2].equals("Done")) {
                        boolean done = Boolean.valueOf(cmd[4]);
                        //  System.out.println("board;Done");
                        if (userList.size() > 0) {
                            // System.out.println("board;Done>0");
                            if (cmd[3 - 2] != null && cmd[3] != null) {
                                user = userList.getOrDefault(cmd[2] + ";" + cmd[3], null);
                                if (user != null) {
                                    // System.out.println("user!=null");
                                    if (done == true) {
                                        fund = fund + ctavka;
                                        boolean ret = deb.addMoneyFundcuzGetUser(user, -ctavka);
                                        //      System.out.println("main.Server: "+ret);
                                        if (ret == true) {
                                            user.send("board;move;" + boardDesktop.getBoard() + ";main",boardDesktop);
                                            user.send("account;money;" + user.money,boardDesktop);
                                            logins[user.index] = user.login;

                                            // user.send("position;boardreturn;" + user.index,boardDesktop);

                                            typeGotov[user.index] = done;
                                        } else {
                                            userList.remove(user);
                                        }
                                    }


                                }
                                if (user.index == 1) {
                                    returnAllboard();
                                }
                            }
                        } else {
                            // System.out.println("user==null");
                        }
                    }


                }

                int j = 0;
                Iterator it = userList.entrySet().iterator();
                while (it.hasNext()) {

                    Map.Entry pair = (Map.Entry) it.next();
                    User user = (User) pair.getValue();
                    if (user == null) {
                        if (typeGotov[j] == true) {
                            typeGotov[j] = false;
                        }
                    }
                    j++;

                }
                if (typeGotov[0] && typeGotov[1]) {
                    gameStart = true;
                    whiteTime = System.currentTimeMillis();
                    blackTime = whiteTime;
                    Iterator ii = userList.entrySet().iterator();
                    while (ii.hasNext()) {
                        Map.Entry pair = (Map.Entry) ii.next();
                        // System.out.println(pair.getKey() + " = " + pair.getValue());
                        User user = (User) pair.getValue();
                        history = history + user.login + "|";
                       // if (user.userType != TypeUser.BOT)
                            user.send("board;move;" + boardDesktop.getBoard() + ";" + (boardDesktop.getSideToMove() == Side.WHITE ? "black" : "white") + ";" ,boardDesktop);
                        //else user.botU.executeCommands("board;move;" + (boardDesktop.getSideToMove() == Side.WHITE ? "black" : "white"), boardDesktop);
                    }
                }
            } else {
                boolean rem = false;
                Iterator it = userList.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    User user = (User) pair.getValue();
                    if (user.con != null && user.bot != true)
                        if (user.con.isFlushAndClose() == false) {
                            rem = true;
                            userList.remove(user);

                        }


                    //it.remove(); // avoids a ConcurrentModificationException
                }
                if (rem = true) {
                    winPlayerOneBecouseTwoPlayerToDoExit();
                }
                if (userList.size() == 2) {

                    if (cmd.length >= 2) {
                        if (cmd[0].equals("table")) {
                            if (cmd[1].equals("move")) {
                                User user = userList.getOrDefault(cmd[5] + ";" + cmd[6], null);
                                if (user != null)
                                    if (getUseranMove(user)) {
                                        if (cmd[3] != null && cmd[4] != null) {
                                            if (viewListener.size() >= 1) {
                                                returnBoards();
                                            }
                                            String mes = cmd[3] + ";" + cmd[4];
                                            game(user, mes);
                                        }
                                    }
                            }
                        }
                    }
                } else if (userList.size() == 1) {
                    winPlayerOneBecouseTwoPlayerToDoExit();
                } else {

                }
            }


        }


    }

    public String getBoardSymbol(int key) {
        //  function getSymbolBoard(key){
        switch (key) {
            case 0:
                return "h";
            case 1:
                return "g";
            case 2:
                return "f";
            case 3:
                return "e";
            case 4:
                return "d";
            case 5:
                return "c";
            case 6:
                return "b";
            case 7:
                return "a";
            default:
                return null;
        }

    }

    public String[] getMoveCode(int old[], int[] last) {
        if (old.length >= 2 && last.length >= 2) {

            String s = "" + getBoardSymbol(old[1]) + "" + (char) (old[0] + 49) + "" + getBoardSymbol(last[1]) + "" + (char) (last[0] + 49);
            String j = "" + getBoardSymbol(old[1]) + "" + (char) (old[0] + 49) + ";" + getBoardSymbol(last[1]) + "" + (char) (last[0] + 49);

            return new String[]{s, j};
        } else {
            return null;
        }
    }

    public void game(User users, String message) {
///////////////////////////////////////////

        String[] e = message.split(";");

        int[] old = Chunk.getPosition(Integer.parseInt(e[0]));
        int[] last = Chunk.getPosition(Integer.parseInt(e[1]));

        String[] str = getMoveCode(old, last);
        if (str != null) {
//System.out.println("str");
            try {
Move move =MoveGenerator.isValidMove(boardDesktop, str[0]);
                if (move!=null) {


                    boardDesktop.doMove(new com.github.bhlangonijr.chesslib.move.Move(move.toString(), boardDesktop.getSideToMove()));

                    lastMove = e[0] + ";" + e[1];
                    history = history + lastMove + "|";
                    if (boardDesktop.getSideToMove() == Side.WHITE) {

                        whiteTime = System.currentTimeMillis();
                    } else {
                        blackTime = System.currentTimeMillis();
                    }
                    //System.out.println(Integer.parseInt(e[0])+" : "+Integer.parseInt(e[1]));
                    if (boardDesktop.isKingAttacked()) {
                        Iterator it = userList.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry e2 = (Map.Entry) it.next();
                            User user = (User) e2.getValue();
                            if (user.side.equals(boardDesktop.getSideToMove() == Side.WHITE ? "white" : "black")) {
                                user.send("impotantMes;0",boardDesktop);
                            }
                        }
                    }
                    if (boardDesktop.isMated()) {
                        Iterator it = userList.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry e2 = (Map.Entry) it.next();
                            User user = (User) e2.getValue();
                            if (!user.side.equals(boardDesktop.getSideToMove() == Side.WHITE ? "white" : "black")) {
                                user.send("impotantMes;1",boardDesktop);
                            }
                        }
                        // System.out.println("mate");
                        gameStart = false;
                        updateBoard(boardDesktop.getSideToMove() != Side.WHITE ? "white" : "black");
                    }


                    if (boardDesktop.isStaleMate()) {
                        //System.out.println("pat");
                        Iterator it = userList.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry e2 = (Map.Entry) it.next();
                            User user = (User) e2.getValue();
                            user.send("impotantMes;2",boardDesktop);

                        }
                        gameStart = false;
                        updateBoard("pat");
                    }
                    Iterator it = userList.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry) it.next();
                        // System.out.println(pair.getKey() + " = " + pair.getValue());
                        User user = (User) pair.getValue();
                        new Thread(new Runnable(){

                            @Override
                            public void run() {
                                user.send("board;move;" + boardDesktop.getBoard() + ";" + (boardDesktop.getSideToMove() == Side.WHITE ? "black" : "white") + ";" +e[0] + ";" + e[1],boardDesktop);

                            }
                        }).start();
                    }

                }else {
                    users.send("board;move;" + boardDesktop.getBoard() + ";test;" ,boardDesktop);


                }
            } catch (MoveGeneratorException moveGeneratorException) {
                moveGeneratorException.printStackTrace();
                //  System.out.println(moveGeneratorException);
            }
            // boardDesktop.doMove(new com.github.bhlangonijr.chesslib.move.Move())



        } else {
            users.send("board;move;" + boardDesktop.getBoard() + ";test;" ,boardDesktop);


        }


        ///////////////////////////////////////
    }

    private boolean getUseranMove(User user) {
        if (user.side.equals("white") && boardDesktop.getSideToMove() == Side.WHITE) {
            return true;
        } else if (user.side.equals("black") && boardDesktop.getSideToMove() == Side.BLACK) {
            return true;
        } else
            return false;
    }

    public void winPlayerOneBecouseTwoPlayerToDoExit() {
        if (userList.size() == 1 || userList.size() == 0 && gameStart) {
            Iterator it = userList.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                // System.out.println(pair.getKey() + " = " + pair.getValue());
                User u = (User) pair.getValue();


                boolean ret = deb.addMoneyFundcuzGetUser(u, fund);
                //    deb.getwin(u);
                history = history + u.login;
                deb.addBoardGame(history);
                history = "";
                u.money = u.money + ctavka;
                if (u.userType != TypeUser.BOT) {
                    u.send("tablegame;win;" + fund / 2);
                    u.send("tablegame;end;" + u.money);
                    u.send("account;money;" + u.money);
                    u.send("tables;remove;" + id);
                } else { u.botU.executeCommands("you;winEnd;", boardDesktop); }
                //    userList=null;
                deb.getwin(u);
                Server.mainController.remove(this);

            }

        } else {
            //    Server.mainController.remove(this);
        }
    }

    public boolean addUser(User us) {
        boolean ret = false;
        if (us != null) {


            if (userList.size() < 2) {
                int i = 3;
                if (userList.size() >= (3 - 2)) {

                    Iterator it = userList.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry) it.next();
                        //  System.out.println(pair.getKey() + " = " + pair.getValue());
                        User user = (User) pair.getValue();
                        i = user.index;


                        //it.remove(); // avoids a ConcurrentModificationException
                    }
                }
                if (i == 3) {
                    us.index = 0;
                } else if (i == 0) {
                    us.index = (3 - 2);
                } else if (i == (3 - 2)) {
                    us.index = 0;
                }
                if (us.index == 0) {
                    whiteTime = System.currentTimeMillis();
                } else {
                    blackTime = System.currentTimeMillis();
                }
                ret = true;

                if (us.index == 0) {
                    us.side = "white";

                } else {
                    us.side = "black";
                }
                if (userList.size() == 1) {
                    Iterator it = userList.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry) it.next();
                        //  System.out.println(pair.getKey() + " = " + pair.getValue());
                        User user = (User) pair.getValue();
                       // if (user.userType != TypeUser.BOT)
                            user.send("board;name;" + us.login + ";" + us.money,boardDesktop);
                      //  if (us.userType != TypeUser.BOT)
                            us.send("board;name;" + user.login + ";" + us.money,boardDesktop);

                        //it.remove(); // avoids a ConcurrentModificationException
                    }
                }
                us.send("position;board;" + us.index);
                userList.put(us.login + ";" + us.password, us);
              //  if (us.userType != TypeUser.BOT)
new Thread(new Runnable(){
@Override
    public void run(){
    Server.mainController.web.upd(Table.this);
}
}).start();
                if (us.userType == TypeUser.BOT) {
                    us.botU.join(this);
                }

                deb.boardBattle(us, id);

                //  Thankyou();

            } else {
                us.send("board;error;manypeople;");
                //System.out.println("too") ;
            }
        }
        return ret;
    }
//"R1K1111RP111111P11111P1B1N11P11111nN1111111p1111ppp11Ppp1k1r1b1r"
    public boolean updateUser(User u) {
        boolean c = false;

        Iterator it = userList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            //System.out.println(pair.getKey() + " = " + pair.getValue());

            User user = (User) pair.getValue();
            System.out.println(user.login+" "+u.login);
            if (user.login.equals(u.login)) {
                u.index = user.index;
                u.side = user.side;

                u.send("game;return;boards;" + id,boardDesktop);

                u.send("position;boardreturn;" + u.index,boardDesktop);
                returnbase(u);
                userList.remove(user);
                userList.put(u.login + ";" + u.password, u);
                return true;
                //   user.send("tables;remove;" + id + ";",boardDesktop);
            }

            // System.out.println(typeGotov[0]);
            // System.out.println(typeGotov[1]);
            //it.remove(); // avoids a ConcurrentModificationException
        }
        return false;
    }

    public void returnUserName(User user) {
        Iterator i = userList.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry pair = (Map.Entry) i.next();
            User u = (User) pair.getValue();
           // if (user.userType != TypeUser.BOT) {
                if (!u.login.equals(user.login)) {
                    user.send("board;name;" + u.login + ";" + u.money,boardDesktop);
                    u.send("board;name;" + user.login + ";" + user.money,boardDesktop);
                    user.send("board;move;" + boardDesktop.getBoard() + ";" + (boardDesktop.getSideToMove() == Side.WHITE ? "black" : "white") + ";" ,boardDesktop);
                    user.send("game;removetimers",boardDesktop);
                }
          //  } else {
                //user.botU.executeCommands("board;move;main;",boardDesktop);
           // }

        }
    }

    ///////////////////////////////////
    public Table(int ctavka, String name, int id, DatabasConnect dab, WebsocketServer web) {
        if (ctavka > 500000) ctavka = 500000;
        if (ctavka <= 2) ctavka = 50;
        this.web = web;
        this.deb = dab;
        t.start();
        this.ctavka = ctavka;
        if (name.length() <= 3||name.length()>13) {
            this.name = "defName" + id;
        } else
            this.name = name;
        this.id = id;
    }


    public void MoveFig(User users, String move) {
///////////////////////////////////////////
     //   System.out.println(move);

        if (move != null && move.length() >= 3) {
//System.out.println("str");

            //if(MoveGenerator.isValidMove(boardDesktop,move)){


            boardDesktop.doMove(new Move(move, boardDesktop.getSideToMove()));
            char[] e = move.toCharArray();
            lastMove = String.valueOf(e[0]) + ";" + String.valueOf(e[1]);
            history = history + lastMove + "|";
            if (boardDesktop.getSideToMove() == Side.WHITE) {

                whiteTime = System.currentTimeMillis();
            } else {
                blackTime = System.currentTimeMillis();
            }
            //System.out.println(Integer.parseInt(e[0])+" : "+Integer.parseInt(e[1]));
            if (boardDesktop.isKingAttacked()) {
                Iterator it = userList.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry e2 = (Map.Entry) it.next();
                    User user = (User) e2.getValue();
                    if (user.side.equals(boardDesktop.getSideToMove() == Side.WHITE ? "white" : "black")) {
                        user.send("impotantMes;0",boardDesktop);
                    }
                }
            }
            if (boardDesktop.isMated()) {
                Iterator it = userList.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry e2 = (Map.Entry) it.next();
                    User user = (User) e2.getValue();
                    if (!user.side.equals(boardDesktop.getSideToMove() == Side.WHITE ? "white" : "black")) {
                        user.send("impotantMes;1",boardDesktop);
                    }
                }
                // System.out.println("mate");
                gameStart = false;
                updateBoard(boardDesktop.getSideToMove() != Side.WHITE ? "white" : "black");
            }


            if (boardDesktop.isStaleMate()) {
                //System.out.println("pat");
                Iterator it = userList.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry e2 = (Map.Entry) it.next();
                    User user = (User) e2.getValue();
                    user.send("impotantMes;2",boardDesktop);

                }
                gameStart = false;
                updateBoard("pat");
            }
            //}
            // boardDesktop.doMove(new com.github.bhlangonijr.chesslib.move.Move())

            Iterator it = userList.entrySet().iterator();
            while (it.hasNext()) {

                Map.Entry pair = (Map.Entry) it.next();
                // System.out.println(pair.getKey() + " = " + pair.getValue());
                User user = (User) pair.getValue();
                if (user.userType != TypeUser.BOT)
                    user.send("board;move;" + boardDesktop.getBoard() + ";" + (boardDesktop.getSideToMove() == Side.WHITE ? "black" : "white") + ";",boardDesktop);
             //   else
                //    user.botU.executeCommands("board;move;" + (boardDesktop.getSideToMove() == Side.WHITE ? "black" : "white"), boardDesktop);
           //  if(j==1)
             //   it = userList.entrySet().iterator();
            }


        } else {
            user.send("board;move;" + boardDesktop.getBoard() + ";" + (boardDesktop.getSideToMove() == Side.WHITE ? "black" : "white") + ";",boardDesktop);


        }


        ///////////////////////////////////////
    }
//////////////////////////////////

}
