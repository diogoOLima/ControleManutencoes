import java.io.Serializable;
import java.util.Scanner;

public class Manual implements Serializable {
    private String modeloMoto;
    private int relação;
    private int oleoMotor;
    
    public String getModeloMoto() {
        return modeloMoto;
    }

    public void setModeloMoto(String modeloMoto) {
        this.modeloMoto = modeloMoto;
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
    
    public void setManual(Scanner entrada) throws Exception{
        System.out.println("Informe o nome do modelo da moto:");
        String modelo = entrada.nextLine();
            this.setModeloMoto(modelo);
            System.out.println("A durabilidade da relação em km:");
            this.setRelação(entrada.nextInt());
            entrada.nextLine();
            System.out.println("Informe a durabilidade do oleo do motor em km");
            this.setOleoMotor(entrada.nextInt());
            entrada.nextLine();    
    }
}
