# Lab 05 - Grades
# @author: Orfeas Zafeiris <cs02250>

from random import randint

std = [ ['Eric', 'Cox',7,5],
       ['John', 'Huphrey', 7, 5],
       ['Abel', 'Jones',7 , 5],
       ['Henry', 'Lewis',7,5] ,
       ['David', 'Powell',7,5],
       ['Gabriel', 'Smith',7,5],
       ['Lucifer', 'Pearson',7,5],
       ['Nikolas', 'Gibbs',7,5],
       ['Paul', 'Russo',7,5],
       ['Ophelia', 'Skinner',7,5],
       ['Cecil', 'Sullivan',7,5],
       ['Walter', 'Addison',7,5],
       ['Stefan', 'Cowan',7,5],
       ['Tony', 'Pruit',7,5] ]

def insertStudent(index, Name, LastName, GradeA, GradeB):
      std.insert(index, [Name, LastName, GradeA, GradeB])

def scramble():
      for student in std:
            student[2] = randint(0, 10)
            student[3] = randint(0, 10)

def avg():
      for student in std:
            student.append((student[2] + student[3]) / 2)

def lessonAvg(index):
      x = 0
      for student in std:
            x += student[index]
      x /= len(std)
      return x

def countGrade(index, grade):
      x = 0
      for student in std:
            if student[index] == grade:
                  x += 1
      return x

def printHistogram(x, y):
      for i in range(0, len(x)):
            j = "%d\t: %s\n\t  %s" % (i, '*'*x[i], '+'*y[i])
            print(j)

def greatest(index):
      student = std[0]
      for stud in std:
            if stud[index] > student[index]:
                  student = stud
      return student

def main():
      insertStudent(2, 'Michael', 'Osporn', 8, 8)
      insertStudent(7, 'Yen', 'Men', 10, 10)
      scramble()
      avg()

      MO_Pr = lessonAvg(2)
      MO_Y = lessonAvg(3)

      print("MO_Pr: %d" % MO_Pr)
      print("MO_Y: %d\n" % MO_Y)

      L_pr = []
      for i in range(0, 11):
            L_pr.append(countGrade(2, i))

      L_y = []
      for i in range(0, 11):
            L_y.append(countGrade(3, i))

      print L_pr
      print L_y

      print("")

      printHistogram(L_pr, L_y)

      by_avg = sorted(std, key=lambda student: student[4])
      by_lname = sorted(std, key=lambda student: student[1])

      print("")
      print by_avg
      print("")
      print by_lname



main()
raw_input()