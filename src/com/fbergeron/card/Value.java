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

/** A value for a classic card.
 * @author Frederic Bergeron
 * @author <a href="http://javasol.sourceforge.net">http://javasol.sourceforge.net</a>
 * @version Version 1.0
 */
public class Value {

	public static final Value V_1 = new Value( 1 );
	public static final Value V_2 = new Value( 2 );
	public static final Value V_3 = new Value( 3 );
	public static final Value V_4 = new Value( 4 );
	public static final Value V_5 = new Value( 5 );
	public static final Value V_6 = new Value( 6 );
	public static final Value V_7 = new Value( 7 );
	public static final Value V_8 = new Value( 8 );
	public static final Value V_9 = new Value( 9 );
	public static final Value V_10 = new Value( 10 );
	public static final Value V_11 = new Value( 11 );
	public static final Value V_12 = new Value( 12 );
	public static final Value V_13 = new Value( 13 );

	public static final Value V_JACK = V_11;
	public static final Value V_QUEEN = V_12;
	public static final Value V_KING = V_13;

	public static final Value V_ACE = V_1;

    public static final String  STRING_ACE     = "A";
    public static final String  STRING_JACK    = "J";
    public static final String  STRING_QUEEN   = "Q";
    public static final String  STRING_KING    = "K";

	public static final Value[] values = {
		V_1, V_2, V_3, V_4, V_5,
		V_6, V_7, V_8, V_9, V_10,
		V_11, V_12, V_13
	};

	private Value( int value ) {
		_value = value;
	}

	public int getValue() {
		return( _value );
	}

	public String toString() {
		if( equals( V_JACK ) )
			return( STRING_JACK );
		else if( equals( V_QUEEN ) )
			return( STRING_QUEEN );
		else if( equals( V_KING ) )
			return( STRING_KING );
		else if( equals( V_ACE ) )
			return( STRING_ACE );
		else
			return( new Integer( _value ).toString() );
	}

	int _value;

}
