/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wallpapercreator.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import static wallpapercreator.WallpaperCreator.theApp;
import wallpapercreator.utils.AppResource;

/**
 *
 * @author shinobisoft
 */
public class HelpDialog extends JDialog {
    public final static String TAG = HelpDialog.class.getSimpleName();
    
    private AppResource res;
    private int hlpCX = 800, hlpCY = 600;
    private String HELP_FILE = "res/help/help.html";
    private String DLG_ICON  = "res/icon/help-contents.png";
    private String DLG_TITLE = "Help Contents";
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public HelpDialog() {
        super( theApp );
        
        res = new AppResource( theApp.getClass() );
        setIconImage( res.getIcon( DLG_ICON ).getImage() );
        setTitle( DLG_TITLE );
        pack();

        JPanel c = new JPanel( new BorderLayout() );
        c.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ) );
        setContentPane( c );
        
        String helpText = res.getRawText( HELP_FILE );
        JEditorPane edit = new JEditorPane();
        JScrollPane sb = new JScrollPane( edit );
        
        edit.setContentType( "text/html" );
        edit.setEditable( false );
        edit.setText( helpText );
        edit.setCaretPosition( 0 );
        
        c.add( sb, BorderLayout.CENTER );
        
        setMinimumSize( new Dimension( hlpCX, hlpCY ) );
        setLocationRelativeTo( theApp );
    }
}
