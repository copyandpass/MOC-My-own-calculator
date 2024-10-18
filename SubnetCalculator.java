import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubnetCalculator {

    public static void main(String[] args) {
        // 프레임 생성
        JFrame frame = new JFrame("Subnet Mask Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(null);

        // IP 주소 입력 필드
        JLabel ipLabel = new JLabel("IP Address:");
        ipLabel.setBounds(30, 20, 100, 25);
        frame.add(ipLabel);

        JTextField ipField = new JTextField();
        ipField.setBounds(130, 20, 200, 25);
        frame.add(ipField);

        // 서브넷 마스크 크기 입력 필드
        JLabel subnetLabel = new JLabel("Subnet Mask:");
        subnetLabel.setBounds(30, 60, 100, 25);
        frame.add(subnetLabel);

        JTextField subnetField = new JTextField();
        subnetField.setBounds(130, 60, 200, 25);
        frame.add(subnetField);

        // 결과 출력 레이블
        JLabel resultLabel = new JLabel("Result:");
        resultLabel.setBounds(30, 140, 300, 25);
        frame.add(resultLabel);

        // 계산 버튼
        JButton calculateButton = new JButton("Calculate");
        calculateButton.setBounds(130, 100, 100, 25);
        frame.add(calculateButton);

        // 버튼에 액션 리스너 추가
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ipAddress = ipField.getText();
                String subnetMaskInput = subnetField.getText();

                try {
                    int subnetMaskLength = Integer.parseInt(subnetMaskInput);
                    int subnetMask = 0xFFFFFFFF << (32 - subnetMaskLength);
                    int networkAddress = ipToInt(ipAddress) & subnetMask;

                    String subnetMaskStr = intToIp(subnetMask);
                    String networkAddressStr = intToIp(networkAddress);

                    resultLabel.setText("Subnet Mask: " + subnetMaskStr + " | Network: " + networkAddressStr);
                } catch (Exception ex) {
                    resultLabel.setText("Error: Invalid input");
                }
            }
        });

        // 프레임을 화면에 표시
        frame.setVisible(true);
    }

    // IP 주소를 정수로 변환
    public static int ipToInt(String ipAddress) {
        String[] parts = ipAddress.split("\\.");
        int ip = 0;
        for (int i = 0; i < 4; i++) {
            ip <<= 8;
            ip |= Integer.parseInt(parts[i]);
        }
        return ip;
    }

    // 정수를 다시 IP 주소 형식으로 변환
    public static String intToIp(int ip) {
        return ((ip >> 24) & 0xFF) + "." +
               ((ip >> 16) & 0xFF) + "." +
               ((ip >> 8) & 0xFF) + "." +
               (ip & 0xFF);
    }
}
