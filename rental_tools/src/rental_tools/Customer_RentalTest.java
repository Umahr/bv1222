package rental_tools;

import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * @author Bradley Vogt
 *
 */
class Customer_RentalTest {

	@Test
	final void throwWhenDiscountGreaterThan100() {
		//test 1 from doc
		var tool = new Hardware("JAKR", "Jackhammer", "Ridgid", BigDecimal.valueOf(2.99), true, false, false);
		Assert.assertThrows(IllegalArgumentException.class, () -> { tool.setDiscount("101"); } );
	}
	
	@Test
	final void july4HolidayFreeWeekendCharge() {
		//test 2 from doc
		var tool = new Hardware("LADW", "Ladder", "Werner", BigDecimal.valueOf(1.99), true, true, false);
		tool.setRentalDays("3");
		tool.setDiscount("10");
		tool.setCheckout("7/2/20");
		Assert.assertEquals(BigDecimal.valueOf(3.58), tool.calculateTotal());
	}
	
	@Test
	final void july4HolidayCharge() {
		//test 3 from doc
		var tool = new Hardware("CHNS", "Chainsaw", "Stihl", BigDecimal.valueOf(1.49), true, false, true);
		tool.setRentalDays("5");
		tool.setDiscount("25");
		tool.setCheckout("7/2/15");
		Assert.assertEquals(BigDecimal.valueOf(3.35), tool.calculateTotal());
	}
	
	
	@Test
	final void laborDayFree() {
		//test 4 from doc
		var tool = new Hardware("JAKD", "Jackhammer", "DeWalt", BigDecimal.valueOf(2.99), true, false, false);
		tool.setRentalDays("6");
		tool.setDiscount("0");
		tool.setCheckout("9/3/15");
		Assert.assertEquals(BigDecimal.valueOf(8.97), tool.calculateTotal());
	}
	
	@Test
	final void july4FreeWeekendFree() {
		//test 5 from doc
		var tool = new Hardware("JAKR", "Jackhammer", "Ridgid", BigDecimal.valueOf(2.99), true, false, false);
		tool.setRentalDays("9");
		tool.setDiscount("0");
		tool.setCheckout("7/2/15");
		Assert.assertEquals(BigDecimal.valueOf(17.94), tool.calculateTotal());
	}
	
	@Test
	final void july4FreeWeekendFreeSecondTest() {
		//test 6 from doc
		var tool = new Hardware("JAKR", "Jackhammer", "Ridgid", BigDecimal.valueOf(2.99), true, false, false);
		tool.setRentalDays("4");
		tool.setDiscount("50");
		tool.setCheckout("7/2/20");
		Assert.assertEquals(BigDecimal.valueOf(1.49), tool.calculateTotal());
	}
	
	@Test
	final void throwWhenDiscountLessThan0() {
		var tool = new Hardware("JAKR", "Jackhammer", "Ridgid", BigDecimal.valueOf(2.99), true, false, false);
		Assert.assertThrows(IllegalArgumentException.class, () -> { tool.setDiscount("-5"); } );
	}
	
	@Test
	final void throwWhenRentalDaysIs0() {
		var tool = new Hardware("JAKR", "Jackhammer", "Ridgid", BigDecimal.valueOf(2.99), true, false, false);
		Assert.assertThrows(IllegalArgumentException.class, () -> { tool.setRentalDays("0");; } );
	}
	
	@Test
	final void throwWhenRentalDaysIsLessThan0() {
		var tool = new Hardware("JAKR", "Jackhammer", "Ridgid", BigDecimal.valueOf(2.99), true, false, false);
		Assert.assertThrows(IllegalArgumentException.class, () -> { tool.setRentalDays("-5");; } );
	}



}
