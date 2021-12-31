package net.ideaslibres.advent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public abstract class AdventOfCodeDay8Challenge2 {

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		List<String> input = new ArrayList<>();
		int emptyLines = 0;
		do {
			try {
				String line = br.readLine();
				input.add(line);

				if (line.isEmpty()) emptyLines++;
				else emptyLines = 0;

			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (emptyLines <= 1);

		executeChallenge(input.subList(0, input.size() - 2));
	}

	static void executeChallenge(List<String> input) {
		List<Note> notes = readInput(input);
		int count = 0;
		for (Note note : notes) {
			int displayedNumber = 0;
			for (String segment : note.segments) {
				int lenght = segment.length();
				if (lenght == 2) {
					displayedNumber = appendDigit(displayedNumber, 1);
					continue;
				}
				if (lenght == 4) {
					displayedNumber = appendDigit(displayedNumber, 4);
					continue;
				}
				if (lenght == 3) {
					displayedNumber = appendDigit(displayedNumber, 7);
					continue;
				}
				if (lenght == 7) {
					displayedNumber = appendDigit(displayedNumber, 8);
					continue;
				}
			}
			System.out.println(displayedNumber);
			count += displayedNumber;
		}
		System.out.println(count);
	}

	private static int appendDigit(int displayedNumber, int i) {
		System.out.println(displayedNumber + " " + i);
		displayedNumber *= 10;
		displayedNumber += i;
		return displayedNumber;
	}

	private static List<Note> readInput(List<String> input) {
		List<Note> notes = new ArrayList<>();
		for (String line : input) {
			String[] inputLine = line.split("\\|");
			Note note = new Note();
			String[] segments = inputLine[1].trim().split(" ");
			for (String segment : segments) {
				note.addSegment(segment.trim());
			}
			notes.add(note);
		}

		return notes;
	}

	static class Note {
		List<String> wires;
		List<String> segments;

		public void addSegment(String segment) {
			if (segments == null) {segments = new ArrayList<>();}
			segments.add(segment);
		}
	}
}
