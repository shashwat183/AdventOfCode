package day2

import (
	"bufio"
	"errors"
	"fmt"
	"os"
	"strconv"
	"strings"
)

const (
	totalRed = 12
	totalGreen = 13
	totalBlue = 14
)

type game struct {
	red int
	green int
	blue int
}

func (g game) validate() bool {
	if g.red > totalRed {
		return false
	}
	if g.green > totalGreen {
		return false
	}
	if g.blue > totalBlue {
		return false
	}
	return true
}

func fromString(data string) (*game, error) {
	g := new(game)

	data = strings.Trim(data, " ")
	split := strings.Split(data, ",")

	for _, d := range split {
		d = strings.Trim(d, " ")
		spl := strings.Split(d, " ")
		val, err := strconv.Atoi(spl[0])
		if err != nil {
			return nil, err
		}

		colour := spl[1]
		switch (colour) {
		case "green":
			g.green += val
			break
		case "red":
			g.red += val
			break
		case "blue":
			g.blue += val
			break
		default:
			return nil, errors.New("invalid input, colour could not be identified")
		}
	}

	return g, nil
}

func Solution1() error {
	file, err := os.Open("./day2/input.txt")
	if err != nil {
		return err
	}
	defer file.Close()
	scanner := bufio.NewScanner(file)
	result := 0
	for scanner.Scan() {
		line := scanner.Text()
		idx, games, err := getGames(line)
		if err != nil {
			return err
		}
		valid := true
		for _, game := range games {
			if !game.validate() {
				valid = false
			}
		}
		if valid {
			result += idx
		}
	}
	fmt.Println(result)
	return nil
}

func getGames(input string) (int, []game, error) {
	games := make([]game, 0)
	split := strings.Split(input, ":")
	metadata := split[0]
	data := split[1]

	idx, err := strconv.Atoi(strings.Split(metadata, " ")[1])
	if err != nil {
		return 0, nil, err
	}
	split = strings.Split(data, ";")
	for _, d := range split {
		currGame, err := fromString(d)
		if err != nil {
			return 0, nil, err
		}
		games = append(games, *currGame)
	}
	return idx, games, nil
}
