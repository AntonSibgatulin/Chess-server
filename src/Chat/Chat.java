package Chat;

public class Chat extends Thread{
    private String id="";
    public static final int MaxWarning = 4,MaxTime = 900;
@Override
public String toString(){
    return id;
}

    public Chat(String id){
this.id = id;
    }

    public static  boolean CheckErrorSymbol(String mes) {
        if(mes.split(" ").length<=1)
            return false;
        else
            return true;
    }
    public static boolean getStrZero(String str1,String str2){
    return str1.equals(str2);
    }
public static boolean  getMaxTime(long time1,long time2){
    return time2-time1<MaxTime;
}

    public static boolean CheckAntiFlood(String[]str,long[] time) {
    for(int i = 0;i<MaxWarning;i++){
        if(str[i].length()==1||str[i].length()==0||str[i]==""){
            return true;
        }
    }
    if(str.length==MaxWarning&& time.length==MaxWarning){
        if(getStrZero(str[0],str[1])&&getStrZero(str[1],str[2])&&getStrZero(str[2],str[3])){
            return false;
        }else if(getStrZero(str[0],str[1])&&getStrZero(str[2],str[3])){
            return false;
        }else if(getStrZero(str[1],str[3])&&getStrZero(str[0],str[2])){
            return false;
        }
if(getMaxTime(time[0],time[1])&&getMaxTime(time[1],time[2])&&getMaxTime(time[2],time[3])){
return false;
}else if(getMaxTime(time[0],time[1])&&getMaxTime(time[2],time[3])){
    return false;
}
else{
    return true;
}
    }else
        return false;
    }

    @Override
    public void run(){
    while(true){
     //   Thread.sleep();
    }
    }

}
