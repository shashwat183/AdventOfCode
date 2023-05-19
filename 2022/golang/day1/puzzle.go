package day1

import (
	"fmt"
	"log"
	"os"
	"sort"
	"strconv"
	"strings"
)

func Solution1() {
  data, err := os.ReadFile("./day1/input.txt")

  if err!= nil {
    log.Fatal(err)
  }

  input := string(data)
  input = strings.TrimSpace(input)
  inputs := strings.Split(input, "\n\n")

  listOfCalories := [][]int{}

  for _, input := range inputs {

    splits := strings.Split(input, "\n")
    currCalories := []int{}

    for _, split := range splits {
      cal, err := strconv.Atoi(split)
      if err != nil {
        log.Fatal(err)
      }

      currCalories = append(currCalories, cal)
    }

    listOfCalories = append(listOfCalories, currCalories)
  }

  maxCalSum := 0
  for _, calories := range listOfCalories {
    currSum := 0
    for _, calorie := range calories {
      currSum = currSum + calorie
    }
    if currSum > maxCalSum {
      maxCalSum = currSum
    }
  }
  fmt.Println(maxCalSum)
}

func Solution2() {
  data, err := os.ReadFile("./day1/input.txt")

  if err!= nil {
    log.Fatal(err)
  }

  input := string(data)
  input = strings.TrimSpace(input)
  inputs := strings.Split(input, "\n\n")

  listOfCalories := [][]int{}

  for _, input := range inputs {

    splits := strings.Split(input, "\n")
    currCalories := []int{}

    for _, split := range splits {
      cal, err := strconv.Atoi(split)
      if err != nil {
        log.Fatal(err)
      }

      currCalories = append(currCalories, cal)
    }

    listOfCalories = append(listOfCalories, currCalories)
  }
  
  calSums := []int{}
  for _, calories := range listOfCalories {
    currSum := 0
    for _, calorie := range calories {
      currSum = currSum + calorie
    }
    calSums = append(calSums, currSum)
  }
  sort.Ints(calSums)
  res := calSums[len(calSums)-1] + calSums[len(calSums)-2] + calSums[len(calSums)-3]
  fmt.Println(res)
}
