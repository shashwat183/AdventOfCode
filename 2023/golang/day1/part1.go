package day1

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"unicode"
)

func Solution1() error {
	file, err := os.Open("./day1/input.txt")
	if err != nil {
		return err
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	result := 0
	for scanner.Scan() {
		val, err := getVal1(scanner.Text())
		if err != nil {
			return err
		}
		result += val
	}
	fmt.Println(result)
	return nil
}

func getVal1(input string) (int, error) {
	var calibration int
	for i := 0; i < len(input); i++ {
		if unicode.IsDigit(rune(input[i])) {
			val, err := strconv.Atoi(string(input[i]))
			if err != nil {
				return 0, err
			}
			calibration += val * 10
			break
		}
	}
	for i := len(input) - 1; i > -1; i-- {
		if unicode.IsDigit(rune(input[i])) {
			val, err := strconv.Atoi(string(input[i]))
			if err != nil {
				return 0, err
			}
			calibration += val
			break
		}
	}
	return calibration, nil
}
