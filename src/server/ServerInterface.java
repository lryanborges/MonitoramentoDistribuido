package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import model.Patient;

public interface ServerInterface extends Remote {
    
    public void Teste() throws RemoteException;
    public void receiveVitalSigns(Patient patient) throws RemoteException;
    public Patient admitPatient(String cpf, String password) throws RemoteException;

}
