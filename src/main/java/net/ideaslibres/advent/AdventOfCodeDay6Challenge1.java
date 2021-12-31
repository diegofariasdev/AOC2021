package net.ideaslibres.advent;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class AdventOfCodeDay6Challenge1 {

	static int daysToRun = 0;

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
		List<LanternFish> lanternFish = readInput(input);
		for (int i = 0 ; i < daysToRun; i++) {
			List<LanternFish> newLanternFish = new ArrayList<>();
			for (LanternFish fish : lanternFish) {
				LanternFish child = fish.decrement();
				if (child != null) {
					newLanternFish.add(child);
				}
			}
			if (!newLanternFish.isEmpty()) {
				lanternFish.addAll(newLanternFish);
			}
			//System.out.println(lanternFish);
		}
		printLanterfishCount(lanternFish);
	}

	private static void printLanterfishCount(List<LanternFish> lanternFish) {
		System.out.println(lanternFish.size());
	}

	private static List<LanternFish> readInput(List<String> input) {
		String[] lanternInitialState = input.get(0).split(",");
		daysToRun = Integer.parseInt(input.get(1));
		List<LanternFish> lanternFish = new ArrayList<>();
		for (String s : lanternInitialState) {
			lanternFish.add(new LanternFish(Integer.parseInt(s)));
		}
		return lanternFish;
	}

	private static class LanternFish {
		int counter;

		public LanternFish() {
			this.counter = 8;
		}

		public LanternFish(int initialState) {
			this.counter = initialState;
		}

		public LanternFish decrement() {
			counter--;
			if (counter < 0) {
				counter = 6;
				return new LanternFish();
			}
			return null;
		}

		public int getCounter() {
			return counter;
		}

		@Override
		public String toString() {
			return counter + "";
		}
	}
}
