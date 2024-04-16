package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.SecureRandom;
import java.util.Scanner;

import model.Patient;
import model.VitalSigns;
import server.ServerInterface;

public class Client {

    static ServerInterface server;
    static Patient currentPatient = null;
    static boolean isMonitoring = false;
    static SecureRandom rd;

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        Client.rd = new SecureRandom();

        try {
            Registry refServer = LocateRegistry.getRegistry("192.168.137.1", 5001);
            server = (ServerInterface) refServer.lookup("Server");

            while (!isMonitoring) {
                System.out.println("---------------------------------------------------");
                System.out.println("\t\tINTERNAR PACIENTE");
                System.out.println("---------------------------------------------------");
                System.out.print("CPF: ");
                String cpf = scan.nextLine();
                System.out.print("Senha: ");
                String password = scan.nextLine();

                currentPatient = server.admitPatient(cpf, password);

                if (currentPatient != null) {
                    isMonitoring = true;
                    System.out.println("---------------------------------------------------");
                    System.out.println("Paciente internado: " + currentPatient.getName());
                    System.out.println("---------------------------------------------------");
                }
            }

            int frequency = 5000;

            switch (currentPatient.getStatus()) {
                case URGENTE:
                    frequency = 1000;
                    break;
                case GRAVE:
                    frequency = 2000;
                    break;
                case RECUPERACAO:
                    frequency = 5000;
                    break;
                case ESTAVEL:
                    frequency = 10000;
                    break;
                default:
                    break;
            }
            while (isMonitoring) {
                getVitalSigns();
                sendVitalSigns();
                Thread.sleep(frequency);
            }

            server.Teste();
        } catch (RemoteException | NotBoundException | InterruptedException e) {
            e.printStackTrace();
        }

