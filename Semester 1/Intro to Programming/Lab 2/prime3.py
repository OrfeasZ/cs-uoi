# Lab 02 - Prime03
# @author: Orfeas Zafeiris <cs02250>

from math import sqrt

def isprime(N):
	is_prime = True

	if N <= 1:
		is_prime = False
	else:
		for M in range(2, int(sqrt(N)) + 1):
			if (N % M == 0):
				is_prime = False
				break

	return is_prime


def main():
	n = int(input('Please enter a number:\n'))

	if n <= 1:
		print('Invalid input.\n')
	else:
		for i in range(2, n + 1):
			if isprime(i):
				print i

main()