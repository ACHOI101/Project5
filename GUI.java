package prj5;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import cs2.Button;
import cs2.Shape;
import cs2.TextShape;
import cs2.Window;
import cs2.WindowSide;

/**
 * @author achoi
 * @version 11/27/2021
 * 
 * @param <T>
 *            T
 *
 */
public class GUI<T> {

    // I haven't worked out the specifics yet cause I need the classes
    public static final int BAR_WDITH = 100;
    public static final int BAR_HEIGHT = 5;
    public static final int BAR_GAP = 20;
    private ArrayList<Object[]> stateButtons;
    private Button sortByAlpha;
    private Button sortByCFR;
    private Button quit;
    private Window window;
    private LinkedList<T> activeList;
    private int startBarX = 500;
    private int startBarY = 500;

    private CaseReader<T> caseReader;

    /**
     * Constructor
     * 
     * @param reader
     *            reader
     */
    public GUI(CaseReader<T> reader) {
        stateButtons = new ArrayList<Object[]>();
        caseReader = reader;
        window = new Window();

        // Uses the user's screen dimensions to set initial window size
        // This is something that I (Andrew Choi) thought of so if it doesn't do
        // well
        // just penalize me please
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int userWidth = (int)screenSize.getWidth();
        int userHeight = (int)screenSize.getHeight() - 30;

        window.setSize(userWidth, userHeight);
        window.setTitle("Case Fatalities Ratio by Race");
        buildStateButtons();
        sortByAlpha = new Button("Sort By Alpha");
        quit = new Button("Quit");
        sortByCFR = new Button("Sort By CFR");
        sortByAlpha.onClick(this, "sortActiveListAlpha");
        quit.onClick(this, "quitWindow");
        sortByCFR.onClick(this, "sortActiveListCFR");
        window.addButton(sortByAlpha, WindowSide.NORTH);
        window.addButton(quit, WindowSide.NORTH);
        window.addButton(sortByCFR, WindowSide.NORTH);
    }


    /**
     * An array list will sort and build the state buttons
     * 
     * @param <T>
     *            T
     * 
     */
    public void buildStateButtons() {
        Iterator<LinkedList<T>> state_ll_iter = caseReader.getStateLinkedLists()
            .iterator();

        while (state_ll_iter.hasNext()) {
            String state_name = state_ll_iter.next().getStateName();
            Button genericStateBtn = new Button("Represent " + state_name);
            genericStateBtn.onClick(this, "clickedStateButton");
            Object[] buttonSet = { state_name, genericStateBtn };
            stateButtons.add(buttonSet);
            window.addButton((Button)buttonSet[1], WindowSide.SOUTH);
        }
    }


    /**
     * State Button 
     * 
     * @param button
     *            that was clicked for a specific state
     */
    public void clickedStateButton(Button button) {
        clearAllShapes();
        window.removeAllShapes();
        for (Object[] buttonSet : stateButtons) {
            if (buttonSet[1].equals(button)) {
                buildBarGraphs((String)buttonSet[0]);
            }
        }
    }


    /**
     * Sort list by race Alpha
     * 
     * @param button
     *            that was clicked
     */
    public void sortListAlpha(Button button) {
        clearAllShapes();
        activeList.sortByRace();
        buildBarGraphs(activeList.getStateName());
    }


    /**
     * Sorts the active list by race CFR
     * 
     * @param button
     *            that was clicked
     */
    public void sortActiveListCFR(Button button) {
        clearAllShapes();
        activeList.sortByCFR();
        buildBarGraphs(activeList.getStateName());
    }


    /**
     * Quits the window
     * 
     * @param button
     *            that was clicked
     */
    public void quitWindow(Button button) {
        System.exit(0);
    }


    /**
     * Clear all the shapes
     */
    private void clearAllShapes() {
        window.removeAllShapes();
        startBarX = 600;
        startBarY = 800;
    }


    /**
     * Builds the bar graphs based on the data from the reader
     * 
     * @param stateName
     *            the name of the state correlated with the dynamic
     *            buttons clicked
     */
    public void buildBarGraphs(String stateName) {
        Iterator<LinkedList<T>> state_ll_iter = caseReader.getStateLinkedLists()
            .iterator();

        while (state_ll_iter.hasNext()) {
            activeList = state_ll_iter.next();
            if (activeList.getStateName().equals(stateName)) {
                break;
            }
        }

        Iterator<T> ll_iter = activeList.iterator();
        while (ll_iter.hasNext()) {
            CaseData caseData = (CaseData)ll_iter.next();
            buildSingleBar(caseData);
        }
        // Title of graph
        TextShape stateText = new TextShape(startBarX - 100, startBarY - 100,
            stateName, Color.black, 10);
        TextShape titleText = new TextShape(startBarX - 200, startBarY - 200,
            "Case Fatalities Ratio by Race", Color.black, 10);

        window.addShape(stateText);
        window.addShape(titleText);
    }


    /**
     * Builds the text displayed with the bar graphs to indicate the details
     * 
     * @param race
     *            data used to label CFR
     */
    public void buildRaceTextShape(String race) {
        TextShape raceText = new TextShape(startBarX, startBarY + 16, race,
            Color.black, 14);
        window.addShape(raceText);
    }


    /**
     * Builds the text displayed with the bar graphs to indicate the details
     * 
     * @param cfr
     *            calculated CFR for a specific race
     */
    public void buildCFRTextShape(double cfr) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        TextShape cfrText = new TextShape(startBarX, startBarY + 37,
            decimalFormat.format(cfr) + "%", Color.black, 14);
        window.addShape(cfrText);
    }


    /**
     * Builds the individual bar graphs
     * 
     * @param caseData
     *            the case data from a node of the active list
     */
    public void buildSingleBar(CaseData caseData) {
        int barHeight = (int)(caseData.getCFR() * 10);
        if (barHeight < 0);

            // Random values for now
            TextShape shape = new TextShape(startBarX, startBarY - 50, "N/A",
                Color.black, 50);
            window.addShape(shape);
            buildRaceTextShape(caseData.getRace());

        }
        else {
            Shape shape = new Shape(startBarX, startBarY - (barHeight / 2), 50,
                barHeight / 2, Color.blue);
            window.addShape(shape);
            buildRaceTextShape(caseData.getRace());
            buildCFRTextShape(caseData.getCFR());
        }
    }
}
