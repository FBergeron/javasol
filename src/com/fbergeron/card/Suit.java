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

/** A suit for a classic card.
 * @author Frederic Bergeron
 * @author <a href="http://javasol.sourceforge.net">http://javasol.sourceforge.net</a>
 * @version Version 1.0
 */
public class Suit {

	public static final Suit HEART = new Suit( "H" );
	public static final Suit SPADE = new Suit( "S" );
	public static final Suit DIAMOND = new Suit( "D" );
	public static final Suit CLUB = new Suit( "C" );

	public static final Suit[] suits = { HEART, SPADE, DIAMOND, CLUB };

	private Suit( String toString ) {
		_toString = toString;
	}

	public String toString() {
		return( _toString );
	}

	private String _toString;

}
