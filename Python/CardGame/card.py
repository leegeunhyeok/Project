# Card class
class Card(object):
    def __init__(self, number, shape):
        self.__number = number
        self.__shape = shape

    @property
    def number(self):
        return self.__number

    @property
    def shape(self):
        return self.__shape