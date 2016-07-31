/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wallpapercreator.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GrayFilter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;


/**
 *
 * @author shinobisoft
 */
public class ImageButton extends JButton {
    public static String TAG = ImageButton.class.getSimpleName();

    private Dimension mSize = null;
    
    public ImageButton( Icon icon ) {
        super( icon );
        
        this.mSize = new Dimension( icon.getIconWidth()+8, Math.max( icon.getIconWidth()+8, 24 ) );
        setBorder( BorderFactory.createEmptyBorder() );
        setMinimumSize( mSize );
        setPreferredSize( mSize );
        setFocusPainted( false );
        setOpaque( false );
    }
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ImageButton( Icon icon, String actionCommand, ActionListener actionListener ) { 
        super( icon );
        
        this.mSize = new Dimension( icon.getIconWidth()+8, Math.max( icon.getIconWidth()+8, 24 ) );
        setBorder( BorderFactory.createEmptyBorder() );
        setMinimumSize( mSize );
        setPreferredSize( mSize );
        setActionCommand( actionCommand );
        addActionListener( actionListener );
        setFocusPainted( false );
        setOpaque( false );
    }
    
    @Override
    public final void paintComponent( Graphics g ) {
        super.paintComponent( g );
        
        if( getIcon() != null ) {
            
            ImageIcon icon = (ImageIcon)getIcon();
            int x = (getWidth()-icon.getIconWidth())/2;
            int y = (getHeight()-icon.getIconHeight())/2;
            
            if( isEnabled() ) {
                icon.paintIcon( ImageButton.this, g, x+1, y+1 );
                
            } else {
                Image img = GrayFilter.createDisabledImage( icon.getImage() );
                ImageIcon ico = new ImageIcon( img );
                ico.paintIcon( ImageButton.this, g, x+1, y+1 );
            }
        } 
    }
}
