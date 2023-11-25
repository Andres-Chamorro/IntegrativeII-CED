package com.example.pipegame.control;

import com.example.pipegame.ApplicationMain;
import com.example.pipegame.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class GameController implements Initializable {

    @FXML
    private Label vText;
    @FXML
    private GridPane board;
    @FXML
    private Canvas canvas;
    @FXML
    private Button validateButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button giveUpButton;
    private GraphicsContext gc;
    private Vertex<Pipe> sourceVertex;
    private Vertex<Pipe> drainVertex;
    private IGraph<Pipe> graph;
    private boolean handleGridClickEnabled = true;
    private int currentImageIndex = 1;
    private boolean[][] blockedCells;
    private Image source, drain;
    private Calendar startTime;
    private boolean isVertical;
    public static int selectedGraphMode;
    private final ArrayList<Pipe> pipesOnScreen = new ArrayList<>();

/**
 * This function initializes the JavaFX application by setting up the graphics context, determining the
 * direction of the flow, creating the graph data structure based on the selected mode, initializing
 * the game, and setting up the mouse click event handler.
 * 
 * @param url The `url` parameter is the location of the FXML file that defines the user interface. It
 * is used to load the FXML file and initialize the user interface components.
 * @param resourceBundle The `resourceBundle` parameter is a bundle of resources that can be used to
 * localize an application. It contains localized versions of the strings and other resources that are
 * used in the application.
 */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        int direction = (int)(Math.random() * 2) + 1;
        isVertical = direction == 1;
        getSourceAndDrainImage();
        if (selectedGraphMode == 1) {
            graph = new GraphAdjacentyList<>();
        } else if (selectedGraphMode == 2) {
            graph = new GraphAdjacentyMatriz<>();
        }
        initializeGame();
        board.setOnMouseClicked(this::handleGridClick);
    }

    /**
     * The function initializes a game by generating blocked cells, initializing a graph, adding source
     * and drain vertices, building the graph without pipes, and checking if there is a path from the
     * source to the drain vertex.
     */
    private void initializeGame() {
        generateBlockedCells();
        Platform.runLater(() -> {
            initializeGraph();
            addSourceAndDrainVertex();
            buildGraphWithoutPipes();
            if (path().contains(drainVertex)) {
                startTime = Calendar.getInstance();
                paintFountainAndDraw();
                graph.removeAllEdges();
            } else {
                ApplicationMain.showAlert(Alert.AlertType.WARNING,"Warning","Game without solution","Sorry, the generated game has no solution. Please try again.");
                ApplicationMain.hideWindow((Stage)vText.getScene().getWindow());
                ApplicationMain.showWindow("hello-view", null);
            }
        });
    }

    /**
     * The function returns an ArrayList of vertices representing the path from the source vertex to
     * other vertices in the graph using the breadth-first search algorithm.
     * 
     * @return The method is returning an ArrayList of Vertex objects with a generic type of Pipe.
     */
    private ArrayList<Vertex<Pipe>> path() {
        return graph.bfs(sourceVertex);
    }

    /**
     * The function generates a random set of blocked cells on a board.
     */
    private void generateBlockedCells(){
        blockedCells = new boolean[board.getRowCount()][board.getColumnCount()];
        Random random = new Random();
        int blockedCount = 0;
        while (blockedCount < 60) {
            int row = random.nextInt(board.getRowCount());
            int col = random.nextInt(board.getColumnCount());
            if (!isCellBlocked(row, col)) {
                blockedCells[row][col] = true;
                blockedCount++;
            }
        }
    }

    /**
     * The function initializes a graph by adding vertices based on the blockedCells array and adds
     * black rectangles to the board for blocked cells.
     */
    private void initializeGraph() {
        for (int row = 0; row < board.getRowCount(); row++) {
            for (int col = 0; col < board.getColumnCount(); col++) {
                boolean isBlocked = blockedCells[row][col];
                if (!isBlocked) {
                    Vertex<Pipe> vertex = new Vertex<>(new Pipe(-1, row, col));
                    graph.addVertex(vertex);
                } else {
                    Rectangle rectangle = new Rectangle(board.getWidth() / board.getColumnCount(), board.getHeight() / board.getRowCount());
                    rectangle.setFill(Color.BLACK);
                    board.add(rectangle, col, row);
                }
            }
        }
    }

    /**
     * The function "addSourceAndDrainVertex" generates and assigns source and drain vertices based on
     * the orientation of the board.
     */
    private void addSourceAndDrainVertex(){
        int[] pos = generateSourceAndDrainCols(isVertical);
        sourceVertex = getVertexFromCell(isVertical ? pos[0] : 0, isVertical ? 0 : pos[0]);
        drainVertex = getVertexFromCell(isVertical ? pos[1] : board.getColumnCount()-1, isVertical ? board.getRowCount()-1 : pos[1]);
    }

    /**
     * The function paints a fountain and a drain on a canvas using the gc.drawImage() method.
     */
    private void paintFountainAndDraw() {
        gc.drawImage(source, isVertical ? allowedCoordinates(sourceVertex.getData().getCol()) : 0, isVertical ? 0 : allowedCoordinates(sourceVertex.getData().getRow()), 35, 35);
        gc.drawImage(drain, isVertical ? allowedCoordinates(drainVertex.getData().getCol()) : 565, isVertical ? 565 : allowedCoordinates(drainVertex.getData().getRow()), 35, 35);
    }

    /**
     * The function "allowedCoordinates" returns the value at the specified position in the
     * "coordinates" array.
     * 
     * @param position The parameter "position" represents the index of the array "coordinates" that
     * you want to access.
     * @return The method is returning the value at the specified position in the "coordinates" array.
     */
    private int allowedCoordinates(int position){
        int[] coordinates = {35,71,106,141,176,211,246,282,318,353,388,424,459,494,530};
        return coordinates[position];
    }

    /**
     * The function generates random positions for a source and drain column on a board, ensuring that
     * the cells are not blocked.
     * 
     * @param isVertical A boolean value indicating whether the source and drain columns should be
     * generated vertically or horizontally.
     * @return The method `generateSourceAndDrainCols` returns an array of integers.
     */
    private int[] generateSourceAndDrainCols(boolean isVertical) {
        Random random = new Random();
        int fountain_pos;
        do {
            fountain_pos = random.nextInt(board.getColumnCount());
        } while (isCellBlocked(isVertical ? 0 : fountain_pos, isVertical ? fountain_pos : 0));
        int drain_pos;
        do {
            drain_pos = random.nextInt(board.getColumnCount());
        } while (isCellBlocked(isVertical ? board.getRowCount()-1 : drain_pos, isVertical ? drain_pos : board.getRowCount()-1));
        return new int[]{fountain_pos, drain_pos};
    }

    /**
     * The function checks if a cell is blocked or not based on its row and column coordinates.
     * 
     * @param row The row parameter represents the row index of a cell in a grid or matrix.
     * @param col The col parameter represents the column index of a cell in a grid or matrix.
     * @return The method is returning a boolean value.
     */
    private boolean isCellBlocked(int row, int col) {
        return blockedCells[row][col];
    }

    /**
     * The `handleGridClick` function handles the click event on a grid, updates the board and vertex
     * based on the click, and manages the pipes on the screen.
     * 
     * @param event The event parameter is of type MouseEvent and represents the mouse click event that
     * triggered the method. It contains information about the mouse click, such as the position of the
     * click (getX() and getY() methods) and the source of the event.
     */
    private void handleGridClick(MouseEvent event) {
        if (handleGridClickEnabled){
            int columnIndex = (int) (event.getX() / (board.getWidth() / board.getColumnCount()));
            int rowIndex = (int) (event.getY() / (board.getHeight() / board.getRowCount()));
            if (isCellBlocked(rowIndex, columnIndex)) {
                return;
            }
            // update board
            Pipe existingPipe = getPipeInCell(columnIndex, rowIndex);
            if (existingPipe != null) {
                pipesOnScreen.remove(existingPipe);
                board.getChildren().removeIf(node -> GridPane.getColumnIndex(node) != null &&
                        GridPane.getRowIndex(node) != null &&
                        GridPane.getColumnIndex(node) == columnIndex &&
                        GridPane.getRowIndex(node) == rowIndex
                );
            } else {
                currentImageIndex = 1;
            }
            Pipe pipe = new Pipe(currentImageIndex, rowIndex, columnIndex);
            pipesOnScreen.add(pipe);
            showImageInBoard(pipe.getImage(),columnIndex,rowIndex);
            currentImageIndex = (currentImageIndex % 6) + 1;
            // update vertex
            Vertex<Pipe> currentVertex = getVertexFromCell(columnIndex, rowIndex);
            if (currentVertex != null) {
                currentVertex.setData(pipe);
            }
        }
    }
