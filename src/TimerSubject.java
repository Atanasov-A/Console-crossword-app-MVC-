public interface TimerSubject {

    void registerObserver(TimerObserver o);
    void removeObserver(TimerObserver o);
    void notifyObservers();
}
