import deck
import card
import kind

# Temp card list
card_list = []

# Create All kind(Number, shape) of card
for s in kind.shape:
    for n in kind.number:
        card_list.append(card.Card(n, s))

# Create deck and init card list
my_deck = deck.Deck(card_list)

# shuffle card deck
my_deck.shuffle()

# Choose random card
my_card = my_deck.choose_card()

# Print
print "Choose card : %s %s" % (my_card.number, my_card.shape)