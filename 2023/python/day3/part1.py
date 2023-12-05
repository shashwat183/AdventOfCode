def parse_num(row, r, c, parts_map):
    while c > -1:
        if row[c].isnumeric():
            c -= 1
        else:
            break
    c += 1
    c_idx = c
    r_idx = r

    num = ""
    while c < len(row):
        if row[c].isnumeric():
            num += row[c]
            c += 1
        else:
            break
    parts_map[(r_idx, c_idx)] = int(num)

if __name__ == "__main__":
    data = []
    with open("./input.txt", "r+") as file:
        for line in file:
            data.append(list(line.strip()))

    parts_map = {}

    for r in range(len(data)):
        for c in range(len(data[0])):
            if not data[r][c].isnumeric() and data[r][c] != '.':
                if r>0:
                    if data[r-1][c].isnumeric():
                        parse_num(data[r-1], r-1, c, parts_map)
                    else:
                        if c>0 and data[r-1][c-1].isnumeric():
                            parse_num(data[r-1], r-1, c-1, parts_map)
                        if c<len(data[0])-1 and data[r-1][c+1].isnumeric():
                            parse_num(data[r-1], r-1, c+1, parts_map)

                if c>0:
                    if data[r][c-1].isnumeric():
                        parse_num(data[r], r, c-1, parts_map)

                if c<len(data[0])-1:
                    if data[r][c+1].isnumeric():
                        parse_num(data[r], r, c+1, parts_map)

                if r<len(data)-1:
                    if data[r+1][c].isnumeric():
                        parse_num(data[r+1], r+1, c, parts_map)
                    else:
                        if c>0 and data[r+1][c-1].isnumeric():
                            parse_num(data[r+1], r+1, c-1, parts_map)
                        if c<len(data[0])-1 and data[r+1][c+1].isnumeric():
                            parse_num(data[r+1], r+1, c+1, parts_map)

    result = 0
    for _, num in parts_map.items():
        result += num

    print(result)
