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

import java.awt.*;
import java.util.*;

import com.fbergeron.util.*;

public class FrameRules extends Frame
{
    public FrameRules()
    {
        setLayout(new BorderLayout(0,0));
        setSize(500,600);
        setVisible(false);
        textAreaHelp.setEditable(false);
        add("Center", textAreaHelp);
        textAreaHelp.setBounds(0,0,500,600);
        
        setTitle( "Rules" );
        setLocation( 50, 50 );
        textAreaHelp.setFont( new Font( "Courier", Font.PLAIN, 14 ) );
        textAreaHelp.append( "Rules01" );
        
        addWindowListener( new WindowManager( this, WindowManager.HIDE_ON_CLOSE ) );
    }

    java.awt.TextArea textAreaHelp = new java.awt.TextArea("",0,0,TextArea.SCROLLBARS_VERTICAL_ONLY);

    /** 
     * Sets the locale of the Frame.
     * @param locale Locale of the frame.
     */
    public void setLocale( Locale locale ) {
        super.setLocale( locale );

        _resBundle = ResourceBundle.getBundle( getClass().getName() + "Ress", locale ); 
        
        textAreaHelp.setText( _resBundle.getString( "Rules01" ) );
        setTitle( _resBundle.getString( "Rules" ) );
    }
    
    private ResourceBundle _resBundle;
}
