package view;

import javax.swing.*;
import java.awt.*;

public class GroupStatistics {
    private final JFrame jFrame = new JFrame();
    private final String data;

    public GroupStatistics(String data) {
        this.data = data;
        init();
    }

    private void init() {
        this.jFrame.setTitle("Group Statistics");
        this.jFrame.setSize(600, 500);
        this.jFrame.setLocationRelativeTo(null);
        this.jFrame.setResizable(false);
        this.jFrame.setLayout(new BorderLayout());

        Font font = new Font("Arial", Font.BOLD, 15);

        JLabel titleLabel = new JLabel("Connected Components");
        titleLabel.setFont(font);
        this.jFrame.add(titleLabel, BorderLayout.NORTH);

        JLabel dataLabel = new JLabel(data);
        dataLabel.setFont(font);
        JScrollPane scrollPane = new JScrollPane(dataLabel);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.jFrame.add(scrollPane, BorderLayout.CENTER);

        this.jFrame.setVisible(true);
    }
}
