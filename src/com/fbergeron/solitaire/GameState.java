package com.fbergeron.solitaire;



import com.fbergeron.card.ClassicCard;
import com.fbergeron.card.ClassicDeck;
import com.fbergeron.card.Stack;


// Holds the game state of a solitaire game

public class GameState {


	//
	// Save the state of the solitaire game
	// This is used to undo moves
	//
	public GameState(	ClassicDeck deck,
						Stack revealedCards,
						SolitaireStack[] solStack,
						SequentialStack[] seqStack,
						Stack src,
						Stack dst,
						Stack curr) {
		super();
		this.deck=new ClassicDeck();
		for (int i = 0; i < deck.cardCount(); i++) {
			ClassicCard currentCard = ((ClassicCard)deck.elementAt(i));
			ClassicCard c=new ClassicCard(currentCard);
			this.deck.push(c);
		}
		this.revealedCards=new Stack();
		for (int i = 0; i < revealedCards.cardCount(); i++) {
			ClassicCard currentCard = ((ClassicCard)revealedCards.elementAt(i));
			ClassicCard c=new ClassicCard(currentCard);
			this.revealedCards.push(c);
		}

		this.solStack=new SolitaireStack[Solitaire.SOL_STACK_CNT];
		for (int i = 0; i < Solitaire.SOL_STACK_CNT; i++) {
			this.solStack[ i ] = new SolitaireStack();
			for (int j = 0; j < solStack[i].cardCount(); j++) {
				ClassicCard currentCard = ((ClassicCard)solStack[i].elementAt(j));
				ClassicCard c=new ClassicCard(currentCard);
				this.solStack[i].push(c);
			}
		}
		this.seqStack=new SequentialStack[Solitaire.SEQ_STACK_CNT];
		for (int i = 0; i < Solitaire.SEQ_STACK_CNT; i++) {
			this.seqStack[ i ] = new SequentialStack();
			for (int j = 0; j < seqStack[i].cardCount(); j++) {
				ClassicCard currentCard = ((ClassicCard)seqStack[i].elementAt(j));
				ClassicCard c=new ClassicCard(currentCard);
				this.seqStack[i].push(c);
			}
		}
		this.src = src;
		this.dst = dst;
		this.curr = curr;
	}
	// Restore the game state to the previous state
	// In the case of undo this would be before the last move
	public void restoreGameState (	ClassicDeck deck,
			Stack revealedCards,
			SolitaireStack[] solStack,
			SequentialStack[] seqStack) {


		for( ; !revealedCards.isEmpty(); ){
			revealedCards.pop();
		}

		for (int i = 0; i < this.revealedCards.cardCount(); i++) {
			ClassicCard currentCard = ((ClassicCard)this.revealedCards.elementAt(i));
			ClassicCard c=new ClassicCard(currentCard);
			revealedCards.push(c);

		}
		for( ; !deck.isEmpty(); ){
			deck.pop();
		}
		for (int i = 0; i < this.deck.cardCount(); i++) {
			ClassicCard currentCard  = ((ClassicCard)this.deck.elementAt(i));
			ClassicCard c=new ClassicCard(currentCard);
			deck.push(c);
		}
		for (int i = 0; i < Solitaire.SEQ_STACK_CNT; i++) {
			for( ; !seqStack[i].isEmpty(); ){
				seqStack[i].pop();
			}
			for (int j = 0; j < this.seqStack[i].cardCount(); j++) {
				ClassicCard currentCard  = ((ClassicCard)this.seqStack[i].elementAt(j));
				ClassicCard c=new ClassicCard(currentCard);
				seqStack[i].push(c);
			}
		}
		for (int i = 0; i < Solitaire.SOL_STACK_CNT; i++) {
			for( ; !solStack[i].isEmpty(); ){
				solStack[i].pop();
			}

			for (int j = 0; j < this.solStack[i].cardCount(); j++) {
				ClassicCard currentCard  = ((ClassicCard)this.solStack[i].elementAt(j));
				ClassicCard c=new ClassicCard(currentCard);
				solStack[i].push(c);
			}
		}

	}
	protected   ClassicDeck         deck;
	protected   Stack               revealedCards;
	protected   SolitaireStack[]    solStack;
	protected   SequentialStack[]   seqStack;

	protected 	Stack src;
	protected 	Stack dst;
	protected 	Stack curr;
}
