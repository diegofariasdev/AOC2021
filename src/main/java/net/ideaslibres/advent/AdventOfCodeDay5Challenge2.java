package net.ideaslibres.advent;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class AdventOfCodeDay5Challenge2 {
	static int max = 0;

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
		List<Line> lines = readPoints(input);
		int[][] plane = drawLines(lines);
		int overlaps = countOverlaps(plane);
		printPlane(plane);
		System.out.println("Overlaps: " + overlaps);
	}

	private static int countOverlaps(int[][] plane) {
		int overlaps = 0;
		for (int i = 0; i < plane.length; i++) {
			for (int j = 0; j < plane.length; j++) {
				if (plane[i][j] > 1) {
					overlaps++;
				}
			}
		}
		return overlaps;
	}

	private static void printPlane(int[][] plane) {
		for (int i = 0; i < plane.length; i++) {
			for (int j = 0; j < plane.length; j++) {
				System.out.print(plane[i][j] + " ");
			}
			System.out.println();
		}
	}

	private static int[][] drawLines(List<Line> lines) {
		int[][] plane = new int[AdventOfCodeDay5Challenge2.max + 1][AdventOfCodeDay5Challenge2.max + 1];
		for (Line line : lines) {
			List<Point> points = line.getPoints();
			drawPoints(points, plane);
		}
		return plane;
	}

	private static void drawPoints(List<Point> points, int[][] plane) {
		for (Point point : points) {
			plane[point.y][point.x]++;
		}
	}

	private static List<Line> readPoints(List<String> input) {
		List<Line> lines = new ArrayList<>();
		int max = 0;
		for (int i = 0; i < input.size(); i++) {
			String[] points = input.get(i).split("->");
			String[] point1String = points[0].split(",");
			String[] point2String = points[1].split(",");
			Point point1 = new Point(Integer.parseInt(point1String[0].trim()), Integer.parseInt(point1String[1].trim()));
			Point point2 = new Point(Integer.parseInt(point2String[0].trim()), Integer.parseInt(point2String[1].trim()));
			lines.add(new Line(point1, point2));
			if (point1.x > max) max = point1.x;
			if (point1.y > max) max = point1.y;
			if (point2.x > max) max = point2.x;
			if (point2.y > max) max = point2.y;
		}
		AdventOfCodeDay5Challenge2.max = max;
		return lines;
	}

	static class Line {
		Point start;
		Point end;
		List<Point> points;

		Line(Point start, Point end) {
			this.start = start;
			this.end = end;
		}

		public List<Point> getPoints() {
			if (points != null) return points;
			points = new ArrayList<>();
			if (start.x == end.x) {
				int minY = Math.min(start.y, end.y);
				int maxY = Math.max(start.y, end.y);
				for (int y = minY; y <= maxY; y++) {
					points.add(new Point(start.x, y));
				}
			} else if (start.y == end.y) {
				int minX = Math.min(start.x, end.x);
				int maxX = Math.max(start.x, end.x);
				for (int x = minX; x <= maxX; x++) {
					points.add(new Point(x, start.y));
				}
			} else {
				int m = (end.y - start.y) / (end.x - start.x);
				int minX = Math.min(start.x, end.x);
				int maxX = Math.max(start.x, end.x);
				for (int x = minX; x <= maxX; x++) {
					int y = m * (x - start.x) + start.y;
					points.add(new Point(x, y));
				}
			}
			return points;
		}
	}
}
