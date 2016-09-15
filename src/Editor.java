import editor.FastLinkedList;
import editor.TextFileManager;
import editor.TextRenderEngine;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private static final int DEFAULT_FONT_SIZE = 12;

    /**
     * A container to store the text on the screen.
     */
    private FastLinkedList<String> buffer;

    /**
     * A rendering engine to create a Group of Text nodes for each character.
     */
    private TextRenderEngine renderer;

    /**
     * The name of the file.
     */
    private String fileName;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT, Color.WHITE);

        // Load file if exists
        List<String> args = getParameters().getUnnamed();
        if (args.size() == 0) {
            System.out.println("Must input name of file!");
            System.exit(2);
        }
        String fileContents = "";
        if (args.size() > 0) {
            fileName = args.get(0);
            try {
                fileContents = TextFileManager.loadFromDisk(fileName);
            } catch (IOException e) {
                System.out.println("Loading new file: " + fileName);
            }
        }

        // register event handlers
        EventHandler<KeyEvent> keyEventHandler = new KeyEventHandler(root);
        scene.setOnKeyPressed(keyEventHandler);
        scene.setOnKeyTyped(keyEventHandler);

        /* Thanks to the author of this article for the method signatures for a window resize listener.
           https://blog.idrsolutions.com/2012/11/adding-a-window-resize-listener-to-javafx-scene/
         */
        scene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
            renderer.setGroupWidth(newSceneWidth.intValue());
            root.getChildren().clear();
            root.getChildren().add(renderer.build(buffer));
        });
        scene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> {
            renderer.setGroupHeight(newSceneHeight.intValue());
            root.getChildren().clear();
            root.getChildren().add(renderer.build(buffer));
        });

        buffer = new FastLinkedList<>();
        renderer = new TextRenderEngine(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_FONT_SIZE, DEFAULT_FONT_NAME);
        for (int i = 0; i < fileContents.length(); ++i) {
            buffer.insert(Character.toString(fileContents.charAt(i)));
        }
        root.getChildren().add(renderer.build(buffer));

        primaryStage.setTitle("Editor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * An EventHandler to handle keys that get pressed.
     */
    private class KeyEventHandler implements EventHandler<KeyEvent> {
        private Group root;

        public KeyEventHandler(Group r) {
            root = r;
        }

        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getEventType() == KeyEvent.KEY_TYPED) {
                String characterTyped = keyEvent.getCharacter();
                if (characterTyped.equals("\r")) {
                    // handle newlines
                    buffer.insert("\n");
                } else if (characterTyped.length() > 0 && characterTyped.charAt(0) != 8) {
                    // handle regular typed characters
                    buffer.insert(characterTyped);
                    keyEvent.consume();
                }
            } else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                // handle control inputs such as font resizing and file saving
                KeyCode code = keyEvent.getCode();
                if (code == KeyCode.BACK_SPACE && !buffer.isEmpty()) {
                    buffer.delete();
                } else if (keyEvent.isShortcutDown() && code == KeyCode.UP) {
                    renderer.setFontSize(renderer.getFontSize() + 5);
                } else if (keyEvent.isShortcutDown() && code == KeyCode.DOWN) {
                    renderer.setFontSize(renderer.getFontSize() - 5);
                } else if (keyEvent.isShortcutDown() && code == KeyCode.F1) {
                    try {
                        TextFileManager.saveToDisk(buffer.toString(), fileName);
                        System.out.println("File saved as " + fileName);
                    } catch (IOException e) {
                        System.out.println("Unable to save file");
                    }
                }
            }
            root.getChildren().clear();
            root.getChildren().add(renderer.build(buffer));
        }
    }
}
