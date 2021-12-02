package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    private double[] temperatureSeries;
    private int size = 0;
    private int capacity = 0;
    private static final int zero = -273;
    private static final double minTemp = -100000000;
    private static final double maxTemp = 100000000;

    public TemperatureSeriesAnalysis() {
        temperatureSeries = new double[]{};
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        checkSeriesIsValid(temperatureSeries);
        this.temperatureSeries = Arrays.copyOf(temperatureSeries,
                                            temperatureSeries.length);
        size = temperatureSeries.length;
        capacity = temperatureSeries.length;
    }
    public void checkSeriesIsValid(double[] temperatureSeq) {
        for (double temp: temperatureSeq) {
            if (temp < zero) {
                throw new InputMismatchException();
            }
        }
    }


    public double average() {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }

        double sum = 0;
        for (double temp : temperatureSeries) {
            sum += temp;
        }
        return sum/temperatureSeries.length;
    }

    public double deviation() {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }

        double mean = average();
        double meanSq = 0;
        for (int i = 0; i < size; i++) {
            meanSq += temperatureSeries[i] * temperatureSeries[i];
        }
        meanSq /= temperatureSeries.length;
        return Math.sqrt(meanSq - mean*mean);
    }

    public double min() {
        return findTempClosestToValue(minTemp);
    }

    public double max() {
        return findTempClosestToValue(maxTemp);
    }

    public double findTempClosestToZero() {

        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
        double minDif = Double.POSITIVE_INFINITY;
        double searchedTemp = temperatureSeries[0];
        for (int i = 0; i < size; i++) {
            double temp = temperatureSeries[i];
            if (minDif > Math.abs(temp-tempValue)) {
                minDif = Math.abs(temp-tempValue);
                searchedTemp = temp;
            } else if (Math.abs(minDif - Math.abs(temp-tempValue)) < 0.0001 && temp > 0) {
                searchedTemp = temp;
            }
        }

        return searchedTemp;
    }

    public double[] findTempsLessThen(double tempValue) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (temperatureSeries[i] < tempValue) { count++; }
        }
        double[] lessTemps = new double[count];

        count = 0;
        for (int i = 0; i < size; i++) {
            double temp = temperatureSeries[i];
            if (temp < tempValue) {
                lessTemps[count] = temp;
                count++;
            }
        }
        return lessTemps;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (temperatureSeries[i] > tempValue) { count++; }
        }
        double[] greaterTemps = new double[count];

        count = 0;
        for (int i = 0; i < size; i++) {
            double temp = temperatureSeries[i];
            if (temp > tempValue) {
                greaterTemps[count] = temp;
                count++;
            }
        }
        return greaterTemps;
    }

    public TempSummaryStatistics summaryStatistics() {
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public double addTemps(double... temps) {
        checkSeriesIsValid(temps);
        double sum = 0;
        while (temps.length + size > capacity) {
            capacity *= 2;
        }

        double[] newTemps = new double[capacity];
        for (int i = 0; i < size; i++) {
            newTemps[i] = temperatureSeries[i];
            sum += temperatureSeries[i];
        }
        temperatureSeries = newTemps;
        for (double temp: temps) {
            temperatureSeries[size] = temp;
            size++;
            sum += temp;
        }
        return sum;
    }
}
