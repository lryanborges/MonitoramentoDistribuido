package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map.Entry;

import model.Patient;
import model.VitalSigns;

public class Server implements ServerInterface {

    static HashMap<String, Patient> patients;
    static boolean isOn = true;
    static boolean emergencyAlarm = false;

    public Server(){
        patients = new HashMap<String, Patient>();
    }

    public static void main(String[] args) {

        Server server = new Server();

        try {
            ServerInterface refServer = (ServerInterface) UnicastRemoteObject.exportObject(server, 0);

            LocateRegistry.createRegistry(5001);
            Registry reg = LocateRegistry.getRegistry("localhost", 5001);
            reg.bind("Server", refServer);

            Thread registerThread = new RegisterThread();
            registerThread.run();

        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void Teste() throws RemoteException {
        System.out.println("Teste deu certo! <3");
    }

    @Override
    public void receiveVitalSigns(Patient patient) throws RemoteException {
        patients.put(patient.getCpf(), patient);
        System.out.println("\tDADOS RECEBIDOS DO PACIENTE " + patient.getName());
        System.out.println("---------------------------------------------------");
        System.out.println("Frequência cardíaca: " + patient.getCurrentVitalSigns().getHeartRate());
        System.out.println("Frequência respiratória: " + patient.getCurrentVitalSigns().getBreathRate());
        System.out.println("Pressão sanguínea: " + patient.getCurrentVitalSigns().getBloodPressure());
        System.out.println("Taxa de oxigênio no sangue: " + patient.getCurrentVitalSigns().getBloodOxygenRate());
        System.out.println("Temperatura corporal: " + patient.getCurrentVitalSigns().getBodyTemperature());
        System.out.println("Taxa de glicose: " + patient.getCurrentVitalSigns().getGlucoseRate());
        System.out.println("---------------------------------------------------");

        if(verifyEmergency(patient.getCurrentVitalSigns())){
            emergencyAlarm = true;
            
            System.out.println("\t\tEMERGÊNCIA!!!!!!");
            System.out.println("---------------------------------------------------");
            System.out.println("Paciente: " + patient.getName() + ", CPF: " + patient.getCpf());
        }
    }

    @Override
    public Patient admitPatient(String cpf, String password) throws RemoteException {
        
        for(Entry<String, Patient> pat : patients.entrySet()){
            if(pat.getKey().equals(cpf) && pat.getValue().getPassword().equals(password)){
                System.out.println("---------------------------------------------------");
                System.out.println("Paciente internado: " + pat.getValue().getName());
                System.out.println("---------------------------------------------------");

                return pat.getValue();
            }
        }
        
        // caso nao ache
        return null;
    }

        public static boolean verifyEmergency(VitalSigns vitalSigns){
            if(vitalSigns.getHeartRate() < 60 || vitalSigns.getHeartRate() > 100){ // faixa normal (60-100 bpm)
                return true;
            }
            if(vitalSigns.getBreathRate() < 12 || vitalSigns.getBreathRate() > 20){ // faixa normal (12-20 rpm)
                return true;
            }
            if(vitalSigns.getBloodPressure() < 90 || vitalSigns.getBloodPressure() > 120){ // faixa normal (90-120 mmHg)
                return true;
            }
            if(vitalSigns.getBloodOxygenRate() < 95.0f || vitalSigns.getBloodOxygenRate() > 100.0f){ // faixa normal (95%-100%)
                return true;
            }
            if(vitalSigns.getBodyTemperature() < 36.5f || vitalSigns.getBodyTemperature() > 37.5f){ // faixa normal (36.5°C-37.5°C)
                return true;
            }
            if(vitalSigns.getGlucoseRate() < 70 || vitalSigns.getGlucoseRate() > 110){ // faixa normal (70-110 mg/dL)
                return true;
            }
            
            return false;
        }
    
}
