def func1(*args): # 가변인자는 *을 붙인다.
    list = []
    for x in args:
        for y in x:
            if not y in list:
                list.append(y) 
    return list

def func2(address, domain, **property): # 딕셔너리 형태 가변인자
    str = "http://" + address + "." + domain + "/?";
    for k in property.keys():
        str += k + "=" + property[k] + "&"
    return str
        
print(func1("ABC", "AND", "AT"))
print(func2("codevkr", "tistory.com", id='lghlove0509', boolean='true'))

"""
RESULT:
['A', 'B', 'C', 'N', 'D', 'T']
http://codevkr.tistory.com/?id=lghlove0509&boolean=true&
"""