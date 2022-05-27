package com.example.smartsecurityminor;

class Sensors {
    private String gasLevels;
    private String humidity;
    private String motionStatus;
    private String temperature;
    private String username;

    private Sensors() {}

    public Sensors(String gasLevels, String humidity, String motionStatus, String temperature) {
        this.gasLevels = gasLevels;
        this.humidity = humidity;
        this.motionStatus = motionStatus;
        this.temperature = temperature;
        this.username = username;
    }

    public String getGasLevels() {
        return gasLevels;
    }

    public void setGasLevels(String gasLevels) {
        this.gasLevels = gasLevels;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getMotionStatus() {
        return motionStatus;
    }

    public void setMotionStatus(String motionStatus) {
        this.motionStatus = motionStatus;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}

