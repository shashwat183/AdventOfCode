import json

if __name__ == "__main__":
    cards = {}
    with open("./input.txt", "r+") as file:
        for line in file:
            id = int(line.split(":")[0].split(" ")[-1])
            cards[id] = {
                "id": id,
                "win_nums": list(filter(lambda n: n != '', line.split(":")[1].split("|")[0].strip().split(" "))),
                "nums": list(filter(lambda n: n != '', line.split(":")[1].split("|")[1].strip().split(" "))),
                "count": 1
            }

    for id in range(1, len(cards.keys())+1):
        print(id)
        for count in range(cards[id]["count"]):
            matches = len([v for v in cards[id]["nums"] if v in cards[id]["win_nums"]])
            for i in range(1, matches+1):
                cards[id+i]["count"] += 1

    result = 0
    for id, card in cards.items():
        result += card["count"]
    
    print(result)
