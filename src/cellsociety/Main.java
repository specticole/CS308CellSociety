package cellsociety;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main {
    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        CellularAutomatonController testParser = new CellularAutomatonController("ControllerTest.xml");
        testParser.loadConfigFile();
    }
}
