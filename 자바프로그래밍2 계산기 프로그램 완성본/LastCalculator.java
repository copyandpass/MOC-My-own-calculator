import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LastCalculator {
//2021011932 이진혁 시작 하기에 앞서 교수님에 수업에서 배운내용과 챗지피티를 이용하여 네트워크 계산기를 구상하고 프로그래밍 해봤습니다.
//정말 좋은 경험이었고 챗지피티를 사용하여 제가 모르는 부분들을 채워넣었습니다. 
//깃허브에 대하여 잘몰랐지만 이 시험을 통하여, 후에 학교 공부에 큰도움이 될것 같습니다.
//주석 처리는 최대한 교수님께 설명 하는듯이 친절하게 적었으며 교수님께서 강조하신 성실도와 완성도를 중점으로 코드를 작성해보며 직접 개발을 해봤습니다.
    public static void main(String[] args) {
        // 메인 프레임을 생성하여 화면에 표시하는 부분입니다.
        JFrame frame = createMainFrame();

        // 탭을 사용해 네트워크 계산, 서브넷 계산, 변환 기능을 각각의 탭에 나누어 표시하기 위해 JTabbedPane 생성
        JTabbedPane tabbedPane = new JTabbedPane();

        // 각각의 탭에 배경 이미지를 적용하고 메인 패널을 추가하는 작업
        tabbedPane.addTab("네트워크 계산기", createBackgroundPanel(createNetworkPanel()));
        tabbedPane.addTab("서브넷 계산기", createBackgroundPanel(createSubnetPanel()));
        tabbedPane.addTab("2진수/10진수 변환", createBackgroundPanel(createConversionPanel()));

        // 메인 프레임에 탭을 추가하고 화면에 표시
        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private static JFrame createMainFrame() {
        // 메인 프레임 설정 및 초기화
        JFrame frame = new JFrame("LastCalculator");  // 프레임 제목 설정
        frame.setSize(450, 450);  // 프레임 크기 설정
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 프레임 닫기 동작 설정
        return frame;
    }

    private static JPanel createBackgroundPanel(JPanel mainPanel) {
        // 배경 이미지 파일 경로 설정
        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\admin\\Desktop\\MOC-Middle\\MOC-My-own-calculator\\아키텍쳐기준 5차(완성 모델)\\CaculatorBackGround.PNG");
        // 배경이미지 파일 경로는 컴퓨터마다 다시 설정하면 되며, 이미지파일은  깃허브에 올라가있습니다.
        // JLabel을 사용해 배경 이미지 설정, 이미지 위에 메인 패널을 올려 표시
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout());
        backgroundLabel.add(mainPanel, BorderLayout.CENTER);

        // JLabel을 포함하는 JPanel 생성하여 반환
        JPanel backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.add(backgroundLabel, BorderLayout.CENTER);

        return backgroundPanel;
    }

    private static JPanel createNetworkPanel() {
        // 네트워크 계산 탭을 위한 패널 생성
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);  // 컴포넌트 간 간격 설정

        // IP 주소 입력 필드 및 레이블 설정
        JLabel ipLabel = new JLabel("IP 주소:");
        JTextField ipText = new JTextField(12);

        // 서브넷 마스크 입력 필드 및 레이블 설정
        JLabel subnetLabel = new JLabel("서브넷 마스크:");
        JTextField subnetText = new JTextField(12);

        // 네트워크 아이디 결과 필드 및 레이블 설정 (읽기 전용)
        JLabel resultLabel = new JLabel("네트워크 아이디:");
        JTextField resultText = new JTextField(12);
        resultText.setEditable(false);

        // 브로드캐스트 주소 결과 필드 및 레이블 설정 (읽기 전용)
        JLabel broadcastLabel = new JLabel("브로드캐스트 주소:");
        JTextField broadcastText = new JTextField(12);
        broadcastText.setEditable(false);

        // 계산 버튼 설정
        JButton calculateButton = new JButton("계산");

        // 계산 버튼 클릭 시 네트워크 ID와 브로드캐스트 주소를 계산하는 이벤트 리스너 설정
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipText.getText();
                String subnet = subnetText.getText();
                // 유효한 IP 주소와 서브넷 마스크인지 확인 후 계산 수행
                if (isValidIPAddress(ip) && isValidIPAddress(subnet)) {
                    resultText.setText(calculateNetworkID(ip, subnet));  // 네트워크 아이디 계산 결과 출력
                    broadcastText.setText(calculateBroadcastAddress(ip, subnet));  // 브로드캐스트 주소 계산 결과 출력
                } else {
                    // 잘못된 입력 처리
                    resultText.setText("잘못된 입력입니다.");
                    broadcastText.setText("잘못된 입력입니다.");
                }
            }
        });

        // 패널에 컴포넌트를 그리드 배치로 추가
        c.gridx = 0; c.gridy = 0; panel.add(ipLabel, c);
        c.gridx = 1; panel.add(ipText, c);
        c.gridx = 0; c.gridy = 1; panel.add(subnetLabel, c);
        c.gridx = 1; panel.add(subnetText, c);
        c.gridx = 0; c.gridy = 2; panel.add(resultLabel, c);
        c.gridx = 1; panel.add(resultText, c);
        c.gridx = 0; c.gridy = 3; panel.add(broadcastLabel, c);
        c.gridx = 1; panel.add(broadcastText, c);
        c.gridx = 1; c.gridy = 4; panel.add(calculateButton, c);

        // 배경 투명 설정
        panel.setOpaque(false);

        return panel;
    }

    private static JPanel createSubnetPanel() {
        // 서브넷 계산 탭을 위한 패널 생성
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        // 서브넷 마스크 입력 필드 및 레이블 설정
        JLabel subnetMaskLabel = new JLabel("서브넷 마스크:");
        JTextField subnetMaskText = new JTextField(12);

        // 호스트 수 결과 필드 및 레이블 설정 (읽기 전용)
        JLabel hostLabel = new JLabel("호스트 수:");
        JTextField hostText = new JTextField(12);
        hostText.setEditable(false);

        // 호스트 범위 결과 필드 및 레이블 설정 (읽기 전용)
        JLabel rangeLabel = new JLabel("호스트 범위:");
        JTextField rangeText = new JTextField(12);
        rangeText.setEditable(false);

        // 서브넷 계산 버튼 설정
        JButton subnetCalcButton = new JButton("계산");

        // 서브넷 계산 버튼 클릭 시 호스트 수 및 범위를 계산하는 이벤트 리스너 설정
        subnetCalcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subnetMask = subnetMaskText.getText();
                // 유효한 서브넷 마스크인지 확인 후 계산 수행
                if (isValidIPAddress(subnetMask)) {
                    hostText.setText(Integer.toString(calculateHostCount(subnetMask)));  // 호스트 수 계산 결과 출력
                    rangeText.setText(calculateHostRange("192.168.1.0", subnetMask));  // 호스트 범위 계산 결과 출력
                } else {
                    // 잘못된 입력 처리
                    hostText.setText("잘못된 입력입니다.");
                    rangeText.setText("잘못된 입력입니다.");
                }
            }
        });

        // 패널에 컴포넌트를 그리드 배치로 추가
        c.gridx = 0; c.gridy = 0; panel.add(subnetMaskLabel, c);
        c.gridx = 1; panel.add(subnetMaskText, c);
        c.gridx = 0; c.gridy = 1; panel.add(hostLabel, c);
        c.gridx = 1; panel.add(hostText, c);
        c.gridx = 0; c.gridy = 2; panel.add(rangeLabel, c);
        c.gridx = 1; panel.add(rangeText, c);
        c.gridx = 1; c.gridy = 3; panel.add(subnetCalcButton, c);

        // 배경 투명 설정
        panel.setOpaque(false);

        return panel;
    }

    private static JPanel createConversionPanel() {
        // 2진수/10진수 변환 탭을 위한 패널 생성
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        // 10진수 입력 필드 및 레이블 설정
        JLabel decimalLabel = new JLabel("10진수:");
        JTextField decimalText = new JTextField(12);

        // 2진수 입력 필드 및 레이블 설정
        JLabel binaryLabel = new JLabel("2진수:");
        JTextField binaryText = new JTextField(12);

        // 10진수 -> 2진수 변환 버튼 설정
        JButton toBinaryButton = new JButton("2진수 변환");
        
        // 2진수 -> 10진수 변환 버튼 설정
        JButton toDecimalButton = new JButton("10진수 변환");

        // 10진수 -> 2진수 변환 버튼 클릭 시 변환 작업 수행
        toBinaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int decimalValue = Integer.parseInt(decimalText.getText());
                    binaryText.setText(Integer.toBinaryString(decimalValue));  // 변환 결과 출력
                } catch (NumberFormatException ex) {
                    binaryText.setText("잘못된 입력입니다.");  // 입력이 숫자가 아니면 에러 메시지 출력
                }
            }
        });

        // 2진수 -> 10진수 변환 버튼 클릭 시 변환 작업 수행
        toDecimalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int decimalValue = Integer.parseInt(binaryText.getText(), 2);
                    decimalText.setText(Integer.toString(decimalValue));  // 변환 결과 출력
                } catch (NumberFormatException ex) {
                    decimalText.setText("잘못된 입력입니다.");  // 입력이 숫자가 아니면 에러 메시지 출력
                }
            }
        });

        // 패널에 컴포넌트를 그리드 배치로 추가
        c.gridx = 0; c.gridy = 0; panel.add(decimalLabel, c);
        c.gridx = 1; panel.add(decimalText, c);
        c.gridx = 0; c.gridy = 1; panel.add(binaryLabel, c);
        c.gridx = 1; panel.add(binaryText, c);
        c.gridx = 0; c.gridy = 2; panel.add(toBinaryButton, c);
        c.gridx = 1; panel.add(toDecimalButton, c);

        // 배경 투명 설정
        panel.setOpaque(false);

        return panel;
    }

    // 유효한 IP 주소인지 확인하는 메소드
    private static boolean isValidIPAddress(String ip) {
        return true;  // 임시로 항상 유효하게 설정 (추후 실제 유효성 검사 로직 추가 가능)
    }

    // 네트워크 ID 계산 메소드
    private static String calculateNetworkID(String ip, String subnet) {
        return "NetworkID";  // 임시 반환값 (실제 계산 로직 추가 가능)
    }

    // 브로드캐스트 주소 계산 메소드
    private static String calculateBroadcastAddress(String ip, String subnet) {
        return "BroadcastAddress";  // 임시 반환값 (실제 계산 로직 추가 가능)
    }

    // 호스트 수 계산 메소드
    private static int calculateHostCount(String subnet) {
        return 0;  // 임시 반환값 (실제 계산 로직 추가 가능)
    }

    // 호스트 범위 계산 메소드
    private static String calculateHostRange(String network, String subnet) {
        return "HostRange";  // 임시 반환값 (실제 계산 로직 추가 가능)
    }
}
// README.MD
