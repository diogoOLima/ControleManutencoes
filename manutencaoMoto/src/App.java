import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner entrada = new Scanner(System.in);

        try {
            menu(entrada);
        }catch(InputMismatchException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            entrada.nextLine();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }   
    }

    private static void menu(Scanner entrada) throws Exception {
        File arqManual = new File("modelos.dat");
        ArrayList<Manual> modelos = new ArrayList<Manual>();
        Manual manualObjeto = new Manual();
        int decisaoSair = 0;
        File arqMotos = new File("motos.dat");
        Moto moto = new Moto();
        ArrayList<Moto> motos = new ArrayList<Moto>();
        String placa;
        int kmAtual;
        Boolean achouMoto = false;

        System.out.println("Bem vindo a app de controle de manutencoes periodicas de moto");
        System.out.println("Escolha uma opcao:");
        System.out.println("1-Cadastrar periodicidade de trocas de pecas de um modelo de motos");
        System.out.println("2-Cadastrar uma moto");
        System.out.println("3-Consultar se existem manutencoes a serem feitas");
        System.out.println("4-registrar uma troca de peca");
        int opcaoMenu = entrada.nextInt();
        entrada.nextLine();

        switch (opcaoMenu) {
            case 1:
                if(arqManual.length() != 0){
                    modelos = lerManuais(modelos, arqManual);

                    System.out.println("Modelos ja existentes: ");
                    System.out.println("|    Modelo|Troca relacao|Troca Oleo|");
                    for (Manual modelo : modelos) {
                        System.out.printf("|%10s|%13d|%10d|\n", modelo.getModeloMoto(), modelo.getRelação(), modelo.getOleoMotor());
                    }

                    System.out.println("Deseja sair, pois o modelo ja esta cadastrado?(1-sim / 0-nao)");
                    decisaoSair = entrada.nextInt();
                    entrada.nextLine();
                }

                if(decisaoSair == 0){
                    manualObjeto.setManual(entrada);

                    if(manualObjeto.getModeloMoto().length() > 4){
                        modelos.add(manualObjeto);
                        escreverManual(modelos, arqManual);
                    }else{
                        System.out.println("modelo nao cadastrado pois o nome deve conter mais de 4 caracteres");
                    }
                }
            break;
            
            case 2:
                Boolean motoRepetida = false;
                
                if(arqMotos.length() != 0){
                    if(arqMotos.length() != 0){
                        System.out.println("Motos ja cadastradas:");
                        motos = lerMotos(motos, arqMotos);  
                    }
                    System.out.println("Deseja sair, pois a moto ja esta cadastrada?(1-sim / 0-nao)");
                    decisaoSair = entrada.nextInt();
                    entrada.nextLine();
                }

                if(decisaoSair != 1){
                    boolean modeloValido = false;

                    if(arqManual.length() != 0){
                        modelos = lerManuais(modelos, arqManual);
                        
                        System.out.println("Modelos ja existentes: ");
                        for (Manual modelo : modelos) {
                            System.out.printf("%s %d %d\n", modelo.getModeloMoto(), modelo.getRelação(), modelo.getOleoMotor());
                        }
                        
                        moto.setMoto(entrada);
                        
                        for (Manual modelo : modelos) {
                            if(modelo.getModeloMoto().equals(moto.getModelo())){
                                modeloValido = true;
                            }
                        }
                        
                        for (Moto motoIndex : motos) {
                            if(motoIndex.getPlaca().equals(moto.getPlaca())){
                                motoRepetida = true;
                            }   
                        }
                        
                        if(modeloValido){
                            if(!motoRepetida){
                                motos.add(moto);
                                escreverMotos(motos, arqMotos);
                            }else{
                                System.out.println("placa ja cadastrada");
                            }
                        }else{
                            System.out.println("Modelo digitado nao encontrado");
                        }
                    }else{
                        System.out.println("Não é possivel cadastrar motos, pois nao existem modelos cadastrados ainda");
                    }
                }
            break;

            case 3:
                motos = lerMotos(motos, arqMotos);

                System.out.println("Informe qual é a placa da sua moto");
                placa = entrada.nextLine();
                System.out.println("Informe quilometragem atual da moto");
                kmAtual = entrada.nextInt();

                modelos = lerManuais(modelos, arqManual);

                for (Moto motoIndex : motos) {
                    if(motoIndex.getPlaca().equals(placa)){
                        achouMoto = true;
                        for (Manual modelo : modelos) {
                            if(motoIndex.getModelo().equals(modelo.getModeloMoto())){
                                if(motoIndex.getRelação() + modelo.getRelação() <= kmAtual){
                                    System.out.println("Voce precisa trocar sua relacao");
                                }else{
                                    System.out.printf("Voce ainda nao atingiu a quilometragem necessaria para a troca da relação (%d) \n",(motoIndex.getRelação() + modelo.getRelação()));
                                }
                                
                                if(motoIndex.getOleoMotor() + modelo.getOleoMotor() <= kmAtual){
                                    System.out.println("Voce precisa trocar o oleo");
                                }else{
                                    System.out.printf("Voce ainda nao atingiu a quilometragem necessaria para troca do oleo (%d) \n",(motoIndex.getOleoMotor() + modelo.getOleoMotor()));
                                }
                            }
                        }
                    }
                }
                
                if(!achouMoto){
                    System.out.println("Moto nao encontrada");
                }     
            break;

            case 4:
                motos = lerMotos(motos, arqMotos);
                
                System.out.println("Informe qual é a placa sua moto");
                placa = entrada.nextLine();
                System.out.println("Informe quilometragem em que trocou a peca");
                int kmDaTroca = entrada.nextInt();
                System.out.println("Voce trocou: 1-Relacao ou 2-Oleo");
                int peca = entrada.nextInt();
                entrada.nextLine();
                
                if(motos.size() != 0){
                    for( int x = 0 ; x < motos.size() ; x++ ){
                        if(motos.get(x).getPlaca().equals(placa)){
                            achouMoto = true;
                            
                            if(peca == 1){
                                motos.get(x).setRelação(kmDaTroca);
                            }

                            if(peca == 2){
                                motos.get(x).setOleoMotor(kmDaTroca);
                            }
                        }
                    }
                }
                
                if(achouMoto){
                    escreverMotos(motos, arqMotos);
                }else{
                    System.out.println("Moto nao encontrada");
                }
            break;

            default:
                System.out.println("Opcao invalida");
        }
    }

    private static void escreverMotos(ArrayList<Moto> motos, File arqMotos) {
        ObjectOutputStream oos = null;
        
        try {
            oos = new ObjectOutputStream(new FileOutputStream(arqMotos));
            oos.writeObject(motos);
        } catch (IOException e) {
            System.out.println(e.getMessage());  
        }finally{
            if(oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    System.out.println("Erro ao fechar arquivo.");
                }
            }
        }
    }

    private static void escreverManual(ArrayList<Manual> modelos,  File arqManual){
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(new FileOutputStream(arqManual));
            oos.writeObject(modelos);
        }catch (IOException e) {
            System.out.println(e.getMessage());   
        }finally{
            if(oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    System.out.println("Erro ao fechar arquivo.");
                }
            }
        }
    }

    private static ArrayList<Manual> lerManuais(ArrayList<Manual> modelos, File arqManual) {
        ObjectInputStream ois = null;

        try{
            ois =  new ObjectInputStream(new FileInputStream(arqManual));
            modelos = (ArrayList<Manual>)ois.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("Classe nao encontrada");
        } catch (IOException e ){
            System.out.println("Erro ao criar o arquivo");
        } finally{
            if(ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    System.out.println("Erro ao fechar arquivo.");
                }
            }
        }

        return modelos;
    }

    private static ArrayList<Moto> lerMotos(ArrayList<Moto> motos, File arqMotos) {
        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(new FileInputStream(arqMotos));
            motos = (ArrayList<Moto>)ois.readObject();
            System.out.println("Motos ja cadastradas:");
            for (Moto moto : motos) {
                System.out.printf("placa: %s modelo: %s \n", moto.getPlaca(), moto.getModelo());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Classe nao encontrada");
        } catch (IOException e ){
            System.out.println("Erro ao ler o arquivo");
            e.printStackTrace();
        } finally{
            if(ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    System.out.println("Erro ao fechar arquivo.");
                }
            }
        }

        return motos;
    }
}
