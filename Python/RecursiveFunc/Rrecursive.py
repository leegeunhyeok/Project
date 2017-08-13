def factorial(x):
    """
    Recursive Function
    """
    if x==1:
        return 1
    return x * factorial(x - 1)

print(factorial(10))