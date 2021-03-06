package de.uni_koeln.spinfo.textengineering.ir.basic;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestBasicIR {

	private static Corpus corpus;
	private LinearSearch s;

	@BeforeClass
	public static void setUp() {
		// Korpus einlesen und in Werke unterteilen:
		String filename = "pg100.txt";
		String delimiter = "1[56][0-9]{2}\n";
		corpus = new Corpus(filename, delimiter);
	}

	@Test
	public void testCorpus() {
		// Testen, ob Korpus korrekt angelegt wurde:
		List<Work> works = corpus.getWorks();
		assertTrue("Korpus sollte mehr als 1 Werk enthalten", works.size() >= 1);
	}

	@Test
	public void testSearch() throws Exception {
		
		s = new LinearSearch(corpus);
		String query = "Brutus Caesar";
		
		Set<Integer> result = s.search(query);
		assertTrue("Result sollte nicht leer sein, ist es aber", result.size() > 0);
		System.out.println(result);

		// Minimale Ergebnisaufbereitung
		List<Work> works = corpus.getWorks();
		for (Integer id : result) {
			System.out.println(works.get(id).getTitle());
		}
	}
}