/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wallpapercreator.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
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
public class AboutDialog extends JDialog {
    public final static String TAG = AboutDialog.class.getSimpleName();
    
    public AboutDialog() {
        super();
        
        AppResource res = new AppResource( theApp.getClass() );
        
        setResizable( false );
        setIconImage( res.getIcon( "res/icon/help-about.png" ).getImage() );
        setTitle( "About Easy Wallpaper Creator" );
        setLayout( new BorderLayout() );
        pack();
        
        String about = res.getRawText( "res/raw/about.html" );
        JEditorPane ed = new JEditorPane();
        
        ed.setContentType( "text/html" );
        ed.setEditable( false );
        ed.setText( about );
        add( new JScrollPane( ed ), BorderLayout.CENTER );
        
        JPanel btnBar = new JPanel( new FlowLayout( FlowLayout.CENTER ) );
        btnBar.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ) );
        JButton btnOk = new JButton( "Close" );
        
        btnBar.add( btnOk );
        add( btnBar, BorderLayout.SOUTH );
        
        btnOk.setMinimumSize( new Dimension( 90, 28 ) );
        btnOk.setPreferredSize( new Dimension( 90, 28 ) );
        btnOk.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        } );
        setMinimumSize( new Dimension( 400, 400 ) );
        setLocationRelativeTo( theApp );
    }
}
