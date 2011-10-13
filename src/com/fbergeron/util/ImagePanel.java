/*
 * Copyright (C) 2001  Frédéric Bergeron (fbergeron@users.sourceforge.net)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.fbergeron.util;

import java.awt.*;

/**
 * Panel drawn with an image.
 */
public class ImagePanel extends Panel {

    /**
     * Create an <code>ImagePanel</code> without image.
     */
    public ImagePanel() {
    }

    /**
     * Create an <code>ImagePanel</code> with an image.
     * @param image Image to be drawn on the canvas.
     */
    public ImagePanel( Image image ) {
        setImage( image );
    }

    /**
     * Gets the preferred size of the component.
     * @return The size of the image if specified.
     * Otherwise, returns default behavior.
     */
    public Dimension getPreferredSize() {
        if( _img == null )
            return( super.getPreferredSize() );
        int width = _img.getWidth( this );
        int height = _img.getHeight( this );
        return( new Dimension( width, height ) );
    }

    public void setBounds( int x, int y, int width, int height ) {
        if( _offscreen != null ) {
            _offscreenGr.dispose();
            _offscreen = null;
            _offscreenGr = null;
        }
        super.setBounds( x, y, width, height );
    }

    /**
     * Affect an image to the panel and redraw it.
     * @param image Image to be drawn on the panel.
     */
    public void setImage( Image image ) {
        _img = image;
        _isImgLoaded = false;
        repaint();
    }

    /**
     * Redraw the panel.
     * This method is more efficient than the default behavior.
     * @param g Graphics on which we draw the panel.
     */
    public void update( Graphics g ) {
        if( !_isDoubleBuffered ) {
            paint( g );
            return;
        }

        //Create offscreen
        Dimension dim = this.getSize();
        if( _offscreen == null ) {
            _offscreen = this.createImage( dim.width, dim.height );
            _offscreenGr = _offscreen.getGraphics();
        }

        paint( _offscreenGr );

        g.drawImage( _offscreen, 0, 0, this );
    }

    /**
     * Draw the panel.
     * <p>
     * Subclasses of <code>ImagePanel</code> can draw over the image
     * once it is loaded.
     * @param g Graphics on which we draw the panel.
     */
    public void paint( Graphics g ) {
        super.paint( g );

        // If no _img is specified, nothing more to do.
        if( _img == null )
            return;

        // Print a message while the image is loading.
        if( !prepareImage( _img, this ) ) {
            String str = "Loading image...";
            FontMetrics fm = getFontMetrics( getFont() );
            Dimension dim = getSize();
            int x = ( dim.width - fm.stringWidth( str ) ) / 2;
            int y = ( dim.height - fm.getHeight() ) / 2;
            g.drawString( str, x, y );
            return;
        }

        // Draw the image when loaded.
        _isImgLoaded = true;
        Dimension dim = getSize();
        int imgWidth = _img.getWidth( this );
        int imgHeight = _img.getHeight( this );
        int x = ( dim.width - imgWidth ) / 2;
        int y = ( dim.height - imgHeight ) / 2;
        // Rounding correction
        if( x < 0 )
            x = 0;
        if( y < 0 )
            y = 0;
        g.drawImage( _img, x, y, this );
    }

    /**
     * @return <code>true</code> if the image has been specified and if it is loaded,
     * <code>false</code>, otherwise.
     */
    public boolean isImageLoaded() {
        return( _isImgLoaded );
    }

    /**
     * @return <code>true</code> if the canvas uses an offscreen to draw itself.
     */
    public boolean isDoubleBuffered() {
        return( _isDoubleBuffered );
    }

    /**
     * Set if whether the canvas must draw itself in an offscreen before rendering.
     * <p>
     * By default, <code>ImagePanel</code> doesn't use an offscreen.
     * @param isDoubleBuffered <code>true</code> to use an offscreen.
     */
    public void setDoubleBuffered( boolean isDoubleBuffered ) {
        _isDoubleBuffered = isDoubleBuffered;
        repaint();
    }

    /**
     * Clean up the offscreen when the canvas is destroyed.
     */
    public void destroy() {
        if( _offscreenGr != null )
            _offscreenGr.dispose();
    }

    private boolean     _isDoubleBuffered;
    private boolean     _isImgLoaded;
    private Image       _img;
    private Image       _offscreen;
    private Graphics    _offscreenGr;

}

