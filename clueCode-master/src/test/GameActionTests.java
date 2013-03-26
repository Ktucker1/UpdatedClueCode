package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;

public class GameActionTests {
	public Board testBoard;
	public Solution testSolution;
	public ClueGame newGame;
	public Player person;
	private static Card peacockCard;
	private static Card plumCard;
	private static Card ballroomCard;
	private static Card studyCard;
	private static Card pipeCard;
	private static Card wrenchCard;
	
	@BeforeClass
	public static void cardSet(){
		peacockCard = new Card("Mrs. Peacock", Card.CardType.PERSON);
		plumCard = new Card("Professor Plum", Card.CardType.PERSON);
		ballroomCard = new Card("Ballroom", Card.CardType.ROOM);
		studyCard = new Card("Study", Card.CardType.ROOM);
		pipeCard = new Card("Pipe", Card.CardType.WEAPON);
		wrenchCard = new Card("Wrench", Card.CardType.PERSON);
	}
	@Before
	public void setUp() {
		testBoard = new Board();
		newGame = new ClueGame();
		testSolution = new Solution();
		testBoard.loadBoardConfigFiles();
		testBoard.calcAdjacencies();
	}
	@Test
	public void checkAccusationTrue(){
		newGame.getSol().setSolution("Mrs. Peacock","Wrench","Lounge");
		testSolution.setSolution("Mrs. Peacock","Wrench","Lounge");
		assertTrue(newGame.checkAccusation(testSolution));
	//checkAccusationFalse1(){
		testSolution.setSolution("Mrs. Peacock","Pipe","Lounge");
		assertFalse(newGame.checkAccusation(testSolution));
	//checkAccusationFalse2(){
		testSolution.setSolution("Mrs. Peacock","Wrench","Ballroom");
		assertFalse(newGame.checkAccusation(testSolution));
	//checkAccusationFalse3(){
		person = new HumanPlayer();
		testSolution.setSolution("Mr. Green","Wrench","Lounge");
		assertFalse(newGame.checkAccusation(testSolution));
	//checkAccusationFalseAll(){
		person = new HumanPlayer();
		testSolution.setSolution("Mr. Green","Pipe","Ballroom");
		assertFalse(newGame.checkAccusation(testSolution));
	}
	
