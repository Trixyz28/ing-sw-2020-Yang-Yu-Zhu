package it.polimi.ingsw.view.cli;

public class CliPrinter {

    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String WHITE = "\033[0;37m";   // WHITE

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
    public static final String gridColor = BLACK_BRIGHT;
    public static final String gridNumberColor = YELLOW;

    public static final String domeColor = BLUE_BOLD;
    public static final String heightColor = WHITE;

    public static final String player0 = CYAN_BOLD;
    public static final String player1 = PURPLE_BOLD;
    public static final String player2 = GREEN_BOLD;

    public static final String chosenColor = RED_UNDERLINED;
    public static final String canOpColor = BLACK_BACKGROUND_BRIGHT;


    // Board Characters
    public static final String horizontalBar = gridColor + "────║───────┼───────┼───────┼───────┼───────║" + RESET;
    public static final String verticalBar = gridColor + "│" + RESET;
    public static final String supBoard = gridColor + "────╔═══════╤═══════╤═══════╤═══════╤═══════╗" + RESET;
    public static final String infBoard = gridColor + "────╚═══════╧═══════╧═══════╧═══════╧═══════╝" + RESET;
    public static final String verticalBoard = gridColor + "║" + RESET;

    // Frame Characters
    public static final String extColor = WHITE;
    public static final String upExt = extColor + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + RESET;
    public static final String downExt = extColor + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET;
    public static final String leftExt = extColor + "┃   " + RESET;
    public static final String rightExt = extColor + "   ┃" + RESET;
    public static final String emptyExt = extColor + "┃                                                   ┃" + RESET;

}
