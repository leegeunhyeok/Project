# -*- coding: utf-8 -*-
def reverse(data):
    # 제너레이터
    for i in range(len(data)-1, -1, -1):
        yield data[i]
        
for c in reverse("Hello"):
    print(c)
    
    
print()


it = iter(reverse("ABCDE"))
while True:
    try:
        print(next(it))
    except StopIteration:
        break
    