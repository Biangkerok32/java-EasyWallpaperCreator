/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wallpapercreator.ui;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

/**
 *
 * @author shinobisoft
 */
public class LabelEdit extends JComponent {
    public final static String TAG = LabelEdit.class.getSimpleName();
    
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
    private int        labelBase = ALIGN_BOTTOM;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public LabelEdit( String labelText ) {
        super();
        
        setOpaque( true );
        setLayout( null );
        
        if( FONT == null ) {
            FONT  = new Font( "Dialog", 0, 12 );
            FONTB = FONT.deriveFont( Font.BOLD );
        }
        
        label = new JLabel( labelText );
        edit  = new JTextField( 4 );
        
        label.setFont( FONT );
        edit.setFont( FONT );
        
        add( label );
        add( edit );
    }
    
    public String getText() {
        return edit.getText();
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
    
    public void setText( String text ) {
        edit.setText( text );
    }
    
    public void addDocumentListener( DocumentListener documentListener ) {
        edit.getDocument().addDocumentListener( documentListener );
    }
    
    @Override
    public Dimension getMinimumSize() {
        int CX = label.getMinimumSize().width+edit.getMinimumSize().width;
        return new Dimension( CX, edit.getMinimumSize().height );
    }
    
    @Override
    public Dimension getPreferredSize() {
        if( !isVisible() || getParent() == null )
            return null;
        
        int CX = label.getPreferredSize().width+edit.getPreferredSize().width;
        return new Dimension( Math.max( CX, getParent().getWidth() ), edit.getPreferredSize().height );
    }
    
    @Override
    public void doLayout() {
        int lblCX = label.getPreferredSize().width;
        int lblCY = label.getPreferredSize().height;
        int x = 0, y = 0;
        
        if( labelBase == ALIGN_TOP )
            label.setBounds( x, y, lblCX, lblCY );
        else if( labelBase == ALIGN_MIDDLE ) {
            y = (getHeight()-lblCY)/2;
            label.setBounds( x, y, lblCX, lblCY );
        } else if( labelBase == ALIGN_BOTTOM ) {
            y = getHeight()-lblCY;
            label.setBounds( x, y, lblCX, lblCY );
        }
        edit.setBounds( x+lblCX+4, 0, getWidth()-(x+lblCX+4), edit.getPreferredSize().height );
    }
}
