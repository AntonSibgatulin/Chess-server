package User;

import Bot.Bot;
import com.github.bhlangonijr.chesslib.Board;
import org.java_websocket.WebSocket;

public class User {
    public String[] message ={"","",""};
    public long[] timeMessage = {};
    public long chatBan = 0;
    public Bot botU;
    public boolean bot = false;
    /**
     * @author AntonSibgatulin
     */
  //  public GameTheBoard game;
            public int ban = 0;
    public int id;
    public int win;
    public WebSocket con;
    public int over;
   // public ServerSomthing server;
    public String login;
public long currentTime=0l;
    public boolean accept=false;
    public String password;
   // public JSONObject obj;
    public String gettableId(){
        return tableId;
    }
public String tableId;
    public int money=5000;
    public int gold;
public String tableN ="";
public String side="none";
public void flip(){
    if(side.equals("white")){
        side = "black";
    }else{
        side = "white";
    }
}
public int boardIdC=0;
public void setTime(){
    boardcreate=System.currentTimeMillis();
}
public void addBoard(){
    boardIdC++;
}
public boolean try_create(){
    if(userType == TypeUser.ADMIN || userType==TypeUser.BOT)
        return true;
    if(boardIdC>=3){
        if(System.currentTimeMillis()-boardcreate>=(500+500)*(60)*3){
            boardIdC=0;
            return true;
        }else {
            return false;
        }
    }else if(boardIdC<=2 && boardIdC>=0){

        return true;
    }else {
        return false;
    }
    //if(System.currentTimeMillis()-boardcreate[3])
}
public long boardcreate;

@Override
    public String toString(){
        return "login;"+login+";money;"+money;
    }
    public String getAccept(){
        return ""+accept;
    }
public int index;
public TypeUser userType= TypeUser.DEFAULT;
public String[] lastTimeCommand = new String[4];
public void send(String msg, Board board){
new Thread(new Runnable() {
    @Override
    public void run() {
        if (con != null && !bot) {

            if (con.isFlushAndClose() == false && con.isConnecting() == false && con.isClosing()==false){


                con.send(msg);
            }

        }else{
            if(bot==true && con==null){
                botU.executeCommands(msg,board);
            }
        }
    }
}).start();

}
    public void send(String msg) {
new Thread(new Runnable() {
    @Override
    public void run() {
        if (con != null && !bot) {

            if (con.isFlushAndClose() == false && con.isConnecting() == false && con.isClosing()==false){


                con.send(msg);
            }

        }else{

        }
    }
}).start();
 // con.isFlushAndClose();
    }


    public void send(WebSocket con ,String msg) {

        if (con != null && !bot) {

            if (con.isFlushAndClose() == false || con.isConnecting()==true || con.isClosing()==false){
                con.send(msg);
            }

        }else{
            if(bot){
                if(botU!=null){
                    botU.executeCommand(msg);
                }
            }
        }
        // con.isFlushAndClose();


    }

    public User(boolean bot,String login,String password,TypeUser type){
        this.bot = bot;
        this.login = login;
        this.password = password;
        this.userType = type;
        botU= new Bot();
        botU.user=this;
        //Andrey234 Tolikit Jimmys Flamingos Harrys Antoni Stark Tonnys halking
        // initwrite();
    }
    public  User(String log,String password,int wine,int overe){
        this.login =log;
        this.password = password;
        this.win = wine;
        this.over = overe;
    }

   /* public User(Socket socket,ServerSomthing server){
        this.socket = socket;
        this.server = server;
    }*/


}