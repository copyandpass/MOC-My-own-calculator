import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubnetCalculator {

    public static void main(String[] args) {
        JFrame frame = new JFrame("서브넷마스크 계산기");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);// 자꾸 결과값이 끝자락에 걸쳐서 잘리기때문에 프레임 필드를 확장시켰습니다.
        frame.setLayout(null);

        // IP 주소 입력 필드
        JLabel ipLabel = new JLabel("IP 주소:");
        ipLabel.setBounds(30, 20, 100, 25);
        frame.add(ipLabel);

        JTextField ipField = new JTextField();// 전 코드와는 다르게 한국인들을 위한 계산기로짜봤습니다.
        ipField.setBounds(130, 20, 300, 25);
        frame.add(ipField);

        JLabel ipExample = new JLabel("(예: 192.168.1.1)");
        ipExample.setBounds(130, 45, 200, 20);
        ipExample.setForeground(Color.DARK_GRAY);// 흐린 다크 그레이 색상
        frame.add(ipExample);// 예시를 들어줘야 이해가 편할것 같아서 변경했습니다.

        // 서브넷 마스크 크기 입력 필드
        JLabel subnetLabel = new JLabel("서브넷 마스크:");
        subnetLabel.setBounds(30, 70, 100, 25);
        frame.add(subnetLabel);

        JTextField subnetField = new JTextField();
        subnetField.setBounds(130, 70, 300, 25);
        frame.add(subnetField);

        JLabel subnetExample = new JLabel("(예: 24)");
        subnetExample.setBounds(130, 95, 200, 20);
        subnetExample.setForeground(Color.DARK_GRAY);
        frame.add(subnetExample);// 흐린 다크 그레이 색상

        // 결과 출력 레이블
        JLabel resultLabel = new JLabel("결과 값:");
        resultLabel.setBounds(30, 170, 450, 25);
        frame.add(resultLabel); // 레이블 크기 확대

        // 계산 버튼
        JButton calculateButton = new JButton("계산");
        calculateButton.setBounds(130, 130, 100, 25);
        frame.add(calculateButton);

        // 버튼에 액션 리스너 추가
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ipAddress = ipField.getText();
                String subnetMaskInput = subnetField.getText();

                try {
                    int subnetMaskLength = Integer.parseInt(subnetMaskInput);

                    // 비트연산 프로그래 추가
                    int subnetMask = 0xFFFFFFFF >>> (subnetMaskLength);  
                    int networkAddress = ipToInt(ipAddress) & subnetMask;

                    String subnetMaskStr = intToIp(subnetMask);
                    String networkAddressStr = intToIp(networkAddress);

                    resultLabel.setText("결과 값: 서브넷 마스크: " + subnetMaskStr + " | 네트워크: " + networkAddressStr);
                } catch (NumberFormatException ex) {
                    resultLabel.setText("결과 값: 서브넷 마스크는 숫자여야 합니다.");
                } catch (Exception ex) {
                    resultLabel.setText("결과 값: 오류 - 잘못된 입력");
                }
            }
        });

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
    // IP주소 구하는 코드는 인터넷 @ https://seb.kr/subnet/ 에서 참고해서 썼습니다.
    // 정수를 다시 IP 주소 형식으로 변환
    public static String intToIp(int ip) {
        return ((ip >> 24) & 0xFF) + "." +
               ((ip >> 16) & 0xFF) + "." +
               ((ip >> 8) & 0xFF) + "." +
               (ip & 0xFF);
    }
}
