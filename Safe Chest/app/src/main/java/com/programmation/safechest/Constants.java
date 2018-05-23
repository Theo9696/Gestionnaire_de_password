package com.programmation.safechest;

final class Constants {
    // **** Realm Cloud Users:
    // **** Replace INSTANCE_ADDRESS with the hostname of your cloud instance
    // **** e.g., "mycoolapp.us1.cloud.realm.io"
    // ****
    // ****
    // **** ROS On-Premises Users
    // **** address of your ROS server, e.g.: INSTANCE_ADDRESS = "192.168.1.65:9080" and "http://" + INSTANCE_ADDRESS + "/auth"
    // **** (remember to use 'http' instead of 'https' if you didn't setup SSL on ROS yet)

    private static final String INSTANCE_ADDRESS = "safe-chest.us1.cloud.realm.io";
    static final String AUTH_URL = "https://" + INSTANCE_ADDRESS + "/auth";
}