package edu.caltech.cs2.project08.bots;

import edu.caltech.cs2.project08.game.Board;
import edu.caltech.cs2.project08.game.Evaluator;
import edu.caltech.cs2.project08.game.Move;
import edu.caltech.cs2.project08.game.SimpleEvaluator;

public class AlphaBetaSearcher<B extends Board> extends AbstractSearcher<B> {
    @Override
    public Move getBestMove(B board, int myTime, int opTime) {
        BestMove best = alphaBeta(this.evaluator, board, ply);
        return best.move;
    }


    /*
1 int minimax(Position p) {
2 if (p is a leaf) {
3 // evaluate tells us the
4 // value of the current
5 // position
6 return p.evaluate();
7 }
8
9 int bestValue = −∞;
10 for (move in p.getMoves()) {
11 p.applyMove(move);
12 int value = −minimax(p);
13 p.undoMove();
14 if (value > bestValue) {
15 bestValue = value;
16 }
17 }
18 }
     */

    private static <B extends Board> BestMove minimax(Evaluator<B> evaluator, B board, int depth) {

        if (board.isGameOver() || depth == 0) {
            int value = evaluator.eval(board);
            Move move = null;
            return new BestMove(move, value);
        }

        int bestOption = Integer.MIN_VALUE;
        Move bestMove = null;
        for (Move move : board.getMoves()) {
            board.makeMove(move);
            int value = -1 * (minimax(evaluator, board, depth - 1).score);
            board.undoMove();
            if (value > bestOption) {
                bestOption = value;
                bestMove = move;
            }

        }
        return new BestMove(bestMove, bestOption);
    }

    /*
1 int alphabeta(Position p, int alpha, int beta) {
2 if (p is a leaf) {
3 return p.evaluate();
4 }
5
6 for (move in p.getMoves()) {
7 p.applyMove(move);
8 int value = −alphabeta(p, −beta, −alpha);
9 p.undoMove();
10
11 // If value is between alpha and beta, we've
12 // found a new lower bound
13 if (value > alpha) {
14 alpha = value;
15 }
16
17 // If the value is bigger than beta, we won't
18 // actually be able to get this move
19 if (alpha >= beta) {
20 return alpha;
21 }
22 }
23
24 // Return the best achievable value
25 r
     */

    private static <B extends Board> BestMove alphaBeta(Evaluator<B> evaluator, B board, int depth) {
        int alpha = Integer.MIN_VALUE + 1;
        int beta = Integer.MAX_VALUE;
        return alphaBeta(evaluator, board, depth, alpha, beta);
    }

    private static <B extends Board> BestMove alphaBeta(Evaluator<B> evaluator, B board, int depth, int alpha, int beta) {

        int newAlpha = alpha;

        if (board.isGameOver() || depth == 0) {
            int value = evaluator.eval(board);
            Move move = null;
            return new BestMove(move, value);
        }

        Move bestMove = null;

        for (Move move : board.getMoves()) {
            bestMove = move;
            board.makeMove(move);
            int value = -1 * alphaBeta(evaluator, board, depth - 1, -1 * beta, -1 * newAlpha).score;
            board.undoMove();

            if (value > newAlpha) {
                newAlpha = value;
            }

            if (newAlpha >= beta) {
                return new BestMove(move, newAlpha);
            }
        }

        return new BestMove(bestMove, newAlpha);
    }
}