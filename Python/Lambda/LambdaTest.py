def sqrt(n):
    return n**2

list1 = list(map(sqrt, [1, 2, 3, 4, 5]))
list2 = list(map(lambda n : n * n, [1, 2, 3, 4, 5]))
print(list1, list2)

