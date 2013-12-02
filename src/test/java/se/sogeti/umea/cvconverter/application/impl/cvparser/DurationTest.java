package se.sogeti.umea.cvconverter.application.impl.cvparser;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

public class DurationTest {

	/**
	 * Test that durations are parsed correctly.
	 * 
	 * 2012-08 - 2012-12 4 2009-01 - 2011-04 27
	 * 
	 * @throws java.text.ParseException
	 */
	@Test
	public void testSameYearDuration() throws ParseException {
		String fourMonths = "2012-08 - 2012-12";
		Duration fourMonthsDuration = new Duration(fourMonths);
		Assert.assertEquals("2012", fourMonthsDuration.getDurationYearString());
	}	

	@Test
	public void testMonthDuration() throws ParseException {
		String fourMonths = "2012-08 - 2012-12";
		Duration fourMonthsDuration = new Duration(fourMonths);
		Assert.assertEquals(4, fourMonthsDuration.getDurationMonths());
		String twentySevenMonths = "2009-01 - 2011-04";
		Duration twentySevenMonthsDuration = new Duration(twentySevenMonths);
		Assert.assertEquals(27, twentySevenMonthsDuration.getDurationMonths());
	}
	
	@Test
	public void testDurationWithNoStop() throws ParseException {
		String current = "2012-10";
		Duration currentDuration = new Duration(current);
		Assert.assertEquals(0, currentDuration.getDurationMonths());
		Assert.assertEquals("2012", currentDuration.getDurationYearString());
	}
	
}
