#!/usr/bin/python

phonebook = {"Rui":"150 1100 5932", "Phonex":"138 1166 6985", "Mother":"132 0713 1873"}
print phonebook["Rui"]

list = [("Rui", "150 1100 5932"), ("Phonex", "138 1166 6985"), ("Mother", "132 0713 1873")]
pb2 = dict(list)
print pb2


print dict(Rui = "150 1100 5932", Phonex = "138 1166 6985", Mother = "132 0713 1873")

pb3 = {
	'version': 2,
	'member': ['rui', 'p', 'm'],
	"Rui": {
		"tel":"150 1100 5932",
		"addr": "Wang Jing"
	},
	"Phonex": {
		"tel": "138 1166 6985",
		"addr": "Zhi Chun Road"
	},
	"Mother": {
		"tel":"132 0713 1873",
		"addr": "Wu Han"
	}
}

# print "Rui's info %(Rui)s" % pb3
# pb3.clear()
#print pb3

pb4 = {
	'version': 2,
	'member': ['rui', 'p', 'm']
}

from copy import deepcopy
pbs = deepcopy(pb4);

pbs['version'] = 3
pbs['member'].remove('rui')
print pb4
print pbs

print {}.fromkeys(['test', 1, 'good'])
print dict.fromkeys(['test', 1, 'good'], 'default')

pb5 = {
	'version': 2,
	'member': ['rui', 'p', 'm']
}
pb6 = {
	'version': 3,
	'data' : 80
}
pb5.update(pb6)
print pb5

pb = {"Rui":"150 1100 5932", "Phonex":"138 1166 6985", "Mother":"132 0713 1873"}
print pb.items()
print pb.iteritems()
print pb.keys()
print pb.iterkeys()
print pb.values()
print pb.itervalues()




