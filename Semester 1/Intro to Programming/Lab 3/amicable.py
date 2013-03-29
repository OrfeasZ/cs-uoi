# Lab 03 - Amicable
# @author: Orfeas Zafeiris <cs02250>

def sum_divisors(num):
	S = 0

	for i in range(num / 2, 0, -1):
		if (num % i) == 0:
			S += i

	return S


def main():
	r1 = int(input())
	r2 = int(input())

	if (r1 >= r2):
		print 'Invalid input.'
		return

	nums = {}
	r_nums = []

	for n in range(r1, r2 + 1):
		nums[n] = sum_divisors(n);

	for k1 in nums.keys():
		for k2 in nums.keys():
			if k2 != k1:
				if k1 == nums[k2] and k2 == nums[k1] and k1 not in r_nums:
					r_nums.append(k1)
					r_nums.append(nums[k1])
					print 'The numbers %d and %d are amicable.' % (k1, nums[k1])

main()