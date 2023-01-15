/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.notification;

import java.util.HashSet;
import java.util.Set;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Informer {
    
    private final Object MONITOR = new Object();
    private Set<Observer> observers;

    public void registerObserver(Observer observer) {
        if (observer == null) {
            return;
        }
        synchronized (MONITOR) {
            if (observers == null) {
                observers = new HashSet<>(1);
            }
            observers.add(observer);
        }
    }

    public void unregisterObserver(Observer observer) {
        if (observer == null) {
            return;
        }
        synchronized (MONITOR) {
            observers.remove(observer);
        }
    }

    public void informObservers(String message) {
        Set<Observer> observersCopy;
        synchronized (MONITOR) {
            if (observers == null) {
                return;
            }
            observersCopy = new HashSet<>(observers);
        }
        for (Observer observer : observersCopy) {
            observer.onUpdateRecieve(message + "\n> thread: " + Thread.currentThread().getName());
        }
    }
    
}
