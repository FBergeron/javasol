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

package com.fbergeron.solitaire;

import java.awt.*;

import com.fbergeron.card.*;

/** Sequential stack of cards.
 * @author Frederic Bergeron
 * @author <A HREF="http://javasol.sourceforge.net">http://javasol.sourceforge.net</A>
 * @version Version 1.0
 */
class SequentialStack extends Stack {
    /**
     * Allows to push card on the stack only if it obeys the rules of solitaire game
     * for a sequential stack.
     * @return <CODE>true</CODE>, if it's allowed.
     * <CODE>false</CODE> otherwise.
     */
    public boolean isValid( ClassicCard c ) {
        if( isEmpty() )
            return( c.getValue() == Value.V_ACE );
        else
            return( c.getSuit() == ((ClassicCard)top()).getSuit() &&
            	c.getValue().getValue() == ((ClassicCard)top()).getValue().getValue() + 1 );
    }

    /**
     * Allows to push a stack of 1 valid card according to Solitaire rules.
     * @return <CODE>true</CODE>, if it's allowed.
     * <CODE>false</CODE> otherwise.
     */
    public boolean isValid( Stack s ) {
        return( s.cardCount() == 1 && isValid( ((ClassicCard)s.top()) ) );
    }
}

