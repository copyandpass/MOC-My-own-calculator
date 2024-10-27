import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LastCalculator {

    public static void main(String[] args) {
        // 메인 프레임을 생성하고 크기와 기본 종료 동작을 설정합니다.
        JFrame frame = new JFrame("LastCalculator");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // 화면 중앙에 배치

        // 탭을 추가할 JTabbedPane을 생성합니다.
        JTabbedPane tabbedPane = new JTabbedPane();

        // 네트워크 계산기, 서브넷 계산기, 2진수/10진수 변환기의 탭을 추가합니다.
        tabbedPane.add("네트워크 계산기", createNetworkPanel());
        tabbedPane.add("서브넷 계산기", createSubnetPanel());
        tabbedPane.add("2진수/10진수 변환기", createConversionPanel());

        // 메인 패널에 배경 이미지를 설정합니다.
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("CaculatorBackGround.PNG");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // 프레임에 메인 패널을 추가하고 보이도록 설정합니다.
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // 네트워크 아이디와 브로드캐스트 주소를 계산하는 패널을 생성합니다.
    private static JPanel createNetworkPanel() {
        JPanel networkPanel = new JPanel();
        networkPanel.setLayout(new GridLayout(6, 2, 10, 10)); // 격자 레이아웃

        // IP 주소와 서브넷 마스크 입력 필드
        JLabel ipLabel = new JLabel("IP 주소:");
        JTextField ipText = new JTextField();
        ipText.setPreferredSize(new Dimension(100, 30)); // 텍스트 필드 크기 설정

        JLabel subnetLabel = new JLabel("서브넷 마스크:");
        JTextField subnetText = new JTextField();
        subnetText.setPreferredSize(new Dimension(100, 30)); // 텍스트 필드 크기 설정

        // 결과 출력 필드
        JLabel resultLabel = new JLabel("네트워크 아이디:");
        JTextField resultText = new JTextField();
        resultText.setEditable(false);
        resultText.setBackground(Color.LIGHT_GRAY);

        JLabel broadcastLabel = new JLabel("브로드캐스트 주소:");
        JTextField broadcastText = new JTextField();
        broadcastText.setEditable(false);
        broadcastText.setBackground(Color.LIGHT_GRAY);

        // 계산 버튼
        JButton calculateButton = new JButton("계산");
        calculateButton.setBackground(Color.BLUE);
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false);
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipText.getText();
                String subnet = subnetText.getText();

                // IP 주소와 서브넷 마스크가 유효한지 확인
                if (isValidIPAddress(ip) && isValidIPAddress(subnet)) {
                    resultText.setText(calculateNetworkID(ip, subnet));  // 네트워크 아이디 계산
                    broadcastText.setText(calculateBroadcastAddress(ip, subnet));  // 브로드캐스트 주소 계산
                } else {
                    resultText.setText("잘못된 입력입니다.");
                    broadcastText.setText("잘못된 입력입니다.");
                }
            }
        });

        // 패널에 컴포넌트를 추가합니다.
        networkPanel.add(ipLabel);
        networkPanel.add(ipText);
        networkPanel.add(subnetLabel);
        networkPanel.add(subnetText);
        networkPanel.add(resultLabel);
        networkPanel.add(resultText);
        networkPanel.add(broadcastLabel);
        networkPanel.add(broadcastText);
        networkPanel.add(new JLabel()); // 빈 레이블로 공간 확보
        networkPanel.add(calculateButton);

        return networkPanel;
    }

    // 서브넷 마스크를 기반으로 호스트 수와 범위를 계산하는 패널을 생성합니다.
    private static JPanel createSubnetPanel() {
        JPanel subnetPanel = new JPanel();
        subnetPanel.setLayout(new GridLayout(6, 2, 10, 10)); // 격자 레이아웃

        // 서브넷 마스크 입력 필드
        JLabel subnetMaskLabel = new JLabel("서브넷 마스크:");
        JTextField subnetMaskText = new JTextField();

        // 호스트 수 출력 필드
        JLabel hostLabel = new JLabel("호스트 수:");
        JTextField hostText = new JTextField();
        hostText.setEditable(false);
        hostText.setBackground(Color.LIGHT_GRAY);

        // 호스트 범위 출력 필드
        JLabel rangeLabel = new JLabel("호스트 범위:");
        JTextField rangeText = new JTextField();
        rangeText.setEditable(false);
        rangeText.setBackground(Color.LIGHT_GRAY);

        // 계산 버튼
        JButton subnetCalcButton = new JButton("계산");
        subnetCalcButton.setBackground(Color.BLUE);
        subnetCalcButton.setForeground(Color.WHITE);
        subnetCalcButton.setFocusPainted(false);
        subnetCalcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subnetMask = subnetMaskText.getText();
                if (isValidIPAddress(subnetMask)) {
                    hostText.setText(Integer.toString(calculateHostCount(subnetMask)));  // 호스트 수 계산
                    rangeText.setText(calculateHostRange("192.168.1.0", subnetMask));  // 호스트 범위 계산
                } else {
                    hostText.setText("잘못된 입력입니다.");
                    rangeText.setText("잘못된 입력입니다.");
                }
            }
        });

        // 패널에 컴포넌트를 추가합니다.
        subnetPanel.add(subnetMaskLabel);
        subnetPanel.add(subnetMaskText);
        subnetPanel.add(hostLabel);
        subnetPanel.add(hostText);
        subnetPanel.add(rangeLabel);
        subnetPanel.add(rangeText);
        subnetPanel.add(new JLabel()); // 빈 레이블로 공간 확보
        subnetPanel.add(subnetCalcButton);

        return subnetPanel;
    }

    // 2진수와 10진수 간의 변환을 위한 패널을 생성합니다.
    private static JPanel createConversionPanel() {
        JPanel conversionPanel = new JPanel();
        conversionPanel.setLayout(new GridLayout(6, 2, 10, 10)); // 격자 레이아웃

        // 10진수 입력 필드
        JLabel decimalLabel = new JLabel("10진수:");
        JTextField decimalText = new JTextField();

        // 2진수 입력 필드
        JLabel binaryLabel = new JLabel("2진수:");
        JTextField binaryText = new JTextField();

        // 10진수를 2진수로 변환 버튼
        JButton toBinaryButton = new JButton("2진수 변환");
        toBinaryButton.setBackground(Color.GREEN);
        toBinaryButton.setForeground(Color.WHITE);
        toBinaryButton.setFocusPainted(false);
        toBinaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int decimalValue = Integer.parseInt(decimalText.getText());
                    binaryText.setText(Integer.toBinaryString(decimalValue));  // 변환 결과 출력
                } catch (NumberFormatException ex) {
                    binaryText.setText("잘못된 입력입니다.");
                }
            }
        });

        // 2진수를 10진수로 변환 버튼
        JButton toDecimalButton = new JButton("10진수 변환");
        toDecimalButton.setBackground(Color.GREEN);
        toDecimalButton.setForeground(Color.WHITE);
        toDecimalButton.setFocusPainted(false);
        toDecimalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int decimalValue = Integer.parseInt(binaryText.getText(), 2);
                    decimalText.setText(Integer.toString(decimalValue));  // 변환 결과 출력
                } catch (NumberFormatException ex) {
                    decimalText.setText("잘못된 입력입니다.");
                }
            }
        });

        // 패널에 컴포넌트를 추가합니다.
        conversionPanel.add(decimalLabel);
        conversionPanel.add(decimalText);
        conversionPanel.add(toBinaryButton);
        conversionPanel.add(binaryLabel);
        conversionPanel.add(binaryText);
        conversionPanel.add(toDecimalButton);

        return conversionPanel;
    }

    // IP 주소의 유효성을 검사하는 메서드입니다.
    private static boolean isValidIPAddress(String ip) {
        String[] parts = ip.split("\\.");
        if (parts.length != 4) return false;
        try {
            for (String part : parts) {
                int num = Integer.parseInt(part);
                if (num < 0 || num > 255) return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    // 네트워크 ID를 계산하는 메서드입니다.
    private static String calculateNetworkID(String ip, String subnet) {
        // 여기에 네트워크 ID 계산 로직을 추가합니다.
        return ip; // 임시값
    }

    // 브로드캐스트 주소를 계산하는 메서드입니다.
    private static String calculateBroadcastAddress(String ip, String subnet) {
        // 여기에 브로드캐스트 주소 계산 로직을 추가합니다.
        return ip; // 임시값
    }

    // 호스트 수를 계산하는 메서드입니다.
    private static int calculateHostCount(String subnet) {
        // 여기에 호스트 수 계산 로직을 추가합니다.
        return 0; // 임시값
    }

    // 호스트 범위를 계산하는 메서드입니다.
    private static String calculateHostRange(String baseIP, String subnet) {
        // 여기에 호스트 범위 계산 로직을 추가합니다.
        return "범위"; // 임시값
    }
}
