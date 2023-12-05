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
	seedToSoil2 = make([]ConvMap2, 0)
	soilToFert2 = make([]ConvMap2, 0)
	fertToWtr2  = make([]ConvMap2, 0)
	wtrToLght2  = make([]ConvMap2, 0)
	lghtToTmp2  = make([]ConvMap2, 0)
	tmpToHum2   = make([]ConvMap2, 0)
	humToLoc2   = make([]ConvMap2, 0)
)

type ConvMap2 struct {
	src int
	dst int
	rng int
}

func fromLine2(line string) ConvMap2 {
	cMap := new(ConvMap2)

	split := lo.Map(strings.Split(line, " "), func(x string, _ int) int {
		num, err := strconv.Atoi(x)
		if err != nil {
			panic(err)
		}
		return num
	})

	// The problem specifies x-to-y map but specifies a map line as 'dst src rng'.
	// My brain gets confused by this cause I expect src to be before dst.
	cMap.src = split[1]
	cMap.dst = split[0]
	cMap.rng = split[2]

	return *cMap
}

func Solution2() error {
	file, err := os.Open("./day5/testinput.txt")
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

	readMaps2(scnr, &seedToSoil2)
	readMaps2(scnr, &soilToFert2)
	readMaps2(scnr, &fertToWtr2)
	readMaps2(scnr, &wtrToLght2)
	readMaps2(scnr, &lghtToTmp2)
	readMaps2(scnr, &tmpToHum2)
	readMaps2(scnr, &humToLoc2)

	locs := make([]int, 0)

	for i := 0; i < len(seeds); i += 2 {
		for seed := seeds[i]; seed < seeds[i]+seeds[i+1]; seed++ {
			soil := srcToDst2(seed, seedToSoil2)
			fert := srcToDst2(soil, soilToFert2)
			wtr := srcToDst2(fert, fertToWtr2)
			lght := srcToDst2(wtr, wtrToLght2)
			tmp := srcToDst2(lght, lghtToTmp2)
			hum := srcToDst2(tmp, tmpToHum2)
			loc := srcToDst2(hum, humToLoc2)
			// fmt.Printf("seed: %v, %v %v %v %v %v %v %v\n", seed, soil, fert, wtr, lght, tmp, hum, loc)
			locs = append(locs, loc)
		}
	}

	fmt.Println(lo.Min(locs))
	return nil
}

func srcToDst2(val int, maps []ConvMap2) int {
	for _, m := range maps {
		if val >= m.src && val < m.src+m.rng {
			return m.dst + (val - m.src)
		}
	}
	return val
}

func readMaps2(scnr *bufio.Scanner, maps *[]ConvMap2) {
	var line string
	for scnr.Scan() {
		line := scnr.Text()
		if strings.Contains(line, ":") {
			break
		}
	}

	for scnr.Scan() {
		line = scnr.Text()
		if line == "" {
			break
		}

		*maps = append(*maps, fromLine2(line))
	}
}
