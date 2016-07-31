/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wallpapercreator.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.UIManager;
import wallpapercreator.utils.AppResource;

/**
 *
 * @author shinobisoft
 */
public class ButtonBar extends JPanel {
    public final static String TAG = ButtonBar.class.getSimpleName();
    
    private AppResource res;
    private Dimension   btnSize = new Dimension( 24, 24 );
    private Dimension   sepSize = new Dimension( 10, 24 );
    
    private class Separator extends JComponent {
        
        public Separator() { 
            super();
            setOpaque( true );
            setMinimumSize( sepSize );
        }
        
        @Override
        public void paintComponent( Graphics g ) {
            super.paintComponent( g );
            Color clrLight = UIManager.getColor( "controlHighlight" );
            Color clrDark  = UIManager.getColor( "controlShadow" );
            
            g.setColor( clrLight );
            g.drawLine( 4, 0, 4, getHeight() );
            g.setColor( clrDark );
            g.drawLine( 5, 0, 5, getHeight() );
        }
    }
    
    public ButtonBar( AppResource appResource ) {
        super();
        
        setLayout( null );
        
        res = appResource;
        
        HoverButton btnNew = (HoverButton)add( new HoverButton( res.getIcon( "res/menu/fileNew.png" ) ) );
        HoverButton btnSave = (HoverButton)add( new HoverButton( res.getIcon( "res/menu/fileSave.png" ) ) );
        add( new Separator() );
        HoverButton btnView = (HoverButton)add( new HoverButton( res.getIcon( "res/menu/viewPreview.png" ) ) );
        add( new Separator() );
        HoverButton btnHelp = (HoverButton)add( new HoverButton( res.getIcon( "res/menu/helpContents.png" ) ) );
        add( new Separator() );
        HoverButton btnAbout = (HoverButton)add( new HoverButton( res.getIcon( "res/menu/helpAbout.png" ) ) );
        
        btnNew.setActionCommand( "file.New" );
        btnNew.setToolTipText( "Create a new wallpaper" );
        btnSave.setActionCommand( "file.Save" );
        btnSave.setToolTipText( "Save the current wallpaper" );
        btnView.setActionCommand( "view.Preview" );
        btnView.setToolTipText( "Preview the current wallpaper" );
        btnHelp.setActionCommand( "help.Contents" );
        btnHelp.setToolTipText( "View the help contents" );
        btnAbout.setActionCommand( "help.About" );
        btnAbout.setToolTipText( "View information about this application" );
    }
    
    public JButton getButton( int id ) {
        if( id >= 0 && id < getComponentCount() ) {
            if( getComponent( id ) instanceof JButton || getComponent( id ) instanceof ImageButton )
                return (JButton)getComponent( id );
        }
        return null;
    }
    
    @Override
    public Dimension getMinimumSize() {
        return new Dimension( 100, 32 );
    }
    
    @Override
    public Dimension getPreferredSize() {
        if( !isVisible() || getParent() == null )
            return null;
        
        Insets i = getParent().getInsets();
        int CX = getParent().getWidth()-(i.left+i.right);
        return new Dimension( CX, 32 );
    }
    
    @Override
    public void doLayout() {
        int x = 4, y = 4;
        int iter = 0;
        
        if( getComponentCount() != 0 ) {
            Component[] components = getComponents();
            for( Component c : components ) {
                if( c instanceof JButton ) {
                    c.setBounds( x, y, 24, 24 );
                    x += 24;
                } else {
                    c.setBounds( x, y, 10, 24 );
                    x += 10;
                }
            }
        }
    }
}
