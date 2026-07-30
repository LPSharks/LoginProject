/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.letagost10510187.icetask1;

/**
 *
 * @author prais
 */
public class Icetask1 {

    public static void main(String[] args) {
        String[]employeeNumes={"101111","101122","101133","101144","101155"};
        String[]sales={"SALES 1","SALES 2","SALES 3"};
        int[][]salePrice={{3000,2000,3500},{2500,5500,3500},{1100,2000,4500},{1700,2700,2500},{5000,2900,5900}};
        
        System.out.println("**********************************************************");
        System.out.println("EMPLOYEE SALES REPORT");
        System.out.println("**********************************************************");
        
        for (int c = 0; c <sales.length; c++) {
            System.out.print("\t\t"+sales[c]);
        }
        System.out.println("");
        for (int a = 0; a < employeeNumes.length; a++) {
            System.out.print(" "+employeeNumes[a]+"-->"+"\t");
            for (int c = 0; c < salePrice[c].length; c++) {
                System.out.print("R "+salePrice[a][c]+"\t\t");
            }
            System.out.println("");
        }
        
        System.out.println("**********************************************************");
        System.out.println("EMPLOYEE TOTAL SALES");
        System.out.println("**********************************************************");
        
        for (int a = 0; a < employeeNumes.length; a++) {
            double total=0;
            System.out.print(" "+employeeNumes[a]+"-->"+"\t");
            for (int c = 0; c < salePrice[c].length; c++) {
                total+=salePrice[a][c];
            }
            System.out.println("R "+total);
        }
    }
}