/**
 * The function connects vertices with pipes on the screen by iterating through the pipes and
 * connecting them with their neighboring vertices.
 */

    private void connectVerticesWithPipes() {
        for (Pipe pipe : pipesOnScreen){
            Vertex<Pipe> currentVertex = getVertexFromCell(pipe.getCol(), pipe.getRow());
            if (currentVertex != null) {
                connectWithNeighbors(currentVertex);
            }
        }
    }

    /**
     * The function connects a given vertex with its neighboring vertices in a graph, based on certain
     * conditions.
     * 
     * @param vertex The vertex parameter represents a vertex in a graph. In this case, the graph
     * represents a board with pipes. The vertex contains information about the pipe at that position
     * on the board, such as its type, row, and column.
     */
    private void connectWithNeighbors(Vertex<Pipe> vertex) {
        boolean isVertexWithPipe = vertex.getData().getType() != null;
        int row = vertex.getData().getRow();
        int col = vertex.getData().getCol();
        // connect with upper neighbor
        if (row > 0) {
            Vertex<Pipe> neighbor = getVertexFromCell(col, row - 1);
            if (neighbor != null) {
                if (isVertexWithPipe) {
                    Pipe upPipe = getPipeInCell(col, row - 1);
                    if (upPipe != null) {
                        graph.addEdge(vertex, neighbor, 1);
                    }
                } else {
                    graph.addEdge(vertex, neighbor, 1);
                }
            }
        }
        if (row < board.getRowCount() - 1) {
            Vertex<Pipe> neighbor = getVertexFromCell(col, row + 1);
            if (neighbor != null) {
                if (isVertexWithPipe) {
                    Pipe downPipe = getPipeInCell(col, row + 1);
                    if (downPipe != null) {
                        graph.addEdge(vertex, neighbor, 1);
                    }
                } else {
                    graph.addEdge(vertex, neighbor, 1);
                }
            }
        }
        if (col > 0) {
            Vertex<Pipe> neighbor = getVertexFromCell(col - 1, row);
            if (neighbor != null) {
                if (isVertexWithPipe) {
                    Pipe leftPipe = getPipeInCell(col - 1, row);
                    if (leftPipe != null) {
                        graph.addEdge(vertex, neighbor, 1);
                    }
                } else {
                    graph.addEdge(vertex, neighbor, 1);
                }
            }
        }
        if (col < board.getColumnCount() - 1) {
            Vertex<Pipe> neighbor = getVertexFromCell(col + 1, row);
            if (neighbor != null) {
                if (isVertexWithPipe) {
                    Pipe downPipe = getPipeInCell(col + 1, row);
                    if (downPipe != null) {
                        graph.addEdge(vertex, neighbor, 1);
                    }
                } else {
                    graph.addEdge(vertex, neighbor, 1);
                }
            }
        }
    }

   /**
    * The function returns a vertex from a graph based on the given column and row indices.
    * 
    * @param columnIndex The columnIndex parameter represents the index of the column in a grid or
    * matrix. It is used to locate a specific vertex in a graph based on its column position.
    * @param rowIndex The rowIndex parameter represents the index of the row in the grid where the
    * desired vertex is located.
    * @return The method is returning a Vertex object with generic type Pipe.
    */
    private Vertex<Pipe> getVertexFromCell(int columnIndex, int rowIndex) {
        for (Vertex<Pipe> vertex : graph.getVertices()) {
            if (vertex.getData().getRow() == rowIndex && vertex.getData().getCol() == columnIndex) {
                return vertex;
            }
        }
        return null;
    }

    /**
     * The function returns the pipe object located at a specific column and row in a list of pipes.
     * 
     * @param columnIndex The column index represents the column number of the cell in which the pipe
     * is located. It is used to identify the specific column in the grid where the pipe is present.
     * @param rowIndex The rowIndex parameter represents the index of the row in which the pipe is
     * located.
     * @return The method is returning a Pipe object.
     */
    private Pipe getPipeInCell(int columnIndex, int rowIndex) {
        for (Pipe pipe : pipesOnScreen) {
            if (pipe.getCol() == columnIndex && pipe.getRow() == rowIndex) {
                return pipe;
            }
        }
        return null;
    }

    /**
     * The function "showImageInBoard" sets the fit width and height of an ImageView and adds it to a
     * board at a specified column and row index.
     * 
     * @param image The image to be displayed in the board. It is an instance of the ImageView class.
     * @param columnIndex The column index represents the position of the image in the horizontal
     * direction within the board. It determines which column the image will be placed in.
     * @param rowIndex The rowIndex parameter represents the index of the row where the image should be
     * displayed in the board.
     */
    private void showImageInBoard(ImageView image, int columnIndex, int rowIndex){
        image.setFitWidth(board.getWidth() / board.getColumnCount());
        image.setFitHeight(board.getHeight() / board.getRowCount());
        board.add(image, columnIndex, rowIndex);
    }

    /**
     * The function checks if a path is valid and calculates the score based on the time taken and
     * number of pipes used, then displays an alert with the results.
     */
    @FXML
    protected void onValidateButton() {
        if (validatePath()){
            Calendar finalTime = Calendar.getInstance();
            int seconds = calculateTime(finalTime);
            int score = calculateScore(seconds);
            String msg = "Number of pipes used: " + pipesOnScreen.size();
            msg += "\nTime: " + seconds + " sec.";

            int myPathSize = path().size();
            int shortestPathSize = shortestPath().size();
            if (myPathSize == shortestPathSize){
                msg += "\nYou found one of the fastest ways! +1000 pts";
                score += 1000;
            }

            msg += "\nFinal score: " + score;
            ApplicationMain.showAlert(Alert.AlertType.INFORMATION,"Information","Congratulations! You won the game",msg);
            ApplicationMain.hideWindow((Stage)vText.getScene().getWindow());
            ApplicationMain.showWindow("hello-view", null);
        } else {
            ApplicationMain.showAlert(Alert.AlertType.ERROR,"Error","Your solution is not correct.",null);
            deleteCurrentPipes();
        }
    }

    /**
     * The function "validatePath" checks if a path between a source and drain vertex is valid by
     * validating the source and drain vertices, connecting the vertices with pipes, and checking if
     * the path contains the drain vertex and if the pipe connections are valid.
     * 
     * @return The method is returning a boolean value.
     */
    private boolean validatePath(){
        if (validateSourceAndDrain()){
            connectVerticesWithPipes();
            if (path().contains(drainVertex)){
                return validatePipeConnections(path());
            }
        }
        return false;
    }

    /**
     * The function validates whether the source and drain vertices have compatible pipe types based on
     * the orientation of the pipe.
     * 
     * @return The method is returning a boolean value.
     */
    private boolean validateSourceAndDrain(){
        PipeType sourceType = sourceVertex.getData().getType();
        PipeType drainType = drainVertex.getData().getType();
        if (isVertical){
            return (sourceType == PipeType.ELBOW_UP_LEFT || sourceType == PipeType.ELBOW_DOWN_RIGHT || sourceType == PipeType.VERTICAL) &&
                   (drainType == PipeType.ELBOW_DOWN_LEFT || drainType == PipeType.ELBOW_DOWN_RIGHT || drainType == PipeType.VERTICAL);
        } else {
            return (sourceType == PipeType.ELBOW_DOWN_LEFT || sourceType == PipeType.ELBOW_UP_LEFT || sourceType == PipeType.HORIZONTAL) &&
                   (drainType == PipeType.ELBOW_DOWN_RIGHT || drainType == PipeType.ELBOW_UP_RIGHT || drainType == PipeType.HORIZONTAL);
        }
    }

    /**
     * The function validates the connections between pipes in a given path.
     * 
     * @param path An ArrayList of Vertex objects representing the path of the pipe connections.
     * @return The method is returning a boolean value.
     */
    private boolean validatePipeConnections(ArrayList<Vertex<Pipe>> path) {
        ArrayList<Vertex<Pipe>> covered = new ArrayList<>();
        Vertex<Pipe> currentVertex = path.get(0);
        while (currentVertex != drainVertex){
            Vertex<Pipe> nextVertex = null;
            for (Vertex<Pipe> neighbor : currentVertex.getNeighbors()) {
                if (!covered.contains(neighbor)) {
                    Direction direction = getPipeDirection(currentVertex, neighbor);
                    if (isValidPipeConnection(currentVertex.getData(), neighbor.getData(), direction)) {
                        nextVertex = neighbor;
                        break;
                    }
                }
            }
            if (nextVertex == null) {
                return false;
            }
            covered.add(currentVertex);
            currentVertex = nextVertex;
        }
        return true;
    }

    /**
     * The function returns the direction in which a pipe should be connected based on the current and
     * next vertex positions.
     * 
     * @param currentVertex The current vertex in the graph, which contains a Pipe object with
     * information about its row and column position.
     * @param nextVertex The nextVertex parameter is the vertex that is adjacent to the currentVertex
     * parameter. It represents the next vertex in the path or direction that we are trying to
     * determine.
     * @return The method returns a Direction enum value, which represents the direction from the
     * current vertex to the next vertex.
     */
    private Direction getPipeDirection(Vertex<Pipe> currentVertex, Vertex<Pipe> nextVertex) {
        int currentRow = currentVertex.getData().getRow();
        int currentCol = currentVertex.getData().getCol();
        int nextRow = nextVertex.getData().getRow();
        int nextCol = nextVertex.getData().getCol();
        if (currentRow < nextRow) {
            return Direction.DOWN;
        } else if (currentRow > nextRow) {
            return Direction.UP;
        } else if (currentCol < nextCol) {
            return Direction.RIGHT;
        } else if (currentCol > nextCol) {
            return Direction.LEFT;
        }
        return null;
    }

    /**
     * The function checks if a pipe connection is valid based on the current pipe type, the next pipe
     * type, and the direction of the connection.
     * 
     * @param currentPipe The currentPipe parameter represents the pipe that is currently being checked
     * for valid connection.
     * @param nextPipe The `nextPipe` parameter is the pipe that is connected to the `currentPipe` in a
     * specific direction.
     * @param direction The direction parameter represents the direction in which the next pipe is
     * connected to the current pipe. It can have one of the following values: UP, DOWN, LEFT, or
     * RIGHT.
     * @return The method is returning a boolean value.
     */
    private boolean isValidPipeConnection(Pipe currentPipe, Pipe nextPipe, Direction direction) {
        PipeType currentType = currentPipe.getType();
        PipeType nextType = nextPipe.getType();
        if (currentType == PipeType.VERTICAL){
            if (direction == Direction.DOWN){
                return (nextType == PipeType.VERTICAL || nextType == PipeType.ELBOW_UP_LEFT || nextType == PipeType.ELBOW_UP_RIGHT);
            } else if (direction == Direction.UP){
                return (nextType == PipeType.VERTICAL || nextType == PipeType.ELBOW_DOWN_LEFT || nextType == PipeType.ELBOW_DOWN_RIGHT);
            }
        } else if (currentType == PipeType.HORIZONTAL){
            if (direction == Direction.LEFT){
                return (nextType == PipeType.HORIZONTAL || nextType == PipeType.ELBOW_UP_RIGHT || nextType == PipeType.ELBOW_DOWN_RIGHT);
            } else if (direction == Direction.RIGHT){
                return (nextType == PipeType.HORIZONTAL || nextType == PipeType.ELBOW_UP_LEFT || nextType == PipeType.ELBOW_DOWN_LEFT);
            }
        } else if (currentType == PipeType.ELBOW_UP_RIGHT){
            if (direction == Direction.RIGHT){
                return (nextType == PipeType.HORIZONTAL || nextType == PipeType.ELBOW_UP_LEFT || nextType == PipeType.ELBOW_DOWN_LEFT);
            } else if (direction == Direction.UP){
                return (nextType == PipeType.VERTICAL || nextType == PipeType.ELBOW_DOWN_LEFT || nextType == PipeType.ELBOW_DOWN_RIGHT);
            }
        } else if (currentType == PipeType.ELBOW_UP_LEFT){
            if (direction == Direction.LEFT){
                return (nextType == PipeType.HORIZONTAL || nextType == PipeType.ELBOW_UP_RIGHT || nextType == PipeType.ELBOW_DOWN_RIGHT);
            } else if (direction == Direction.UP){
                return (nextType == PipeType.VERTICAL || nextType == PipeType.ELBOW_DOWN_LEFT || nextType == PipeType.ELBOW_DOWN_RIGHT);
            }
        } else if (currentType == PipeType.ELBOW_DOWN_RIGHT){
            if (direction == Direction.RIGHT){
                return (nextType == PipeType.HORIZONTAL || nextType == PipeType.ELBOW_UP_LEFT || nextType == PipeType.ELBOW_DOWN_LEFT);
            } else if (direction == Direction.DOWN){
                return (nextType == PipeType.VERTICAL || nextType == PipeType.ELBOW_UP_LEFT || nextType == PipeType.ELBOW_UP_RIGHT);
            }
        } else if (currentType == PipeType.ELBOW_DOWN_LEFT){
            if (direction == Direction.LEFT){
                return (nextType == PipeType.HORIZONTAL || nextType == PipeType.ELBOW_UP_RIGHT || nextType == PipeType.ELBOW_DOWN_RIGHT);
            } else if (direction == Direction.DOWN){
                return (nextType == PipeType.VERTICAL || nextType == PipeType.ELBOW_UP_LEFT || nextType == PipeType.ELBOW_UP_RIGHT);
            }
        }
        return false;
    }

    /**
     * The function prompts the user for confirmation and if they choose to give up, it deletes the
     * current pipes, rebuilds the graph without pipes, highlights the shortest path, and disables
     * certain buttons and grid click functionality.
     */
    @FXML
    protected void onGiveUpButton() {
        Optional<ButtonType> result = ApplicationMain.showAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Are you sure you want to give up?", null);
        if (result.isPresent() && result.get() == ButtonType.OK) {
            deleteCurrentPipes();
            buildGraphWithoutPipes();
            highlightPath(shortestPath());
            validateButton.setDisable(true);
            resetButton.setDisable(true);
            giveUpButton.setDisable(true);
            handleGridClickEnabled = false;
        }
    }

    /**
     * The function returns the shortest path between a source vertex and a drain vertex in a graph
     * using Dijkstra's algorithm.
     * 
     * @return The method `shortestPath()` returns an `ArrayList` of `Vertex` objects representing the
     * shortest path from the source vertex to the drain vertex in a graph.
     */
    private ArrayList<Vertex<Pipe>> shortestPath(){
        return graph.dijkstra(sourceVertex,drainVertex);
    }

   /**
    * The function builds a graph by connecting vertices with their neighboring vertices.
    */
    private void buildGraphWithoutPipes() {
        for (int row = 0; row < board.getRowCount(); row++) {
            for (int col = 0; col < board.getColumnCount(); col++) {
                Vertex<Pipe> currentVertex = getVertexFromCell(row, col);
                if (currentVertex != null){
                    connectWithNeighbors(currentVertex);
                }
            }
        }
    }

    /**
     * The function highlights a path on a board by adding yellow rectangles to the corresponding
     * positions.
     * 
     * @param path An ArrayList of Vertex objects, where each Vertex represents a node in a graph.
     */
    private void highlightPath(ArrayList<Vertex<Pipe>> path) {
        for (Vertex<Pipe> vertex : path) {
            int columnIndex = vertex.getData().getCol();
            int rowIndex = vertex.getData().getRow();
            Rectangle rectangle = new Rectangle(board.getWidth() / board.getColumnCount(), board.getHeight() / board.getRowCount());
            rectangle.setFill(Color.YELLOW);
            board.add(rectangle, columnIndex, rowIndex);
        }
    }

    @// The above code is defining a method called "onResetButton" that is executed when a reset button
    // is clicked in a JavaFX application. Inside the method, it calls a method called
    // "deleteCurrentPipes" to delete the current pipes.
    FXML
    protected void onResetButton() {
        deleteCurrentPipes();
    }
