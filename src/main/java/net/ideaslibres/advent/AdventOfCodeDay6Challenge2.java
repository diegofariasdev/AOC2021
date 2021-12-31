package net.ideaslibres.advent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AdventOfCodeDay6Challenge2 {

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
		List<Long> fishBuckets = readInput(input);
		for (int i = 0 ; i < daysToRun; i++) {
			Long newFish = fishBuckets.get(0);
			fishBuckets.remove(0);
			fishBuckets.set(6, fishBuckets.get(6) + newFish);
			fishBuckets.add(newFish);
			System.out.println(fishBuckets);
		}
		printLanterfishCount(fishBuckets);
	}

	private static void printLanterfishCount(List<Long> lanternFish) {
		System.out.println(lanternFish.stream().reduce(Math::addExact).get());
	}

	private static List<Long> readInput(List<String> input) {
		String[] lanternInitialState = input.get(0).split(",");
		daysToRun = Integer.parseInt(input.get(1));
		List<Long> fishBuckets = new ArrayList<>(Arrays.asList(0l,0l,0l,0l,0l,0l,0l,0l,0l));
		for (String s : lanternInitialState) {
			int index =Integer.parseInt(s.trim());
			fishBuckets.set(index, fishBuckets.get(index) + 1);
		}

		return fishBuckets;
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
