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
import java.awt.event.*;
import java.applet.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import com.fbergeron.card.*;
import com.fbergeron.util.*;

/** Java version of the famous card game.
 * @author Frederic Bergeron
 * @author <A HREF="http://javasol.sourceforge.net">http://javasol.sourceforge.net</A>
 * @version Version 1.0
 */
public class Solitaire extends Frame
{
    /** Number of sequential stacks. */
    public static final int SEQ_STACK_CNT = 4;

    /** Number of solitaire stacks. */
    public static final int SOL_STACK_CNT = 7;

    /** Number of cards freed from the deck when requested. */
    public static final int FREED_CARDS_CNT = 3;

    public static final Point DECK_POS              = new Point( 5, 5 );
    public static final Point REVEALED_CARDS_POS    = new Point( DECK_POS.x + ClassicCard.DEFAULT_WIDTH + 5, 5 );
    public static final Point SEQ_STACK_POS         = new Point( REVEALED_CARDS_POS.x + ClassicCard.DEFAULT_WIDTH + 92, DECK_POS.y );
    public static final Point SOL_STACK_POS         = new Point( DECK_POS.x, SEQ_STACK_POS.y + ClassicCard.DEFAULT_HEIGHT + 5 );

    public static final Color TABLE_COLOR           = new Color( 0, 150, 0 );

