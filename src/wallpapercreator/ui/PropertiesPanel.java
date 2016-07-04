/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wallpapercreator.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author shinobisoft
 */
public class PropertiesPanel extends JPanel {
    public final static String TAG = PropertiesPanel.class.getSimpleName();
    
    private static int minCX = 240, minCY = 300;
    private int CX = 240, CY = 0;
    private int padding = 10;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PropertiesPanel() {
        super();
        
        this.padding = 10;
        setBorder( BorderFactory.createEmptyBorder( padding, padding, padding, padding ) );
        setLayout( null );
        
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
        return new Dimension( CX, (CY == 0) ? getParent().getHeight()-(i.left+i.right) : CY );
    }
    
    public void setHeight( int height ) {
        CY = height;
        doLayout();
    }
    
    public void setWidth( int width ) {
        CX = width;
        doLayout();
    }
    
    @Override
    public void doLayout() { // This is what I call a LinearLayout
        int nComponents = getComponentCount();
        if( nComponents > 0 ) {
            Component[] arr = getComponents();
            
            int x = padding+2, y = padding+2;
            for( Component c : arr ) {
                
                int cmpCY = c.getPreferredSize().height;
                int cmpCX = getWidth()-((padding*2)+4);
                
                c.setBounds( x, y, cmpCX, cmpCY );
                y += cmpCY+padding;
            }
        }
    }
}
