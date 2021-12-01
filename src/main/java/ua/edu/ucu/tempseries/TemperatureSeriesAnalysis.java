package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class TemperatureSeriesAnalysis {
    double[] temperatureSeries;
    int size = 0;
    int capacity = 0;
    public TemperatureSeriesAnalysis() {
        temperatureSeries = new double[]{};
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        checkSeriesIsValid(temperatureSeries);
        this.temperatureSeries = Arrays.copyOf(temperatureSeries,temperatureSeries.length);
        size = temperatureSeries.length;
        capacity = temperatureSeries.length;
    }

    public void checkSeriesIsValid(double[] temperatureSeries) {
        for(double temp: temperatureSeries){
            if (temp<-273){
                throw new InputMismatchException();
            }
        }
    }

    public double average() {
        if(temperatureSeries.length == 0) throw new IllegalArgumentException();

        double sum = 0;
        for(double temp : temperatureSeries){
            sum += temp;
        }
        return sum/temperatureSeries.length;
    }

    public double deviation() {
        if(temperatureSeries.length == 0) throw new IllegalArgumentException();

        double mean = average();
        double mean_sq = 0;
        for(int i = 0; i<size; i++){
            mean_sq += temperatureSeries[i];
        }
        mean_sq /= temperatureSeries.length;
        return sqrt(mean_sq - mean*mean);
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
        if (temperatureSeries.length == 0) throw new IllegalArgumentException();
        double minDif = Double.POSITIVE_INFINITY;
        double searchedTemp = temperatureSeries[0];
        for(int i = 0; i < size ; i++){
            double temp = temperatureSeries[i];
            if(minDif > abs(temp-tempValue)){
                minDif = abs(temp-tempValue);
                searchedTemp = temp;
            }  if(minDif == abs(temp-tempValue) && temp > 0){
                searchedTemp = temp;
            }
        }

        return searchedTemp;
    }

    public double[] findTempsLessThen(double tempValue) {
        int count = 0;
        for(int i = 0; i<size; i++){
            if(temperatureSeries[i] < tempValue) count++;
        }
        double[] lessTemps = new double[count];

        count = 0;
        for(int i = 0; i<size; i++){
            double temp = temperatureSeries[i];
            if(temp < tempValue){
                lessTemps[count] = temp;
                count++;
            }
        }
        return lessTemps;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        int count = 0;
        for(int i = 0; i<size; i++){
            if(temperatureSeries[i] < tempValue) count++;
        }
        double[] lessTemps = new double[count];

        count = 0;
        for(int i = 0; i<size; i++){
            double temp = temperatureSeries[i];
            if(temp < tempValue){
                lessTemps[count] = temp;
                count++;
            }
        }
        return lessTemps;
    }

    public TempSummaryStatistics summaryStatistics() {
        return new TempSummaryStatistics(average(),deviation(),min(),max());
    }

    public int addTemps(double... temps) {
        checkSeriesIsValid(temps);
        if (temps.length + size >capacity) {
            while (temps.length + size > capacity){
                capacity *= 2;
            }
            temperatureSeries = Arrays.copyOf(temperatureSeries,size);
        }

        for(double temp: temps){
            temperatureSeries[size] = temp;
            size++;
        }
        return 0;
    }
}
