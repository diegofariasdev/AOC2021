package net.ideaslibres.advent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AdventOfCodeDay8Challenge1 {

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
			for (String segment : note.segments) {
				int lenght = segment.length();
				if (lenght == 2 || lenght == 4 || lenght == 3 || lenght == 7) {
					count ++;
				}
			}
		}
		System.out.println(count);
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
