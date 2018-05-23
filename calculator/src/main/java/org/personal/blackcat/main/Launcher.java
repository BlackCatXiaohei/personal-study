package org.personal.blackcat.main;

import org.personal.blackcat.model.TDDepartment;
import org.personal.blackcat.service.CalculatorService;

import java.util.Scanner;

/**
 * @author lifan.li
 * @since 2018/5/21
 **/
public class Launcher {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please write file path in terminal:");
        String next = scanner.next();
        System.out.println("calculating, Please wait...");
        CalculatorService service = new CalculatorService();
        TDDepartment tdDepartment = service.leastSquares(next);
        System.out.println("a="+tdDepartment.getX()+", b="+tdDepartment.getY());
    }
}
