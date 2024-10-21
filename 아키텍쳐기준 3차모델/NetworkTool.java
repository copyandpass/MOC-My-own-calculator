import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NetworkTool {
    
    public static void main(String[] args) {
        // JFrame 설정
        JFrame frame = new JFrame("Network Tool");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // 탭 설정
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(10, 10, 460, 240);

        // 첫 번째 탭: 네트워크 아이디 계산기
        JPanel networkPanel = new JPanel();
        networkPanel.setLayout(null);

        JLabel ipLabel = new JLabel("IP 주소:");
        ipLabel.setBounds(20, 20, 80, 25);
        networkPanel.add(ipLabel);

        JTextField ipText = new JTextField(15);
        ipText.setBounds(100, 20, 150, 25);
        networkPanel.add(ipText);

        JLabel subnetLabel = new JLabel("서브넷 마스크:");
        subnetLabel.setBounds(20, 60, 100, 25);
        networkPanel.add(subnetLabel);

        JTextField subnetText = new JTextField(15);
        subnetText.setBounds(130, 60, 150, 25);
        networkPanel.add(subnetText);

        JLabel resultLabel = new JLabel("네트워크 아이디:");
        resultLabel.setBounds(20, 100, 150, 25);
        networkPanel.add(resultLabel);

        JTextField resultText = new JTextField(15);
        resultText.setBounds(150, 100, 150, 25);
        resultText.setEditable(false);
        networkPanel.add(resultText);

        JButton calculateButton = new JButton("계산");
        calculateButton.setBounds(150, 140, 80, 25);
        networkPanel.add(calculateButton);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipText.getText();
                String subnet = subnetText.getText();
                if (isValidIPAddress(ip) && isValidIPAddress(subnet)) {
                    String networkId = calculateNetworkID(ip, subnet);
                    resultText.setText(networkId);
                } else {
                    resultText.setText("잘못된 입력입니다.");
                }
            }
        });

        tabbedPane.add("네트워크 계산기", networkPanel);

        // 두 번째 탭: 서브넷 계산기
        JPanel subnetPanel = new JPanel();
        subnetPanel.setLayout(null);

        JLabel subnetMaskLabel = new JLabel("서브넷 마스크:");
        subnetMaskLabel.setBounds(20, 20, 100, 25);
        subnetPanel.add(subnetMaskLabel);

        JTextField subnetMaskText = new JTextField(15);
        subnetMaskText.setBounds(130, 20, 150, 25);
        subnetPanel.add(subnetMaskText);

        JLabel hostLabel = new JLabel("호스트 수:");
        hostLabel.setBounds(20, 60, 80, 25);
        subnetPanel.add(hostLabel);

        JTextField hostText = new JTextField(15);
        hostText.setBounds(100, 60, 150, 25);
        subnetPanel.add(hostText);

        JButton subnetCalcButton = new JButton("계산");
        subnetCalcButton.setBounds(150, 100, 80, 25);
        subnetPanel.add(subnetCalcButton);

        subnetCalcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subnetMask = subnetMaskText.getText();
                if (isValidIPAddress(subnetMask)) {
                    int hostCount = calculateHostCount(subnetMask);
                    hostText.setText(Integer.toString(hostCount));
                } else {
                    hostText.setText("잘못된 입력입니다.");
                }
            }
        });

        tabbedPane.add("서브넷 계산기", subnetPanel);

        // 세 번째 탭: 2진수 / 10진수 변환기
        JPanel conversionPanel = new JPanel();
        conversionPanel.setLayout(null);

        JLabel decimalLabel = new JLabel("10진수:");
        decimalLabel.setBounds(20, 20, 80, 25);
        conversionPanel.add(decimalLabel);

        JTextField decimalText = new JTextField(15);
        decimalText.setBounds(100, 20, 150, 25);
        conversionPanel.add(decimalText);

        JLabel binaryLabel = new JLabel("2진수:");
        binaryLabel.setBounds(20, 60, 80, 25);
        conversionPanel.add(binaryLabel);

        JTextField binaryText = new JTextField(15);
        binaryText.setBounds(100, 60, 150, 25);
        conversionPanel.add(binaryText);

        JButton toBinaryButton = new JButton("2진수 변환");
        toBinaryButton.setBounds(260, 20, 120, 25);
        conversionPanel.add(toBinaryButton);

        JButton toDecimalButton = new JButton("10진수 변환");
        toDecimalButton.setBounds(260, 60, 120, 25);
        conversionPanel.add(toDecimalButton);

        toBinaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String decimal = decimalText.getText();
                try {
                    int decimalValue = Integer.parseInt(decimal);
                    binaryText.setText(Integer.toBinaryString(decimalValue));
                } catch (NumberFormatException ex) {
                    binaryText.setText("잘못된 입력입니다.");
                }
            }
        });

        toDecimalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String binary = binaryText.getText();
                try {
                    int decimalValue = Integer.parseInt(binary, 2);
                    decimalText.setText(Integer.toString(decimalValue));
                } catch (NumberFormatException ex) {
                    decimalText.setText("잘못된 입력입니다.");
                }
            }
        });

        tabbedPane.add("2진수/10진수 변환", conversionPanel);

        // 탭 추가 후 프레임에 추가
        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    // IP 주소의 유효성 검사
    private static boolean isValidIPAddress(String ip) {
        String[] parts = ip.split("\\.");
        if (parts.length != 4) return false;
        try {
            for (String part : parts) {
                int num = Integer.parseInt(part);
                if (num < 0 || num > 255) return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // 네트워크 아이디 계산
    private static String calculateNetworkID(String ip, String subnet) {
        String[] ipParts = ip.split("\\.");
        String[] subnetParts = subnet.split("\\.");

        int[] networkParts = new int[4];
        for (int i = 0; i < 4; i++) {
            networkParts[i] = Integer.parseInt(ipParts[i]) & Integer.parseInt(subnetParts[i]);
        }

        return networkParts[0] + "." + networkParts[1] + "." + networkParts[2] + "." + networkParts[3];
    }

    // 서브넷 마스크를 통해 호스트 수 계산
    private static int calculateHostCount(String subnet) {
        String[] subnetParts = subnet.split("\\.");
        int mask = 0;
        for (String part : subnetParts) {
            mask += Integer.bitCount(Integer.parseInt(part));
        }
        return (int) Math.pow(2, 32 - mask) - 2;
    }
}
