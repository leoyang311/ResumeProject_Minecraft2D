package homework9

import org.openrndr.application
import org.openrndr.draw.loadFont
import org.openrndr.shape.Rectangle
import org.openrndr.writer
import kotlin.system.exitProcess

const val CELL_WIDTH = 64
const val CELL_HEIGHT = 64
const val NUM_COLS = 10
const val NUM_ROWS = 10
const val GRID_WIDTH = CELL_WIDTH * NUM_COLS
const val GRID_HEIGHT = CELL_HEIGHT * NUM_ROWS

const val TEXTAREA_HEIGHT = 240.0
const val TEXTAREA_WIDTH = GRID_WIDTH.toDouble()
const val TEXTAREA_HORIZONTAL_MARGIN = 20.0
const val FONT_SIZE = 24.0
const val NUM_TEXT_LINES = (TEXTAREA_HEIGHT / FONT_SIZE).toInt()

fun main() = application {
    configure {
        width = GRID_WIDTH
        height = GRID_HEIGHT + TEXTAREA_HEIGHT.toInt()
        title = "Fundies Homework 9"
    }

    program {
        val font = loadFont("data/fonts/default.otf", 24.0)

        keyboard.keyDown.listen {
            if (it.name == "escape") {
                // This *should* exit the program gracefully but doesn't always work.
                program.application.exit()
                // This will definitely end the program.
                exitProcess(0)
            }
            Game.player.lastKeyPressed = it.name
            Game.tick()
        }

        extend {
            for (x in 0 until NUM_COLS) {
                for (y in 0 until NUM_ROWS) {
                    Game.getImage(x, y)?.let {
                        drawer.image(it, x * CELL_WIDTH.toDouble(), y * CELL_HEIGHT.toDouble())
                    }
                }
            }

            drawer.fontMap = font

            writer {
                box = Rectangle(
                    TEXTAREA_HORIZONTAL_MARGIN,
                    GRID_HEIGHT.toDouble(),
                    TEXTAREA_WIDTH,
                    TEXTAREA_HEIGHT
                )

                for (s in Game.textToPrint.takeLast(NUM_TEXT_LINES)) {
                    newLine()
                    text(s)
                }
            }
        }
    }
}
