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
	soilToSeed = make([]ConvMap3, 0)
	fertToSoil = make([]ConvMap3, 0)
	wtrToFert  = make([]ConvMap3, 0)
	lghtToWtr  = make([]ConvMap3, 0)
	tmpToLght  = make([]ConvMap3, 0)
	humToTmp   = make([]ConvMap3, 0)
	locToHum   = make([]ConvMap3, 0)
)

type ConvMap3 struct {
	src int
	dst int
	rng int
}

func fromLine3(line string) ConvMap3 {
	cMap := new(ConvMap3)

	split := lo.Map(strings.Split(line, " "), func(x string, _ int) int {
		num, err := strconv.Atoi(x)
		if err != nil {
			panic(err)
		}
		return num
	})

	// The problem specifies x-to-y map but specifies a map line as 'dst src rng'.
	// My brain gets confused by this cause i expect src to be before dst.
	cMap.src = split[0]
	cMap.dst = split[1]
	cMap.rng = split[2]

	return *cMap
}

func Solution3() error {
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


	readMaps3(scnr, &soilToSeed)
	readMaps3(scnr, &fertToSoil)
	readMaps3(scnr, &wtrToFert)
	readMaps3(scnr, &lghtToWtr)
	readMaps3(scnr, &tmpToLght)
	readMaps3(scnr, &humToTmp)
	readMaps3(scnr, &locToHum)


	for i := 0; i < 99999999999; i++ {
		hum := srcToDst3(i, locToHum)
		tmp := srcToDst3(hum, humToTmp)
		lght := srcToDst3(tmp, tmpToLght)
		wtr := srcToDst3(lght, lghtToWtr)
		fert := srcToDst3(wtr, wtrToFert)
		soil := srcToDst3(fert, fertToSoil)
		seed := srcToDst3(soil, soilToSeed)
		if validSeed(seed, seeds) {
			fmt.Println(i)
			break
		}
		// fmt.Printf("seed: %v, %v %v %v %v %v %v %v\n", seed, soil, fert, wtr, lght, tmp, hum, i)
	}

	return nil
}

func validSeed(seed int, seeds []int) bool {
	for i := 0; i < len(seeds); i += 2 {
		if seed >= seeds[i] && seed < seeds[i]+seeds[i+1] {
			return true
		}
	}
	return false
}

// val = 45, src 1, dst 0, rng  69
func srcToDst3(val int, maps []ConvMap3) int {
	for _, m := range maps {
		if val >= m.src && val < m.src+m.rng {
			return m.dst + (val - m.src)
		}
	}
	return val
}

func readMaps3(scnr *bufio.Scanner, maps *[]ConvMap3) {
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

		*maps = append(*maps, fromLine3(line))
	}
}

/*

seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

seed = 79
is in range 50 - 98(50+48)

soil = 52 + (79-50) = 81

Now then

seed-to-soil map:
50 98 2
52 50 48

soil = 81
is in range 52 - 100(52+48)

seed = 50 + (82-52) = 79
*/
