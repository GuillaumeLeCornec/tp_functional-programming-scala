import com.github.polomarcus.utils.ClimateService
import com.github.polomarcus.model.CO2Record
import org.scalatest.funsuite.AnyFunSuite

//@See https://www.scalatest.org/scaladoc/3.1.2/org/scalatest/funsuite/AnyFunSuite.html
class ClimateServiceTest extends AnyFunSuite {
  test("containsWordGlobalWarming - non climate related words should return false") {
    assert( ClimateService.isClimateRelated("pizza") == false)
  }

  test("isClimateRelated - climate related words should return true") {
    assert(ClimateService.isClimateRelated("climate change") == true)
    assert(ClimateService.isClimateRelated("IPCC")== true)
  }

  //@TODO
  test("parseRawData") {
    // our inputs
    val firstRecord = (2003, 1, 355.2)     //help: to acces 2003 of this tuple, you can do firstRecord._1
    val secondRecord = (2004, 1, 375.2)
    val list1 = List(firstRecord, secondRecord)

    // our output of our method "parseRawData"
    val co2RecordWithType = CO2Record(firstRecord._1, firstRecord._2, firstRecord._3)
    val co2RecordWithType2 = CO2Record(secondRecord._1, secondRecord._2, secondRecord._3)
    val output = List(Some(co2RecordWithType), Some(co2RecordWithType2))

    // we call our function here to test our input and output
    assert(ClimateService.parseRawData(list1) == output)
  }



  test("getMinMax") {
    val records = List(
      CO2Record(year = 2000, month = 1, ppm = 12),
      CO2Record(year = 2000, month = 2, ppm = 4),
      CO2Record(year = 2000, month = 3, ppm = 2),
      CO2Record(year = 2000, month = 4, ppm = 6),
      CO2Record(year = 2000, month = 5, ppm = 5)
    )

    val (minValue, maxValue) = ClimateService.getMinMax(records)
    assert(minValue == 2)
    assert(maxValue == 12)
  }

  test("getMinMaxByYear") {
    val records = List(
      CO2Record(year = 2000, month = 1, ppm = 12),
      CO2Record(year = 2001, month = 2, ppm = 10),
      CO2Record(year = 2001, month = 3, ppm = 9),
      CO2Record(year = 2001, month = 4, ppm = 6),
      CO2Record(year = 2000, month = 5, ppm = 5)
    )

    val (minValue, maxValue) = ClimateService.getMinMaxByYear(records, 2001)
    assert(minValue == 6)
    assert(maxValue == 10)
  }

  test("minMaxDifference") {
    val records = List(
      CO2Record(year = 2000, month = 1, ppm = 12),
      CO2Record(year = 2001, month = 2, ppm = 10),
      CO2Record(year = 2001, month = 3, ppm = 9),
      CO2Record(year = 2001, month = 4, ppm = 6),
      CO2Record(year = 2000, month = 5, ppm = 5)
    )

    val diff = ClimateService.minMaxDifference(records, 2001)
    assert(diff == 4)
  }

  test("filterDecemberData") {
    val records = List(
      CO2Record(year = 2000, month = 1, ppm = 12),
      CO2Record(year = 2001, month = 12, ppm = 10),
      CO2Record(year = 2001, month = 3, ppm = 9),
      CO2Record(year = 2001, month = 4, ppm = 6),
      CO2Record(year = 2000, month = 12, ppm = 5)
    )
    val expectedFilteredRecords = List(
      CO2Record(year = 2000, month = 1, ppm = 12),
      CO2Record(year = 2001, month = 3, ppm = 9),
      CO2Record(year = 2001, month = 4, ppm = 6)
    )
    val filteredList = ClimateService.filterDecemberData(records)
    assert(filteredList == expectedFilteredRecords)
  }

}
