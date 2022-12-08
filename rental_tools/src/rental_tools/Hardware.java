package rental_tools;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.*;
import java.util.EnumSet;

/**
 * @author Bradley Vogt
 */
public class Hardware {
	private String code, type, brand;
	private int rentalDays = 0, chargeableDays = 0, discount = 0;
	private BigDecimal chargeRate = BigDecimal.ZERO, totalCharge = BigDecimal.ZERO, 
					   discountTotal = BigDecimal.ZERO, finalCharge = BigDecimal.ZERO;
	private boolean weekdayCharge, weekendCharge, holidayCharge;
	private LocalDate checkOut, dueDate;
	
	
	//Set the initial object values
	public Hardware(String toolCode, String toolType, String toolBrand, BigDecimal toolCharge, boolean weekday, boolean weekend, boolean holiday) {
		code = toolCode;
		type = toolType;
		brand = toolBrand;
		chargeRate = toolCharge;
		weekdayCharge = weekday;
		weekendCharge = weekend;
		holidayCharge = holiday;		
	}
	
	public void setRentalDays(String days) {
		int rentDays = Integer.parseInt(days);
		if(rentDays > 0) {
			rentalDays = rentDays;
		} else { 
			throw new IllegalArgumentException();
		}
	}
	
	public int getRentalDays() {
		return rentalDays;
	}
	
	public void setDiscount(String percent) {
		int percentage = Integer.parseInt(percent);
		if(percentage >= 0 && percentage <= 100) {
			discount = percentage;
		} else { 
			throw new IllegalArgumentException();
		}
	}
	
	public int getDiscount() {
		return discount;
	}
	
	public void setCheckout(String start) {
		String[] inputDate = start.split("/");
		inputDate[2] = "20" + inputDate[2];
		checkOut = LocalDate.of(Integer.parseInt(inputDate[2]), Integer.parseInt(inputDate[0]), Integer.parseInt(inputDate[1]));
	}
	
	public LocalDate getCheckout() {
		return checkOut;
	}
	
	public BigDecimal calculateTotal() {

		EnumSet<DayOfWeek> weekend  = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
		LocalDate tempDate = checkOut;
		dueDate = checkOut.plusDays(rentalDays-1);
		

		
		while(tempDate.isBefore(dueDate) || tempDate.isEqual(dueDate)) {
			//if it is labor day, we will skip this day if we dont charge holidays
			if(!holidayCharge &&tempDate.getMonth() == Month.SEPTEMBER && tempDate.getDayOfWeek() == DayOfWeek.MONDAY && tempDate.getDayOfMonth() <= 7) {
				tempDate = tempDate.plusDays(1);
				continue;
			}
			
			if(weekendCharge && weekend.contains(tempDate.getDayOfWeek())) {
				totalCharge = totalCharge.add(chargeRate);
				chargeableDays++;
			} else if (weekdayCharge && !weekend.contains(tempDate.getDayOfWeek())){
				//if it is july 4th and we dont charge for holidays then continue on. separated out this if statement for readability
				if(tempDate.getMonth() == Month.JULY) {
					if(tempDate.getDayOfMonth() == 3 && tempDate.getDayOfWeek() == DayOfWeek.FRIDAY && !holidayCharge) {
						tempDate = tempDate.plusDays(1);
						continue;
					} else if(tempDate.getDayOfMonth() == 5 && tempDate.getDayOfWeek() == DayOfWeek.MONDAY && !holidayCharge) {
						tempDate = tempDate.plusDays(1);
						continue;
					} else if(tempDate.getDayOfMonth() == 4 && !holidayCharge) {
						tempDate = tempDate.plusDays(1);
						continue;
					}
				}
				
				
				totalCharge = totalCharge.add(chargeRate);
				chargeableDays++;
			}
			tempDate = tempDate.plusDays(1);
		}
		
		/*
		 * This discount calculates the discount amount then subtracts it from the total. This
		 * puts the rounding on the discount amount instead of the total. This can cause a slight difference
		 * compared to doing total * (1-discount) with the discount already being a decimal
		 * 
		 */
		BigDecimal discountPercent  = new BigDecimal(discount);
		discountTotal = totalCharge.multiply(discountPercent.divide(BigDecimal.valueOf(100)));
		discountTotal = discountTotal.setScale(2, RoundingMode.HALF_UP);
		
		finalCharge = totalCharge.subtract(discountTotal);
		finalCharge = finalCharge.setScale(2, RoundingMode.HALF_UP);
		
		return finalCharge;
	}
	
	private String formatDate(LocalDate date) {
		String year = Integer.toString(date.getYear());
		String month = Integer.toString(date.getMonthValue());
		String day = Integer.toString(date.getDayOfMonth());
		year = year.substring(2);
		return month + "/" + day + "/" + year;
	}
	
	public void outputAgreement() {
		DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");
		
		System.out.println("Tool code: " + code);
		System.out.println("Tool type: " + type);
		System.out.println("Tool brand: " + brand);
		System.out.println("Rental days: " + rentalDays);
		System.out.println("Check out date: " + formatDate(checkOut));
		System.out.println("Due date: " + formatDate(dueDate));
		System.out.println("Daily rental charge: $" + moneyFormat.format(chargeRate));
		System.out.println("Charge days: " + chargeableDays);
		System.out.println("Pre-discount charge: $" + moneyFormat.format(totalCharge));
		System.out.println("Discount percent: " + discount + "%");
		System.out.println("Discount amount: $" + moneyFormat.format(discountTotal));
		System.out.println("Final Charge: $" + moneyFormat.format(finalCharge));		
	}
	
}


