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

/** A deck of cards.
 * @author Frederic Bergeron
 * @author <A HREF="http://javasol.sourceforge.net">http://javasol.sourceforge.net</A>
 * @version Version 1.0
 */
public class ClassicDeck extends Deck {
    /**
     * Creates a stack of 52 cards.  No jokers yet!
     */
	public ClassicDeck(){
		
	}
    public ClassicDeck( ImageObserver imgObserver ) {
    	_imgObserver = imgObserver;
        buildCards();
    }

    protected void buildCards() {
        for( int suit = 0; suit < Suit.suits.length; suit++ ) {
            for ( int value = 0; value < Value.values.length; value++ ) {
                ClassicCard c = new ClassicCard( Value.values[ value ], Suit.suits[ suit ] );
                if( _imgObserver != null )
                	c.setImageObserver( _imgObserver );
                push( c );

            }
        }
    }

    protected ImageObserver _imgObserver;
}
