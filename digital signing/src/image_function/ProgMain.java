package image_function;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
public class ProgMain extends JFrame
{
	boolean valid = false,keyvalid=false,encrypted=false,sign=false;
	BufferedImage image,image2,key,encrypt,orig,pic,distb;
	Button b,b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,test;
	JTextField t1,t2,t3;
	JComboBox<String> cb1,cb2;
	JScrollPane scrollFrame;
	public ProgMain(){
        		setTitle("distributed image");
        		setSize(900, 500);
        		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        		b=new Button("monochrome watermark");
        		b1=new Button("GreyScale watermark");
        		b2=new Button("Generate Randomkey");
        		b3=new Button("encrypt and VSC");
        		b4=new Button("sign cover");
        		b5=new Button("resize");
        		b6=new Button("Visual Decryption");
        		b7=new Button("Start Over");
        		b8=new Button("Select Watermark");
        		b9=new Button("Select Cover Image");
        		b10=new Button("Start");
        		b.setVisible(false);
        		b1.setVisible(false);
        		b2.setVisible(false);
        		b3.setVisible(false);
        		b4.setVisible(false);
        		b5.setVisible(false);
        		b6.setVisible(false);
        		b7.setVisible(false);
        		cb1=new JComboBox<>();
        		cb1.insertItemAt(".png",0);
        		cb1.insertItemAt(".jpg",1);
        		cb1.setSelectedIndex(0);
        		cb2=new JComboBox<>();
        		cb2.insertItemAt("alpha plane",0);
        		cb2.insertItemAt("red plane",1);
        		cb2.insertItemAt("green plane",2);
        		cb2.insertItemAt("blue pane",3);
        		cb2.setSelectedIndex(0);
        		t3=new JTextField("sign threshold value");
        		cb1.setVisible(false);
        		cb2.setVisible(false);
        		t3.setVisible(false);
        		t1=new JTextField("width");
        		t2=new JTextField("Height");
        		DrawPanel  component = new DrawPanel();
        		component.setSize(450,500);
                component.setBackground(Color.BLUE);
                component.setVisible(true);
                final JPanel component2 = new JPanel();
        		component.setSize(450,500);
        		component2.setBackground(Color.red);
        		component2.setVisible(true);
        		b7.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						image2=deepCopy(image);
						valid=false;
						keyvalid=false;
						encrypted=false;
						sign=false;
						scrollFrame.getHorizontalScrollBar().setValue(0);
						scrollFrame.getVerticalScrollBar().setValue(0);
						b.setVisible(false);
		        		b1.setVisible(false);
		        		b2.setVisible(false);
		        		b3.setVisible(false);
		        		b4.setVisible(false);
		        		b5.setVisible(false);
		        		b6.setVisible(false);
		        		b7.setVisible(false);
		        		b8.setVisible(true);
		        		b9.setVisible(true);
		        		b10.setVisible(true);
		        		cb1.setVisible(false);
		        		cb2.setVisible(false);
		        		t3.setVisible(false);
						repaint();
						
					}
				});
        		b.addActionListener(new ActionListener() {
    				
    				@Override
    				public void actionPerformed(ActionEvent e) {
    					// TODO Auto-generated method stub
    					image2=deepCopy(image);
    					image2=ImageOp.monochrome(image);
    					valid = InOut.saveImage(image2, "B&W", cb1.getSelectedIndex());
    					repaint();
    				}
    			});
        		b1.addActionListener(new ActionListener() {
    				
    				@Override
    				public void actionPerformed(ActionEvent e) {
    					// TODO Auto-generated method stub
    					image2=deepCopy(image);
    					image2=KeyGrey.GreyScale(image);
    					valid = InOut.saveImage(image2, "Grey", cb1.getSelectedIndex());
    					repaint();
    				}
    			});
        		b2.addActionListener(new ActionListener() {
    				
    				@Override
    				public void actionPerformed(ActionEvent e) {
    					// TODO Auto-generated method stub
    					key=ImageOp.createRandom(image);
    					keyvalid=true;
    					//valid = inout.saveImage(key, "src/image_function/data/key1", cb1.getSelectedIndex());
    					repaint();
    				}
    			});
        		b3.addActionListener(new ActionListener() {
    				
    				@Override
    				public void actionPerformed(ActionEvent e) {
    					// TODO Auto-generated method stub
    					if(keyvalid && !encrypted){
    					encrypt=ImageOp.meldImages(image2,key);
    					encrypted=true;
    					orig=ImageOp.meldImages(encrypt,key);
    					encrypt=ImageOp.makevsc(encrypt);
    					key=ImageOp.makevsc(key);
    					//image2=alter.makevsc(image2);
    					image2=KeyGrey.resizeImage(image2, image2.getWidth()*2, image2.getHeight()*2);
    					valid = InOut.saveImage(key, "key", cb1.getSelectedIndex());
    					repaint();
    					}
    					else if(encrypted)
    						JOptionPane.showMessageDialog(null, "Already encrypted");
    					else
    						JOptionPane.showMessageDialog(null, "Generate Random key first");
    				}
    			});
        		b4.addActionListener(new ActionListener() {
    				
    				@Override
    				public void actionPerformed(ActionEvent e) {
    					// TODO Auto-generated method stub
    					int th=50;
    					if(encrypted)
    					{
    					try{th=Integer.parseInt(t3.getText());}catch(Exception ThEx){JOptionPane.showMessageDialog(null,"threshold not integer using default 50");}
    					distb=KeyGrey.setalpha(pic, encrypt,th,cb2.getSelectedIndex());
    					sign=true;
    					t3.setText("sign threshold value");
    					valid = InOut.saveImage(distb, "Signed_Image", cb1.getSelectedIndex());
    					repaint();
    					}
    					else
    						JOptionPane.showMessageDialog(null, "key image not encrypted");
    				}
    			});
        		b5.addActionListener(new ActionListener() {
    				
    				@Override
    				public void actionPerformed(ActionEvent e) {
    					// TODO Auto-generated method stub
    					int w,h;
    				try{	w=Integer.parseInt(t1.getText());
    						h=Integer.parseInt(t2.getText());
    				} catch(Exception ec3){JOptionPane.showMessageDialog(null,"resize accepts integers only");}
    				BufferedImage temp = KeyGrey.resizeImage(pic, image.getWidth(), image.getHeight());
    					valid = InOut.saveImage(temp, "resizedcover", cb1.getSelectedIndex());
    					repaint();
    				}
    			});
        		b6.addActionListener(new ActionListener() {
    				
    				@Override
    				public void actionPerformed(ActionEvent e) {
    					// TODO Auto-generated method stub
    					if(sign){
    					ImageFrame frame = new ImageFrame(key,distb);
                		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                		frame.setVisible(true);
                		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    					}
    					if(!sign && !keyvalid){
    						JOptionPane.showMessageDialog(null, "Select The Key and distribution image");
    						JFileChooser chooser = new JFileChooser();
						    chooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png","gif","jpeg"));
						    File dir = new File(System.getProperty("user.dir"));
						    chooser.setCurrentDirectory(dir);
						    try
						    {
							chooser.setDialogTitle("Key Image");
							chooser.showOpenDialog(null);
							File f = chooser.getSelectedFile();
							key = ImageIO.read(f);
							chooser.setDialogTitle("Signed Image");
							chooser.showOpenDialog(null);
							f=chooser.getSelectedFile();
							distb = ImageIO.read(f);
    						ImageFrame frame = new ImageFrame(key,distb);
	                		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	                		frame.setVisible(true);
	                		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
						    }
						    catch(Exception e1){}
    					}
    					if(!sign && keyvalid)
    						JOptionPane.showMessageDialog(null, "Image not signed");
    					
    				}
    			});
        		t3.addFocusListener(new FocusListener() {
					
					@Override
					public void focusLost(FocusEvent arg0) {
						// TODO Auto-generated method stub
					}
					
					@Override
					public void focusGained(FocusEvent arg0) {
						// TODO Auto-generated method stub
						t3.setText("");
					}
				});
        		b8.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						   JFileChooser chooser = new JFileChooser();
						   chooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png","gif","jpeg"));
						    File dir = new File(System.getProperty("user.dir"));
						    chooser.setCurrentDirectory(dir);
						    chooser.showOpenDialog(null);
						    File f = chooser.getSelectedFile();
						    try {
						    	if(f!=null){
								image = ImageIO.read(f);
								if(pic!=null)
								image=KeyGrey.resizeImage(image, pic.getWidth()/2, pic.getHeight()/2);
								image2=deepCopy(image);
						    	}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						    repaint();
					}
				});
        		b9.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						JFileChooser chooser = new JFileChooser();
						chooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png","gif","jpeg"));
					    File dir= new File(System.getProperty("user.dir"));
					    chooser.setCurrentDirectory(dir);
					    chooser.showOpenDialog(null);
					    File f = chooser.getSelectedFile();
					    try {
					    	if(f!=null){
							pic = ImageIO.read(f);
							image=KeyGrey.resizeImage(image, pic.getWidth()/2, pic.getHeight()/2);
							image2=deepCopy(image);
					    	}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					    repaint();
					}
				});
        		b10.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						b8.setVisible(false);
						b9.setVisible(false);
						b10.setVisible(false);
						b.setVisible(true);
						b1.setVisible(true);
						b2.setVisible(true);
						b3.setVisible(true);
						b4.setVisible(true);
						b5.setVisible(true);
						b6.setVisible(true);
						b7.setVisible(true);
						cb1.setVisible(true);
						cb2.setVisible(true);
						t3.setVisible(true);
					}
				});
        		//setLayout(new GridLayout());
        		component2.add(b8);
        		component2.add(b9);
        		component2.add(b10);
        		component2.add(b7);
        		component2.add(b);
        //		component2.add(b1);
        		component2.add(b2);
        		component2.add(b3);
        		component2.add(t3);
        		component2.add(cb2);
        		component2.add(b4);
        		component2.add(b6);
        		component2.add(cb1);
        		//component2.add(t1);
        		//component2.add(t2);
        		//component2.add(b5);
        		scrollFrame = new JScrollPane(component);
        		component.setAutoscrolls(true);
        		component.setPreferredSize(new Dimension(2500,1500));
        		scrollFrame.setPreferredSize(new Dimension( 800,300));

        		JSplitPane splitPane = new JSplitPane();
                splitPane.setSize(this.getWidth(), this.getHeight());
                splitPane.setDividerSize(1);
                splitPane.setDividerLocation(50);
                splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
                splitPane.setLeftComponent(component2);
                splitPane.setRightComponent(scrollFrame);

                this.add(splitPane);

    			
      		component.requestFocusInWindow();
      		repaint();
	}
	public static void main(String[] args)
	{
                		ProgMain frame = new ProgMain();
                		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                		frame.setVisible(true);

    	}

	public class DrawPanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Image distbImg;
		public DrawPanel(){
		try{
			//JOptionPane.showMessageDialog(null, "working driectory"+Paths.get(".").toAbsolutePath());//System.getProperty("user.dir"));
			//File f = new File(getClass().getResource("data/keyclr.png").toURI());
			//File f = new File("src/image_function/data/keyclr.png");
			//File f2 = new File(getClass().getResource("data/rgb.jpg").toURI());
			//File f2 = new File("src/image_function/data/rgb.jpg");
			image = ImageIO.read(getClass().getResource("data/keyclr.png"));
			pic = ImageIO.read(getClass().getResource("data/rgb.jpg"));
			image=KeyGrey.resizeImage(image, pic.getWidth()/2, pic.getHeight()/2);
			image2=deepCopy(image);
			
		}
		catch (IOException e){
			e.printStackTrace();
		}     
		catch(Exception e){e.printStackTrace();}
		}
	public void paint (Graphics g)
		{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if(image==null) return;
		int imageWidth = image2.getWidth(this);
		int imageHeight = image2.getHeight(this);
		g2.drawImage(image2, 0, 0, this);
		g2.drawImage(pic, imageWidth*2+20, 0, this);
		if(keyvalid)
			g2.drawImage(key, imageWidth+10, 0, this);
		if(encrypted){
			g2.drawImage(encrypt, 0, imageHeight+5, this);
			g2.drawImage(orig, imageWidth+10, imageHeight+5, this);
		}
		if(sign)
			g2.drawImage(distb, imageWidth*2+20, pic.getHeight()+10, this);
		g2.dispose();
		}
	}
	static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		}

}