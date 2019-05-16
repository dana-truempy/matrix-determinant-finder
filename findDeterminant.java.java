import java.io.*;
import java.util.Scanner;

/** 
 * This class contains the three methods for the program: main, det, and minor.
 * It takes an input file containing a number of matrices and uses a recursive 
 * function to find their determinants. The minor method is used within the determinant
 * calculation to find each minor matrix. 
 * The program creates an output file repeating the input matrices and sizes and
 * specifying each of their determinants. 
 * 
 * @author danat
 *
 */
public class findDeterminant {

	/**
	 * The main method controls I/O.
	 * Takes command line args for input and output paths and parses input.
	 * Input is then passed into det() method.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String fileIn = args[0];
	    File f = new File( fileIn );
	    
	    if( !f.exists() ){ //Incorrect path or nonexistent file will exit program.
	      System.out.println( "File does not exist. Terminating program." );
	      System.exit( 1 );
	    }
	    
	    else {
	      System.out.println( "Reading file." );
	    }	
	    
	    String fileOut = args[1];
	    File out = new File(fileOut);
	    FileWriter wout = new FileWriter(out);
	    
	    Scanner input = new Scanner( f );
	    
	    while(input.hasNextLine()) {
    		int size = 0;
    		
	    	try { 
	    	String line = input.nextLine();
	    	size = Integer.parseInt(line); 
	    		//tries to parse matrix size input, throws exception for multiple numbers on one line
    		}
    		catch(NumberFormatException e){
    			System.out.println("Invalid entry (multiple values for matrix dimensions). Please check file and try again.");
    			System.exit( 1 ); 
    				//this exception actually closes the file vs. the next one which just makes a note in the output
    		}
    		 int[][] m = new int[size][size]; //core of the problem, 2D matrix
    		 
    		 if(size == 0) {
    			 wout.write("Invalid input for matrix size (0). Please check file and make correction.\n\n");
    			 //this just prints a note on this matrix to tell the user this input is invalid
    		 }
    		 for( int i = 0; i < size; i++ ) {
 				String[] nextLine = input.nextLine().split(" "); //split String input
 				for( int j = 0; j < size; j++ ) {
 					m[i][j] = Integer.parseInt(nextLine[j]); //parse String as int to perform calculations
 				}
    		 }
    		 
    		 wout.write("Matrix: \n\n"); //double newline/carriage return is for windows that requires it
    		 
    		 for(int i = 0; i < size; i++) { //prints the matrix to the output file
    			 for(int j = 0; j < size; j++) {
    				 wout.write(m[i][j] + " ");
    			 } 
    			 wout.write("\n\n"); //adds newline every n elements to recreate matrix shape
    		 }
    		 wout.write("Size: " + size + "\n\n");
    		 wout.write("Determinant: ");
    		 wout.write(det(m, size) + " "); //prints determinant of each matrix
    		 wout.write("\n\n\n\n");
	    }
	    input.close(); //Close out scanner and writer
	    wout.close();
	}
	
	/**
	 * This is the core recursive function which calculates the determinant.
	 * It takes the input matrix and its size and calls itself repeatedly.
	 * The algorithm itself is just a Java-ized version of the one given in the assignment.
	 * @param m
	 * @param size
	 * @return int determinant value
	 */
	public static int det(int[][] m, int size) {
		int d = 0;
		if(m.length == 1 ) {
			return m[0][0];
		}
		
		else {
			for( int i = 0; i < size; i++ ) {
				d += (int)(Math.pow(-1, i)) * m[0][i] * det(minor(m, 0, i), size-1 );
			}
			return d;
		}
	}
	
	/**
	 * The minor method takes an input matrix and finds the minor matrix of an element.
	 * It takes the row and column indices of the input element and creates a new
	 * matrix with only those elements in neither that row nor that column.
	 * The minor method is eventually called for every element in the first row
	 * of each input matrix. 
	 * @param m
	 * @param row
	 * @param col
	 * @return int[][] minor matrix of a given element
	 */
	public static int[][] minor( int[][] m, int row, int col) {
		int size = m.length; //size of larger input matrix
		int[][] min = new int[size-1][size-1]; //dimensions of minor matrix (m-1)
		int minorRow = 0;
		for( int i = 0; i < size; i++) {
			int minorCol = 0;
			if ( i != row ) {
				for( int j = 0; j < size; j++) {
					if( j != col ) { 
						//if the row and column values are not the same as the given element, 
						//the element is added to the minor matrix 
						min[minorRow][minorCol] = m[i][j];
						minorCol++;
					}
				}
				minorRow++;
			}
		}
		return min;
	}
}
