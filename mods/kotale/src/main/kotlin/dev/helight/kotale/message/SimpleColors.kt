package dev.helight.kotale.message

import java.awt.Color

object SimpleColors {
    val black = Color(0, 0, 0)
    val darkBlue = Color(0, 0, 170)
    val darkGreen = Color(0, 170, 0)
    val darkAqua = Color(0, 170, 170)
    val darkRed = Color(170, 0, 0)
    val darkPurple = Color(170, 0, 170)
    val gold = Color(255, 170, 0)
    val gray = Color(170, 170, 170)
    val darkGray = Color(85, 85, 85)
    val blue = Color(85, 85, 255)
    val green = Color(85, 255, 85)
    val aqua = Color(85, 255, 255)
    val red = Color(255, 85, 85)
    val lightPurple = Color(255, 85, 255)
    val yellow = Color(255, 255, 85)
    val white = Color(255, 255, 255)

    val charMap = mapOf(
        '0' to black,
        '1' to darkBlue,
        '2' to darkGreen,
        '3' to darkAqua,
        '4' to darkRed,
        '5' to darkPurple,
        '6' to gold,
        '7' to gray,
        '8' to darkGray,
        '9' to blue,
        'a' to green,
        'c' to red,
        'd' to lightPurple,
        'e' to yellow,
        'f' to white,
        'g' to aqua,
    )
}