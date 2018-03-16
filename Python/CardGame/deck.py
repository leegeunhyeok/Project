import random

# Deck class
class Deck(object):
    def __init__(self, cards):
        self.__cards = cards

    @property
    def cards(self):
        return self.__cards

    # Add card
    def add_card(self, new_card):
        for card in self.__cards:
            if card.number == new_card.number and card.shape == new_card.shape:
                print "%s %s is already in deck!" % (card.shape, card.number)
                break
        else:
            self.__cards.append(card)
            print "%s %s added!"

    # Shuffle card deck
    def shuffle(self):
        random.shuffle(self.__cards)
        print "Deck shuffled!"

    # Choose random card
    def choose_card(self):
        return self.__cards.pop()