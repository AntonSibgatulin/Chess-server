package Systems;

import Table.Table;
import WebServer.WebsocketServer;
import database.DatabasConnect;
import org.java_websocket.WebSocket;
import register.Register;
import register.antiDDoS;

public class Controller {
    public void executeCommands(String str, WebSocket conn){
        reg.executeCommands(str,conn);
    }
    Register reg ;
    public int i =0;
    public WebsocketServer web;


    public void removeCommands(Integer id){
        reg.comList.remove(id).interrupt();

    }
    public void remove(Table  table){

      //  System.out.println("controller yes");

        if(table!=null) {
            table.t.stop();
            if(table.isInterrupted()==false)
            table.interrupt();
         //   System.out.println("don't == null or 0 or false");
            web.tableListTest.remove(table);
            web.exit(table.id);
        }
    }
    public DatabasConnect dat;
    public Controller(WebsocketServer web, Register reg){
this.web = web;
this.reg = reg;
this.dat = web.connectionDB;
    }
    public antiDDoS dos;
}
