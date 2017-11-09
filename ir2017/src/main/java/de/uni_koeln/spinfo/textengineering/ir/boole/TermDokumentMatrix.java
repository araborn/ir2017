package de.uni_koeln.spinfo.textengineering.ir.boole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.uni_koeln.spinfo.textengineering.ir.basic.Corpus;
import de.uni_koeln.spinfo.textengineering.ir.basic.Work;

public class TermDokumentMatrix {

	private boolean[][] matrix;
	private List<String> terms;
	private Map<String, Integer> positions;

	public TermDokumentMatrix(Corpus corpus) {

		long start = System.currentTimeMillis();
		System.out.println("Erstelle Matrix ...");
		List<Work> works = corpus.getWorks();
		terms = getTerms(works);
		positions = getPositions(terms);// 'Zeilennummern' der Terme
		matrix = new boolean[terms.size()][works.size()];

		for (int spalte = 0; spalte < works.size(); spalte++) {
			String[] tokens = works.get(spalte).getText().split("\\P{L}+");
			for (int j = 0; j < tokens.length; j++) {
				String t = tokens[j];// das aktuelle Token
				int zeile = positions.get(t);// Zeilennummer des Tokens
				matrix[zeile][spalte] = true;
			}
		}
		System.out.println("Matrix erstellt, Dauer: " + (System.currentTimeMillis() - start) + " ms.");
		System.out.println("Größe der Matrix: " + terms.size() + " X " + works.size());
	}

	/*
	 * Legt die 'Zeilennummern' der Terme in eine Map (für schnellen Zugriff).
	 */
	private Map<String, Integer> getPositions(List<String> terms) {
		Map<String, Integer> pos = new HashMap<String, Integer>();
		for (int i = 0; i < terms.size(); i++) {
			pos.put(terms.get(i), i);
		}
		return pos;
	}

	/*
	 * Ermittelt die Terme aller Werke. Das Set wird abschließend in eine Liste umgewandelt, da der Listen-Zugriff über
	 * get(index) sowohl das Mappen der Positionen als auch das Ausgeben der Matrix erleichtert.
	 */
	private List<String> getTerms(List<Work> works) {
		Set<String> allTerms = new HashSet<String>();
		for (Work work : works) {
			List<String> termsInCurrentWork = Arrays.asList(work.getText().split("\\P{L}+"));
			allTerms.addAll(termsInCurrentWork);
		}
		return new ArrayList<String>(allTerms);
	}

	/*
	 * Optionale Ausgabe der Matrix
	 */
	public void printMatrix() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print((matrix[i][j]) ? "1 " : "0 ");
			}
			System.out.println(terms.get(i) + " ");
		}
	}

	/*
	 * Die eigentliche Suche.
	 */
	public Set<Integer> search(String query) {

		long start = System.currentTimeMillis();
		Set<Integer> result = new HashSet<Integer>();
		List<String> queries = Arrays.asList(query.split("\\s+"));

		for (String q : queries) {
			// erstmal die Zeile ermitteln:
			Integer zeile = positions.get(q);
			// ... und dann Spalte für Spalte (Werk für Werk) nachsehen:
			for (int i = 0; i < matrix[0].length; i++) {
				// die boolesche Matrix enthält ein 'true' für jeden Treffer:
				if (matrix[zeile][i]) {
					result.add(i);
					/*
					 * Hier behandeln wir die Suchwörter noch immer als ODER-verknüpft! 
					 * TODO UND-Verknüpfung?
					 * 
					 */
				}
			}
		}
		System.out.println("Suchdauer: " + (System.currentTimeMillis() - start) + " ms.");
		return result;
	}

}
