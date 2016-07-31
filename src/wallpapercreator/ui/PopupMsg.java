/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wallpapercreator.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JWindow;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 *
 * @author shinobisoft
 */
public class PopupMsg extends JWindow {
    public final static String TAG = PopupMsg.class.getSimpleName();
    
    private String MSG = "";
    private Font   FONT = null;
    private Timer  TIMER = null;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PopupMsg( JDialog parent, String message ) {
        super();
        
        this.MSG = message;
        this.FONT = new Font( "Dialog", 0, 12 );
        
        this.TIMER = new Timer( 2000, new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                Rectangle rc = getBounds();
                dispose();
                if( parent instanceof Preview ) {
                    parent.repaint( rc.x, rc.y, rc.width, rc.height );
                }
            }
        } );

        setMinimumSize( getMinimumSize() );
        pack();
    }
    
    @Override
    public Dimension getMinimumSize() {
        return new Dimension( (MSG.length()*7)+20, 35 );
    }
    
    @Override
    public Dimension getPreferredSize() {
        if( !isVisible() )
            return getMinimumSize();
        
        FontMetrics fm = getGraphics().getFontMetrics( FONT );
        int CX = fm.stringWidth( MSG ) + 20;
        int CY = (fm.getHeight()+fm.getDescent()) + 20;
        return new Dimension( CX, CY );
    }
            
    @Override
    public void setVisible( boolean bVisible ) {
        if( bVisible ) {
            setPreferredSize( getPreferredSize() );
            setLocationRelativeTo( null );
        }
        
        super.setVisible( bVisible );
        if( bVisible ) {
            repaint();
            TIMER.start();
        } else
            dispose();
    }
    
    @Override
    public void paint( Graphics g ) {
        int x = 0, y = 10;

        g.setColor( UIManager.getColor( "control" ) );
        g.fillRect( 0, 0, getWidth(), getHeight() );
        
        g.draw3DRect( 0, 0, getWidth(), getHeight(), true );
        
        g.setColor( UIManager.getColor( "controlText" ) );
        g.setFont( FONT );
        FontMetrics fm = g.getFontMetrics( FONT );
        
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints hints = g2d.getRenderingHints();
        g2d.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
        x = (getWidth()-fm.stringWidth( MSG ))/2;
        y += fm.getHeight();
        g2d.drawString( MSG, x, y );
        g2d.setRenderingHints( hints );
    }
}
