package sorting

import java.io.File
import java.io.InputStream
import java.util.*
import kotlin.math.roundToInt

fun main(args: Array<String>) {
    // write your code here
    var dataType = ""
    var sortingType = ""
    var inputFile = ""
    var outputFile = ""
    var switchDataType = false
    var switchSortingType = false
    var switchInputFile = false
    var switchOutputFile = false

    for (arg in args) {
        if (arg == "-dataType") {
            switchDataType = true
        } else if (arg == "-sortingType") {
            switchSortingType = true
        } else if (switchDataType) {
            dataType = arg
            switchDataType = false
        } else if (switchSortingType) {
            sortingType = arg
            switchSortingType = false
        } else if (arg == "-inputFile") {
            switchInputFile = true
        } else if (arg == "-outputFile") {
            switchOutputFile = true
        } else if (switchInputFile) {
            inputFile = arg
            switchInputFile = false
        } else if (switchOutputFile) {
            outputFile = arg
            switchOutputFile = false
        } else {
            println("\"$arg\" is not a valid parameter. It will be skipped.")
        }
    }

    if (dataType == "" && switchDataType) {
        println("No data type defined!")
    }

    if (sortingType == "" && switchSortingType) {
        println("No sorting type defined!")
    }

    if (inputFile == "" && switchInputFile) {
        println("No input file defined!")
    }

    if (outputFile == "" && switchOutputFile) {
        println("No output defined!")
    }

    if (switchInputFile) {
        val inputStream: InputStream = File(inputFile).inputStream()
        val stringStream = inputStream.bufferedReader().use { it.readText() }

        when (dataType) {
            "long" -> StatisticsNumbers(sortingType, stringStream)
            "word" -> StatisticsWords(sortingType, stringStream)
            "line" -> StatisticsLines(sortingType, stringStream)
        }
    } else {
        when (dataType) {
            "long" -> StatisticsNumbers(sortingType)
            "word" -> StatisticsWords(sortingType)
            "line" -> StatisticsLines(sortingType)
        }
    }


}

class StatisticsNumbers(val sortingType: String, val inputText: String) {
    val numbers = readNumbers()
    val count = numbers.size
    var isFileProvided = true

    constructor(sortingType: String) : this(sortingType, ""){
        isFileProvided = false
    }

    init {
        when (sortingType) {
            "byCount" -> sortByCount(numbers)
            "natural" -> sortNaturally(numbers)
            else -> sortNaturally(numbers) //errorHandling(sortingType)
        }
    }

    fun sortByCount(numbers: List<Int>) {
        println("Total numbers: $count.")
        val map = numbers.groupingBy { it }.eachCount()
        for ((key, value) in map.toList().sortedBy { (k, v) -> k }.sortedBy { (k, v) -> v }) {
            println("$key: $value time(s), ${(value * 100 / count.toDouble()).roundToInt()}%")
        }
    }

    private fun sortNaturally(numbers: List<Int>) {
        println("Total numbers: $count.")
        print("Sorted data: ")
        println(numbers.sorted().joinToString(" ", "", ""))
    }

    private fun readNumbers(): List<Int> {
        return if (isFileProvided) {
            inputText.split("\\s".toRegex()).map { it.toInt() }
        } else {
            val scanner = Scanner(System.`in`)
            val numbers = mutableListOf<Int>()
            do {
                val n = scanner.next()
                if (n.toIntOrNull() == null) {
                    println("\"$n\" is not a long. It will be skipped.")
                }
                numbers.add(n.toInt())
            } while (scanner.hasNext())


            numbers
        }
    }
}

class StatisticsLines(val sortingType: String, val inputText: String) {
    val lines = readLines()
    val count = lines.size
    var isFileProvided = true

    constructor(sortingType: String) : this(sortingType, ""){
        isFileProvided = false
    }

    init {
        println("Total lines: $count.")
        if (sortingType == "byCount") {
            sortByCount(lines)
        } else {
            sortNaturally(lines)
        }
    }

    fun sortByCount(words: List<String>) {
        val map = words.groupingBy { it }.eachCount()
        for ((key, value) in map.toList().sortedBy { (k, v) -> k }.sortedBy { (k, v) -> v }) {
            println("$key: $value time(s), ${(value * 100 / count.toDouble()).roundToInt()}%")
        }
    }

    private fun sortNaturally(words: List<String>) {
        println("Sorted data: ")
        println(words.sorted().joinToString("\n", "", ""))
    }

    private fun readLines(): List<String> {
        return if (isFileProvided) {
            inputText.split("\n".toRegex())
        } else {
            val scanner = Scanner(System.`in`)
            val words = mutableListOf<String>()
            do {
                words.add(scanner.nextLine())
            } while (scanner.hasNextLine())


            words
        }
    }
}

class StatisticsWords(val sortingType: String, val inputText: String) {
    val words = readWords()
    val count = words.size
    var isFileProvided = true

    constructor(sortingType: String) : this(sortingType, ""){
        isFileProvided = false
    }

    init {
        println("Total words: $count.")
        if (sortingType == "byCount") {
            sortByCount(words)
        } else {
            sortNaturally(words)
        }
    }

    fun sortByCount(words: List<String>) {
        val map = words.groupingBy { it }.eachCount()
        for ((key, value) in map.toList().sortedBy { (k, v) -> k }.sortedBy { (k, v) -> v }) {
            println("$key: $value time(s), ${(value * 100 / count.toDouble()).roundToInt()}%")
        }
    }

    private fun sortNaturally(words: List<String>) {
        print("Sorted data: ")
        println(words.sorted().joinToString(" ", "", ""))
    }

    private fun readWords(): List<String> {
        return if (isFileProvided) {
            inputText.split("\\s+".toRegex())
        } else {
            val scanner = Scanner(System.`in`)
            val words = mutableListOf<String>()
            do {
                words.add(scanner.next())
            } while (scanner.hasNext())
            words
        }
    }
}