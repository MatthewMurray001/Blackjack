/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *              ♠ ♥ ♦ ♣  Blackjack!  ♣ ♦ ♥ ♠                *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                  Project :  prjBlackjack                    *
 *                  Package :  pckMain                         *
 *                    Class :  clsGame                         *
 *                   Author :  Matthew Murray                  *
 *                     Date :  10/05/2015                      *
 *                  Version :  2.3.7                           *    
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                          Description                        *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *
 * ~ Gameplay class, all game related methods are in this      *
 *   class so that they are not confused with menu related     *
 *   methods.                                                  *
 * ~ When ran the user is asked to make a bet then dealt two   *
 *   cards and asked if they want to hit, stand, or double.    *
 *   if doubling they are dealt one more card then their cards *
 *   are compared with the dealers to decide a winner.         *
 *   Otherwise the user will move to the next screen (and      *
 *   draws one card if they hit). They are again asked if they *
 *   want to hit or stand, hitting gives the player one card   *
 *   and repeats the screen until the user stands or goes bust.*
 *   When they stand their cards are compared with the dealer  * 
 *   to decide the winner. Going above 21 at any stage causes  *
 *   the player to lose, drawing 21 as your starting hand will *
 *   give the player a Blackjack and cause them to win (unless *
 *   the dealer also has a blackjack).                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package pkcMain;

import java.util.Random;
import java.util.Scanner;

public class clsGame 
{
    public static Scanner INPUT = new Scanner(System.in);
    public static Random RndNum = new Random();
    
    public static final String RESET = "\u001B[0m";   
    public static final String RED = "\u001B[31m";    
    public static final String GREEN = "\u001B[32m";   
    public static final String YELLOW = "\u001B[33m";   
    public static final String BLUE = "\u001B[34m";   
    public static final String PURPLE = "\u001B[35m"; 
    public static final String CYAN = "\u001B[36m";
    
