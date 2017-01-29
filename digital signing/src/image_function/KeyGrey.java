package image_function;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.*;

public class KeyGrey
{
static int val;
 static int getGrey(int rgb) {
    int r,g,b;
    Color pixColour = new Color(rgb);
    r=pixColour.getRed();
    g=pixColour.getGreen();
    b=pixColour.getBlue();
    rgb = (int)((0.299*r+0.587*g+0.114*b)/3);
    return rgb;
  }

 static BufferedImage GreyScale(BufferedImage pic) {
    int i,j,min=128,max=128;
    BufferedImage output = new BufferedImage(pic.getWidth(),pic.getHeight(),BufferedImage.TYPE_INT_RGB);

    for (i=0;i<pic.getWidth();i++){
      for (j = 0; j < pic.getHeight(); j++) {
	val=getGrey(pic.getRGB(i,j));
	if(val<min) min=val;
	if(val>max) max=val;
	Color c = new Color(val,val,val);
	output.setRGB(i,j,c.getRGB());
      }
    }
    val = (int)Math.ceil((max+min)/2);
    ImageOp.val=val;
    return output;
    }
 
 static BufferedImage setalpha(BufferedImage real1,BufferedImage tmp, int thresh, int set)
 {
	 BufferedImage output = new BufferedImage(tmp.getWidth(),tmp.getHeight(),BufferedImage.TYPE_INT_ARGB);
	 int alpha,r,g,b;
	 BufferedImage real;
	 Color c,cn;
	 //tmp=resizeImage(tmp,real.getWidth(),real.getHeight());
	 real=resizeImage(real1,tmp.getWidth(),tmp.getHeight());
	 for (int i=0;i<real.getWidth();i++){
	      for (int j = 0; j < real.getHeight(); j++) {
	    		c= new Color(real.getRGB(i,j));
	    	  	r=c.getRed();
	    	    g=c.getGreen();
	    	    b=c.getBlue();
	    	    alpha=c.getAlpha();
	    	    if(set==0){
	    	    if(tmp.getRGB(i, j)<-8388608)
	    	    	alpha=c.getAlpha()-thresh;//(c.getAlpha()+getGrey(tmp.getRGB(i,j)))/2;
	    	    else
	    	    	alpha=c.getAlpha();
	    	    }
	    	    if(set==1){
	    	    if(tmp.getRGB(i, j)<-8388608){
	    	    	r=c.getRed()+thresh;
	    	    	if(r<0 || r>255)r=c.getRed();//thresh;
	    	    }
	    	    else
	    	    	r=c.getRed();
	    	    }
	    	    if(set==2){
		    	    if(tmp.getRGB(i, j)<-8388608){
		    	    	g=c.getGreen()+thresh;
		    	    	if(g<0 || g>255)g=c.getGreen();
		    	    }
		    	    else
		    	    	g=c.getGreen();
		    	    }
	    	    if(set==3){
		    	    if(tmp.getRGB(i, j)<-8388608){
		    	    	b=c.getBlue()+thresh;
		    	    	if(b<0 || b>255)b=c.getBlue();
		    	    }
		    	    else
		    	    	b=c.getBlue();
		    	    }
	    	  	cn=new Color(r,g,b,alpha);
	    	  	output.setRGB(i,j,cn.getRGB());
	    	    
	    	  
	      }
	 }
	 return output;
 }
 	
static  BufferedImage resizeImage(BufferedImage image, int width, int height) {
     int type=0;
    type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
    BufferedImage resizedImage = new BufferedImage(width, height,type);
    Graphics2D g = resizedImage.createGraphics();
    g.drawImage(image, 0, 0, width, height, null);
    g.dispose();
    return resizedImage;
 }
 }