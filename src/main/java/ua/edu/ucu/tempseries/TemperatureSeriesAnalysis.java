package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    private double[] temperatureSeries;
    private int size = 0;
    private int capacity = 0;
    private final int ZERO = -273;

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
            if (temp < ZERO) {
                throw new InputMismatchException();
            }
        }
    }


    public double average() {
        if (temperatureSeries.length == 0) { throw new IllegalArgumentException(); }

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
            meanSq += temperatureSeries[i];
        }
        meanSq /= temperatureSeries.length;
        return Math.sqrt(meanSq - mean*mean);
    }

    public double min() {
        return findTempClosestToValue(Double.NEGATIVE_INFINITY);
    }

    public double max() {
        return findTempClosestToValue(Double.POSITIVE_INFINITY);
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
        for (int i = 0; i < size ; i++) {
            double temp = temperatureSeries[i];
            if (minDif > Math.abs(temp-tempValue)) {
                minDif = Math.abs(temp-tempValue);
                searchedTemp = temp;
            }  if (minDif == Math.abs(temp-tempValue) && temp > 0) {
                searchedTemp = temp;
            }
        }

        return searchedTemp;
    }

    public double[] findTempsLessThen(double tempValue) {
        int count = 0;
        for (int i = 0; i<size; i++) {
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

    public TempSummaryStatistics summaryStatistics() {
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public int addTemps(double... temps) {
        checkSeriesIsValid(temps);
        if (temps.length + size > capacity) {
            while (temps.length + size > capacity) {
                capacity *= 2;
            }
            temperatureSeries = Arrays.copyOf(temperatureSeries, size);
        }

        for (double temp: temps) {
            temperatureSeries[size] = temp;
            size++;
        }
        return 0;
    }
}
