package image_function;

import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class InOut {

  
static BufferedImage loadImage(String filename)
{
    
BufferedImage in;
  
  try {
     
 File filein = new File(filename);

      in = ImageIO.read(filein);
    
}
    catch (Exception ex) 
{
      in = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }
 
   return in;
  
}

 
 static boolean validateImage(BufferedImage pic){
 
   boolean valid;
    
int x=pic.getWidth();
  
  if (x>0) {
 
     x=pic.getHeight();
   
   if (x>0) valid=true;
    
    else valid=false;
   
 }
    else valid=false;
    
return valid;

  }

  
static boolean saveImage(BufferedImage pic, String filename, int format) {
  
  boolean valid=false;
    
String type="png";
   
 if (format==1) type="gif";
   
 filename.trim();
  
  filename=filename+"."+type;
 
   try {
      
File fileout = new File(filename);
  
    ImageIO.write(pic, type, fileout);
 
     valid=true;
    
}
    
catch (Exception ex) {
 
     valid = false;
   
 }
   
 return valid;

  
}


}