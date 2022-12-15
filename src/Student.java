public class Student extends Thread{

  private int num;
  private int group;

  public Student(int num, int group){
    this.num = num;
    this.group = group;
  }


  private int randint(int min, int max) {
    max -= min;
    return (int) (Math.random() * ++max) + min;
  }


  @Override
  public void run() {
    System.out.printf("Заходит и садится за парту студент %d группы КИ22-%d/1б \n", num, group);
    int answerTime = randint(1000, 5000);
    int thinkingTime = randint(1000, 3000);
    int thinkCount = 0;
    while (true) {
      try {
        Auditorium.tutor.put(new Object());
        System.out.printf("Студент %d группы КИ22-%d/1б садится сдавать экзамен\n", num, group);
        Thread.sleep(answerTime);
      if (randint(0, 1) == 1) {
        System.out.printf("Студент %d группы КИ22-%d/1б сдал экзамен c %d-го раза!\n", num, group, ++thinkCount);
        Auditorium.tutor.take();
        break;
      } else {
        if (thinkCount >= 3) {
          System.out.printf("Студент %d группы КИ22-%d/1б был отправлен на пересдачу :(\n", num, group);
          break;
        }
        System.out.printf("Студент %d группы КИ22-%d/1б отправляется на подумать в %d-й раз\n",
            num, group, ++thinkCount);
        Auditorium.tutor.take();
        Thread.sleep(thinkingTime);
      }
      } catch (InterruptedException ignored) {}

    }
    Auditorium.pass_count.incrementAndGet();
  }
}
