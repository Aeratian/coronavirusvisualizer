/**
 * CoronaVirusData.java
 * A class to display Coronavirus case data of the five largest
 * states in terms of population in the United States over time
 * in an animated bar graph. A scale is set in the background
 * to quantify the number of cases. The total number of Coronavirus
 * cases in the US and date of the collection of data is displayed
 * in the bottom left of the canvas along with the title of the graph.
 * @author   Ian Youn
 * @version  1.0
 * @since    9/11/2020
 */

import java.awt.Color;
import java.awt.Font;
import java.util.Scanner;

public class CoronaVirusData
{
	/** The number of Coronavirus cases in California */
	private int californiaCases;
	/** The number of Coronavirus cases in Texas */
	private int texasCases;
	/** The number of Coronavirus cases in Florida */
	private int floridaCases;
	/** The number of Coronavirus cases in New York */
	private int newYorkCases;
	/** The number of Coronavirus cases in Illinois */
	private int illinoisCases;
	/** The total number of Coronavirus cases in the United States */
	private int usTotalCases;
	/** The maximum number of Coronavirus cases in one state */
	private int maximumCases;
	/** The previous date of the collected data */
	private int lastDate;
	/** The previous month of the collected data */
	private int lastMonth;
	
	/**
	 * Creates a CoronaVirusData object. There are no field variables
	 * that require constructing, making this a no-args constructor.
	 */
	public CoronaVirusData ( )
	{
		
	}

	/**
	 *  The main method, to create and run an instance of CoronaVirusData,
	 *  named "run" in this method.
	 *  @param args          The array of Strings that are used to pass in
	 *                       the String arguments from the command line.
	 */
	public static void main(String [] args)   
	{
		CoronaVirusData run = new CoronaVirusData();
		run.setUpCanvas();
		run.displayDataAndScale();
	}

	/**
	 * Sets up the canvas of the Frame, using a 1400 by 900 size.
	 * Sets the scales for the x and y axes to one. The background
	 * color is set to black and the font is set to Arial with a
	 * bold modifier and size 16. Finally, double buffering is enabled
	 * for animation purposes.
	*/
	public void setUpCanvas ()
	{
		//Sets the canvas size and scale
		StdDraw.setCanvasSize(1400, 900);
		StdDraw.setXscale(-700, 700);
		StdDraw.setYscale(-450, 450);

		//Sets the canvas color to Black
		StdDraw.clear(StdDraw.BLACK);

		//Sets the font to Arial with size 16
		Font font = new Font("Arial", Font.BOLD, 16);
		StdDraw.setFont(font);

		StdDraw.enableDoubleBuffering();
	}

