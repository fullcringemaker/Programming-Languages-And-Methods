-------------------------------------------------- Resume.java --------------------------------------------------
import java.util.HashSet;
import java.util.Set;

public class Resume implements Comparable<Resume> {
    private Set<String> programmingLanguages;
    private Vacancy vacancy;

    public Resume(Set<String> programmingLanguages, Vacancy vacancy) {
        this.programmingLanguages = programmingLanguages;
        this.vacancy = vacancy;
    }

    @Override
    public int compareTo(Resume other) {
        if (this.vacancy == other.vacancy) {
            return Integer.compare(intersectionSize(other.programmingLanguages, other.vacancy.getLanguages()),
                    intersectionSize(this.programmingLanguages, this.vacancy.getLanguages()));
        }

        return Integer.compare(other.vacancy.getLanguages().size(), this.vacancy.getLanguages().size());
    }


    private static int intersectionSize(Set<String> a, Set<String> b) {
        Set<String> intersection = new HashSet<>(a);
        intersection.retainAll(b);
        return intersection.size();
    }

    @Override
    public String toString() {
        return "Resume: " + "programmingLanguages=" + programmingLanguages + ", vacancy=" + vacancy;
    }
}


-------------------------------------------------- Vacancy.java -------------------------------------------------- 
import java.util.*;

class Vacancy {
    private Set<String> languages;

    public Vacancy(Set<String> languages) {
        this.languages = languages;
    }

    public Set<String> getLanguages() {
        return languages;
    }

    @Override
    public String toString() {
        return "Vacancy{" + "languages=" + languages + '}';
    }
}


-------------------------------------------------- Test.java --------------------------------------------------
import java.util.Arrays;
import java.util.HashSet;

public class Test {

    public static void main(String[] args) {
        Vacancy vacancy1 = new Vacancy(new HashSet<>(Arrays.asList("Java", "C++", "Python")));
        Vacancy vacancy2 = new Vacancy(new HashSet<>(Arrays.asList("JavaScript", "HTML", "CSS")));

        Resume[] resumes = {
                new Resume(new HashSet<>(Arrays.asList("Java", "C++")), vacancy1),
                new Resume(new HashSet<>(Arrays.asList("Java", "Python")), vacancy1),
                new Resume(new HashSet<>(Arrays.asList("Java", "C")), vacancy1),
                new Resume(new HashSet<>(Arrays.asList("JavaScript", "HTML")), vacancy2),
                new Resume(new HashSet<>(Arrays.asList("JavaScript", "HTML", "CSS")), vacancy2),
        };

        Arrays.sort(resumes);

        for (Resume resume : resumes) {
            System.out.println(resume);
        }
    }
}
