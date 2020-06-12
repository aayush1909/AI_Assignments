'''
def isWin(board):

    ### check if any of the rows has winning combination
    for i in range(3):
        if len(set(board[i*3:i*3+3])) is  1 and board[i*3] is not '-': return True
    ### check if any of the Columns has winning combination
    for i in range(3):
       if (board[i] is board[i+3]) and (board[i] is  board[i+6]) and board[i] is not '-':
           return True
    ### 2,4,6 and 0,4,8 cases
    if board[0] is board[4] and board[4] is board[8] and board[4] is not '-':
        return  True
    if board[2] is board[4] and board[4] is board[6] and board[4] is not '-':
        return  True
    return False
'''
import random


class TicTacToe:
	def __init__(self):
		# Initialize with empty board
		self.board = [" ", " ", " ",
		              " ", " ", " ",
		              " ", " ", " "]
	def show(self):
		# Format and print board
		print("""
          {} | {} | {}
         -----------
          {} | {} | {}
         -----------
          {} | {} | {}
        """.format(*self.board))

	def clearBoard(self):
		self.board = [" ", " ", " ",
		              " ", " ", " ",
		              " ", " ", " "]

	def whoWon(self):
		if self.checkWin() == "X":
			return "X"
		elif self.checkWin() == "O":
			return "O"
		elif self.gameOver() == True:
			return "Nobody"

	def availableMoves(self):
		# Return empty spaces on the board
		moves = []
		for i in range(0, len(self.board)):
			if self.board[i] == " ":
				moves.append(i)
		return moves

	def getMoves(self, player):
		# Get all moves made by a given player
		moves = []
		for i in range(0, len(self.board)):
			if self.board[i] == player:
				moves.append(i)
		return moves

	def makeMove(self, position, player):
		# Make a move on the board
		self.board[position] = player

	def checkWin(self):
		# Return the player that wins the game
		combos = ([0, 1, 2], [3, 4, 5], [6, 7, 8],
		          [0, 3, 6], [1, 4, 7], [2, 5, 8],
		          [0, 4, 8], [2, 4, 6])

		for player in ("X", "O"):
			positions = self.getMoves(player)
			for combo in combos:
				win = True
				for pos in combo:
					if pos not in positions:
						win = False
				if win:
					return player

	def gameOver(self):
		# Return True if X wins, O wins, or draw, else return False
		if self.checkWin() != None:
			return True
		for i in self.board:
			if i == " ":
				return False
		return True

	def minimax(self, node, depth, player):
		if depth == 0 or node.gameOver():
			if node.checkWin() == "X":
				return 0
			elif node.checkWin() == "O":
				return 100
			else:
				return 50

		if player == "O":
			bestValue = 0
			for move in node.availableMoves():
				node.makeMove(move, player)
				moveValue = self.minimax(node, depth - 1, changePlayer(player))
				node.makeMove(move, " ")
				bestValue = max(bestValue, moveValue)
			return bestValue

		if player == "X":
			bestValue = 100
			for move in node.availableMoves():
				node.makeMove(move, player)
				moveValue = self.minimax(node, depth - 1, changePlayer(player))
				node.makeMove(move, " ")
				bestValue = min(bestValue, moveValue)
			return bestValue


def changePlayer(player):
	if player == "X":
		return "O"
	else:
		return "X"


def make_best_move(board, depth, player):
	neutralValue = 50
	choices = []
	for move in board.availableMoves():
		board.makeMove(move, player)
		moveValue = board.minimax(board, depth - 1, changePlayer(player))
		board.makeMove(move, " ")

		if moveValue > neutralValue:
			choices = [move]
		elif moveValue == neutralValue:
			choices.append(move)
	# print("choices: ", choices)

	if len(choices) > 0:
		return random.choice(choices)
	else:
		return random.choice(board.availableMoves())


# Actual game
if __name__ == '__main__':
	game = TicTacToe()
	game.show()

	while game.gameOver() == False:
		person_move = int(input("You are X: Choose number from 1-9: "))
		game.makeMove(person_move - 1, "X")
		game.show()

		if game.gameOver() == True:
			break

		print("Computer choosing move...")
		ai_move = make_best_move(game, -1, "O")
		game.makeMove(ai_move, "O")
		game.show()

	print("Game Over. " + game.whoWon() + " Wins")

