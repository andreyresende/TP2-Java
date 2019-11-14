import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


class palavra{
    Integer frequencia;
    palavra(){
        frequencia = 0;
    }
}
class Map1{
    public ArrayList<String> linhas(String stopwords){
        String[] Stopwords = stopwords.split("\n");
        ArrayList<String> auxiliar = new ArrayList<>();
        for(String word : Stopwords){
            auxiliar.add(word);
        }
        return auxiliar;
    }
    public ArrayList<String> palavras(String texto){
        String[] TextWords = texto.split(" ");
        ArrayList<String> auxiliar = new ArrayList<>();
        for(String word : TextWords){
            auxiliar.add(word);
        }
        return auxiliar;
    }
}

class Map2{
    public ArrayList<String> removeStopwords(ArrayList<String> stopwordsList, ArrayList<String> textWordsList){
        textWordsList.removeAll(stopwordsList);
        return textWordsList;
    }

    public int howMany(ArrayList<String> textWordsList, String word){//Retorna quantas vezes o argumento aparece
        Integer number = 0;
        for(String test : textWordsList){
            if(word.equals(test)){
              number += 1;
            }
        }
        return number;
    }
    
    public ArrayList<String> semRepetir(ArrayList<String> textWordsList){//Monta a lista de palavras sem repetir
        ArrayList<String> semRepetir   = new ArrayList<String>();
        for(String word : textWordsList){
            if(!semRepetir.contains(word))
                semRepetir.add(word);
        }
        return semRepetir;
    }

    public ArrayList<Integer> wordCount(ArrayList<String> textWordsList){//Monta a lista de quantidade de repeticoes
        ArrayList<String> semRepetir   = new ArrayList<String>();
        ArrayList<Integer> quantidades = new ArrayList<Integer>();

        semRepetir = this.semRepetir(textWordsList);

        for(String word : semRepetir)
            quantidades.add(howMany(textWordsList, word));

        return quantidades;
    }
}

class Reduce1{
    public ArrayList<String> organizaArray(ArrayList<String> semRepetir, ArrayList<Integer> quantidades){
        ArrayList<String> palavraRepete = new ArrayList<String>();

        for(Integer i = 0; i < semRepetir.size(); i++){
            palavraRepete.add(semRepetir.get(i));
            palavraRepete.add(quantidades.get(i).toString());
        }
        return palavraRepete;
    }
}

class Reduce2{
    public Integer printaResultado(ArrayList<String> palavraRepete){
        for(Integer i = 0; i < palavraRepete.size(); i++){
            if(i % 2 == 0){
                System.out.print(palavraRepete.get(i));
                System.out.print(" ");
            }
            else{
                System.out.print(palavraRepete.get(i));
                System.out.println();
            }
        }
        return 0;
    }
}

public class dmr{
    public static void main(String[] args){
        Map1 map1       = new Map1();
        Map2 map2       = new Map2();
        Reduce1 reduce1 = new Reduce1();
        Reduce2 reduce2 = new Reduce2();
        Path path       = Paths.get("dracula.txt");
        Path path2      = Paths.get("stopwords.txt");
        ArrayList<String> textWordsList;    
        ArrayList<String> stopwordsList;
        ArrayList<String> semRepetir;
        ArrayList<Integer> quantidades;
        ArrayList<String> palavraRepete;

        try{
            byte[] leitorTexto       = Files.readAllBytes(path);
            String texto             = new String(leitorTexto);//Armazeno o texto na variavel
            texto = texto.toLowerCase();

            byte[] leitorStopwords   = Files.readAllBytes(path2);
            String stopwords         = new String(leitorStopwords);//Armazeno as stopwords na variavel
            //Input dividido em blocos()
            
            textWordsList = map1.palavras(texto);//Realiza a funcao split no texto por palavra
            stopwordsList = map1.linhas(stopwords);//Realiza a funcao split nas stopwords por linha
            //map1 realiza os splits

            textWordsList = map2.removeStopwords(stopwordsList, textWordsList);//Retira as stopwords do texto
            quantidades   = map2.wordCount(textWordsList);//Faz a contagem de cada palavra e armazena na lista
            semRepetir    = map2.semRepetir(textWordsList);//Cria uma lista com as palavras sem repeticoes
            //map2 retira as stopwords e faz a contagem de palavras

            palavraRepete = reduce1.organizaArray(semRepetir, quantidades);
            //reduce1 organiza os dados para serem mostrados
            reduce2.printaResultado(palavraRepete);
            //reduce2 mostra os dados previamente organizados de forma compreensivel
        }
        catch(Exception erro){
            System.out.println("Arquivo nao encontrado");
            System.exit(0);
        }
    }
}