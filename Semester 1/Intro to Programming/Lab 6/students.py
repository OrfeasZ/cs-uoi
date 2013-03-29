# Lab 06 - Students
# @author: Orfeas Zafeiris <cs02250>

def load(infile):
	students = infile.readlines()

	for i in range(0, len(students)):
		students[i] = students[i].split()


	students.pop(0)
	while (len(students[len(students) - 1]) < 3):
		students.pop(len(students) - 1)

	return students

def dump(data = [], filename = ''):
	students_file = open(filename, 'w')

	students_file.write('LastName\tName\tGrade\n')

	for student in data:
		students_file.write('%s\t%s\t%s\n' % (student[0], student[1], student[2]))

	return

def get_palin_perc(name, lastname):
	combined = (name + lastname).lower()
	inv_combined = (name[::-1] + lastname[::-1]).lower()

	matches = 0

	for i in range(0, len(combined)):
		if combined[i] == inv_combined[i]:
			matches += 1

	return str(float(matches) / float(len(combined)) * 100) + '%'


def get_palin(data = []):
	
	print('\nPalindromes:')
	for student in data:
		if student[0].lower() == student[0][::-1].lower():
			print student[0]
		if student[1].lower() == student[1][::-1].lower():
			print student[1]


def main():
	students = []

	students_file = open('students.txt', 'r+')
	students = load(students_file)

	s_grade = 0.0
	for student in students:
		s_grade += float(student[2])

	s_grade /= len(students)

	students_file.write('%f\n' % s_grade)
	print('Student Grade Average: %f\n' % s_grade)

	dump(students, 'students2.txt')
	rev_students = load(open('students2.txt', 'r'))

	print rev_students

	get_palin(rev_students)

	print('')

	for student in students:
		print("[%s %s] Distance: %s" % (student[0], student[1], get_palin_perc(student[0], student[1])))


main()
raw_input()