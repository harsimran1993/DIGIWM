package image_function;

import java.awt.image.*;
import java.awt.Color;
import java.util.*;


public class ImageOp {

static int val;
  static int getBrightness(int rgb) {
    int r,g,b,a;
    Color pixColour = new Color(rgb);
    r=pixColour.getRed();
    g=pixColour.getGreen();
    b=pixColour.getBlue();
    //a=pixColour.getAlpha();
    rgb = (int)((r+g+b)/3);
    return rgb;
  }

  static BufferedImage monochrome(BufferedImage picture) {
    int i,j,min=128,max=128,value=0,col;
    
    BufferedImage output = new BufferedImage(picture.getWidth(),picture.getHeight(),BufferedImage.TYPE_INT_RGB);

    for (i=0;i<picture.getHeight();i++){
      for (j=0;j<picture.getWidth();j++) {
        value=getBrightness(picture.getRGB(j,i));
        if (value<min) min=value;
        if (value>max) max=value;
      }
    }

    value=(int)Math.ceil((max+min)/2);

    for (i=0;i<picture.getHeight();i++){
      for (j=0;j<picture.getWidth();j++) {
        col=getBrightness(picture.getRGB(j,i));
        if (col<value) output.setRGB(j,i,-16777216);//black
        else output.setRGB(j,i,-1);//white
      }
    }

    return output;
  }

  static BufferedImage meldImages(BufferedImage template, BufferedImage random) {
    int i,j;
    BufferedImage output = new BufferedImage(template.getWidth(),template.getHeight(),BufferedImage.TYPE_INT_RGB);
    
    for (i=0;i<template.getHeight();i++){
      for (j = 0; j < template.getWidth(); j++) {
        if (template.getRGB(j,i)<-8388608) {//black
          if (random.getRGB(j,i)<-8388608) output.setRGB(j,i,-1);			//case 1 1
          else output.setRGB(j,i,-16777216);								//case 1 0
        }
        else {
          if (random.getRGB(j,i)<-8388608) output.setRGB(j,i,-16777216);	//case 0 1
          else output.setRGB(j,i,-1);										//case 0 0
        }
      }
    }
    return output;
  }

  static BufferedImage makevsc(BufferedImage template) {//note; 0 is black in the top left corner, 1 has white in that corner
    BufferedImage output = new BufferedImage(template.getWidth()*2,template.getHeight()*2,BufferedImage.TYPE_INT_RGB);
    int i,j;

    for (i=0;i<template.getHeight();i++){
      for (j=0;j<template.getWidth();j++) {
        if (template.getRGB(j,i)<-8388608) {//black
          output.setRGB((j*2),(i*2),-16777216);//black
          output.setRGB((j*2),(i*2)+1,-1);//white
          output.setRGB((j*2)+1,(i*2),-1);//white
          output.setRGB((j*2)+1,(i*2)+1,-16777216);//black
        }
        else {
          output.setRGB((j*2),(i*2),-1);//white
          output.setRGB((j*2),(i*2)+1,-16777216);//black
          output.setRGB((j*2)+1,(i*2),-16777216);//black
          output.setRGB((j*2)+1,(i*2)+1,-1);//white
        }

      }
    }
    return output;
  }

  static BufferedImage createRandom(BufferedImage template) {
    int i,j;
    Random rn = new Random();
    BufferedImage output = new BufferedImage(template.getWidth(),template.getHeight(),BufferedImage.TYPE_INT_RGB);
    for (i=0;i<template.getHeight();i++){
      for (j=0;j<template.getWidth();j++) {
        if (rn.nextBoolean()==false) output.setRGB((j),(i),-16777216);//black
        else output.setRGB((j),(i),-1);//white
      }
    }

    return output;
  }


}