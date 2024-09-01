public class CustomNumberDecomposition {
    private int number;
    private int d;
    public CustomNumberDecomposition (int number, int d){
        this.number = number;
        this.d = d;
    }

    public int NumOfPos (int number,  int d){
        int r = number;
        int f = d;
        int k = 0;
        while (r > 0){
            k++;
            r = r / f;
        }
        return k;
    }
    public int getDigOnPos (int pos){
        if (pos<0) {
            System.out.println("Error, negative position");
        }
        if (d < 0 || d > 36) {
            System.out.println("Non-compliance with the assignment condition");
        }
        int dig = (Math.abs(number) / (int)Math.pow(d,pos)) % d;
        return dig;
    }

    public String getSign() {
        if (number < 0) {
            return "-";
        } else {
            return "+";
        }
    }

    public String toString() {
        return "Number:" + " " + number + " " + "in base" + " " + d;
    }
}
