package de.uni_koeln.spinfo.textengineering.ir.basic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LinearSearch {

	private List<Work> works;

	public LinearSearch(Corpus corpus) {
		works = corpus.getWorks();
	}

	public Set<Integer> search(String query) {

		long start = System.currentTimeMillis();
		Set<Integer> result = new HashSet<>();

		for (Work work : works) {
			String text = work.getText();
			List<String> tokens = Arrays.asList(text.split("\\s+"));
			for (String t : tokens) {
				if (t.compareTo(query) == 0) {
					result.add(works.indexOf(work));
					break;
				}
			}
		}
		System.out.println("Dauer: " + (System.currentTimeMillis() - start) + " ms.");
		return result;
	}

}
