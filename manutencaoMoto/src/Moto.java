import java.io.Serializable;
import java.util.Scanner;

public class Moto implements Serializable {
    String placa;
    String modelo;
    int relação;
    int oleoMotor;
    
    public String getPlaca() {
        return placa;
    }
    
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    public String getModelo() {
        return modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public int getRelação() {
        return relação;
    }
    
    public void setRelação(int relação) {
        this.relação = relação;
    }
    
    public int getOleoMotor() {
        return oleoMotor;
    }
    
    public void setOleoMotor(int oleoMotor) {
        this.oleoMotor = oleoMotor;
    }

    public void setMoto(Scanner entrada){
        System.out.println("Informe o modelo da moto: ");
        this.setModelo(entrada.nextLine());
        System.out.println("Informe a placa da moto: ");
        this.setPlaca(entrada.nextLine());// verificar se a placa ja nao foi castrada e se ela tem 7 digitos
        System.out.println("Informe a quilometragem em que foi trocado a relacao pela ultima vez:");
        this.setRelação(entrada.nextInt());
        entrada.nextLine();
        System.out.println("Informe a quilometragem em que foi trocado o oleo pela ultima vez:");
        this.setOleoMotor(entrada.nextInt());
        entrada.nextLine();
    }

    

}
