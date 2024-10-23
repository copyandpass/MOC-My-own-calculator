import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NetworkTool {

    public static void main(String[] args) {
        // 메인 프레임을 생성하고 크기 및 레이아웃을 설정합니다.
        JFrame frame = createMainFrame();

        // JTabbedPane을 사용하여 여러 기능을 탭으로 나눕니다.
        JTabbedPane tabbedPane = new JTabbedPane();

        // 네트워크 아이디 계산기, 서브넷 계산기, 2진수/10진수 변환기의 패널을 생성하여 탭에 추가합니다.
        tabbedPane.add("네트워크 계산기", createNetworkPanel());
        tabbedPane.add("서브넷 계산기", createSubnetPanel());  // 서브넷 계산기에 호스트 범위 기능이 추가되었습니다.
        tabbedPane.add("2진수/10진수 변환", createConversionPanel());

        // 탭 패널을 메인 프레임에 추가하고, 프레임을 보이도록 설정합니다.
        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    // 메인 프레임을 생성하는 메서드입니다.
    private static JFrame createMainFrame() {
        JFrame frame = new JFrame("Network Tool");
        frame.setSize(500, 300);  // 프레임의 크기를 설정합니다.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // X 버튼 클릭 시 프로그램 종료
        frame.setLayout(null);  // 레이아웃 관리자를 사용하지 않음 (절대 좌표로 배치)
        return frame;
    }

    // 네트워크 아이디 계산기를 위한 패널을 생성하는 메서드입니다.
    private static JPanel createNetworkPanel() {
        JPanel networkPanel = new JPanel();
        networkPanel.setLayout(null);  // 레이아웃 관리자를 사용하지 않고, 절대 위치로 컴포넌트를 배치합니다.

        // IP 주소와 서브넷 마스크 입력을 위한 텍스트 필드와 라벨을 추가합니다.
        JLabel ipLabel = new JLabel("IP 주소:");
        JTextField ipText = createTextFieldWithLabel(networkPanel, ipLabel, 20, 20);

        JLabel subnetLabel = new JLabel("서브넷 마스크:");
        JTextField subnetText = createTextFieldWithLabel(networkPanel, subnetLabel, 20, 60);

        // 네트워크 아이디 계산 결과를 표시할 필드를 추가합니다.
        JLabel resultLabel = new JLabel("네트워크 아이디:");
        JTextField resultText = createTextFieldWithLabel(networkPanel, resultLabel, 20, 100);
        resultText.setEditable(false);  // 결과 필드는 사용자가 수정할 수 없도록 설정합니다.

        // 계산 버튼을 추가하여 클릭 시 IP와 서브넷 마스크로부터 네트워크 아이디를 계산합니다.
        JButton calculateButton = new JButton("계산");
        calculateButton.setBounds(150, 140, 80, 25);
        networkPanel.add(calculateButton);

        // 버튼 클릭 시 발생하는 이벤트를 처리하는 리스너를 추가합니다.
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipText.getText();
                String subnet = subnetText.getText();

                // 유효한 IP 주소와 서브넷 마스크인지 확인한 후, 네트워크 아이디를 계산합니다.
                if (isValidIPAddress(ip) && isValidIPAddress(subnet)) {
                    resultText.setText(calculateNetworkID(ip, subnet));  // 계산된 네트워크 아이디 출력
                } else {
                    resultText.setText("잘못된 입력입니다.");  // 유효하지 않은 입력일 경우 에러 메시지 출력
                }
            }
        });

        return networkPanel;
    }

    // 서브넷 계산기 패널을 생성하는 메서드입니다.
    private static JPanel createSubnetPanel() {
        JPanel subnetPanel = new JPanel();
        subnetPanel.setLayout(null);

        // 서브넷 마스크 입력 필드
        JLabel subnetMaskLabel = new JLabel("서브넷 마스크:");
        JTextField subnetMaskText = createTextFieldWithLabel(subnetPanel, subnetMaskLabel, 20, 20);

        // 가능한 호스트 수 결과를 출력할 필드
        JLabel hostLabel = new JLabel("호스트 수:");
        JTextField hostText = createTextFieldWithLabel(subnetPanel, hostLabel, 20, 60);
        hostText.setEditable(false);  // 결과 필드는 수정 불가능

        // 호스트 범위 필드를 추가합니다.
        JLabel rangeLabel = new JLabel("호스트 범위:");
        JTextField rangeText = createTextFieldWithLabel(subnetPanel, rangeLabel, 20, 100);
        rangeText.setEditable(false);  // 결과 필드는 수정 불가능

        // 계산 버튼을 추가하여 클릭 시 서브넷 마스크로부터 호스트 수와 호스트 범위를 계산합니다.
        JButton subnetCalcButton = new JButton("계산");
        subnetCalcButton.setBounds(150, 140, 80, 25);
        subnetPanel.add(subnetCalcButton);

        // 버튼 클릭 시 발생하는 이벤트를 처리하는 리스너를 추가합니다.
        subnetCalcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subnetMask = subnetMaskText.getText();
                if (isValidIPAddress(subnetMask)) {
                    // 호스트 수 계산 및 결과 출력
                    hostText.setText(Integer.toString(calculateHostCount(subnetMask)));

                    // 호스트 범위 계산 및 결과 출력 (예시 IP 사용)
                    rangeText.setText(calculateHostRange("192.168.1.0", subnetMask));  // 네트워크 ID가 필요
                } else {
                    hostText.setText("잘못된 입력입니다.");
                    rangeText.setText("잘못된 입력입니다.");
                }
            }
        });

        return subnetPanel;
    }

    // 2진수 / 10진수 변환기 패널을 생성하는 메서드입니다.
    private static JPanel createConversionPanel() {
        JPanel conversionPanel = new JPanel();
        conversionPanel.setLayout(null);

        // 10진수 입력 필드
        JLabel decimalLabel = new JLabel("10진수:");
        JTextField decimalText = createTextFieldWithLabel(conversionPanel, decimalLabel, 20, 20);

        // 2진수 입력 필드
        JLabel binaryLabel = new JLabel("2진수:");
        JTextField binaryText = createTextFieldWithLabel(conversionPanel, binaryLabel, 20, 60);

        // 10진수를 2진수로 변환하는 버튼
        JButton toBinaryButton = new JButton("2진수 변환");
        toBinaryButton.setBounds(260, 20, 120, 25);
        conversionPanel.add(toBinaryButton);

        // 2진수를 10진수로 변환하는 버튼
        JButton toDecimalButton = new JButton("10진수 변환");
        toDecimalButton.setBounds(260, 60, 120, 25);
        conversionPanel.add(toDecimalButton);

        // 10진수를 2진수로 변환하는 로직
        toBinaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int decimalValue = Integer.parseInt(decimalText.getText());
                    binaryText.setText(Integer.toBinaryString(decimalValue));  // 변환 결과를 2진수 필드에 출력
                } catch (NumberFormatException ex) {
                    binaryText.setText("잘못된 입력입니다.");  // 잘못된 입력일 경우 에러 메시지 출력
                }
            }
        });

        // 2진수를 10진수로 변환하는 로직
        toDecimalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int decimalValue = Integer.parseInt(binaryText.getText(), 2);
                    decimalText.setText(Integer.toString(decimalValue));  // 변환 결과를 10진수 필드에 출력
                } catch (NumberFormatException ex) {
                    decimalText.setText("잘못된 입력입니다.");  // 잘못된 입력일 경우 에러 메시지 출력
                }
            }
        });

        return conversionPanel;
    }

    // 공통으로 사용되는 텍스트 필드와 라벨을 생성하는 메서드입니다.
    private static JTextField createTextFieldWithLabel(JPanel panel, JLabel label, int x, int y) {
        label.setBounds(x, y, 100, 25);  // 라벨 위치 및 크기 설정
        panel.add(label);

        JTextField textField = new JTextField(15);  // 15글자 길이의 텍스트 필드 생성
        textField.setBounds(x + 110, y, 150, 25);  // 텍스트 필드 위치 및 크기 설정
        panel.add(textField);

        return textField;
    }

    // 입력된 IP 주소의 유효성을 확인하는 메서드입니다.
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

    // IP 주소와 서브넷 마스크를 사용하여 네트워크 ID를 계산하는 메서드입니다.
    private static String calculateNetworkID(String ip, String subnet) {
        String[] ipParts = ip.split("\\.");
        String[] subnetParts = subnet.split("\\.");

        int[] networkParts = new int[4];
        for (int i = 0; i < 4; i++) {
            networkParts[i] = Integer.parseInt(ipParts[i]) & Integer.parseInt(subnetParts[i]);
        }

        return networkParts[0] + "." + networkParts[1] + "." + networkParts[2] + "." + networkParts[3];
    }

    // 서브넷 마스크로부터 호스트 수를 계산하는 메서드입니다.
    private static int calculateHostCount(String subnet) {
        String[] subnetParts = subnet.split("\\.");
        int mask = 0;

        for (String part : subnetParts) {
            mask += Integer.bitCount(Integer.parseInt(part));
        }

        return (int) Math.pow(2, 32 - mask) - 2;  // 가능한 호스트 수 계산
    }

    // 호스트 범위를 계산하는 메서드입니다.
    private static String calculateHostRange(String networkID, String subnet) {
        String[] ipParts = networkID.split("\\.");
        String[] subnetParts = subnet.split("\\.");

        // 브로드캐스트 주소 계산
        int[] broadcastParts = new int[4];
        for (int i = 0; i < 4; i++) {
            broadcastParts[i] = Integer.parseInt(ipParts[i]) | (~Integer.parseInt(subnetParts[i]) & 255);
        }

        String broadcastAddress = broadcastParts[0] + "." + broadcastParts[1] + "." + broadcastParts[2] + "." + broadcastParts[3];

        // 첫 번째 호스트와 마지막 호스트 계산 (네트워크 ID + 1, 브로드캐스트 - 1)
        String firstHost = (Integer.parseInt(ipParts[0]) + 1) + "." + ipParts[1] + "." + ipParts[2] + "." + ipParts[3];
        String lastHost = (broadcastParts[0] - 1) + "." + broadcastParts[1] + "." + broadcastParts[2] + "." + broadcastParts[3];

        return firstHost + " ~ " + lastHost;  // 호스트 범위 반환
    }
}
