/*
 * Copyright (C) 2002-2011  Frédéric Bergeron (fbergeron@users.sourceforge.net)
 *                          and other contributors
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
import java.awt.event.*;
import java.io.*;
import java.util.*;

import com.sixlegs.image.png.PngImage;

public class Util {

    /** 
     * Gets an image resource. 
     * @param strResourceFilename Name of the image file.
     * @param srcClass Class from which the location of the resource is determined.
     * @return Instance of an <code>Image</code>.
     */
    public static Image getImageResourceFile( String strResourceFilename, Class srcClass ) {
        PngImage pngImage = null;
        Image image = null;
        try {
            BufferedInputStream in = new BufferedInputStream(
                srcClass.getResourceAsStream( strResourceFilename ) );
            if( in == null ) {
                System.err.println( "Image not found:" + strResourceFilename );
                return null;
            }
            if( strResourceFilename.endsWith( ".png" ) ) {
                pngImage = new PngImage( in );
                image = Toolkit.getDefaultToolkit().createImage( pngImage );
            }
            else { 
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                copy( in, out );
                image = Toolkit.getDefaultToolkit().createImage( out.toByteArray() );
            }
        }
        catch( java.io.IOException e ) {
            System.err.println( "Unable to read image " + strResourceFilename + "." );
            e.printStackTrace();
        }
        return( image );
    }
    
    /** 
     * Load images (used for preloading images).
     * @param images Array of <code>Image</code> instances to preload.
     * @param comp Component that will observe the loading state of the images.
     */
    public static void loadImages( Image[] images, Component comp ) {
        MediaTracker tracker = new MediaTracker( comp );
        for( int i = 0; i < images.length; i++ )
            tracker.addImage( images[ i ], 0 );
        try {
            tracker.waitForID( 0 );
        }
        catch( InterruptedException ignore ) {
        }
    }
    
    /** 
     * Copy data from an input stream to an output stream. 
     * @param in Input stream.
     * @param out Output stream.
     * @throws IOException 
     */
    public static void copy( InputStream in, OutputStream out ) throws IOException
    {
        // Do not allow other threads to read from the
        // input or write to the output while copying is
        // taking place
        synchronized( in ) {
            synchronized( out ) {
                byte[] buffer = new byte[ 1024 ];
                while( true ) {
                    int bytesRead = in.read( buffer );
                    if( bytesRead == -1 )
                        break;
                    out.write( buffer, 0, bytesRead );
                }
            }
        }
    }
}
