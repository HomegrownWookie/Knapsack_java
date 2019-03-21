import java.util.*;

public class Knapsack {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        Knapsack01 knap01 = new Knapsack01();
        KnapsackFractional knapfrac = new KnapsackFractional();

        System.out.println("Which knapsack would you like to run?");    //Print menu
        System.out.println("1. Fractional Knapsack");                   //Print menu
        System.out.println("2. 0/1 Knapsack");                          //Print menu
        System.out.print("Your choice: ");                              //Print menu
        int choice = scan.nextInt();                                    //Collect use choice for input

        switch(choice){                                                 //Start switch case function
            case 1:                                                     //Case 1
                knapfrac.main(args);                                    // Run method readTerminal
                break;                                                  //Exit Input()
            case 2:                                                     //Case 2
                knap01.main(args);                                      //Run method readFile
                break;                                                  //Exit input()
            default:                                                    //Default case
                System.out.println("Not a valid choice!");              //Print invalid choice
                break;                                                  //Exit input()
        }
    }
}
