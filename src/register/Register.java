package register;

import database.DatabasConnect;
import org.java_websocket.WebSocket;

import java.util.*;
import java.util.regex.Pattern;

public class Register extends Thread {
    public int i = 0;
    antiDDoS DDoS = new antiDDoS();
  public  Map<Integer, Commands> comList = new HashMap<>();
    Set<WebSocket> list = new HashSet<WebSocket>();
    public DatabasConnect database;
    public Register(DatabasConnect database)
    {
        this.database=database;
    }
    public void removenull(){
        for(WebSocket sock : list){
            if(sock==null ||sock.isFlushAndClose()==true)
                list.remove(sock);
        }
    } public boolean getHaveWeb(WebSocket conns){
        removenull();
        for(WebSocket sock:list){
            if(sock.getRemoteSocketAddress().getHostName().equals(conns)){
return true;
            }
        }
return false;
    }
public boolean isValidLogin(String cmd){
    Pattern pat = Pattern.compile("^[A-Za-z]([.A-Za-z0-9-]{1,10})([A-Za-z0-9])$");
if(cmd.length()>3&&cmd.length()<=10)
    return pat.matcher(cmd).matches();
else
    return false;
    }
    public boolean isValidPassword(String cmd){
        Pattern pat = Pattern.compile("^[A-Za-z]([.A-Za-z0-9-]{1,10})([A-Za-z0-9])$");
        if(cmd.length()>=6&&cmd.length()<=20)
            return pat.matcher(cmd).matches();
        else
            return false;
    }
    public Commands  getCaptcha(WebSocket con){
        Iterator i = comList.entrySet().iterator();
        while(i.hasNext()){
            Map.Entry pair = (Map.Entry)i.next();
            Commands com = (Commands)pair.getValue();
            if(com.socket!=null && con!=null){
            	
          if(com.remote.equals(con.getRemoteSocketAddress().getAddress().getHostAddress())){
           //  System.out.println(com.socket.getRemoteSocketAddress().getAddress().getHostAddress());
        	  System.out.println(com.remote);
          	
        	  return com;
                
            }
        }else{
                comList.remove(com.id).interrupt();
                break;
            }
        }
        return null;
    }
    public void executeCommands(String commands, WebSocket con) {
        long time = System.currentTimeMillis();
        //  Commands com = new Commands(commands,time,con);

        String[] c = commands.split(";");
        if (c.length >= 3){
            if (c[0].equals("register")) {
                if (c[1] != null && isValidLogin(c[1])) {
                    if (c[2] != null && isValidPassword(c[2])) {
                        int j = i++;
                       Commands coman =  getCaptcha(con);
                       if(coman!=null){
                    	   coman.socket = con;
                    	   coman.str = c[1] + ";" + c[2];
                    	   coman.data = coman.str.split(";");
                    	   coman.sendCaptcha();
                       }else{
                            Commands com = new Commands(j, c[1] + ";" + c[2], time, con);
                            com.start();
                            comList.put(j, com);
                       }

                    } else {
                        con.send("game;unvalid;pass");
                    }
                } else {
                    con.send("game;unvalid;login");
                }
            }else if(c[0].equals("captcha")){
                if(c[1].equals("id")){

                    Integer i = Integer.valueOf(c[2]);
                    Commands com = comList.getOrDefault(i,null);
                    if(com!=null){
                        if(c[3]!=null){
                            Integer a = Integer.valueOf(c[3]);
                                    com.getProverka(a);
                        }
                    }else{
                        con.send("game;outcap");
                    }

                }
            }
    }
    }




    @Override
    public void run(){
        while(true){
            try {
                Thread.sleep(50);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
