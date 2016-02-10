import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;





public class tm {
	
	tm(){}
	
	public class Rule {
		public int state;
		public String read;
		public int endState;
		public String write;
		public int move; //Either -1 for left or 1 for right
		
		
		 Rule(){}
		 
		 Rule(int state, String read, int endState, String write, int move)
		 {
			 this.state = state;
			 this.read = read;
			 this.endState = endState;
			 this.write = write;
			 this.move = move;
		 }
	}
	
	public String write(String tape, int tapeHead, Rule tempRule)
	{
		//Write slightly differently depending on if tapeHead is at either end of the String
		String tempTape = tape;
		if(tapeHead == 0)
			tempTape = tempRule.write + tape.substring(1);
			
		else if (tapeHead == tape.length() - 1)
			tempTape = tape.substring(0, tape.length() - 1) + tempRule.write;
			
		else
			tempTape = tape.substring(0, tapeHead) + tempRule.write + tape.substring(tapeHead + 1);
		
		return tempTape;
	}
	
	public void tmSim(List<Rule> rList, String tape, int maxSteps)
	{
		if(rList.isEmpty())
		{
			System.out.println("No rules, who knows?");
			return;
		}
		
		if(tape.isEmpty())
		{
			System.out.println(" : NO");
			return;
		}
			
		
		tm TM = new tm();
		Rule tempRule = TM.new Rule();
		String input = tape;
		int currentState = 0, tapeHead = 0;
		int step = 1, i, rListSize = rList.size();
		
		boolean halt = false, foundRule = false;
		
		while(halt == false && step <= maxSteps)
		{
			halt = true;
			
			//Begin looking for applicable rule
			i = 0;
			foundRule = false;
			while(foundRule == false && i < rListSize)
			{
				tempRule = rList.get(i);
				if(tempRule.state == currentState)
				{
					//System.out.println("String is :" + tape);
					if(	tempRule.read.equals(Character.toString(tape.charAt(tapeHead))) )
					{	
						//We can stop the loop and keep going
						foundRule = true;
						halt = false;
						
						//Write
						//Error check this. What if tapehead is at the end of the string???
						tape = write(tape, tapeHead, tempRule);
						
						//State change
						currentState = tempRule.endState;
						
						//Move
						if(tempRule.move == -1)
						{
							//If we're at the leftmost side and we're asked to move left, do nothing
							if(tapeHead == 0)
								;
							
							else
								tapeHead -= 1;
						}
						
						else
						{
							tapeHead += 1;
							//If we're at the end of the tape, we add a blank space to simulate an infinite tape
							if (tapeHead == tape.length())
							{
								tape = tape.concat("B");
							}
								
						}
							
					}
				}
				
				i++;
			}
		
			step++;
		}
		
		if(currentState == 1)
			System.out.println(input + ": YES");
		else
		{
			if(currentState == 2)
				System.out.println(input + ": NO");
			
			else
				System.out.println(input + ": DOES NOT HALT IN " + maxSteps + " STEPS");
		}
	}
	
	
	public static void main(String args[])
	{ 
		tm TM = new tm();
		Rule tempRule = TM.new Rule();
		int i , j, k;
		int numStates, numRules, numStrings, maxSteps;
		char testMove;
		String[] nextLine;
		List<Rule> rList = new ArrayList<Rule>();
		List<String> sList = new ArrayList<String>();
		Scanner input = new Scanner(System.in);
		
		int numMachines = Integer.parseInt(input.nextLine());
		
		for(i = 0; i < numMachines; i++)
		{
			//Saving information for running tm
			rList.clear();
			sList.clear();
			nextLine = input.nextLine().split(" ");
			numStates = Integer.parseInt(nextLine[0]);
			numRules = Integer.parseInt(nextLine[1]);
			
			
			for(j = 0; j < numRules; j++)
			{
				nextLine = input.nextLine().split(" ");
				tempRule.state = Integer.parseInt(nextLine[0]);
				tempRule.read = Character.toString(nextLine[1].charAt(0));
				tempRule.endState = Integer.parseInt(nextLine[2]);
				tempRule.write = Character.toString(nextLine[3].charAt(0));
				testMove = nextLine[4].charAt(0);
				if(testMove == 'R' || testMove == 'r')
					tempRule.move = 1;
				else
					tempRule.move = -1;
				
				rList.add(TM.new Rule(tempRule.state, tempRule.read, tempRule.endState, 
						tempRule.write, tempRule.move));
				
				
				
			}
			
			
			
			nextLine = input.nextLine().split(" ");
			numStrings = Integer.parseInt(nextLine[0]);
			maxSteps = Integer.parseInt(nextLine[1]);
			
			for(k = 0; k < numStrings; k++)
			{
				nextLine[0] = input.nextLine();
				sList.add(nextLine[0]);
			}
			
			//Sending information to actual tm simulator
			System.out.println("\nMachine #" + i + ":");
			for(k = 0; k < numStrings; k++)
			TM.tmSim(rList, sList.get(k), maxSteps);
		}
		
		
		
	} 
}
