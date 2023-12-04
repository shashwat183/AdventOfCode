if __name__ == "__main__":
    cards = []
    with open("./input.txt", "r+") as file:
        for line in file:
            cards.append({
                "id": line.split(":")[0].split(" ")[-1],
                "win_nums": list(filter(lambda n: n != '', line.split(":")[1].split("|")[0].strip().split(" "))),
                "nums": list(filter(lambda n: n != '', line.split(":")[1].split("|")[1].strip().split(" ")))
            })

    result = 0
    for card in cards:
        points = len([v for v in card["nums"] if v in card["win_nums"]])
        if points > 0:
            result += 2**(points-1)

    print(result)
