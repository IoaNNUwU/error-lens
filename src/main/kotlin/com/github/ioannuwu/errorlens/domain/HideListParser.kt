package com.github.ioannuwu.errorlens.domain

interface HideListParser {
    fun parseToList(string: String): List<String>

    fun parseToString(list: List<String>): String

    class DividedByApostropheAndComa : HideListParser {
        private val separators: Array<String> =
                arrayOf(" ' , ' ", " ' , '", "' , '", " ', ' ", " ' ,' ", " ' ,'", "' ,'", "' ,' ",
                        " ', ' ", " ', '", "', '", "', ' ", "' ,'", "', '", "','")

        override fun parseToList(string: String): List<String> {
            val newString = string.dropLastWhile { it == ' ' || it == '\'' }
            val newNewString = newString.dropWhile { it == ' ' || it == '\'' }
            val list = newNewString.split(*separators)
            val newList = list.map { it.dropLastWhile { char -> char == ' ' } }
            return newList.map { it.dropWhile { char -> char == ' ' } }
        }

        override fun parseToString(list: List<String>): String {
            val temp = list.joinToString("', '")
            return "'$temp'"
        }
    }
}