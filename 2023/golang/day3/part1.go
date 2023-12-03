package day3

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"unicode"
)

func Solution1() error {
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
	result, err := solve(data)
	if err != nil {
		return err
	}

	fmt.Println(result)
	return nil
}

func solve(data [][]rune) (int, error) {
	result := 0
	for r := 0; r < len(data); r++ {
		for c := 0; c < len(data[0]); {
			if unicode.IsDigit(data[r][c]) {
				num, newC, err := parse(data, r, c)
				if err != nil {
					return 0, err
				}
				result += num
				c = newC
			} else {
				c++
			}
		}
	}
	return result, nil
}

func parse(data [][]rune, r, startC int) (int, int, error) {
	numStr := ""
	endC := startC
	for endC < len(data[0]) && unicode.IsDigit(data[r][endC]) {
		numStr += string(data[r][endC])
		endC++
	}
	if allDirSrch(data, r, startC, endC) {
		num, err := strconv.Atoi(numStr)
		if err != nil {
			return 0, 0, err
		}
		return num, endC, nil
	}
	return 0, endC, nil
}

func allDirSrch(data [][]rune, r, startC, endC int) bool {
	for c := startC; c < endC; c++ {
		if r > 0 && c > 0 && data[r-1][c-1] != '.' && !unicode.IsDigit(data[r-1][c-1]) {
			return true
		}
		if r > 0 && data[r-1][c] != '.' && !unicode.IsDigit(data[r-1][c]) {
			return true
		}
		if r > 0 && c < len(data[0])-1 && data[r-1][c+1] != '.' && !unicode.IsDigit(data[r-1][c+1]) {
			return true
		}
		if c > 0 && data[r][c-1] != '.' && !unicode.IsDigit(data[r][c-1]) {
			return true
		}
		if c < len(data[0])-1 && data[r][c+1] != '.' && !unicode.IsDigit(data[r][c+1]) {
			return true
		}
		if r < len(data)-1 && c > 0 && data[r+1][c-1] != '.' && !unicode.IsDigit(data[r+1][c-1]) {
			return true
		}
		if r < len(data)-1 && data[r+1][c] != '.' && !unicode.IsDigit(data[r+1][c]) {
			return true
		}
		if r < len(data)-1 && c < len(data[0])-1 && data[r+1][c+1] != '.' && !unicode.IsDigit(data[r+1][c+1]) {
			return true
		}
	}
	return false
}
