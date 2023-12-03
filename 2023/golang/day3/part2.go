package day3

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"unicode"
)

func Solution2() error {
	data := make([][]rune, 0)
	file, err := os.Open("./day3/input.txt")
	if err != nil {
		return err
	}

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := scanner.Text()
		data = append(data, []rune(line))
	}
	result, err := solve2(data)
	if err != nil {
		return err
	}
	fmt.Println(result)
	return nil
}

func solve2(data [][]rune) (int, error) {
	result := 0
	for r := 0; r < len(data); r++ {
		for c := 0; c < len(data[0]); c++ {
			if data[r][c] == '*' {
				// printAround(data, r, c)
				ratio, err := gearRatio(data, r, c)
				if err != nil {
					return 0, err
				}
				// fmt.Println(ratio)
				result += ratio
			}
		}
	}
	return result, nil
}

func gearRatio(data [][]rune, r, c int) (int, error) {
	// fmt.Println("----------")
	// fmt.Println(r, c)
	nums := make([]int, 0)
	if r > 0 {
		if unicode.IsDigit(data[r-1][c]) {
			num, err := getNum(data, r-1, c)
			if err != nil {
				return 0, err
			}
			nums = append(nums, num)
		} else {
			if c > 0 && unicode.IsDigit(data[r-1][c-1]) {
				num, err := getNum(data, r-1, c-1)
				if err != nil {
					return 0, err
				}
				nums = append(nums, num)
			}

			if c < len(data[0])-1 && unicode.IsDigit(data[r-1][c+1]) {
				num, err := getNum(data, r-1, c+1)
				if err != nil {
					return 0, err
				}
				nums = append(nums, num)
			}
		}
	}

	if c > 0 && unicode.IsDigit(data[r][c-1]) {
		num, err := getNum(data, r, c-1)
		if err != nil {
			return 0, err
		}
		nums = append(nums, num)
	}

	if c < len(data[0])-1 && unicode.IsDigit(data[r][c+1]) {
		num, err := getNum(data, r, c+1)
		if err != nil {
			return 0, err
		}
		nums = append(nums, num)
	}

	if r < len(data)-1 {
		if unicode.IsDigit(data[r+1][c]) {
			num, err := getNum(data, r+1, c)
			if err != nil {
				return 0, err
			}
			nums = append(nums, num)
		} else {
			if c > 0 && unicode.IsDigit(data[r+1][c-1]) {
				num, err := getNum(data, r+1, c-1)
				if err != nil {
					return 0, err
				}
				nums = append(nums, num)
			}
			if c < len(data[0])-1 && unicode.IsDigit(data[r+1][c+1]) {
				num, err := getNum(data, r+1, c+1)
				if err != nil {
					return 0, err
				}
				nums = append(nums, num)
			}
		}

	}
	if len(nums) == 2 {
		printAround(data, r, c)
		fmt.Println(nums)
		return nums[0] * nums[1], nil
	}
	return 0, nil
}

// for any r,c parse and return the number at that point in the row
func getNum(data [][]rune, r, c int) (int, error) {
	// fmt.Printf("getNum called for %v, %v\n", r, c)
	// fmt.Println(string(data[r]))
	for c > -1 && unicode.IsDigit(data[r][c]) {
		c--
	}
	c++

	numStr := ""
	for c < len(data[0]) && unicode.IsDigit(data[r][c]) {
		numStr += string(data[r][c])
		c++
	}
	// fmt.Println(numStr)
	return strconv.Atoi(numStr)
}

func printAround(data [][]rune, r, c int) {
	var prevRow, currRow, nextRow string
	if r > 0 {
		prevRow = string(data[r-1])
	}
	currRow = string(data[r])
	if r < len(data)-1 {
		nextRow = string(data[r+1])
	}
	fmt.Println(prevRow)
	fmt.Println(currRow)
	fmt.Println(nextRow)
}
