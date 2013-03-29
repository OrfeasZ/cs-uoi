# @author: Orfeas Zafeiris <cs02250>

from math import sqrt

def calc(x=[]):
	N = len(x)

	if N <= 1:
		print 'Invalid input'
		return 0.0

	avg = float(sum(x))
	avg /= float(N)

	xsum = 0
	for i in x:
		xsum += pow((i - avg), 2)

	s = 1.0/(float(N) - 1)
	s *= xsum
	s = sqrt(s)

	return s

print calc(x=[1,5,4,6,2,6,8,4,9,6,7,3,9,1])