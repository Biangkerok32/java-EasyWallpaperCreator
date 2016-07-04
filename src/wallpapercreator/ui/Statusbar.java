/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wallpapercreator.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 *
 * @author shinobisoft
 */
public class Statusbar extends JPanel {
    public final static String TAG = Statusbar.class.getSimpleName();
    
    public final static int LEFT   = JLabel.LEFT;
    public final static int CENTER = JLabel.CENTER;
    public final static int RIGHT  = JLabel.RIGHT;
    
    private int minCY = 28;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Statusbar() {
        super();
        
        setOpaque( true );
        setLayout( null );
        setBorder( BorderFactory.createLineBorder( Color.GRAY, 1 ) );
    }
    
    public void setMinHeight( int minHeight ) {
        minCY = minHeight;
        setMinimumSize( getMinimumSize() );
    }
    
    @Override
    public Dimension getMinimumSize() {
        return new Dimension( 300, minCY );
    }
    
    @Override
    public Dimension getPreferredSize() {
        if( !isVisible() || getParent() == null )
            return null;
        Insets i = getParent().getInsets();
        return new Dimension( getParent().getWidth()-(i.left+i.right), minCY );
    }
    
    public Component addPanel( int width ) {
        JLabel cmp = new JLabel();
        cmp.setBorder( BorderFactory.createLineBorder( UIManager.getColor( "controlShadow" ), 1 ) );
        add( cmp );
        cmp.setMinimumSize( new Dimension( width, minCY-8 ) );
        return cmp;
    }
    
    public void setPanelAlignment( int panel, int alignmentFlag ) {
        if( panel >= 0 && panel < getComponentCount() ) {
            ((JLabel)getComponent( panel )).setHorizontalAlignment( alignmentFlag );
            ((JLabel)getComponent( panel )).repaint();
        }
    }
    
    public void setIcon( int panel, ImageIcon icon ) {
        if( panel >= 0 && panel < getComponentCount() ) {
            ((JLabel)getComponent( panel )).setIcon( icon );
            ((JLabel)getComponent( panel )).repaint();
        }
    }
    
    public void setText( int panel, String text ) {
        if( panel >= 0 && panel < getComponentCount() ) {
            ((JLabel)getComponent( panel )).setText( text );
            ((JLabel)getComponent( panel )).repaint();
        }
    }
    
    public void setToolTipText( int panel, String text ) {
        if( panel >= 0 && panel < getComponentCount() ) {
            ((JLabel)getComponent( panel )).setToolTipText( text );
        }
    }
    
    @Override
    public void doLayout() {
        if( getComponentCount() > 0 ) {
            Component[] arr = getComponents();
            int x = 4, y = 4;
            
            for( Component c : arr ) {
                
                int cmpCX = c.getMinimumSize().width;
                c.setBounds( x, y, cmpCX, minCY-8 );
                x += (cmpCX+3);
            }
        }
    }
}
