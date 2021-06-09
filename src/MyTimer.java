import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyTimer implements TimerSubject {

    private int timeLeftInSeconds = Utils.TURN_TIME_IN_SECONDS;

    private Timer timer;
    private TimerTask timerTask;

    private List<TimerObserver> observers = new ArrayList<>();

    private static MyTimer instance = null;

    public static MyTimer getInstance() {
        if (instance == null) {
            instance = new MyTimer();
        }
        return instance;
    }

    private MyTimer() {
    }

    public void startTimer() {
        this.timeLeftInSeconds = Utils.TURN_TIME_IN_SECONDS;
        this.timer = new Timer();
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                timeLeftInSeconds -= 1;
                if (timeLeftInSeconds == 0) {
                    notifyObservers();
                }
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    public void stopTimer() {
        this.timer.cancel();
        this.timer.purge();
    }

    public void changeTimerTime(int seconds) {
        this.timeLeftInSeconds += seconds;
    }

    public int getTimeLeftInSeconds() {
        return timeLeftInSeconds;
    }


    @Override
    public void registerObserver(TimerObserver o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(TimerObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (TimerObserver to : observers) {
            to.update();
        }
    }
}
