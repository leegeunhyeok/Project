# -*- coding: utf-8 -*-
import copy
a = [1, 2, 3] #List instance
b = a # b는 a가 참조하고 있는 리스트를 참조
c = a[:] # c는 독자적인 리스트를 참조

print(a, b, c)
print(id(a), id(b), id(c))