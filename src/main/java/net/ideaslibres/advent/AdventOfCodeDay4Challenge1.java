package net.ideaslibres.advent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AdventOfCodeDay4Challenge1 {
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
		String[] cards = input.get(0).trim().split(",");
		List<BoardItem[][]> boards = new ArrayList<>();
		boolean reading = false;
		for (int i = 2; i < input.size(); i += 6) {
			BoardItem[][] board = new BoardItem[5][5];
			for (int j = 0; j < 5; j++) {
				String[] line = input.get(i + j).trim().split(" +");
				for (int k = 0; k < 5; k++) {
					board[j][k] = new BoardItem(line[k]);
				}
			}
			boards.add(board);
		}

		for (int i = 0; i < cards.length; i++) {
			String card = cards[i];
			markCardOnAllBoards(boards, card);
			int winner = findWinner(boards);
			if (winner != -1) {
				calculateScore(boards, winner, card);
				return;
			}
			boards.stream().forEach(board -> System.out.println(Arrays.deepToString(board)));
			System.out.println();
		}
	}

	private static void calculateScore(List<BoardItem[][]> boards, int winner, String card) {
		BoardItem[][] board = boards.get(winner);
		int score = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (!board[i][j].isMarked()) score += Integer.parseInt(board[i][j].value);
			}
		}
		System.out.printf("winner: %s, score: %d\n", Arrays.deepToString(board), score * Integer.parseInt(card));
	}

	private static int findWinner(List<BoardItem[][]> boards) {
		int winner = -1;
		for (int i = 0; i < boards.size(); i++) {
			if (isWinner(boards.get(i))) {
				return i;
			}
		}
		return -1;
	}

	private static boolean isWinner(BoardItem[][] boardItems) {
		for (int i = 0; i < 5; i++) {
			if (isRowWinner(boardItems[i]) || isColumnWinner(boardItems, i)) return true;
		}
		return false;
	}

	private static boolean isColumnWinner(BoardItem[][] boardItems, int i) {
		for (int j = 0; j < 5; j++) {
			if (!boardItems[j][i].isMarked()) return false;
		}
		return true;
	}

	private static boolean isRowWinner(BoardItem[] boardItem) {
		for (BoardItem item : boardItem) {
			if (!item.isMarked()) return false;
		}
		return true;
	}

	private static void markCardOnAllBoards(List<BoardItem[][]> boards, String card) {
		for (BoardItem[][] board : boards) {
			markCardOnBoard(board, card);
		}
	}

	private static void markCardOnBoard(BoardItem[][] board, String card) {
		for (BoardItem[] row : board) {
			for (BoardItem item : row) {
				if (item.value.equals(card)) item.mark();
			}
		}

	}

	static class BoardItem {
		public String value;
		public boolean marked;

		public BoardItem(String value) {
			this.value = value;
		}

		public void mark() {
			this.marked = true;
		}

		public boolean isMarked() {
			return marked;
		}

		@Override
		public String toString() {
			return String.format("%s%s", value, marked ? "*" : "");
		}
	}
}
