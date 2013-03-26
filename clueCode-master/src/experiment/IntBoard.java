package experiment;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public class IntBoard {
	private Map<Integer, LinkedList<Integer>> adjacencyMatrix;
	private boolean[] visited = new boolean[16];
	private static final int sideLength = 4;
	private HashSet<Integer> targets;
	//public LinkedList<Integer> test = new LinkedList<Integer>();
	
	
	public IntBoard()
	{
		for(int i=0;i<16;i++)
			visited[i]=false;
		adjacencyMatrix = new HashMap<Integer, LinkedList<Integer>>();
		calcAdjacencies();
		targets = new HashSet<Integer>();
	}
	
	public void calcAdjacencies() {
		for (int i=0;i<16;i++) {
			adjacencyMatrix.put(i, getAdjList(i));
		}
	}
	
	public void startTargets(int location, int steps) 
	{
		LinkedList<Integer> tempCopy = adjacencyMatrix.get(location);
		LinkedList<Integer> currAdj = new LinkedList<Integer>();
		//System.out.println(tempCopy.size());
		//System.out.println(adjacencyMatrix.size());
		//System.out.println("NEW REC" + steps);
		for (int i=0;i<adjacencyMatrix.get(location).size();i++)
		{
			if (visited[tempCopy.get(i)]==false) {
				currAdj.add(tempCopy.get(i));
				//System.out.println(tempCopy.get(i));
			}
		}
		visited[location] = true;
		for (int i=0; i<currAdj.size();i++)
		{
			visited[currAdj.get(i)]=true;
			if (steps==1) {
				//System.out.println("ADD TO TARGETS" + currAdj.get(i));
				targets.add(currAdj.get(i));
			} else {
				//System.out.println("BEFORE REC" + steps);
				startTargets(currAdj.get(i),steps-1);
			}
			visited[currAdj.get(i)]=false;
		}
		visited[location] = false;
	}
	
	public HashSet<Integer> getTargets() {
		return targets;
	}
	
	public LinkedList<Integer> getAdjList(int Location) {
		LinkedList<Integer> adjList = new LinkedList<Integer>();
		int row, column;
		row = Location / sideLength;
		column = Location % sideLength;
//		System.out.println(Location);
//		System.out.println(row);
//		System.out.println(column);
//		System.out.println();
		if (row != 0) {
			adjList.push(calcIndex(row - 1, column));
		}
		if (row != (sideLength - 1)) {
			adjList.push(calcIndex(row + 1, column));
		}
		if (column != 0) {
			adjList.push(calcIndex(row, column - 1));
		}
		if (column != (sideLength - 1)) {
			adjList.push(calcIndex(row, column + 1));
		}
		return adjList;
	}
	
	public int calcIndex(int row, int column) {
		return row*sideLength + column;
	}
	
	
	
}
