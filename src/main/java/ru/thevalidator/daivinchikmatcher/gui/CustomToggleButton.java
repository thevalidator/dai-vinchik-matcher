/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JToggleButton;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class CustomToggleButton extends JToggleButton {

    public CustomToggleButton() {
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Color bg;
        if (isSelected()) {
            bg = Color.lightGray;
        } else {
            bg = new Color(78, 78, 78);
        }
        setBackground(bg);
        super.paintComponent(g);
    }

}
