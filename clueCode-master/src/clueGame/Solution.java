package clueGame;

public class Solution {
public String person;
public String weapon;
public String room;

public Solution(){
}



public void setSolution(String person, String weapon, String room){
	this.person = person;
	this.weapon = weapon;
	this.room = room;
}
public String getSolutionRoom(){
	return this.room;
}
public String getSolutionWeapon(){
	return this.weapon;
}
public String getSolutionPerson(){
	return this.person;
}
}
