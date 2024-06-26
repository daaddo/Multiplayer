/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package it.ji.game.client.gui;

import it.ji.game.utils.logic.PlayerType;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author sommovir
 */
public class HPPanel extends javax.swing.JPanel {
    private PlayerType playerType;
    private int hp = 100;
    /**
     * Creates new form HPPanel
     */
    public HPPanel() {
        initComponents();
    }
    public HPPanel(PlayerType playerType) {
        initComponents();
        this.playerType = playerType;
    }
    
    public int getHp(){
        return this.hp;
    }
    
    public void setHP(int hp){
        this.hp = hp;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Calculate the width of the green part
        int greenWidth = (int) ((hp / 100.0) * panelWidth);

        // Draw the green part
        if (playerType == PlayerType.ENEMY) {
            g.setColor(Color.RED);
            g.fillRect(0, 0, greenWidth, panelHeight);
        }
        if (playerType == PlayerType.SELF) {
            g.setColor(Color.GREEN);
            g.fillRect(0, 0, greenWidth, panelHeight);
        }
        // Draw the red part
        g.setColor(Color.GRAY);
        g.fillRect(greenWidth, 0, panelWidth - greenWidth, panelHeight);
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 339, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
