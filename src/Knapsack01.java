import java.util.*;
import java.io.*;

public class Knapsack01 {

    private static int capacity;                                            //Declare variable Capacity
    private static Scanner scan = new Scanner(System.in);                   //Declare Scanner object
    private static ArrayList<Object01> objArr = new ArrayList<>();          //Declare Array list of Objects called objArr

    public static void main(String[] args){                                 //Create Main class
        System.out.println("Fractional Knapsack");                          //Print Introduction
        System.out.println("How much weight can the knapsack hold?");       //Request capacity of knapsack
        capacity = scan.nextInt();                                          //Assign capacity to global variable

        input();                                                            //Run input method
        objArr.sort(Comparator.comparing(Object01::getWeight));             //Sort Array list
        solve();                                                            //Run solve method
    }

    private static void solve() {                                           //Create method solve
        ArrayList<Object01> optimal = new ArrayList<>();                    //Create Array list optimal

        double[][] matrix = new double[objArr.size()+1][capacity+1];        //Create 2d matrix
        for(int i = 0; i <= capacity; i++){                                 //Start for loop
            matrix[0][i] = 0;                                               //Set row 0 = 0
        }
        for(int i = 0; i <= objArr.size(); i++){                            //Start for loop
            matrix[i][0] = 0;                                               //Set column 0 = 0
        }

        for(int j = 1; j <= objArr.size(); j++){                            //Start nested for loop
            for(int k = 1; k <= capacity; k++){                             //Continue nested for loop
                double tempWeight = objArr.get(j-1).getWeight();            //Set tempweight = objArr[j-1].weight
                double tempValue = objArr.get(j-1).getValue();              //Set tempvalue = objArr[j-1].value
                double takeCurrent;                                         //Create takecurrent
                double skipCurrent;                                         //Create skipcurrent

                if(k-tempWeight < 0) {                                      //Start if statement
                    matrix[j][k] = matrix[j-1][k];                          //Set matrix[j][k] = to the element above it
                } else{                                                     //Create else statement
                    takeCurrent = tempValue + matrix[j-1][(int) (k - tempWeight)];//Calculate option if current object is taken
                    skipCurrent = matrix[j-1][k];                           //Calculate option
                    matrix[j][k] = Math.max(skipCurrent, takeCurrent);      //Determine max of the two
                }
            }
        }

        int j = objArr.size();                                              //Create int j = to objArr size
        int k = capacity;                                                   //Create int k = capacity
        while(true){                                                        //Start while loop
            double temp = matrix[j][k];                                     //Create temp = matrix[j][k]
            if(j == 0 || k == 0) {                                          //If j&k = 0, matix is empty
                break;                                                      //Ext loop
            }
            if(k < objArr.get(j-1).getWeight()){                            //If cpacity is less than weight above,
                j--;                                                        //Decrement i
                continue;                                                   //Skip to next iteration
            }
            if(temp == matrix[j-1][k]){                                     //if current matrix element is equal to the one above
                j--;                                                        //Decrement column
                continue;                                                   //Skip to next iteration
            }
            if(temp != matrix[j-1][k]){                                     //If temp is not equal to element above
                optimal.add(objArr.get(j-1));                               //Add object above to optimal
                j--;                                                        //Decrement column
                k -= objArr.get(j).getWeight();                             //Decrement available capacity
            }
        }
        print(optimal);                                                     //Print optimal
    }

    private static void print(ArrayList<Object01> optimal) {                //Create method print
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

        Object01 blah = new Object01(tempWgt, tempVal);                     //Create temporary object with given value and weight
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
                Object01 blah = new Object01(tempWgt, tempVal);             //Create new object with given values
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

class Object01{                                                             //Create class ObjectFrac

    private double value;                                                   //Create private double value
    private double weight;                                                  //Create private double value per weight

    Object01(double weight, double value){                                //Constructor
        this.value = value;                                                 //Assign given value to value
        this.weight = weight;                                               //Calculate value per weight
    }

    public double getValue(){                                               //Create getter method for value
        return value;                                                       //return value
    }

    public double getWeight(){                                              //Create getter method for weight
        return weight;                                                      //return weight
    }
}

