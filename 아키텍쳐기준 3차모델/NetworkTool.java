import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NetworkTool {

    public static void main(String[] args) {
        // 메인 프레임 생성 및 설정
        JFrame frame = createMainFrame();
        JTabbedPane tabbedPane = new JTabbedPane();

        // 각 탭에 해당하는 패널을 생성하고 추가
        tabbedPane.add("네트워크 계산기", createNetworkPanel());
        tabbedPane.add("서브넷 계산기", createSubnetPanel());
        tabbedPane.add("2진수/10진수 변환", createConversionPanel());

        // 프레임에 탭을 추가하고 보여주기
        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    // 메인 프레임 생성 메서드
    private static JFrame createMainFrame() {
        JFrame frame = new JFrame("Network Tool");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);  // 절대 위치 레이아웃 사용
        return frame;
    }

    // 네트워크 아이디 계산기 패널 생성 메서드
    private static JPanel createNetworkPanel() {
        JPanel networkPanel = new JPanel();
        networkPanel.setLayout(null);

        // IP 주소 입력 필드 및 서브넷 마스크 입력 필드
        JLabel ipLabel = new JLabel("IP 주소:");
        JTextField ipText = createTextFieldWithLabel(networkPanel, ipLabel, 20, 20);

        JLabel subnetLabel = new JLabel("서브넷 마스크:");
        JTextField subnetText = createTextFieldWithLabel(networkPanel, subnetLabel, 20, 60);

        // 결과 출력 필드
        JLabel resultLabel = new JLabel("네트워크 아이디:");
        JTextField resultText = createTextFieldWithLabel(networkPanel, resultLabel, 20, 100);
        resultText.setEditable(false);

        // 계산 버튼 생성 및 이벤트 처리
        JButton calculateButton = new JButton("계산");
        calculateButton.setBounds(150, 140, 80, 25);
        networkPanel.add(calculateButton);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipText.getText();
                String subnet = subnetText.getText();
                if (isValidIPAddress(ip) && isValidIPAddress(subnet)) {
                    resultText.setText(calculateNetworkID(ip, subnet));
                } else {
                    resultText.setText("잘못된 입력입니다.");
                }
            }
        });

        return networkPanel;
    }

    // 서브넷 계산기 패널 생성 메서드
    private static JPanel createSubnetPanel() {
        JPanel subnetPanel = new JPanel();
        subnetPanel.setLayout(null);

        // 서브넷 마스크 입력 필드
        JLabel subnetMaskLabel = new JLabel("서브넷 마스크:");
        JTextField subnetMaskText = createTextFieldWithLabel(subnetPanel, subnetMaskLabel, 20, 20);

        // 호스트 수 결과 필드
        JLabel hostLabel = new JLabel("호스트 수:");
        JTextField hostText = createTextFieldWithLabel(subnetPanel, hostLabel, 20, 60);
        hostText.setEditable(false);

        // 계산 버튼
        JButton subnetCalcButton = new JButton("계산");
        subnetCalcButton.setBounds(150, 100, 80, 25);
        subnetPanel.add(subnetCalcButton);

        // 서브넷 계산 버튼 클릭 시 로직 처리
        subnetCalcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subnetMask = subnetMaskText.getText();
                if (isValidIPAddress(subnetMask)) {
                    hostText.setText(Integer.toString(calculateHostCount(subnetMask)));
                } else {
                    hostText.setText("잘못된 입력입니다.");
                }
            }
        });

        return subnetPanel;
    }

    // 2진수/10진수 변환 패널 생성 메서드
    private static JPanel createConversionPanel() {
        JPanel conversionPanel = new JPanel();
        conversionPanel.setLayout(null);

        // 10진수 및 2진수 입력 필드
        JLabel decimalLabel = new JLabel("10진수:");
        JTextField decimalText = createTextFieldWithLabel(conversionPanel, decimalLabel, 20, 20);

        JLabel binaryLabel = new JLabel("2진수:");
        JTextField binaryText = createTextFieldWithLabel(conversionPanel, binaryLabel, 20, 60);

        // 10진수 -> 2진수 변환 버튼
        JButton toBinaryButton = new JButton("2진수 변환");
        toBinaryButton.setBounds(260, 20, 120, 25);
        conversionPanel.add(toBinaryButton);

        // 2진수 -> 10진수 변환 버튼
        JButton toDecimalButton = new JButton("10진수 변환");
        toDecimalButton.setBounds(260, 60, 120, 25);
        conversionPanel.add(toDecimalButton);

        // 10진수 -> 2진수 변환 로직
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

        // 2진수 -> 10진수 변환 로직
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

        return conversionPanel;
    }

    // 공통으로 사용되는 텍스트 필드 생성 메서드 (라벨 포함)
    private static JTextField createTextFieldWithLabel(JPanel panel, JLabel label, int x, int y) {
        label.setBounds(x, y, 100, 25);
        panel.add(label);

        JTextField textField = new JTextField(15);
        textField.setBounds(x + 110, y, 150, 25);
        panel.add(textField);

        return textField;
    }

    // 유효한 IP 주소인지 확인하는 메서드
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

    // 네트워크 아이디 계산 메서드
    private static String calculateNetworkID(String ip, String subnet) {
        String[] ipParts = ip.split("\\.");
        String[] subnetParts = subnet.split("\\.");

        int[] networkParts = new int[4];
        for (int i = 0; i < 4; i++) {
            networkParts[i] = Integer.parseInt(ipParts[i]) & Integer.parseInt(subnetParts[i]);
        }

        return networkParts[0] + "." + networkParts[1] + "." + networkParts[2] + "." + networkParts[3];
    }

    // 서브넷 마스크에서 가능한 호스트 수 계산
    private static int calculateHostCount(String subnet) {
        String[] subnetParts = subnet.split("\\.");
        int mask = 0;
        for (String part : subnetParts) {
            mask += Integer.bitCount(Integer.parseInt(part));
        }
        return (int) Math.pow(2, 32 - mask) - 2;
    }
}