	/**
	 * Reads in the "data4.txt" file and scans each line for the number of cases.
	 * Then, checks if the first line has been read before scanning the line for
	 * the date and the month. Checks if the date is equal to the lastDate and, if
	 * false, calculates the maximum cases of the five states, and draws the graph
	 * using setScale, createBarGraph, and setTileDateAndCases, before setting the
	 * lastMonth and lastDate variables to the current date and the usTotalCases
	 * variable to zero. Finally, sets the global variables californiaCases, texasCases,
	 * floridaCases, newYorkCases, and illinoisCases by checking if the line read contains
	 * the state name then setting the variable equal to the number of cases. The maximum
	 * value is calculated and the other methods are called again for the last date.
	 */
	public void displayDataAndScale ()
	{
		Scanner inFile = OpenFile.openToRead("data4.txt");

		//Used to determine if the first line has been read for the sum
		boolean notFirst = false;

		lastDate = 1;
		lastMonth = 3;

		while(inFile.hasNext())
		{
			String line = inFile.nextLine();
			String cases = line.substring(line.substring(0, line.lastIndexOf(',')-1).lastIndexOf(',') + 1, line.lastIndexOf(','));

			if(notFirst)
			{
				int month = Integer.parseInt(line.substring(line.indexOf("-") + 1, line.indexOf("-") + line.substring(line.indexOf("-") + 1).indexOf("-") + 1));
				int date = Integer.parseInt(line.substring(line.indexOf("-") + line.substring(line.indexOf("-") + 1).indexOf("-") + 2, line.indexOf(",")));
				if (lastDate != date)
				{
					//Draws the graph
					maximumCases = Math.max(californiaCases, Math.max(texasCases, Math.max(floridaCases, Math.max(newYorkCases, illinoisCases))));
					setScale();
					createBarGraph();
					setTitleDateAndCases();
					StdDraw.show();
					StdDraw.pause(100);
					StdDraw.clear(StdDraw.BLACK);

					//Updates the variables
					lastMonth = month;
					lastDate = date;
					usTotalCases = 0;
				}
				usTotalCases += Integer.parseInt(cases);

				//Finds each of the five states in the text and updates vars
				if (line.indexOf("California") != -1)
				{
					californiaCases = Integer.parseInt(cases);
				}
				if (line.indexOf("Texas") != -1)
				{
					texasCases = Integer.parseInt(cases);
				}
				if (line.indexOf("Florida") != -1)
				{
					floridaCases = Integer.parseInt(cases);
				}
				if (line.indexOf("New York") != -1)
				{
					newYorkCases = Integer.parseInt(cases);
				}
				if (line.indexOf("Illinois") != -1)
				{
					illinoisCases = Integer.parseInt(cases);
				}
			}
			notFirst = true;
		}

		//Last date's data is drawn separately as it is not drawn inside of the loop
		maximumCases = Math.max(californiaCases, Math.max(texasCases, Math.max(floridaCases, Math.max(newYorkCases, illinoisCases))));
		setScale();
		createBarGraph();
		setTitleDateAndCases();
		StdDraw.show();
		StdDraw.pause(100);
		StdDraw.clear(StdDraw.BLACK);
	}

	/**
	 * Adds the scale of the bar graph that represents the number of
	 * Coronavirus cases for each of the biggest 5 states. Sets the
	 * pen color to light grey and draws vertical lines, iterating by
	 * a value according to the scale of 1000 units per maximum value
	 * of cases. Then, writes the labels for the scale above each line,
	 * starting from 0 and incrementing by the highest power of ten lower
	 * than the maximum number of cases of the five states until the
	 * maximum value is exceeded. The lines with x-value above 275 are
	 * shortened to prevent them from intersecting with the title.
	 */
	public void setScale ()
	{
		Font font = new Font("Arial", Font.BOLD, 20);
		StdDraw.setFont(font);
		StdDraw.setPenColor(new Color( 75,75,75));
		String number = maximumCases+"";
		int firstDigit = Integer.parseInt(number.substring(0, 1));
		String scale;

		for (int i = 0; i <= firstDigit + 1; i++)
		{
			//Determines if the text should contain a k for shortening of text
			if (number.length() > 4)
			{
				scale = (int) Math.pow(10, number.length() - 4) * i + "k";
			}
			else
			{
				scale = (int) Math.pow(10, number.length() - 1) * i + "";
			}

			StdDraw.text(-600 + 1000 * Math.pow(10, number.length() - 1) * i / maximumCases, 320, scale);

			//Determines if the line should be shortened
			if (-600 + 1000 * Math.pow(10, number.length() - 1) * i / maximumCases > 275)
			{
				StdDraw.line(-600 + 1000 * Math.pow(10, number.length() - 1) * i / maximumCases, 305, -600 + 1000 * Math.pow(10, number.length() - 1) * i / maximumCases, -100);
			}
			else
			{
				StdDraw.line(-600 + 1000 * Math.pow(10, number.length() - 1) * i / maximumCases, 305, -600 + 1000 * Math.pow(10, number.length() - 1) * i / maximumCases, -350);
			}
		}
	}


