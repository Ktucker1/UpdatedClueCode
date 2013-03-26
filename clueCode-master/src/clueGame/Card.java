package clueGame;

public class Card {
	public enum CardType {ROOM,WEAPON,PERSON};
	private String name;
	private CardType cardType;

	
	public Card (String n, CardType ct){
		this.name = n;
		this.cardType = ct;
	}
	
	public CardType getCardType(){
		return cardType;
	}
	public void setCardType(CardType card){
		this.cardType = card;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String Name){
		this.name=Name;
	}
	public boolean equals(Card other){
		if(this.name.equals(other.name)){
			return true;
		}else{
		return false;
	}
}
}
