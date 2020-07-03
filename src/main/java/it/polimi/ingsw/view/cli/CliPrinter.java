package it.polimi.ingsw.view.cli;
/**
 * Class that helps the printing of the CLI of the game.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class CliPrinter {

    // Reset
    /**
     * Prints a text reset for the CLI.
     */
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    /**
     * Prints the yellow color for the CLI.
     */
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    /**
     * Prints the white color for the CLI.
     */
    public static final String WHITE = "\033[0;37m";   // WHITE
    /**
     * Prints the blue color for the CLI.
     */
    public static final String BLUE = "\033[0;34m";    // BLUE

    // Bold Colors
    /**
     * Prints the bold green color for the CLI.
     */
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    /**
     * Prints the blue purple color for the CLI.
     */
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    /**
     * Prints the bold purpole color for the CLI.
     */
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    /**
     * Prints the bold cyan color for the CLI.
     */
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN

    // Underlined
    /**
     * Prints the red underline for the CLI.
     */
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    /**
     * Prints the cyan underline for the CLI.
     */
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN

    // High Intensity
    /**
     * Prints the high intensity bright black color for the CLI.
     */
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK

    // Bold High Intensity
    /**
     * Prints the bold high intensity bold black color for the CLI.
     */
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK

    // Background
    /**
     * Prints the background bright black for the CLI.
     */
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK


    // Color Indicators
    /**
     * Prints the external color for the CLI.
     */
    public static final String EXT_COLOR = WHITE;
    /**
     * Prints the grid color for the CLI.
     */
    public static final String GRID_COLOR = BLACK_BRIGHT;
    /**
     * Prints the index color for the CLI.
     */
    public static final String INDEX_COLOR = YELLOW;
    /**
     * Prints the logo color for the CLI.
     */
    public static final String LOGO_COLOR = BLUE;

    /**
     * Prints the dome color for the CLI.
     */
    public static final String DOME_COLOR = BLUE_BOLD;
    /**
     * Prints the block color for the CLI.
     */
    public static final String BLOCK_COLOR = WHITE;
    /**
     * Prints the player 0 for the CLI.
     */
    public static final String PLAYER_0 = CYAN_BOLD;
    /**
     * Prints the player 1 for the CLI.
     */
    public static final String PLAYER_1 = PURPLE_BOLD;
    /**
     * Prints the player 2 for the CLI.
     */
    public static final String PLAYER_2 = GREEN_BOLD;

    /**
     * Prints the chosen color for the tile for the CLI.
     */
    public static final String CHOSEN_COLOR = RED_UNDERLINED;
    /**
     * Prints the "can do operation" for the CLI.
     */
    public static final String CAN_OP_COLOR = BLACK_BACKGROUND_BRIGHT;


    // Board Characters
    /**
     * Prints the horizontal bar for the CLI.
     */
    public static final String horizontalBar = GRID_COLOR + "────║───────┼───────┼───────┼───────┼───────║" + RESET;
    /**
     * Prints the vertical bar for the CLI.
     */
    public static final String verticalBar = GRID_COLOR + "│" + RESET;
    /**
     * Prints the superior board for the CLI.
     */
    public static final String supBoard = GRID_COLOR + "────╔═══════╤═══════╤═══════╤═══════╤═══════╗" + RESET;
    /**
     * Prints the inferior board for the CLI.
     */
    public static final String infBoard = GRID_COLOR + "────╚═══════╧═══════╧═══════╧═══════╧═══════╝" + RESET;
    /**
     * Prints the vertical board for the CLI.
     */
    public static final String verticalBoard = GRID_COLOR + "║" + RESET;

    // Frame Characters
    /**
     * Prints the upExt frame for the CLI.
     */
    public static final String upExt = EXT_COLOR + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + RESET;
    /**
     * Print the downExt frame for the CLI.
     */
    public static final String downExt = EXT_COLOR + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET;
    /**
     * Prints the leftExt frame for the CLI.
     */
    public static final String leftExt = EXT_COLOR + "┃   " + RESET;
    /**
     * Prints the centerExt frame for the CLI.
     */
    public static final String centerExt = EXT_COLOR + "   ┃" + RESET;
    /**
     * Prints the emptyLeft frame for the CLI.
     */
    public static final String emptyLeft = EXT_COLOR + "┃                                                   ┃" + RESET;
    /**
     * Prints the emptyRight frame for the CLI.
     */
    public static final String emptyRight = EXT_COLOR + "                                       ┃" + RESET;

}
