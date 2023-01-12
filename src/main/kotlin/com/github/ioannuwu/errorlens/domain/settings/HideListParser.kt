package com.github.ioannuwu.errorlens.domain.settings

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

    class DividedByNewLine : HideListParser {
        override fun parseToList(string: String): List<String> =
            string.split('\n').map { it.trim() }

        override fun parseToString(list: List<String>): String =
                list.joinToString("\n")

    }
}