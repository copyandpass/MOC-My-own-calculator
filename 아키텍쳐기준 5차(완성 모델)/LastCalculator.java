import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LastCalculator {

    public static void main(String[] args) {
        JFrame frame = createMainFrame();

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("네트워크 계산기", createBackgroundPanel(createNetworkPanel()));
        tabbedPane.addTab("서브넷 계산기", createBackgroundPanel(createSubnetPanel()));
        tabbedPane.addTab("2진수/10진수 변환", createBackgroundPanel(createConversionPanel()));

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private static JFrame createMainFrame() {
        JFrame frame = new JFrame("LastCalculator");
        frame.setSize(450, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    private static JPanel createBackgroundPanel(JPanel mainPanel) {
        // 배경 이미지 경로 설정
        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\admin\\Desktop\\MOC-Middle\\MOC-My-own-calculator\\아키텍쳐기준 5차(완성 모델)\\CaculatorBackGround.PNG");
        
        // JLabel에 배경 이미지 추가
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout());
        backgroundLabel.add(mainPanel, BorderLayout.CENTER);

        // 배경을 JPanel로 래핑하여 반환
        JPanel backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.add(backgroundLabel, BorderLayout.CENTER);

        return backgroundPanel;
    }

    private static JPanel createNetworkPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        JLabel ipLabel = new JLabel("IP 주소:");
        JTextField ipText = new JTextField(12);

        JLabel subnetLabel = new JLabel("서브넷 마스크:");
        JTextField subnetText = new JTextField(12);

        JLabel resultLabel = new JLabel("네트워크 아이디:");
        JTextField resultText = new JTextField(12);
        resultText.setEditable(false);

        JLabel broadcastLabel = new JLabel("브로드캐스트 주소:");
        JTextField broadcastText = new JTextField(12);
        broadcastText.setEditable(false);

        JButton calculateButton = new JButton("계산");

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipText.getText();
                String subnet = subnetText.getText();
                if (isValidIPAddress(ip) && isValidIPAddress(subnet)) {
                    resultText.setText(calculateNetworkID(ip, subnet));
                    broadcastText.setText(calculateBroadcastAddress(ip, subnet));
                } else {
                    resultText.setText("잘못된 입력입니다.");
                    broadcastText.setText("잘못된 입력입니다.");
                }
            }
        });

        c.gridx = 0; c.gridy = 0; panel.add(ipLabel, c);
        c.gridx = 1; panel.add(ipText, c);
        c.gridx = 0; c.gridy = 1; panel.add(subnetLabel, c);
        c.gridx = 1; panel.add(subnetText, c);
        c.gridx = 0; c.gridy = 2; panel.add(resultLabel, c);
        c.gridx = 1; panel.add(resultText, c);
        c.gridx = 0; c.gridy = 3; panel.add(broadcastLabel, c);
        c.gridx = 1; panel.add(broadcastText, c);
        c.gridx = 1; c.gridy = 4; panel.add(calculateButton, c);

        panel.setOpaque(false); // 배경 투명 설정

        return panel;
    }

    private static JPanel createSubnetPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        JLabel subnetMaskLabel = new JLabel("서브넷 마스크:");
        JTextField subnetMaskText = new JTextField(12);

        JLabel hostLabel = new JLabel("호스트 수:");
        JTextField hostText = new JTextField(12);
        hostText.setEditable(false);

        JLabel rangeLabel = new JLabel("호스트 범위:");
        JTextField rangeText = new JTextField(12);
        rangeText.setEditable(false);

        JButton subnetCalcButton = new JButton("계산");

        subnetCalcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subnetMask = subnetMaskText.getText();
                if (isValidIPAddress(subnetMask)) {
                    hostText.setText(Integer.toString(calculateHostCount(subnetMask)));
                    rangeText.setText(calculateHostRange("192.168.1.0", subnetMask));
                } else {
                    hostText.setText("잘못된 입력입니다.");
                    rangeText.setText("잘못된 입력입니다.");
                }
            }
        });

        c.gridx = 0; c.gridy = 0; panel.add(subnetMaskLabel, c);
        c.gridx = 1; panel.add(subnetMaskText, c);
        c.gridx = 0; c.gridy = 1; panel.add(hostLabel, c);
        c.gridx = 1; panel.add(hostText, c);
        c.gridx = 0; c.gridy = 2; panel.add(rangeLabel, c);
        c.gridx = 1; panel.add(rangeText, c);
        c.gridx = 1; c.gridy = 3; panel.add(subnetCalcButton, c);

        panel.setOpaque(false); // 배경 투명 설정

        return panel;
    }

    private static JPanel createConversionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        JLabel decimalLabel = new JLabel("10진수:");
        JTextField decimalText = new JTextField(12);

        JLabel binaryLabel = new JLabel("2진수:");
        JTextField binaryText = new JTextField(12);

        JButton toBinaryButton = new JButton("2진수 변환");
        JButton toDecimalButton = new JButton("10진수 변환");

        toBinaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int decimalValue = Integer.parseInt(decimalText.getText());
                    binaryText.setText(Integer.toBinaryString(decimalValue));
                } catch (NumberFormatException ex) {
                    binaryText.setText("잘못된 입력입니다.");
                }
            }
        });

        toDecimalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int decimalValue = Integer.parseInt(binaryText.getText(), 2);
                    decimalText.setText(Integer.toString(decimalValue));
                } catch (NumberFormatException ex) {
                    decimalText.setText("잘못된 입력입니다.");
                }
            }
        });

        c.gridx = 0; c.gridy = 0; panel.add(decimalLabel, c);
        c.gridx = 1; panel.add(decimalText, c);
        c.gridx = 0; c.gridy = 1; panel.add(binaryLabel, c);
        c.gridx = 1; panel.add(binaryText, c);
        c.gridx = 0; c.gridy = 2; panel.add(toBinaryButton, c);
        c.gridx = 1; panel.add(toDecimalButton, c);

        panel.setOpaque(false); // 배경 투명 설정

        return panel;
    }

    private static boolean isValidIPAddress(String ip) {
        return true;
    }

    private static String calculateNetworkID(String ip, String subnet) {
        return "NetworkID";
    }

    private static String calculateBroadcastAddress(String ip, String subnet) {
        return "BroadcastAddress";
    }

    private static int calculateHostCount(String subnet) {
        return 0;
    }

    private static String calculateHostRange(String network, String subnet) {
        return "HostRange";
    }
}
