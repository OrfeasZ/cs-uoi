# Lab 02 - Prime01
# @author: Orfeas Zafeiris <cs02250>

# Read the input
N = int(input('Please input a number:\n'))

# Check if it's valid for prime checking
if N <= 1:
	print('It\'s not a prime')
else:
	# If it is check every number up to it
	is_prime = True
	for M in range (2, N):
		if (N % M == 0):
			is_prime = False
			break
	if is_prime:
		print('It is a prime')
	else:
		print('It\'s not a prime')
