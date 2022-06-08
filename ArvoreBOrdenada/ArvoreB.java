

public class ArvoreB {
  private static class Pagina {
    int n; Item r[]; Pagina p[]; 
    public Pagina (int mm) { //Construtor de uma página.
      this.n = 0; this.r = new Item[mm]; this.p = new Pagina[mm+1];
    }
  }
  private Pagina raiz;
  private int m, mm;
  private Item pesquisa (Item reg, Pagina ap) {
    if (ap == null){ //Caso a página esteja vazia, retorna nulo
      reg.incrementaComparacoes();
      return null; 
    } 
    else {
      int i = 0;
      while ((i < ap.n-1) && (reg.compara (ap.r[i]) > 0)){
        reg.incrementaComparacoes();
        i++;
      }     
      if (reg.compara (ap.r[i]) == 0) {
        reg.incrementaComparacoes();
        return ap.r[i];
      }
        
      else if (reg.compara (ap.r[i]) < 0){
        reg.incrementaComparacoes();
        return pesquisa (reg, ap.p[i]);
      }  
      else{
        reg.incrementaComparacoes();
        return pesquisa (reg, ap.p[i+1]);
      } 
        
    }
  }
  //r -> vetor de item
  //n -> numero de itens
  //p -> vetor de paginas
  private void insereNaPagina (Pagina ap, Item reg, Pagina apDir) {
    int k = ap.n - 1; //K recebe o numero de elementos da pagina - 1.
    while ((k >= 0) && (reg.compara (ap.r[k]) < 0)) { //Enquanto houver elementos na pagina e nenhum item presente nela for zero.
      ap.r[k+1] = ap.r[k];   //O item seguinte é igual ao seu anterior, deslocando os elementos.
      ap.p[k+2] = ap.p[k+1]; 
      k--; 
    }
    ap.r[k+1] = reg; //O item seguinte passa a ser o item a ser inserido.
    ap.p[k+2] = apDir; 
    ap.n++;     
  }
  
  private Pagina insere (Item reg, Pagina ap, Item[] regRetorno, boolean[] cresceu) {
    Pagina apRetorno = null;
    if (ap == null) { 
      cresceu[0] = true; 
      regRetorno[0] = reg; 
    } 
    else { //Caso ap nao for nulo
      int i = 0;
      while ((i < ap.n-1) && (reg.compara (ap.r[i]) > 0)) i++;  //i recebe o numero de elementos presentes na pagina.    
      if (reg.compara (ap.r[i]) == 0) {
        System.out.println ("Erro: Registro ja existente");
        cresceu[0] = false;
      } 
      else {
        if (reg.compara (ap.r[i]) > 0) i++;
        apRetorno = insere (reg, ap.p[i], regRetorno, cresceu); //Chamada recursiva, insereindo o item
        if (cresceu[0]) //Caso o item tenha sido inserido, e cresceu[0] == true         
          if (ap.n < this.mm) { 
            this.insereNaPagina (ap, regRetorno[0], apRetorno); //Chama o metodo insereNaPagina
            cresceu[0] = false; apRetorno = ap;
          } 
          else { 
            Pagina apTemp = new Pagina (this.mm); 
            apTemp.p[0] = null;
            if (i <= this.m) {
              this.insereNaPagina (apTemp, ap.r[this.mm-1], ap.p[this.mm]);
              ap.n--;
              this.insereNaPagina (ap, regRetorno[0], apRetorno);
            } else this.insereNaPagina (apTemp, regRetorno[0], apRetorno);
            for (int j = this.m+1; j < this.mm; j++) {
              this.insereNaPagina (apTemp, ap.r[j], ap.p[j+1]);
              ap.p[j+1] = null; 
            }
            ap.n = this.m; apTemp.p[0] = ap.p[this.m+1]; 
            regRetorno[0] = ap.r[this.m]; apRetorno = apTemp;
          }
      }        
    }
    return (cresceu[0] ? apRetorno : ap);
  }


  public ArvoreB (int m) {
    this.raiz = null; this.m = m; this.mm = 2*m;
  }
  
  public Item pesquisa (Item reg) {
    return this.pesquisa (reg, this.raiz);
  }

  public void insere (Item reg) {
    Item regRetorno[] = new Item[1]; //Inicializa as variaves
    boolean cresceu[] = new boolean[1];
    Pagina apRetorno = this.insere (reg, this.raiz, regRetorno, cresceu); //Insere o item
    if (cresceu[0]) { //Caso a pagina tenha crescido
      Pagina apTemp = new Pagina(this.mm); //Inicializa uma nova pagina temporaria
      apTemp.r[0] = regRetorno[0];
      apTemp.p[0] = this.raiz;
      apTemp.p[1] = apRetorno;
      this.raiz = apTemp; this.raiz.n++;
    } else this.raiz = apRetorno;
  }

}
