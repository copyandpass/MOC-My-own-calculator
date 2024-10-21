import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NetworkTool {
    


    
    public static void main(String[] args) {
        // JFrame을 생성하여 애플리케이션의 메인 윈도우를 설정합니다.
        JFrame frame = new JFrame("Network Tool");
        frame.setSize(500, 300);  // 윈도우 크기를 설정합니다.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 닫기 버튼을 누르면 프로그램이 종료되도록 설정합니다.
        frame.setLayout(null);  // 레이아웃 매니저를 사용하지 않고 절대 위치를 사용합니다.

        // 여러 기능을 나누기 위해 탭을 생성합니다.
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(10, 10, 460, 240);  // 탭의 크기와 위치를 설정합니다.

        // 첫 번째 탭: 네트워크 아이디 계산기
        JPanel networkPanel = new JPanel();
        networkPanel.setLayout(null);  // 절대 위치를 사용하여 컴포넌트를 배치합니다.

        // IP 주소 입력을 위한 라벨과 텍스트 필드를 추가합니다.
        JLabel ipLabel = new JLabel("IP 주소:");
        ipLabel.setBounds(20, 20, 80, 25);  // 위치와 크기를 설정합니다.
        networkPanel.add(ipLabel);

        JTextField ipText = new JTextField(15);  // 15글자 길이의 텍스트 필드를 만듭니다.
        ipText.setBounds(100, 20, 150, 25);
        networkPanel.add(ipText);

        // 서브넷 마스크 입력을 위한 라벨과 텍스트 필드를 추가합니다.
        JLabel subnetLabel = new JLabel("서브넷 마스크:");
        subnetLabel.setBounds(20, 60, 100, 25);
        networkPanel.add(subnetLabel);

        JTextField subnetText = new JTextField(15);
        subnetText.setBounds(130, 60, 150, 25);
        networkPanel.add(subnetText);

        // 결과를 출력할 레이블과 텍스트 필드를 추가합니다.
        JLabel resultLabel = new JLabel("네트워크 아이디:");
        resultLabel.setBounds(20, 100, 150, 25);
        networkPanel.add(resultLabel);

        JTextField resultText = new JTextField(15);
        resultText.setBounds(150, 100, 150, 25);
        resultText.setEditable(false);  // 결과는 수정할 수 없도록 비활성화합니다.
        networkPanel.add(resultText);

        // 계산 버튼을 추가합니다.
        JButton calculateButton = new JButton("계산");
        calculateButton.setBounds(150, 140, 80, 25);  // 버튼의 크기와 위치를 설정합니다.
        networkPanel.add(calculateButton);

        // 계산 버튼 클릭 시 네트워크 아이디를 계산하는 로직을 추가합니다.
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipText.getText();  // IP 입력값을 가져옵니다.
                String subnet = subnetText.getText();  // 서브넷 마스크 입력값을 가져옵니다.
                // IP와 서브넷 마스크의 유효성을 검사한 후 네트워크 아이디를 계산합니다.
                if (isValidIPAddress(ip) && isValidIPAddress(subnet)) {
                    String networkId = calculateNetworkID(ip, subnet);  // 네트워크 아이디 계산
                    resultText.setText(networkId);  // 결과를 텍스트 필드에 출력합니다.
                } else {
                    resultText.setText("잘못된 입력입니다.");  // 유효하지 않은 경우 오류 메시지를 표시합니다.
                }
            }
        });

        // 네트워크 계산기 탭을 추가합니다.
        tabbedPane.add("네트워크 계산기", networkPanel);

        // 두 번째 탭: 서브넷 계산기
        JPanel subnetPanel = new JPanel();
        subnetPanel.setLayout(null);

        // 서브넷 마스크 입력 라벨과 텍스트 필드를 추가합니다.
        JLabel subnetMaskLabel = new JLabel("서브넷 마스크:");
        subnetMaskLabel.setBounds(20, 20, 100, 25);
        subnetPanel.add(subnetMaskLabel);

        JTextField subnetMaskText = new JTextField(15);
        subnetMaskText.setBounds(130, 20, 150, 25);
        subnetPanel.add(subnetMaskText);

        // 호스트 수 결과를 출력할 라벨과 텍스트 필드를 추가합니다.
        JLabel hostLabel = new JLabel("호스트 수:");
        hostLabel.setBounds(20, 60, 80, 25);
        subnetPanel.add(hostLabel);

        JTextField hostText = new JTextField(15);
        hostText.setBounds(100, 60, 150, 25);
        subnetPanel.add(hostText);

        // 서브넷 계산 버튼을 추가합니다.
        JButton subnetCalcButton = new JButton("계산");
        subnetCalcButton.setBounds(150, 100, 80, 25);
        subnetPanel.add(subnetCalcButton);

        // 서브넷 계산 버튼 클릭 시 서브넷 마스크에서 호스트 수를 계산하는 로직을 추가합니다.
        subnetCalcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subnetMask = subnetMaskText.getText();
                if (isValidIPAddress(subnetMask)) {
                    int hostCount = calculateHostCount(subnetMask);  // 호스트 수 계산
                    hostText.setText(Integer.toString(hostCount));  // 계산된 호스트 수를 출력합니다.
                } else {
                    hostText.setText("잘못된 입력입니다.");  // 유효하지 않은 경우 오류 메시지를 표시합니다.
                }
            }
        });

        // 서브넷 계산기 탭을 추가합니다.
        tabbedPane.add("서브넷 계산기", subnetPanel);

        // 세 번째 탭: 2진수 / 10진수 변환기
        JPanel conversionPanel = new JPanel();
        conversionPanel.setLayout(null);

        // 10진수 입력 라벨과 텍스트 필드를 추가합니다.
        JLabel decimalLabel = new JLabel("10진수:");
        decimalLabel.setBounds(20, 20, 80, 25);
        conversionPanel.add(decimalLabel);

        JTextField decimalText = new JTextField(15);
        decimalText.setBounds(100, 20, 150, 25);
        conversionPanel.add(decimalText);

        // 2진수 입력 라벨과 텍스트 필드를 추가합니다.
        JLabel binaryLabel = new JLabel("2진수:");
        binaryLabel.setBounds(20, 60, 80, 25);
        conversionPanel.add(binaryLabel);

        JTextField binaryText = new JTextField(15);
        binaryText.setBounds(100, 60, 150, 25);
        conversionPanel.add(binaryText);

        // 10진수를 2진수로 변환하는 버튼을 추가합니다.
        JButton toBinaryButton = new JButton("2진수 변환");
        toBinaryButton.setBounds(260, 20, 120, 25);
        conversionPanel.add(toBinaryButton);

        // 2진수를 10진수로 변환하는 버튼을 추가합니다.
        JButton toDecimalButton = new JButton("10진수 변환");
        toDecimalButton.setBounds(260, 60, 120, 25);
        conversionPanel.add(toDecimalButton);

        // 10진수 -> 2진수 변환 로직
        toBinaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String decimal = decimalText.getText();
                try {
                    int decimalValue = Integer.parseInt(decimal);  // 10진수 입력을 숫자로 변환
                    binaryText.setText(Integer.toBinaryString(decimalValue));  // 2진수로 변환하여 출력
                } catch (NumberFormatException ex) {
                    binaryText.setText("잘못된 입력입니다.");  // 입력값이 숫자가 아니면 오류 메시지를 출력
                }
            }
        });

        // 2진수 -> 10진수 변환 로직
        toDecimalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String binary = binaryText.getText();
                try {
                    int decimalValue = Integer.parseInt(binary, 2);  // 2진수를 10진수로 변환
                    decimalText.setText(Integer.toString(decimalValue));  // 결과를 10진수로 출력
                } catch (NumberFormatException ex) {
                    decimalText.setText("잘못된 입력입니다.");  // 입력값이 2진수 형식이 아니면 오류 메시지를 출력
                }
            }
        });

        // 2진수/10진수 변환기 탭을 추가합니다.
        tabbedPane.add("2진수/10진수 변환", conversionPanel);

        // 탭을 프레임에 추가하여 전체 GUI를 구성합니다.
        frame.add(tabbedPane);
        frame.setVisible(true);  // 윈도우를 표시합니다.
   
