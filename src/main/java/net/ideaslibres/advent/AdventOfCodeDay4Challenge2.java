package net.ideaslibres.advent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AdventOfCodeDay4Challenge2 {
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
		List<Board> boards = new ArrayList<>();
		for (int i = 2; i < input.size(); i += 6) {
			BoardItem[][] board = new BoardItem[5][5];
			for (int j = 0; j < 5; j++) {
				String[] line = input.get(i + j).trim().split(" +");
				for (int k = 0; k < 5; k++) {
					board[j][k] = new BoardItem(line[k]);
				}
			}
			boards.add(new Board(board));
		}

		for (int i = 0; i < cards.length; i++) {
			String card = cards[i];
			markCardOnAllBoards(boards, card);
			int loser = getLastLoser(boards);
			if (loser != -1) {
				calculateScore(boards.get(loser), cards, i++);
				return;
			}
			boards.stream().forEach(board -> System.out.println(Arrays.deepToString(board.board)));
			System.out.println();
		}
	}

	private static void calculateScore(Board board, String[] cards, int indexFrom) {
		do {
			markCardOnBoard(board, cards[indexFrom]);
			indexFrom++;
		} while (!isWinner(board));

		int score = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (!board.board[i][j].isMarked()) score += Integer.parseInt(board.board[i][j].value);
			}
		}
		System.out.printf("loser: %s, score: %d\n", Arrays.deepToString(board.board), score * Integer.parseInt(cards[--indexFrom]));
	}

	private static int getLastLoser(List<Board> boards) {
		int loserCount = 0;
		int loser = 0;
		for (int i = 0; i < boards.size(); i++) {
			if (!isWinner(boards.get(i))) {
				loserCount++;
				loser = i;
			}
		}
		return loserCount == 1 ? loser : -1;
	}

	private static boolean isWinner(Board board) {
		if (board.isWinner) return true;
		for (int i = 0; i < 5; i++) {
			if (isRowWinner(board.board[i]) || isColumnWinner(board.board, i)) {
				System.out.println("winner: " + Arrays.deepToString(board.board));
				board.isWinner = true;
				return true;
			}
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

	private static void markCardOnAllBoards(List<Board> boards, String card) {
		System.out.println("card: " + card);
		for (Board board : boards) {
			markCardOnBoard(board, card);
		}
	}

	private static void markCardOnBoard(Board board, String card) {
		for (BoardItem[] row : board.board) {
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

	static class Board {
		public BoardItem[][] board;
		public boolean isWinner;

		public Board(BoardItem[][] board) {
			this.board = board;
			isWinner = false;
		}
	}
}
