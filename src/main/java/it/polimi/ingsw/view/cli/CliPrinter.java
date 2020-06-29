package it.polimi.ingsw.view.cli;

public class CliPrinter {

    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String WHITE = "\033[0;37m";   // WHITE
    public static final String BLUE = "\033[0;34m";    // BLUE

    // Bold Colors
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN

    // Underlined
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN

    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK

    // Background
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK


    // Color Indicators
    public static final String EXT_COLOR = WHITE;
    public static final String GRID_COLOR = BLACK_BRIGHT;
    public static final String INDEX_COLOR = YELLOW;
    public static final String LOGO_COLOR = BLUE;

    public static final String DOME_COLOR = BLUE_BOLD;
    public static final String BLOCK_COLOR = WHITE;

    public static final String PLAYER_0 = CYAN_BOLD;
    public static final String PLAYER_1 = PURPLE_BOLD;
    public static final String PLAYER_2 = GREEN_BOLD;

    public static final String CHOSEN_COLOR = RED_UNDERLINED;
    public static final String CAN_OP_COLOR = BLACK_BACKGROUND_BRIGHT;


    // Board Characters
    public static final String horizontalBar = GRID_COLOR + "────║───────┼───────┼───────┼───────┼───────║" + RESET;
    public static final String verticalBar = GRID_COLOR + "│" + RESET;
    public static final String supBoard = GRID_COLOR + "────╔═══════╤═══════╤═══════╤═══════╤═══════╗" + RESET;
    public static final String infBoard = GRID_COLOR + "────╚═══════╧═══════╧═══════╧═══════╧═══════╝" + RESET;
    public static final String verticalBoard = GRID_COLOR + "║" + RESET;

    // Frame Characters
    public static final String upExt = EXT_COLOR + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + RESET;
    public static final String downExt = EXT_COLOR + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET;
    public static final String leftExt = EXT_COLOR + "┃   " + RESET;
    public static final String centerExt = EXT_COLOR + "   ┃" + RESET;
    public static final String emptyLeft = EXT_COLOR + "┃                                                   ┃" + RESET;
    public static final String emptyRight = EXT_COLOR + "                                       ┃" + RESET;

}
