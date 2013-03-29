# @author: Orfeas Zafeiris <cs02250>

def power(x, y):
	if y >= 0:
		pw = 1
		for i in range(0, y):
			pw *= x

		return pw
	else:
		if x == 0:
			return 'Invalid input'

		pw = 1
		for i in range(0, y, -1):
			pw *= x

		return 1.0/float(pw)

def main():
	x = input()
	y = input()
	print power(x, y)

main()