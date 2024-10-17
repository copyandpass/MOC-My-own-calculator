import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// 내가 쓸 함수에 대한 import 해주기.
public class SimpleBinaryCalculator extends JFrame {
    private JTextField binaryField1;
    private JTextField binaryField2;
    private JTextField decimalField;
    private JButton binToDecButton;
    private JButton decToBinButton;
    private JButton andButton;
    private JButton orButton;
    private JButton xorButton;
// 버튼과 출력값을 만들기위해 객체 생성

    public SimpleBinaryCalculator() {
        // 창 제목과 크기 설정
        // 창이 너무 작아서 400,250에서 500,300으로 변경 
        setTitle("2진수-10진수 변환기");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 패널 생성 및 레이아웃 설정
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10)); // 입력 필드 정렬용
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10)); // 비트 연산 버튼 정렬용
        JPanel conversionPanel = new JPanel(new GridLayout(1, 2, 10, 10)); // 변환 버튼 정렬용 

        // 첫 번째 2진수 입력 필드와 라벨
        JLabel binLabel1 = new JLabel("2진수 1:");
        binaryField1 = new JTextField();
        inputPanel.add(binLabel1);
        inputPanel.add(binaryField1);

        // 두 번째 2진수 입력 필드와 라벨 (비트 연산용)
        JLabel binLabel2 = new JLabel("2진수 2:");
        binaryField2 = new JTextField();
        inputPanel.add(binLabel2);
        inputPanel.add(binaryField2);

        // 10진수 입력 필드와 라벨
        JLabel decLabel = new JLabel("10진수:");
        decimalField = new JTextField();
        inputPanel.add(decLabel);
        inputPanel.add(decimalField);

        // 2진수 -> 10진수 변환 버튼
        binToDecButton = new JButton("2진수 -> 10진수");
        conversionPanel.add(binToDecButton);

        // 10진수 -> 2진수 변환 버튼
        decToBinButton = new JButton("10진수 -> 2진수");
        conversionPanel.add(decToBinButton);

        // AND 연산 버튼
        andButton = new JButton("AND");
        buttonPanel.add(andButton);

        // OR 연산 버튼
        orButton = new JButton("OR");
        buttonPanel.add(orButton);

        // XOR 연산 버튼
        xorButton = new JButton("XOR");
        buttonPanel.add(xorButton);

        // 패널들을 프레임에 추가
        add(inputPanel, BorderLayout.CENTER); // 입력 필드 패널을 가운데에 배치
        add(conversionPanel, BorderLayout.NORTH); // 변환 버튼 패널을 위쪽에 배치
        add(buttonPanel, BorderLayout.SOUTH); // 비트 연산 버튼 패널을 아래쪽에 배치

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
//10진수에서 2진수 변환 하는것은 교재p.65와 쳇GPT를 참고함.
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
//연산 기능들은 2학기 복학 이슈로 챗 GPT와 교재p.82를 참고함.
        // AND 연산 기능
        andButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performBitwiseOperation("AND");
            }
        });

        // OR 연산 기능
        orButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performBitwiseOperation("OR");
            }
        });

        // XOR 연산 기능
        xorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performBitwiseOperation("XOR");
            }
        });
    }

    // 비트 연산 수행 메서드 
    //비트연산은 @see  https://dpcks5959.tistory.com/98 에서 참고하고 개념을 이해함.
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
// GUI에서 배운대로 setVisible(true)를 메인함수에 실행시킴으로써 계산기를 완성시킴.
    public static void main(String[] args) {
        SimpleBinaryCalculator calculator = new SimpleBinaryCalculator();
        calculator.setVisible(true);
    }
}
