# Lab 03 - GCD01
# @author: Orfeas Zafeiris <cs02250>

from gcd import *

def main():
	x = int(input())
	y = int(input())

	while x < 0 or y < 0:
		print 'Invalid input.'
		x = int(input())
		y = int(input())

	print 'The GCD of %d and %d is %d' % (x, y, GCD(x, y))

main()