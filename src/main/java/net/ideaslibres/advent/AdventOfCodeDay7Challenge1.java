package net.ideaslibres.advent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AdventOfCodeDay7Challenge1 {

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
		List<Integer> crabPositions = readInput(input);

		int total = crabPositions.stream().reduce(Integer::sum).get();
		int avg = total / crabPositions.size();
		int variance = 0;
		int stdDev = 0;
		for (int i = 0; i < crabPositions.size(); i++) {
			variance += Math.abs(crabPositions.get(i) - avg);
		}
		variance /= crabPositions.size();
		stdDev = (int) Math.sqrt(variance);
		System.out.printf("Total: %d, Avg: %d, Var: %d, StdDev %d\n", total, avg, variance, stdDev);

		int totalFuel = 0;
		int minFuel = Integer.MAX_VALUE;
		int position = -1;
		for (int i = 0; i <= stdDev + 400; i++) {
			if (i == 0) {
				totalFuel = calculateFuel(crabPositions, avg);
				if (totalFuel < minFuel) {
					minFuel = totalFuel;
					position = avg;
				}
			} else {
				totalFuel = calculateFuel(crabPositions, avg + i);
				if (totalFuel < minFuel) {
					minFuel = totalFuel;
					position = avg + i;
				}
				totalFuel = calculateFuel(crabPositions, avg - i);
				if (totalFuel < minFuel) {
					minFuel = totalFuel;
					position = avg - i;
				}
			}
		}

		System.out.printf("Position: %d, Min Fuel: %d\n", position, minFuel);
	}

	private static int calculateFuel(List<Integer> crabPositions, int target) {
		int fuel = crabPositions.stream()
				.map(crab -> Math.abs(crab - target))
				.reduce(Integer::sum).get();

		System.out.printf("Position: %d, Fuel: %d\n", target, fuel);

		return fuel;
	}

	private static List<Integer> readInput(List<String> input) {
		String[] inputValues = input.get(0).split(",");
		List<Integer> crabPositions = new ArrayList<>();
		for (int i = 0; i < inputValues.length; i++) {
			crabPositions.add(Integer.parseInt(inputValues[i].trim()));
		}

		return crabPositions;
	}
}
