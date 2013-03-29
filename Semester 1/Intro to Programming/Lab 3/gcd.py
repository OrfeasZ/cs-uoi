# Lab 03 - GCD
# @author: Orfeas Zafeiris <cs02250>

def GCD_recursive(x, y):
	p = x % y
	
	if p == 0:
		return y

	return GCD(y, p)

def GCD(x, y):
	p = x % y
	
	while p != 0:
		x = y
		y = p
		p = x % y

	return y