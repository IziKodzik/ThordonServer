package service;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.awt.event.KeyEvent.*;

public class RobotService
		extends Robot {

    int x, y;


    public RobotService(String filename) throws AWTException {
        GraphicsDevice monitor = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        x = monitor.getDisplayMode().getWidth();
        y = monitor.getDisplayMode().getHeight();

    }

    public RobotService(GraphicsDevice screen) throws AWTException {
        super(screen);


    }

    public RobotService() throws AWTException {
        super();
    }

    @Override
    public synchronized void mouseMove(int x, int y) {

        Point cursorPos;
        do {

            cursorPos = MouseInfo.getPointerInfo().getLocation();
            super.mouseMove(x, y);

        } while (cursorPos.x != x || cursorPos.y != y);
    }

    public synchronized void leftClick() {
        super.mousePress(InputEvent.BUTTON1_MASK);
        super.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public synchronized void doubleLeftClick() {
        leftClick();
        leftClick();
    }

//    public synchronized void mouseMove(String where) {
//
//        FloatPoint point = destinations.get(where);
//        this.mouseMove((int) (point.getX() * x), (int) (point.getY() * y));
//
//    }

//    public synchronized void mouseMoveAndSelect(String where) {
//        mouseMove(where);
//        leftClick();
//    }

//    public synchronized void mouseMoveAndDoubleClick(String where) {
//        mouseMove(where);
//        doubleLeftClick();
//    }

    public synchronized void type(String toType) {
        String tmp = toType.toUpperCase();
        for (int op = 0; op < toType.length(); ++op) {
            if (toType.charAt(op) >= 'A' && toType.charAt(op) <= ('Z')) {
                this.typeUpperCaseLetter(tmp.charAt(op));
            } else
                this.keyClick(tmp.charAt(op));
        }
    }

    public synchronized BufferedImage createScreenCapture(){

    	DisplayMode screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
    	Rectangle rectangle = new Rectangle(screen.getWidth(),screen.getHeight());
    	return this.createScreenCapture(rectangle);

    }

    public synchronized void keyClick(char letter) {

        char verifiedLetter = getKeyCode(letter);
        if(verifiedLetter!=letter) {
            letter = verifiedLetter;
            typeUpperCaseLetter(letter);
        }else {
            this.keyPress(letter);
            this.keyRelease(letter);
        }
    }

    public synchronized void typeUpperCaseLetter(char letter) {
        this.keyPress(VK_SHIFT);
        this.keyClick(letter);
        this.keyRelease(VK_SHIFT);
    }

//    public synchronized void clearTextField(String where) {
//        this.mouseMoveAndDoubleClick(where);
//        this.keyClick((char) VK_BACK_SPACE);
//    }

//    public synchronized void fillTextField(String where, String toType) {
//        this.mouseMoveAndSelect(where);
//        this.type(toType);
//    }

    public synchronized void clickAltKey(char val) {

        this.keyPress(VK_ALT);
        this.keyClick(getKeyCode(val));
        this.keyRelease(VK_ALT);

    }

    public char getKeyCode(char val) {
        switch (val) {
            case 126:
                return 96;
            case 33:

                return 49;
            case 64:

                return 50;
            case 35:

                return 51;
            case 36:

                return 52;
            case 37:

                return 53;
            case 94:

                return 54;
            case 38:

                return 55;
            case 42:

                return 56;
            case 40:

                return 57;
            case 41:

                return 48;
            case 95:

                return 45;
            case 43:

                return 61;
            case 123:

                return 91;
            case 125:

                return 93;
            case 58:

                return 59;
            case 34:

                return 39;
            case 60:

                return 44;
            case 62:

                return 46;
            case 63:

                return 47;
            default:
                return val;
        }

    }
}


