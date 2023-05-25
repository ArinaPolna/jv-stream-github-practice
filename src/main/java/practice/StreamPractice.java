package practice;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import model.Candidate;
import model.Cat;
import model.Person;

public class StreamPractice {

    public int findMinEvenNumber(List<String> numbers) {
        return numbers.stream()
          .flatMap(s -> Arrays.stream(s.split(",")))
          .mapToInt(Integer::parseInt)
          .filter(i -> i % 2 == 0)
          .boxed()
          .min(Integer::compare)
          .orElseThrow(() -> new RuntimeException("Can't get min value from list: " + numbers));
    }

    public Double getOddNumsAverage(List<Integer> numbers) {
        return IntStream.range(0, numbers.size())
          .map(i -> i % 2 != 0 ? numbers.get(i) - 1 : numbers.get(i))
          .filter(i -> i % 2 != 0)
          .average()
          .orElseThrow(NoSuchElementException::new);
    }

    public List<Person> selectMenByAge(List<Person> peopleList, int fromAge, int toAge) {
        return peopleList.stream()
          .filter(p -> p.getAge() >= fromAge && p.getAge() <= toAge
                && p.getSex().name().equals("MAN"))
          .collect(Collectors.toList());
    }

    public List<Person> getWorkablePeople(int fromAge, int femaleToAge,
                                          int maleToAge, List<Person> peopleList) {
        return peopleList.stream()
          .filter(p -> p.getAge() >= fromAge
                && ((p.getSex().name().equals("WOMAN") && p.getAge() <= femaleToAge)
                        || (p.getSex().name().equals("MAN") && p.getAge() <= maleToAge)))
          .collect(Collectors.toList());
    }

    public List<String> getCatsNames(List<Person> peopleList, int femaleAge) {
        return peopleList.stream()
          .filter(p -> p.getAge() >= femaleAge && p.getSex().name().equals("WOMAN"))
          .map(Person::getCats)
          .flatMap(Collection::stream)
          .map(Cat::getName)
          .collect(Collectors.toList());
    }

    public List<String> validateCandidates(List<Candidate> candidates) {
        return candidates.stream()
          .filter(new CandidateValidator())
          .map(Candidate::getName)
          .sorted()
          .collect(Collectors.toList());
    }
}
