import java.util.Scanner;

public class SubnetCalculatorver2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 사용자 입력
        String ipAddress = getInput(scanner, "IP 주소를 입력하세요 (예: 192.168.0.1): ");
        String subnetMask = getInput(scanner, "서브넷 마스크를 입력하세요 (예: 255.255.255.0): ");

        // 서브넷 정보 계산
        String networkAddress = calculateNetworkAddress(ipAddress, subnetMask);
        String broadcastAddress = calculateBroadcastAddress(ipAddress, subnetMask);
        int numberOfHosts = calculateNumberOfHosts(subnetMask);

        // 결과 출력
        displayResult("네트워크 주소", networkAddress);
        displayResult("브로드캐스트 주소", broadcastAddress);
        displayResult("호스트 수", String.valueOf(numberOfHosts));
    }

    // 사용자 입력을 받는 공통 메서드
    private static String getInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    // 결과를 출력하는 공통 메서드
    private static void displayResult(String label, String result) {
        System.out.println(label + ": " + result);
    }

    // 네트워크 주소 계산
    private static String calculateNetworkAddress(String ipAddress, String subnetMask) {
        return performBitwiseOperation(ipAddress, subnetMask, "AND");
    }

    // 브로드캐스트 주소 계산
    private static String calculateBroadcastAddress(String ipAddress, String subnetMask) {
        return performBitwiseOperation(ipAddress, subnetMask, "BROADCAST");
    }

    // 비트 연산을 처리하는 공통 메서드 (네트워크 주소와 브로드캐스트 주소 계산용)
    private static String performBitwiseOperation(String ipAddress, String subnetMask, String operation) {
        String[] ipParts = ipAddress.split("\\.");
        String[] maskParts = subnetMask.split("\\.");

        int[] resultParts = new int[4];
        for (int i = 0; i < 4; i++) {
            int ipPart = Integer.parseInt(ipParts[i]);
            int maskPart = Integer.parseInt(maskParts[i]);
            
            if (operation.equals("AND")) {
                resultParts[i] = ipPart & maskPart;
            } else if (operation.equals("BROADCAST")) {
                resultParts[i] = (ipPart & maskPart) | (~maskPart & 0xFF);
            }
        }

        return formatAddress(resultParts);
    }

    // 호스트 수 계산
    private static int calculateNumberOfHosts(String subnetMask) {
        int numberOfZeros = 0;

        String[] maskParts = subnetMask.split("\\.");
        for (String part : maskParts) {
            int mask = Integer.parseInt(part);
            while (mask > 0) {
                if ((mask & 1) == 0) {
                    numberOfZeros++;
                }
                mask >>= 1;
            }
        }

        return (int) Math.pow(2, numberOfZeros) - 2;
    }

    // IP 주소 배열을 문자열로 변환
    private static String formatAddress(int[] addressParts) {
        return String.join(".",
                String.valueOf(addressParts[0]),
                String.valueOf(addressParts[1]),
                String.valueOf(addressParts[2]),
                String.valueOf(addressParts[3]));
    }
}
