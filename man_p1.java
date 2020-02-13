import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class man_p1 
{
	private String symbols, startState;
	private ArrayList<String[]> transitionStrings;
	private ArrayList<String> acceptStates;
	private ArrayList<String> result;
	
	public man_p1(String inputFile, String input)
	{
		symbols = input;
		transitionStrings = new ArrayList<String[]>();
		acceptStates = new ArrayList<String>();
		result = new ArrayList<String>();
/*	
//--------------------TEST
		symbols = "000";
		startState = "1";
		acceptStates.add("7");
		String[] a = {"1", "0", "1"};
		String[] b = {"1", "1", "1"};
		String[] c = {"1", "0", "2"};
		String[] d = {"2", "0", "2"};
		String[] e = {"2", "0", "1"};
		String[] f = {"2", "1", "1"};
		String[] g = {"2", "0", "7"};
		String[] h = {"7", "0", "7"};
		String[] i = {"7", "1", "7"};
		transitionStrings.add(a);
		transitionStrings.add(b);
		transitionStrings.add(c);
		transitionStrings.add(d);
		transitionStrings.add(e);
		transitionStrings.add(f);
		transitionStrings.add(g);
		transitionStrings.add(h);
		transitionStrings.add(i);
//--------------------TEST
*/		
		setupFileInfo(inputFile);
		
		move(startState, symbols.charAt(0), 0);
		printResult();
	}
	
	private void setupFileInfo(String inputFile)
	{
		try
		{
			Scanner lineScanner = new Scanner(new File(inputFile));
			//lineScanner.useDelimiter("	|\\r\\n");
			String line = new String();
			String token = new String();
			
			for(; lineScanner.hasNextLine(); )
			{
				line = lineScanner.nextLine();
				Scanner tokenScanner = new Scanner(line);
				
				//tab delimited;
				tokenScanner.useDelimiter("	");
				for(; tokenScanner.hasNext(); )
				{
					token = tokenScanner.next();
					if(token.equals("state"))
					{
						String tempState = tokenScanner.next();
						if(line.contains("accept"))
							acceptStates.add(tempState);
						if(line.contains("start"))
							startState = tempState;
						break;
					}
					else if(token.equals("transition"))
					{
						String[] transitions = new String[3];
						transitions[0] = tokenScanner.next();
						transitions[1] = tokenScanner.next();
						transitions[2] = tokenScanner.next();
						transitionStrings.add(transitions);
					}
				}
				tokenScanner.close();
			}
			lineScanner.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found.\nAborting program.");
			System.exit(1);
		}
	}

	private void move(String state, char symbol, int symbolsIndex)
	{
		//get all end states when at last char in string;
		if(symbolsIndex >= symbols.length() - 1)
		{
			for(int i = 0; i < transitionStrings.size(); i++)
				if(transitionStrings.get(i)[0].equals(state) && transitionStrings.get(i)[1].indexOf(symbol) != -1)
					updateResult(transitionStrings.get(i)[2]);
			return;
		}
		//loop through all transitions;
		for(int i = 0; i < transitionStrings.size(); i++)
		{
			if(transitionStrings.get(i)[0].equals(state) && transitionStrings.get(i)[1].indexOf(symbol) != -1)
			{
				symbolsIndex++;
				move(transitionStrings.get(i)[2], symbols.charAt(symbolsIndex), symbolsIndex);
				//subtract 1 from the index to ensure the recursion steps through all possible transitionStrings;
				symbolsIndex--;
			}
		}
	}

	private void updateResult(String state)
	{
		//avoid duplicates;
		if(!result.contains(state))
			result.add(state);
	}
	
	private void printResult()
	{
		boolean foundAcceptState = false;
		for(int i = 0; i < result.size(); i++)
		{
			//if accept state found in results;
			if(acceptStates.contains(result.get(i)))
			{
				foundAcceptState = true;
				break;
			}
		}
		
		if(foundAcceptState)
		{
			System.out.print("accept ");
			for(int i = 0; i < result.size(); i++)
				if(acceptStates.contains(result.get(i)))
					System.out.print(result.get(i) + " ");
		}
		else
		{
			System.out.print("reject ");
			for(int i = 0; i < result.size(); i++)
				System.out.print(result.get(i) + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) 
	{
//		man_p1 start = new man_p1("sample_1.txt", "000");
	
		if(args.length == 2) 
		{
			man_p1 start = new man_p1(args[0], args[1]);
		}
		else
		{
			System.out.println("proper usage is: java man_p1 filename.txt testinput");
		}

	}
}