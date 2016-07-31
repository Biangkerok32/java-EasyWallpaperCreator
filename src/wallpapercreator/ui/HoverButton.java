/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wallpapercreator.ui;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.border.Border;

/**
 *
 * @author shinobisoft
 */
public class HoverButton extends ImageButton {
    public static String TAG = HoverButton.class.getSimpleName();

    private Border    mNullBorder;
    private Border    mHoverBorder;
    private Border    mPressedBorder;
    
    public HoverButton( Icon icon ) {
        super( icon );
        this.mNullBorder    = BorderFactory.createEmptyBorder();
        this.mHoverBorder   = BorderFactory.createRaisedSoftBevelBorder();
        this.mPressedBorder = BorderFactory.createLoweredSoftBevelBorder();
        setBorder( mNullBorder );
        addMouseListener( new ButtonMouseListener() );
    }
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public HoverButton( Icon icon, String actionCommand, ActionListener actionListener ) {
        super( icon, actionCommand, actionListener );
        this.mNullBorder    = BorderFactory.createEmptyBorder();
        this.mHoverBorder   = BorderFactory.createRaisedSoftBevelBorder();
        this.mPressedBorder = BorderFactory.createLoweredSoftBevelBorder();
        setBorder( mNullBorder );
        addMouseListener( new ButtonMouseListener() );
    }
    
    private class ButtonMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked( MouseEvent e ) {
            setBorder( mHoverBorder );
        }

        @Override
        public void mousePressed( MouseEvent e ) {
            setBorder( mPressedBorder );
        }

        @Override
        public void mouseReleased( MouseEvent e ) {
        }

        @Override
        public void mouseEntered( MouseEvent e ) {
            if( isEnabled() && getBorder() == mNullBorder )
                setBorder( mHoverBorder );
        }

        @Override
        public void mouseExited( MouseEvent e ) {
            if( isEnabled() )
                setBorder( mNullBorder );
        }
    }
}