        scan.close();

    }

    public static void getVitalSigns() {
        VitalSigns vitalSigns = currentPatient.getCurrentVitalSigns();
        System.out.println("---------------------------------------");
        generateHeartRate(vitalSigns);
        generateBreathRate(vitalSigns);
        generateBloodPressure(vitalSigns);
        generateBloodOxygenRate(vitalSigns);
        generateBodyTemperature(vitalSigns);
        generateGlucoseRate(vitalSigns);
    }

    public static void sendVitalSigns() throws RemoteException {
        server.receiveVitalSigns(currentPatient);
    }

    public static void generateHeartRate(VitalSigns vitalSigns){

        int nextRandInt = 0;
        switch (currentPatient.getStatus()) {
            case URGENTE:
                nextRandInt = rd.nextInt(-10, 10);
                break;
            case GRAVE:
                nextRandInt = rd.nextInt(-5, 5);
                break;
            case RECUPERACAO:
                nextRandInt = rd.nextInt(-2, 2);
                break;
            case ESTAVEL:
                nextRandInt = rd.nextInt(-1, 1);
                break;
            default:
                break;
        }

        int hearth = vitalSigns.getHeartRate() + nextRandInt;
        if(hearth < 40){
            hearth = 40;
        } else if (hearth > 120) {
            hearth = 120;
        }

        if(nextRandInt >= 0){
            System.out.println("Frequência cardíaca: " + hearth + ", Variação: +" + nextRandInt);
        } else {
            System.out.println("Frequência cardíaca: " + hearth + ", Variação: " + nextRandInt);
        }        

        vitalSigns.setHeartRate(hearth);
        currentPatient.setCurrentVitalSigns(vitalSigns);
    }

    public static void generateBreathRate(VitalSigns vitalSigns){
        int nextRandInt = 0;
        switch (currentPatient.getStatus()) {
            case URGENTE:
                nextRandInt = rd.nextInt(-6, 6); 
                break;
            case GRAVE:
                nextRandInt = rd.nextInt(-4, 4); 
                break;
            case RECUPERACAO:
                nextRandInt = rd.nextInt(-2, 2); 
                break;
            case ESTAVEL:
                nextRandInt = rd.nextInt(-1, 1); 
                break;
            default:
                break;
        }

        int breathe = vitalSigns.getBreathRate() + nextRandInt;
        if (breathe < 12) {
            breathe = 12; 
        } else if (breathe > 20) {
            breathe = 20; 
        }
 
        if(nextRandInt >= 0){
            System.out.println("Frequência respiratória: " + breathe + ", Variação: +" + nextRandInt);
        } else {
            System.out.println("Frequência respiratória: " + breathe + ", Variação: " + nextRandInt);
        }

        vitalSigns.setBreathRate(breathe);
        currentPatient.setCurrentVitalSigns(vitalSigns);
    }

    public static void generateBloodPressure(VitalSigns vitalSigns) {
        int nextRandInt = 0;

        switch (currentPatient.getStatus()) {
            case URGENTE:
                nextRandInt = rd.nextInt(-20, 20);
                break;
            case GRAVE:
                nextRandInt = rd.nextInt(-15, 15);
                break;
            case RECUPERACAO:
                nextRandInt = rd.nextInt(-10, 10);
                break;
            case ESTAVEL:
                nextRandInt = rd.nextInt(-5, 5);
                break;
            default:
                break;
        }

        int bloodPressure = vitalSigns.getBloodPressure() + nextRandInt;

        if (bloodPressure < 90) {
            bloodPressure = 90;
        } else if (bloodPressure > 120) {
            bloodPressure = 120;
        }

        if (nextRandInt >= 0) {
            System.out.println("Pressão arterial: " + bloodPressure + ", Variação: +" + nextRandInt);
        } else {
            System.out.println("Pressão arterial: " + bloodPressure + ", Variação: " + nextRandInt);
        }

        vitalSigns.setBloodPressure(bloodPressure);
        currentPatient.setCurrentVitalSigns(vitalSigns);
    }

    public static void generateBloodOxygenRate(VitalSigns vitalSigns) {
        float nextRandFloat = 0;

        switch (currentPatient.getStatus()) {
            case URGENTE:
                nextRandFloat = rd.nextFloat(-5, 5);
                break;
            case GRAVE:
                nextRandFloat = rd.nextFloat(-3, 3);
                break;
            case RECUPERACAO:
                nextRandFloat = rd.nextFloat(-1, 1);
                break;
            case ESTAVEL:
                nextRandFloat = rd.nextFloat(-0.5f, 0.5f);
                break;
            default:
                break;
        }

        float bloodOxygenRate = vitalSigns.getBloodOxygenRate() + nextRandFloat;

        if (bloodOxygenRate < 95.0f) {
            bloodOxygenRate = 95.0f;
        } else if (bloodOxygenRate > 100.0f) {
            bloodOxygenRate = 100.0f;
        }

        if (nextRandFloat >= 0) {
            System.out.println("Taxa de oxigênio no sangue: " + bloodOxygenRate + ", Variação: +" + nextRandFloat);
        } else {
            System.out.println("Taxa de oxigênio no sangue: " + bloodOxygenRate + ", Variação: " + nextRandFloat);
        }

        vitalSigns.setBloodOxygenRate(bloodOxygenRate);
        currentPatient.setCurrentVitalSigns(vitalSigns);
    }

    public static void generateBodyTemperature(VitalSigns vitalSigns) {
        float nextRandFloat = 0;

        switch (currentPatient.getStatus()) {
            case URGENTE:
                nextRandFloat = rd.nextFloat(-1.0f, 1.0f);
                break;
            case GRAVE:
                nextRandFloat = rd.nextFloat(-0.5f, 0.5f);
                break;
            case RECUPERACAO:
                nextRandFloat = rd.nextFloat(-0.3f, 0.3f);
                break;
            case ESTAVEL:
                nextRandFloat = rd.nextFloat(-0.2f, 0.2f);
                break;
            default:
                break;
        }

        float bodyTemperature = vitalSigns.getBodyTemperature() + nextRandFloat;

        if (bodyTemperature < 36.0f) {
            bodyTemperature = 36.0f;
        } else if (bodyTemperature > 39.0f) {
            bodyTemperature = 39.0f;
        }

        if (nextRandFloat >= 0) {
            System.out.println("Temperatura corporal: " + bodyTemperature + ", Variação: +" + nextRandFloat);
        } else {
            System.out.println("Temperatura corporal: " + bodyTemperature + ", Variação: " + nextRandFloat);
        }

        vitalSigns.setBodyTemperature(bodyTemperature);
        currentPatient.setCurrentVitalSigns(vitalSigns);
    }

    public static void generateGlucoseRate(VitalSigns vitalSigns) {
        int nextRandInt = 0;

        switch (currentPatient.getStatus()) {
            case URGENTE:
                nextRandInt = rd.nextInt(-20, 20);
                break;
            case GRAVE:
                nextRandInt = rd.nextInt(-10, 10);
                break;
            case RECUPERACAO:
                nextRandInt = rd.nextInt(-5, 5);
                break;
            case ESTAVEL:
                nextRandInt = rd.nextInt(-2, 2);
                break;
            default:
                break;
        }

        int glucoseRate = vitalSigns.getGlucoseRate() + nextRandInt;

        if (glucoseRate < 50) {
            glucoseRate = 50;
        } else if (glucoseRate > 120) {
            glucoseRate = 120;
        }

        if (nextRandInt >= 0) {
            System.out.println("Nível de glicose no sangue: " + glucoseRate + ", Variação: +" + nextRandInt);
        } else {
            System.out.println("Nível de glicose no sangue: " + glucoseRate + ", Variação: " + nextRandInt);
        }

        vitalSigns.setGlucoseRate(glucoseRate);
        currentPatient.setCurrentVitalSigns(vitalSigns);
    }

}
