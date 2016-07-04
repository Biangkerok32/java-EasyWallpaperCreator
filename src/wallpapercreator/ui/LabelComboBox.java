/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wallpapercreator.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author shinobisoft
 */
public class LabelComboBox extends JComponent {
    public final static String TAG = LabelComboBox.class.getSimpleName();
    
    public final static int TEXT_LEFT   = JLabel.LEFT;
    public final static int TEXT_CENTER = JLabel.CENTER;
    public final static int TEXT_RIGHT  = JLabel.RIGHT;
    
    public final static int ALIGN_TOP    = 0;
    public final static int ALIGN_MIDDLE = 1;
    public final static int ALIGN_BOTTOM = 2;
    
    private static Font FONT  = null;
    private static Font FONTB = null;
    private JLabel    label;
    private JComboBox combo;
    private int       labelBase = ALIGN_BOTTOM;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public LabelComboBox( String labelText, String[] comboItems ) {
        super();
        
        setOpaque( true );
        if( FONT == null ) {
            FONT  = new Font( "Dialog", 0, 12 );
            FONTB = FONT.deriveFont( Font.BOLD );
        }
        
        label = new JLabel( labelText );
        if( comboItems == null )
            combo = new JComboBox();
        else
            combo = new JComboBox( (String[])comboItems );
        
        label.setFont( FONT );
        combo.setFont( FONT );
        
        add( label );
        add( combo );
        
    }
    
    public void addItemListener( ItemListener itemListener ) {
        combo.addItemListener( itemListener );
    }
    
    public int getSelectedIndex() {
        return combo.getSelectedIndex();
    }
    
    public void setSelectedIndex( int index ) {
        combo.setSelectedIndex( index );
    }
    
    public String getSelectedItem() {
        return (String)combo.getSelectedItem();
    }
    
    public void setSelectedItem( String item ) {
        combo.setSelectedItem( item );
    }
    
    public void setLabelBaseAlignment( int alignment ) {
        if( alignment == ALIGN_TOP || alignment == ALIGN_MIDDLE || alignment == ALIGN_BOTTOM ) {
            labelBase = alignment;
            doLayout();
        }
    }
    
    public void setLabelTextAlignment( int alignment ) {
        if( alignment == TEXT_LEFT || alignment == TEXT_CENTER || alignment == TEXT_RIGHT ) {
            label.setHorizontalAlignment( alignment );
        }
    }
    
    public void setLabelBold( boolean bBold ) {
        label.setFont( ( bBold ) ? FONTB : FONT );
    }
    
    public void setLabelWidth( int width ) {
        Dimension sz = label.getPreferredSize();
        sz.width = width;
        label.setPreferredSize( sz );
    }
    
    @Override
    public Dimension getMinimumSize() {
        int CX = label.getMinimumSize().width+combo.getMinimumSize().width;
        return new Dimension( CX, combo.getMinimumSize().height );
    }
    
    @Override
    public Dimension getPreferredSize() {
        int CX = label.getPreferredSize().width+combo.getPreferredSize().width;
        return new Dimension( CX, combo.getPreferredSize().height );
    }
    
    @Override
    public void doLayout() {
        int lblCX = label.getPreferredSize().width;
        int lblCY = label.getPreferredSize().height;
        int x = 0, y = 0;
        
        switch( labelBase ) {
            case ALIGN_TOP: {
                label.setBounds( x, y, lblCX, lblCY );
                break;
            }
            
            case ALIGN_MIDDLE: {
                y = (getHeight()-lblCY)/2;
                label.setBounds( x, y, lblCX, lblCY );
                break;
            }
            
            case ALIGN_BOTTOM: {
                y = getHeight()-lblCY;
                label.setBounds( x, y, lblCX, lblCY );
                break;
            }
        }
        combo.setBounds( x+lblCX+4, 0, getWidth()-(x+lblCX+4), combo.getPreferredSize().height );
    }
    
    @Override
    public void setEnabled( boolean bEnable ) {
        if( getComponentCount() > 0 ) {
            Component[] comps = getComponents();
            for( Component c : comps ) {
                c.setEnabled( bEnable );
            }
        }
    }
}
