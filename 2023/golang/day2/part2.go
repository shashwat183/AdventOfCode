package day2

import (
	"bufio"
	"fmt"
	"os"
)

func Solution2() error {
	file, err := os.Open("./day2/input.txt")
	if err != nil {
		return err
	}
	defer file.Close()
	scanner := bufio.NewScanner(file)
	result := 0
	for scanner.Scan() {
		line := scanner.Text()
		_, games, err := getGames(line)
		if err != nil {
			return err
		}
		powerOfSet := power(games)
		result += powerOfSet
	}
	fmt.Println(result)
	return nil
}

func power(games []game) int {
	minRed := 0
	minGreen := 0
	minBlue := 0

	for _, g := range games {
		if g.red > minRed {
			minRed = g.red
		}
		if g.blue > minBlue {
			minBlue = g.blue
		}
		if g.green > minGreen {
			minGreen = g.green
		}
	}

	return minRed * minBlue * minGreen
}
