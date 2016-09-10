import editor.FastLinkedList;
import editor.TextFileManager;
import editor.TextRenderEngine;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Editor extends Application {
    /**
     * The default width of the window loaded by this application.
     */
    private static final int DEFAULT_WIDTH = 500;
    /**
     * The default height of the window loaded by this application.
     */
    private static final int DEFAULT_HEIGHT = 500;
    /**
     * The default font for the editor.
     */
    private static final String DEFAULT_FONT_NAME = "Verdana";
    /**
     * The default size for the editor's text.
     */
    private static final int DEFAULT_FONT_SIZE = 32;

    /**
     * A container to store the text on the screen.
     */
    private FastLinkedList<String> buffer;
    /**
     * A rendering engine to create a Group of Text nodes for each character.
     */
    private TextRenderEngine renderer;

    public static void main(String[] args) {
        System.out.println(args[0]);
        /** Test code for loading whatever text I want to load for testing. */
        // TODO comment this out when work is complete.
        try {
            TextFileManager.saveToDisk("This is a test\nand you can sUCUK IT FARTKNOCKER", "./farts");
        } catch (Exception e) {
            System.out.println("whoops");
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT, Color.WHITE);
        // Check for file input
        List<String> args = getParameters().getUnnamed();
        String fileContents = null;
        if (args.size() > 0) {
            String fileName = args.get(0);
            try {
                fileContents = TextFileManager.loadFromDisk(fileName);
            } catch (IOException e) {
                System.out.println("Unable to load file: " + fileName);
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }

        // Create and register event handlers
        // TODO Don't worry about registering event handlers until you have thoroughly tested text positioning,
        // TODO window resizing, and scrolling

        // initialize the textbuffer and renderengine
        buffer = new FastLinkedList<>();
        renderer = new TextRenderEngine(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_FONT_SIZE, DEFAULT_FONT_NAME);
        if (fileContents != null) {
            for (int i = 0; i < fileContents.length(); ++i) {
                buffer.insert(Character.toString(fileContents.charAt(i)));
            }
            System.out.println(buffer.toString());
        }
        root.getChildren().add(renderer.build(buffer));
        primaryStage.setTitle("Editor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
