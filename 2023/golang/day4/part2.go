package day4

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"

	"golang.org/x/exp/slices"
)

type Card2 struct {
	Id      int
	WinNums []int
	MyNums  []int
	Count   int
}

func (c Card2) matches() int {
	matches := 0
	for _, myNum := range c.MyNums {
		if slices.Contains(c.WinNums, myNum) {
			matches++
		}
	}
	return matches
}

func fromLine2(line string) (*Card2, error) {
	card := new(Card2)

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
	card.Count = 1

	return card, nil
}

func Solution2() error {
	file, err := os.Open("./day4/input.txt")
	if err != nil {
		return err
	}
	defer file.Close()

	cards := make(map[int]*Card2, 0)
	scnr := bufio.NewScanner(file)
	for scnr.Scan() {
		line := scnr.Text()
		card, err := fromLine2(line)
		if err != nil {
			return err
		}
		cards[card.Id] = card
	}
	fmt.Println(processCards(cards))
	return nil
}

func processCards(cards map[int]*Card2) int {
	for id := 1; id < len(cards)+1; id++ {
		for i := 0; i < cards[id].Count; i++ {
			matches := cards[id].matches()
			for j := 1; j < matches+1; j++ {
				cards[id+j].Count++
			}
		}
	}
	numCards := 0
	for id := range cards {
		numCards += cards[id].Count
	}
	return numCards
}
