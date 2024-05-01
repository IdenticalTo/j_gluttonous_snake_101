package com.flyingBird.www;

import com.sun.xml.internal.ws.handler.HandlerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class MainFrame extends JFrame {
    private JPanel jPanel;
    private Snake snake;
    private Node food;
    private Timer timer;
    public MainFrame() throws HandlerException {
        initFrame();
        initPanel();
        initSnake();
        initFood();
        initTimer();
        setkeyListener();
    }

    private void setkeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if(snake.getDirection() != Direction.DOWN){
                            snake.setDirection(Direction.UP);
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if(snake.getDirection() != Direction.UP){
                            snake.setDirection(Direction.DOWN);
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if(snake.getDirection() != Direction.RIGHT){
                            snake.setDirection(Direction.LEFT);
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if(snake.getDirection() != Direction.LEFT){
                            snake.setDirection(Direction.RIGHT);
                        }
                        break;
                }
            }
        });
    }

    private void initTimer() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                snake.move();
                Node head = snake.getBody().getFirst();
                if (head.getX() == food.getX() && head.getY() == food.getY()) {
                    snake.eat(food);
                    food.random();
                }
                jPanel.repaint();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 100);
    }

    private void initFood() {
        food = new Node();
        food.random();
    }

    private void initSnake() {
        snake = new Snake();
    }

    private void initPanel() {
        jPanel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                g.clearRect(0,0,800,800);
                for (int i = 0; i <= 40; i++) {
                    g.drawLine(0, i*15, 800, i*15);
                }
                for (int j = 0; j <= 40; j++) {
                    g.drawLine(j*15, 0, j*15, 800);
                }
                LinkedList<Node> body = snake.getBody();
                for (Node node:body) {
                    g.fillRect(node.getX()*15, node.getY()*15, 15,15);
                }
                g.fillRect(food.getX()*15, food.getY()*15,15,15);
            }
        };
        add(jPanel);
    }

    private void initFrame() {
        setSize(610, 640);
        setLocation(100, 0);
        setTitle("贪吃蛇");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new MainFrame().setVisible(true);
    }
}
