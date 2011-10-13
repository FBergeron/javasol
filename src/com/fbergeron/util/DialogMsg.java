/*
 * Copyright (C) 2001  Frédéric Bergeron (fbergeron@users.sourceforge.net)
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

public class DialogMsg extends Dialog
{
    /**
     * Simple dialog that displays a message.
     * 
     * @param parent Parent frame from which the dialog is invoked.
     * @param title Title of the dialog window.
     * @param modal <code>true</code>, if it's a modal dialog.
     * @param strMsg Message displayed in the dialog window.
     */
    public DialogMsg(Frame parent, String title, boolean modal, String strMsg)
    {
        super(parent, modal);

        setLayout(new BorderLayout(0,0));
        setVisible(false);
        setSize(240,110);
        setBackground(new Color(12632256));
        
        panelBackground = new java.awt.Panel();
        panelBackground.setLayout(new BorderLayout(0,0));
        panelBackground.setBackground(Color.white);
        add("Center", panelBackground);
        
        textAreaMsg = new java.awt.TextArea("",0,0,TextArea.SCROLLBARS_VERTICAL_ONLY);
        textAreaMsg.setEditable(false);
        textAreaMsg.setText(strMsg);
        //textAreaMsg.setBackground(new Color(16777199));
        panelBackground.add("Center",textAreaMsg);

        setTitle(title);
        setResizable(false);
        
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((d.width - getSize().width)/2, (d.height - getSize().height)/2);

        addWindowListener( new WindowManager( this, WindowManager.DISPOSE_ON_CLOSE ) );
    }

    Panel panelBackground;
    TextArea textAreaMsg;
}
