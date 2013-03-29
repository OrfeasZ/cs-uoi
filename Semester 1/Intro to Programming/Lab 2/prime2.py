# Lab 02 - Prime02
# @author: Orfeas Zafeiris <cs02250>

from math import sqrt

N = int(input('Please input a number:\n'))

if N <= 1:
	print('Not a prime')
else:
	is_prime = True
	for M in range(2, int(sqrt(N)) + 1):
		if (N % M == 0):
			is_prime = False
			break
	if is_prime:
		print('It is a prime')
	else:
		print('It\'s not a prime')