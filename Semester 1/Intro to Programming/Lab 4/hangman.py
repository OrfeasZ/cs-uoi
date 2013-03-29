# Lab 04 - Hangman
# @author: Orfeas Zafeiris <cs02250>

from random import randint
import os

def clear():
	clearCon = lambda: os.system('cls')
	clearCon()

def printWord(C = []):
	y = ""
	for c in C:
		y += "%s " % c
	print y

def printBadLetters(C = []):
	y = ""
	for c in C:
		y += "%s " % c
	print("Missed letters: %s\n" % y)

def printHangman(i = 0):
	print("H A N G M A N\n")
	print("  +---+")
	print("  |   |")

	if (i > 0):
		print("  o   |")
	else:
		print("      |")

	if (i == 2):
		print("  |   |")
	elif (i == 3):
		print(" /|   |")
	elif (i >= 4):
		print(" /|\\  |")
	else:
		print("      |")

	if (i == 5):
		print(" /    |")
	elif (i == 6):
		print(" /\\   |")
	else:
		print("      |")

	print("      |")
	print("========\n")

def main():
	WordList = ['hello', 'bananas', 'pick', 'destiny', 'tenacious', 'jables']

	W = WordList[randint(0, len(WordList) - 1)]
	C = []
	sFound = 0

	Missed = []

	N = 0

	for o in range(0, len(W)):
		C.append("_")

	hasWon = False

	while N < 6:
		clear()
		printHangman(N)
		print("Tries left: %d" % (6 - N))
		printBadLetters(Missed)
		printWord(C)

		L = raw_input("\nInput a letter:\n")

		if L.lower() in C:
			print ("You have already guessed this letter.\n")
			continue

		if L.lower() in W:
			for i in range(0, len(W)):
				if W[i] == L.lower():
					C[i] = L.lower()
					sFound += 1
		else:
			N += 1
			if L.lower() not in Missed:
				Missed.append(L.lower())

		if sFound == len(W):
			hasWon = True
			break

	clear()

	printHangman(N)

	if hasWon:
		print("Congratulations, you've found the word!\n")
	else:
		print("Oops! You lost...\n")

	PlayAgain = raw_input("Do you wish to play again? (y/n)")

	if (PlayAgain.lower() == 'y'):
		main()
	else:
		return

main()