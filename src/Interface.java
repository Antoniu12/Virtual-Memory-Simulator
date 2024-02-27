import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Interface extends JFrame {
    private VirtualMemory virtualMemory;

    public Interface() {
        virtualMemory = new VirtualMemory();
        initComponents();

        setTitle("Virtual Memory Interface");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        JButton pushButton = new JButton("Push");
        JButton popButton = new JButton("Pop");
        JButton replacePageButton = new JButton("Replace");

        JTextField pageNameTF2 = new JTextField(15);
        JTextField pageNameTF = new JTextField(15);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(pageNameTF);
        buttonPanel.add(pageNameTF2);
        buttonPanel.add(pushButton);
        buttonPanel.add(popButton);
        buttonPanel.add(replacePageButton);


        JTextArea pageListArea = new JTextArea(10, 30);
        pageListArea.setEditable(false);

        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(pageListArea), BorderLayout.CENTER);

        pushButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pageName = pageNameTF.getText();
                virtualMemory.createPage(pageName);
                updatePageList(pageListArea);
            }
        });

        popButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                virtualMemory.pop();
                updatePageList(pageListArea);
            }
        });

        replacePageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pageName1 = pageNameTF.getText();
                String pageName2 = pageNameTF2.getText();
                try {
                    virtualMemory.replace(pageName1, pageName2);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                updatePageList(pageListArea);
            }
        });
    }

    private void updatePageList(JTextArea pageListArea) {
        pageListArea.setText("");

        for (Object pageName : virtualMemory.getPageOrder().keySet()) {
            pageListArea.append(pageName + "\n");
        }
    }
}
