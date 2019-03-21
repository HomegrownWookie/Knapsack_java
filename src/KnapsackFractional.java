import java.util.*;
import java.io.*;

public class KnapsackFractional {

    private static int capacity;                                            //Declare variable Capacity
    private static Scanner scan = new Scanner(System.in);                   //Declare Scanner object
    private static ArrayList<ObjectFrac> objArr = new ArrayList<>();        //Declare Array list of Objects called objArr

    public static void main(String[] args){                                 //Create Main class
        System.out.println("Fractional Knapsack");                          //Print Introduction
        System.out.println("How much weight can the knapsack hold?");       //Request capacity of knapsack
        capacity = scan.nextInt();                                          //Assign capacity to global variable

        input();                                                            //Run input method
        objArr.sort(Comparator.comparing(ObjectFrac::getValPerWgt));        //Sort Array list
        solve();                                                            //Run solve method
    }

    private static void solve() {                                           //Create solve method
        ArrayList<ObjectFrac> optimal = new ArrayList<>();                  //Create Array list optimal

        int counter = 0;                                                    //Create int counter = 0
        int i = objArr.size() - 1;                                          //Create int i = one less than the total number of Objects

        while(counter <= capacity && i >= 0){                               //Start while loop
            double tempWeight = objArr.get(i).getWeight();                  //Declare double tempWeight with the weight of the object in position i
            if(tempWeight <= (capacity - counter)){                         //Start if statement
                optimal.add(objArr.get(i));                                 //Add object at position i to ArrLst optimal
                counter += tempWeight;                                      //Increment counter by tempWeight
                i--;                                                        //Decrement i
            } else{
                double percentage = (capacity - counter) / tempWeight;      //Declare percentage = to remaining weight / weight of current object
                ObjectFrac partial = new ObjectFrac(percentage * objArr.get(i).getWeight(),
                        percentage * objArr.get(i).getValue());       //Create new object = to remaining part of available capacity
                optimal.add(partial);                                       //Add partial to optimal
                counter = capacity+1;                                       //Set counter to max value to break loop
                i--;                                                        //Decrement i
            }
        }

        print(optimal);                                                     //Print Optimal
    }

    private static void print(ArrayList<ObjectFrac> optimal) {              //Create method print
        double totalValue = 0.0;                                            //Create variable totalValue
        System.out.println("Optimal List:");                                //Print "Optimal List"
        System.out.printf("%s\t%s\t%s\n","Object","Value","Weight");        //Print Header
        for(int i = 0; i < optimal.size(); i ++){                           //Create For Loop
            System.out.printf("%d\t\t%.2f\t%.2f\n",i+1,
                    optimal.get(i).getValue(),optimal.get(i).getWeight());  //Print Contents of optimal array list
            totalValue += optimal.get(i).getValue();                        //Increment total value
        }
        System.out.printf("%s%.2f","Total Value: ", totalValue);            //Print total value
    }

    private static void input() {                                           //Create Method input
        System.out.println("How would you like to input the objects?");     //Print menu
        System.out.println("1. From the terminal");                         //Print menu
        System.out.println("2. From a file");                               //Print menu
        System.out.print("Your choice: ");                                  //Print menu
        int choice = scan.nextInt();                                        //Collect use choice for input

        switch(choice){                                                     //Start switch case function
            case 1:                                                         //Case 1
                readTerminal();                                             //Run method readTerminal
                break;                                                      //Exit Input()
            case 2:                                                         //Case 2
                readFile();                                                 //Run method readFile
                break;                                                      //Exit input()
            default:                                                        //Default case
                System.out.println("Not a valid choice!");                  //Print invalid choice
                input();                                                    //Run input again
                break;                                                      //Exit input()
        }

    }

    private static void readTerminal() {                                    //Create method readTerminal
        System.out.print("Please enter the value of the object: ");         //Print request for value
        int tempVal = scan.nextInt();                                       //Collect value
        System.out.print("Please enter the weight of the object: ");        //Print request for weight
        int tempWgt = scan.nextInt();                                       //Collect weight

        ObjectFrac blah = new ObjectFrac(tempWgt, tempVal);                 //Create temporary object with given value and weight
        objArr.add(blah);                                                   //Add object to objArr

        System.out.print("Would you like to enter another object? (y/n): ");//Ask if more objects to be added
        String restart = scan.next();                                       //Collect choice
        switch (restart.toLowerCase().charAt(0)) {                          //Start switch statement
            case 'y':                                                       //Affirmative case
                readTerminal();                                             //Restart readTerminal()
                break;                                                      //Exit readTerminal()
            default:                                                        //Default case
                break;                                                      //Exit readTerminal()
        }
    }

    private static void readFile() {                                        //Create method Readfile
        System.out.println("What is the full filepath?");                   //Request filepath to testfile
        scan.nextLine();                                                    //Collect empty line
        String filepath = scan.nextLine();                                  //Collect filepath
        String line;                                                        //Create String line

        try {                                                               //Start Try-catch block
            FileReader fileReader = new FileReader(filepath);               //Create filereader
            BufferedReader bufferedReader = new BufferedReader(fileReader); //Create bufferedReader
            while ((line = bufferedReader.readLine()) != null) {            //Start while loop to read file
                double tempVal = Integer.parseInt(line);                    //Read in first number as value
                double tempWgt = Integer.parseInt(bufferedReader.readLine());//Read in second number as weight
                ObjectFrac blah = new ObjectFrac(tempWgt, tempVal);         //Create new object with given values
                objArr.add(blah);                                           //Add new object to objArr

            }
            bufferedReader.close();                                         //Close reader

        } catch(FileNotFoundException ex) {                                 //Catch FileNotFoundException
            System.out.println("Unable to open file.");                     //Print error message

        } catch(IOException ex) {                                           //Catch IOException
            System.out.println("Error reading file.");                      //Print error message
        }
    }
}

class ObjectFrac{                                                           //Create class ObjectFrac

    private double value;                                                   //Create private double value
    private double weight;                                                  //Create private double weight
    private double valPerWgt;                                               //Create private double value per weight

    ObjectFrac(double weight, double value){                                //Constructor
        this.value = value;                                                 //Assign given value to value
        this.weight = weight;                                               //Assign given weight to weight
        this.valPerWgt = value / weight;                                    //Calculate value per weight
    }

    public double getValue(){                                               //Create getter method for value
        return value;                                                       //return value
    }

    public double getWeight(){                                              //Create getter method for weight
        return weight;                                                      //return weight
    }

    public double getValPerWgt(){                                           //Create getter method for value per weight
        return valPerWgt;                                                   //return value per wieght
    }
}
