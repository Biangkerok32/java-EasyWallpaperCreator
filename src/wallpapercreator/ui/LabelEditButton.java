/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wallpapercreator.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author shinobisoft
 */
public class LabelEditButton extends LabelEdit {
    public final static String TAG = LabelEditButton.class.getSimpleName();
    
    public final static int TEXT_LEFT   = JLabel.LEFT;
    public final static int TEXT_CENTER = JLabel.CENTER;
    public final static int TEXT_RIGHT  = JLabel.RIGHT;
    
    public final static int ALIGN_TOP    = 0;
    public final static int ALIGN_MIDDLE = 1;
    public final static int ALIGN_BOTTOM = 2;
    
    private static Font FONT  = null;
    private static Font FONTB = null;
    
    private JLabel     label;
    private JTextField edit;
    private JButton    btn;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public LabelEditButton( String labelText ) {
        super( labelText );
        
        label = (JLabel)super.getComponent( 0 );
        edit = (JTextField)super.getComponent( 1 );
        
        btn = new JButton( "..." );
        btn.setFont( FONT );
        add( btn );
    }
    
    public void setActionCommand( String actionCommand ) {
        btn.setActionCommand( actionCommand );
    }
    
    public void addActionListener( ActionListener actionListener ) {
        btn.addActionListener( actionListener );
    }
    
    @Override
    public Dimension getMinimumSize() {
        int CX = label.getMinimumSize().width+edit.getMinimumSize().width+30;
        return new Dimension( CX, edit.getMinimumSize().height );
    }
    
    @Override
    public Dimension getPreferredSize() {
        if( !isVisible() || getParent() == null )
            return null;
        int CX = label.getPreferredSize().width+edit.getPreferredSize().width+30;
        return new Dimension( Math.min( CX, getParent().getWidth() ), edit.getPreferredSize().height );
    }
    
    @Override
    public void doLayout() {
        int lblCX = label.getPreferredSize().width;
        int lblCY = label.getPreferredSize().height;
        int x = 0, y = 0;
        
        super.doLayout();
        /* if( labelBase == ALIGN_TOP )
            label.setBounds( x, y, lblCX, lblCY );
        else if( labelBase == ALIGN_MIDDLE ) {
            y = (getHeight()-lblCY)/2;
            label.setBounds( x, y, lblCX, lblCY );
        } else if( labelBase == ALIGN_BOTTOM ) {
            y = getHeight()-lblCY;
            label.setBounds( x, y, lblCX, lblCY );
        } */
        int edCX = getWidth()-(x+lblCX+34);
        x += lblCX+4;
        edit.setBounds( x, y, edCX, 20 );
        x += edCX;
        btn.setBounds( x, y, 30, 20 );
    }
}
