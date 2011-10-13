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
    public ClassicCard (ClassicCard card){
    	super();
    	this._img=card._img;
    	this._imgName=card._imgName;
    	this._imgObserver=card._imgObserver;
    	this._suit=card._suit;
    	this._value=card._value;
    	this.setLocation(card.getLocation());
    	this.setSize(card.getSize());
    	if (card.isFaceDown()){
    		this.turnFaceDown();
    	}else
    	{
    		this.turnFaceUp();
    	}

    	
    }
    public ClassicCard( Value value, Suit suit ) {
        super();
        _suit = suit;
        _value = value;
        StringBuffer imgName = new StringBuffer( _suit.toString() );
        imgName.append( "/" ).append( _value.toString() );
        _imgName = imgName.toString();
        turnFaceDown();
    }

    public void setImageObserver( ImageObserver imgObserver ) {
        _imgObserver = imgObserver;
    }

    /**
     * @return Color of the card.
     * May be either <CODE>Color.red</CODE> or <CODE>Color.black</CODE>.
     */
    public Color getColor() {
        return ( _suit == Suit.SPADE || _suit == Suit.CLUB ) ? Color.black : Color.red;
    }

    /**
     * @return Value of the card.  May be from 1 to 13.
     */
    public Value getValue() {
        return _value;
    }

    /**
     * @return Suit of the card.  May be one of these values
     * <CODE>HEART</CODE>, <CODE>SPADE</CODE>, <CODE>DIAMOND</CODE> or <CODE>CLUB</CODE>,
     */
    public Suit getSuit() {
        return _suit;
    }

    public boolean equals(Object obj) {
        return
            isFaceDown() == ( (ClassicCard)obj ).isFaceDown() &&
            _suit == ( (ClassicCard)obj )._suit &&
            _value == ( (ClassicCard)obj )._value;
    }

    public String toString() {
        StringBuffer strBufTemp = new StringBuffer();
        if( isFaceDown() )
            strBufTemp.append( STRING_HIDDEN );
        strBufTemp.append( _value.toString() );
        strBufTemp.append( _suit.toString() );
        if( isFaceDown() )
            strBufTemp.append( STRING_HIDDEN );
        return strBufTemp.toString();
    }

    public void paint( Graphics g ) {
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

            Image img = (Image)images.get( _imgName );
            if( img != null && _imgObserver != null )
                g.drawImage( img, location.x + 3, location.y + 3, _imgObserver );
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
        try {
            tracker.waitForID( 0 );
        }
        catch( InterruptedException e ) {
            // Ignore the interruption.
        }
    }

    private Suit            _suit;
    private Value           _value;
    private String          _imgName;

    private ImageObserver   _imgObserver;
    private Image           _img;
}
