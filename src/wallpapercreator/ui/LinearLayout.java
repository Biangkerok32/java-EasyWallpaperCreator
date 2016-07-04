/*
 * Copyright (C) 2015 shinobisoft
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package wallpapercreator.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

/**
 *
 * @author shinobisoft
 */
public class LinearLayout implements LayoutManager {
    public final static String TAG = LinearLayout.class.getSimpleName();
    
    public final static int VERTICAL   = 0; // TOP to BOTTOM [ DEFAULT ]
    public final static int HORIZONTAL = 1; // LEFT to RIGHT
    
    private int orientation = VERTICAL;
    private int gap;
    private int minWidth = 0, minHeight = 0;
    private int preferredWidth = 0, preferredHeight = 0;
    private boolean sizeUnknown = true;

    public LinearLayout() {
        this( VERTICAL, 4 );
    }

    public LinearLayout( int style, int gap ) {
        super();
        this.orientation = style;
        this.gap = gap;
    }

    @Override
    public void addLayoutComponent( String name, Component comp ) {
    }

    @Override
    public void removeLayoutComponent( Component comp ) {
    }

    private void setSizes( Container parent ) {
        int nComps = parent.getComponentCount();
        Dimension d = null;
        preferredWidth = 0;
        preferredHeight = 0;
        minWidth = 0;
        minHeight = 0;

        if( orientation == VERTICAL ) {
            for ( int i = 0; i < nComps; i++ ) {
                Component c = parent.getComponent(i);
                if ( c.isVisible() ) {
                    d = c.getPreferredSize();

                    if (i > 0) {
                        preferredWidth += d.width+gap;
                    } else {
                        preferredWidth = d.width;
                    }
                    preferredHeight += d.height;

                    minWidth = Math.max( c.getPreferredSize().width, minWidth );
                    minHeight = preferredHeight;
                }
            }
        } else if( orientation == HORIZONTAL ) {
            int pad = (gap*2) + ((nComps-1)*gap);
            int cmpCX = ((parent.getWidth()-pad)/nComps);
            // System.out.println( "LinearLayout::setSizes() -> comps: " + nComps + " pad: " + pad + " cmpCX: " + cmpCX );
            
            for ( int i = 0; i < nComps; i++ ) {
                Component c = parent.getComponent(i);
                if ( c.isVisible() ) {
                    d = c.getPreferredSize();

                    if (i > 0) {
                        preferredWidth += cmpCX+gap; // d.width+gap;
                    } else {
                        preferredWidth = gap+cmpCX; // d.width;
                    }
                    preferredWidth += cmpCX+gap;
                    preferredHeight = d.height;

                    minWidth = preferredWidth;
                    minHeight = Math.max( c.getPreferredSize().height, minHeight );
                }
            }
        }
    }

    @Override
    public Dimension preferredLayoutSize( Container parent ) {
        Dimension dim = new Dimension( 0, 0 );
        int nComps = parent.getComponentCount();
        Insets insets = parent.getInsets();

        setSizes( parent );
        dim.width = parent.getWidth()-(insets.left+insets.right);
        dim.height = preferredHeight;
        sizeUnknown = false;

        return dim;
    }

    @Override
    public Dimension minimumLayoutSize( Container parent ) {
        Dimension dim = new Dimension( 0, 0 );
        int nComps = parent.getComponentCount();
        Insets insets = parent.getInsets();
        
        dim.width = minWidth + insets.left + insets.right;
        dim.height = minHeight + insets.top + insets.bottom;

        sizeUnknown = false;

        return dim;
    }

    @Override
    public void layoutContainer( Container parent ) {
        Insets insets = parent.getInsets();
        
        int nComps = parent.getComponentCount();
        int previousWidth = 0, previousHeight = 0;
        int x = insets.left, y = insets.top;

        // Go through the components' sizes, if neither
        // preferredLayoutSize nor minimumLayoutSize has
        // been called.
        if( sizeUnknown ) {
            setSizes( parent );
        }

        if( orientation == VERTICAL ) {
            for ( int i = 0 ; i < nComps ; i++ ) {

                Component c = parent.getComponent( i );
                if ( c.isVisible() ) {
                    Dimension d = c.getPreferredSize();

                    if( i > 0 ) {
                        y += previousHeight + gap;
                    }

                    // Set the component's size and position.
                    c.setBounds( x, y, d.width, d.height );

                    previousWidth = d.width;
                    previousHeight = d.height;
                }
            }
        } else if( orientation == HORIZONTAL ) {
            int pad = (gap*2) + ((nComps-1)*gap);
            int cmpCX = parent.getWidth()-gap; //(gap*2);
            cmpCX /= nComps;
            
            for ( int i = 0 ; i < nComps ; i++ ) {

                Component c = parent.getComponent( i );
                if ( c.isVisible() ) {
                    Dimension d = c.getPreferredSize();
                    
                    if( i > 0 ) {
                        x += previousWidth + gap;
                    }

                    // Set the component's size and position.
                    c.setBounds( x, y, cmpCX/*d.width*/, d.height );

                    previousWidth = cmpCX; //d.width;
                    previousHeight = d.height;
                }
            }
        }
    }

    @Override
    public String toString() {
        String fmt = "%s -> type=%s, gap=%d";
        String orient = "VERTICAL";
        if( this.orientation == HORIZONTAL )
            orient = "HORIZONTAL";
        return String.format( fmt, getClass().getName(), orient, gap );
    }
}
