# Lab 03 - Hanoi
# @author: Orfeas Zafeiris <cs02250>

def Hanoi_rec(n, x, y):
    pegs = ['A', 'B', 'C']

    if n > 0:
    	ny = 6 - x - y
        Hanoi_rec(n - 1, x, ny)

        print "%s -> %s" % (pegs[x - 1], pegs[y - 1])

        nx = 6 - x - y
        Hanoi_rec(n - 1, nx, y)
 
n = int(input('Enter the number of disks:\n'))

Hanoi_rec(n, 1, 3)

raw_input()