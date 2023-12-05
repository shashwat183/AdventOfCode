package day3

import (
	"bufio"
	"os"
)

func BSolution1() error {
	data := make([][]rune, 0)
	file, err := os.Open("./day3/input.txt")
	if err != nil {
		return err
	}
	defer file.Close()

	scnr := bufio.NewScanner(file)

	for scnr.Scan() {
		data = append(data, []rune(scnr.Text()))
	}
	return nil
}


