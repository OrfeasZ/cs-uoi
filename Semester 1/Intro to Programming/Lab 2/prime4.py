# Lab 02 - Prime04
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
		print('Invalid input')
	else:
		x = n
		y = []
		k = ""

		while x >= 2:
			for i in range(2, x + 1):
				if (x % i == 0) and isprime(i):
					y.append(i)
					x /= i
					break

		for i, prime in enumerate(y):
			j = "x "
			if i == 0:
				j = ""
			k += "%s%d " % (j, prime)

		k += "= %d" % n
		print k

main()