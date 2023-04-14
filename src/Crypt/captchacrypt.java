package Crypt;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.jar.JarFile;

public class captchacrypt {
   public  BufferedImage buf = new BufferedImage(20,15,BufferedImage.TYPE_INT_RGB);
String str = null;

public captchacrypt(int i){

    buf.getGraphics().drawLine(2,2,35,15);
    buf.getGraphics().drawLine(6,2,35,8);


    //buf.getGraphics().drawLine(2,15,35,8);
     buf.getGraphics().setColor(Color.WHITE);

    buf.getGraphics().drawString(i+"",2,12);
    getCaptchaString();
 //System.out.println(encodeToString(buf,"png"));
}
public String getCaptchaString(){
if(str==null){
	str = encodeToString(buf,"png");
}
    return str;
}
 
    public  String encodeToString(BufferedImage image, String type) {
        String base64String = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            base64String = encoder.encode(imageBytes);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64String;
    }
    /*public static void main(String[]args ){
    JFrame j = new JFrame();
    j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    j.setSize(100,150);
    captchacrypt c =new captchacrypt(500);
    j.getContentPane().add(new JLabel(c.icon));
    j.setVisible(true);
    }*/
}
