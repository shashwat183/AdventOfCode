package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

const (
	Win  int = 6
	Loss     = 0
	Draw     = 3
)

func main() {
	fmt.Println(solution2())
}

func solution2() int {
	file, err := os.Open("input.txt")
	if err != nil {
		panic(err)
	}
	scanner := bufio.NewScanner(file)
	totalScore := 0
	for scanner.Scan() {
		strat := scanner.Text()
                predictedMove := predictMove(strat)
		totalScore += moveScore(predictedMove)
		_, expResult := splitInputStr(strat)
		totalScore += expResultScore(expResult)
	}
	return totalScore
}

func solution1() int {
	file, err := os.Open("input.txt")
	if err != nil {
		panic(err)
	}
	scanner := bufio.NewScanner(file)
	totalScore := 0
	for scanner.Scan() {
		strat := scanner.Text()
		totalScore += moveResult(strat)
		_, ourMove := splitInputStr(strat)
		totalScore += moveScore(ourMove)
	}
	return totalScore
}

func moveResult(move string) int {
	switch move {
	case "A Y", "B Z", "C X":
		return Win
	case "A Z", "B X", "C Y":
		return Loss
	case "A X", "B Y", "C Z":
		return Draw
	default:
		panic("invalid input to moveResult")
	}
}

func moveScore(move string) int {
	switch move {
	case "X":
		return 1
	case "Y":
		return 2
	case "Z":
		return 3
	default:
		panic("inavlid input to moveScore")

	}
}

func expResultScore(expResult string) int {
	switch expResult {
	case "X":
		return 0
	case "Y":
		return 3
	case "Z":
		return 6
	default:
		panic("inavlid input to moveScore")

	}
}

func predictMove(strat string) string {
	pl1Move, expResult := splitInputStr(strat)
	switch expResult {
	case "X":
		switch pl1Move {
		case "A":
			return "Z"
		case "B":
			return "X"
		case "C":
			return "Y"
		}
	case "Y":
		switch pl1Move {
		case "A":
			return "X"
		case "B":
			return "Y"
		case "C":
			return "Z"
		}
	case "Z":
		switch pl1Move {
		case "A":
			return "Y"
		case "B":
			return "Z"
		case "C":
			return "X"

		}
	default:
		panic("invalid input to predictMove")
	}
	return ""
}

func splitInputStr(line string) (string, string) {
	split := strings.Split(line, " ")
	return split[0], split[1]
}