    public static String strPlayer = "";
    public static String strDealer = "";
  //public static String strSplit = "";
    public static int intPlayer[] = {200,0,0,0,/*0,0*/};//[0]chip, [1]bet, [2]tot1, [3]tot2, /*[4]SplitTot1, [5]SplitTot2.*/
    public static int intDealer[] = {0,0}; //[0]tot1, [1]tot2. 
    
    
   /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *                             Method : main                               *
    *                               Date : 24/04/2015                         *
    *                            Version : 2.2.5                              *
    *                         Parameters : N/A                                *
    *                            Returns : N/A                                *
    * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *
    *                              Description                                *
    * ~ Resets players chips to default and starts the game loop which runs   *  
    *   until the user decides to exit.                                       *
    * ~ Runs the Bet() and FirstPlay() methods (which will in turn run the    *
    *   rest of the methods needed for the gameplay)                          *
    * ~ When the user has finished their hand, checks if they still have      *
    *   chips, if not game ends, else a message is displayed asking if they   *
    *   want to play another hand, if the user choses no the game will end,   *
    *   returning the player to the main menu.                                *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */    
    public static void main()
    {
        intPlayer[0] = 200;
        LOOP:while (true)
        {            
            Bet(); 
            FirstPlay();
            if(intPlayer[0] == 0)
            {
                System.out.print("\n\t" + RED + "GAME OVER" + RESET);
                break LOOP;
            }
            System.out.print(GREEN + "Do you want to play another hand? (Y/N) \t" + RESET);
            if (INPUT.next().compareToIgnoreCase("n") == 0 )
            {
                break LOOP;
            }                
        } // end LOOP.       
    }// end main method.   
    
    
   /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *                             Method : Bet                                *
    *                               Date : 24/04/2015                         *
    *                            Version : 2.1.3                              *
    *                         Parameters : N/A                                *
    *                            Returns : N/A                                *
    * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *
    *                              Description                                *
    * ~ Prints how many chips are available and what limits there are to the  *
    *   bet, asks how much the user wants to bet.                             * 
    * ~ If the inputted amount is valid then it is saved to intPlayer[1], if  *
    *   not valid the user will be asked to enter a value again.              *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public static void Bet()
    {   
        System.out.print("\n" + BLUE + "* * * * * * * * * * * * * * * * *\n" +  // displays how much you can bet.
                BLUE + "* \tYou have " + PURPLE + intPlayer[0] + BLUE +" chips\t*\n" +        
                BLUE + "* How much do you want to bet?  *\n" +
                BLUE + "* \tmin: " + PURPLE +"1" + BLUE + "\tmax: " + PURPLE + "50" + BLUE + "\t\t*\n" +
                BLUE + "* * * * * * * * * * * * * * * * *\n" +
                GREEN + "\tBet:\t" + RESET);        
              
        LOOP: for(intPlayer[1] = 0 ; intPlayer[1] < 1 ; System.out.print(GREEN + "\tBet:\t" + RESET))
        {
             
            try
            {
                int tmpHold = INPUT.nextInt();
                if(tmpHold > intPlayer[0])
                {
                    System.out.println(RED + "you only have " + PURPLE + intPlayer[0] + RED + " chips" + RESET);
                }
                else
                {
                    if (tmpHold <= intPlayer[0] && tmpHold >= 1 && tmpHold <= 50)   // checks if the bet is within bounds, 
                    {                                                           //   and if the player has enough chips.
                        intPlayer[1] = tmpHold;  
                        break LOOP;
                    }
                    else
                    {
                        System.out.println(RED + "Your bet must be between 1 and 50"+ RESET);
                    }
                }        
            }// end try
            catch (Exception ex)
            {
                System.out.println(RED + "you must enter a number" + RESET );
                INPUT.nextLine(); // clears INPUT buffer for try/catch.
            }
                
        }// end LOOP.
    }// end Bet method.
    
    
   /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *                             Method : FirstPlay                          *
    *                               Date : 24/04/2015                         *
    *                            Version : 2.2.4                              *
    *                         Parameters : N/A                                *
    *                            Returns : N/A                                *
    * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *
    *                              Description                                *
    * ~ Runs DealPlayer() twice and DealDealer() once so that there are two   *
    *   player and one dealer cards to display.                               *
    * ~ If both of the player card values are the same then just the first is *
    *   displayed, else both values are displayed.                            *
    * ~ If the player has a blackjack the current card values will be         *
    *   displayed along with a blackjack message, the FinalPlay() method is   *
    *   ran and the method ends.                                              *
    * ~ Otherwise the current card values and player options are displayed and*
    *   the user is asked to make their choice.                               *
    * ~ Depending on the user input, SecondPlay() or Double() are ran.        *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */    
    public static void FirstPlay()
    {
        DealPlayer();
      //int tmpCheck1 = intPlayer[2];
        DealPlayer();
      //int tmpCheck2 = (intPlayer[2] - tmpCheck1);
        DealDealer();        
        String tmpPlayerTotal = intPlayer[2] + "/" + intPlayer[3];
        if (intPlayer[2] == intPlayer[3])
        {
            tmpPlayerTotal = Integer.toString(intPlayer[2]);
        }
        if (intPlayer[2] == 21 || intPlayer[3] == 21)
        {
             System.out.println(BLUE + "* * * * * * * * * * * * * * * * * * * \n" 
                     + BLUE + "* Dealer: " + PURPLE + strDealer + BLUE +"\tYour Bet: " 
                     + PURPLE + intPlayer[1] + BLUE + "\t    * \n" 
                     + BLUE + "*    You: " + PURPLE + strPlayer + BLUE + "\tTotal: " 
                     + PURPLE +  tmpPlayerTotal + BLUE+ "\t    *\n" 
                     + BLUE + "* * * * * * * * * * * * * * * * * * * \n"
                     + BLUE + "*" + PURPLE + "  ♠ ♥ ♦ ♣  Blackjack!  ♣ ♦ ♥ ♠  " + BLUE + "*\n" 
                     + BLUE + "* * * * * * * * * * * * * * * * * * *\n" + RESET);
            FinalPlay(1);
            return ; //acts like a break and ends method.
        }        
        
        
        System.out.print(BLUE + "* * * * * * * * * * * * * * * * * * * \n" 
                + BLUE + "* Dealer: " + PURPLE + strDealer + BLUE +"\tYour Bet: " 
                + PURPLE + intPlayer[1] + BLUE + "\t    * \n" 
                + BLUE + "*    You: " + PURPLE + strPlayer + BLUE + "\tTotal: " 
                + PURPLE +  tmpPlayerTotal + BLUE+ "\t    *\n" 
                + BLUE + "*-----------------------------------* \n" + RESET);
    /*if (tmpCheck1 == tmpCheck2)                       // * would be to check if the user could have the switch option displayed  
        {
            System.out.print(BLUE + "* " + CYAN + "Hit(1) Stand(2) Double(3) Split(4)" + BLUE + "*\n" + RESET);
        }
        else
        {*/
            System.out.print(BLUE + "* " + CYAN + "Hit(1) Stand(2) Double(3)         " + BLUE + "*\n" + RESET);
    /* } */
        System.out.print(BLUE + "* * * * * * * * * * * * * * * * * * *\n"
                + GREEN + "\tYour choice:\t" + RESET);  
        LOOP: while(true)
        {
            
            try
            {
                int tmpHold = INPUT.nextInt();
                switch(tmpHold)
                {
                    case 1: DealPlayer();
                            SecondPlay();
                            break LOOP;
                    case 2: DealDealer();
                            SecondPlay();
                            break LOOP;
                    case 3: Double();
                            break LOOP;
                    /*case 4: if (tmpCheck1 == tmpCheck2)
                            {
                                Split(tmpCheck1);
                                break LOOP;
                            }
                            else
                            {
                                System.out.print(RED + "You must enter a number within the bounds\n\t" + GREEN + "Your choice:\t" + RESET);
                            }
                            break;  */
                    default : System.out.print(RED + "You must enter a number within the bounds\n\t" + GREEN + "Your choice:\t" + RESET);
                }// end switch.
            }//end try
            catch (Exception ex)
            {
                System.out.print(RED + "You must enter a number\n\t" + GREEN + "Your choice:\t" + RESET);
                INPUT.nextLine(); // clears INPUT buffer for try/catch.
            }    
        } // end LOOP.
    }//end FirstPlay method.
    
    
   /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *                             Method : SecondPlay                         *
    *                               Date : 24/04/2015                         *
    *                            Version : 2.1.5                              *
    *                         Parameters : N/A                                *
    *                            Returns : N/A                                *
    * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *
    *                              Description                                *
    * ~ If both of the player card values are the same then just the first is *
    *   displayed, else both values are displayed.                            *
    * ~ If the player has gone bust the current card values will be           *  
    *   displayed along with a bust message, the FinalPlay() method is ran    *
    *   and the method ends.                                                  *
    * ~ Otherwise the current card values and player options are displayed and*
    *   the user is asked to make their choice.                               *
    * ~ If the user hits then a card is added and the previous step is        *
    *   repeated until they either go bust or stand.                          *
    * ~ If the user stands the FinalPlay method is ran and the method ends.   *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */    
    public static void SecondPlay()
    {
        LOOP: while(true)
        {            
            String tmpPlayerTotal = intPlayer[2] + "/" + intPlayer[3];
            if (intPlayer[2] == intPlayer[3])
            {
                tmpPlayerTotal = Integer.toString(intPlayer[2]);
            }
            if( intPlayer[2]  > 21 )
            {
                System.out.println(BLUE + "* * * * * * * * * * * * * * * * * * * \n" 
                        + BLUE + "* Dealer: " + PURPLE + strDealer + BLUE + "\tYour Bet: " 
                        + PURPLE + intPlayer[1] + BLUE + "\t    * \n" 
                        + BLUE + "*    You: " + PURPLE + strPlayer + BLUE + "\tTotal: " 
                        + PURPLE + tmpPlayerTotal + BLUE + "   *\n" 
                        + BLUE + "*-----------------------------------* \n" 
                        + BLUE + "*\t  " + PURPLE + "~ You went bust ~" + BLUE + "\t    *\n"
                        + BLUE + "* * * * * * * * * * * * * * * * * * *\n" + RESET);
                FinalPlay(2);    
                break LOOP;
            }
            
            System.out.print(BLUE + "* * * * * * * * * * * * * * * * * * * \n" 
                    + BLUE + "* Dealer: " + PURPLE + strDealer + BLUE + "\tYour Bet: " 
                    + PURPLE + intPlayer[1] + BLUE + "\t    * \n" 
                    + BLUE + "*    You: " + PURPLE + strPlayer + BLUE + "\tTotal: " 
                    + PURPLE + tmpPlayerTotal + BLUE + "   *\n" 
                    + BLUE + "*-----------------------------------* \n" 
                    + BLUE + "* " + CYAN + "Hit(1) Stand(2)" + BLUE + "\t\t    *\n" 
                    + BLUE + "* * * * * * * * * * * * * * * * * * *\n"
                    + GREEN + "\tYour choice:\t" + RESET);
            INNER_LOOP: while(true)
            {            
                try 
                {
                     int TmpHold = INPUT.nextInt();
                    switch(TmpHold)
                    {
                        case 1: DealPlayer();
                                break INNER_LOOP;
                        case 2: DealerPlay();
                                FinalPlay(0);
                                break LOOP;
                        default : System.out.print(RED + "You must enter a number within the bounds.\n\t" 
                                + GREEN + "Your choice:\t" + RESET);
                    }
                } 
                catch (Exception ex) 
                {
                    System.out.println(RED + "you must enter a number" + RESET );
                    INPUT.nextLine(); // clears INPUT buffer for try/catch.
                }
                
               
            } //end INNER_LOOP
        }// end LOOP                               
    }// end SecondPlay method
    
    
   /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *                             Method : DealerPlay                         *
    *                               Date : 22/03/2015                         *
    *                            Version : 2.1.1                              *
    *                         Parameters : N/A                                *
    *                            Returns : N/A                                *
    * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *
    *                              Description                                *
    * ~ The dealer draws cards until either card total is 17 or over.         *
    * ~ If both of the player card values are the same then just the first is *
    *   displayed, else both values are displayed. All card details are       *
    *   displayed and the method ends.                                        *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */    
    public static void DealerPlay()
    {
        LOOP: while(true)
        {
            if(intDealer[1] < 17 )
            {
                DealDealer();
            }
            else if (intDealer[1] > 21)
            {
                if (intDealer[0] < 17)
                {
                    DealDealer();
                }
                else
                {
                    break LOOP;
                }                
            }
            else
            {
                break LOOP;
            }
        }// end LOOP.
        String tmpPlayerTotal = intPlayer[2] + "/" + intPlayer[3];
            if (intPlayer[2] == intPlayer[3])
            {
                tmpPlayerTotal = Integer.toString(intPlayer[2]);
            }
        System.out.println(BLUE + "* * * * * * * * * * * * * * * * * * * \n" 
                + BLUE + "* Dealer: " + PURPLE + strDealer + BLUE + "\tYour Bet: " 
                + PURPLE + intPlayer[1] + BLUE + "* \n" 
                + BLUE + "*    You: " + PURPLE + strPlayer + BLUE+ "\tTotal: " 
                + PURPLE + tmpPlayerTotal + BLUE +"   *\n" 
                + BLUE + "* * * * * * * * * * * * * * * * * * * \n" + RESET);
    }
    
    
   /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *                             Method : FinalPlay                          *
    *                               Date : 24/04/2015                         *
    *                            Version : 2.1.4                              *
    *                         Parameters : int(Blackjack)                     *
    *                            Returns : N/A                                *
    * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *
    *                              Description                                *
    * ~ If the player or dealer have a BlackJack the respective Win/lose/draw *
    *   message is saved to tmpWinner and new chip total saved to intPlayer.  *
    * ~ If there is no BlackJack then Winner is ran and the returned string   *
    *   saved to tmpWinner.                                                   *
    * ~ tmpWinner and the chip total is displayed and all card totals are     *
    *   reset. The method ends (moves to if statement in main())              *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */    
    public static void FinalPlay(int BlackJack)
    {          
        String tmpWinner = (RED + "\tSomething went wrong" + RESET);
        if (BlackJack == 1 && BlackJack == 2)
        {
            tmpWinner = Draw();
        }
        else if (BlackJack == 1) 
        {
            tmpWinner = (BLUE + "*      " + PURPLE + "~ You win ~" + BLUE + "      *" + RESET);
            intPlayer[0] = intPlayer[0] + ((intPlayer[1] * 2) + (intPlayer[1]/2 )) ;
        }             
        else if (BlackJack == 2)
        {
            tmpWinner = (BLUE + "*  " + PURPLE + "~ The dealer Wins ~" + BLUE + "	*" + RESET);
            intPlayer[0] = intPlayer[0] - intPlayer[1];
        }
        else
        {
            tmpWinner = Winner();            
        }
        
        System.out.println(BLUE + "\t* * * * * * * * * * * * *\n\t" + BLUE +  tmpWinner + "\n\t"
                + BLUE + "*  You have " + PURPLE + intPlayer[0] + BLUE + " chips\t*\n\t" + BLUE 
                +"* * * * * * * * * * * * *" + RESET );                
        
        strPlayer = "";
        strDealer = "";
        intPlayer[2] = 0;
        intPlayer[3] = 0;
        intDealer[0] = 0;
        intDealer[1] = 0;
    }// end FinalPlay method
    
    
   /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *                             Method : Winner                             *
    *                               Date : 22/03/2015                         *
    *                            Version : 2.1.1                              *
    *                         Parameters : N/A                                *
    *                            Returns : String                             *
    * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *
    *                              Description                                *
    * ~ Works out if the user has won/lost/drawn with the dealer via a nested *
    *   if statement that (should) contain all possible variations of cards.  * 
    * ~ Win()/Lose()/Draw() ran depending on result, returned value returned  *
    *   to FinalPlay()                                                        *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */    
    public static String Winner()
    {
        String tmpWinner = (RED + "\tSomething went wrong" + RESET);
        if(intPlayer[3] > 21)
            {
                if (intPlayer[2] >21)
                {
                    tmpWinner = Lose();
                }
                else if(intDealer[1] >21)
                {
                    if(intDealer[0] >21)
                    {
                        tmpWinner = Win();
                    }
                    if(intPlayer[2] > 21)
                    {
                        tmpWinner = Lose();
                    }
                    else if(intPlayer[2] == intDealer[0])
                    {
                        tmpWinner = Draw();
                    }
                    else if(intPlayer[2] > intDealer[0])
                    {
                        tmpWinner = Win();
                    }
                    else if(intPlayer[2] < intDealer[0])
                    {
                        tmpWinner = Lose();
                    }
                }
                else if(intPlayer[2] == intDealer[1])
                {
                    tmpWinner = Draw();
                }
                else if(intPlayer[2] > intDealer[1])
                {
                    tmpWinner = Win();
                }
                else if(intPlayer[2] < intDealer[1])
                {
                    tmpWinner = Lose();
                }
            }
            else if(intDealer[1] > 21)
            {
                if (intDealer[1] > 21)
                {
                    tmpWinner = Win();
                }
                else if(intPlayer[3] >21)
                {
                    if(intPlayer[2] > 21)
                    {
                        tmpWinner = Lose();
                    }
                    else if(intDealer[0] > 21)
                    {
                        tmpWinner = Win();
                    }
                    else if(intPlayer[2] == intDealer[0])
                    {
                        tmpWinner = Draw();
                    }
                    else if(intPlayer[2] > intDealer[0])
                    {
                        tmpWinner = Win();
                    }
                    else if(intPlayer[2] < intDealer[0])
                    {
                        tmpWinner = Lose();
                    }
                }
                else if(intPlayer[3] == intDealer[0])
                {
                    tmpWinner = Draw();
                }
                else if(intPlayer[3] < intDealer[0])
                {
                    tmpWinner = Lose();
                }
                else if(intPlayer[3] > intDealer[0])
                {
                    tmpWinner = Win();
                }
            }
            else if(intPlayer[3] == intDealer[1])
            {
                tmpWinner = Draw();
            }
            else if(intPlayer[3] > intDealer[1])
            {
                tmpWinner = Win();
            }
            else if(intPlayer[3] < intDealer[1])
            {
                tmpWinner = Lose();
            }
        
        return tmpWinner;
    }
    
    
   /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *                             Method : Win                                *
    *                               Date : 22/03/2015                         *
    *                            Version : 2.1.1                              *
    *                         Parameters : N/A                                *
    *                            Returns : String                             *
    * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *
    *                              Description                                *
    * ~ Changes Chip total and returns the string to be printed.              *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */    
    public static String Win()
    {
        intPlayer[0] = intPlayer[0] + (intPlayer[1] * 2);
        return (BLUE + "*      " + PURPLE + "~ You win ~" + BLUE + "      *" + RESET);                
    }
    
    
   /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *                             Method : Lose                               *
    *                               Date : 22/03/2015                         *
    *                            Version : 2.1.1                              *
    *                         Parameters : N/A                                *
    *                            Returns : String                             *
    * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *
    *                              Description                                *
    * ~ Changes Chip total and returns the string to be printed.              *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */    
    public static String Lose()
    {
        intPlayer[0] = intPlayer[0] - intPlayer[1];
        return (BLUE + "*  " + PURPLE + "~ The dealer Wins ~" + BLUE + "	*" + RESET);
    }
    
    
   /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *                             Method : Draw                               *
    *                               Date : 22/03/2015                         *
    *                            Version : 2.1.1                              *
    *                         Parameters : N/A                                *
    *                            Returns : String                             *
    * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *
    *                              Description                                *
    * ~ Returns the string to be printed.                                     *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */    
    public static String Draw()
    {
        return (BLUE + "*        " + PURPLE + "~ Draw ~" + BLUE + "      *" + RESET);
    }
    
    
   /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *                             Method : Double                             *
    *                               Date : 22/03/2015                         *
    *                            Version : 2.1.1                              *
    *                         Parameters : N/A                                *
    *                            Returns : N/A                                *
    * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *
    *                              Description                                *
    * ~ Deals the player one card.                                            *               
    * ~ If both of the player card values are the same then just the first is * 
    *   displayed, else both values are displayed.                            *
    * ~ If the player has gone bust the current card values will be           *
    *   displayed along with a bust message, the FinalPlay() method is ran    *
    *   and the method ends.                                                  * 
    * ~ Otherwise the DealerPlay() and FinalPlay() methods are ran.           *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */    
    public static void Double()
    {
        intPlayer[1] = intPlayer[1] * 2;
        DealPlayer();
        String tmpPlayerTotal = intPlayer[2] + "/" + intPlayer[3];
            if (intPlayer[2] == intPlayer[3])
            {
                tmpPlayerTotal = Integer.toString(intPlayer[2]);
            }
            if( intPlayer[2]  > 21 )
            {
                System.out.println(BLUE + "* * * * * * * * * * * * * * * * * * * \n" 
                        + BLUE + "* Dealer: " + PURPLE + strDealer + BLUE + "\tYour Bet: " 
                        + PURPLE + intPlayer[1] + BLUE + "\t    * \n" 
                        + BLUE + "*    You: " + PURPLE + strPlayer + BLUE + "\tTotal: " 
                        + PURPLE + tmpPlayerTotal + BLUE + "   *\n" 
                        + BLUE + "*-----------------------------------* \n" 
                        + BLUE + "*\t  ~ You went bust ~\t    *\n" 
                        + BLUE + "* * * * * * * * * * * * * * * * * * *\n" + RESET);
                FinalPlay(2);
            }
            else
            {
                DealerPlay();
                FinalPlay(0);
            }        
    }
    
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * The Split method was not feasible to fully implement within the given time due to it making everything        *
 * way more complicated and so has been removed, if there are random commented out snippets of code lying around *
 * they're probably connected to this method and were left there in the unlikely event that i'd have enough time *
 * to finish implementing it.                                                                                    *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    /*public static void Split(int Card)    
    {
        //reset player cards to given
        strPlayer = "" + strPlayer.charAt(0) + " ";
        intPlayer[2] = Card;
        intPlayer[3] = Card;
        //set new cards to given
        strSplit = strPlayer;
        intPlayer[4] = Card;
        intPlayer[5] = Card;
        // somehow follow playing each hand with playing the second hand.  
        
    } */
    
    
   /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *                             Method : DealPlayer                         *
    *                               Date : 22/03/2015                         *
    *                            Version : 2.1.5                              *
    *                         Parameters : N/A                                *
    *                            Returns : N/A                                *
    * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *
    *                              Description                                *
    * ~ Generates a random number between 1 and 13.                           *
    * ~ The card value is added to the player card totals (10 for face cards, *
    *   1 & 10 to total 1 and 2 respectively if A)                            *
    * ~ Card number or letter is added to strPlayer.                          *    
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */    
    public static void DealPlayer() 
    {  
        int tmpHold = RndNum.nextInt(13)+1;
        switch (tmpHold)
        {
            case 1: strPlayer = strPlayer + "A ";
                    intPlayer[2] = intPlayer[2] + 1;
                    intPlayer[3] = intPlayer[3] + 11;
                    break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10: strPlayer = strPlayer + tmpHold + " ";
                    intPlayer[2] = intPlayer[2] + tmpHold;
                    intPlayer[3] = (intPlayer[3] + tmpHold);
                    break;
            case 11: strPlayer = strPlayer + "J ";
                    intPlayer[2] = intPlayer[2] + 10;
                    intPlayer[3] = intPlayer[3] + 10;
                    break;
            case 12: strPlayer = strPlayer + "Q ";
                    intPlayer[2] = intPlayer[2] + 10;
                    intPlayer[3] = intPlayer[3] + 10;
                    break;
            case 13: strPlayer = strPlayer + "K ";
                    intPlayer[2] = intPlayer[2] + 10;
                    intPlayer[3] = intPlayer[3] + 10; 
                    break;   
            default: System.out.println(RED + "ERROR: something went wrong again" + RESET);
        }
    }
    
    
   /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *                             Method : DealDealer                         *
    *                               Date : 22/03/2015                         *
    *                            Version : 2.1.5                              *
    *                         Parameters : N/A                                *
    *                            Returns : N/A                                *
    * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *
    *                              Description                                *
    * ~ Generates a random number between 1 and 13.                           *
    * ~ The card value is added to the player card totals (10 for face cards, * 
    *   1 & 10 to total 1 and 2 respectively if A)                            *
    * ~ Card number or letter is added to strDealer.                          *   
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */ 
    //used as a seperate method to DealPlayer because nothing works if i put them together and pass perameters. 
    public static void DealDealer()    {                                /*### seriously, fix this. ###*/
        int tmpHold = RndNum.nextInt(13)+1;
        switch (tmpHold)
        {
            case 1: strDealer = strDealer + "A ";
                    intDealer[0] = intDealer[0] + 1;
                    intDealer[1] = intDealer[1] + 11;
                    break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10: strDealer = strDealer + tmpHold + " ";
                    intDealer[0] = intDealer[0] + tmpHold;
                    intDealer[1] = intDealer[1] + tmpHold;
                    break;
            case 11: strDealer = strDealer + "J ";
                    intDealer[0] = intDealer[0] + 10;
                    intDealer[1] = intDealer[1] + 10;
                    break;
            case 12: strDealer= strDealer + "Q ";
                    intDealer[0] = intDealer[0] + 10;
                    intDealer[1] = intDealer[1] + 10;
                    break;
            case 13: strDealer = strDealer + "K ";
                    intDealer[0] = intDealer[0] + 10;
                    intDealer[1] = intDealer[1] + 10; 
                    break;   
            default: System.out.println(RED + "ERROR: something went wrong again" + RESET);
        }
    }    
    
}