	@Test
	public void disprovingSuggestions(){
		Player player = new Player();
		Random randomNum = new Random();
		player.addCard(peacockCard);
		player.addCard(plumCard);
		player.addCard(pipeCard);
		player.addCard(wrenchCard);
		player.addCard(studyCard);
		player.addCard(ballroomCard);
		//One Card tests
		Assert.assertEquals(player.disproveSuggestion("Mrs. Peacock","Dagger","Library"),peacockCard);
		Assert.assertEquals(player.disproveSuggestion("Mr. Green","Pipe","Library"),pipeCard);
		Assert.assertEquals(player.disproveSuggestion("Mr. Green","Dagger","Study"),studyCard);
		Assert.assertEquals(player.disproveSuggestion("Mr. Green","Dagger","Library"),null);
		//Two possible cards test
		int peacock =0;
		int pipe = 0;
		for (int i = 0; i < 100; i++){
			Card selection = player.disproveSuggestion("Mrs. Peacock","Pipe","Library");
			if(selection == peacockCard){
				peacock++;
			}else if(selection == pipeCard){
				pipe++;
			}else{
				fail("Card returned that does not disprove the selection");
			}
		}
			Assert.assertEquals(100, peacock + pipe);
			assertTrue(peacock > 20);
			assertTrue(pipe > 20);
			//Multiple players test
			ArrayList<ComputerPlayer> comp = new ArrayList<ComputerPlayer>();
			comp.add(new ComputerPlayer());	
			comp.add(new ComputerPlayer());
			Player testPlayer = new HumanPlayer();
			//Give human cards
			testPlayer.addCard(plumCard);
			testPlayer.addCard(studyCard);
			//Give comps cards
			comp.get(0).addCard(peacockCard);
			comp.get(0).addCard(pipeCard);
			comp.get(1).addCard(wrenchCard);
			comp.get(1).addCard(ballroomCard);
			//Cannot disprove
			Assert.assertEquals(testPlayer.disproveSuggestion("Colonel Mustard","Candlestick","Library"), null);
			Assert.assertEquals(comp.get(0).disproveSuggestion("Colonel Mustard","Candlestick","Library"), null);
			Assert.assertEquals(comp.get(1).disproveSuggestion("Colonel Mustard","Candlestick","Library"), null);
			//Human can disprove
			Assert.assertEquals(testPlayer.disproveSuggestion("Professor Plum","Candlestick","Library"), plumCard);
			Assert.assertEquals(comp.get(0).disproveSuggestion("Professor Plum","Candlestick","Library"), null);
			Assert.assertEquals(comp.get(1).disproveSuggestion("Professor Plum","Candlestick","Library"), null);
			pipe = 0;
			int ballroom =0;
			ArrayList<Card> cardList = new ArrayList<Card>();
			//Randomly returns a card between multiple people
			for(int i=0;i < 200; i++){
				Card newCard= testPlayer.disproveSuggestion("Colonel Mustard","Pipe","Ballroom");
				if(newCard != null){
					cardList.add(newCard);
				}
				newCard= comp.get(0).disproveSuggestion("Colonel Mustard","Pipe","Ballroom");
				if(newCard != null){
					cardList.add(newCard);
				}
				newCard= comp.get(1).disproveSuggestion("Colonel Mustard","Pipe","Ballroom");
				if(newCard != null){
					cardList.add(newCard);
				}
				if(cardList.size()> 1){
					int choice=randomNum.nextInt(cardList.size());
					if(cardList.get(choice).getName() == "Pipe"){
						pipe++;
					}else if (cardList.get(choice).getName() == "Ballroom"){
						ballroom++;
					}
				}
			}
			Assert.assertEquals(200, ballroom + pipe);
			Assert.assertTrue(pipe >20);
			Assert.assertTrue(ballroom > 20);
		}
	@Test 
	public void selectTargetLocations(){
		ComputerPlayer player = new ComputerPlayer();
		testBoard.calcTargets(24,7, 2);
		int Loc22_7 =0;
		int Loc23_6 =0;
		System.out.println(testBoard.getTargets());
		//Correct Targets
		for(int i =0; i < 200; i++){
		BoardCell selected = player.pickLocation(testBoard.getTargets());
		if((selected.getCol() == 22) && (selected.getRow() == 7)){  //The test errors because of these statments this is due to the pickLocation returning null becuase its not written.
			Loc22_7++;
		}else if ((selected.getCol() == 23) && (selected.getRow() == 6)){
			Loc23_6++;
		}else{
			fail("Invalid target selection");
		}
		Assert.assertEquals(200, Loc22_7+Loc23_6);
		Assert.assertTrue(Loc22_7 > 10);
		Assert.assertTrue(Loc23_6 > 10);
		}
		//Room Preference test
		testBoard.calcTargets(0,8, 2);
		int room=0;
		player.setLastRoom('O');
		for(int i =0;i<100;i++){
			BoardCell selected = player.pickLocation(testBoard.getTargets());
			if((selected.getCol() == 1) && (selected.getRow() == 9)){
				room++;
			}else{
				fail("Invalid target selected");
			}
		}
		Assert.assertEquals(100,room);
		//room visited test
		player.setLastRoom('S');
		testBoard.calcTargets(0,8, 2);
		room = 0;
		int Loc1_7 = 0;
		int Loc2_8 = 0;
		for(int i =0;i<100;i++){
			BoardCell selected = player.pickLocation(testBoard.getTargets());
			if((selected.getCol() == 1) && (selected.getRow() == 9)){
				room++;
			}else if((selected.getCol() == 1) && (selected.getRow() == 7)){
				Loc1_7++;
			}else if((selected.getCol() == 2) && (selected.getRow() == 8)){
				Loc2_8++;
			}else{
				fail("invalid target Selection ");
			}
		}
		Assert.assertEquals(100,room+Loc1_7+Loc2_8);
		Assert.assertTrue(room > 10);
		Assert.assertTrue(Loc2_8 > 10);
		Assert.assertTrue(Loc1_7 > 10);
	}
	@Test
	public void makeSuggestions(){
		ComputerPlayer comp = new ComputerPlayer();
		testBoard.loadCards();
		for(Card i: newGame.getDeck()){ //Again these tests "Error" our because the fuctions are stubs that return null
			comp.addSeen(i);
		}
		comp.getSeenCards().remove(plumCard);
		comp.getSeenCards().remove(pipeCard);
		comp.getSeenCards().remove(wrenchCard);
		comp.setLocation(testBoard.calcIndex(4,12));
		int plumPipe = 0;
		int plumWrench = 0;
		for(int i=0;i<200;i++){
			Suggestion newSuggest = comp.createSuggestion("Study");
			//Did the suggestion make a correct room?
			Assert.assertEquals(new Card(("Study"), Card.CardType.ROOM),newSuggest.getRoom());
			if (newSuggest.getPerson().equals(plumCard) && newSuggest.getWeapon().equals(pipeCard)){
				plumPipe++;
			}else if (newSuggest.getPerson().equals(plumCard) && newSuggest.getWeapon().equals(wrenchCard)){
				plumWrench++;
			}else{
				fail("Invalid Selection");
			}
		}
		Assert.assertEquals(200, plumPipe+plumWrench);
		Assert.assertTrue(plumPipe > 10);
		Assert.assertTrue(plumWrench > 10);
		//Test if only one suggestion is avalible
		comp.addSeen(wrenchCard);
		comp.setLocation(testBoard.calcIndex(8,8));
		plumPipe = 0;
		for (int i=0;i<100;i++){
			Suggestion newSuggest = comp.createSuggestion("Dining Room");
			Assert.assertEquals(new Card(("Dining Room"), Card.CardType.ROOM),newSuggest.getRoom());
			if (newSuggest.getPerson().equals(plumCard) && newSuggest.getWeapon().equals(pipeCard)){
				plumPipe++;
			}else{
				fail("Invalid selection");
			}
	}
		Assert.assertEquals(100, plumPipe);
	}
}
