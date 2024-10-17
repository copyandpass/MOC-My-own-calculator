import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// JFrame에 필요 한 모듈들을 임포트 해줍니다. ActionEvent,ActionListener는 챗지피티 참조
public class SimpleBinaryCalculator extends JFrame {
    private JTextField binaryField1;
    private JTextField binaryField2;
    private JTextField decimalField;
    private JButton binToDecButton;
    private JButton decToBinButton;
    private JButton andButton;
    private JButton orButton;
    private JButton xorButton;
//버튼들을 하나의 객체로 설정 해줍니다.
    public SimpleBinaryCalculator() {
        // 창 제목과 크기 설정 (버튼들이 붙지 않게 좀 넓게 세팅 해줍니다)
        setTitle("2진수-10진수 변환기");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // 첫 번째 2진수 입력 필드와 라벨
        JLabel binLabel1 = new JLabel("2진수 1:");
        binLabel1.setBounds(30, 30, 80, 25);
        add(binLabel1);

        binaryField1 = new JTextField();
        binaryField1.setBounds(110, 30, 150, 25);
        add(binaryField1);

        // 두 번째 2진수 입력 필드와 라벨 (비트 연산용)
        JLabel binLabel2 = new JLabel("2진수 2:");
        binLabel2.setBounds(30, 70, 80, 25);
        add(binLabel2);

        binaryField2 = new JTextField();
        binaryField2.setBounds(110, 70, 150, 25);
        add(binaryField2);

        // 10진수 입력 필드와 라벨
        JLabel decLabel = new JLabel("10진수:");
        decLabel.setBounds(30, 110, 80, 25);
        add(decLabel);

        decimalField = new JTextField();
        decimalField.setBounds(110, 110, 150, 25);
        add(decimalField);

        // 2진수 -> 10진수 변환 버튼
        binToDecButton = new JButton("2진수 -> 10진수");
        binToDecButton.setBounds(30, 150, 200, 30);
        add(binToDecButton);

        // 10진수 -> 2진수 변환 버튼
        decToBinButton = new JButton("10진수 -> 2진수");
        decToBinButton.setBounds(30, 190, 200, 30);
        add(decToBinButton);

        // AND 연산 버튼
        andButton = new JButton("AND");
        andButton.setBounds(30, 230, 70, 30);
        add(andButton);

        // OR 연산 버튼
        orButton = new JButton("OR");
        orButton.setBounds(110, 230, 70, 30);
        add(orButton);

        // XOR 연산 버튼
        xorButton = new JButton("XOR");
        xorButton.setBounds(190, 230, 70, 30);
        add(xorButton);

        // 2진수 -> 10진수 변환 기능
        binToDecButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String binary = binaryField1.getText();
                try {
                    int decimal = Integer.parseInt(binary, 2); // 2진수를 10진수로 변환
                    decimalField.setText(String.valueOf(decimal));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "올바른 2진수를 입력하세요.");
                }
            }
        });

        // 10진수 -> 2진수 변환 기능
        decToBinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String decimal = decimalField.getText();
                try {
                    int dec = Integer.parseInt(decimal); // 10진수를 받아서
                    String binary = Integer.toBinaryString(dec); // 2진수로 변환
                    binaryField1.setText(binary);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "올바른 10진수를 입력하세요.");
                }
            }
        });

        // AND 연산 기능 자바프로그래밍. p.63참조
        andButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performBitwiseOperation("AND");
            }
        });

        // OR 연산 기능  자바프로그래밍. p.64참조
        orButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performBitwiseOperation("OR");
            }
        });

        // XOR 연산 기능  자바프로그래밍. p.65참조
        xorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performBitwiseOperation("XOR");
            }
        });
    }

    // 비트 연산 수행 메서드 자바 자바프로그래밍. p.73참조, 챗 지피티를 활용한 결과값 도출
    private void performBitwiseOperation(String operation) {
        try {
            int binary1 = Integer.parseInt(binaryField1.getText(), 2);
            int binary2 = Integer.parseInt(binaryField2.getText(), 2);
            int result = 0;

            if (operation.equals("AND")) {
                result = binary1 & binary2; // AND 연산
            } else if (operation.equals("OR")) {
                result = binary1 | binary2; // OR 연산
            } else if (operation.equals("XOR")) {
                result = binary1 ^ binary2; // XOR 연산
            }

            JOptionPane.showMessageDialog(null, operation + " 결과: " + Integer.toBinaryString(result));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "올바른 2진수를 입력하세요.");
        }
    }

    public static void main(String[] args) {
        SimpleBinaryCalculator calculator = new SimpleBinaryCalculator();
        calculator.setVisible(true);
    }
}
