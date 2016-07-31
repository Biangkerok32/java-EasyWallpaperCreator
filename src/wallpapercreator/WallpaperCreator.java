/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpapercreator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import wallpapercreator.ui.AboutDialog;
import wallpapercreator.ui.ButtonBar;
import wallpapercreator.ui.HR;
import wallpapercreator.ui.HelpDialog;
import wallpapercreator.ui.LabelComboBox;
import wallpapercreator.ui.LabelEdit;
import wallpapercreator.ui.LabelEditButton;
import wallpapercreator.ui.LinearLayout;
import wallpapercreator.ui.Menu;
import wallpapercreator.ui.Preview;
import wallpapercreator.ui.PreviewView;
import wallpapercreator.ui.PropertiesPanel;
import wallpapercreator.ui.Statusbar;
import wallpapercreator.utils.AppResource;

/**
 *
 * @author shinobisoft
 */
public class WallpaperCreator extends JFrame {
    public final static String TAG = WallpaperCreator.class.getSimpleName();
    public static WallpaperCreator theApp = null;
    public final static String TITLE = "Easy Wallpaper Creator";
    
    private String donateURL = "https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=william%2eweckel%40gmail%2ecom&lc=US&item_name=ShinobiSoft%20Software&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted";
    private int mMinCX = 1024;
    private int mMinCY = (int)(mMinCX/16)*9; // 16:9 frame height ratio // (mMinCX/4)*3; // 4:3 frame height ratio // (mMinCX*0.75); // Default frame height. Same as 4:3
    
    private JMenuBar mMenubar = null;
    private ButtonBar mToolbar = null;
    private JPanel mClient = null;
    private LabelEdit lblWidth;
    private LabelEdit lblHeight;
    private LabelEditButton lblBgColor;
    private LabelEditButton lblOverlay;
    private LabelComboBox lblCombo;
    private PreviewView preview;
    private Statusbar sbar;
    
    private String[] STYLES = { "Center", "Stretch", "Tile" };
    private ActionHandler   actionHandler   = null;
    
    private AppResource     res;
    private Config          config;
    
