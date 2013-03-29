# @author: Orfeas Zafeiris <cs02250>

from math import sqrt

def isPrime(x):
	for i in range(2, int(sqrt(x) + 1)):
		if (x % i == 0):
			return False
	return True

def genPrimes(limit):
	x = []
	for i in range(2, limit):
		if isPrime(i):
			x.append(i)
	return x

primes = genPrimes(100)
print(sum(primes))