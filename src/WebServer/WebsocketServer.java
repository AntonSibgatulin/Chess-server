package WebServer;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.*;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.DefaultSSLWebSocketServerFactory;
import org.java_websocket.server.WebSocketServer;



import Table.Table;
import User.TypeUser;
import User.User;
import chesspresso.game.Game;
import database.DatabasConnect;
import main.Server;

public class WebsocketServer extends WebSocketServer {
	
    WebsocketServer webserverLists = this;
public Map<String,Table> tableListTest = new HashMap<String ,Table>();
Map<WebSocket,User> webList = new HashMap<WebSocket, User>();

   public  DatabasConnect connectionDB = new DatabasConnect();
    private static int TCP_PORT = 4444;
    int j =0;
public boolean Auth(){

	//webserverLists.setSsl(tru;)
    return false;

}

Game g = new Game();
public void move(){

}

    private Set<WebSocket> conns;
    public Map<String,User> userList = new HashMap<>();
   // List<Table> tableList = new ArrayList<Table>();
//public Map<String,Table> boardList = new HashMap<String, Table>();
    public WebsocketServer() throws UnknownHostException {

        super(new InetSocketAddress(InetAddress.getByName("localhost"),TCP_PORT));


        conns = new HashSet<WebSocket>();
    }
public void getTable(WebSocket conn){
    Iterator it = tableListTest.entrySet().iterator();

    while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();

        //System.out.println(pair.getKey() + " = " + pair.getValue());
        Table t = (Table) pair.getValue();
        conn.send("tables;all;"+t.id+";"+t.name+";"+t.id+";"+t.ctavka+";"+t.userList.size()+";"+t.getTime()+";");

        // user.send("tables;remove;" +id+";");
        //it.remove(); // avoids a ConcurrentModificationException
    }
   /* for(int i =0;i<tableList.size();i++){
        Table t = tableList.get(i);

        conn.send("tables;all;"+i+";"+t.name+";"+t.id+";"+t.ctavka+";"+t.users+";"+t.getTime()+";");
    }*/

}

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        if(conns.size()<1500) {
            conns.add(conn);

            getTable(conn);
            System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress() + ":" + conn.getRemoteSocketAddress().getPort());

        }else{
            conn.send("error;toomany");
            conn.close();
        }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
         conns.remove(conn);
        System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
       // System.out.println(message);
        new Thread(new Runnable() {
            @Override
            public void run() {

                String[] str = message.split(";");
                if (str.length >= 3) {
                    if (str[0].equals("auth")) {
                        if (str[3 - 2].equals("login")) {
                            boolean log_in = Boolean.valueOf(str[2]);

                            if (log_in) {
                                if (str.length >= 5) {
                                    User use = connectionDB.getUser(str[3], str[4]);
                                    if (use != null) {
                                        User user = userList.getOrDefault(use.login + ";" + use.password, null);
                                        if (user != null) {
                                            use.boardcreate = user.boardcreate;
                                            use.boardIdC = user.boardIdC;
                                            user.con.close();

                                            userList.remove(user);

                                        }
                                        conn.send("auth;login;true;");
                                        use.con = conn;
                                        connectionDB.getTableId(use);
                                        if (use.ban == 2) {
                                            use.con.send("game;ban");
                                            use.con.close();
                                        }
                                        use.send("account;money;" + use.money);

                                        userList.put(use.login + ";" + use.password, use);
                                        if (use.tableId != null) {
                                            Table t = tableListTest.getOrDefault(use.tableId + "", null);
                                        System.out.println(use.tableId);
                                            if (t != null) {
                                                if (t.updateUser(use) == true) {
                                                    t.returnUserName(use);
                                                }
                                            }
                                        }
                                        webList.put(conn, use);
                                        // User u = userList.getOrDefault(use.login+";"+use.password, null);

                                        System.out.println("main.Server: user " + use.login + " log in... size " + userList.size());
                                    } else {
                                        conn.send("auth;login;false;");
                                        conn.isFlushAndClose();
                                        conn.close();

                                    }
                                }
                            }
                        }
                    } else if (str[0].equals("register")) {
                        Server.mainController.executeCommands(message, conn);
                    } else if (str[0].equals("captcha")) {
                        Server.mainController.executeCommands(message, conn);

                    } else if (str[0].equals("tabless")) {
                        int c = Integer.valueOf(str[3 - 2]);
                        String string = "";
                        for (int u = 2; u < str.length; u++) {
                            if (u > 2) {
                                string = string + ";" + str[u];
                            } else {
                                string = str[u];
                            }
                        }//System.out.println("Table "+string);
                        Table t = tableListTest.getOrDefault(c + "", null);
                        if (t != null) {
                            if (str[2].equals("removeuser")) {
                                boolean bol =
                                        t.removeUser(string);
                            } else {
                                t.executeCommand(string);
                            }
                        }
                        // if(tableList.get(c)!= null)
                        // tableList.get(c).executeCommand(string,conn);
                    } else if (str[0].equals("game")) {
                        if (str[3 - 2].equals("join")) {
                            int c = Integer.valueOf(str[2]);
                            if (str.length >= 5) {
                                if (str[3] != null && str[4] != null) {
                                    User user = userList.getOrDefault(str[3] + ";" + str[4], null);
                                    if (user != null) {
                                        //   System.out.println(user.userType);
                                        //  System.out.println("main.Server:touch " + str[3]);
                                        user.login = str[3];
                                        Table t = tableListTest.getOrDefault(c + "", null);
                                        if (t != null) {
                                            if (user.money - t.ctavka >= 0) {
                                                if (t.addUser(user)) {
                                                    t.returnbase(user);

                                                    conn.send("game;join;" + c);

                                                } else {
                                                    conn.send("board;error;manypeople");
                                                }
                                            } else {
                                                conn.send("board;error;moneyneed");
                                            }
                                        } else {
                                            conn.send("game;tablelist;notfound;" + c);

                                        }

                                    }
                                }
                            }
                        } else if (str[1].equals("removeTableAdmin")) {
                            //      System.out.println("remove table Admin");
                            User u = userList.getOrDefault(str[3] + ";" + str[4], null);
//System.out.println((u==null?"null":"not null")+" "+u.userType.toString());
                            if (u.userType == TypeUser.ADMIN || u.userType == TypeUser.MODERATOR) {
                                //int i = Integer.valueOf(str[2]);
                                System.out.println("Administrator " + str[3]);
                                Server.mainController.remove(tableListTest.getOrDefault("" + str[2], null));


                            }
                        } else if (str[1].equals("ban")) {
                            if (str[2] != null) {
                                User u = userList.getOrDefault(str[3] + ";" + str[4], null);
                                if (u != null) {
                                    if (u.userType == TypeUser.ADMIN || u.userType == TypeUser.MODERATOR) {
                                        Iterator it = userList.entrySet().iterator();
                                        while (it.hasNext()) {
                                            Map.Entry pair = (Map.Entry) it.next();
                                            System.out.println(pair.getKey() + " = " + pair.getValue());
                                            User user = (User) pair.getValue();
                                            if (user.login.equals(str[2])) {
                                                if (user.userType != TypeUser.ADMIN) {
                                                    connectionDB.banIp(user.con, user.login);
                                                    user.con.close();
                                                    conn.send("game;userkick;" + str[2]);
                                                }

                                            }
                                            //it.remove(); // avoids a ConcurrentModificationException
                                        }
                                    }
                                }
                            }
                        }
                    } else if (str[0].equals("tables")) {
                        if (str[3 - 2].equals("create")) {
                            User u = userList.getOrDefault(str[4] + ";" + str[5], null);

                            if (u != null) {
                                if (u.try_create()) {
                                    // if ((Table)tableListTest.get(0 + "", null) == null) {
                                    Table t = new Table(Integer.parseInt(str[3]), str[2], j++, connectionDB, webserverLists);
                                    //  tableList.add(t);
                                    t.start();
                                    u.addBoard();
                                    u.setTime();
                                    tableListTest.put((t.id) + "", t);
                                    for (WebSocket sock : conns) {

                                        sock.send("tables;all;" + t.id + ";" + t.name + ";" + t.id + ";" + t.ctavka + ";" + t.userList.size() + ";" + t.getTime() + ";");
                                    }
                                    //}else{

                                    //  }
                                } else {
                                    conn.send("game;trashboard");
                                }
                            } else {
                                conn.close();
                            }
                        }
                    } else if (str[0].equals("chat")) {
                        if (str.length >= 4)
                            if (str[1].equals("com")) {
                               if (str.length >= 6) {
                                    User user = userList.getOrDefault(str[2] + ";" + str[3], null);
                                    System.out.println(System.currentTimeMillis()-user.chatBan);
                                 if( System.currentTimeMillis()-user.chatBan>0){
                                    String pass= connectionDB.UnBanChat(user.login);
                                    if(pass!=null){

                                     user.chatBan=0;

                                    }
                                 }
                           if (user != null
                                            && (user.userType == TypeUser.ADMIN
                                            || user.userType == TypeUser.MODERATOR)) {
                                        System.out.println(str.length);


                                        if (str[4].equals("banchat") && str[4] != null) {

                                            if (user.chatBan == 0) {
                                                if (str[5] != null) {
                                                    long time = System.currentTimeMillis() + (5 * 60000);
                                                    String pass = connectionDB.banChat(str[5], 5);

                                                    if (pass != null) {
                                                        User user1 = userList.getOrDefault(str[5] + ";" + pass, null);
                                                        if (user1 != null) {
                                                            user1.chatBan = time;
                                                            if(str[6]!=null) {
                                                                for (WebSocket conection : conns) {

                                                                    conection.send("chat;server;Игрок " + str[5] + " был отключон от чата по причине " + str[6] + ".");
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
                                                conn.send("chat;ban;" + user.chatBan);
                                            }
                                        }      else if (str[4].equals("unbanchat") && str[4] != null) {
                                            if (user.chatBan == 0) {
                                                if (str[5] != null) {
                                                    String pass = connectionDB.UnBanChat(str[5]);
                                                    if (pass != null) {
                                                        User user1 = userList.getOrDefault(str[5] + ";" + pass, null);
                                                        if (user1 != null) {
                                                            user1.chatBan = 0;

                                                        }
                                                    }
                                                }
                                            } else {
                                                conn.send("chat;ban;" + user.chatBan);
                                            }
                                        }


                                        else if (str[4].equals("clearchat") && str[4] != null) {
                                            if (user.chatBan == 0) {
                                                for (WebSocket conection : conns) {
                                                    conection.send("chat;clear");
                                                }

                                            } else {
                                                conn.send("chat;ban;" + user.chatBan);
                                            }

                                        }

                                        else if (str[4].equals("server") && str[4] != null) {
                                            if (user.chatBan == 0) {
                                                if(str[5]!=null)
                                                for (WebSocket conection : conns) {

                                                    conection.send("chat;server;");
                                                }

                                            } else {
                                                conn.send("chat;ban;" + user.chatBan);
                                            }

                                        }
                                        else if (str[4].equals("reloadPage") && str[4] != null) {
                                            if (user.chatBan == 0) {
                                                for (WebSocket conection : conns) {
                                                    conection.send("chat;reload");
                                                }
                                            } else {
                                                conn.send("chat;ban;" + user.chatBan);
                                            }


                                        }
                                    }
                                }

                            } else if (str[1].equals("text")) {
                                if (str[2] != null) {
                                    if (str[2].length() <= 300 && str[2].length() >= 2) {
                                        if (str[3] != null && str[4] != null) {
                                            User user = userList.getOrDefault(str[3] + ";" + str[4], null);
                                            if (user != null) {
                                                if( System.currentTimeMillis()-user.chatBan>0){
                                                    String pass= connectionDB.UnBanChat(user.login);
                                                    if(pass!=null){

                                                        user.chatBan=0;

                                                    }
                                                }
                                                if (user.chatBan == 0) {
                                                    for (WebSocket conection : conns)
                                                        conection.send("chat;" + user.login + ";" + str[2]);
                                                } else {
                                                    conn.send("chat;ban;" + user.chatBan);
                                                }
                                            }
                                        }
                                    } else {
                                        str[2] = null;
                                        return;
                                    }
                                }

                            }
                    }
                }


            }

        }).start();
        }
public void upd(Table t){
    if(t!=null) {
        for (WebSocket soc : conns) {

            soc.send("tables;upd;" + t.id + ";" + t.name + ";" + t.id + ";" + t.ctavka + ";" + t.userList.size() + ";" + t.getTime() + ";");

        }
    }
}
    @Override
    public void onError(WebSocket conn, Exception ex) {
        //ex.printStackTrace();
        if (conn != null) {
            conns.remove(conn);
            // do some thing if required
        }

        System.out.println("ERROR from "+ex+"  " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }
    public void remove(Table table,int id){
        Iterator it = userList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            User user = (User)pair.getValue();
            user.send("tables;remove;" +id+";");
            //it.remove(); // avoids a ConcurrentModificationException
        }

        tableListTest.remove(table);

    }


    public void ban(String login){




    }
    public void exit(int id) {
       /* Iterator it = userList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
          //  System.out.println(pair.getKey() + " = " + pair.getValue());
            User user = (User)pair.getValue();
            if(user.con.isFlushAndClose()==false) {
                user.send("tables;remove;" + id + ";");
            }
            //it.remove(); // avoids a ConcurrentModificationException
        }


    }
*/   tableListTest.remove(""+id);
        for (WebSocket con : conns) {
            con.send("tables;remove;" + id + ";");
        }

    }
	public void createTableBot(){
        Table t = new Table(new Random().nextInt(50), "", j++, connectionDB, webserverLists);
        //  tableList.add(t);
        t.start();

        tableListTest.put((t.id) + "", t);
        for (WebSocket sock : conns) {

            sock.send("tables;all;" + t.id + ";" + t.name + ";" + t.id + ";" + t.ctavka + ";" + t.userList.size() + ";" + t.getTime() + ";");
        }
    }
}
