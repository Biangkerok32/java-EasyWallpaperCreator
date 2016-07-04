/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wallpapercreator.ui;

import java.awt.Event;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author shinobisoft
 */
public class Menu {
    public final static String TAG = Menu.class.getSimpleName();
    
    protected Menu() {}
    
    public static JMenuItem menuItem( String textData, String actionName, ActionListener actionListener ) {
        JMenuItem item = new JMenuItem();
        
        item.setActionCommand( actionName );
        item.addActionListener( actionListener );
        // Parse "data"
        // 1) Check for mnemonic char
        int pMnemonic = textData.indexOf( '&' );
        if( pMnemonic != -1 )
            item.setMnemonic( textData.charAt( pMnemonic+1 ) );
        
        // 2) Check for tab ( \t ) char
        int flags = 0;
        int accelKey = 0;
        String text; // = itemData.substring( 0, pTab );
        int pTab = textData.indexOf( '\t' );
        if( pTab != -1 ) {
            text = textData.substring( 0, pTab );
            String[] keys = null;
            String buf = textData.substring( pTab+1 );
            if( !buf.isEmpty() ) {
                if( buf.indexOf( '+' ) != -1 )
                    while( buf.indexOf( '+' ) != -1 )
                        buf = buf.replace( '+', '\n' );
                
                if( buf.indexOf( "\n" ) == -1 )
                    buf += "\n";

                keys = buf.split( "\n" );
                if( keys.length != 0 ) {
                    
                    for( String key : keys ) {
                        key = key.trim();

                        switch( key ) {
                            case "Ctrl":
                                flags |= Event.CTRL_MASK;
                                break;
                                
                            case "Alt":
                                flags |= Event.ALT_MASK;
                                break;
                                
                            case "Shift":
                                flags |= Event.SHIFT_MASK;
                                break;
                                
                            default: {
                                if( key.length() == 1 )
                                    accelKey = (int)key.charAt( 0 );
                                
                                else if( key.length() > 1 ) {
                                    // Virtual key
                                    switch( key.toUpperCase() ) {
                                        case "DELETE":
                                            accelKey = KeyEvent.VK_DELETE; break;
                                        case "TAB":
                                            accelKey = KeyEvent.VK_TAB; break;
                                        case "F1":
                                            accelKey = KeyEvent.VK_F1; break;
                                        case "F2":
                                            accelKey = KeyEvent.VK_F2; break;
                                        case "F3":
                                            accelKey = KeyEvent.VK_F3; break;
                                        case "F4":
                                            accelKey = KeyEvent.VK_F4; break;
                                        case "F5":
                                            accelKey = KeyEvent.VK_F5; break;
                                        case "F6":
                                            accelKey = KeyEvent.VK_F6; break;
                                        case "F7":
                                            accelKey = KeyEvent.VK_F7; break;
                                        case "F8":
                                            accelKey = KeyEvent.VK_F8; break;
                                        case "F9":
                                            accelKey = KeyEvent.VK_F9; break;
                                        case "F10":
                                            accelKey = KeyEvent.VK_F10; break;
                                        case "F11":
                                            accelKey = KeyEvent.VK_F11; break;
                                        case "F12":
                                            accelKey = KeyEvent.VK_F12; break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        else {
            text = textData;
        }
        
        if( text.indexOf( '&' ) != -1 )
            text = text.replace( "&", "" );
        
        item.setText( text );
        if( accelKey != 0 )
            item.setAccelerator( KeyStroke.getKeyStroke( accelKey, flags ) );

        return item;
    }
    
    /**
     * 
     * @param itemData String in the format of "&New\tCtrl+N"
     * @param cmdName String containing the name of the items action event when selected
     * @param actionListener 
     * @param bChecked 
     * @return  
     */
    public static JCheckBoxMenuItem checkMenuItem( String itemData, String cmdName, ActionListener actionListener, boolean bChecked  ) {

        JCheckBoxMenuItem item = new JCheckBoxMenuItem();
        item.setActionCommand( cmdName );
        item.addActionListener( actionListener );
        // Parse "data"
        // 1) Check for mnemonic char
        int pMnemonic = itemData.indexOf( '&' );
        if( pMnemonic != -1 )
            item.setMnemonic( itemData.charAt( pMnemonic+1 ) );
        
        // 2) Check for tab ( \t ) char
        int flags = 0;
        int accelKey = 0;
        String text; // = itemData.substring( 0, pTab );
        int pTab = itemData.indexOf( '\t' );
        if( pTab != -1 ) {
            text = itemData.substring( 0, pTab );
            String[] keys;
            String buf = itemData.substring( pTab+1 );
            if( !buf.isEmpty() && buf.indexOf( '+' ) != -1 ) {
                while( buf.indexOf( '+' ) != -1 )
                    buf = buf.replace( '+', '\n' );
                
                keys = buf.split( "\n" );
                if( keys.length != 0 ) {
                    
                    for( String key : keys ) {
                        key = key.trim();

                        switch( key ) {
                            case "Ctrl":
                                flags |= Event.CTRL_MASK;
                                break;
                                
                            case "Alt":
                                flags |= Event.ALT_MASK;
                                break;
                                
                            case "Shift":
                                flags |= Event.SHIFT_MASK;
                                break;
                                
                            default: {
                                if( key.length() == 1 )
                                    accelKey = (int)key.charAt( 0 );
                                
                                else if( key.length() > 1 ) {
                                    // Virtual key
                                    switch( key.toUpperCase() ) {
                                        case "DELETE":
                                            accelKey = Event.DELETE; break;
                                            
                                        case "F1":
                                            accelKey = KeyEvent.VK_F1; break;
                                        case "F2":
                                            accelKey = KeyEvent.VK_F2; break;
                                        case "F3":
                                            accelKey = KeyEvent.VK_F3; break;
                                        case "F4":
                                            accelKey = KeyEvent.VK_F4; break;
                                        case "F5":
                                            accelKey = KeyEvent.VK_F5; break;
                                        case "F6":
                                            accelKey = KeyEvent.VK_F6; break;
                                        case "F7":
                                            accelKey = KeyEvent.VK_F7; break;
                                        case "F8":
                                            accelKey = KeyEvent.VK_F8; break;
                                        case "F9":
                                            accelKey = KeyEvent.VK_F9; break;
                                        case "F10":
                                            accelKey = KeyEvent.VK_F10; break;
                                        case "F11":
                                            accelKey = KeyEvent.VK_F11; break;
                                        case "F12":
                                            accelKey = KeyEvent.VK_F12; break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        else {
            text = itemData;
        }
        
        if( text.indexOf( '&' ) != -1 )
            text = text.replace( "&", "" );
        
        item.setText( text );
        if( accelKey != 0 || flags != 0 )
            item.setAccelerator( KeyStroke.getKeyStroke( accelKey, flags ) );
        item.setState( bChecked );
        
        return item;
    }
}
