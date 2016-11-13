/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *              ♠ ♥ ♦ ♣  Blackjack!  ♣ ♦ ♥ ♠                *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                  Project :  prjBlackjack                    *
 *                  Package :  pckMain                         *
 *                    Class :  clsMenu                         *
 *                   Author :  Matthew Murray                  *
 *                     Date :  10/05/2015                      *
 *                  Version :  2.2.3                           *    
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                          Description                        *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *
 * ~ Startup class of project, has the initial menu, help      *
 *   information, and links the technical documentation.       *
 * ~ On start, prints menu then asks for user input. 1 opens   *
 *   the game, 2 prints the game instructions, 3 opens your    *
 *   web browser with the technical documentation, 4 exits the *
 *   application. After each of these methods (apart from #4)  *
 *   the menu will display again.                              *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package pkcMain;

import java.util.Scanner;
import java.io.*;  // *used to read from a file in Help()
import java.awt.Desktop; // *used to open a webpage in Info()
import java.net.URI;     //

public class clsMenu 
{
    public static Scanner INPUT = new Scanner(System.in);   
    public static final String RESET = "\u001B[0m";   // * Sets text colour in printout using ANSI escape codes,
    public static final String RED = "\u001B[31m";    //   calling a string is just easier than retyping the code.
    public static final String GREEN = "\u001B[32m";  // 
    public static final String YELLOW = "\u001B[33m"; // * Apparently windows based compilers dont always like this method.    
    public static final String BLUE = "\u001B[34m";   //   Might cause incorrectly displayed text.
    public static final String PURPLE = "\u001B[35m"; // * Remember you need to recall the Esc code when you \n and for each string you concatenate.
    public static final String CYAN = "\u001B[36m";   // * End with RESET to make sure that the printout returns to the default.
    public static final String strMenu = (BLUE + "* * * * * * * * * * * * *\n*\t" + BLUE + "Main Menu\t*\n"
                + BLUE + "* * * * * * * * * * * * *\n*\t" + CYAN + "1. New Game" 
                + BLUE + "\t*\n*\t" + CYAN + "2. Help" + BLUE + "\t\t*\n*\t"       // text for menu screen.
                + CYAN + "3. More Info" + BLUE + "\t*\n*\t" + CYAN + "4. Exit" 
                + BLUE + "\t\t*\n* * * * * * * * * * * * *" + RESET);
    
            
   /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *                             Method : main                               *
    *                               Date : 24/04/2015                         *
    *                            Version : 2.1.1                              *
    *                         Parameters : String[] args                      *
    *                            Returns : N/A                                *
    * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *
    *                              Description                                *
    * ~ Gives menu perameters to Menu() and runs a method depending on        *
    *   returned value.                                                       *
    * ~ Continues to loop until the exit option is selected which finishes    *
    *   the applicatipn.                                                      *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public static void main(String[] args) 
    {
        Loop: while(true)
        {
            switch(Menu(strMenu, 1, 4))
            {
                case 1: //New Game
                        clsGame.main();
                        break;

                case 2: //Help
                        Help();
                        break;

                case 3: //Documentation
                        Info();
                        break;

                case 4: //exit
                        System.out.print(GREEN + "Are you sure? (Y/N) \t" + RESET);
                        if (INPUT.next().compareToIgnoreCase("y") == 0 )
                        {
                            break Loop;
                        }
                        break;             
            }// end switch            
        }// end Loop
        System.out.println("Bai :)");
    }
     
   
   /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *                             Method : Menu                               *
    *                               Date : 24/04/2015                         *
    *                            Version : 2.1.2                              *
    *                         Parameters : String(print), int(Lbound),        *
    *                                       int(Hbound)                       *
    *                            Returns : int                                *
    * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *
    *                              Description                                *
    * ~ Prints out parameter string and asks for user input, if inputted      *
    *   integer is a digit and within the bounds set by the two integer       *
    *   parameters then the number is returned to main()                      *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public static int Menu(String Print, int Lbound, int Hbound)
    { /* Lbound and Hbound must be between 0 & 9, Lbound must be smaller than Hbound */
        int tmpReturn;
        System.out.println(Print);
        while(true)
        {
            System.out.print(GREEN + "Please pick an option:\t" + RESET);
            tmpReturn = INPUT.next().charAt(0);
            if (Character.isDigit(tmpReturn) == true)
            {
                tmpReturn = Character.getNumericValue(tmpReturn);
                if( (tmpReturn >= Lbound) && (tmpReturn <= Hbound) )
                {
                    return tmpReturn;
                }
                else
                {
                    System.out.println(RED + "ERROR: Incorect entry" + RESET);
                }
            }
            else
            {
                System.out.println(RED + "ERROR: You must enter a number" + RESET);
            }
        }
    }
 
    
   /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *                             Method : Help                               *
    *                               Date : 24/04/2015                         *
    *                            Version : 2.2.4                              *
    *                         Parameters : N/A                                *
    *                            Returns : N/A                                *
    * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *
    *                              Description                                *
    * ~ Creates a FileInputStream to a text file that has the content to be   *
    *   printed.                                                              *
    * ~ Each char from the input stream is taken and saved to a string.       *
    * ~ When all chars have been copied the string is printed.                *
    * ~ An exception message is printed if something goes wrong with the      * 
    *   input stream.                                                         *
    * ~ Asks for user input, when inputted ends the method (added to prevent  *
    *   the menu display from being printed which caused part of the help     *
    *   display to extend off the screen.)                                    *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public static void Help()
    {
        String strContent = "";
        try
        {
            FileInputStream fisHelp = new FileInputStream("GameHelp.txt");
            
            InputStreamReader reader = new InputStreamReader(fisHelp);

            int intPos = reader.read();
            while (intPos != -1)
            {
                char chrMove = (char) intPos;
                strContent += chrMove;
                intPos = reader.read();
            }
            fisHelp.close();
        } 
        catch (Exception ex)
        {
            System.out.println(RED + "File error " + RED + ex.toString() + RESET );
        }
        System.out.println(strContent); 
        System.out.print(GREEN + "Continue? (Y) " + RESET);
        INPUT.next();
    }
    
    
   /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *                             Method : Info                               *
    *                               Date : 24/04/2015                         *
    *                            Version : 2.3.7                              *
    *                         Parameters : N/A                                *
    *                            Returns : N/A                                *
    * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *
    *                              Description                                *
    * ~ Checks if the application can open your default browser.              *
    * ~ opens a new browser window using the html file stored in the root     *
    *   folder of the java application.                                       *
    * ~ If the browser cant open an exception message will be printed, if the *
    *   application cannot access the browser an error message is printed.    * 
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */    
    public static void Info()
    {               
        if(Desktop.isDesktopSupported())
        {
            try
            {
                Desktop.getDesktop().browse(new URI("GameInfo.html"));
                System.out.println(BLUE + "The help screen has opened in your web browser.\n" + RESET);
            }
            catch (Exception ex)
            {
                System.out.println(RED + "File error " + RED + ex.toString() + RESET );
            }        
        }
        else
        {
            System.out.println(RED + "Unable to open web browser" + RESET );
        }
    }
    
}