/*
 * Copyright (C) 2016 ShinobiSoft Software
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

package wallpapercreator.utils;

import java.awt.Image;
import java.net.URL;
import java.util.Scanner;
import javax.swing.ImageIcon;

/**
 * A utility class to retrieve an applications resources.
 * This class can most definitely be expanded to include custom resource
 * types as well. This class is in fact a stripped down version of another
 * custom class I wrote that does that very thing.
 * @author shinobisoft
 */
public class AppResource {
    public final static String TAG = AppResource.class.getSimpleName();
    
    private ClassLoader classLoader;
    
    /**
     * Constructor
     * @param loader The Class object from which to retrieve resources from
     */
    public AppResource( Class loader ) {
        this.classLoader = loader.getClassLoader();
    }
    
    /**
     * Gets an ImageIcon resource located within a .jar application.
     * @param iconResource Relative path to the desired icon resource
     * @return An ImageIcon object containing the requested resource or null.
     */
    public ImageIcon getIcon( String iconResource ) {
        URL imgURL = classLoader.getResource( iconResource );
        if( imgURL != null ) {
            return new ImageIcon( imgURL );
        }
        return null;
    }
    
    /**
     * Gets an Image resource located within a .jar application.
     * @param imageResource Full relative path to the desired image resource
     * @return An Image object containing the requested resource or null.
     */
    public Image getImage( String imageResource ) {
        URL imgURL = classLoader.getResource( imageResource );
        if( imgURL != null ) {
            return new ImageIcon( imgURL ).getImage();
        }
        return null;
    }
    
    /**
     * Gets the raw string data of a resource located within a .jar application.
     * @param textResource Full relative path to the desired text resource
     * @return A String containing the requested resource data or null.
     */
    public String getRawText( String textResource ) {
        StringBuilder result = new StringBuilder( "" );
        try ( Scanner scanner = new Scanner( classLoader.getResourceAsStream( textResource ) ) ) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append( line ).append( "\n" );
            }
            scanner.close();
        }

        if( result.length() != 0 )
            return result.toString();
        return null;
    }
}
