package day5

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"

	"github.com/samber/lo"
)

var (
	seedToSoil = make([]ConvMap, 0)
	soilToFert = make([]ConvMap, 0)
	fertToWtr  = make([]ConvMap, 0)
	wtrToLght  = make([]ConvMap, 0)
	lghtToTmp = make([]ConvMap, 0)
	tmpToHum  = make([]ConvMap, 0)
	humToLoc   = make([]ConvMap, 0)
)

type ConvMap struct {
	src int
	dst int
	rng int
}

func fromLine(line string) ConvMap {
	cMap := new(ConvMap)

	split := lo.Map(strings.Split(line, " "), func(x string, _ int) int {
		num, err := strconv.Atoi(x)
		if err != nil {
			panic(err)
		}
		return num
	})

	// The problem specifies x-to-y map but specifies a map line as 'dst src rng'.
	// My brain gets confused by this cause i expect src to be before dst.
	cMap.src = split[1]
	cMap.dst = split[0]
	cMap.rng = split[2]

	return *cMap
}

func Solution1() error {
	file, err := os.Open("./day5/input.txt")
	if err != nil {
		return err
	}
	defer file.Close()

	scnr := bufio.NewScanner(file)

	scnr.Scan()
	line := scnr.Text()
	seeds := lo.Map(strings.Split(strings.Trim(strings.Split(line, ":")[1], " "), " "), func(x string, _ int) int {
		num, err := strconv.Atoi(x)
		if err != nil {
			panic(err)
		}
		return num
	})

	readMaps(scnr, &seedToSoil)
	readMaps(scnr, &soilToFert)
	readMaps(scnr, &fertToWtr)
	readMaps(scnr, &wtrToLght)
	readMaps(scnr, &lghtToTmp)
	readMaps(scnr, &tmpToHum)
	readMaps(scnr, &humToLoc)

	locs := make([]int, 0)

	for _, seed := range seeds {
		soil := srcToDst(seed, seedToSoil)
		fert := srcToDst(soil, soilToFert)
		wtr := srcToDst(fert, fertToWtr)
		lght := srcToDst(wtr, wtrToLght)
		tmp := srcToDst(lght, lghtToTmp)
		hum := srcToDst(tmp, tmpToHum)
		loc := srcToDst(hum, humToLoc)
		
		locs = append(locs, loc)
	}

	fmt.Println(lo.Min(locs))
	return nil
}

func srcToDst(val int, maps []ConvMap) int {
	for _, m := range maps {
		if val > m.src && val < m.src + m.rng {
			return m.dst + (val-m.src)
		}
	}
	return val
}

func readMaps(scnr *bufio.Scanner, maps *[]ConvMap) {
	var line string
	scnr.Scan()
	scnr.Scan()
	for scnr.Scan() {
		line = scnr.Text()
		if line == "" {
			break
		}

		*maps = append(*maps, fromLine(line))
	}
}
