/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package calculatorswitch;

/**
 *
 * @author prais
 */
public class CalculatorSwitch {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
           
            System.out.println("MAIN MENU ");
            System.out.println("1.Calculator ");
            System.out.println("2. Exit ");
            
            System.out.print("Enter your choice (1-2): ");
            
            int mainChoice = s.nextInt();
            
            switch (mainChoice) {
                case 1:
                    showCalculatorMenu(s);
                    break;
                case 2:
                    System.out.println("\nThank you Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("\n Invalid choice! Please enter 1 or 2.");
            }
        }
        
        s.close();
    }
    
    
    public static void showCalculatorMenu(Scanner s) {
        boolean calcRunning = true;
        
        while (calcRunning) {
            System.out.println("CALCULATOR MENU"); 
            System.out.println("1. Add ");
            System.out.println("2. Subtrac ");
            System.out.println("3. Back to Main Menu ");
            System.out.print("Enter your choice (1-3): ");
            
            int calcChoice = s.nextInt();
            
            switch (calcChoice) {
                case 1:
                    performCalculation(s, "add");
                    break;
                case 2:
                    performCalculation(s, "subtract");
                    break;
                case 3:
                    calcRunning = false;
                    break;
                default:
                    System.out.println("\n Invalid choice! Please enter 1, 2, or 3.");
            }
        }
    }
    
    
    public static void performCalculation(Scanner scanner, String operation) {
        System.out.print("\nEnter first number: ");
        double num1 = scanner.nextDouble();
        
        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();

        Calculator c = new Calculator(num1, num2);
        
        double result;
        String symbol;
        
        if (operation.equals("add")) {
            result = c.add();
            symbol = " + ";
        } else {
            result = c.subtract();
            symbol = " - ";
        }
        System.out.println("  Result: " + num1 + symbol + num2 + " = " + result);
    }
}
