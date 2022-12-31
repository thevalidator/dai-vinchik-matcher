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
        setFont(new java.awt.Font("Segoe UI", 1, 12));
        Color bg;
        if (isSelected()) {
            bg = javax.swing.UIManager.getDefaults().getColor("Actions.Green");
        } else {
            bg = javax.swing.UIManager.getDefaults().getColor("Button.Background");
        }
        setBackground(bg);
        super.paintComponent(g);
    }

}
