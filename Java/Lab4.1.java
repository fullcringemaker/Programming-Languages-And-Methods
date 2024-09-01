-------------------------------------------------- EsperantoNounGenerator.java --------------------------------------------------
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class EsperantoNounGenerator implements Iterable<String> {
    private List<String> roots;
    private List<String> suffixes;
    public EsperantoNounGenerator(String[] roots, String[] suffixes) {
        this.roots = new ArrayList<>(List.of(roots));
        this.suffixes = new ArrayList<>(List.of(suffixes));
    }
    public void addRoot(String root) {
        this.roots.add(root);
    }
    public void addSuffix(String suffix) {
        this.suffixes.add(suffix);
    }
    public void removeRoot(String root) {
        this.roots.remove(root);
    }
    public void removeSuffix(String suffix) {
        this.suffixes.remove(suffix);
    }
    @Override
    public Iterator<String> iterator() {
        return new EsperantoNounIterator();
    }
    private class EsperantoNounIterator implements Iterator<String> {
        private int curRootIndex = 0;
        private int curSuffixIndex = 0;
        @Override
        public boolean hasNext() {
            return curRootIndex < roots.size() && curSuffixIndex < suffixes.size();
        }
        @Override
        public String next() {
            String noun = roots.get(curRootIndex) + suffixes.get(curSuffixIndex);
            curSuffixIndex++;
            if (curSuffixIndex >= suffixes.size()) {
                curSuffixIndex = 0;
                curRootIndex++;
            }
            return noun;
        }
    }
}


-------------------------------------------------- Test.java --------------------------------------------------
public class Test {
    public static void main(String[] args) {
        String[] roots = {"hund", "knab", "tag", "libr", "flor", "bird"};
        String[] suffixes = {"ej", "ar", "ist"};
        EsperantoNounGenerator generator = new EsperantoNounGenerator(roots, suffixes);
        generator.addRoot("mar");
        generator.addSuffix("ul");
        for (String noun : generator) {
            System.out.println(noun + "o");
        }
    }
}