	/**
	 * Creates the bars for the five states, using the filledRectangle
	 * method. Changes the font size to 20 and sets each bar's starting
	 * x-value to -600. The half-width of the rectangles are scaled
	 * based on five-hundred times the number of cases in the current
	 * state divided by the maximum number of cases for the five states.
	 * Sets each rectangle's color to one of the state colors of the
	 * state and sets the text color to white. Writes out the state name
	 * 20 units to the right of the end of the rectangle and writes the
	 * number of Coronavirus cases 90 units to the left.
	 */
	public void createBarGraph ()
	{
		Font font = new Font("Arial", Font.BOLD, 20);
		StdDraw.setFont(font);

		//Creates the bar for California
		StdDraw.setPenColor(new Color( 255,199,44));
		StdDraw.filledRectangle(-600 + 500 * californiaCases / maximumCases,280, 500 * californiaCases / maximumCases,25);
		StdDraw.setPenColor(new Color( 255,255,255));
		StdDraw.textRight(-610 + 1000 * californiaCases / maximumCases, 280, String.format("%,d", californiaCases));
		StdDraw.textLeft(-580 + 1000 * californiaCases / maximumCases, 280, "California");

		//Creates the bar for Texas
		StdDraw.setPenColor(new Color( 0,0,255));
		StdDraw.filledRectangle(-600 + 500 * texasCases / maximumCases,205,500 * texasCases / maximumCases,25);
		StdDraw.setPenColor(new Color( 255,255,255));
		StdDraw.textRight(-610 + 1000 * texasCases / maximumCases, 205, String.format("%,d", texasCases));
		StdDraw.textLeft(-580 + 1000 * texasCases / maximumCases, 205, "Texas");

		//Creates the bar for Florida
		StdDraw.setPenColor(new Color( 250,70,22));
		StdDraw.filledRectangle(-600 + 500 * floridaCases / maximumCases,130,500 * floridaCases / maximumCases,25);
		StdDraw.setPenColor(new Color( 255,255,255));
		StdDraw.textRight(-610 + 1000 * floridaCases / maximumCases, 130, String.format("%,d", floridaCases));
		StdDraw.textLeft(-580 + 1000 * floridaCases / maximumCases, 130, "Florida");

		//Creates the bar for New York
		StdDraw.setPenColor(new Color( 252,76,2));
		StdDraw.filledRectangle(-600 + 500 * newYorkCases / maximumCases,55,500 * newYorkCases / maximumCases,25);
		StdDraw.setPenColor(new Color( 255,255,255));
		StdDraw.textRight(-610 + 1000 * newYorkCases / maximumCases, 55, String.format("%,d", newYorkCases));
		StdDraw.textLeft(-580 + 1000 * newYorkCases / maximumCases, 55, "New York");

		//Creates the bar for Illinois
		StdDraw.setPenColor(new Color( 63,134,63));
		StdDraw.filledRectangle(-600 + 500 * illinoisCases / maximumCases,-20,500 * illinoisCases / maximumCases,25);
		StdDraw.setPenColor(new Color( 255,255,255));
		StdDraw.textRight(-610 + 1000 * illinoisCases / maximumCases, -20, String.format("%,d", illinoisCases));
		StdDraw.textLeft(-580 + 1000 * illinoisCases / maximumCases, -20, "Illinois");
	}

	/**
	 * Changes the font size to 30 and color to red. Then, writes, on
	 * the bottom right of the canvas, the date in which the data was
	 * collected, based on the parameters month and day. Writes the sum
	 * of the fifty states' cases (usTotalCases) beneath it. Changes the
	 * font color back to white and writes the title of the bar graph
	 * "Coronavirus Cases by State" underneath.
	 */
	public void setTitleDateAndCases ()
	{
		String monthText = "";
		Font font = new Font("Arial", Font.BOLD, 30);
		StdDraw.setFont(font);

		switch(lastMonth)
		{
			case 3: monthText = "March";       break;
			case 4: monthText = "April";       break;
			case 5: monthText = "May";         break;
			case 6: monthText = "June";        break;
			case 7: monthText = "July";        break;
			case 8: monthText = "August";      break;
			case 9: monthText = "September";   break;
		}

		StdDraw.setPenColor(new Color(175, 0, 0));
		StdDraw.textLeft(300, -150, monthText + " " + lastDate + ", 2020");
		StdDraw.textLeft(300, -200, "US Total: " + String.format("%,d", usTotalCases));

		StdDraw.setPenColor(new Color( 255,255,255));
		StdDraw.textLeft(300, -250, "CORONAVIRUS");
		StdDraw.textLeft(300, -300, "Cases by State");
	}
}