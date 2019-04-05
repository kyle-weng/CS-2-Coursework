package edu.caltech.cs2.project08.bots;

import edu.caltech.cs2.project08.game.Board;
import edu.caltech.cs2.project08.game.Evaluator;
import edu.caltech.cs2.project08.game.Move;

public class MinimaxSearcher<B extends Board> extends AbstractSearcher<B> {
    @Override
    /* Hell yeah brother cheers from iraq! */
    public Move getBestMove(B board, int myTime, int opTime) {
        BestMove best = minimax(this.evaluator, board, ply);
        return best.move;
    }

    private static <B extends Board> BestMove minimax(Evaluator<B> evaluator, B board, int depth) {

        if(board.isGameOver() || depth == 0) {                                      // if depth == 0 or game over in position
            int value = evaluator.eval(board);                                      // value = static evaluation of position
            Move move = null;                                                       // no move was made
            return new BestMove(move, value);                                       // return static evaluation of position, no move
        }

        int bestOption = Integer.MIN_VALUE;                                         // bestOption is initially as bad as possible
        Move bestMove = null;                                                       // best move initially doesn't exist
        for(Move move: board.getMoves()) {                                          // iterate through every possible move
            board.makeMove(move);                                                   // change the board state by making the move
            int value = -1 * (minimax(evaluator, board, depth - 1).score);   // value is set to the negative of the bestmove object's score attribute (ie. value)
            board.undoMove();                                                       // then, undo the move
            if(value > bestOption) {                                                // if this was better than the best option, set the option to the value and the best move to the move
                bestOption = value;
                bestMove = move;
            }

        }
        return new BestMove(bestMove, bestOption);                                  // return new bestmove object
    }
}