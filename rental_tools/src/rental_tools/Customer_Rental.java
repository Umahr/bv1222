package rental_tools;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

/**
 * This program lets a user rent a tool and outputs the rental agreement
 * 
 * @author Bradley Vogt
 *
 */
public class Customer_Rental {

	public static void main(String[] args) {
		Map<String, Hardware> validTools = new HashMap<String, Hardware>();
		Hardware currTool = null;

		validTools.put("CHNS", new Hardware("CHNS", "Chainsaw", "Stihl", BigDecimal.valueOf(1.49), true, false, true));
		validTools.put("LADW", new Hardware("LADW", "Ladder", "Werner", BigDecimal.valueOf(1.99), true, true, false));
		validTools.put("JAKD", new Hardware("JAKD", "Jackhammer", "DeWalt", BigDecimal.valueOf(2.99), true, false, false));
		validTools.put("JAKR", new Hardware("JAKR", "Jackhammer", "Ridgid", BigDecimal.valueOf(2.99), true, false, false));

		System.out.println("Tools \'R Us Rental Agreement Tool");
		System.out.println("------------------------------------------------");

		// tool code
		// I am going to make the assumption that tool code is case sensitive for future tool inventory.
		System.out.println("Enter the number tool code you are renting: ");
		String toolCode = null;
		Scanner scanner = new Scanner(System.in);
		boolean success = false;
		while (!success) {
			toolCode = scanner.nextLine();
			if (validTools.containsKey(toolCode)) {
				currTool = validTools.get(toolCode);
				success = true;
			} else {
				System.out.println("Must enter a valid tool code.");
			}
		}

		String scanInput;
		
		// Rental Days
		success = false;
		while (!success) {
			System.out.println("Enter the number of rental days: ");
			try {
				scanInput = scanner.nextLine();
				currTool.setRentalDays(scanInput);
				success = true;
			} catch (Exception ex) {
				System.out.println("Tools must be rented for one or more days.");
			}
		}

		// Discount Percentage
		success = false;
		while (!success) {
			System.out.println("Enter the discount percentage (1-100): ");
			try {
				scanInput = scanner.nextLine();
				currTool.setDiscount(scanInput);
				success = true;
			} catch (Exception ex) {
				System.out.println("Discount must be a whole number in the range of 0-100.");
			}
		}

		// Checkout Date
		success = false;
		while (!success) {
			System.out.println("Enter the check out date (mm/dd/yy): ");
			try {
				scanInput = scanner.nextLine();
				currTool.setCheckout(scanInput);
				success = true;
			} catch (Exception ex) {
				System.out.println("Checkout must be a valid date in mm/dd/yy format.");
			}
		}
		scanner.close();

		try {
			currTool.calculateTotal();
			currTool.outputAgreement();
		} catch (Exception ex) {
			System.out.println("Somethign went wrong in agreement processing.");
		}

	}
}
