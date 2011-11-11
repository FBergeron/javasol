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

package com.fbergeron.card;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.awt.geom.RoundRectangle2D;

import com.fbergeron.util.*;

/** A classic game card.
 * @author Frederic Bergeron
 * @author <A HREF="http://javasol.sourceforge.net">http://javasol.sourceforge.net</A>
 * @version Version 1.0
 */
public class ClassicCard extends Card {

    public static final String  STRING_HIDDEN  = "X";
    
    public static final int     BORDER_ARC = 20;
    public static final Color   CARD_COLOR = Color.blue;

    /**
     * Creates a card.
     *
     * @param value The value of the card.
     * @param suit  The suit of the card.
     */
    public ClassicCard( ClassicCard card ) {
        super();
        this.img = card.img;
        this.imgName = card.imgName;
        this.imgObserver = card.imgObserver;
        this.suit = card.suit;
        this.value = card.value;
//      this.legal=true;
        this.setLocation( card.getLocation() );
        this.setSize( card.getSize() );
        if( card.isFaceDown() )
            this.turnFaceDown();
        else
            this.turnFaceUp();
    }

    public ClassicCard( Value value, Suit suit ) {
        super();
        this.suit = suit;
        this.value = value;
        StringBuffer tmpImgName = new StringBuffer( suit.toString() );
        tmpImgName.append( "/" ).append( value.toString() );
        this.imgName = tmpImgName.toString();
        this.legal = false;
        turnFaceDown();
    }

    public boolean isLegal() {
        return legal;
    }

    public void setLegal( boolean legal ) {
        this.legal = legal;
    }

    public void setImageObserver( ImageObserver imgObserver ) {
        this.imgObserver = imgObserver;
    }

    /**
     * @return Color of the card.
     * May be either <CODE>Color.red</CODE> or <CODE>Color.black</CODE>.
     */
    public Color getColor() {
        return ( suit == Suit.SPADE || suit == Suit.CLUB ) ? Color.black : Color.red;
    }

    /**
     * @return Value of the card.  May be from 1 to 13.
     */
    public Value getValue() {
        return value;
    }

    /**
     * @return Suit of the card.  May be one of these values
     * <CODE>HEART</CODE>, <CODE>SPADE</CODE>, <CODE>DIAMOND</CODE> or <CODE>CLUB</CODE>,
     */
    public Suit getSuit() {
        return suit;
    }

    public boolean equals(Object obj) {
        return
            isFaceDown() == ( (ClassicCard)obj ).isFaceDown() &&
            suit == ( (ClassicCard)obj ).suit &&
            value == ( (ClassicCard)obj ).value;
    }

    public String toString() {
        StringBuffer strBufTemp = new StringBuffer();
        if( isFaceDown() )
            strBufTemp.append( STRING_HIDDEN );
        strBufTemp.append( value.toString() );
        strBufTemp.append( suit.toString() );
        if( isFaceDown() )
            strBufTemp.append( STRING_HIDDEN );
        return strBufTemp.toString();
    }

    public void paint( Graphics g, boolean hint ) {
        Point location = getLocation();

        //Background
        RoundRectangle2D border = new RoundRectangle2D.Double(
            location.x, location.y, getSize().width - 1, getSize().height - 1, BORDER_ARC, BORDER_ARC);

        g.setClip(border); // Don't draw outside the lines

        if( isFaceDown() ) {
            g.setColor( CARD_COLOR );
            g.fillRect( location.x, location.y, getSize().width - 1, getSize().height - 1 );
        }
        else {
            g.setColor( Color.white );
            g.fillRect( location.x, location.y, getSize().width - 1, getSize().height - 1 );

            Image img = (Image)images.get( imgName );
            if( img != null && imgObserver != null )
                g.drawImage( img, location.x + 3, location.y + 3, imgObserver );
            if( hint ) {
                if( this.legal ) {
                    img = (Image)images.get( "Legal" );
                    if( img != null && imgObserver != null )
                        g.drawImage( img, location.x + 3, location.y + 3, imgObserver ); 
                }
            }
        }

        g.setClip(null); // OK, you can draw anywhere again

        // Frame
        g.setColor( Color.black );
        g.drawRoundRect(location.x, location.y, getSize().width - 1, getSize().height - 1, BORDER_ARC, BORDER_ARC);
    }

    static private Hashtable    images = new Hashtable();
    static private MediaTracker tracker = new MediaTracker( new Button() );

    //Preloading of the card images.
    static {
        for( int i = 0; i < Suit.suits.length; i++ ) {
            for( int j = 0; j < Value.values.length; j++ ) {
                StringBuffer imgFilename = new StringBuffer( Suit.suits[ i ].toString() );
                imgFilename.append( "/" ).append( Value.values[ j ].toString() );
                String imgName = imgFilename.toString();
                imgFilename.append( ".png" );
                Image img = Util.getImageResourceFile( imgFilename.toString(), ClassicCard.class );
                tracker.addImage( img, 0 );
                images.put( imgName, img );
            }
        }
        String imgName = "Legal";
        Image img = Util.getImageResourceFile( imgName + ".png", ClassicCard.class );
        tracker.addImage( img, 0 );
        images.put( imgName, img );
        try {
            tracker.waitForID( 0 );
        }
        catch( InterruptedException e ) {
            // Ignore the interruption.
        }
    }
    
    private Suit            suit;
    private Value           value;
    private String          imgName;
    private boolean         legal;

    private ImageObserver   imgObserver;
    private Image           img;

}
