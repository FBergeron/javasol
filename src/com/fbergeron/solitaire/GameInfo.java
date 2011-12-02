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
package com.fbergeron.solitaire;

/** Container for game information.
 * @author Frederic Bergeron
 * @author <A HREF="http://javasol.sourceforge.net">http://javasol.sourceforge.net</A>
 * @version Version 1.0
 */
public class GameInfo {

    // Game Types
    public static final String RANDOM = "Random";
    public static final String WINNABLE_EASY = "Winnable-Easy";
    public static final String WINNABLE_NORMAL = "Winnable-Normal";
    public static final String WINNABLE_HARD = "Winnable-Hard";
    public static final String WINNABLE_TRICKY = "Winnable-Tricky";

    public GameInfo() {
    }

    public GameInfo( String type, int seed ) {
        this.type = type;
        this.seed = seed;
    }

    public String getType() {
        return( type );
    }

    public void setType( String type ) {
        this.type = type;
    }

    public int getSeed() {
        return( seed );
    }

    public void setSeed( int seed ) {
        this.seed = seed;
    }

    public boolean equals( Object obj ) {
        if( obj == null || !( obj instanceof GameInfo ) )
            return( false );
        GameInfo gi = (GameInfo)obj;
        return( type.equals( gi.getType() ) && seed == gi.getSeed() );
    }

    public int hashCode() {
        return( ( type + "|" + seed ).hashCode() );
    }

    public String toString() {
        return( type + "|" + seed );
    }

    private String type = RANDOM;
    private int seed = -1;

}
