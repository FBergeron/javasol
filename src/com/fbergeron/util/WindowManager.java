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
package com.fbergeron.util;

import java.awt.*;
import java.awt.event.*;

public class WindowManager extends WindowAdapter {
    
    public static final int HIDE_ON_CLOSE       = 0;
    public static final int DISPOSE_ON_CLOSE    = 1;
    public static final int EXIT_ON_CLOSE       = 2;
    
    public WindowManager( Window window, int action ) {
        super();
        this.window = window;
        this.action = action;
    }

    public void windowClosing(WindowEvent e) {
        switch( action ) {
            case HIDE_ON_CLOSE : window.setVisible( false ); break;
            case DISPOSE_ON_CLOSE : window.dispose(); break;
            case EXIT_ON_CLOSE : window.dispose(); System.exit( 0 ); break;
        }
    }

    private int     action;
    private Window  window;
}

