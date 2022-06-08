
public class CriaArvoreB {
  public static void main (String[] args) throws Exception {
    ArvoreB arv = new ArvoreB (2);
    Item itemAdd = new Item(0);
    Item itemPesquisa = new Item(61000);
    for(int i = 10000; i < 60000; i++){
      itemAdd = new Item(i);
      arv.insere(itemAdd);
    }
    arv.pesquisa(itemPesquisa);
    System.out.println("Comparacoes: "+itemPesquisa.getComparacoes());
  }
}
