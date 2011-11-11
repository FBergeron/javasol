/*
 * Copyright (C) 2011  Frédéric Bergeron (fbergeron@users.sourceforge.net)
 *                     and other contributors
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

import java.util.ArrayList;

public class LegalGameStates {
    private ArrayList<GameState> gameStates = new ArrayList<GameState>();
    int gsIdx=0;

    public LegalGameStates(ArrayList<GameState> gameStates) {
        super();
        this.gameStates = gameStates;
        this.gsIdx=0;
        
    }
    public LegalGameStates(){
        
    }
    public void addGameStates(GameState gameState){
        this.gameStates.add(gameState);
    }
    public ArrayList<GameState> getGameStates() {
        return gameStates;
    }
    public void setGameStates(ArrayList<GameState> gameStates) {
        this.gameStates = gameStates;
    }
    public int getGsIdx() {
        return gsIdx;
    }
    public void setGsIdx(int gsIdx) {
        this.gsIdx = gsIdx;
    }
}
