package project5;

import java.util.ArrayList;
/**
 * This class implement a program that finds all solutions to a number puzzle. The solution is found through a recursive algorithm by finding all the possible solutions to the puzzle
 * The puzzle uses an array of positive integers. The objective is to find a path from index zero to the last index in the array. At each step
 * we need to know the distance to be traveled and the direction. The function will print out the all the possible solutions and indicate how many valid solutions in total. 
 * @author Zerui Ji
 * @version 11/18/2019
 *
 */

public class WayFinder {
	private static int count; // this variable is used to record the number of solutions
	/**
	 * This is the main function which the program will run, 
	 * it checks all possible condition which may crash the program. 
	 * If all conditions are checked, the program will convert the command line argument into a array of integers, call the recursive function 
	 * and display the results
	 * @param args 
	 */
	
	public static void main(String[] args) {
		
		if (args.length == 0) {
			System.err.println("ERROR: incorrect usage. At least one argument is required.");   // checks the command line argument length, if the command line argument is 0, 
			System.exit(1);          															// print out the error message and exit the program
		}
		for (int i =0; i < args.length; i++) { 
			try {																	
			if (Integer.parseInt(args[i]) < 0 || Integer.parseInt(args[i]) > 99) {  
				// use the for loop to check if each value is a valid argument. If not, print out the error message and exit the program			
				System.err.println("ERROR: the puzzle value have to be positive integers in range [0,99]");
				System.exit(1);
			}
			}catch (NumberFormatException e) {
				System.err.println("ERROR: the puzzle value have to be positive integers in range [0,99] ");
				System.exit(1);
			}
		}
		if (Integer.parseInt(args[args.length-1]) != 0) {			// now, we want to check if the last digit of the input command line is 0. If the condition fails, print out the error message and exit the program
			System.err.println("ERROR: the last puzzle value has to be 0");
			System.exit(1);
		}
		// All conditions are now checked
		int [] inputList = new int[args.length];
		for ( int i = 0; i< args.length ; i++) {
			inputList[i] = Integer.parseInt(args[i]);  // read in all the command line argument and convert them into integers. 
		}
		
		puzzleBoard(inputList,0);  // Call the wrapper class function
		
		// display the end results
		if ( count >1) {
			System.out.println("There are " +count + " ways through the puzzle");    // display three different output messages based on the value of count 
		}
		else if (count == 1) {
			System.out.println("There is " + count + " way through the puzzle"); // if there is only one solution 
		}
		else {
			System.out.println("No way through this puzzle"); // if there is no solution
		}
	}
/**
 * This is the wrapper class of the recursive function. It takes an array of integer and index as parameters. 
 * This function reformats the input array, so that it is ready to be called as parameter to the actual recursive function.
 * @param currentList
 * @param index
 * 
 */
	public static void puzzleBoard(int[] currentList , int index) {
	
		ArrayList<String> updated = new ArrayList<String>(); 		// create an empty string 
		for (int i =0 ; i< currentList.length ; i++) {
			if ( currentList[i] > 9) {
				updated.add( Integer.toString(currentList[i])+ " "); 
			}else {
				updated.add(" " + Integer.toString(currentList[i]) +" "); // gives the extra space for value below 9
			}
		}
		puzzleBoardt(currentList, index, updated);  // call the recursive function
	}
/**
 * This is the actual recursive function to find the answer by giving an array of integer, starting index 
 * and an updated ArrayList to record all the answers 
 * The function generate all answers for the given puzzle and avoid possible conditions of infinite loop.   
 * @param currentList
 * @param index
 * @param updated
 */
	public static void puzzleBoardt(int[] currentList, int index, ArrayList<String> updated){

		// This is the base case for the program, once the index reaches the end of array, the function will print out the recorded path
		if ( index == (currentList.length-1)) {
			System.out.println(updated);
			count++;				// counting numbers of solutions
		}else if(index == 0){		// Special conditions of having index at 0
			int next_step = currentList[index];				// next step
			if(next_step > 9) {		// check the digits of input and reformat accordingly
				updated.set(0, next_step+"R");    
			}
			else {
				updated.set(0, " " + next_step+"R");   
			}
			puzzleBoardt(currentList, index+next_step, updated);   // recursive call to move right, since the current index is at the beginning of the array
		}
		else {
			// operations on the right direction
			
			int next_step = currentList[index];			
			ArrayList<String> right = new ArrayList<String>(); 		// create an empty ArrayList and copy each element 
			for (String input: updated) {
				right.add(input);
			}
			// check to see if all the conditions are met, 1. the next step should not step out of the boundary 
			// 2. the next step should not contain either R or L
		
			if((index+next_step < currentList.length)&& (!right.get(index+next_step).contains("L") && (!right.get(index+next_step).contains("R")))) {
				if(next_step>9) {
					right.set(index, next_step+"R");			// record the step and direction taken
				}else {
					right.set(index, " "+next_step+"R");
					}
					puzzleBoardt(currentList, index + next_step,right);			// make the next recursive call to move the index further 
				}
			
			// operations on the left direction 
			ArrayList<String> left = new ArrayList<String>();			// create an empty ArrayList and copy the list 
			for(String input : updated) {
				left.add(input);
			}
			
			// check if all the conditions are met: 1. the next index should not below 0 
			//	2. We have not yet visited the next element more than once(no R or L)
			
			if (index-next_step>=0 && (!left.get(index - next_step).contains("L")) && (!left.get(index - next_step).contains("R"))){
				if(next_step > 9)
					left.set(index, next_step + "L");	// record the operation base on different digits 
				else {
					left.set(index, " " + next_step +"L");
				}
				puzzleBoardt(currentList,index-next_step,left);   // make the next recursive call to move the index to the left. 
			}
		}	
	}

}
	



