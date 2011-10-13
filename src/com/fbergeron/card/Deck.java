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
import java.util.*;

/** A deck of cards.
 * @author Frederic Bergeron
 * @author <A HREF="http://javasol.sourceforge.net">http://javasol.sourceforge.net</A>
 * @version Version 1.0
 */
public abstract class Deck extends Stack {
    /**
     * Shuffles the deck.
     */
    public void shuffle() {
        //We transfer the deck in a vector temporarily
        Vector v = new Vector();
        while( !isEmpty() )
            v.addElement( pop() );

        Random aRandom = new Random();
        
        // set the seed if you want to replay the same game 
        // BTW game 18 is winnable aRandom.setSeed(18);

        //We push randomly selected cards on the empty deck
        while( !v.isEmpty() ) {
            int randomCard=aRandom.nextInt((int)v.size());
            Card c = (Card) v.elementAt( randomCard );
            push( c );
           v.removeElement( c );
        }
    }
}
