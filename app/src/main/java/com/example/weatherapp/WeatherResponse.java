package com.example.weatherapp;

public class WeatherResponse {
    private Main mainDetails;
    private Wind windInfo;
    private String cityName;
    private Weather[] weatherConditions;

    public Main getMain() {
        return mainDetails;
    }

    public Wind getWind() {
        return windInfo;
    }

    public String getName() {
        return cityName;
    }

    public Weather[] getWeather() {
        return weatherConditions;
    }

    public class Main {
        private double temperature;
        private double feelsLike;

        public double getTemp() {
            return temperature;
        }

        public double getFeelsLike() {
            return feelsLike;
        }
    }

    public class Wind {
        private double speedValue;

        public double getSpeed() {
            return speedValue;
        }
    }

    public class Weather {
        private String weatherDescription;

        public String getDescription() {
            return weatherDescription;
        }
    }
}
