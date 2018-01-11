//package week1.xml_inject;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;

//import week1.xml_inject.inputValidation.DefendbyWhitelisting;
//import week1.xml_inject.sample.Attack;
//import week1.xml_inject.sample.Item;
//import week1.xml_inject.sample.StaXParser;
//import week1.xml_inject.sample.XMLAttack;
//import week1.xml_inject.schema.CustomResolver;
/**
 * 
 * @author Fan Hu
 * @function This is for XML injection:
 * 				Test.java is the entry of this program, you can choose to run "injection" or two
 * 				kinds of defending methods.
 *
 */
public class Test {
	String OUTPUT_FILE = "test_for_xml_injection.xml";
	Attack attack;
	
	String dangerousCommand="123</pin><securitycode>fakeCode</securitycode><pin>123";
	String safeCommand="1";
	
	public static void main(String[] args) {
		Test test=new Test();
		test.run();

	}
	
	public void run() {
		OutputStream out = null;
		try {
			out = new FileOutputStream(OUTPUT_FILE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedOutputStream outStream = new BufferedOutputStream(out);

		System.out.println("Please input the Demo that you want to try");
		System.out.println("1. Noncompliant Code");
		System.out.println("2. Whitelisting Solution");
		System.out.println("3. Schema Solution");
		Scanner scanner = new Scanner(System.in);
		int choose = scanner.nextInt();
		switch (choose) {
		case 1:
			attack=new XMLAttack();
			try {
				attack.attack(outStream,dangerousCommand);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			attack=new DefendbyWhitelisting();
			try {
				attack.attack(outStream,dangerousCommand);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			attack=new CustomResolver();
			try {
				attack.attack(outStream,dangerousCommand);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 99:
			System.out.println("Exiting");
			break;
		default:
			System.out.println("NO INPUT");
			//break;
		}
		
		printResult();
		//scanner.close();
	}
	
	public void printResult(){
		StaXParser parser = new StaXParser();
		List<PassCode> readConfig = parser.readConfig(OUTPUT_FILE);
		for (PassCode code : readConfig) {
            System.out.println(code);
    }
		System.out.println("Done");
	}
	
}
