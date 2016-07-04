/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wallpapercreator.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.UIManager;

/**
 *
 * @author shinobisoft
 */
public class HR extends JComponent {
    public final static String TAG = HR.class.getSimpleName();
    
    private int minCX = 100;
    private int minCY = 10;
    private int CX = minCX;
    private int CY = minCY;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public HR() {
        super();
        setOpaque( true );
        //setBorder( BorderFactory.createEmptyBorder( 4, 4, 4, 4 ) );
        minCX = 100;
        minCY = 10;
        CX = minCX;
        CY = minCY;
    }
    
    @Override
    public Dimension getMinimumSize() {
        return new Dimension( minCX, minCY );
    }
    
    @Override
    public Dimension getPreferredSize() {
        if( !isVisible() || getParent() == null )
            return null;
        
        Insets i = getParent().getInsets();
        CX = getParent().getWidth()-(i.left+i.right);
        //CX -= 8;
        return new Dimension( CX, CY );
    }
    
    public void setHeight( int height ) {
        CY = height;
        repaint();
    }
    
    public void setWidth( int width ) {
        CX = width;
        repaint();
    }
    
    @Override
    public void paintComponent( Graphics g ) {
        // Insets i = getBorder().getBorderInsets( this );
        int x = 0;
        int y = (CY-2)/2;
        
        int x1 = CX, y1 = y; ;
        
        g.setColor( UIManager.getColor( "controlShadow" ) );
        g.drawLine( x, y, x1, y1 );
        
        y += 1;
        y1 += 1;
        g.setColor( UIManager.getColor( "controlHighlight" ) );
        g.drawLine( x, y, x1, y1 );
    }
}
