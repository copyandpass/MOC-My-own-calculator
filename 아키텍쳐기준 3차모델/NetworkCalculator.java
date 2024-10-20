import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NetworkCalculator {
    
    public static void main(String[] args) {
        // JFrame 설정
        JFrame frame = new JFrame("Network ID Calculator");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // IP 주소 입력 필드
        JLabel ipLabel = new JLabel("IP 주소:");
        ipLabel.setBounds(20, 20, 80, 25);
        frame.add(ipLabel);

        JTextField ipText = new JTextField(15);
        ipText.setBounds(100, 20, 150, 25);
        frame.add(ipText);

        // 서브넷 마스크 입력 필드
        JLabel subnetLabel = new JLabel("서브넷 마스크:");
        subnetLabel.setBounds(20, 60, 100, 25);
        frame.add(subnetLabel);

        JTextField subnetText = new JTextField(15);
        subnetText.setBounds(130, 60, 150, 25);
        frame.add(subnetText);

        // 결과 출력 레이블
        JLabel resultLabel = new JLabel("네트워크 아이디:");
        resultLabel.setBounds(20, 100, 150, 25);
        frame.add(resultLabel);

        JTextField resultText = new JTextField(15);
        resultText.setBounds(150, 100, 150, 25);
        resultText.setEditable(false);  // 결과는 수정 불가
        frame.add(resultText);

        // 계산 버튼
        JButton calculateButton = new JButton("계산");
        calculateButton.setBounds(150, 140, 80, 25);
        frame.add(calculateButton);

        // 버튼 클릭 이벤트 처리
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipText.getText();
                String subnet = subnetText.getText();

                // IP와 서브넷이 올바른 형식인지 확인 후 네트워크 아이디 계산
                if (isValidIPAddress(ip) && isValidIPAddress(subnet)) {
                    String networkId = calculateNetworkID(ip, subnet);
                    resultText.setText(networkId);
                } else {
                    resultText.setText("잘못된 입력입니다.");
                }
            }
        });

        // 프레임 표시
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

        // 네트워크 아이디 계산 (IP와 서브넷 마스크의 비트 AND 연산)
        for (int i = 0; i < 4; i++) {
            networkParts[i] = Integer.parseInt(ipParts[i]) & Integer.parseInt(subnetParts[i]);
        }

        return networkParts[0] + "." + networkParts[1] + "." + networkParts[2] + "." + networkParts[3];
    }
}