    public Solitaire( boolean isApplet )
    {
        super();
        setLayout( new BorderLayout( 0, 0 ) );
        setResizable( false );

        class LocaleListener implements ItemListener {
            public LocaleListener( Locale locale ) {
                this.locale = locale;
            }

            public void itemStateChanged( ItemEvent e ) {
                Solitaire.this.setLocale( locale );
            }

            Locale locale;
        }

        class LicenseListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String title = resBundle.getString( "License" );
                String msg = resBundle.getString( "LicenseText" );
                DialogMsg licenseWindow = new DialogMsg( Solitaire.this, title, true, msg );
                licenseWindow.setLocation( 20, 20 );
                licenseWindow.setSize( 500, 300 );
                licenseWindow.setFont( new Font( "Arial", Font.PLAIN, 14 ) );
                licenseWindow.setResizable( true );
                licenseWindow.setVisible( true );
            }
        }

        //Menus
        menubar = new MenuBar();
        setMenuBar( menubar );

        //Menu Options
        menuOptions = new Menu( "Options" );
        menubar.add( menuOptions );
        menuItemNewGame = new MenuItem( "NewGame");
        menuItemNewGame.addActionListener( new NewGameListener() );
        menuOptions.add( menuItemNewGame );

        menuItemRestart = new MenuItem( "Restart");
        menuItemRestart.addActionListener( new RestartListener() );
        menuOptions.add( menuItemRestart );

        menuItemUndo = new MenuItem( "Undo");
        menuItemUndo.addActionListener( new UndoListener() );
        menuOptions.add( menuItemUndo );

        //Menu Help
        menuHelp = new Menu( "Help" );
        menubar.add( menuHelp );
        menuItemRules = new MenuItem( "Rules" );
        menuItemRules.addActionListener( new RulesListener() );
        menuItemAbout = new MenuItem( "About" );
        menuItemAbout.addActionListener( new AboutListener() );
        menuItemLicense = new MenuItem();
        menuItemLicense.addActionListener( new LicenseListener() );
        menuHelp.add( menuItemRules );
        menuHelp.add( new MenuItem( "-" ) );
        menuHelp.add( menuItemAbout );
        menuHelp.add( menuItemLicense );
        menuHelp.add( new MenuItem( "-" ) );
        menuItemEnglish = new CheckboxMenuItem( "English" );
        menuItemEnglish.addItemListener( new LocaleListener( Locale.ENGLISH ) );
        menuItemFrench = new CheckboxMenuItem( "French" );
        menuItemFrench.addItemListener( new LocaleListener( Locale.FRENCH ) );
        menuHelp.add( menuItemEnglish );
        menuHelp.add( menuItemFrench );

        //String backgroundImageName = "test";
        //backgroundImage = Util.getImageResourceFile( "images/" + backgroundImageName + ".jpg", Solitaire.class );

        //Table
        table = new Table();
        add("Center", table);
        MouseManager mouseManager = new MouseManager();
        table.addMouseListener( mouseManager );
        table.addMouseMotionListener( mouseManager );

        setSize((ClassicCard.DEFAULT_WIDTH + 5) * SOL_STACK_CNT + 10 + getInsets().left + getInsets().right + 3,560);

        addWindowListener(
            new SolitaireWindowManager( this,
                isApplet ? WindowManager.HIDE_ON_CLOSE : WindowManager.EXIT_ON_CLOSE ) );

        newGame();
        setVisible( true );
    }

    /**
     * Shows or hides the component depending on the boolean flag b.
     * @param b  if true, show the component; otherwise, hide the component.
     * @see java.awt.Component#isVisible
     */
    public void setVisible(boolean b)
    {
        Dimension scrSize = getToolkit().getScreenSize();
        Dimension size = getSize();
        if(b)
        {
            setLocation( (scrSize.width - size.width) / 2, (scrSize.height - size.height) / 2 );
        }
        super.setVisible(b);
    }

    /**
     * Sets the locale.
     * @param locale Locale.
     */
    public void setLocale( Locale locale ) {
        super.setLocale( locale );
        
        resBundle = ResourceBundle.getBundle( Solitaire.class.getName() + "Ress", locale );
        
        menuOptions.setLabel( resBundle.getString( "Options" ) );
        menuItemNewGame.setLabel( resBundle.getString( "NewGame" ) );
        menuHelp.setLabel( resBundle.getString( "Help" ) );
        menuItemRules.setLabel( resBundle.getString( "Rules" ) );
        menuItemAbout.setLabel( resBundle.getString( "About" ) );
        menuItemLicense.setLabel( resBundle.getString( "License" ) );
        menuItemEnglish.setLabel( resBundle.getString( "English" ) );
        menuItemFrench.setLabel( resBundle.getString( "French" ) );

        menuItemEnglish.setState( Locale.ENGLISH.equals( locale ) );
        menuItemFrench.setState( Locale.FRENCH.equals( locale ) );

        setTitle( resBundle.getString( "Solitaire" ) );

        if( frameAbout != null )
            frameAbout.setLocale( locale );
        if( frameRules != null )
            frameRules.setLocale( locale );
    }
    
    public static void main( String[] args ) {
        Locale loc = null;
        if( args.length == 0 )
            loc = Locale.ENGLISH;
        else if( args.length == 1 )
            loc = args[ 0 ].equals( "fr" ) ? Locale.FRENCH : Locale.ENGLISH;

        if( loc == null )
            System.out.println( "Usage : java com.fbergeron.solitaire.Solitaire [locale]\n\n" +
                "locale may be fr (for french) or en (for english).\n" );
        else {
            Solitaire sol = new Solitaire( false );
            sol.setLocale( loc );
        }
    }

    /**
     * Starts a new Solitaire game.
     */
    public void newGame() {
        deck = new ClassicDeck( table );
        deck.shuffle();
        deck.setLocation( DECK_POS.x, DECK_POS.y );

        revealedCards = new Stack();
        revealedCards.setLocation( REVEALED_CARDS_POS.x, REVEALED_CARDS_POS.y );

        seqStack = new SequentialStack[ SEQ_STACK_CNT ];
        for ( int i = 0; i < SEQ_STACK_CNT; i++ ) {
            seqStack[ i ] = new SequentialStack();
            seqStack[ i ].setLocation( SEQ_STACK_POS.x + i * (ClassicCard.DEFAULT_WIDTH + 5), SEQ_STACK_POS.y );
        }

        solStack = new SolitaireStack[ SOL_STACK_CNT ];
        for ( int i = 0; i < SOL_STACK_CNT; i++ ) {
            solStack[ i ] = new SolitaireStack();
            solStack[ i ].setSpreadingDirection( Stack.SPREAD_SOUTH );
            solStack[ i ].setSpreadingDelta( 20 );
            solStack[ i ].setLocation( SOL_STACK_POS.x + i * (ClassicCard.DEFAULT_WIDTH + 5), SOL_STACK_POS.y );
        }

        currStack = new Stack();
        currStack.setSpreadingDirection( Stack.SPREAD_SOUTH );
        currStack.setSpreadingDelta( 20 );

        distributeCards();
        if( table != null )
            table.repaint();
    }

    /**
     * Requests cards from the deck.
     * The number of new cards is equal to FREED_CARDS_CNT.
     */
    public void getNewCards() {
        //First, restore the deck if it's empty.
        if( deck.isEmpty() ) {
            for( ; !revealedCards.isEmpty(); ) {
                ClassicCard c = ((ClassicCard)revealedCards.pop());
                c.turnFaceDown();
                deck.push( c );
            }
        }

        for( int i = 0; !deck.isEmpty() && i < FREED_CARDS_CNT; i++ ) {
            ClassicCard c = ((ClassicCard)deck.pop());
            c.turnFaceUp();
            revealedCards.push( c );
        }
        // Save the state of the game after the move
        gameStates.add(new GameState(deck,revealedCards,solStack,seqStack,null,null,null));

        if( table != null )
            table.repaint();
    }

    /**
     * Plays a stack of cards from a stack to another stack.
     * @param curr Current stack of cards to be played.
     * @param src Stack where the card comes from.
     * @param dst Stack where the card is played.
     */
    public void play( Stack curr, Stack src, Stack dst ) {
        if( curr != null )
            curr.reverse();
        if( dst != null && dst.isValid( curr ) ) {
            for( ; !curr.isEmpty(); )
                dst.push( curr.pop() );
            if( !src.isEmpty() && src.top().isFaceDown() ) {
                ClassicCard topCard = ((ClassicCard)src.top());
                topCard.turnFaceUp();
            }
            // Save the state of the game after the move
            gameStates.add(new GameState(deck,revealedCards,solStack,seqStack,null,null,null));

            if( isGameWon() )
                congratulate();
        }
        else {
            for( ; !curr.isEmpty(); )
                src.push( curr.pop() );
        }
        if( table != null )
            table.repaint();
    }

    /** Each time a new game begins, we have to distribute the
     * cards in colums.  The number of columns is equal to SOL_STACK_CNT.
     */
    private void distributeCards() {
        for( int i = 0; i < SOL_STACK_CNT; i++ ) {
            ClassicCard c = ((ClassicCard)deck.pop());
            c.turnFaceUp();
            solStack[ i ].push( c );
            for( int j = i+1; j < SOL_STACK_CNT; j++ )
                solStack[ j ].push( deck.pop() );
        }
        
        // Save the initial game state
        gameStates.add(new GameState(deck,revealedCards,solStack,seqStack,null,null,null));

    }

    /**
     * @return <CODE>true</CODE>, if the game is won.
     * <CODE>false</CODE> otherwise.
     */
    private boolean isGameWon() {
        boolean gameWon = deck.isEmpty() && revealedCards.isEmpty();
        if( gameWon )
            for( int i = 0; i < SOL_STACK_CNT && gameWon; i++ )
                gameWon = gameWon && solStack[ i ].isEmpty();
        return( gameWon );
    }

    /**
     * Shows a frame congratulating the player.
     */
    private void congratulate() {
        FrameCongratulations f = new FrameCongratulations();
        f.setVisible( true );
    }

    class AboutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if( frameAbout == null )
                frameAbout = new FrameAbout();
            frameAbout.setLocale( Solitaire.this.getLocale() );
            frameAbout.setVisible( true );
        }
    }

    class NewGameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            newGame();
        }
    }
    class RestartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            gameStates.get(0).restoreGameState(deck, revealedCards, solStack, seqStack);
            table.repaint();
        }
    }
    class UndoListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (gameStates.size()-2 >=0){
                gameStates.get(gameStates.size()-2).restoreGameState(deck, revealedCards, solStack, seqStack);
                gameStates.remove(gameStates.size()-1);
            }

            table.repaint();
        }
    }

    class RulesListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if( frameRules == null )
                frameRules = new FrameRules();
            frameRules.setLocale( Solitaire.this.getLocale() );
            frameRules.setVisible( true );
        }
    }

    class MouseManager extends MouseAdapter implements MouseMotionListener {
        public void mouseMoved(MouseEvent e) {
        }

        public void mouseDragged(MouseEvent e) {
            if( currStack != null && translation != null ) {
                Point p = e.getPoint();
                currStack.setLocation( p.x - translation.x, p.y - translation.y );
                table.repaint();
            }
        }

        public void mousePressed(MouseEvent e) {
            if( !e.isMetaDown() && !e.isControlDown() && !e.isShiftDown() ) {
                ClassicCard c = null;
                Point p = e.getPoint();

                if( deck.contains( p ) )
                    getNewCards();
                else {
                    if( !revealedCards.isEmpty() && revealedCards.top().contains( p ) ) {
                        src = revealedCards;
                        c = ((ClassicCard)src.top());
                    }
                    else {
                        for( int i = 0; i < SOL_STACK_CNT && src == null; i++ ) {
                            if( !solStack[ i ].isEmpty() && solStack[ i ].contains( p ) ) {
                                src = solStack[ i ];
                                c = ((ClassicCard)src.getClickedCard( p ));
                            }
                        }
                        for( int i = 0; i < SEQ_STACK_CNT && src == null; i++ ) {
                            if( !seqStack[ i ].isEmpty() && seqStack[ i ].contains( p ) ) {
                                src = seqStack[ i ];
                                c = ((ClassicCard)src.top());
                            }
                        }
                    }
                    //We don't allow to drag hidden cards
                    if( c != null && c.isFaceDown() ) {
                        src = null;
                        c = null;
                    }
                    if( src != null && c != null ) {
                        Point loc = c.getLocation();
                        translation = new Point( p.x - loc.x, p.y - loc.y );
                        currStack = src.pop( c );
                        currStack.reverse();
                        curr = currStack;
                    }
                }
            }
        }

        public void mouseReleased(MouseEvent e) {
            Point p = e.getPoint();

            for( int i = 0; i < SOL_STACK_CNT && dst == null; i++ ) {
                if( solStack[ i ].contains( p ) )
                    dst = solStack[ i ];
            }
            for( int i = 0; i < SEQ_STACK_CNT && dst == null; i++ ) {
                if( seqStack[ i ].contains( p ) )
                    dst = seqStack[ i ];
            }
            if( curr != null && src != null )
                play( curr, src, dst );
            curr = src = dst = null;
        }

        private   Stack   curr;
        private   Stack   src;
        private   Stack   dst;
        private   Point   translation;
    }

    class Table extends Canvas
    {
        public void update( Graphics g ) {
            paint( g );
        }

        public void paint( Graphics g ) {
            //Create offscreen
            Dimension dim = this.getSize();
            if( offscreen == null ) {
                offscreen = this.createImage( dim.width, dim.height );
                offscreenGr = offscreen.getGraphics();
            }

            //Draw background
            offscreenGr.setColor( TABLE_COLOR );
            offscreenGr.fillRect( 0, 0, dim.width, dim.height );

            //Draw background image
            //offscreenGr.drawImage( backgroundImage, 0, 0, dim.width, dim.height, this );

            //Draw deck
            if( deck != null )
                if( deck.isEmpty() ) {
                    Point loc = deck.getLocation();
                    offscreenGr.setColor( Color.darkGray );
                    offscreenGr.fillRect( loc.x, loc.y, ClassicCard.DEFAULT_WIDTH, ClassicCard.DEFAULT_HEIGHT );
                    offscreenGr.setColor( Color.black );
                    offscreenGr.drawRect( loc.x, loc.y, ClassicCard.DEFAULT_WIDTH, ClassicCard.DEFAULT_HEIGHT );
                }
                else
                    deck.top().paint( offscreenGr );

            //Draw revealedCards
            if( revealedCards != null && !revealedCards.isEmpty() )
                revealedCards.top().paint( offscreenGr );

            //Draw sequential stacks
            if( seqStack != null )
                for( int i = 0; i < Solitaire.SEQ_STACK_CNT; i++ )
                    seqStack[ i ].paint( offscreenGr );

            //Draw solitaire stacks
            if( solStack != null )
                for( int i = 0; i < Solitaire.SOL_STACK_CNT; i++ )
                    solStack[ i ].paint( offscreenGr );

            //Draw current stack
            if( currStack != null && !currStack.isEmpty())
                currStack.paint( offscreenGr );

            g.drawImage( offscreen, 0, 0, this );
        }

        public void destroy() {
            offscreenGr.dispose();
        }

        private Image       offscreen;
        private Graphics    offscreenGr;
    }

    class SolitaireWindowManager extends WindowManager {

        SolitaireWindowManager( Window window, int action ) {
            super( window, action );
        }

        public void windowClosing( WindowEvent e ) {
            if( frameAbout != null ) {
                frameAbout.dispose();
                frameAbout = null;
            }
            if( frameRules != null ) {
                frameRules.dispose();
                frameRules = null;
            }
            super.windowClosing( e );
        }
    }
                
    //protected   Image               backgroundImage;
    protected   Stack               currStack;
    protected   ClassicDeck         deck;
    protected   Stack               revealedCards;
    protected   SolitaireStack[]    solStack;
    protected   SequentialStack[]   seqStack;
    protected   Table               table;
    // Holds the state of the solitaire game after each move
    protected ArrayList<GameState>  gameStates = new ArrayList<GameState>();
    protected ArrayList<GameState>  legalGs = new ArrayList<GameState>();


    static protected ResourceBundle resBundle;

    private MenuBar             menubar;
    private Menu                menuOptions;
    private Menu                menuHelp;
    private MenuItem            menuItemNewGame;
    private MenuItem            menuItemRestart;
    private MenuItem            menuItemUndo;
    private MenuItem            menuItemRules;
    private MenuItem            menuItemAbout;
    private MenuItem            menuItemLicense;
    private CheckboxMenuItem    menuItemEnglish;
    private CheckboxMenuItem    menuItemFrench;

    private FrameAbout          frameAbout;
    private FrameRules          frameRules;
}

