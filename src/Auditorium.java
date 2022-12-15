/**
 * Студенты (потоки) сдают экзамен. В аудитории N парт
 * и один стол экзаменатора (разделяемые ресурсы).
 * Студенты подходят к экзаменатору в произвольном порядке и садятся сдавать экзамен.
 * <p>
 * Далее экзаменатор либо ставит студенту оценку,
 * либо выгоняет его, либо отправляет еще раз подумать за парту
 * (это состояние является случайной величиной с равномерным законом).
 * Когда аудитория освобождается, заходит новая группа студентов.
 * <p>
 * Значение N задается пользователем при старте приложения.
 */


import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Auditorium {

  public static BlockingQueue<Object> tutor = new ArrayBlockingQueue<>(1, true);
  private ArrayList<Student> students;

  public static AtomicInteger pass_count = new AtomicInteger(0);
  private int deskAmount;
  private int curGroup = 10;


  public Auditorium(int deskAmount){
    this.deskAmount = deskAmount;
    students = IntStream.range(1, deskAmount + 1).mapToObj((x) -> new Student(x, curGroup))
        .collect(Collectors.toCollection(ArrayList<Student>::new));
  }

  public void run(){
    students.forEach(Student::start);
    curGroup += 1;
    while (true){
      if (pass_count.get() == deskAmount) {
        System.out.println("Заходит новая группа студентов");
        students = IntStream.range(1, deskAmount + 1).mapToObj((x) -> new Student(x, curGroup))
            .collect(Collectors.toCollection(ArrayList<Student>::new));
        students.forEach(Student::start);
        pass_count.set(0);
        curGroup += 1;
      }
    }
  }

}
