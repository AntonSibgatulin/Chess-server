package register;

import Crypt.captchacrypt;

import java.util.Random;

public class Captcha {
    public int equally;

public captchacrypt captcha;

    public void generatorCaptcha(){
                    if(equally>21 &&equally<=100){
                        captcha = new captchacrypt(equally);
                    }

    }

@Override
public String toString(){
        return ""+equally;
}
    public Captcha(int equally){
        this.equally = equally;
        generatorCaptcha();
    }
}
