package edu.caltech.cs2.lab03;

import edu.caltech.cs2.libraries.Pixel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;

public class Image {
    private Pixel[][] pixels;

    public Image(File imageFile) throws IOException {
        BufferedImage img = ImageIO.read(imageFile);
        this.pixels = new Pixel[img.getWidth()][img.getHeight()];
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                this.pixels[i][j] = Pixel.fromInt(img.getRGB(i, j));
            }
        }
    }

    private Image(Pixel[][] pixels) {
        this.pixels = pixels;
    }

    public Image transpose() {
        Pixel[][] result = new Pixel[pixels[0].length][pixels.length];
        for (int i = 0; i < pixels[0].length; i++) {
            for (int j = 0; j < pixels.length; j++) {
                result[i][j] = pixels[j][i];
            }
        }
        return new Image(result);
    }

    public String decodeText() {
        String message = "";
        int counter = 0;
        String tempByte = "";

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                tempByte = pixels[i][j].getLowestBitOfR() + tempByte;
                counter++;
                if (counter % 8 == 0) {
                    message += (char)(Integer.parseInt(tempByte, 2));
                    tempByte = "";
                }
            }
        }
        return message.trim();
    }


    public Image hideText(String text) {
        Pixel[][] arr = this.pixels.clone();
        String tempByte;
        int xCounter = 0;
        int yCounter = 0;
        //System.out.println("DIAGNOSTIC INFO: x length is " + arr.length + "and y length is " + arr[0].length);
        for (int i = 0; i < text.length(); i++) {
            tempByte = Integer.toString((int)(text.charAt(i)), 2);

            //add enough zeroes to take tempByte to eight digits
            int tempLength = tempByte.length();
            if (tempLength < 8) {
                for (int z = tempLength; z < 8; z++) {
                    tempByte = "0" + tempByte;
                }
            }
            if (tempLength > 8) {
                tempByte = tempByte.substring(tempLength - 8, tempLength);
            }
            /*
            if (tempByte.length() > 8) {
                tempByte = tempByte.substring(0, 8);
            }
            */

            System.out.println("new byte: " + tempByte);
            for (int j = tempByte.length() - 1; j >= 0; j--) {
            //for (int j = 0; j < tempByte.length(); j++) {
                try {
                    //System.out.println("Attempting to fix " + tempByte
                    // .substring(j, j + 1) + " at " + xCounter + ", " + yCounter);
                    arr[xCounter][yCounter] =
                            arr[xCounter][yCounter].fixLowestBitOfR(Integer.parseInt(tempByte.substring(j, j + 1)));
                    yCounter++;
                    if (yCounter == arr[0].length) {
                        yCounter = 0;
                        xCounter++;
                    }
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    //System.out.println("Fixing failed.");
                }
            }
        }

        for (int xC = xCounter; xC < arr.length; xC++) {
            for (int yC = yCounter; yC < arr[0].length; yC++) {
                try {
                    //System.out.println("Attempting to fix 0 at " + xC + ", "+ yC);
                    arr[xC][yC] = arr[xC][yC].fixLowestBitOfR(0);
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    //System.out.println("Fixing 0 failed.");
                }
            }
            yCounter = 0;
        }
        return new Image(arr);
    }

    public BufferedImage toBufferedImage() {
        BufferedImage b = new BufferedImage(this.pixels.length, this.pixels[0].length, BufferedImage.TYPE_4BYTE_ABGR);
        for (int i = 0; i < this.pixels.length; i++) {
            for (int j = 0; j < this.pixels[0].length; j++) {
                b.setRGB(i, j, this.pixels[i][j].toInt());
            }
        }
        return b;
    }

    public void save(String filename) {
        File out = new File(filename);
        try {
            ImageIO.write(this.toBufferedImage(), filename.substring(filename.lastIndexOf(".") + 1, filename.length()), out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
