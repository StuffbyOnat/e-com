/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frames;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author onatu
 */
public class utilities {
    public ImageIcon setIconSize(int x,int y,String iconPath){
    
    ImageIcon PlayIcon = new ImageIcon(getClass().getResource(iconPath));
        Image scaledImage = PlayIcon.getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        return resizedIcon;
    }
}
