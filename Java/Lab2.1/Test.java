public class Test {
    public static void main(String[] args) {
        CustomNumberDecomposition customNumber = new CustomNumberDecomposition(3740, 13);
        System.out.println(customNumber.toString());
        for (int i = customNumber.NumOfPos(3740, 13); i > 0; i--){
            System.out.println("Digit at position" + " " + (customNumber.NumOfPos(3740, 13) - i) + ":" + customNumber.getDigOnPos(i-1));
        }
        System.out.println("Sign of the number:" + customNumber.getSign());
    }
}
