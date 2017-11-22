/**
 * 
 */
package de.uni_koeln.spinfo.textengineering.ir.preprocess;

import java.util.List;

import org.junit.Test;

/**
 * @author spinfo
 *
 */
public class TestPreprocessing {

	private String testString = 
			  "Meine Mail: claesn@spinfo.uni-koeln.de "
			+ "Meine Telefonnummer: 0221-4706675" 
			+ "Kann man auch so schreiben: 470 6675"
			+ "Meine Raumnummer: 3.014"
			+ "Dort bin ich zwischen 8.30 und 17 Uhr.";

	@Test
	public void testPreprocessing() {
		System.out.println("Test Tokenizer:");
		System.out.println("-------------------");

		Preprocessor p = new Preprocessor();
		List<String> tokens = p.tokenize(testString);
		List<String> terms = p.getTerms(testString);
		List<String> stems = p.getStems(testString);
		
		// TODO was wären hier gute Assertions ?

		System.out.println("tokens:\t"+tokens);
		System.out.println("terms:\t"+terms);
		System.out.println("stems:\t"+stems);
	}

}
