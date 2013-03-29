# Lab 02 - Fibonacci02
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


def getprimes(n):
	primes = []
	for i in range(2, n + 1):
		if isprime(i):
			primes.append(i)
	return primes

n = int(input('Enter a number:\n'))

nums = [ 0, 1 ]
last = 1

while last < n:
	last = nums[-2:][0] + nums[-2:][1]
	nums.append(last)

# r = [i for i in l1 if i in l2]

primes = getprimes(n=n)

for i in nums:
	if i >= n:
		break
	if i in primes:
		print i
