import java.util.Scanner;

public class SubnetCalculatorver2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 사용자에게 IP 주소와 서브넷 마스크를 입력받음
        // 여기서 getInput 메서드는 사용자 입력을 간결하게 처리하기 위한 유틸리티 메서드입니다.
        String ipAddress = getInput(scanner, "IP 주소를 입력하세요 (예: 192.168.0.1): ");
        String subnetMask = getInput(scanner, "서브넷 마스크를 입력하세요 (예: 255.255.255.0): ");

        // 입력받은 IP 주소와 서브넷 마스크를 사용해 네트워크 주소와 브로드캐스트 주소, 호스트 수를 계산
        String networkAddress = calculateNetworkAddress(ipAddress, subnetMask); // 네트워크 주소 계산
        String broadcastAddress = calculateBroadcastAddress(ipAddress, subnetMask); // 브로드캐스트 주소 계산
        int numberOfHosts = calculateNumberOfHosts(subnetMask); // 서브넷 내 호스트 수 계산

        // 계산된 결과를 출력 (displayResult 메서드는 출력 형식을 통일하기 위해 사용됨)
        displayResult("네트워크 주소", networkAddress);
        displayResult("브로드캐스트 주소", broadcastAddress);
        displayResult("호스트 수", String.valueOf(numberOfHosts));
    }

    // 사용자 입력을 받아오는 메서드
    // 동일한 입력 처리를 여러 번 사용할 수 있기 때문에 반복적인 코드를 줄이기 위해 따로 분리
    private static String getInput(Scanner scanner, String prompt) {
        System.out.print(prompt); // 메시지 출력
        return scanner.nextLine(); // 사용자가 입력한 값을 반환
    }

    // 결과를 출력하는 메서드
    // 이 메서드는 다양한 결과(네트워크 주소, 브로드캐스트 주소 등)를 일관성 있게 출력하기 위해 만들어짐
    private static void displayResult(String label, String result) {
        System.out.println(label + ": " + result); // "라벨: 결과" 형태로 출력
    }

    // 네트워크 주소 계산 메서드
    // IP 주소와 서브넷 마스크를 입력받아 네트워크 주소를 반환
    // 네트워크 주소는 IP 주소와 서브넷 마스크를 비트 단위로 AND 연산하여 구함
    private static String calculateNetworkAddress(String ipAddress, String subnetMask) {
        return performBitwiseOperation(ipAddress, subnetMask, "AND"); // AND 연산을 수행하여 네트워크 주소 계산
    }

    // 브로드캐스트 주소 계산 메서드
    // IP 주소와 서브넷 마스크를 입력받아 브로드캐스트 주소를 반환
    // 브로드캐스트 주소는 IP 주소와 서브넷 마스크를 사용하여 남은 비트에 1을 채운 값으로 계산
    private static String calculateBroadcastAddress(String ipAddress, String subnetMask) {
        return performBitwiseOperation(ipAddress, subnetMask, "BROADCAST"); // 브로드캐스트 주소를 계산
    }

    // 비트 연산을 수행하는 공통 메서드
    // 이 메서드는 AND 또는 BROADCAST 연산을 선택적으로 수행하여 네트워크 주소나 브로드캐스트 주소를 계산할 수 있음
    private static String performBitwiseOperation(String ipAddress, String subnetMask, String operation) {
        // IP 주소와 서브넷 마스크를 각각 "."으로 분리하여 4개의 부분으로 나눔
        String[] ipParts = ipAddress.split("\\.");
        String[] maskParts = subnetMask.split("\\.");

        int[] resultParts = new int[4]; // 결과를 저장할 배열
        // 각 부분에 대해 연산을 수행
        for (int i = 0; i < 4; i++) {
            int ipPart = Integer.parseInt(ipParts[i]); // IP 주소의 각 부분을 정수로 변환
            int maskPart = Integer.parseInt(maskParts[i]); // 서브넷 마스크의 각 부분을 정수로 변환
            
            if (operation.equals("AND")) {
                // 네트워크 주소를 계산할 때는 IP와 마스크를 AND 연산
                resultParts[i] = ipPart & maskPart;
            } else if (operation.equals("BROADCAST")) {
                // 브로드캐스트 주소를 계산할 때는 IP와 마스크를 AND 연산한 후 남는 비트를 1로 채움
                resultParts[i] = (ipPart & maskPart) | (~maskPart & 0xFF);
            }
        }

        // 계산된 각 부분을 다시 IP 주소 형태로 변환
        return formatAddress(resultParts);
    }

    // 서브넷 내의 호스트 수를 계산하는 메서드
    // 서브넷 마스크에서 0의 개수를 카운트하여 계산 (호스트 수는 2^0의 개수 - 2)
    // 왜 -2를 하는가: 첫 번째는 네트워크 주소, 마지막은 브로드캐스트 주소이기 때문에 호스트로 사용할 수 없음
    private static int calculateNumberOfHosts(String subnetMask) {
        int numberOfZeros = 0; // 서브넷 마스크에서 0의 개수를 셈

        // 서브넷 마스크를 "."으로 분리
        String[] maskParts = subnetMask.split("\\.");
        for (String part : maskParts) {
            int mask = Integer.parseInt(part); // 각 부분을 정수로 변환
            // 각 서브넷 마스크의 비트에 대해 확인
            while (mask > 0) {
                if ((mask & 1) == 0) {
                    // 마지막 비트가 0이면 0의 개수 증가
                    numberOfZeros++;
                }
                mask >>= 1; // 비트를 오른쪽으로 이동하여 다음 비트를 확인
            }
        }

        // 호스트 수는 2^0의 개수 - 2로 계산
        return (int) Math.pow(2, numberOfZeros) - 2;
    }

    // IP 주소 배열을 다시 문자열로 변환하는 메서드
    // IP 주소는 4개의 정수 배열로 처리되므로 이를 "."으로 구분된 문자열로 변환해주는 역할
    private static String formatAddress(int[] addressParts) {
        // 각 정수 부분을 "."으로 연결하여 IP 주소 형태로 반환
        return String.join(".",
                String.valueOf(addressParts[0]),
                String.valueOf(addressParts[1]),
                String.valueOf(addressParts[2]),
                String.valueOf(addressParts[3]));
    }
}
