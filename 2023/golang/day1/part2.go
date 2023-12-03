package day1

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
	"unicode"
)

var (
	numMap = map[string]int{
		"one":   1,
		"two":   2,
		"three": 3,
		"four":  4,
		"five":  5,
		"six":   6,
		"seven": 7,
		"eight": 8,
		"nine":  9,
	}
)

func Solution2() error {
	file, err := os.Open("./day1/input.txt")
	if err != nil {
		return err
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	result := 0
	for scanner.Scan() {
		input := scanner.Text()
		val, err := getVal2(input)
		if err != nil {
			return err
		}
		fmt.Println(val)
		result += val
	}
	fmt.Println(result)
	return nil
}

func getVal2(input string) (int, error) {
	var calibration int
	for i := 0; i < len(input); i++ {
		foundVal := -1
		if unicode.IsDigit(rune(input[i])) {
			val, err := strconv.Atoi(string(input[i]))
			if err != nil {
				return 0, err
			}
			foundVal = val
		} else {
			for word := range numMap {
				if strings.Contains(input[:i+1], word) {
					foundVal = numMap[word]
					break
				}
			}

		}
		if foundVal > 0 {
			calibration += foundVal * 10
			break
		}
	}
	for i := len(input) - 1; i > -1; i-- {
		foundVal := -1
		if unicode.IsDigit(rune(input[i])) {
			val, err := strconv.Atoi(string(input[i]))
			if err != nil {
				return 0, err
			}
			foundVal = val
		} else {
			for word := range numMap {
				if strings.Contains(input[i:], word) {
					foundVal = numMap[word]
					break
				}
			}
		}
		if foundVal > 0 {
			calibration += foundVal
			break
		}
	}
	return calibration, nil
}
