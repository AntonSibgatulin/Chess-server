package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.BufferOverflowException;
import java.sql.SQLException;

import Bot.BotController;
import Systems.Controller;
import WebServer.WebsocketServer;
import org.json.JSONException;
import org.json.JSONObject;
import register.Register;
import register.antiDDoS;

public class Server {
	
public static Controller mainController;
    public static void main(String[] args) throws IOException, UnknownHostException, JSONException {
		FileReader file = new FileReader(new File("sock.cfg"));
		BufferedReader buf = new BufferedReader(file);
		String out = "";
		String str  ="";

		while((str = buf.readLine())!=null){
			out = out+str;
		}
		JSONObject json = new JSONObject(out);
		String ip = json.getString("IP");
		long PORT = json.getInt("PORT");
     //   String board = "rnbUkbnrpppppppp11111111111111111111111111111111PPPPPPPPRNBQKBNR";
      // Chunk.getFiguresLadia(Chunk.getMassBoard(board),Chunk.getPosition(63));
     // System.out.println("valid login: "+isValidLogin("Admen")+";valid password: "+isValidPassword("Dert869&*4%))("));
      WebsocketServer s = new WebsocketServer();//1
        Register res = new Register(s.connectionDB);

        mainController = new Controller(s,res);
        res.start();
	//	antiDDoS dos = new antiDDoS();
		//dos.start();
		//mainController.dos = does;
		//bot.tables.start();
        //mainController.start();
     
        s.start();
		/*BotController bot = new BotController(1);
		bot.t.start();*/
		//bot.tables.start();

		String[] arg = {"auth","login","auth;login;true","auth;login;false","tabless","table","move","game","join","tables","create","tabless;all;"};

new Thread(new Runnable(){
	@Override
	public void run(){
		while(true){
			try {
				Thread.sleep(60000);
				int t =0;
				for(Thread th :Thread.getAllStackTraces().keySet()){
					if(th.getState()==Thread.State.RUNNABLE)t++;
					
				}
				System.out.print(" Thread: "+t+"\n");
				s.connectionDB.updateData();
			} catch (InterruptedException | SQLException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			}
		}
	}
}).start();
    }

}