/**
 * The function deletes all current pipes from the graph and removes them from the screen.
 */

    private void deleteCurrentPipes(){
        graph.removeAllEdges();
        for (Pipe pipe : pipesOnScreen) {
            int columnIndex = pipe.getCol();
            int rowIndex = pipe.getRow();
            board.getChildren().removeIf(node -> GridPane.getColumnIndex(node) != null &&
                    GridPane.getRowIndex(node) != null &&
                    GridPane.getColumnIndex(node) == columnIndex &&
                    GridPane.getRowIndex(node) == rowIndex
            );
            Vertex<Pipe> currentVertex = getVertexFromCell(columnIndex, rowIndex);
            if (currentVertex != null) {
                currentVertex.setData(new Pipe(-1,rowIndex,columnIndex));
            }
        }
        pipesOnScreen.clear();
    }

    /**
     * The function checks if a grid click is enabled and shows a confirmation dialog before returning
     * to the menu.
     */
    @FXML
    protected void onMenuButton() {
        Optional<ButtonType> result = handleGridClickEnabled
                ? ApplicationMain.showAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Are you sure you want to return to the menu?", null)
                : Optional.of(ButtonType.OK);

        if (result.isPresent() && result.get() == ButtonType.OK) {
            ApplicationMain.hideWindow((Stage) vText.getScene().getWindow());
            ApplicationMain.showWindow("hello-view", null);
        }
    }

    /**
     * The function sets the source and drain images to a specific image based on whether it is
     * vertical or not.
     */
    private void getSourceAndDrainImage(){
        Image image = new Image("file:" + ApplicationMain.getFile("images/pipe_"+(isVertical? "7":"8")+".png").getPath());
        source = image;
        drain = image;
    }

    private int calculateScore(int seconds){
        return (100 - pipesOnScreen.size()) * 10 - seconds;
    }

    private int calculateTime(Calendar finalTime){
        return (int) ((finalTime.getTimeInMillis() - startTime.getTimeInMillis()) / 1000);
    }
}