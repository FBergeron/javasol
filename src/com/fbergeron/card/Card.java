/*
 * Copyright (C) 2002  Frédéric Bergeron (fbergeron@users.sourceforge.net)
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

package com.fbergeron.card;

import java.awt.*;

/** An abstract game card.
 * @author Frederic Bergeron
 * @author <A HREF="http://javasol.sourceforge.net">http://javasol.sourceforge.net</A>
 * @version Version 1.0
 */
public abstract class Card {

    public static int DEFAULT_HEIGHT = 129;//123;//124;
    public static int DEFAULT_WIDTH = 86;//80;//82;

    public abstract String toString();
    public abstract void paint( Graphics g );

    /**
     * @return <CODE>true</CODE>, if the card is turned face down, hidden.
     * <CODE>false</CODE> otherwise.
     */
    public boolean isFaceDown() {
        return faceDown;
    }

    /**
     * Shows the card.
     */
    public void turnFaceUp() {
        faceDown = false;
    }

    /**
     * Hides the card.
     */
    public void turnFaceDown() {
        faceDown = true;
    }

    /** 
     * Flips the card.
     */
    public void flip() {
        if( isFaceDown() )
            turnFaceUp();
        else
            turnFaceDown();
    }

    /**
     * @return Location of the card.
     */
    public Point getLocation() {
        return location;
    }

    /**
     * @param p Point corresponding to the new location.
     */
    public void setLocation( Point p ) {
        location = new Point( p.x, p.y );
    }

    /**
     * @param x X-coord of the new location.
     * @param y Y-coord of the new location.
     */
    public void setLocation( int x, int y ) {
        location = new Point( x, y );
    }

    public Dimension getSize() {
        return( size );
    }

    public void setSize( int width, int height ) {
        size = new Dimension( width, height );
    }

    public void setSize( Dimension dim ) {
        size = new Dimension( dim.width, dim.height );
    }

    public boolean contains( Point p ) {
        Rectangle rect = new Rectangle( location.x, location.y, size.width, size.height );
        return( rect.contains( p ) );
    }

    private   boolean       faceDown;
    private   Point         location;
    private   Dimension     size = new Dimension( DEFAULT_WIDTH, DEFAULT_HEIGHT );
}
