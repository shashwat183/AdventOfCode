def parse_num(row, c):
    while c > -1:
        if row[c].isnumeric():
            c -= 1
        else:
            break
    c += 1

    num = ""
    while c < len(row):
        if row[c].isnumeric():
            num += row[c]
            c += 1
        else:
            break
    return int(num)

if __name__ == "__main__":
    data = []
    with open("./input.txt", "r+") as file:
        for line in file:
            data.append(list(line.strip()))

    result = 0
    for r in range(len(data)):
        for c in range(len(data[0])):
            if data[r][c] == '*':
                nums = []
                if r>0:
                    if data[r-1][c].isnumeric():
                        nums.append(parse_num(data[r-1], c))
                    else:
                        if c>0 and data[r-1][c-1].isnumeric():
                            nums.append(parse_num(data[r-1], c-1))
                        if c<len(data[0])-1 and data[r-1][c+1].isnumeric():
                            nums.append(parse_num(data[r-1], c+1))

                if c>0:
                    if data[r][c-1].isnumeric():
                        nums.append(parse_num(data[r], c-1))

                if c<len(data[0])-1:
                    if data[r][c+1].isnumeric():
                        nums.append(parse_num(data[r], c+1))

                if r<len(data)-1:
                    if data[r+1][c].isnumeric():
                        nums.append(parse_num(data[r+1], c))
                    else:
                        if c>0 and data[r+1][c-1].isnumeric():
                            nums.append(parse_num(data[r+1], c-1))
                        if c<len(data[0])-1 and data[r+1][c+1].isnumeric():
                            nums.append(parse_num(data[r+1], c+1))

                if len(nums) == 2:
                    result += nums[0]*nums[1]
    
    print(result)
