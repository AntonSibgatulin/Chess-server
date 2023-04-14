package register;

import org.java_websocket.WebSocket;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class antiDDoS extends  Thread{
    public List<WebSocket> list = new ArrayList<>();
    public boolean getantiDoss(long lastreg,long newreg){
        if(newreg-lastreg<4000L){
            return true;
        }else return false;
    }
    public void add(WebSocket webSocket){
        list.add(webSocket);
    }
    public boolean iteration(){
        System.out.println(list.get(0).getRemoteSocketAddress().getAddress().getHostAddress());
       if(list.size()>=4){
           if(list.get(0).getRemoteSocketAddress().getAddress().getHostAddress().equals(list.get(0).getRemoteSocketAddress().getAddress().getHostAddress()) &&
                   list.get(3-2).getRemoteSocketAddress().getAddress().getHostAddress().equals(list.get(2).getRemoteSocketAddress().getAddress().getHostAddress())
           && list.get(2).getRemoteSocketAddress().getAddress().getHostAddress().equals(list.get(3).getRemoteSocketAddress().getAddress().getHostAddress())){
               System.out.println(list.get(0).getRemoteSocketAddress().getAddress().getHostAddress());
           }
       }
        return false;
    }


    @Override
    public void run(){
        while(true){
            try {
                Thread.sleep(999);
                if(list.size()>(5+5)){
                    list.remove(list.size()-3+2);
                }
                iteration();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
