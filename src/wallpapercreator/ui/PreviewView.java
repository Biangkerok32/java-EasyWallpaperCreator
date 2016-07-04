/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wallpapercreator.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author shinobisoft
 */
public class PreviewView extends JPanel {
    public final static String TAG = PreviewView.class.getSimpleName();
    
    private BufferedImage imgDC;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PreviewView() {
        super();
        setOpaque( true );
        
    }
    
    public BufferedImage getImage() {
        return imgDC;
    }
    
    public void setImage( BufferedImage image ) {
        imgDC = image;
        repaint();
    }
    
    @Override
    public Dimension getMinimumSize() {
        return new Dimension( 200, 200 );
    }
    
    @Override
    public void paintComponent( Graphics g ) {
        super.paintComponent( g );
        
        if( imgDC != null )
            g.drawImage( imgDC, 0, 0, null );
    }
}
