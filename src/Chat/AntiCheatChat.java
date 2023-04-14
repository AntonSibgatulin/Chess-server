package Chat;

public interface AntiCheatChat {
    void CheckErrorSymbol(String str);
    boolean CheckAntiFlood(String str[],long[] time);

}
