package register;

import main.Server;
import org.java_websocket.WebSocket;

import java.util.Random;

public class Commands extends Thread{
    public int id;
    Captcha captcha = new Captcha(new Random().nextInt(79)+21);
    public WebSocket socket;
    public String remote;
    public String[] data;
    public  String str;
    private long time;
    public void sendCaptcha(){
        if(socket!=null){
            if(socket.isFlushAndClose()==false){
                socket.send("game;captcha;"+id+";"+captcha.captcha.getCaptchaString());
            }else{
                Server.mainController.removeCommands(id);
            }
        }else{
            Server.mainController.removeCommands(id);

        }
    }

    public void sendAllright(){
        if(socket!=null){
            if(socket.isFlushAndClose()==false){
                socket.send("game;allright;"+id+";");
                if(Server.mainController.dat.registerUser(data[0],data[1])){
                    socket.send("game;register;true;");

                    Server.mainController.removeCommands(id);
                }else{
                    socket.send("game;register;false;");
                    Server.mainController.removeCommands(id);
                }
            }else{
                Server.mainController.removeCommands(id);
            }
        }else{
            Server.mainController.removeCommands(id);

        }
    }
    int i =0;
public void getProverka(Integer i){
    if(i!=null){
        if(i==captcha.equally){
          sendAllright();
        }else{
            if(this.i>=3){
                if(socket.isFlushAndClose()==false){
                    socket.send("game;limit");
                }
                Server.mainController.removeCommands(id);
            }else {
             if(socket.isFlushAndClose()==false) {
              socket.send("game;noncaptcha");
                this.i++;
             }else{
                 Server.mainController.removeCommands(id);

             }
            }
        }
    }

}

    public Commands(int id, String cmd, long time, WebSocket socket){
        this.str=cmd;
        this.id = id;
        this.time = time;
data = str.split(";");
remote = socket.getRemoteSocketAddress().getAddress().getHostAddress();
        this.socket = socket;
        sendCaptcha();
       
    }
    public WebSocket getSocket(){
        return socket;
    }
    public long getTime(){
        return time;
    }
    public String getCmd(){
        return str;
    }

    @Override
    public void run(){
    while(true){
        try {
            if(isInterrupted()==true) {
                Thread.sleep(1000);
                if (System.currentTimeMillis() - time > 30*1000) {
                    Server.mainController.removeCommands(id);
                    
                }
                
            }else{
                break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    }
}