    private static FileNameExtensionFilter lastFilter = null;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public WallpaperCreator() {
        super();

        setLayout( new BorderLayout() );
        setTitle( TITLE );
        setMinimumSize( new Dimension( mMinCX, mMinCY ) );
        pack();
        
        res = new AppResource( WallpaperCreator.class );
        
        actionHandler = new ActionHandler();
        setJMenuBar( mMenubar = getMenubar() );
        mMenubar.getMenu( 0 ).getPopupMenu().addPopupMenuListener( new FileMenuListener() );
        mMenubar.getMenu( 1 ).getPopupMenu().addPopupMenuListener( new ViewMenuListener() );
        
        mClient = new JPanel();
        setContentPane( mClient );
        mClient.setLayout( new BorderLayout() );
        
        mToolbar = new ButtonBar( res );
        mToolbar.getButton( 0 ).addActionListener( actionHandler );
        mToolbar.getButton( 1 ).addActionListener( actionHandler );
        mToolbar.getButton( 3 ).addActionListener( actionHandler );
        mToolbar.getButton( 5 ).addActionListener( actionHandler );
        mToolbar.getButton( 7 ).addActionListener( actionHandler );
        mToolbar.getButton( 1 ).setEnabled( false );
        mToolbar.getButton( 3 ).setEnabled( false );
        
        mClient.add( mToolbar, BorderLayout.NORTH );
        
        PropertiesPanel pp = new PropertiesPanel();
        JPanel p1 = new JPanel();
        p1.setLayout( new LinearLayout( LinearLayout.HORIZONTAL, 10 ) );
        
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        
        config = new Config();
        config.imgCX = screen.width;
        config.imgCY = screen.height;
        
        lblWidth = new LabelEdit( "Width:" );
        lblWidth.setText( "" + screen.width );
        lblWidth.addDocumentListener( new ImageWidthListener() );
        
        lblHeight = new LabelEdit( "Height:" );
        lblHeight.setText( "" + screen.height );
        lblHeight.addDocumentListener( new ImageHeightListener() );
        
        config.memImage = new BufferedImage( config.imgCX, config.imgCY, BufferedImage.TYPE_INT_ARGB );
        
        p1.add( lblWidth );
        p1.add( lblHeight );

        pp.add( p1 );
        lblBgColor = new LabelEditButton( "Bg color:" );
        lblBgColor.setLabelWidth( 60 );
        lblBgColor.setLabelTextAlignment( LabelComboBox.TEXT_RIGHT );
        
        lblOverlay = new LabelEditButton( "Image:" );
        lblOverlay.setLabelWidth( 60 );
        lblOverlay.setLabelTextAlignment( LabelComboBox.TEXT_RIGHT );
        
        lblCombo   = new LabelComboBox( "Style:", STYLES );

        lblBgColor.setText( "FF000000" );
        lblBgColor.addDocumentListener( new BgColorListener() );
        lblBgColor.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                JColorChooser dlg = new JColorChooser();
                Color c;
                if( ( c = dlg.showDialog( WallpaperCreator.this, "Choose color", config.bgColor ) ) != null ) {
                    int a = c.getAlpha();
                    int r = c.getRed();
                    int g = c.getGreen();
                    int b = c.getBlue();
                    String fmt = "%02X%02X%02X%02X";
                    lblBgColor.setText( String.format( fmt, a, r, g, b ) );
                    config.isModified = true;
                }
            }
        } );
        
        lblOverlay.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                JFileChooser dlg = new JFileChooser();
                FileNameExtensionFilter imgFilter = new FileNameExtensionFilter( "Image files", "bmp", "jpg", "jpeg", "png" );
                
                dlg.addChoosableFileFilter( imgFilter );
                dlg.setFileFilter( imgFilter );
                
                String sep = java.io.File.separator;
                if( !config.imgSrc.isEmpty() ) {
                    String p = config.imgSrc.substring( 0, config.imgSrc.lastIndexOf( sep ) );
                    dlg.setCurrentDirectory( new File( p ) );
                    dlg.setSelectedFile( new File( config.imgSrc ) );
                }
                
                if( dlg.showOpenDialog( WallpaperCreator.this ) == JFileChooser.APPROVE_OPTION ) {
                    lblOverlay.setText( dlg.getSelectedFile().getAbsolutePath() );
                    config.imgSrc = lblOverlay.getText();
                    try {
                        config.overlayImage = ImageIO.read( new File( lblOverlay.getText() ) );
                        if( config.overlayImage != null ) {
                            config.isModified = true;
                            updateImages();
                            lblCombo.setEnabled( true );
                            mToolbar.getButton( 1 ).setEnabled( true );
                            mToolbar.getButton( 3 ).setEnabled( true );
                        }
                    } catch (IOException ex) {}
                    
                } else
                    lblCombo.setEnabled( false );
            }
        } );
        
        lblCombo.setLabelTextAlignment( LabelComboBox.TEXT_RIGHT );
        lblCombo.setLabelWidth( 80 );
        lblCombo.setEnabled( false );
        lblCombo.addItemListener( new ItemListener() {
            @Override
            public void itemStateChanged( ItemEvent e ) {
                int index = lblCombo.getSelectedIndex();
                switch( index ) {
                    
                    case 0:
                        config.imgStyle = "CENTER";
                        break;
                        
                    case 1:
                        config.imgStyle = "STRETCH";
                        break;
                        
                    case 2:
                        config.imgStyle = "TILE";
                        break;
                }
                
                if( index >= 0 && index <= 2 )
                    updateImages();
            }
        } );
        
        pp.add( lblBgColor );
        pp.add( lblOverlay );
        pp.add( lblCombo );
        pp.add( new HR() );
        
        mClient.add( pp, BorderLayout.WEST );
        
        preview = new PreviewView();
        preview.addComponentListener( new ComponentListener() {
            @Override
            public void componentResized( ComponentEvent e ) {
                updatePreview();
            }

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {}

            @Override
            public void componentHidden(ComponentEvent e) {}
        } );
        
        mClient.add( preview, BorderLayout.CENTER );
        
        sbar = new Statusbar();
        sbar.addPanel( 200 );
        sbar.addPanel( 200 );
        sbar.addPanel( 200 );
        mClient.add( sbar, BorderLayout.SOUTH );
        
        setLocationRelativeTo( null );
        addWindowListener( new FrameListener() );
        setVisible( true );
        updateImages();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main( final String[] args ) {
        // Add jgoodies-common-1.8.1.jar and jgoodies-looks-2.7.0.jar to your project libraries
        boolean[] bResults = new boolean[] { false, false };
        int iter = 0;
        
        // This checks for the jgoodies libraries
        String packages[] = { "com.jgoodies.looks.plastic.PlasticXPLookAndFeel", "com.jgoodies.common.base.SystemUtils" };
        for ( String pkg : packages ) {
            
            try {
                Class<?> cls = (Class<?>)Class.forName( pkg );
                bResults[ iter ] = (cls != null);
            } catch ( ClassNotFoundException uncaught ) {
                System.out.println( "ClassNotFoundException : " + pkg );
            } catch ( NoClassDefFoundError e ) {
                // We catch the error to avoid killing the application
                System.out.println( "NoClassDefFoundError : " + pkg );
            }
            
            iter++;
        }
        
        // If both jgoodies libraries exist, set the LookAndFeel
        if( bResults[ 0 ] && bResults[ 1 ] ) {
            try {
                UIManager.setLookAndFeel( "com.jgoodies.looks.plastic.PlasticXPLookAndFeel" );
            } catch ( ClassNotFoundException ex ) {
                System.out.println( "UIManager.setLookAndFeel(): Class Not Found Exception" );
            } catch ( UnsupportedLookAndFeelException ex ) { 
                System.out.println( "UIManager.setLookAndFeel(): Unsupported Look And Feel Exception" );
            } catch ( InstantiationException ex ) {
                System.out.println( "UIManager.setLookAndFeel(): Instantiation Exception" );
            } catch ( IllegalAccessException ex ) {
                System.out.println( "UIManager.setLookAndFeel(): Illegal Access Exception" );
            }
        }
        
        SwingUtilities.invokeLater( new Runnable() {
            
            @Override
            public void run() {
                theApp = new WallpaperCreator();
            }
        });
    }
    
    private void updateImages() {
        if( config.memImage != null )
            config.memImage.flush();

        config.memImage = new BufferedImage( config.imgCX, config.imgCY, BufferedImage.TYPE_INT_ARGB );

        Graphics2D dcOut = config.memImage.createGraphics();

        dcOut.setColor( config.bgColor );
        dcOut.fillRect( 0, 0, config.imgCX, config.imgCY );

        if( config.overlayImage != null ) {
            int outCX = config.memImage.getWidth(), outCY = config.memImage.getHeight();
            int srcCX = config.overlayImage.getWidth(), srcCY = config.overlayImage.getHeight();
            
            if( config.imgStyle.equals( "CENTER" ) ) {
                // FIXME:
                // This could possibly crop the overlay image if it is larger
                // then the destination image.
                int x = (outCX-srcCX)/2, y = (outCY-srcCY)/2;
                dcOut.drawImage( config.overlayImage, x, y, null );
                
            } else if( config.imgStyle.equals( "STRETCH" ) ) {
                // This will also "shrink" the overlay image if it's size is
                // greater than that of the destination image.
                dcOut.drawImage( config.overlayImage, 0, 0, outCX, outCY, null );
                
            } else if( config.imgStyle.equals( "TILE" ) ) {
                int x = 0, y = 0;
                
                while( y < config.imgCY ) {
                    dcOut.drawImage( config.overlayImage, x, y, null );
                    if( (x+srcCX >= outCX ) ) {
                        x = 0;
                        y += srcCY;
                    } else
                        x += srcCX;
                }
            }
            sbar.setText( 1, "Overlay Image:  " + srcCX + " x " + srcCY );
            dcOut.dispose();
        } else
            sbar.setText( 1, "Overlay Image:  0 x 0" );
        
        sbar.setText( 0, "Output Image:  " + config.imgCX + " x " + config.imgCY );
        updatePreview();
    }
    
    public void updatePreview() {
        if( config.previewImage != null )
            config.previewImage.flush();
        
        config.previewImage = new BufferedImage( preview.getWidth(), preview.getHeight(), BufferedImage.TYPE_INT_ARGB );
        
        Graphics2D dcPreview = config.previewImage.createGraphics();
        int preCX = config.previewImage.getWidth(), preCY = config.previewImage.getHeight();
        dcPreview.drawImage( config.memImage, 0, 0, preCX, preCY, null );
        dcPreview.dispose();

        preview.setImage( config.previewImage );
        sbar.setText( 2, "Preview Image:  " + preCX + " x " + preCY );
    }
    
    
    private JMenuBar getMenubar() {
        JMenuBar mbar = new JMenuBar();
        
        JMenu fileMenu = new JMenu( "File" );
        JMenuItem fileNew  = Menu.menuItem( "&New\tCtrl+N", "file.New", actionHandler );
        JMenuItem fileSave = Menu.menuItem( "&Save\tCtrl+S", "file.Save", actionHandler );
        JMenuItem fileSaveAs = Menu.menuItem( "Save &As...", "file.SaveAs", actionHandler );
        JMenuItem fileExit = Menu.menuItem( "E&xit\tAlt+X", "file.Exit", actionHandler );
        
        fileNew.setIcon( res.getIcon( "res/menu/fileNew.png" ) );
        fileSave.setIcon( res.getIcon( "res/menu/fileSave.png" ) );
        fileSaveAs.setIcon( res.getIcon( "res/menu/fileSaveAs.png" ) );
        fileExit.setIcon( res.getIcon( "res/menu/fileExit.png" ) );
        
        fileMenu.add( fileNew );
        fileMenu.add( fileSave );
        fileMenu.add( fileSaveAs );
        fileMenu.addSeparator();
        fileMenu.add( fileExit );
        
        JMenu viewMenu = new JMenu( "View" );
        JCheckBoxMenuItem viewStatusbar = Menu.checkMenuItem( "&Statusbar", "view.Statusbar", actionHandler, true );
        JCheckBoxMenuItem viewToolbar = Menu.checkMenuItem( "&Toolbar", "view.Toolbar", actionHandler, true );
        JMenuItem viewPreview = Menu.menuItem( "&Preview\tF12", "view.Preview", actionHandler );
        
        viewPreview.setIcon( res.getIcon( "res/menu/viewPreview.png" ) );
        
        viewMenu.add( viewStatusbar );
        viewMenu.add( viewToolbar );
        viewMenu.addSeparator();
        viewMenu.add( viewPreview );
        
        JMenu helpMenu = new JMenu( "Help" );
        JMenuItem helpContents = Menu.menuItem( "&Contents\tF1", "help.Contents", actionHandler );
        JMenuItem helpDonate = Menu.menuItem( "&Donate to ShinobiSoft Software", "help.Donate", actionHandler );
        JMenuItem helpAbout = Menu.menuItem( "&About Wallpaper Creator", "help.About", actionHandler );
        
        helpContents.setIcon( res.getIcon( "res/menu/helpContents.png" ) );
        helpAbout.setIcon( res.getIcon( "res/menu/helpAbout.png" ) );
        
        helpMenu.add( helpContents );
        helpMenu.addSeparator();
        helpMenu.add( helpDonate );
        helpMenu.addSeparator();
        helpMenu.add( helpAbout );
        
        mbar.add( fileMenu );
        mbar.add( viewMenu );
        mbar.add( helpMenu );
        return mbar;
    }
    
    private class FrameListener extends WindowAdapter {
        @Override
        public void windowClosing( WindowEvent e ) {
            dispose();
        }

        @Override
        public void windowClosed( WindowEvent e ) {
            System.exit( 0 );
        }
    }
    
    private class ImageWidthListener implements DocumentListener {

        @Override
        public void insertUpdate( DocumentEvent e ) {
            config.imgCX = Integer.parseInt( lblWidth.getText() );
            updateImages();
        }

        @Override
        public void removeUpdate( DocumentEvent e ) {
            if( lblWidth.getText().length() > 0 ) {
                config.imgCX = Integer.parseInt( lblWidth.getText() );
                updateImages();
            }
        }

        @Override
        public void changedUpdate( DocumentEvent e ) {
        }
    }
    
    private class ImageHeightListener implements DocumentListener {

        @Override
        public void insertUpdate( DocumentEvent e ) {
            config.imgCY = Integer.parseInt( lblHeight.getText() );
            updateImages();
        }

        @Override
        public void removeUpdate( DocumentEvent e ) {
            if( lblHeight.getText().length() > 0 ) {
                config.imgCY = Integer.parseInt( lblHeight.getText() );
                updateImages();
            }
        }

        @Override
        public void changedUpdate( DocumentEvent e ) {
        }
    }
    
    private class BgColorListener implements DocumentListener {

        @Override
        public void insertUpdate( DocumentEvent e ) {
            if( lblBgColor.getText().length() == 8 ) {
                String clr = lblBgColor.getText();
                
                int a = Integer.parseInt( clr.substring( 0, 2 ), 16 );
                int r = Integer.parseInt( clr.substring( 2, 4 ), 16 );
                int g = Integer.parseInt( clr.substring( 4, 6 ), 16 );
                int b = Integer.parseInt( clr.substring( 6 ), 16 );
                
                config.bgColor = new Color( r, g, b, a );
                updateImages();
            }
        }

        @Override
        public void removeUpdate( DocumentEvent e ) {
            if( lblBgColor.getText().length() == 8 ) {
                String clr = lblBgColor.getText();
                
                int a = Integer.parseInt( clr.substring( 0, 2 ), 16 );
                int r = Integer.parseInt( clr.substring( 2, 4 ), 16 );
                int g = Integer.parseInt( clr.substring( 4, 6 ), 16 );
                int b = Integer.parseInt( clr.substring( 6 ), 16 );
                
                config.bgColor = new Color( r, g, b, a );
                updateImages();
            }
        }

        @Override
        public void changedUpdate( DocumentEvent e ) {
        }
    }
    
    private class ActionHandler implements ActionListener {
        @Override
        public void actionPerformed( ActionEvent e ) {
            String cmd = e.getActionCommand();

            switch( cmd ) {

                case "file.New": {
                    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

                    // Reset all data members
                    if( config.overlayImage != null ) {
                        config.overlayImage.flush();
                        config.overlayImage = null;
                    }
                    
                    config.imgCX = screen.width;
                    config.imgCY = screen.height;
                    config.bgColor = new Color( 0, 0, 0, 255 );
                    config.imgSrc = "";
                    config.memDest = "";
                    config.memDestFormat = "png";
                    config.imgStyle = "CENTER";
                    config.isModified = false;
                    
                    // And reset the UI controls as well
                    lblWidth.setText( Integer.toString( config.imgCX ) );
                    lblHeight.setText( Integer.toString( config.imgCY ) );
                    lblBgColor.setText( "FF000000" );
                    lblOverlay.setText( "" );
                    lblCombo.setSelectedIndex( 0 );
                    lblCombo.setEnabled( false );
                    mToolbar.getButton( 1 ).setEnabled( false );
                    mToolbar.getButton( 3 ).setEnabled( false );
                    updateImages();
                    break;
                }

                case "file.Save": {
                    if( config.memDest.isEmpty() )
                        actionPerformed( new ActionEvent( e.getSource(), ActionEvent.ACTION_PERFORMED, "file.SaveAs" ) );
                    
                    else {

                        try {
                            boolean bSuccess = false;
                            bSuccess = ImageIO.write( config.memImage, config.memDestFormat, new File( config.memDest ) );
                            config.isModified = ( bSuccess ) ? false : true;
                        } catch (IOException ex) {}
                    }
                    break;
                }

                case "file.SaveAs": {
                    JFileChooser dlg = new JFileChooser();
                    
                    FileNameExtensionFilter gifFilter = new FileNameExtensionFilter( "GIF images", "gif" );
                    FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter( "JPG images", "jpg" );
                    FileNameExtensionFilter pngFilter = new FileNameExtensionFilter( "PNG images", "png" );

                    dlg.addChoosableFileFilter( gifFilter );
                    dlg.addChoosableFileFilter( jpgFilter );
                    dlg.addChoosableFileFilter( pngFilter );
                    
                    if( lastFilter != null )
                        dlg.setFileFilter( lastFilter );
                    else
                        dlg.setFileFilter( pngFilter );
                    
                    if( dlg.showSaveDialog( WallpaperCreator.this ) == JFileChooser.APPROVE_OPTION ) {
                        
                        config.memDest = dlg.getSelectedFile().getAbsolutePath();
                        config.memDestFormat = "png";
                        FileNameExtensionFilter filter = (FileNameExtensionFilter)dlg.getFileFilter();
                        lastFilter = filter;
                        
                        if( filter.getDescription().startsWith( "GIF" ) )
                            config.memDestFormat = "gif";
                        else if( filter.getDescription().startsWith( "JPG" ) )
                            config.memDestFormat = "jpg";
                        
                        if( !config.memDest.endsWith( "." + config.memDestFormat ) )
                            config.memDest += "." + config.memDestFormat;
                        
                        actionPerformed( new ActionEvent( e.getSource(), ActionEvent.ACTION_PERFORMED, "file.Save" ) );
                    }
                    break;
                }

                case "file.Exit": {
                    processWindowEvent( new WindowEvent( WallpaperCreator.this, WindowEvent.WINDOW_CLOSING ) );
                    break;
                }

                case "view.Statusbar": {
                    JMenu viewMenu = mMenubar.getMenu( 1 );
                    if( sbar.isVisible() ) {
                        sbar.setVisible( false );
                        ((JCheckBoxMenuItem)viewMenu.getMenuComponent( 0 )).setSelected( false );
                    } else {
                        sbar.setVisible( true );
                        ((JCheckBoxMenuItem)viewMenu.getMenuComponent( 0 )).setSelected( true );
                    }
                    break;
                }
                
                case "view.Toolbar": {
                    JMenu viewMenu = mMenubar.getMenu( 1 );
                    if( mToolbar.isVisible() ) {
                        mToolbar.setVisible( false );
                        ((JCheckBoxMenuItem)viewMenu.getMenuComponent( 1 )).setSelected( false );
                    } else {
                        mToolbar.setVisible( true );
                        ((JCheckBoxMenuItem)viewMenu.getMenuComponent( 1 )).setSelected( true );
                    }
                    break;
                }
                
                case "view.Preview": {
                    Preview view = new Preview( config.memImage );
                    view.setVisible( true );
                    break;
                }

                case "help.Contents": {
                    HelpDialog hlpDlg = new HelpDialog();
                    hlpDlg.setVisible( true );
                    break;
                }
                
                case "help.Donate": {
                    openURL( donateURL );
                    break;
                }
                
                case "help.About": {
                    AboutDialog dlg = new AboutDialog();
                    dlg.setVisible( true );
                    break;
                }
            }
        }
    }
    
    private class FileMenuListener implements PopupMenuListener {

        @Override
        public void popupMenuWillBecomeVisible( PopupMenuEvent e ) {
            JPopupMenu menu = (JPopupMenu)e.getSource();
            menu.getComponent( 1 ).setEnabled( config.isModified );
            menu.getComponent( 2 ).setEnabled( config.isModified );
            if( mToolbar != null ) {
                mToolbar.getButton( 1 ).setEnabled( config.isModified );
            }
        }

        @Override
        public void popupMenuWillBecomeInvisible( PopupMenuEvent e ) {}

        @Override
        public void popupMenuCanceled( PopupMenuEvent e ) {}
        
    }
    
    private class ViewMenuListener implements PopupMenuListener {
        
        @Override
        public void popupMenuWillBecomeVisible( PopupMenuEvent e ) {
            JPopupMenu menu = (JPopupMenu)e.getSource();
            menu.getComponent( 3 ).setEnabled( !config.imgSrc.isEmpty() );
            if( mToolbar != null ) {
                mToolbar.getButton( 3 ).setEnabled( !config.imgSrc.isEmpty() );
            }
        }

        @Override
        public void popupMenuWillBecomeInvisible( PopupMenuEvent e ) {}

        @Override
        public void popupMenuCanceled( PopupMenuEvent e ) {}
        
    }
    
    private void openURL( String url ) {
        String[] browsers = { "google-chrome", "firefox", "opera",
            "epiphany", "konqueror", "conkeror", "midori", "kazehakase", "mozilla" };
        String errMsg = "Error attempting to launch web browser";

        /**
         * Opens the specified web page in the user's default browser
         * @param url A web address (URL) of a web page (ex: "http://www.google.com/")
        */
        try {  //attempt to use Desktop library from JDK 1.6+
            Class<?> d = Class.forName( "java.awt.Desktop" );
            d.getDeclaredMethod( "browse", new Class[] {java.net.URI.class} ).invoke(
                d.getDeclaredMethod( "getDesktop" ).invoke( null ),
                new Object[] { java.net.URI.create( url ) } );
           //above code mimicks:  java.awt.Desktop.getDesktop().browse()
        }
        catch ( Exception ignore ) {  //library not available or failed
            String osName = System.getProperty( "os.name" );
            try {
                if ( osName.startsWith( "Mac OS" ) ) {
                    Class.forName( "com.apple.eio.FileManager" ).getDeclaredMethod(
                        "openURL", new Class[] { String.class }).invoke( null,
                        new Object[] { url } );
                
                } else if ( osName.startsWith( "Windows" ) ) {
                    Runtime.getRuntime().exec(
                        "rundll32 url.dll,FileProtocolHandler " + url );
                  
                } else { //assume Unix or Linux
                    String browser = null;
                    for ( String b : browsers )
                        if ( browser == null && Runtime.getRuntime().exec( new String[]
                            { "which", b } ).getInputStream().read() != -1 )
                            Runtime.getRuntime().exec( new String[] { browser = b, url } );
                    
                    if ( browser == null )
                        throw new Exception( Arrays.toString( browsers ) );
                }
            }
            catch ( Exception e ) {
               JOptionPane.showMessageDialog( WallpaperCreator.this, errMsg + "\n" + e.toString() );
            }
        }
    }
}
