package model;

import java.io.Serializable;
import java.util.Queue;

public class Patient implements Serializable {
    
    private String name;
    private String cpf;
    private String password;
    private Status status;

    private VitalSigns currentVitalSigns;
    private Queue<VitalSigns> last10VitalSigns;

    public Patient(String name, String cpf, String password, int statusNumber, VitalSigns vitalSigns){
        this.name = name;
        this.cpf = cpf;
        this.password = password;
        this.currentVitalSigns = vitalSigns;

        switch (statusNumber) {
            case 1:
                this.status = Status.URGENTE;
                break;
            case 2:
                this.status = Status.GRAVE;
                break;
            case 3:
                this.status = Status.RECUPERACAO;
                break;
            case 4:
                this.status = Status.ESTAVEL;
                break; 
            default:
                this.status = Status.URGENTE;
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public VitalSigns getCurrentVitalSigns() {
        return currentVitalSigns;
    }

    public void setCurrentVitalSigns(VitalSigns currentVitalSigns) {
        this.currentVitalSigns = currentVitalSigns;
    }
    
    public Queue<VitalSigns> getLast10VitalSigns() {
        return last10VitalSigns;
    }

    public void setLast10VitalSigns(Queue<VitalSigns> last10VitalSigns) {
        this.last10VitalSigns = last10VitalSigns;
    }

}
