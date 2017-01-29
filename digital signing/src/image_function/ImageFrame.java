package image_function;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class ImageFrame extends JFrame{
static BufferedImage key=null,image=null;
	public ImageFrame(){
        		setTitle("ImageTest");
        		setSize(700, 600);
        		DrawPanel  component = new DrawPanel();
        		add(component);
      		component.requestFocusInWindow();
	}

	public ImageFrame(BufferedImage key, BufferedImage distb) {
		// TODO Auto-generated constructor stub
		setTitle("ImageTest");
		setSize(700, 600);
		this.key=key;
		this.image=distb;
		DrawPanel  component = new DrawPanel();
		add(component);
		component.requestFocusInWindow();
	}
}


class DrawPanel extends JPanel{
    /**
     * 
     */	
	static final long serialVersionUID = 1L;
	BufferedImage image,image2;
	int x=50;
	int y=50;
	float opacity = 0.8f;
	public DrawPanel(){
		try{
			if(ImageFrame.key==null || ImageFrame.image==null){
			File f = new File(getClass().getResource("data/Key1.png").toURI());
			image = ImageIO.read(f);
			File f2 = new File(getClass().getResource("data/output_3.png").toURI());
			image2 = ImageIO.read(f2);
			}
			else
			{
				image=ImageFrame.key;
				image2=ImageFrame.image;
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}       

		KeyListener listener = new KeyListener() {
			public void keyTyped(KeyEvent e) {	}
			public void keyPressed(KeyEvent e) {
				//System.out.println("down");
				int keyCode=e.getKeyCode();
				if((keyCode==KeyEvent.VK_UP))
				{                   
					if(opacity>0.05f)  opacity-=0.05f;
					//System.out.println("alphadown");
				}
				else if((keyCode==KeyEvent.VK_DOWN))
				{
					if(opacity<1f)  opacity+=0.05f;
					//System.out.println("alphaup");         
                				}
                				else if((keyCode==KeyEvent.VK_LEFT))
                				{
					if(x>0)  x-=1;                
					//System.out.println("left");
				}
				else if((keyCode==KeyEvent.VK_RIGHT))
				{
					if(x<700)  x+=1;                       
					//System.out.println("right");
				}
				repaint(); 
			}
			public void keyReleased(KeyEvent e) {	}
		};
		addKeyListener(listener);
		setFocusable(true);
	}   						 //end of constructor

    	public void paint (Graphics g){
		
		Graphics2D g2 = (Graphics2D) g;
		if(image == null) return;
		int imageWidth = image.getWidth(this);
		int imageHeight = image.getHeight(this);
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		//g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		g.drawImage(image2, 50, 50, this);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g2.drawImage(image, x, y, this);
		g2.dispose();
	}

}
