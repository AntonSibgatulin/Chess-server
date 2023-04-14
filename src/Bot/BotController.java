package Bot;

import Table.Table;
import User.User;
import main.Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class  BotController extends Thread {
String pass="bsdfhabdsafbhsdabfjhas";
List<Table> unempty = new ArrayList<Table>();


public javax.swing.Timer t = new javax.swing.Timer(20,new ActionListener(){

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

            if(users.size()<controller) {
                for (int j = 0; j < login.length; j++) {
                    User user = users.getOrDefault(login[j], null);
                    if (user == null) {
                        addBot(login[j], 0);
                    }

                }
            }
            Iterator i = users.entrySet().iterator();
            while(i.hasNext()){
                Map.Entry pair = (Map.Entry)i.next();
                User e = (User)pair.getValue();
                //    System.out.println(e.bot);
                if(e.bot==false){
                    users.remove(e.login);
                    break;
                }

              //  System.out.println(System.currentTimeMillis()-e.botU.timeExit);
                if(System.currentTimeMillis()-e.botU.timeExit>=20000&&e.botU.t==null){
                     new Thread(new Runnable(){

                    @Override
                    public void run() {
                        if (Server.mainController.web.tableListTest.size() >= 1 ) {
                            Iterator it = Server.mainController.web.tableListTest.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry pair = (Map.Entry)it.next();
                                Table u = (Table) pair.getValue();

                                if (u != null)
                                    if (u.gameStart == false && u.userList.size()<2) {
                                     System.out.println("iteration - "+e.botU.type);
                                        u.addUser(e);
                                       // System.out.println("Iterator "+(System.currentTimeMillis()-e.botU.timeExit));

                                        break;
                                    }


                            }
                        }else if(Server.mainController.web.tableListTest.size()<controller){
                            Server.mainController.web.createTableBot();

                        }
                    }
                }).start();

                }else{
            if(e.botU.t!=null && !e.botU.t.gameStart && System.currentTimeMillis()-e.botU.timeExit>=75000){
                e.botU.remove(e.botU.t);
            }
                }

                //   System.out.println(users.size());

            }


    }
});
public boolean have(Table t){
    if(t.gameStart==true||t.userList.size()==2) {
        return true;
    }
        for(int i =0;i<unempty.size();i++) {
            Table y = unempty.get(i);
            if(y.id==t.id||y.userList.size()==2){
                if(y.gameStart)unempty.remove(i);

                    return true;
            }
        }

    return false;
}
public Set<String> s= new HashSet<>();//Set<String>();
public Thread tables = new Thread(new Runnable(){
    @Override
    public void run() {
while (true){
        try {
            Thread.sleep(2500);

            if(Server.mainController.web.tableListTest.size()<9){
                Server.mainController.web.createTableBot();

            }

          //  System.out.println(Server.mainController.web.tableListTest.size());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

}
    }
});
    String[] login = {"petter0","peyton","Pyton","luis","Sibgatulin","Marshall","TonnyS","jimmys",
    "Bot","Harryk","IgorWin","GameChessM","Chesses"};
    Map<String,User> users = new HashMap<String,User>();

    public int controller = 0;
public int controllerBlack=0;
public BotController(int controller){
    this.controller = controller;

}
public BotController (int controller ,int Blackcontroller){
    this.controller = controller;
    this.controllerBlack = Blackcontroller;

}
public void addBot(String login,int i ){
    User user = Server.mainController.dat.getBot(login);
    user.botU.type=0;
    if(users.size()<controller) {
System.out.println(user.login +" - bot login");
        users.put(user.login, user);
    }
}
public void addBot(int i){
   User user = Server.mainController.dat.getBot(login[i]);
    if(users.size()!=controller) {

        users.put(user.login, user);
    }
}

    @Override
    public void run() {

    }
}
