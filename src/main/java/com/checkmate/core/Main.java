package com.checkmate.core;

import com.checkmate.ui.GameFrame;
import com.checkmate.ui.LoginFrame;
import javax.swing.*;
import java.io.File;

public class Main {
    public static GameFrame frame;

    public static void main(String[] args) {
        // creating start menu form
        new LoginFrame();
    }
}
