package server;

import java.util.Scanner;

import model.Patient;
import model.VitalSigns;

public class RegisterThread extends Thread {

    @Override
    public void run() {

        Scanner scan = new Scanner(System.in);

        while(Server.isOn){

            scan.nextLine(); // p ficar bonito no CMD

            System.out.println("---------------------------------------------------");
            System.out.println("\t\tREGISTRAR PACIENTE");
            System.out.println("---------------------------------------------------");
            System.out.print("Nome: ");
            String name = scan.nextLine();
            System.out.print("CPF: ");
            String cpf = scan.nextLine();
            System.out.print("Senha: ");
            String password = scan.nextLine();

            int status = 0;
            do {
                System.out.println("--------------------");
                System.out.println("STATUS DO PACIENTE");
                System.out.println("--------------------");
                System.out.println("[1] - Urgente");
                System.out.println("[2] - Grave");
                System.out.println("[3] - Recuperação");
                System.out.println("[4] - Estável");
                System.out.println("-----------------------");
                System.out.print("Status: ");
                status = scan.nextInt();
                scan.nextLine();
            } while(status < 1 || status > 4);

            VitalSigns vs = new VitalSigns();
            System.out.println("---------------------------------------------------");
            System.out.println("\t\tDADOS INICIAIS");
            System.out.println("---------------------------------------------------");
            System.out.print("Frequência Cardíaca: ");
            vs.setHeartRate(scan.nextInt());
            scan.nextLine();
            System.out.print("Frequência Respiratória: ");
            vs.setBreathRate(scan.nextInt());
            scan.nextLine();
            System.out.print("Pressão Sanguínea: ");
            vs.setBloodPressure(scan.nextInt());
            scan.nextLine();
            System.out.print("Taxa de Oxigênio no Sangue: ");
            vs.setBloodOxygenRate(scan.nextFloat());
            scan.nextLine();
            System.out.print("Temperatura Corporal: ");
            vs.setBodyTemperature(scan.nextFloat());
            scan.nextLine();
            System.out.print("Taxa de Glicose: ");
            vs.setGlucoseRate(scan.nextInt());
            scan.nextLine();

            try {
				registerPatient(new Patient(name, cpf, password, status, vs));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            System.out.println("---------------------------------------------------");
            
        }

        scan.close();

    }

    public void registerPatient(Patient newPatient) throws Exception {
        Server.patients.put(newPatient.getCpf(), newPatient);
        Server.salvarLista();
        System.out.println("---------------------------------------------------");
        System.out.println("Paciente cadastrado com sucesso.");
    }
    
}
