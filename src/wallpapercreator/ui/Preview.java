/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wallpapercreator.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JDialog;

/**
 *
 * @author shinobisoft
 */
public class Preview extends JDialog {
    public final static String TAG = Preview.class.getSimpleName();
    
    private BufferedImage srcImage = null;
    private String closeMsg = "Press the Esc key to close";
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Preview( BufferedImage image ) {
        super();
        
        setUndecorated( true );
        srcImage = image;
        
        Dimension sz = new Dimension( image.getWidth(), image.getHeight() );
        setMinimumSize( sz );
        setPreferredSize( sz );
        
        addKeyListener( new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased( KeyEvent e ) {
                if( e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
                    dispose();
                }
            }
        } );
    }
    
    @Override
    public void setVisible( boolean bVisible ) {
        super.setVisible( bVisible );
        if( bVisible ) {
            PopupMsg pop = new PopupMsg( this, closeMsg );
            pop.setVisible( true );
        }
    }
    
    @Override
    public void paint( Graphics g ) {
        super.paint( g );
        
        if( srcImage != null ) {
            g.drawImage( srcImage, 0, 0, null );
        }
    }
}
