# Lab 02 - Fibonacci01
# @author: Orfeas Zafeiris <cs02250>

n = int(input('Enter a number:\n'))

nums = [ 0, 1 ]
last = 1

while last < n:
	last = nums[-2:][0] + nums[-2:][1]
	nums.append(last)

for i in nums:
	if i >= n:
		break
	print i
