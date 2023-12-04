package day4

import (
	"bufio"
	"fmt"
	"math"
	"os"
	"strconv"
	"strings"

	"golang.org/x/exp/slices"
)

type Card struct {
	Id      int
	WinNums []int
	MyNums  []int
}

func (c Card) points() int {
	matches := 0
	for _, myNum := range c.MyNums {
		if slices.Contains(c.WinNums, myNum) {
			matches++
		}
	}
	return int(math.Pow(2, float64(matches-1)))
}

func fromLine(line string) (*Card, error) {
	card := new(Card)

	split := strings.Split(line, ":")
	metadata, nums := strings.Trim(split[0], " "), strings.Trim(split[1], " ")
	s := strings.Split(metadata, " ")
	id, err := strconv.Atoi(s[len(s)-1])
	if err != nil {
		return nil, err
	}
	card.Id = id

	split = strings.Split(nums, "|")
	left, right := strings.Trim(split[0], " "), strings.Trim(split[1], " ")

	winNums := strings.Split(left, " ")
	for _, num := range winNums {
		num = strings.Trim(num, " ")
		if num == "" {
			continue
		}
		n, err := strconv.Atoi(num)
		if err != nil {
			return nil, err
		}
		card.WinNums = append(card.WinNums, n)
	}

	myNums := strings.Split(right, " ")
	for _, num := range myNums {
		num = strings.Trim(num, " ")
		if num == "" {
			continue
		}
		n, err := strconv.Atoi(num)
		if err != nil {
			return nil, err
		}
		card.MyNums = append(card.MyNums, n)
	}

	return card, nil
}

func Solution1() error {
	file, err := os.Open("./day4/input.txt")
	if err != nil {
		return err
	}
	defer file.Close()
	
	cards := make([]Card, 0)
	scnr := bufio.NewScanner(file)
	for scnr.Scan() {
		line := scnr.Text()
		card, err := fromLine(line)
		if err != nil {
			return err
		}
		cards = append(cards, *card)
	}
	points := 0
	for _, card := range cards {
		points += card.points()
	}
	fmt.Println(points)
	return nil
}
