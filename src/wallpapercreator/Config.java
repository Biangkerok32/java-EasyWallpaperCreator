/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wallpapercreator;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author shinobisoft
 */
public class Config {
    public int             imgCX           = 0;
    public int             imgCY           = 0;
    public Color           bgColor         = new Color( 0, 0, 0, 255 );
    public String          imgSrc          = "";
    public String          memDest         = "";
    public String          memDestFormat   = "png";
    public String          imgStyle        = "CENTER";
    public BufferedImage   overlayImage    = null;
    public BufferedImage   previewImage    = null;
    public BufferedImage   memImage        = null;
    public boolean         isModified      = false;
}
